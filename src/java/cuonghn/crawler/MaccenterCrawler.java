/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cuonghn.crawler;

import cuonghn.utils.ParserDom;
import cuonghn.utils.XMLUtilities;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author nhatc
 */
public class MaccenterCrawler {
//
//    private final String host = "http://www.maccenter.vn/";
//
//    public void getProductPerPage(String filePath, String DestinationFile) throws FileNotFoundException, XMLStreamException, SAXException, ParserConfigurationException, IOException, XPathExpressionException {
//        Document doc = ParserDom.parserXMLFromFile(filePath);
//        List<jaxb_Macbook> listProduct = new ArrayList<>();
//        String id = "";
//        String className = "";
//
//        XPath xpath = XMLUtilities.createXPath();
//        String exp = "//table[@class='Table-Thin']/tr/td";
//        NodeList nodelist = (NodeList) xpath.evaluate(exp, doc, XPathConstants.NODESET);
//        System.out.println(nodelist.getLength());
//        for (int i = 0; i < nodelist.getLength(); i++) {
//            //declare object variable
//            String name = "";
//            String cpu = "";
//            String gpu = "";
//            String ram = "";
//            String hardDrive = "";
//            String price = "";
//            String battery = "";
//            //end declare object variable
//            Node child = nodelist.item(i);
//            price = child.getTextContent().trim();
////            System.out.println("price:" + price);
//            NodeList infoOfProduct = child.getChildNodes();
//            for (int j = 0; j < infoOfProduct.getLength(); j++) {
//                Node field = infoOfProduct.item(j);
//                NamedNodeMap attr = field.getAttributes();
//                if (attr != null && attr.getNamedItem("class") != null) {
//                    String tagAclassName = attr.getNamedItem("class").getNodeValue();
//                    if (field.getNodeName().equals("a")
//                            && tagAclassName.equalsIgnoreCase("TitleProduct")) {
//                        if (field.getTextContent() != null) {
////                            System.out.println(field.getTextContent().trim());
//                            name = field.getTextContent().trim();
//                        }//end if check textContext, text context mà không có trị thì bên trong là thẻ image
//
//                    } //end if thẻ a có class titleProduct là thẻ chứa tên của máy, tuy nhiên cũng có thể chứa image nên cần check thêm cái thằng textcontext có null không
//                    else if (field.getNodeName().equals("ul")) {
//                        NodeList listLi = field.getChildNodes();
//
//                        cpu = listLi.item(0).getTextContent().trim();
//                        ram = listLi.item(1).getTextContent().trim();
//                        hardDrive = listLi.item(2).getTextContent().trim();
//                        gpu = listLi.item(3).getTextContent().trim();
//                        battery = listLi.item(4).getTextContent().trim();
//
//                    }
//                } // end if (đảm bảo các giá trị attribute không null khi đọc ra, tránh crash bộ cào)
//
//            } // đọc hết các thẻ nằm trong thẻ td ra , mỗi thẻ tương ứng với 1 field của object
//            listProduct.add(new jaxb_Macbook(name, cpu, gpu, ram, hardDrive, price, battery));
//        } //end for (đọc hết thẻ td mỗi thẻ td tương ứng với 1 product)
//        System.out.println("list product of page " + filePath + " " + listProduct.size());
//    }
//
//    public List<String> getListCategory(String htmlFile, String DestinationFile) throws FileNotFoundException, XMLStreamException {
//        XMLInputFactory factory = XMLInputFactory.newInstance();
//        factory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, true);
//        factory.setProperty(XMLInputFactory.IS_VALIDATING, false);
//        factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
//        factory.setProperty(XMLInputFactory.IS_COALESCING, true);
//        InputStream is = null;
//        List<String> categories = null;
//        XMLStreamReader reader = null;
//        is = new FileInputStream(htmlFile);
//        reader = factory.createXMLStreamReader(is);
//        int countul = 0;
//        String tagContent = null;
//        String href = null;
//        boolean trueTag = false;
//        boolean endTask = false;
//        while (reader.hasNext() && !endTask) {
//            int cursor = reader.next();
//            switch (cursor) {
//                case XMLStreamConstants.START_ELEMENT:
//                    if ("ul".equals(reader.getLocalName())) {
//                        if (countul > 0) {
//                            countul++;
//                        } else {
//                            String id = reader.getAttributeValue("", "id");
//                            if (id.equals("menu")) {
//                                countul++;
//                            }
//                        }
//
//                    } else if (reader.getLocalName().equals("a")) {
//                        if (countul == 2) {
//                            href = reader.getAttributeValue("", "href");
//                        }
//                    }
//                    break;
//                case XMLStreamConstants.CHARACTERS:
//                    tagContent = reader.getText().trim();
//                    if (tagContent.contains("MacBook")) {
//                        trueTag = true;
//                    }
//                    break;
//                case XMLStreamConstants.END_ELEMENT:
//                    if (reader.getLocalName().equals("a") && trueTag) {
//                        if (categories == null) {
//                            categories = new ArrayList<>();
//                        }
//                        categories.add(href);
//                        trueTag = false;
//                    }/*end if (nếu thẻ đóng a thỏa cả 2 đk là nằng trong 2 thẻ ul[id="menu"]/ul 
//                    và content có chữ Macbook) thì mới lấy href
//                     */
//                    if (reader.getLocalName().equals("ul") && (countul > 0)) {
//                        endTask = true;
//                        break;
//                    } // end if (nếu tài liệu đóng thẻ ul có id == menu thì dừng sớm bộ parse mà không đọc tiếp)
//                    break;
//            } // end switch (kiểm tra để vào ul lần đầu tiên)
//        } // end while (đọc hết tài liệu ra)
//        System.out.println(categories.size());
//        for (int i = 0; i < categories.size(); i++) {
//            System.out.println(categories.get(i).toString());
//
//        }
//        return categories;
//    }
//
//    public static String getNodeStaxCursorValue(XMLStreamReader reader, String elementName, String namespaceURI, String attrName) throws XMLStreamException {
//        if (reader != null) {
//            while (reader.hasNext()) {
//                int cursor = reader.getEventType();
//                if (cursor == XMLStreamConstants.START_ELEMENT) {
//                    String tagName = reader.getLocalName();
//                    if (tagName.equals(elementName)) {
//                        String result = reader.getAttributeValue(namespaceURI, attrName);
//                        return result;
//                    }//end if element name
//                }//end if start element
//                reader.next();
//            }//end if while
//        }//end if reader
//        return null;
//    }
}
