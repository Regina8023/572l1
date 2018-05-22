package handler;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import parserdef.Meaquantity;
import static java.lang.System.out;

public class HandlerForMeaquantity extends DefaultHandler {
	private List<Meaquantity> meaquantityList = null;
	private Meaquantity mea = null;
	
	public List<Meaquantity> getMeaquantity() {
		return meaquantityList;
	}
	
	boolean inMeaquantity = false;
	boolean bName = false;
	boolean bDatatype = false;
	boolean bUnit = false;
	boolean bId = false;
	boolean bQuantity = false;
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("MeaQuantity")) {
			inMeaquantity = true;
			mea = new Meaquantity();
			if (meaquantityList == null) 
				meaquantityList = new ArrayList<>();
		} else if (qName.equalsIgnoreCase("Name")) {
			if (inMeaquantity)
				bName = true;
		} else if (qName.equalsIgnoreCase("Id")) {
			if (inMeaquantity)
				bId = true;
		} else if (qName.equalsIgnoreCase("DataType")) {
			if (inMeaquantity)
				bDatatype = true;
		} else if (qName.equalsIgnoreCase("Unit")) {
			if (inMeaquantity)
				bUnit = true;
		} else if (qName.equalsIgnoreCase("Quantity")) {
			if (inMeaquantity)
				bQuantity = true;
		}
	}
	
	@Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("MeaQuantity")) {
        	if (mea.getName() != null)
        		meaquantityList.add(mea);
            inMeaquantity = false;
        } else if (qName.equalsIgnoreCase("Name")) {
        	if (inMeaquantity)
				bName= false;
        }  else if (qName.equalsIgnoreCase("Id")) {
			if (inMeaquantity)
				bId = false;
		} else if (qName.equalsIgnoreCase("DataType")) {
			if (inMeaquantity)
				bDatatype = false;
		} else if (qName.equalsIgnoreCase("Unit")) {
			if (inMeaquantity)
				bUnit = false;
		} else if (qName.equalsIgnoreCase("Quantity")) {
			if (inMeaquantity)
				bQuantity = false;
		}
    }
	
	@Override
    public void characters(char ch[], int start, int length) throws SAXException {
		if (bName) {
			String temp = mea.getName();
			mea.setName(temp==null?new String(ch, start, length):temp+new String(ch, start, length));
		} else if (bId) {
			mea.setId(Integer.parseInt(new String(ch, start, length)));
		} else if (bDatatype) {
			String temp = mea.getDatatype();
			mea.setDatatype(temp==null?new String(ch, start, length):temp+new String(ch, start, length));
		} else if (bUnit) {
			mea.setUnit(Integer.parseInt(new String(ch, start, length)));
		} else if (bQuantity) {
			mea.setQuantity(Integer.parseInt(new String(ch, start, length)));
		} 
	}
}
