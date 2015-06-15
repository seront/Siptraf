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
import modelo.entity.PrefijoNumeracion;

/**
 *
 * @author seront
 */
public class PrefijoNumeracionJpaController implements Serializable {

    public PrefijoNumeracionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PrefijoNumeracion prefijoNumeracion) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(prefijoNumeracion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPrefijoNumeracion(prefijoNumeracion.getIdPrefijo()) != null) {
                throw new PreexistingEntityException("PrefijoNumeracion " + prefijoNumeracion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PrefijoNumeracion prefijoNumeracion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            prefijoNumeracion = em.merge(prefijoNumeracion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = prefijoNumeracion.getIdPrefijo();
                if (findPrefijoNumeracion(id) == null) {
                    throw new NonexistentEntityException("The prefijoNumeracion with id " + id + " no longer exists.");
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
            PrefijoNumeracion prefijoNumeracion;
            try {
                prefijoNumeracion = em.getReference(PrefijoNumeracion.class, id);
                prefijoNumeracion.getIdPrefijo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The prefijoNumeracion with id " + id + " no longer exists.", enfe);
            }
            em.remove(prefijoNumeracion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PrefijoNumeracion> findPrefijoNumeracionEntities() {
        return findPrefijoNumeracionEntities(true, -1, -1);
    }

    public List<PrefijoNumeracion> findPrefijoNumeracionEntities(int maxResults, int firstResult) {
        return findPrefijoNumeracionEntities(false, maxResults, firstResult);
    }

    private List<PrefijoNumeracion> findPrefijoNumeracionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PrefijoNumeracion.class));
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

    public PrefijoNumeracion findPrefijoNumeracion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PrefijoNumeracion.class, id);
        } finally {
            em.close();
        }
    }

    public int getPrefijoNumeracionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PrefijoNumeracion> rt = cq.from(PrefijoNumeracion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Integer> listarId() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Integer> cq = em.createNamedQuery("PrefijoNumeracion.listarIdPrefijo", Integer.class);
            return cq.getResultList();
        } finally {
            em.close();
        }
    }
    
}
