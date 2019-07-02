package cuonghn.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element ref="{http://www.brand.com}Brand" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "brand"
})
@XmlRootElement(name = "ListBrand", namespace = "http://www.brands.com")
public class ListBrand {

    @XmlElement(name = "Brand", namespace = "http://www.brand.com", required = true)
    protected List<Brand> brand;

    /**
     * Gets the value of the brand property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the brand property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBrand().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Brand }
     *
     *
     */
    public ListBrand(List<Brand> brand) {
        this.brand = brand;
    }

    public ListBrand() {
    }

    public void Add(Brand newBrand) {
        if (brand == null) {
            brand = new ArrayList<Brand>();
        }
        brand.add(newBrand);
    }

    public void setBrand(List<Brand> brand) {
        this.brand = brand;
    }

    public List<Brand> getBrand() {
        if (brand == null) {
            brand = new ArrayList<Brand>();
        }
        return this.brand;
    }

}
