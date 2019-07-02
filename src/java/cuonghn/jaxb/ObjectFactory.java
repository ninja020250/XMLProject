
package cuonghn.jaxb;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cuonghn.jaxb package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cuonghn.jaxb
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Store }
     * 
     */
    public Store createStore() {
        return new Store();
    }

    /**
     * Create an instance of {@link ListBrand }
     * 
     */
    public ListBrand createListBrand() {
        return new ListBrand();
    }

    /**
     * Create an instance of {@link Brand }
     * 
     */
    public Brand createBrand() {
        return new Brand();
    }

    /**
     * Create an instance of {@link ListMonitor }
     * 
     */
    public ListMonitor createListMonitor() {
        return new ListMonitor();
    }

    /**
     * Create an instance of {@link Monitor }
     * 
     */
    public Monitor createMonitor() {
        return new Monitor();
    }

}
