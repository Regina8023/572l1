package handler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import parserdef.Meaquantity;
import parserdef.Unit;
import parserdef.Quantity;
import parserdef.ExternalComponent;
import handler.Data;

public class test {
	public static List<Data> data = new ArrayList<>();
	public static Object[][] fromBin = new Object[30][5000];
	
	public static String convertBin(byte b[], String dataType, int dataLen) {
		//System.out.println("!!!!"+dataType);
		if (dataType.equals("DT_DOUBLE")) {
			//System.out.println("???");
			int j = 0;
			long x = 0;
			for ( int shiftBy = 0; shiftBy < 64; shiftBy += 8 ) {
				x |= ( (long)( b[j] & 0xff ) ) << shiftBy;
				j++;
			}
			return String.valueOf(Double.longBitsToDouble(x));
		} else if (dataType.equals("DT_LONG")) {
			int j = 0;
			long x = 0;
			for ( int shiftBy = 0; shiftBy < 32; shiftBy += 8 ) {
				x |= ( (long)( b[j] & 0xFFL ) ) << shiftBy;
				j++;
			}
			return Long.toString(x);
		} else if (dataType.equals("DT_FLOAT")) {
			int j = 0;
			int x = 0;
			for ( int shiftBy = 0; shiftBy < 32; shiftBy += 8 ) {
				x |= ( (int)( b[j] & 0xFF ) ) << shiftBy;
				j++;
			}
			return String.valueOf(Float.intBitsToFloat(x));
		} else if (dataType.equals("DT_SHORT")) {
			int j = 0;
			long x = 0;
			for ( int shiftBy = 0; shiftBy < 16; shiftBy += 8 ) {
				x |= ( (long)( b[j] & 0xFF ) ) << shiftBy;
				j++;
			}
			return String.valueOf((short)x);
		}
		return "";
	}
	
	
	public static void main(String[] args) {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
	    try {
	    	String fileName = "validate.xml";
	    	
	        SAXParser saxParser = saxParserFactory.newSAXParser();
	        
	        //Parse information from xml	        
	        HandlerForMeaquantity handlerMea = new HandlerForMeaquantity();	    
	        saxParser.parse(new File(fileName), handlerMea);
	        List<Meaquantity> meaquantityList = handlerMea.getMeaquantity();
	             
	        HandlerForUnit handlerUnit = new HandlerForUnit();
	        saxParser.parse(new File(fileName), handlerUnit);
	        List<Unit> unitList = handlerUnit.getUnit();	        
	        
	        HandlerForQuantity handlerQuantity = new HandlerForQuantity();
	        saxParser.parse(new File(fileName), handlerQuantity);
	        List<Quantity> quantityList = handlerQuantity.getQuantity();
	        
	        HandlerForExternalComponent handlerExternalComponent = new HandlerForExternalComponent();
	        saxParser.parse(new File(fileName), handlerExternalComponent);
	        List<ExternalComponent> externalComponentList = handlerExternalComponent.getExternalComponent();
	        
	        //Print parsed information for debugging
	        
	        for(Meaquantity mea : meaquantityList)
	            System.out.println(mea);
	        for(Unit unit : unitList)
	            System.out.println(unit);
	        for(Quantity quantity : quantityList)
	            System.out.println(quantity);
	        for(ExternalComponent ext : externalComponentList)
	            System.out.println(ext);
	        
	        
	        //Construct data class for each series
	        for (int i = 0; i < meaquantityList.size(); i++) {
	        	Meaquantity mea = meaquantityList.get(i);
	        	Data d = new Data();
	        	d.setName(mea.getName());
	        	d.setId(mea.getId());
	        	d.setDatatype(mea.getDatatype());
	        	//set unit
	        	Unit u = null;
	        	for (int j = 0; j < unitList.size(); j++) {
	        		u = unitList.get(j);
	        		if (u.getId() == mea.getUnit())
	        			break;
	        	}
	        	d.setUnit(u.getName());
	        	//set quantity
	        	Quantity q = null;
	        	for (int j = 0; j < quantityList.size(); j++) {
	        		q = quantityList.get(j);
	        		if (q.getId() == mea.getQuantity())
	        			break;
	        	}
	        	d.setQuantity(q.getName());
	        	//set e
	        	ExternalComponent e = null;
	        	for (int j = 0; j < externalComponentList.size(); j++) {
	        		e = externalComponentList.get(j);
	        		if (e.getId() == mea.getId())
	        			break;
	        	}
	        	d.setE(e);
	        	data.add(d);
	        }
	       
	        //print data information for debugging
	        /*
	        for (int i = 0; i < data.size(); i++) {
	        	System.out.println("For " + i + ": quantity = " + data.get(i).getQuantity());
	        }
	        */
	        
	        //read data from bin and store in a vector
	        for(int i = 0; i < data.size(); i++) {
	        	ExternalComponent ext = data.get(i).getE();
	        	String inputFile = ext.getFilenameURL();
	        	String dataType = data.get(i).getDatatype();
	        	int dataLen = data.get(i).getDatalen();
	        	int id = data.get(i).getId();
	        	try(InputStream inputStream = new FileInputStream(inputFile);) {
	        		System.out.println("******"+ext.getStart());
	        		FilterInputStream fis = new BufferedInputStream(inputStream);
	        		int startPos = ext.getStart(), blockSize = ext.getBlocksize();
	        		int len = ext.getLength(), valueoff = ext.getValueoffset();
	        		int numPerBlock = ext.getPerblock();
	        		int numOfData = 0;
	        		byte[] allBytes = new byte[ext.getLength() + startPos];
	        		fis.read(allBytes, 0, ext.getLength() + startPos - 1);
	        		
	        		int curLen = 0;
	        		while (curLen + blockSize <= len) {
	        			int curPos = startPos + curLen + valueoff;
	        			for (int j = 0; j < numPerBlock; j++) {
	        				byte[] Bytes = new byte[dataLen];
	        				for (int k = curPos; k < curPos + dataLen; k++)
	        					Bytes[k-curPos] = allBytes[k];
	        				//System.out.println("!!!!"+dataType);
	        				String s = convertBin(Bytes, dataType, dataLen);
	        				if (curLen < 20)
	        					System.out.println(s);
	        				fromBin[id][numOfData] = s;
	        				numOfData++;
	        			}
	        			curLen += blockSize;
	        		}
	        		
	        		data.get(i).setNumber(numOfData);
	        	}
	        }
	        
	    } catch (ParserConfigurationException | SAXException | IOException e) {
	        e.printStackTrace();
	    }
	}
}
