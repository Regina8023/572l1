package handler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xml.sax.SAXException;

import parserdef.ExternalComponent;
import parserdef.Meaquantity;
import parserdef.Phydim;
import parserdef.Unit;

public class test {
	public static List<Data> data = new ArrayList<>();
	public static Vector< Vector<Object> > fromBin = new Vector< Vector<Object>>();
	
	
	public static String convertBin(byte b[], String dataType, int dataLen, int i) {
		//System.out.println("!!!!"+dataType);
		if (dataType.equals("DT_DOUBLE")) {
			//System.out.println("???");
			int j = 0;
			long x = 0;
			for ( int shiftBy = 0; shiftBy < 64; shiftBy += 8 ) {
				x |= ( (long)( b[j] & 0xff ) ) << shiftBy;
				j++;
			}
			data.get(i).HandleData(Double.longBitsToDouble(x));
			return String.valueOf(Double.longBitsToDouble(x));
		} else if (dataType.equals("DT_LONG")) {
			int j = 0;
			long x = 0;
			for ( int shiftBy = 0; shiftBy < 32; shiftBy += 8 ) {
				x |= ( (long)( b[j] & 0xFFL ) ) << shiftBy;
				j++;
			}
			data.get(i).HandleData(x);
			return Long.toString(x);
		} else if (dataType.equals("DT_FLOAT")) {
			int j = 0;
			int x = 0;
			for ( int shiftBy = 0; shiftBy < 32; shiftBy += 8 ) {
				x |= ( (int)( b[j] & 0xFF ) ) << shiftBy;
				j++;
			}
			data.get(i).HandleData(Float.intBitsToFloat(x));
			return String.valueOf(Float.intBitsToFloat(x));
		} else if (dataType.equals("DT_SHORT")) {
			int j = 0;
			long x = 0;
			for ( int shiftBy = 0; shiftBy < 16; shiftBy += 8 ) {
				x |= ( (long)( b[j] & 0xFF ) ) << shiftBy;
				j++;
			}
			data.get(i).HandleData((short)x);
			return String.valueOf((short)x);
		}
		return "";
	}
	
