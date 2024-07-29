package Test;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class ReadComplexXMLFile {
    public static void main(String[] args) {
        try {
            // Load and parse the XML file
            File inputFile = new File("src/complex.xml");
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
            NodeList studentList = doc.getElementsByTagName("student");

            // Loop through each student element
            for (int i = 0; i < studentList.getLength(); i++) {
                Node studentNode = studentList.item(i);
                if (studentNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element studentElement = (Element) studentNode;

                    // Extract and print student details
                    String id = studentElement.getAttribute("id");
                    String name = studentElement.getElementsByTagName("name").item(0).getTextContent();
                    String age = studentElement.getElementsByTagName("age").item(0).getTextContent();
                    String gender = studentElement.getElementsByTagName("gender").item(0).getTextContent();
                    String major = studentElement.getElementsByTagName("major").item(0).getTextContent();

                    System.out.println("Student ID: " + id);
                    System.out.println("Name: " + name);
                    System.out.println("Age: " + age);
                    System.out.println("Gender: " + gender);
                    System.out.println("Major: " + major);

                    // Extract and print course details
                    NodeList courseList = studentElement.getElementsByTagName("course");
                    for (int j = 0; j < courseList.getLength(); j++) {
                        Node courseNode = courseList.item(j);
                        if (courseNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element courseElement = (Element) courseNode;
                            String courseCode = courseElement.getAttribute("code");
                            String semester = courseElement.getAttribute("semester");
                            String courseName = courseElement.getElementsByTagName("name").item(0).getTextContent();
                            String grade = courseElement.getElementsByTagName("grade").item(0).getTextContent();

                            System.out.println("  Course Code: " + courseCode);
                            System.out.println("  Semester: " + semester);
                            System.out.println("  Course Name: " + courseName);
                            System.out.println("  Grade: " + grade);
                        }
                    }
                    System.out.println();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
