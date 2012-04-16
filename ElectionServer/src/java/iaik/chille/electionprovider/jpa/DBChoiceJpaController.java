/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iaik.chille.electionprovider.jpa;

import iaik.chille.electionprovider.db.DBChoice;
import iaik.chille.electionprovider.jpa.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

/**
 *
 * @author chille
 */
public class DBChoiceJpaController implements Serializable {
  private static final long serialVersionUID = 1L;

  public DBChoiceJpaController(EntityManagerFactory emf) {
    this.emf = emf;
  }
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public void create(DBChoice DBChoice) {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      em.persist(DBChoice);
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void edit(DBChoice DBChoice) throws NonexistentEntityException, Exception {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      DBChoice = em.merge(DBChoice);
      em.getTransaction().commit();
    } catch (Exception ex) {
      String msg = ex.getLocalizedMessage();
      if (msg == null || msg.length() == 0) {
        String id = DBChoice.getId().toString();
        if (findDBChoice(id) == null) {
          throw new NonexistentEntityException("The dBChoice with id " + id + " no longer exists.");
        }
      }
      throw ex;
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void destroy(String id) throws NonexistentEntityException {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      DBChoice DBChoice;
      try {
        DBChoice = em.getReference(DBChoice.class, id);
        DBChoice.getId();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The DBChoice with id " + id + " no longer exists.", enfe);
      }
      em.remove(DBChoice);
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public List<DBChoice> findDBChoiceEntities() {
    return findDBChoiceEntities(true, -1, -1);
  }

  public List<DBChoice> findDBChoiceEntities(int maxResults, int firstResult) {
    return findDBChoiceEntities(false, maxResults, firstResult);
  }

  private List<DBChoice> findDBChoiceEntities(boolean all, int maxResults, int firstResult) {
    EntityManager em = getEntityManager();
    try {
      Query q = em.createQuery("select object(o) from DBChoice as o");
      if (!all) {
        q.setMaxResults(maxResults);
        q.setFirstResult(firstResult);
      }
      return q.getResultList();
    } finally {
      em.close();
    }
  }

  public DBChoice findDBChoice(String id) {
    EntityManager em = getEntityManager();
    try {
      return em.find(DBChoice.class, id);
    } finally {
      em.close();
    }
  }

  public int getDBChoiceCount() {
    EntityManager em = getEntityManager();
    try {
      Query q = em.createQuery("select count(o) from DBChoice as o");
      return ((Long) q.getSingleResult()).intValue();
    } finally {
      em.close();
    }
  }

}
