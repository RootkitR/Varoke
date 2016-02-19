package ch.rootkit.varoke.habbohotel.items.parsers;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ch.rootkit.varoke.Varoke;

public class FurniDataParser {
	Document dom;
	private HashMap<String, FurniData> floorItems;
	private HashMap<String, FurniData> wallItems;
	public FurniDataParser(){
		floorItems = new HashMap<String, FurniData>();
		wallItems = new HashMap<String, FurniData>();
	}
	public void parseXmlFile(){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.parse("furnidata.xml");
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
				FurniData furniData = getFloorFurniData(el);
				this.floorItems.put(el.getAttribute("classname"), furniData);
			}
		}
		// WallItems Part
		docEle = (Element)dom.getDocumentElement().getElementsByTagName("wallitemtypes").item(0);
		nl = docEle.getElementsByTagName("furnitype");
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {
				Element el = (Element)nl.item(i);
				FurniData furniData = getWallFurniData(el);
				this.wallItems.put(el.getAttribute("classname"), furniData);
			}
		}
	}
	public FurniData getFloorFurniData(Element furni) {
		return new FurniData(getIntAttribute(furni, "id"),
				getTextValue(furni, "name"),
				getIntValue(furni,"xdim"),
				getIntValue(furni,"ydim"),
				Varoke.stringToBool(getTextValue(furni, "cansiton")),
				Varoke.stringToBool(getTextValue(furni, "canstandon"))
				);
	}
	public FurniData getWallFurniData(Element furni){
		return new FurniData(getIntAttribute(furni, "id"),
				getTextValue(furni, "name"),0,0,false,false);
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
	public FurniData getFurni(String itemName){
		if(this.floorItems.containsKey(itemName))
			return this.floorItems.get(itemName);
		if(this.wallItems.containsKey(itemName))
			return this.wallItems.get(itemName);
		return null;
	}
}
