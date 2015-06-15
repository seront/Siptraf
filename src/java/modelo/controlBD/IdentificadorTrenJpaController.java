/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.controlBD;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.controlBD.exceptions.NonexistentEntityException;
import modelo.controlBD.exceptions.PreexistingEntityException;
import modelo.entity.IdentificadorTren;

/**
 *
 * @author seront
 */
public class IdentificadorTrenJpaController implements Serializable {

    public IdentificadorTrenJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(IdentificadorTren identificadorTren) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(identificadorTren);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findIdentificadorTren(identificadorTren.getIdIdentificadorTren()) != null) {
                throw new PreexistingEntityException("IdentificadorTren " + identificadorTren + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(IdentificadorTren identificadorTren) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            identificadorTren = em.merge(identificadorTren);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = identificadorTren.getIdIdentificadorTren();
                if (findIdentificadorTren(id) == null) {
                    throw new NonexistentEntityException("The identificadorTren with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IdentificadorTren identificadorTren;
            try {
                identificadorTren = em.getReference(IdentificadorTren.class, id);
                identificadorTren.getIdIdentificadorTren();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The identificadorTren with id " + id + " no longer exists.", enfe);
            }
            em.remove(identificadorTren);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<IdentificadorTren> findIdentificadorTrenEntities() {
        return findIdentificadorTrenEntities(true, -1, -1);
    }

    public List<IdentificadorTren> findIdentificadorTrenEntities(int maxResults, int firstResult) {
        return findIdentificadorTrenEntities(false, maxResults, firstResult);
    }

    private List<IdentificadorTren> findIdentificadorTrenEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(IdentificadorTren.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public IdentificadorTren findIdentificadorTren(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(IdentificadorTren.class, id);
        } finally {
            em.close();
        }
    }

    public int getIdentificadorTrenCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<IdentificadorTren> rt = cq.from(IdentificadorTren.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<IdentificadorTren> listarServicio() {
       EntityManager em=getEntityManager();
        try {            
            TypedQuery<IdentificadorTren> cq=em.createNamedQuery("IdentificadorTren.findByServicio",IdentificadorTren.class);
             return cq.getResultList();
           } finally {
            em.close();
        }   
    }
    public List<IdentificadorTren> listarCategoriaIdentificacion() {
       EntityManager em=getEntityManager();
        try {            
            TypedQuery<IdentificadorTren> cq=em.createNamedQuery("IdentificadorTren.findByCategoriaIdentificacion",IdentificadorTren.class);
             return cq.getResultList();
           } finally {
            em.close();
        }   
    }
    public List<IdentificadorTren> listarCRT() {
       EntityManager em=getEntityManager();
        try {            
            TypedQuery<IdentificadorTren> cq=em.createNamedQuery("IdentificadorTren.findByCRT",IdentificadorTren.class);
             return cq.getResultList();
           } finally {
            em.close();
        }   
    }
    public List<IdentificadorTren> listarEmpresaPropietaria() {
       EntityManager em=getEntityManager();
        try {            
            TypedQuery<IdentificadorTren> cq=em.createNamedQuery("IdentificadorTren.findByEmpresaPropietaria",IdentificadorTren.class);
             return cq.getResultList();
           } finally {
            em.close();
        }   
    }
    
}
