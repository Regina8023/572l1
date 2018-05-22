package handler;

import java.io.*;
//import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import parserdef.Meaquantity;
import parserdef.Unit;
import parserdef.Quantity;
import parserdef.ExternalComponent;

public class test {
	public static void main(String[] args) {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
	    try {
	    	String fileName = "validate.xml";
	    	
	        SAXParser saxParser = saxParserFactory.newSAXParser();
	        
	        HandlerForMeaquantity handlerMea = new HandlerForMeaquantity();	    
	        saxParser.parse(new File(fileName), handlerMea);
	        List<Meaquantity> meaquantityList = handlerMea.getMeaquantity();
	        for(Meaquantity mea : meaquantityList)
	            System.out.println(mea);
	        
	        HandlerForUnit handlerUnit = new HandlerForUnit();
	        saxParser.parse(new File(fileName), handlerUnit);
	        List<Unit> unitList = handlerUnit.getUnit();
	        for(Unit unit : unitList)
	            System.out.println(unit);
	        
	        HandlerForQuantity handlerQuantity = new HandlerForQuantity();
	        saxParser.parse(new File(fileName), handlerQuantity);
	        List<Quantity> quantityList = handlerQuantity.getQuantity();
	        for(Quantity quantity : quantityList)
	            System.out.println(quantity);
	        
	        HandlerForExternalComponent handlerExternalComponent = new HandlerForExternalComponent();
	        saxParser.parse(new File(fileName), handlerExternalComponent);
	        List<ExternalComponent> externalComponentList = handlerExternalComponent.getExternalComponent();
	        for(ExternalComponent ext : externalComponentList)
	            System.out.println(ext);
	        
	        for(int i = 0; i < externalComponentList.size(); i++) {
	        	ExternalComponent ext = externalComponentList.get(i);
	        	String inputFile = ext.getFilenameURL();
	        	try(InputStream inputStream = new FileInputStream(inputFile);) {
	        		long fileSize = new File(inputFile).length();
	        		byte[] allBytes = new byte[8];
	        		for (int j = 0; j < 8; j++)
	        			allBytes[j] = (byte)inputStream.read();
	        		int j = 0;
	        		long x = 0;
	        		for ( int shiftBy = 0; shiftBy < 64; shiftBy += 8 ) {
	        	        x |= ( (long)( allBytes[j] & 0xff ) ) << shiftBy;
	        	        j++;
	        	    }
	        		System.out.println(Double.longBitsToDouble(x));
	        	}
	        }
	        
	    } catch (ParserConfigurationException | SAXException | IOException e) {
	        e.printStackTrace();
	    }
	}
}
