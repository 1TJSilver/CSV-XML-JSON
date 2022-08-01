import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Type;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // #1 задание
        /*
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String filename = "data.csv";
        List<Employee> list = parseCSV(columnMapping, filename);
        String json = listToJson(list);
        writeStringFile(json, "data.json");
        */
        // #2 задание
        /*
        String json1 = listToJson(parseXML("data1.xml"));
        writeStringFile(json1, "data1.json");
         */
        // #3 задание
        //printList(jsonToList(readString("data.json")));

    }
    public static List<Employee> parseCSV(String[] columnMapping, String filename){
        List<Employee> list = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filename))) {
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);
            CsvToBean<Employee> beanBuilder = new CsvToBeanBuilder<Employee>(reader)
                    .withMappingStrategy(strategy)
                    .build();
            list = beanBuilder.parse();
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        return list;
    }
    public static String listToJson (List<Employee> list) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.toJson(list);
    }
    public static void writeStringFile (String txt, String name){
        try (FileWriter fileWriter = new FileWriter(name)) {
            fileWriter.write(txt);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public static List<Employee> parseXML (String name){
        List<Employee> list = new ArrayList<>();
        try {
            Scanner scanner;

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(name));

            NodeList nodeList = doc.getElementsByTagName("employee");
            for (int i = 0; i < nodeList.getLength(); i++){
                Node node = nodeList.item(i);
                if (Node.ELEMENT_NODE == node.getNodeType()){
                    Element innerElement = (Element) node;
                    Employee employee = new Employee();
                    String textInfo = innerElement.getTextContent().substring(1);
                    scanner = new Scanner(textInfo);
                    employee.id = Integer.parseInt(scanner.nextLine().trim());
                    employee.firstName = scanner.nextLine();
                    employee.lastName = scanner.nextLine();
                    employee.country = scanner.nextLine();
                    employee.age = Integer.parseInt(scanner.nextLine().trim());
                    list.add(employee);
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }
    public static String readString (String filename){
        String text = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))){
            text = reader.readLine();
        } catch (IOException  ex){
            System.out.println(ex.getMessage());
        }
        return text;
    }
    public static List<Employee> jsonToList (String json){
        GsonBuilder  builder = new GsonBuilder();
        Gson gson = builder.create();

        Type userListType = new TypeToken<ArrayList<Employee>>(){}.getType();

        List<Employee> userArray = gson.fromJson(json, userListType);
        return userArray;
    }
    public static void printList (List<Employee> list){
        for (Employee employee : list) {
            System.out.println(employee.toString());
        }
    }
}
