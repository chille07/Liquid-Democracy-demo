
package iaik.chille.ballotsigner.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "login", namespace = "http://ballotsigner.chille.iaik/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "login", namespace = "http://ballotsigner.chille.iaik/", propOrder = {
    "id",
    "something"
})
public class Login {

    @XmlElement(name = "id", namespace = "")
    private String id;
    @XmlElement(name = "something", namespace = "")
    private String something;

    /**
     * 
     * @return
     *     returns String
     */
    public String getId() {
        return this.id;
    }

    /**
     * 
     * @param id
     *     the value for the id property
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     returns String
     */
    public String getSomething() {
        return this.something;
    }

    /**
     * 
     * @param something
     *     the value for the something property
     */
    public void setSomething(String something) {
        this.something = something;
    }

}
