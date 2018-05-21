package handler;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import parserdef.Quantity;

public class HandlerForQuantity extends DefaultHandler {
	private List<Quantity> quantityList = null;
	private Quantity quantity = null;
	
	public List<Quantity> getQuantity() {
		return quantityList;
	}
	
	boolean inQuantity = false;
	boolean bName = false;
	boolean bId = false;
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("Quantity")) {
			inQuantity = true;
			quantity = new Quantity();
			if (quantityList == null) 
				quantityList = new ArrayList<>();
		} else if (qName.equalsIgnoreCase("Name")) {
			if (inQuantity)
				bName = true;
		} else if (qName.equalsIgnoreCase("Id")) {
			if (inQuantity)
				bId = true;
		} 
	}
	
	@Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("Quantity")) {
        	if (quantity.getName() != null)
        		quantityList.add(quantity);
            inQuantity = false;
        }
    }
	
	@Override
    public void characters(char ch[], int start, int length) throws SAXException {
		if (bName) {
			quantity.setName(new String(ch, start, length));
			bName = false;
		} else if (bId) {
			quantity.setId(Integer.parseInt(new String(ch, start, length)));
			bId = false;
		}
	}
}
