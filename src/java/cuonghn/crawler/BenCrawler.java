/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cuonghn.crawler;


import cuonghn.jaxb.Brand;
import cuonghn.jaxb.ListBrand;
import cuonghn.jaxb.ListMonitor;
import cuonghn.jaxb.Monitor;
import cuonghn.jaxb.Store;
import cuonghn.parseData.Internet;
import cuonghn.utils.ParserDom;
import cuonghn.utils.TextUtilities;
import cuonghn.utils.XMLUtilities;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author nhatc
 */
public class BenCrawler {

    private String host = "";
    private String uriMonitorFromCategory = null;
    private Map<String, String> uriCategories = null;
    private List<String> uriProducts = null;
    private Store store = null;
//    private jaxb_Categories jaxb_Categories = new jaxb_Categories();
    private int count = 0;
    private String realPath;

    private String xPathAccessMonitorCategory = "";
    private String xPathGetListCategoryUrl = "";
    private String xPathGetListCategoryName = "";
    private String xPathGetListProductUri = "";
    private String xPathCheckPagination = "";
    private String xPathGetProductInfo = "";
    private boolean configReady = false;

    public BenCrawler(String realPath) {
        this.realPath = realPath;
        readConfigFile();
    }

    public BenCrawler() {
        readConfigFile();
    }

