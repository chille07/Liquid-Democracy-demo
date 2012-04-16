/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iaik.chille.electionprovider.db;

import iaik.chille.electionprovider.jaxb.Choice;
import iaik.chille.electionprovider.jaxb.Election;
import java.io.Serializable;
import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;
import java.util.Vector;
import javax.persistence.*;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

/**
 * 
 * @author chille
 */
@Entity
public class DBElection implements Serializable {
  private static final long serialVersionUID = 1L;
  
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  private String title;
  private String question;
  private String url;
  private Date validFrom;
  private Date validTo;

  private String privateKey;
  private String publicKey;

  @OneToMany
  private List<DBChoice> choice;

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash; 
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof DBElection)) {
      return false;
    }
    DBElection other = (DBElection) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "iaik.chille.elections.test.Election[ id=" + id + " ]";
  }

  public String getTitle() {
    return title;
  }

  public String getQuestion() {
    return question;
  }

  public String getUrl() {
    return url;
  }

  public Date getValidFrom() {
    return validFrom;
  }

  public Date getValidTo() {
    return validTo;
  }

  public List<DBChoice> getChoice() {
    return choice;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setValidFrom(Date validFrom) {
    this.validFrom = validFrom;
  }

  public void setValidTo(Date validTo) {
    this.validTo = validTo;
  }

  public void setChoice(List<DBChoice> choice) {
    this.choice = choice;
  }

  public String getPrivateKey() {
    return privateKey;
  }

  public void setPrivateKey(String privateKey) {
    this.privateKey = privateKey;
  }

  public String getPublicKey() {
    return publicKey;
  }

  public void setPublicKey(String publicKey) {
    this.publicKey = publicKey;
  }


  // added:
  public Election convert() throws DatatypeConfigurationException
  {
    Election el = new Election();
    el.setId(this.getId().toString());
    el.setPrivateKey(this.getPrivateKey());
    el.setPublicKey(this.getPublicKey());
    el.setTitle(this.getTitle());
    el.setQuestion(this.getQuestion());
    el.setUrl(this.getUrl());

    GregorianCalendar c = new GregorianCalendar();
    c.setTime(this.getValidFrom());
    el.setValidFrom(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
    c.setTime(this.getValidTo());
    el.setValidTo(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));

    
    for(int i=0;i<this.choice.size();i++)
    {
      el.getChoice().add(choice.get(i).convert());
    }
    return el;
  }

  public DBElection()
  {
    this.choice = new Vector<DBChoice>();
  }
  public DBElection(Election el)
  {
    this.choice = new Vector<DBChoice>();
    this.setId(UUID.fromString(el.getId()));
    this.setPrivateKey(el.getPrivateKey());
    this.setPublicKey(el.getPublicKey());
    this.setQuestion(el.getQuestion());
    this.setTitle(el.getTitle());
    this.setUrl(el.getUrl());
    this.setValidFrom(new java.sql.Date(el.getValidFrom().toGregorianCalendar().getTime().getTime()));
    this.setValidTo(new java.sql.Date(el.getValidTo().toGregorianCalendar().getTime().getTime()));


    for(int i=0;i<el.getChoice().size();i++)
    {
      this.choice.add(new DBChoice(el.getChoice().get(i)));
    }
  }


}
