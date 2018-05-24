package handler;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import parserdef.Phydim;

public class HandlerForPhydim extends DefaultHandler {
	private List<Phydim> phydimList = null;
	private Phydim phydim = null;
	
	public List<Phydim> getPhydim() {
		return phydimList;
	}
	
	boolean inPhydim = false;
	boolean bName = false;
	boolean bId = false;
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("PhysDimension")) {
			inPhydim = true;
			phydim = new Phydim();
			if (phydimList == null) 
				phydimList = new ArrayList<>();
		} else if (qName.equalsIgnoreCase("Name")) {
			if (inPhydim)
				bName = true;
		} else if (qName.equalsIgnoreCase("Id")) {
			if (inPhydim)
				bId = true;
		} 
	}
	
	@Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("PhysDimension")) {
        	if (phydim.getName() != null)
        		phydimList.add(phydim);
            inPhydim = false;
        } else if (qName.equalsIgnoreCase("Name")) {
			if (inPhydim)
				bName = false;
		} else if (qName.equalsIgnoreCase("Id")) {
			if (inPhydim)
				bId = false;
		} 
    }
	
	@Override
    public void characters(char ch[], int start, int length) throws SAXException {
		if (bName) {
			String temp = phydim.getName();
			phydim.setName(temp==null?new String(ch, start, length):temp+new String(ch, start, length));
		} else if (bId) {
			phydim.setId(Integer.parseInt(new String(ch, start, length)));
		}
	}
}
