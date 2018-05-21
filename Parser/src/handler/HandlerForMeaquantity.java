package handler;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import parserdef.Meaquantity;

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
        }
    }
	
	@Override
    public void characters(char ch[], int start, int length) throws SAXException {
		if (bName) {
			mea.setName(new String(ch, start, length));
			bName = false;
		} else if (bId) {
			mea.setId(Integer.parseInt(new String(ch, start, length)));
			bId = false;
		} else if (bDatatype) {
			mea.setDatatype(new String(ch, start, length));
			bDatatype = false;
		} else if (bUnit) {
			mea.setUnit(Integer.parseInt(new String(ch, start, length)));
			bUnit = false;
		} else if (bQuantity) {
			mea.setQuantity(Integer.parseInt(new String(ch, start, length)));
			bQuantity = false;
		} 
	}
}