    public void readConfigFile() {
        InputStream in = null;
        XMLStreamReader reader = null;
        String filePath = this.realPath + "WEB-INF/configFile/BenConfig.xml";
        try {
            in = new FileInputStream(filePath);
            reader = XMLUtilities.parserFiletoXMLcursor(in);
            String xpath = "";
            while (reader.hasNext()) {
                int currentCursor = reader.next();
                if (currentCursor == XMLStreamConstants.START_ELEMENT) {
                    String currentTagName = reader.getLocalName();
                    if (currentTagName.equals("website")) {
                        host = reader.getAttributeValue(null, "host");
                        System.out.println("host" + host);
                    } // end if user
                    else if (currentTagName.equals("XPathGetURLOfCategory")) {
                        xpath = reader.getAttributeValue(null, "xpath");
                        this.xPathGetListCategoryUrl = xpath;
                    } else if (currentTagName.equals("XPathAccessMonitorCategory")) {
                        xpath = reader.getAttributeValue(null, "xpath");
                        this.xPathAccessMonitorCategory = xpath;
                    } else if (currentTagName.equals("XPathGetListCategoryName")) {
                        xpath = reader.getAttributeValue(null, "xpath");
                        this.xPathGetListCategoryName = xpath;
                    } else if (currentTagName.equals("XPathGetListProductUri")) {
                        xpath = reader.getAttributeValue(null, "xpath");
                        this.xPathGetListProductUri = xpath;
                    } else if (currentTagName.equals("XPathCheckPagination")) {
                        xpath = reader.getAttributeValue(null, "xpath");
                        this.xPathCheckPagination = xpath;
                    } else if (currentTagName.equals("XPathGetProductInfo")) {
                        xpath = reader.getAttributeValue(null, "xpath");
                        this.xPathGetProductInfo = xpath;
                    } else {
                    }
                }// end if current cursor
            }
            if (xPathGetListCategoryUrl != ""
                    && xPathAccessMonitorCategory != ""
                    && xPathGetListCategoryName != ""
                    && xPathGetListProductUri != ""
                    && xPathCheckPagination != ""
                    && xPathGetProductInfo != "") {
                configReady = true;
                System.out.println("xPathAccessMonitorCategory: " + xPathAccessMonitorCategory);
                System.out.println("xPathGetListCategoryUrl: " + xPathGetListCategoryUrl);
                System.out.println("xPathGetListCategoryName: " + xPathGetListCategoryName);
                System.out.println("xPathGetListProductUri: " + xPathGetListProductUri);
                System.out.println("xPathCheckPagination: " + xPathCheckPagination);
                System.out.println("xPathGetProductInfo: " + xPathGetProductInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testValidate() {
        Store store = unmarshallingStore(realPath + "schema/test.xml");
        Store storeInvalid = validateStore(store);
        XMLUtilities.saveToXML(this.realPath + "WEB-INF/validate/valid.xml", store);
        XMLUtilities.saveToXML(this.realPath + "WEB-INF/validate/Invalid.xml", storeInvalid);
    }

    public void run() {
        if (configReady) {
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
                        System.out.println("dang cao: " + entry.getKey() + " " + entry.getValue());
//
                     
                        List<String> uris = getListProductUri(entry.getValue());
                        System.out.println("so luong product: " + uris.size());
                        for (int j = 0; j < uris.size(); j++) {
                            count++;
                            try {
                                Monitor monitor = getProductByUri(uris.get(j));
                                if (monitor != null) {
                                    brand.addMonitor(monitor);
                                }
                            } catch (Exception e) {
                            }
                        }
                        listBrand.Add(brand);
                        //jaxb_Categories.addNewCategory(cate);
                    } // end for
                    store.setListBrand(listBrand);
                } // end if uri categories
                System.out.println("Crawler is done his task 0~0");

                XMLUtilities.saveToXML(this.realPath + "WEB-INF/HTMLPAGE/productBen.xml", store);
//                validateStore(store);
//                XMLUtilities.saveToXML(this.realPath + "WEB-INF/HTMLPAGE/valid.xml", store);
            } catch (Exception e) {
                System.out.println("Error");
                e.printStackTrace();
            }
        } else {
            System.out.println("Error From Config File");
        }
    }

    private Store validateStore(Store storeTolavidate) {
        ListBrand listBrandValid = new ListBrand();
        // tạo store invalid để log lỗi ra hỏi tội thằng chó viết code ngu của trang đó
        Store storeInvalid = new Store(storeTolavidate.getStoreName(), storeTolavidate.getHomeUrl());
        ListBrand listBrandInValid = new ListBrand();
        //end  tạo store invalid để log lỗi ra hỏi tội thằng chó viết code ngu của trang đó
        for (int i = 0; i < storeTolavidate.getListBrand().getBrand().size(); i++) {
            Brand InvalidBrand = validateBrand(storeTolavidate.getListBrand().getBrand().get(i));
            listBrandValid.Add(storeTolavidate.getListBrand().getBrand().get(i));
            listBrandInValid.Add(InvalidBrand);
        }
        storeTolavidate.setListBrand(listBrandValid);
        storeInvalid.setListBrand(listBrandInValid);
        return storeInvalid;
    }

    private Brand validateBrand(Brand brandTovalidate) {
        boolean isBrandValid = true;
        ListMonitor listMonitorValid = new ListMonitor();
        // tạo listBrand invalid để log lỗi ra hỏi tội thằng chó viết code ngu của trang đó
        Brand brandInvalid = new Brand(brandTovalidate.getBrandName());
        ListMonitor listMonitorInvalid = new ListMonitor();
        // tạo listBrand invalid để log lỗi ra hỏi tội thằng chó viết code ngu của trang đó

        for (int i = 0; i < brandTovalidate.getListMonitor().getMonitor().size(); i++) {

            boolean isMonitorValid = validateProduct(brandTovalidate.getListMonitor().getMonitor().get(i));
            if (!isMonitorValid) {
                isBrandValid = false;
                listMonitorInvalid.add(brandTovalidate.getListMonitor().getMonitor().get(i));
            } else {
                listMonitorValid.add(brandTovalidate.getListMonitor().getMonitor().get(i));
            }
        } // đọc hết toàn bộ product ở trong brand và check từng product có valid hay không
        brandTovalidate.setListMonitor(listMonitorValid);
        brandInvalid.setListMonitor(listMonitorInvalid);
        return brandInvalid;
//        return brandTovalidate;
    }

    private boolean validateProduct(Monitor monitor) {
        boolean validated = true;
        try {
            if (monitor == null) {
                System.out.println("a");
            }
            String xmlString = XMLUtilities.marshallingToString(monitor);
            validated = validateXMLToInsertDB(xmlString, realPath + "schema/monitorSchema.xsd");
            if (validated) {
                System.out.println("san pham: " + monitor.getModel() + " is " + "valid");
            } else {
                System.out.println("san pham: " + monitor.getModel() + " is " + "invalid");
            }
        } catch (Exception e) {
            validated = false;
            System.out.println("san pham: " + monitor.getModel() + " is " + "invalid");
        }
        return validated;
    }

    public Monitor getProductByUri(String ProductUri) throws SAXException, ParserConfigurationException, IOException, XPathExpressionException, ParserConfigurationException, ParserConfigurationException, ParserConfigurationException {
//     String url = Internet.parseStringToUTF8Uri(ProductUri);
        Monitor monitor = null;
        String wellformXML = Internet.parseListHTMLToString(ProductUri);
        if (wellformXML != null) {
            Document doc = ParserDom.getDocumentFromStringXML(wellformXML);
            XPath xpath = XMLUtilities.createXPath();
            // get name
            String exp = "//*[@id=\"tab1\"]//table/tbody//tr[2]/td/table//tr[2]//table//tr";
            NodeList trList = (NodeList) xpath.evaluate(exp, doc, XPathConstants.NODESET); // lấy danh sách tr ra
            String price = "";
            String model = "";
            String screenBackground = "";
            String resolution = "";
            String contrast = "";
            String brightness = "";
            String responseTime = "";
            String screenColor = "";
            String screenView = "";
            String hubs = "";
            String electricalCapacity = "";
            String weight = "";

            NodeList nodeTRChild;
            for (int i = 0; i < trList.getLength(); i++) { // đọc từng cái tr
                nodeTRChild = trList.item(i).getChildNodes(); // lấy danh sách td của 1 thằng tr nào đó trong list
                String title = nodeTRChild.item(0).getTextContent();
                if (title.toLowerCase().contains("model")) {
                    if (nodeTRChild.item(1) != null) { // vị trí thứ nhất là vị trí của value còn vị trí 0 là title
                        model = nodeTRChild.item(1).getTextContent(); //  get value
                        model = TextUtilities.removeSymbols(model); // remove any symbol
                    }
                } else if (title.toLowerCase().contains("loại màn hình")) {
                    if (nodeTRChild.item(1) != null) { // vị trí thứ nhất là vị trí của value còn vị trí 0 là title
                        screenBackground = nodeTRChild.item(1).getTextContent(); //  get value
                        screenBackground = TextUtilities.removeSymbols(screenBackground); // remove any symbol
                    }
                } else if (title.toLowerCase().contains("độ phân giải")) {
                    if (nodeTRChild.item(1) != null) { // vị trí thứ nhất là vị trí của value còn vị trí 0 là title
                        resolution = nodeTRChild.item(1).getTextContent(); //  get value
                        resolution = TextUtilities.removeSymbols(resolution); // remove any symbol
                    }
                } else if (title.toLowerCase().contains("độ tương phản")) {
                    if (nodeTRChild.item(1) != null) { // vị trí thứ nhất là vị trí của value còn vị trí 0 là title
                        contrast = nodeTRChild.item(1).getTextContent(); //  get value
                        contrast = TextUtilities.removeSymbols(contrast); // remove any symbol
                    }
                } else if (title.toLowerCase().contains("độ sáng")) {
                    if (nodeTRChild.item(1) != null) { // vị trí thứ nhất là vị trí của value còn vị trí 0 là title
                        brightness = nodeTRChild.item(1).getTextContent(); //  get value
                        brightness = TextUtilities.removeSymbols(brightness); // remove any symbol
                        brightness = TextUtilities.getOnlyNumber(brightness);
                    }
                } else if (title.toLowerCase().contains("thời gian đáp ứng") || title.toLowerCase().contains("phản hồi")) {
                    if (nodeTRChild.item(1) != null) { // vị trí thứ nhất là vị trí của value còn vị trí 0 là title
                        responseTime = nodeTRChild.item(1).getTextContent(); //  get value
                        responseTime = TextUtilities.removeSymbols(responseTime); // remove any symbol
                        responseTime = TextUtilities.getOnlyNumber(responseTime);
                    }
                } else if (title.toLowerCase().contains("hiển thị màu")) {
                    if (nodeTRChild.item(1) != null) { // vị trí thứ nhất là vị trí của value còn vị trí 0 là title
                        screenColor = nodeTRChild.item(1).getTextContent(); //  get value
                        screenColor = TextUtilities.removeSymbols(screenColor); // remove any symbol
                    }
                } else if (title.toLowerCase().contains("góc nhìn")) {
                    if (nodeTRChild.item(1) != null) { // vị trí thứ nhất là vị trí của value còn vị trí 0 là title
                        screenView = nodeTRChild.item(1).getTextContent(); //  get value
                        screenView = TextUtilities.removeSymbols(screenView); // remove any symbol
                        screenView = TextUtilities.getOnlyNumber(screenView);
                    }
                } else if (title.toLowerCase().contains("kết nối")) {
                    if (nodeTRChild.item(1) != null) { // vị trí thứ nhất là vị trí của value còn vị trí 0 là title
                        hubs = nodeTRChild.item(1).getTextContent(); //  get value
                        hubs = TextUtilities.removeSymbols(hubs); // remove any symbol
                    }
                } else if (title.toLowerCase().contains("công xuất")) {
                    if (nodeTRChild.item(1) != null) { // vị trí thứ nhất là vị trí của value còn vị trí 0 là title
                        electricalCapacity = nodeTRChild.item(1).getTextContent(); //  get value
                        electricalCapacity = TextUtilities.removeSymbols(electricalCapacity); // remove any symbol
                        electricalCapacity = TextUtilities.getOnlyNumber(electricalCapacity);
                    }
                } else if (title.toLowerCase().contains("trọng lượng") || title.toLowerCase().contains("cân nặng")) {
                    if (nodeTRChild.item(1) != null) { // vị trí thứ nhất là vị trí của value còn vị trí 0 là title
                        weight = nodeTRChild.item(1).getTextContent(); //  get value
                        weight = TextUtilities.removeSymbols(weight); // remove any symbol
                        weight = TextUtilities.getOnlyNumber(weight);
                    }
                }
            } //end for get product infomation from table

            //lấy giá
            exp = "//*[@class='p-price-main']/span/text()";
            price = (String) xpath.evaluate(exp, doc, XPathConstants.STRING);
            if (price.matches(TextUtilities.EXPRESSION_CONTAINS_NUMBER)) {
                price = TextUtilities.getOnlyNumber(price);// cắt hết chỉ lấy số thôi
            }

            // end lấy giá
//            tạo Object để lưu giá trị vào 
            if (monitor == null) {
                monitor = new Monitor(model);
            }
            monitor.setUrl(ProductUri); // 
            monitor.setScreenBackground(screenBackground); //  loại màn hình
            monitor.setResolution(resolution); //độ phân giản
            monitor.setContrast(contrast); // độ tương phản
            monitor.setBrightness(new BigInteger(brightness)); // độ sáng
            monitor.setResponseTime(new BigInteger(responseTime)); // thời gian phản hồi
            monitor.setScreenColor(screenColor); // độ hiển thị màu
            monitor.setScreenView(new BigInteger(screenView)); // góc nhìn
            monitor.setHubs(hubs); // cổng kết nối
            monitor.setElectricalCapacity(new BigInteger(electricalCapacity));
            monitor.setWeight(Long.parseLong(weight));
            monitor.setPrice(price);

        }
        return monitor;
    }

    public void getLinkMonitorFromCategory() throws SAXException, ParserConfigurationException, IOException, XPathExpressionException {
        String wellformerHomePage = Internet.parseListHTMLToString("https://ben.com.vn/");
        Document doc = ParserDom.getDocumentFromStringXML(wellformerHomePage);

        XPath xpath = XMLUtilities.createXPath();
//        String exp = "//*[@id='mega-parent']/li/ul//a[contains(@href,'man-hinh-may-tinh')]";
        String exp = this.xPathAccessMonitorCategory;
        Node node = (Node) xpath.evaluate(exp, doc, XPathConstants.NODE);

        NamedNodeMap attr = node.getAttributes();
        uriMonitorFromCategory = host + attr.getNamedItem("href").getNodeValue();
        System.out.println(uriMonitorFromCategory);
    }

    public void getListCategoryUri(String uri) throws SAXException, ParserConfigurationException, IOException, XPathExpressionException {

//        String url = Internet.parseStringToUTF8Uri(uri);
        String wellformXML = Internet.parseListHTMLToString(uri);
        if (wellformXML != null) {
            Document doc = ParserDom.getDocumentFromStringXML(wellformXML);
            XPath xpath = XMLUtilities.createXPath();
//            String exp = "//*[@id=\"ajax-temp-pro\"]/div[@class='wrap-left']/div[@class='pa-list temp-tree']/div[@class='filter']/a";
            String exp = this.xPathGetListCategoryUrl;
            NodeList nodelist = (NodeList) xpath.evaluate(exp, doc, XPathConstants.NODESET);
            System.out.println("node list length:  " + nodelist.getLength());
            for (int i = 0; i < nodelist.getLength(); i++) {
                // lấy href của category
                NamedNodeMap attr = nodelist.item(i).getAttributes();
                String uriCategory = attr.getNamedItem("href").getNodeValue();
                // lấy tên của category từ cái thẻ img
                Node nodeImg = (Node) xpath.evaluate(this.xPathGetListCategoryName, nodelist.item(i), XPathConstants.NODE);
                attr = nodeImg.getAttributes();
                String categoryName = attr.getNamedItem("alt").getNodeValue();
                if (uriCategories == null) {
                    uriCategories = new HashMap<String, String>();

                }
                uriCategories.put(categoryName, host + uriCategory);
                System.out.println(host + uriCategory + " : " + categoryName);
            }

        }

    }

    public List<String> getListProductUri(String uriCategory) throws SAXException, ParserConfigurationException, IOException, XPathExpressionException {
        List<String> results = new ArrayList<>();

        int counterPagi = 0;
        String nextPage = "";
        do {

            counterPagi++;
            nextPage = uriCategory + "&page=" + counterPagi;

            String wellformXML = Internet.parseListHTMLToString(nextPage);

            if (wellformXML != null) {
                Document doc = ParserDom.getDocumentFromStringXML(wellformXML);
                XPath xpath = XMLUtilities.createXPath();
                String exp = this.xPathGetListProductUri;
//                String exp = "//*[@class='ls temp-pro-item']/li/p[@class='brand']/a";
                NodeList nodelist = (NodeList) xpath.evaluate(exp, doc, XPathConstants.NODESET);
//            System.out.println(nodelist.getLength());
                for (int i = 0; i < nodelist.getLength(); i++) {
                    NamedNodeMap attr = nodelist.item(i).getAttributes();
                    String uriProduct = attr.getNamedItem("href").getNodeValue();
                    if (uriProducts == null) {
                        uriProducts = new ArrayList<>();
                    }
                    results.add(host + uriProduct);
                    uriProducts.add(host + uriProduct);
//                System.out.println(uriProduct);

                }
                exp = this.xPathCheckPagination;
//                exp = "//*[@class='paging-next ico']";
                Node nextIcon = (Node) xpath.evaluate(exp, doc, XPathConstants.NODE);

                if (nextIcon != null) {
                    NamedNodeMap attr = nextIcon.getAttributes();
                    nextPage = host + attr.getNamedItem("href").getNodeValue();
                } else {
                    nextPage = null;
                }

            } else {
                nextPage = null;
            }
        } while (nextPage != null && nextPage != "");

        return results;
    }

    private boolean validateXMLToInsertDB(String xmlString, String schemaFilePath) {
        try {

            SchemaFactory sFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sFactory.newSchema(new File(schemaFilePath));
            Validator validator = schema.newValidator();
            InputSource inputSource = new InputSource(new StringReader(xmlString));
            validator.validate(new SAXSource(inputSource));

            return true;
        } catch (SAXException e) {
            Logger.getLogger(BenCrawler.class.getName()).log(Level.SEVERE, null, e);
        } catch (FileNotFoundException e) {
            Logger.getLogger(BenCrawler.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException e) {
            Logger.getLogger(BenCrawler.class.getName()).log(Level.SEVERE, null, e);
        }

        return false;
    }

    public Store unmarshallingStore(String filePath) {
        try {
            JAXBContext jc = JAXBContext.newInstance(Store.class);
            Unmarshaller u = jc.createUnmarshaller();
            File f = new File(filePath);
            Store item = (Store) u.unmarshal(f);
            return item;
        } catch (Exception e) {
            System.out.println("Failed when try to unmarshall file xml");
        }
        return null;
    }
}
