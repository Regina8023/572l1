package handler;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import parserdef.Unit;

public class HandlerForUnit extends DefaultHandler {
	private List<Unit> unitList = null;
	private Unit unit = null;
	
	public List<Unit> getUnit() {
		return unitList;
	}
	
	boolean inUnit = false;
	boolean bName = false;
	boolean bId = false;
	boolean bPhydim = false;
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("Unit")) {
			inUnit = true;
			unit = new Unit();
			if (unitList == null) 
				unitList = new ArrayList<>();
		} else if (qName.equalsIgnoreCase("Name")) {
			if (inUnit)
				bName = true;
		} else if (qName.equalsIgnoreCase("Id")) {
			if (inUnit)
				bId = true;
		} else if (qName.equalsIgnoreCase("PhysDimension")) {
			if (inUnit)
				bPhydim = true;
		}
	}
	
	@Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("Unit")) {
        	if (unit.getName() != null)
        		unitList.add(unit);
            inUnit = false;
        } else if (qName.equalsIgnoreCase("Name")) {
			if (inUnit)
				bName = false;
		} else if (qName.equalsIgnoreCase("Id")) {
			if (inUnit)
				bId = false;
		} else if (qName.equalsIgnoreCase("PhysDimension")) {
			if (inUnit)
				bPhydim = false;
		}
    }
	
	@Override
    public void characters(char ch[], int start, int length) throws SAXException {
		if (bName) {
			String temp = unit.getName();
			unit.setName(temp==null?new String(ch, start, length):temp+new String(ch, start, length));
		} else if (bId) {
			unit.setId(Integer.parseInt(new String(ch, start, length)));
		} else if (bPhydim) {
			unit.setPhydim(Integer.parseInt(new String(ch, start, length)));
		}
	}
}
