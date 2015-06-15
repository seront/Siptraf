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
import modelo.entity.MarchaTipo;
import modelo.entity.RestriccionMarchaTipo;
import modelo.entity.RestriccionMarchaTipoPK;

/**
 *
 * @author Kelvins Insua
 */
public class RestriccionMarchaTipoJpaController implements Serializable {

    public RestriccionMarchaTipoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RestriccionMarchaTipo restriccionMarchaTipo) throws PreexistingEntityException, Exception {
        if (restriccionMarchaTipo.getRestriccionMarchaTipoPK() == null) {
            restriccionMarchaTipo.setRestriccionMarchaTipoPK(new RestriccionMarchaTipoPK());
        }
        restriccionMarchaTipo.getRestriccionMarchaTipoPK().setIdMarchaTipo(restriccionMarchaTipo.getMarchaTipo().getIdMarchaTipo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MarchaTipo marchaTipo = restriccionMarchaTipo.getMarchaTipo();
            if (marchaTipo != null) {
                marchaTipo = em.getReference(marchaTipo.getClass(), marchaTipo.getIdMarchaTipo());
                restriccionMarchaTipo.setMarchaTipo(marchaTipo);
            }
            em.persist(restriccionMarchaTipo);
            if (marchaTipo != null) {
                marchaTipo.getRestriccionMarchaTipoList().add(restriccionMarchaTipo);
                marchaTipo = em.merge(marchaTipo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRestriccionMarchaTipo(restriccionMarchaTipo.getRestriccionMarchaTipoPK()) != null) {
                throw new PreexistingEntityException("RestriccionMarchaTipo " + restriccionMarchaTipo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RestriccionMarchaTipo restriccionMarchaTipo) throws NonexistentEntityException, Exception {
        restriccionMarchaTipo.getRestriccionMarchaTipoPK().setIdMarchaTipo(restriccionMarchaTipo.getMarchaTipo().getIdMarchaTipo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
//            RestriccionMarchaTipo persistentRestriccionMarchaTipo = em.find(RestriccionMarchaTipo.class, restriccionMarchaTipo.getRestriccionMarchaTipoPK());
//            MarchaTipo marchaTipoOld = persistentRestriccionMarchaTipo.getMarchaTipo();
//            MarchaTipo marchaTipoNew = restriccionMarchaTipo.getMarchaTipo();
//            if (marchaTipoNew != null) {
//                marchaTipoNew = em.getReference(marchaTipoNew.getClass(), marchaTipoNew.getIdMarchaTipo());
//                restriccionMarchaTipo.setMarchaTipo(marchaTipoNew);
//            }
            restriccionMarchaTipo = em.merge(restriccionMarchaTipo);
//            if (marchaTipoOld != null && !marchaTipoOld.equals(marchaTipoNew)) {
//                marchaTipoOld.getRestriccionMarchaTipoList().remove(restriccionMarchaTipo);
//                marchaTipoOld = em.merge(marchaTipoOld);
//            }
//            if (marchaTipoNew != null && !marchaTipoNew.equals(marchaTipoOld)) {
//                marchaTipoNew.getRestriccionMarchaTipoList().add(restriccionMarchaTipo);
//                marchaTipoNew = em.merge(marchaTipoNew);
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                RestriccionMarchaTipoPK id = restriccionMarchaTipo.getRestriccionMarchaTipoPK();
                if (findRestriccionMarchaTipo(id) == null) {
                    throw new NonexistentEntityException("The restriccionMarchaTipo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(RestriccionMarchaTipoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RestriccionMarchaTipo restriccionMarchaTipo;
            try {
                restriccionMarchaTipo = em.getReference(RestriccionMarchaTipo.class, id);
                restriccionMarchaTipo.getRestriccionMarchaTipoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The restriccionMarchaTipo with id " + id + " no longer exists.", enfe);
            }
//            MarchaTipo marchaTipo = restriccionMarchaTipo.getMarchaTipo();
//            if (marchaTipo != null) {
//                marchaTipo.getRestriccionMarchaTipoList().remove(restriccionMarchaTipo);
//                marchaTipo = em.merge(marchaTipo);
//            }
            em.remove(restriccionMarchaTipo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RestriccionMarchaTipo> findRestriccionMarchaTipoEntities() {
        return findRestriccionMarchaTipoEntities(true, -1, -1);
    }

    public List<RestriccionMarchaTipo> findRestriccionMarchaTipoEntities(int maxResults, int firstResult) {
        return findRestriccionMarchaTipoEntities(false, maxResults, firstResult);
    }

    private List<RestriccionMarchaTipo> findRestriccionMarchaTipoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RestriccionMarchaTipo.class));
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

    public RestriccionMarchaTipo findRestriccionMarchaTipo(RestriccionMarchaTipoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RestriccionMarchaTipo.class, id);
        } finally {
            em.close();
        }
    }

    public int getRestriccionMarchaTipoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RestriccionMarchaTipo> rt = cq.from(RestriccionMarchaTipo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<RestriccionMarchaTipo> buscarPorIdMarchaTipo(int idMarchaTipo){
      EntityManager em=getEntityManager();
        try {
            
            TypedQuery<RestriccionMarchaTipo> cq=em.createNamedQuery("RestriccionMarchaTipo.findByIdMarchaTipo",RestriccionMarchaTipo.class);
            cq.setParameter("idMarchaTipo", idMarchaTipo);
             return cq.getResultList();
           } finally {
            em.close();
        } 
    }
    
}
