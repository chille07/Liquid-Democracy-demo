
package iaik.chille.ballotsigner.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getVoteSigned", namespace = "http://ballotsigner.chille.iaik/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getVoteSigned", namespace = "http://ballotsigner.chille.iaik/")
public class GetVoteSigned {

    @XmlElement(name = "vote", namespace = "")
    private String vote;

    /**
     * 
     * @return
     *     returns String
     */
    public String getVote() {
        return this.vote;
    }

    /**
     * 
     * @param vote
     *     the value for the vote property
     */
    public void setVote(String vote) {
        this.vote = vote;
    }

}
