package it.ubix.model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import it.ubix.support.XmlFormat;

public class Serie extends Product {

	
	private HashMap<Integer,Vector<XmlFormat>> map;
	private String relativeSource;
	public Serie() {
		super();
		map=new HashMap<>();
		
	}
	
	public String getRelativeSource() {
		return relativeSource;
	}
	@Override
	public <T> T getSrc() {
		return (T) map;
	}

	@Override
	public <T> void setSrc(String path,String src) {
		try {
			this.relativeSource=src;
			readXml(path,src);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			System.out.println(e);
		}
		
		
		
		
	}
	
	
	private void readXml(String path ,String src) throws ParserConfigurationException, SAXException, IOException {
		File fXmlFile = new File(path + "resource/serie/" + src.toString() + "/serie.xml" );
		System.out.println(fXmlFile.getAbsolutePath());
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
	    dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = dBuilder.parse(fXmlFile);
		NodeList serie=doc.getElementsByTagName("season");
		int id=-1;
		for(int i=0;i<serie.getLength();++i) {
			Node season = serie.item(i);
		    NodeList epL=season.getChildNodes();
		    ++id;
		    Vector<XmlFormat> temp=new Vector<>();
			for(int j=0;j<epL.getLength();++j) {
			  Node ep =epL.item(j);
			  if (ep.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element)ep;
					temp.add(new XmlFormat(element.getTextContent(), element.getAttribute("value")));
				}
			}
			
			map.put(id, temp);
					
		}
		
	}

	
}
