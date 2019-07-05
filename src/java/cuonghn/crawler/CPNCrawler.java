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
import cuonghn.utils.Constant;
import cuonghn.utils.ImageUtils;
import cuonghn.utils.ParserDom;
import cuonghn.utils.TextUtilities;
import cuonghn.utils.XMLUtilities;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URLEncoder;
import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
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
    private int count = 0;
    private String realPath;
    private int totalproduct = 0;

    public CPNCrawler(String realPath) {
        this.realPath = realPath;
    }

    public CPNCrawler() {
    }

    public Store run() {
        Store storeValidated = null;
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
                            Monitor monitor = getProductByUri(uris.get(j), brand.getBrandName(), host);
                            if (monitor != null) {
                                brand.addMonitor(monitor);
                                totalproduct++;
                                System.out.println("cao xong product: " + monitor.getModel() + " trong brand: " + brand.getBrandName());
                            }

                        } catch (Exception e) {
                            System.out.println("Khong cao duoc trang nay  :" + uris.get(j));
                        }
                    }
                    //jaxb_Categories.addNewCategory(cate);
                    listBrand.Add(brand);
                } // end for
                store.setListBrand(listBrand);
            } // end if uri categories
//            System.out.println(jaxb_Categories.toString());

            XMLUtilities.saveToXML(this.realPath + "WEB-INF/HTMLPAGE/testPage.xml", store);
            storeValidated = validateStoreToInsertDB(store);
            System.out.println("Done");
            System.out.println("total product: " + totalproduct);
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        return storeValidated;
    }

    public Store validateStoreToInsertDB(Store store) {
        System.out.println("start validate ... ");
//        Store store = unmarshallingStore(realPath + "schema/test.xml");
        Store storeInvalid = validateStore(store);
        XMLUtilities.saveToXML(this.realPath + Constant.VALIDATED_FOLDER + "cpnvalid.xml", store);
        XMLUtilities.saveToXML(this.realPath + Constant.VALIDATED_FOLDER + "cpnInvalid.xml", storeInvalid);
        return store;
//        StoreDAO dao = new StoreDAO();
//        System.out.println("start insert to DB");
//        dao.masterInsertStore(store);
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

        if (brandTovalidate.getListMonitor() != null) {
            for (int i = 0; i < brandTovalidate.getListMonitor().getMonitor().size(); i++) {

                boolean isMonitorValid = validateProduct(brandTovalidate.getListMonitor().getMonitor().get(i));
                if (!isMonitorValid) {
                    isBrandValid = false;
                    listMonitorInvalid.add(brandTovalidate.getListMonitor().getMonitor().get(i));
                } else {
                    listMonitorValid.add(brandTovalidate.getListMonitor().getMonitor().get(i));
                }
            } // đọc hết toàn bộ product ở trong brand và check từng product có valid hay không
        }
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

    public Monitor getProductByUri(String ProductUri, String brandName, String currentStore) throws SAXException, ParserConfigurationException, IOException, XPathExpressionException, ParserConfigurationException, ParserConfigurationException, ParserConfigurationException {
//     String url = Internet.parseStringToUTF8Uri(ProductUri);
        Monitor monitor = null;
        String wellformXML = Internet.parseListHTMLToStringCPN(ProductUri);
        if (wellformXML != null) {
            Document doc = ParserDom.getDocumentFromStringXML(wellformXML);
            XPath xpath = XMLUtilities.createXPath();
            // get name
            String exp = "//*[@id='ProductDescription']/div/table//tr";
            NodeList trList = (NodeList) xpath.evaluate(exp, doc, XPathConstants.NODESET);
//            System.out.println(nodelist.getLength());
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
            String imageURL = "";
            NodeList nodeTRChild;
            for (int i = 0; i < trList.getLength(); i++) {
                nodeTRChild = trList.item(i).getChildNodes();
                String title = nodeTRChild.item(0).getTextContent();

                if (title.toLowerCase().contains("model")) {
//                    if (nodeTRChild.item(1) != null) { // vị trí thứ nhất là vị trí của value còn vị trí 0 là title
//                        model = nodeTRChild.item(1).getTextContent(); //  get value
//                        model = TextUtilities.removeSymbols(model); // remove any symbol
//                    }
                } else if (title.toLowerCase().contains("tấm nền") || title.toLowerCase().contains("loại màn hình") || title.equalsIgnoreCase("Tấm nền")) {
                    if (nodeTRChild.item(1) != null) { // vị trí thứ nhất là vị trí của value còn vị trí 0 là title
                        screenBackground = nodeTRChild.item(1).getTextContent(); //  get value
                        screenBackground = TextUtilities.removeSymbols(screenBackground); // remove any symbol
                    }
                } else if (title.equalsIgnoreCase("Độ phân giải") || title.equalsIgnoreCase("Độ ph&acirc;n giải")) {
                    if (nodeTRChild.item(1) != null) { // vị trí thứ nhất là vị trí của value còn vị trí 0 là title
                        resolution = nodeTRChild.item(1).getTextContent().trim(); //  get value
//                     
                        resolution = resolution.toLowerCase();
                        resolution = TextUtilities.removeSymbols(resolution);
                        resolution = TextUtilities.getByExpression(resolution, Constant.RESOLUTION_REGEX);
                        resolution = TextUtilities.removeWhiteSpace(resolution);
                        System.out.println("resolution: " + resolution);
                    }
                } else if (title.toLowerCase().contains("độ tương phản")) {
                    if (nodeTRChild.item(1) != null) { // vị trí thứ nhất là vị trí của value còn vị trí 0 là title
                        contrast = nodeTRChild.item(1).getTextContent(); //  get value
                        contrast = TextUtilities.removeSymbols(contrast); // remove any symbol
                    }
                } else if (title.toLowerCase().contains("độ sáng") || title.toLowerCase().contains("độ s&aacute;ng")) {
                    if (nodeTRChild.item(1) != null) { // vị trí thứ nhất là vị trí của value còn vị trí 0 là title
                        brightness = nodeTRChild.item(1).getTextContent(); //  get value
                        brightness = TextUtilities.removeSymbols(brightness); // remove any symbol
                        brightness = TextUtilities.getOnlyNumber(brightness);
                    }
                } else if (title.toLowerCase().contains("thời gian đáp ứng") || title.toLowerCase().contains("phản hồi") || title.equalsIgnoreCase("Thời gian đ&aacute;p ứng")) {
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
                } else if (title.toLowerCase().contains("kết nối") || title.equalsIgnoreCase("Cổng giao tiếp")) {
                    if (nodeTRChild.item(1) != null) { // vị trí thứ nhất là vị trí của value còn vị trí 0 là title
                        hubs = nodeTRChild.item(1).getTextContent(); //  get value
                        hubs = TextUtilities.removeSymbols(hubs); // remove any symbol
                    }
                } else if (title.toLowerCase().contains("công xuất") || title.equalsIgnoreCase("C&ocirc;ng suất")) {
                    if (nodeTRChild.item(1) != null) { // vị trí thứ nhất là vị trí của value còn vị trí 0 là title
                        electricalCapacity = nodeTRChild.item(1).getTextContent(); //  get value
                        electricalCapacity = TextUtilities.removeSymbols(electricalCapacity); // remove any symbol
                        electricalCapacity = TextUtilities.getOnlyNumber(electricalCapacity);
                    }
                } else if (title.toLowerCase().contains("trọng lượng") || title.toLowerCase().contains("cân nặng")) {
                    if (nodeTRChild.item(1) != null) { // vị trí thứ nhất là vị trí của value còn vị trí 0 là title
                        weight = nodeTRChild.item(1).getTextContent().trim().toLowerCase(); //  get value
                        weight = TextUtilities.removeSymbols(weight); // remove any symbol
//                        weight = weight.replaceAll(" ", "");
                        String expression = "(\\d)+\\.*(\\d+)(kg)";
                        weight = TextUtilities.getByExpression(weight, expression);
//                        weight = TextUtilities.getOnlyNumber(weight);
                    }
                }
            }

            //get model
            exp = "//*[@class='prodinfo']/li[contains(text(),'Mã hàng')]/span/text()";
            model = (String) xpath.evaluate(exp, doc, XPathConstants.STRING);

            //end get model
            // get price
            exp = "//*[@id=\"ProductDetails\"]//span[@class=\"SalePrice\"]/text()";
            price = (String) xpath.evaluate(exp, doc, XPathConstants.STRING);
            price = price.replaceAll("\\D+", ""); // cắt hết chỉ lấy số thôi
            //lấy description
            //
            exp = "//*[@class='BlockContent']/h2";
            String description = (String) xpath.evaluate(exp, doc, XPathConstants.STRING);
            // end lấy description
            if (monitor == null && model != "") {
                monitor = new Monitor(model);
            }
            // lấy hình
            exp = "//*[@class='ProductThumbImage']/a/img/@src";
            imageURL = (String) xpath.evaluate(exp, doc, XPathConstants.STRING);
            String imageName = ImageUtils.getNameImageFromUrl(imageURL);
            String filePath = new File(realPath).getParentFile().getAbsoluteFile().getParent();
            boolean isDownloaded = ImageUtils.saveImageByURL(filePath + Constant.IMAGE_FOLDER + imageName, imageURL); //lấy ảnh 
            System.out.println("lay anh: " + isDownloaded);
// lấy hình
            monitor.setBrandName(brandName);
            monitor.setUrl(ProductUri); // 
            monitor.setScreenBackground(screenBackground); //  loại màn hình
            monitor.setResolution(resolution); //độ phân giản
            monitor.setContrast(contrast); // độ tương phản
            if (!brightness.equals("")) {
                monitor.setBrightness(new BigInteger(brightness)); // độ sáng
            }
            if (!responseTime.equals("")) {
                monitor.setResponseTime(new BigInteger(responseTime)); // thời gian phản hồi
            }
            if (!screenView.equals("")) {
                monitor.setScreenView(new BigInteger(screenView)); // góc nhìn
            }
            monitor.setScreenColor(screenColor); // độ hiển thị màu

            monitor.setHubs(hubs); // cổng kết nối
            if (!electricalCapacity.equals("")) {
                monitor.setElectricalCapacity(new BigInteger(electricalCapacity));
            }
            monitor.setImgURL(Constant.IMAGE_FOLDER_SHOWING + imageName);
            monitor.setWeight(weight);
            monitor.setStoreName(currentStore);
            monitor.setPrice(price);
            monitor.setDescription(description);
        }
        return monitor;
    }

    public void getLinkMonitorFromCategory() throws SAXException, ParserConfigurationException, IOException, XPathExpressionException {
        String wellformerHomePage = Internet.parseListHTMLToString(host);
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
                String categoryName = nodelist.item(i).getTextContent();

                if (categoryName != null && categoryName != "") {
                    if (uriCategories == null) {
                        uriCategories = new HashMap<>();
                    }
                    uriCategories.put(categoryName, uriCategory);
                    System.out.println(host + uriCategory + " : " + categoryName);
                }

//                System.out.println(uriCategory);
            }
        }

    }

    public List<String> getListProductUri(String uriCategory) throws SAXException, ParserConfigurationException, IOException, XPathExpressionException {
        List<String> results = new ArrayList<>();
        String url = Internet.parseStringToUTF8Uri(uriCategory);
        String wellformXML = Internet.parseListHTMLToStringCPN(url);
        if (wellformXML != null) {
            try {
                Document doc = ParserDom.getDocumentFromStringXML(wellformXML);
                XPath xpath = XMLUtilities.createXPath();
                String exp = "//ul[@class='ProductList ']/li/div[@class='ProductDetails']/strong/a";
                NodeList nodelist = (NodeList) xpath.evaluate(exp, doc, XPathConstants.NODESET);
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
