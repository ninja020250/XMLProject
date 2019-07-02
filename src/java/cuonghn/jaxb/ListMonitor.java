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
 *         &lt;element ref="{http://www.monitor.com}Monitor" maxOccurs="unbounded"/>
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
    "monitor"
})
@XmlRootElement(name = "ListMonitor", namespace = "http://www.monitors.com")
public class ListMonitor {

    @XmlElement(name = "Monitor", required = true)
    protected List<Monitor> monitor;

    /**
     * Gets the value of the monitor property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the monitor property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMonitor().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Monitor }
     *
     *
     */
    public ListMonitor(List<Monitor> monitor) {
        this.monitor = monitor;
    }

    public ListMonitor() {
    }

    public void add(Monitor newMon) {
        if (monitor == null) {
            monitor = new ArrayList<>();

        }
        monitor.add(newMon);
    }

    public List<Monitor> getMonitor() {
        if (monitor == null) {
            monitor = new ArrayList<Monitor>();
        }
        return this.monitor;
    }

    public void setMonitor(List<Monitor> monitor) {
        this.monitor = monitor;
    }

}
