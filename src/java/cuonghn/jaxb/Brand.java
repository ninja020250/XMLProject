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
 *         &lt;element name="BrandName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element ref="{http://www.monitors.com}ListMonitor"/>
 *       &lt;/sequence>
 *       &lt;attribute name="BrandID" use="required" type="{http://www.brand.com}IDDeclare" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "brandName",
    "description",
    "listMonitor"
})
@XmlRootElement(name = "Brand", namespace = "http://www.brand.com")
public class Brand {

    @XmlElement(name = "BrandName", namespace = "http://www.brand.com", required = true)
    protected String brandName;
    @XmlElement(name = "Description", namespace = "http://www.brand.com", required = true)
    protected String description;
    @XmlElement(name = "ListMonitor", namespace = "http://www.monitors.com", required = true)
    protected ListMonitor listMonitor;
    @XmlAttribute(name = "BrandID", required = true)
    protected long brandID;

    public Brand(String brandName) {
        this.brandName = brandName;
    }

    public Brand() {
    }

    public void addMonitor(Monitor newMon) {
        if (this.listMonitor == null) {
            listMonitor = new ListMonitor();
        }
        listMonitor.add(newMon);
    }

    /**
     * Gets the value of the brandName property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * Sets the value of the brandName property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setBrandName(String value) {
        this.brandName = value;
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
     * Gets the value of the listMonitor property.
     *
     * @return possible object is {@link ListMonitor }
     *
     */
    public ListMonitor getListMonitor() {
        return listMonitor;
    }

    /**
     * Sets the value of the listMonitor property.
     *
     * @param value allowed object is {@link ListMonitor }
     *
     */
    public void setListMonitor(ListMonitor value) {
        this.listMonitor = value;
    }

    /**
     * Gets the value of the brandID property.
     *
     */
    public long getBrandID() {
        return brandID;
    }

    /**
     * Sets the value of the brandID property.
     *
     */
    public void setBrandID(long value) {
        this.brandID = value;
    }

}
