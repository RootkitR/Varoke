package ch.rootkit.varoke.tools.furni;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class FurniDataParser {
	Document dom;
	private HashMap<String, FurniData> items;
	private String FurniData;
	public FurniDataParser(String furniData){
		FurniData = furniData;
		items = new HashMap<String, FurniData>();
	}
	public void parseXmlFile(){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.parse(FurniData);
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	public void parseDocument(){
		//FloorItems Part
		Element docEle = (Element)dom.getDocumentElement().getElementsByTagName("roomitemtypes").item(0);
		NodeList nl = docEle.getElementsByTagName("furnitype");
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {
				Element el = (Element)nl.item(i);
				FurniData furniData = getFurniData(el);
				if(!this.items.containsKey(furniData.getFurniName()))
					this.items.put(furniData.getFurniName(), furniData);
			}
		}
		// WallItems Part
		docEle = (Element)dom.getDocumentElement().getElementsByTagName("wallitemtypes").item(0);
		nl = docEle.getElementsByTagName("furnitype");
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {
				Element el = (Element)nl.item(i);
				FurniData furniData = getFurniData(el);
				if(!this.items.containsKey(furniData.getFurniName()))
					this.items.put(furniData.getFurniName(), furniData);
			}
		}
	}
	public FurniData getFurniData(Element furni) {
		return new FurniData(
				furni.getAttribute("classname"),
				getTextValue(furni, "revision"));
	}
	public String getTextValue(Element ele, String tagName) {
		return ele.getElementsByTagName(tagName).item(0).getTextContent();
	}
	public String getAttribute(Element ele, String attr){
		return ele.getAttribute(attr);
	}
	public int getIntAttribute(Element ele, String attr){
		return Integer.parseInt(ele.getAttribute(attr));
	}
	public int getIntValue(Element ele, String tagName) {
		return Integer.parseInt(getTextValue(ele,tagName));
	}
	public List<FurniData> getItems(){ return new ArrayList<FurniData>(items.values()); }
}