	public static boolean exportToXlsx(Vector<Vector<Object> > datatypes) {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
	        XSSFSheet sheet = workbook.createSheet("Datatypes in Java");
	        
	        int rowNum = 0;
	        System.out.println("Creating excel");
	        
	        Row row = sheet.createRow(rowNum++);
	        for (int i = 0; i < data.size(); i++) {
	        	Cell cell = row.createCell(i);
	        	cell.setCellValue((String)data.get(i).getName());
	        }
	        
	        row = sheet.createRow(rowNum++);
	        for (int i = 0; i < data.size(); i++) {
	        	Cell cell = row.createCell(i);
	        	cell.setCellValue((String)data.get(i).getQuantity());
	        }
	        
	        row = sheet.createRow(rowNum++);
	        for (int i = 0; i < data.size(); i++) {
	        	Cell cell = row.createCell(i);
	        	cell.setCellValue((String)data.get(i).getUnit());
	        }
	        boolean flag = true;
	        for (Vector<Object> datatype : datatypes) {
	            row = sheet.createRow(rowNum++);
	            int colNum = 0;
	            for (Object field : datatype) {
	                Cell cell = row.createCell(colNum++);
	                if (flag && colNum==1 && field==null) {
	                	flag = false;
	                	System.out.println(rowNum);
	                }	             
	                if (field != null)
	                	cell.setCellValue(Double.parseDouble((String)field));	               
	            }
	        }

	        try {
	        	File file = new File("output.xlsx");
				// if file doesn't exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}
	            FileOutputStream outputStream = new FileOutputStream(file);
	            workbook.write(outputStream);
	            workbook.close();
	            outputStream.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        System.out.println("Done");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static List<Data> Query(String fileName) {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		data.clear();
	    try {
	        SAXParser saxParser = saxParserFactory.newSAXParser();
	        
	        //Parse information from xml	        
	        HandlerForMeaquantity handlerMea = new HandlerForMeaquantity();	    
	        saxParser.parse(new File(fileName), handlerMea);
	        List<Meaquantity> meaquantityList = handlerMea.getMeaquantity();
	             
	        HandlerForUnit handlerUnit = new HandlerForUnit();
	        saxParser.parse(new File(fileName), handlerUnit);
	        List<Unit> unitList = handlerUnit.getUnit();	        
	        
	        HandlerForPhydim handlerPhydim = new HandlerForPhydim();
	        saxParser.parse(new File(fileName), handlerPhydim);
	        List<Phydim> phydimList = handlerPhydim.getPhydim();
	        
	        HandlerForExternalComponent handlerExternalComponent = new HandlerForExternalComponent();
	        saxParser.parse(new File(fileName), handlerExternalComponent);
	        List<ExternalComponent> externalComponentList = handlerExternalComponent.getExternalComponent();
	        
	        //Print parsed information for debugging
	        
	        for(Meaquantity mea : meaquantityList)
	            System.out.println(mea);
	        for(Unit unit : unitList)
	            System.out.println(unit);
	        for(Phydim phydim : phydimList)
	            System.out.println(phydim);
	        for(ExternalComponent ext : externalComponentList)
	            System.out.println(ext);
	        
	        
	        //Construct data class for each series
	        for (int i = 0; i < meaquantityList.size(); i++) {
	        	Meaquantity mea = meaquantityList.get(i);
	        	Data d;
	        	switch (mea.getDatatype()) {
	        	case "DT_DOUBLE":
	        		d = new Data<Double>();
	        		break;
	        	case "DT_LONG":
	        		d = new Data<Long>();
	        		break;
	        	case "DT_FLOAT":
	        		d = new Data<Float>();
	        		break;
	        	case "DT_SHORT":
	        		d = new Data<Short>();
	        		break;
	        	default:
	        		throw new IllegalArgumentException("Invalid data type: "+mea.getDatatype());
	        	}
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
	        	Phydim p = null;
	        	for (int k = 0; k < phydimList.size(); k++) {
	        		p = phydimList.get(k);
	        		if (p.getId() == u.getPhydim())
	        			break;
	        	}
	        	d.setQuantity(p.getName());
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
	        	System.out.println("For " + i + ": quantity = " + data.get(i).getPhydim());
	        }
	        */
	        
	        //read data from bin and store in a vector
	        for(int i = 0; i < data.size(); i++) {
	        	Vector<Object> temp = new Vector<Object>();
	        	ExternalComponent ext = data.get(i).getE();
	        	String inputFile = ext.getFilenameURL();
	        	String dataType = data.get(i).getDatatype();
	        	int dataLen = data.get(i).getDatalen();
				int id = data.get(i).getId();
				
				InputStream inputStream = new FileInputStream(inputFile);
				FilterInputStream fis = new BufferedInputStream(inputStream);
				int startPos = ext.getStart(), blockSize = ext.getBlocksize();
				int len = ext.getLength(), valueoff = ext.getValueoffset();
				int numPerBlock = ext.getPerblock();
				int numOfData = 0;
				long size = inputStream.available();
				byte[] allBytes = new byte[(int) size];
				fis.read(allBytes, 0, (int) size);
				fis.close();
				int curLen = 0;
				while (numOfData < len) {
					int curPos = startPos + curLen + valueoff;
					for (int j = 0; j < numPerBlock; j++) {
						byte[] Bytes = new byte[dataLen];
						for (int k = curPos; k < curPos + dataLen; k++)
							Bytes[k - curPos] = allBytes[k];
						String s = convertBin(Bytes, dataType, dataLen, i);
						temp.add(s);
						numOfData++;
						curPos += dataLen;
					}
					curLen += blockSize;
				}
				fromBin.add(temp);
				assert(data.get(i).getNumber()==numOfData);
				System.out.println("*****" + data.get(i).getNumber()+" MAX:"+data.get(i).getMax()+" MIN:"+data.get(i).getMin()
						+" Med:"+data.get(i).getMed()+" Sum:"+data.get(i).getSum()+" Avg:"+data.get(i).getAvg());
	        }
	        exportToXlsx(fromBin);
	    } catch (ParserConfigurationException | SAXException | IOException e) {
	        e.printStackTrace();
	    }
	    return data;
	}
}
