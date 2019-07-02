package cuonghn.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="StoreName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="HomeUrl" type="{http://www.store.com}URLDeclare"/>
 *         &lt;element ref="{http://www.brands.com}ListBrand"/>
 *       &lt;/sequence>
 *       &lt;attribute name="StoreID" use="required" type="{http://www.store.com}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "storeName",
    "description",
    "homeUrl",
    "listBrand"
})
@XmlRootElement(name = "Store", namespace = "http://www.store.com")
public class Store {

    @XmlElement(name = "StoreName", namespace = "http://www.store.com", required = true)
    protected String storeName;
    @XmlElement(name = "Description", namespace = "http://www.store.com", required = true)
    protected String description;
    @XmlElement(name = "HomeUrl", namespace = "http://www.store.com", required = true)
    protected String homeUrl;
    @XmlElement(name = "ListBrand", namespace = "http://www.brands.com", required = true)
    protected ListBrand listBrand;
    @XmlAttribute(name = "StoreID", required = true)
    protected long storeID;

    public Store(String homeUrl) {
        this.homeUrl = homeUrl;
    }

    public Store(String storeName, String homeUrl) {
        this.storeName = storeName;
        this.homeUrl = homeUrl;
    }

    public Store() {
    }

    /**
     * Gets the value of the storeName property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * Sets the value of the storeName property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setStoreName(String value) {
        this.storeName = value;
    }

    /**
     * Gets the value of the description property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the homeUrl property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getHomeUrl() {
        return homeUrl;
    }

    /**
     * Sets the value of the homeUrl property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setHomeUrl(String value) {
        this.homeUrl = value;
    }

    /**
     * Gets the value of the listBrand property.
     *
     * @return possible object is {@link ListBrand }
     *
     */
    public ListBrand getListBrand() {
        return listBrand;
    }

    /**
     * Sets the value of the listBrand property.
     *
     * @param value allowed object is {@link ListBrand }
     *
     */
    public void setListBrand(ListBrand value) {
        this.listBrand = value;
    }

    /**
     * Gets the value of the storeID property.
     *
     */
    public long getStoreID() {
        return storeID;
    }

    /**
     * Sets the value of the storeID property.
     *
     */
    public void setStoreID(long value) {
        this.storeID = value;
    }

}
