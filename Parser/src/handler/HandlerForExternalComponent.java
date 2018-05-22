package handler;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import parserdef.ExternalComponent;

public class HandlerForExternalComponent extends DefaultHandler {
	private List<ExternalComponent> externalComponentList = null;
	private ExternalComponent ext = null;
	
	public List<ExternalComponent> getExternalComponent() {
		return externalComponentList;
	}
	
	boolean inExternalComponent = false;
	boolean bFilenameURL = false;
	boolean bStart = false;
	boolean bLength = false;
	boolean bId = false;
	boolean bBlocksize = false;
	boolean bValueoffset = false;
	boolean bPerblock = false;
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("ExternalComponent")) {
			inExternalComponent = true;
			ext = new ExternalComponent();
			if (externalComponentList == null) 
				externalComponentList = new ArrayList<>();
		} else if (qName.equalsIgnoreCase("FilenameURL")) {
			if (inExternalComponent)
				bFilenameURL = true;
		} else if (qName.equalsIgnoreCase("Id")) {
			if (inExternalComponent)
				bId = true;
		} else if (qName.equalsIgnoreCase("StartOffset")) {
			if (inExternalComponent)
				bStart = true;
		} else if (qName.equalsIgnoreCase("Blocksize")) {
			if (inExternalComponent)
				bBlocksize = true;
		} else if (qName.equalsIgnoreCase("ValuesPerBlock")) {
			if (inExternalComponent)
				bPerblock = true;
		} else if (qName.equalsIgnoreCase("ValueOffset")) {
			if (inExternalComponent)
				bValueoffset = true;
		} else if (qName.equalsIgnoreCase("Length")) {
			if (inExternalComponent)
				bLength = true;
		}
	}
	
	@Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("ExternalComponent")) {
        	if (ext.getFilenameURL() != null) {
        		ext.setSize();
        		externalComponentList.add(ext);
        	} 		
            inExternalComponent = false;
        } else if (qName.equalsIgnoreCase("FilenameURL")) {
			if (inExternalComponent)
				bFilenameURL = false;
		} else if (qName.equalsIgnoreCase("Id")) {
			if (inExternalComponent)
				bId = false;
		} else if (qName.equalsIgnoreCase("StartOffset")) {
			if (inExternalComponent)
				bStart = false;
		} else if (qName.equalsIgnoreCase("Blocksize")) {
			if (inExternalComponent)
				bBlocksize = false;
		} else if (qName.equalsIgnoreCase("ValuesPerBlock")) {
			if (inExternalComponent)
				bPerblock = false;
		} else if (qName.equalsIgnoreCase("ValueOffset")) {
			if (inExternalComponent)
				bValueoffset = false;
		} else if (qName.equalsIgnoreCase("Length")) {
			if (inExternalComponent)
				bLength = false;
		}
    }
	
	@Override
    public void characters(char ch[], int start, int length) throws SAXException {
		if (bFilenameURL) {
			String temp = ext.getFilenameURL();
			ext.setFilenameURL(temp==null?new String(ch, start, length):temp+new String(ch, start, length));
		} else if (bId) {
			ext.setId(Integer.parseInt(new String(ch, start, length)));
		}  else if (bStart) {
			ext.setStart(Integer.parseInt(new String(ch, start, length)));
		} else if (bLength) {
			ext.setLength(Integer.parseInt(new String(ch, start, length)));
		} else if (bPerblock) {
			ext.setPerblock(Integer.parseInt(new String(ch, start, length)));
		} else if (bValueoffset) {
			ext.setValueoffset(Integer.parseInt(new String(ch, start, length)));
		} else if (bBlocksize) {
			ext.setBlocksize(Integer.parseInt(new String(ch, start, length)));
		}		
	}
}
