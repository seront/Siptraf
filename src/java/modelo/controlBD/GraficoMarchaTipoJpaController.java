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
import modelo.entity.GraficoMarchaTipo;
import modelo.entity.GraficoMarchaTipoPK;
import modelo.entity.MarchaTipo;

/**
 *
 * @author Kelvins Insua
 */
public class GraficoMarchaTipoJpaController implements Serializable {

    public GraficoMarchaTipoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GraficoMarchaTipo graficoMarchaTipo) throws PreexistingEntityException, Exception {
        if (graficoMarchaTipo.getGraficoMarchaTipoPK() == null) {
            graficoMarchaTipo.setGraficoMarchaTipoPK(new GraficoMarchaTipoPK());
        }
        graficoMarchaTipo.getGraficoMarchaTipoPK().setIdMarchaTipo(graficoMarchaTipo.getMarchaTipo().getIdMarchaTipo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MarchaTipo marchaTipo = graficoMarchaTipo.getMarchaTipo();
            if (marchaTipo != null) {
                marchaTipo = em.getReference(marchaTipo.getClass(), marchaTipo.getIdMarchaTipo());
                graficoMarchaTipo.setMarchaTipo(marchaTipo);
            }
            em.persist(graficoMarchaTipo);
            if (marchaTipo != null) {
                marchaTipo.getGraficoMarchaTipoList().add(graficoMarchaTipo);
                marchaTipo = em.merge(marchaTipo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGraficoMarchaTipo(graficoMarchaTipo.getGraficoMarchaTipoPK()) != null) {
                throw new PreexistingEntityException("GraficoMarchaTipo " + graficoMarchaTipo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GraficoMarchaTipo graficoMarchaTipo) throws NonexistentEntityException, Exception {
        graficoMarchaTipo.getGraficoMarchaTipoPK().setIdMarchaTipo(graficoMarchaTipo.getMarchaTipo().getIdMarchaTipo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
//            GraficoMarchaTipo persistentGraficoMarchaTipo = em.find(GraficoMarchaTipo.class, graficoMarchaTipo.getGraficoMarchaTipoPK());
//            MarchaTipo marchaTipoOld = persistentGraficoMarchaTipo.getMarchaTipo();
//            MarchaTipo marchaTipoNew = graficoMarchaTipo.getMarchaTipo();
//            if (marchaTipoNew != null) {
//                marchaTipoNew = em.getReference(marchaTipoNew.getClass(), marchaTipoNew.getIdMarchaTipo());
//                graficoMarchaTipo.setMarchaTipo(marchaTipoNew);
//            }
            graficoMarchaTipo = em.merge(graficoMarchaTipo);
//            if (marchaTipoOld != null && !marchaTipoOld.equals(marchaTipoNew)) {
//                marchaTipoOld.getGraficoMarchaTipoList().remove(graficoMarchaTipo);
//                marchaTipoOld = em.merge(marchaTipoOld);
//            }
//            if (marchaTipoNew != null && !marchaTipoNew.equals(marchaTipoOld)) {
//                marchaTipoNew.getGraficoMarchaTipoList().add(graficoMarchaTipo);
//                marchaTipoNew = em.merge(marchaTipoNew);
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                GraficoMarchaTipoPK id = graficoMarchaTipo.getGraficoMarchaTipoPK();
                if (findGraficoMarchaTipo(id) == null) {
                    throw new NonexistentEntityException("The graficoMarchaTipo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(GraficoMarchaTipoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GraficoMarchaTipo graficoMarchaTipo;
            try {
                graficoMarchaTipo = em.getReference(GraficoMarchaTipo.class, id);
                graficoMarchaTipo.getGraficoMarchaTipoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The graficoMarchaTipo with id " + id + " no longer exists.", enfe);
            }
            MarchaTipo marchaTipo = graficoMarchaTipo.getMarchaTipo();
//            if (marchaTipo != null) {
//                marchaTipo.getGraficoMarchaTipoList().remove(graficoMarchaTipo);
//                marchaTipo = em.merge(marchaTipo);
//            }
            em.remove(graficoMarchaTipo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GraficoMarchaTipo> findGraficoMarchaTipoEntities() {
        return findGraficoMarchaTipoEntities(true, -1, -1);
    }

    public List<GraficoMarchaTipo> findGraficoMarchaTipoEntities(int maxResults, int firstResult) {
        return findGraficoMarchaTipoEntities(false, maxResults, firstResult);
    }

    private List<GraficoMarchaTipo> findGraficoMarchaTipoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GraficoMarchaTipo.class));
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

    public GraficoMarchaTipo findGraficoMarchaTipo(GraficoMarchaTipoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GraficoMarchaTipo.class, id);
        } finally {
            em.close();
        }
    }

    public int getGraficoMarchaTipoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GraficoMarchaTipo> rt = cq.from(GraficoMarchaTipo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<GraficoMarchaTipo> buscarPorIdMarchaTipo(int idMarchaTipo){
      EntityManager em=getEntityManager();
        try {
            
            TypedQuery<GraficoMarchaTipo> cq=em.createNamedQuery("GraficoMarchaTipo.findByIdMarchaTipo",GraficoMarchaTipo.class);
            cq.setParameter("idMarchaTipo", idMarchaTipo);
             return cq.getResultList();
           } finally {
            em.close();
        } 
    }
    
}
