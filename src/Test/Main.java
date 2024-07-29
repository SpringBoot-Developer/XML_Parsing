package Test;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;


public class Main {

    public static void main(String[] args) {
        try {
            // Load and parse the XML file
            File inputFile = new File("src/students.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

            // Prevent XXE attacks (Disable access to external entities in XML parsing.)
            dbFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            dbFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            dbFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            dbFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
            dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);


            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // Get a list of all student elements
            NodeList nList = doc.getElementsByTagName("student");

            // Loop through each student element
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    // Extract and print student details
                    String id = eElement.getElementsByTagName("id").item(0).getTextContent();
                    String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                    String age = eElement.getElementsByTagName("age").item(0).getTextContent();
                    String gender = eElement.getElementsByTagName("gender").item(0).getTextContent();
                    String major = eElement.getElementsByTagName("major").item(0).getTextContent();

                    System.out.println("Student ID: " + id);
                    System.out.println("Name: " + name);
                    System.out.println("Age: " + age);
                    System.out.println("Gender: " + gender);
                    System.out.println("Major: " + major);
                    System.out.println();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
