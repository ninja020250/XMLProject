/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cuonghn.crawler;


import cuonghn.parseData.Internet;
import cuonghn.utils.ParserDom;
import cuonghn.utils.XMLUtilities;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
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
public class PhuCanhCrawler {
//
//    private final String host = "https://www.phucanh.vn";
//    private String uriMonitorFromCategory = null;
//    private List<String> uriCategories = null;
//    private List<String> uriProducts = null;
//    private jaxb_Categories jaxb_Categories = new jaxb_Categories();
//    private int count = 0;
//    private String realPath = "";
//
//    public void run() {
////        realPath = realFilePath;
//        try {
//            System.out.println("Crawling.....");
//            getLinkMonitorFromCategory();
//            if (uriMonitorFromCategory != null && !uriMonitorFromCategory.equals("")) {
//                getListCategoryUri(uriMonitorFromCategory);
//            }
//            if (uriCategories != null) {
//
//                for (int i = 0; i < uriCategories.size(); i++) {
//
//                    System.out.println("dang cao: " + uriCategories.get(i));
////
//                    tbl_Category cate = new tbl_Category();
//                    List<String> uris = getListProductUri(uriCategories.get(i));
//                    System.out.println("so luong product: " + uris.size());
//                    for (int j = 0; j < uris.size(); j++) {
//                        count++;
//                        try {
//                            tbl_Product product = getProductByUri(uris.get(j));
//                            cate.addProduct(product);
//                            System.out.println("Xong " + count + " product");
//                        } catch (Exception e) {
//                        }
//                    }
//
//                    //jaxb_Categories.addNewCategory(cate);
//                } // end for
//            } // end if uri categories
////            System.out.println(jaxb_Categories.toString());
////            System.out.println("Done");
//
//        } catch (Exception e) {
//            System.out.println("Error");
//            e.printStackTrace();
//        }
//    }
//
//    public tbl_Product getProductByUri(String ProductUri) throws SAXException, ParserConfigurationException, IOException, XPathExpressionException, ParserConfigurationException, ParserConfigurationException, ParserConfigurationException {
////     String url = Internet.parseStringToUTF8Uri(ProductUri);
//        tbl_Product product = null;
//        String wellformXML = Internet.parseListHTMLToString(ProductUri);
//        if (wellformXML != null) {
//            Document doc = ParserDom.getDocumentFromStringXML(wellformXML);
//            XPath xpath = XMLUtilities.createXPath();
//            // get name
//            String exp = "//*[@id='ProductDescription']/div/table//tr";
//            NodeList nodelist = (NodeList) xpath.evaluate(exp, doc, XPathConstants.NODESET);
////            System.out.println(nodelist.getLength());
//            for (int i = 0; i < nodelist.getLength(); i++) {
//                NodeList nodeTRChild = nodelist.item(i).getChildNodes();
//                if (nodeTRChild.item(1) != null) {
//                    String model = nodeTRChild.item(1).getTextContent();
//                    if (product == null) {
//                        product = new tbl_Product(model);
//                    }
//                }
//            }
//            //end get name
//            //get price 
//            exp = "//*[@id=\"ProductDetails\"]//span[@class=\"SalePrice\"]/text()";
//            String price = (String) xpath.evaluate(exp, doc, XPathConstants.STRING);
//            if (product != null) {
//                product.setPrice(price);
//            }
//        }
//        return product;
//    }
//
//    public void getLinkMonitorFromCategory() throws SAXException, ParserConfigurationException, IOException, XPathExpressionException {
//        String wellformerHomePage = Internet.parseListHTMLToString("https://www.phucanh.vn");
//        Document doc = ParserDom.getDocumentFromStringXML(wellformerHomePage);
//        XPath xpath = XMLUtilities.createXPath();
//        String exp = "//div[@id=\"main-menu\"]//div[@class='sub-menu']/div[@class='box-cat']/a[@class='cat1'  and contains(@href, 'man-hinh-may-tinh')]";
//        Node node = (Node) xpath.evaluate(exp, doc, XPathConstants.NODE);
//
//        NamedNodeMap attr = node.getAttributes();
//        uriMonitorFromCategory = host + attr.getNamedItem("href").getNodeValue();
//        System.out.println(uriMonitorFromCategory);
//    }
//
//    public void getListCategoryUri(String uri) throws SAXException, ParserConfigurationException, IOException, XPathExpressionException {
//
////        String url = Internet.parseStringToUTF8Uri(uri);
//        String wellformXML = Internet.parseListHTMLToString(uri);
//        if (wellformXML != null) {
//            Document doc = ParserDom.getDocumentFromStringXML(wellformXML);
//            XPath xpath = XMLUtilities.createXPath();
//            String exp = "//div[@class=\"filter-pro\"]/div[@class='item-filter category-filter']//a";
//            NodeList nodelist = (NodeList) xpath.evaluate(exp, doc, XPathConstants.NODESET);
//            System.out.println("node list length:  " + nodelist.getLength());
//            for (int i = 0; i < nodelist.getLength(); i++) {
//                NamedNodeMap attr = nodelist.item(i).getAttributes();
//                String uriCategory = attr.getNamedItem("href").getNodeValue();
//                if (uriCategories == null) {
//                    uriCategories = new ArrayList<>();
//                }
//                uriCategories.add(host + uriCategory);
//                System.out.println(host + uriCategory);
//            }
//        }
//
//    }
//
//    public List<String> getListProductUri(String uriCategory) throws SAXException, ParserConfigurationException, IOException, XPathExpressionException {
//        List<String> results = new ArrayList<>();
////        String url = Internet.parseStringToUTF8Uri(uriCategory);
//
//        int pagi = 0;
//        uriCategory = uriCategory + "?page=";
//        while (true) {
//            pagi++;
//            String uriPageCategory = uriCategory + pagi;
//            String wellformXML = Internet.parseListHTMLToString(uriPageCategory);
////            System.out.println("dang cao trang con: " + uriPageCategory);
//            if (wellformXML != null) {
//                Document doc = ParserDom.getDocumentFromStringXML(wellformXML);
//                XPath xpath = XMLUtilities.createXPath();
//                String exp = "//div[@id=\"content-full\"]//ul[@class='ul product-list']/li//a[@class='p-img']";
//                NodeList nodelist = (NodeList) xpath.evaluate(exp, doc, XPathConstants.NODESET);
//                if (nodelist.getLength() <= 0) {
//                    break;
//                }
//                for (int i = 0; i < nodelist.getLength(); i++) {
//                    NamedNodeMap attr = nodelist.item(i).getAttributes();
//                    String uriProduct = attr.getNamedItem("href").getNodeValue();
//                    if (uriProducts == null) {
//                        uriProducts = new ArrayList<>();
//                    }
//                    results.add(host +uriProduct);
//                    uriProducts.add(host + uriProduct);
//
//                }
//            }
//        }
//
//        return results;
//    }
}
