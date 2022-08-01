package kvbdev.xml2json;

import kvbdev.common.Employee;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static kvbdev.common.JsonUtils.listToJson;
import static kvbdev.common.JsonUtils.writeString;


public class Main {

    public static void main(String[] args) throws Exception {
        String outFileName = "data2.json";

        List<Employee> list = parseXML("data.xml");

        String json = listToJson(list);

        writeString(json, outFileName);

        System.out.println(outFileName + " created");
    }

    public static List<Employee> parseXML(String fileName)
            throws IOException, ParserConfigurationException, SAXException {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document doc = documentBuilder.parse(new File(fileName));

        List<Employee> resultList = new ArrayList<>();

        Element root = doc.getDocumentElement();
        NodeList nodeList = root.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (Node.ELEMENT_NODE == node.getNodeType()) {
                if ("employee".equals(node.getNodeName())) {
                    Employee employee = parseEmployeeElement((Element) node);
                    resultList.add(employee);
                }
            }
        }

        return resultList;
    }

    private static Employee parseEmployeeElement(Element el) {
        long id = Long.parseLong(el.getElementsByTagName("id").item(0).getTextContent());
        String firstName = el.getElementsByTagName("firstName").item(0).getTextContent();
        String lastName = el.getElementsByTagName("lastName").item(0).getTextContent();
        String country = el.getElementsByTagName("country").item(0).getTextContent();
        int age = Integer.parseInt(el.getElementsByTagName("age").item(0).getTextContent());

        return new Employee(id, firstName, lastName, country, age);
    }

}
