/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cuonghn.crawler;


import cuonghn.jaxb.Brand;
import cuonghn.jaxb.ListBrand;
import cuonghn.jaxb.Monitor;
import cuonghn.jaxb.Store;
import cuonghn.parseData.Internet;
import cuonghn.utils.ParserDom;
import cuonghn.utils.XMLUtilities;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author nhatc
 */
public class CPNCrawler {

    private final String host = "https://cpn.vn/";
    private String uriMonitorFromCategory = null;
    private Map<String, String> uriCategories = null;
    private List<String> uriProducts = null;
    private Store store = null;
//    private jaxb_Categories jaxb_Categories = new jaxb_Categories();
    private int count = 0;
    private String realPath;

    public CPNCrawler(String realPath) {
        this.realPath = realPath;
    }

    public CPNCrawler() {
    }

    public void run() {

        try {
            System.out.println("Crawling.....");
            getLinkMonitorFromCategory();
            if (uriMonitorFromCategory != null && !uriMonitorFromCategory.equals("")) {
                if (store == null) {
                    store = new Store(uriMonitorFromCategory);
                }
                getListCategoryUri(uriMonitorFromCategory);
            }
            if (uriCategories != null) {
                ListBrand listBrand = new ListBrand();
                for (Map.Entry<String, String> entry : uriCategories.entrySet()) {
                    Brand brand = new Brand(entry.getKey());
                    System.out.println("dang cao: " + entry.getValue());
//                    tbl_Category cate = new tbl_Category();
                    List<String> uris = getListProductUri(entry.getValue());
                    System.out.println("so luong product: " + uris.size());
                    for (int j = 0; j < uris.size(); j++) {
                        count++;
                        try {
                            Monitor monitor = getProductByUri(uris.get(j));
                            brand.addMonitor(monitor);
                        } catch (Exception e) {
                        }
                    }
                    //jaxb_Categories.addNewCategory(cate);
                    listBrand.Add(brand);
                } // end for
                store.setListBrand(listBrand);
            } // end if uri categories
//            System.out.println(jaxb_Categories.toString());

            XMLUtilities.saveToXML(this.realPath + "WEB-INF/HTMLPAGE/testPage.xml", store);
            System.out.println("Done");
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    public Monitor getProductByUri(String ProductUri) throws SAXException, ParserConfigurationException, IOException, XPathExpressionException, ParserConfigurationException, ParserConfigurationException, ParserConfigurationException {
//     String url = Internet.parseStringToUTF8Uri(ProductUri);
        Monitor monitor = null;
        String wellformXML = Internet.parseListHTMLToString(ProductUri);
        if (wellformXML != null) {
            Document doc = ParserDom.getDocumentFromStringXML(wellformXML);
            XPath xpath = XMLUtilities.createXPath();
            // get name
            String exp = "//*[@id='ProductDescription']/div/table//tr";
            NodeList nodelist = (NodeList) xpath.evaluate(exp, doc, XPathConstants.NODESET);
//            System.out.println(nodelist.getLength());
            for (int i = 0; i < nodelist.getLength(); i++) {
                NodeList nodeTRChild = nodelist.item(i).getChildNodes();
                if (nodeTRChild.item(1) != null) {
                    String model = nodeTRChild.item(1).getTextContent();
                    if (monitor == null) {
                        monitor = new Monitor(model);
                        monitor.setUrl(ProductUri);
                    }
                }

            }
            //end get name
            //get price 
            exp = "//*[@id=\"ProductDetails\"]//span[@class=\"SalePrice\"]/text()";
            String price = (String) xpath.evaluate(exp, doc, XPathConstants.STRING);
            price = price.replaceAll("\\D+", ""); // cắt hết chỉ lấy số thôi
            if (monitor != null) {
                monitor.setPrice(price);
            }
        }
        return monitor;
    }

    public void getLinkMonitorFromCategory() throws SAXException, ParserConfigurationException, IOException, XPathExpressionException {
        String wellformerHomePage = Internet.parseListHTMLToString("https://cpn.vn/");
        Document doc = ParserDom.getDocumentFromStringXML(wellformerHomePage);
        XPath xpath = XMLUtilities.createXPath();
        String exp = "//div[@id=\"Menu\"]/ul/li/ul/li/a";
        NodeList nodelist = (NodeList) xpath.evaluate(exp, doc, XPathConstants.NODESET);
        for (int i = 0; i < nodelist.getLength(); i++) {
            if (nodelist.item(i).getTextContent().contains("Màn hình máy tính")) {
                NamedNodeMap attr = nodelist.item(i).getAttributes();
                uriMonitorFromCategory = host + attr.getNamedItem("href").getNodeValue();
//                System.out.println("uri cua montitor category: " + uriMonitorFromCategory);
            }

        }
    }

    public void getListCategoryUri(String uri) throws SAXException, ParserConfigurationException, IOException, XPathExpressionException {

        String url = Internet.parseStringToUTF8Uri(uri);
        String wellformXML = Internet.parseListHTMLToString(url);
        if (wellformXML != null) {
            Document doc = ParserDom.getDocumentFromStringXML(wellformXML);
            XPath xpath = XMLUtilities.createXPath();
            String exp = "//div[@class='SubCategoryList']/ul/li/a";
            NodeList nodelist = (NodeList) xpath.evaluate(exp, doc, XPathConstants.NODESET);
            for (int i = 0; i < nodelist.getLength(); i++) {
                NamedNodeMap attr = nodelist.item(i).getAttributes();
                String uriCategory = attr.getNamedItem("href").getNodeValue();
                String brandName = nodelist.item(i).getTextContent();

                if (brandName != null && brandName != "") {
                    if (uriCategories == null) {
                        uriCategories = new HashMap<>();
                    }
                    uriCategories.put(brandName, uriCategory);
                }

//                System.out.println(uriCategory);
            }
        }

    }

    public List<String> getListProductUri(String uriCategory) throws SAXException, ParserConfigurationException, IOException, XPathExpressionException {
        List<String> results = new ArrayList<>();
        String url = Internet.parseStringToUTF8Uri(uriCategory);
        String wellformXML = Internet.parseListHTMLToString(url);
        if (wellformXML != null) {
            try {
                Document doc = ParserDom.getDocumentFromStringXML(wellformXML);
                XPath xpath = XMLUtilities.createXPath();
                String exp = "//ul[@class='ProductList ']/li/div[@class='ProductDetails']/strong/a";
                NodeList nodelist = (NodeList) xpath.evaluate(exp, doc, XPathConstants.NODESET);
//            System.out.println(nodelist.getLength());
                for (int i = 0; i < nodelist.getLength(); i++) {
                    NamedNodeMap attr = nodelist.item(i).getAttributes();
                    String uriProduct = attr.getNamedItem("href").getNodeValue();
                    if (uriProducts == null) {
                        uriProducts = new ArrayList<>();
                    }
                    results.add(uriProduct);
                    uriProducts.add(uriProduct);
//                System.out.println(uriProduct);

                }
            } catch (Exception e) {
                System.out.println("Page nay loi");
            }
        }
        return results;
    }
}
