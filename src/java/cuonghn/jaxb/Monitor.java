
package cuonghn.jaxb;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="model" type="{http://www.monitor.com}modelDeclare"/>
 *         &lt;element name="price" type="{http://www.monitor.com}PriceDeclare"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="url" type="{http://www.monitor.com}URLDeclare"/>
 *         &lt;element name="screenBackground" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="resolution" type="{http://www.monitor.com}resolutionDeclare"/>
 *         &lt;element name="contrast" type="{http://www.monitor.com}contrastDeclare"/>
 *         &lt;element name="brightness" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *         &lt;element name="responseTime" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *         &lt;element name="screenColor" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="screenView" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *         &lt;element name="hubs" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="electricalCapacity" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *         &lt;element name="weight" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.monitor.com}IDDeclare" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "model",
    "price",
    "description",
    "url",
    "screenBackground",
    "resolution",
    "contrast",
    "brightness",
    "responseTime",
    "screenColor",
    "screenView",
    "hubs",
    "electricalCapacity",
    "weight"
})
@XmlRootElement(name = "Monitor")
public class Monitor {
    
    @XmlElement(required = true)
    protected String model;
    @XmlElement(required = true)
    protected String price;
    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true)
    protected String url;
    @XmlElement(required = true)
    protected String screenBackground;
    @XmlElement(required = true)
    protected String resolution;
    @XmlElement(required = true)
    protected String contrast;
    @XmlElement(required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger brightness;
    @XmlElement(required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger responseTime;
    @XmlElement(required = true)
    protected String screenColor;
    @XmlElement(required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger screenView;
    @XmlElement(required = true)
    protected String hubs;
    @XmlElement(required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger electricalCapacity;
    protected long weight;
    @XmlAttribute(name = "id")
    protected Long id;

    public Monitor(String model) {
        this.model = model;
    }

    public Monitor() {
    }

    /**
     * Gets the value of the model property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the value of the model property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModel(String value) {
        this.model = value;
    }

    /**
     * Gets the value of the price property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrice(String value) {
        this.price = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the url property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the value of the url property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrl(String value) {
        this.url = value;
    }

    /**
     * Gets the value of the screenBackground property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScreenBackground() {
        return screenBackground;
    }

    /**
     * Sets the value of the screenBackground property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScreenBackground(String value) {
        this.screenBackground = value;
    }

    /**
     * Gets the value of the resolution property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResolution() {
        return resolution;
    }

    /**
     * Sets the value of the resolution property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResolution(String value) {
        this.resolution = value;
    }

    /**
     * Gets the value of the contrast property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContrast() {
        return contrast;
    }

    /**
     * Sets the value of the contrast property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContrast(String value) {
        this.contrast = value;
    }

    /**
     * Gets the value of the brightness property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBrightness() {
        return brightness;
    }

    /**
     * Sets the value of the brightness property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBrightness(BigInteger value) {
        this.brightness = value;
    }

    /**
     * Gets the value of the responseTime property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getResponseTime() {
        return responseTime;
    }

    /**
     * Sets the value of the responseTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setResponseTime(BigInteger value) {
        this.responseTime = value;
    }

    /**
     * Gets the value of the screenColor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScreenColor() {
        return screenColor;
    }

    /**
     * Sets the value of the screenColor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScreenColor(String value) {
        this.screenColor = value;
    }

    /**
     * Gets the value of the screenView property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getScreenView() {
        return screenView;
    }

    /**
     * Sets the value of the screenView property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setScreenView(BigInteger value) {
        this.screenView = value;
    }

    /**
     * Gets the value of the hubs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHubs() {
        return hubs;
    }

    /**
     * Sets the value of the hubs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHubs(String value) {
        this.hubs = value;
    }

    /**
     * Gets the value of the electricalCapacity property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getElectricalCapacity() {
        return electricalCapacity;
    }

    /**
     * Sets the value of the electricalCapacity property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setElectricalCapacity(BigInteger value) {
        this.electricalCapacity = value;
    }

    /**
     * Gets the value of the weight property.
     * 
     */
    public long getWeight() {
        return weight;
    }

    /**
     * Sets the value of the weight property.
     * 
     */
    public void setWeight(long value) {
        this.weight = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(Long value) {
        this.id = value;
    }

}
