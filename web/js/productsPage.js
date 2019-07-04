var products;

var convertStringToXML = function (xmlString) {
    var parser = new DOMParser();
    xmlDom = parser.parseFromString(xmlString, "text/xml");   
};
