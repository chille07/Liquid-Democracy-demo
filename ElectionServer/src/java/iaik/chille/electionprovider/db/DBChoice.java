/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iaik.chille.electionprovider.db;

import iaik.chille.electionprovider.jaxb.Choice;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author chille
 */
@Entity 
public class DBChoice implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  //@GeneratedValue(strategy = GenerationType.AUTO)
  private String id;


  private String answer;
  private String detail;
  private String url;
  private int result_;

  public DBChoice(Choice get)
  {
    this.id = get.getId();
    this.answer=get.getAnswer();
    this.detail=get.getDetail();
    this.url=get.getUrl();

    this.result_=get.getResult() != null ? get.getResult().intValue(): 0;
  }
  public DBChoice()
  {
    
  }
  public Choice convert()
  {
    Choice c = new Choice();
    c.setAnswer(answer);
    c.setDetail(this.detail);
    c.setId(this.id.toString());
    c.setResult(this.result_);
    c.setUrl(this.url);
    return c;
  }


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof DBChoice)) {
      return false;
    }
    DBChoice other = (DBChoice) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "iaik.chille.elections.db.DBChoice[ id=" + id + " ]";
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public int getResult_() {
    return result_;
  }

  public void setResult_(int result_) {
    this.result_ = result_;
  }

}
