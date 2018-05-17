package de.ddkfm.micro.util.transform;

import de.ddkfm.micro.util.MicroUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The Transformer transforms a given Data
 * as MicroData OR
 * as XML OR
 * as JSON (not implemented yet)
 * and also load this data by the named formats
 * */
public class Transformer {
	private static Logger logger = LogManager.getLogger("Transformer");
	private Micro2Data data;

	/**
	 * Constructor of the Transformer with a inital Micro2Data
	 * @param data The given Micro2Data
	 * */
	public Transformer(Micro2Data data) {
		super();
		this.data = data;
	}
	/**
	 * Constructor of the Transformer with a inital XML-Data
	 * @param data The already loaded XML-Document
	 * */
	public Transformer(Document data){
		this.data = new Micro2Data("");
		setXMLData(data);
	}
	/**
	 * load the Transformer with the Micro2Data
	 * @param data the given Micro2Data
	 * */
	public void setData(Micro2Data data) {
		this.data = data;
	}

	/**
	 * returns the Micro2Data
	 * @return Micro2Data
	 * */
	public Micro2Data getData() {
		return data;
	}
	/**
	 * Set the Microcode(as a part of the Micro2Data) by a given <Microcode>-XML Element
	 * @param xmlDoc already loaded XML-Document
	 * */
	public void setMicrocodeData(Document xmlDoc) {
		Element rootElement = xmlDoc.getDocumentElement();
		NodeList microcodeList= rootElement.getChildNodes();
		for (int i = 0 ; i < microcodeList.getLength() ; i++){
			Node currentNode = microcodeList.item(i);
			if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
				String mnemonic = currentNode.getAttributes().getNamedItem("mnemonic").getNodeValue();
				String addressing = currentNode.getAttributes().getNamedItem("addressing").getNodeValue();
				String microcode = currentNode.getAttributes().getNamedItem("microcode").getNodeValue();
				String comment = currentNode.getAttributes().getNamedItem("comment").getNodeValue();
				Instruction instruction = new Instruction(i, mnemonic, addressing, microcode, comment);
				this.data.getMicrocode().setInstruction(Integer.parseInt(currentNode.getAttributes().getNamedItem("index").getNodeValue()), instruction);
			}
		}
	}
	/**
	 * load the Transformer with the XMl-Document which represents a Program AND a Microcode
	 * @param xmlDoc XML-Document
	 * */
	public void setXMLData(Document xmlDoc) {
		Element rootElement = xmlDoc.getDocumentElement();
		NodeList author = rootElement.getElementsByTagName("Author");
		if(author.item(0) != null)
			data.setAuthor(author.item(0).getTextContent());
		NodeList dateCreated = rootElement.getElementsByTagName("DateCreated");
		if(dateCreated.item(0) != null){
			Date date;
			try {
				date = new SimpleDateFormat("dd.MM.yyyy hh:MM:ss").parse(dateCreated.item(0).getTextContent());
			} catch (Exception e){
				date = new Date();
			}
			data.setDateCreated(date);
		}
		NodeList description = rootElement.getElementsByTagName("Description");
		if(description.item(0) != null){
			data.setDescription(description.item(0).getTextContent());
		}
		NodeList programNode = xmlDoc.getElementsByTagName("Program");
		NodeList programNodelist = programNode.item(0).getChildNodes();
		for (int i = 0 ; i < programNodelist.getLength() ; i++){
			Node currentNode = programNodelist.item(i);
			if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
				String instruction = currentNode.getAttributes().getNamedItem("statement").getNodeValue();
				String address = currentNode.getAttributes().getNamedItem("address").getNodeValue();
				String data = currentNode.getAttributes().getNamedItem("data").getNodeValue();
				String comment = currentNode.getAttributes().getNamedItem("comment").getNodeValue();
				ProgramLine pl = new ProgramLine(instruction, address, data, comment);
				this.data.getProgram().setProgramLine(Integer.parseInt(currentNode.getAttributes().getNamedItem("index").getNodeValue()), pl);
			}
		}
		NodeList microcodeNode = xmlDoc.getElementsByTagName("Microcode");
		NodeList microcodeList= microcodeNode.item(0).getChildNodes();
		for (int i = 0 ; i < microcodeList.getLength() ; i++){
			Node currentNode = microcodeList.item(i);
			if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
				String mnemonic = currentNode.getAttributes().getNamedItem("mnemonic").getNodeValue();
				String addressing = currentNode.getAttributes().getNamedItem("addressing").getNodeValue();
				String microcode = currentNode.getAttributes().getNamedItem("microcode").getNodeValue();
				String comment = currentNode.getAttributes().getNamedItem("comment").getNodeValue();
				Instruction instruction = new Instruction(i, mnemonic, addressing, microcode, comment);
				this.data.getMicrocode().setInstruction(Integer.parseInt(currentNode.getAttributes().getNamedItem("index").getNodeValue()), instruction);
			}
		}
	}
	/**
	 * returns the Microcode as a XMl-Document
	 * @return XMl-Document with <Microcode> as Root-Element
	 * */
	public Document getMicrocodeData() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document xmlDoc = builder.newDocument();

			//Root-Element
			//Microcode
			Element microcodeNode = xmlDoc.createElement("Microcode");

			List<Element> microcodeNodeList = new ArrayList<Element>();
			for (int i = 0 ; i < 16 ; i++){
				Instruction ins = data.getMicrocode().getInstruction(i);

				Element currentNode = xmlDoc.createElement("Instruction");

				Node indexNode = xmlDoc.createAttribute("index");
				indexNode.setNodeValue(Integer.toString(i));

				Node statementNode = xmlDoc.createAttribute("mnemonic");
				statementNode.setNodeValue(ins.getMnemonic());

				Node addressNode = xmlDoc.createAttribute("addressing");
				addressNode.setNodeValue(ins.getAddressing());

				Node dataNode = xmlDoc.createAttribute("microcode");
				dataNode.setNodeValue(MicroUtils.getStringByBinaryNotation(ins.getMicrocode()));

				Node commentNode = xmlDoc.createAttribute("comment");
				commentNode.setNodeValue(ins.getComment());

				currentNode.getAttributes().setNamedItem(indexNode);
				currentNode.getAttributes().setNamedItem(statementNode);
				currentNode.getAttributes().setNamedItem(addressNode);
				currentNode.getAttributes().setNamedItem(dataNode);
				currentNode.getAttributes().setNamedItem(commentNode);

				microcodeNodeList.add(currentNode);
				microcodeNode.appendChild(microcodeNodeList.get(i));
			}
			xmlDoc.appendChild(microcodeNode);
			return xmlDoc;
		} catch (ParserConfigurationException ex) {
			logger.error("Fehler beim Erstellen des XML-Dokumentes: ",ex);
		}

		return null;
	}
	/**
	 * returns the Program AND Microcode as a XMl-Document
	 * @return XMl-Document with <MicroIIData> as Root-Element
	 * */
	public Document getProgramData() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document xmlDoc = builder.newDocument();

			//Root-Element
			Element rootElement = xmlDoc.createElement("MicroIIData");
			xmlDoc.appendChild(rootElement);
			//Allgemeine Informationen
			Element author = xmlDoc.createElement("Author");
			author.setTextContent(data.getAuthor());
			rootElement.appendChild(author);

			Element dateCreated = xmlDoc.createElement("DateCreated");
			dateCreated.setTextContent(new SimpleDateFormat("dd.MM.yyyy hh:MM:ss").format(data.getDateCreated()));
			rootElement.appendChild(dateCreated);

			Element description = xmlDoc.createElement("Description");
			description.setTextContent(data.getDescription());
			rootElement.appendChild(description);

			//Program
			Element programNode = xmlDoc.createElement("Program");
			List<Element> programNodeList = new ArrayList<Element>();
			for (int i = 0 ; i < 16 ; i++){
				ProgramLine pl = data.getProgram().getProgramLine(i);

				Element currentNode = xmlDoc.createElement("Programrow");

				Node indexNode = xmlDoc.createAttribute("index");
				indexNode.setNodeValue(Integer.toString(i));

				Node statementNode = xmlDoc.createAttribute("statement");
				statementNode.setNodeValue(MicroUtils.getStringByBinaryNotation(pl.getInstruction()));

				Node addressNode = xmlDoc.createAttribute("address");
				addressNode.setNodeValue(MicroUtils.getStringByBinaryNotation(pl.getAddress()));

				Node dataNode = xmlDoc.createAttribute("data");
				dataNode.setNodeValue(MicroUtils.getStringByBinaryNotation(pl.getData()));

				Node commentNode = xmlDoc.createAttribute("comment");
				commentNode.setNodeValue(pl.getComment());

				currentNode.getAttributes().setNamedItem(indexNode);
				currentNode.getAttributes().setNamedItem(statementNode);
				currentNode.getAttributes().setNamedItem(addressNode);
				currentNode.getAttributes().setNamedItem(dataNode);
				currentNode.getAttributes().setNamedItem(commentNode);

				programNodeList.add(currentNode);
				programNode.appendChild(programNodeList.get(i));
			}

			rootElement.appendChild(programNode);

			//Microcode
			Element microcodeNode = xmlDoc.createElement("Microcode");
			List<Element> microcodeNodeList = new ArrayList<Element>();
			for (int i = 0 ; i < 16 ; i++){
				Instruction ins = data.getMicrocode().getInstruction(i);

				Element currentNode = xmlDoc.createElement("Instruction");

				Node indexNode = xmlDoc.createAttribute("index");
				indexNode.setNodeValue(Integer.toString(i));

				Node statementNode = xmlDoc.createAttribute("mnemonic");
				statementNode.setNodeValue(ins.getMnemonic());

				Node addressNode = xmlDoc.createAttribute("addressing");
				addressNode.setNodeValue(ins.getAddressing());

				Node dataNode = xmlDoc.createAttribute("microcode");
				dataNode.setNodeValue(MicroUtils.getStringByBinaryNotation(ins.getMicrocode()));

				Node commentNode = xmlDoc.createAttribute("comment");
				commentNode.setNodeValue(ins.getComment());

				currentNode.getAttributes().setNamedItem(indexNode);
				currentNode.getAttributes().setNamedItem(statementNode);
				currentNode.getAttributes().setNamedItem(addressNode);
				currentNode.getAttributes().setNamedItem(dataNode);
				currentNode.getAttributes().setNamedItem(commentNode);

				microcodeNodeList.add(currentNode);
				microcodeNode.appendChild(microcodeNodeList.get(i));
			}

			rootElement.appendChild(microcodeNode);
			return xmlDoc;
		} catch (ParserConfigurationException ex) {
			logger.error("Fehler beim Erstellen des XML-Dokumentes: ",ex);
		}
		return null;

	}

}
