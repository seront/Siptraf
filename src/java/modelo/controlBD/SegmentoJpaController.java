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
import modelo.entity.Linea;
import modelo.entity.Segmento;
import modelo.entity.SegmentoPK;

/**
 *
 * @author Kelvins Insua
 */
public class SegmentoJpaController implements Serializable {

    public SegmentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Segmento segmento) throws PreexistingEntityException, Exception {
        if (segmento.getSegmentoPK() == null) {
            segmento.setSegmentoPK(new SegmentoPK());
        }
        segmento.getSegmentoPK().setIdLinea(segmento.getLinea().getIdLinea());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Linea linea = segmento.getLinea();
            if (linea != null) {
                linea = em.getReference(linea.getClass(), linea.getIdLinea());
                segmento.setLinea(linea);
            }
            em.persist(segmento);
            if (linea != null) {
                linea.getSegmentoList().add(segmento);
                linea = em.merge(linea);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSegmento(segmento.getSegmentoPK()) != null) {
                throw new PreexistingEntityException("Segmento " + segmento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Segmento segmento) throws NonexistentEntityException, Exception {
        segmento.getSegmentoPK().setIdLinea(segmento.getLinea().getIdLinea());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
//            Segmento persistentSegmento = em.find(Segmento.class, segmento.getSegmentoPK());
//            Linea lineaOld = persistentSegmento.getLinea();
//            Linea lineaNew = segmento.getLinea();
//            if (lineaNew != null) {
//                lineaNew = em.getReference(lineaNew.getClass(), lineaNew.getIdLinea());
//                segmento.setLinea(lineaNew);
//            }
            segmento = em.merge(segmento);
//            if (lineaOld != null && !lineaOld.equals(lineaNew)) {
//                lineaOld.getSegmentoList().remove(segmento);
//                lineaOld = em.merge(lineaOld);
//            }
//            if (lineaNew != null && !lineaNew.equals(lineaOld)) {
//                lineaNew.getSegmentoList().add(segmento);
//                lineaNew = em.merge(lineaNew);
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SegmentoPK id = segmento.getSegmentoPK();
                if (findSegmento(id) == null) {
                    throw new NonexistentEntityException("The segmento with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SegmentoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Segmento segmento;
            try {
                segmento = em.getReference(Segmento.class, id);
                segmento.getSegmentoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The segmento with id " + id + " no longer exists.", enfe);
            }
//            Linea linea = segmento.getLinea();
//            if (linea != null) {
//                linea.getSegmentoList().remove(segmento);
//                linea = em.merge(linea);
//            }
            em.remove(segmento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Segmento> findSegmentoEntities() {
        return findSegmentoEntities(true, -1, -1);
    }

    public List<Segmento> findSegmentoEntities(int maxResults, int firstResult) {
        return findSegmentoEntities(false, maxResults, firstResult);
    }

    private List<Segmento> findSegmentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Segmento.class));
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

    public Segmento findSegmento(SegmentoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Segmento.class, id);
        } finally {
            em.close();
        }
    }

    public int getSegmentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Segmento> rt = cq.from(Segmento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
     public List<Segmento> buscarIdLineaAscendente(int idLinea) {
      EntityManager em=getEntityManager();
        try {
            
            TypedQuery<Segmento> cq=em.createNamedQuery("Segmento.findByIdLineaAscendente",Segmento.class);
            cq.setParameter("idLinea", idLinea);
             return cq.getResultList();
           } finally {
            em.close();
        }  
    }

    public List<Segmento> buscarIdLineaDescendente(int idLinea) {
       EntityManager em=getEntityManager();
        try {
            
            TypedQuery<Segmento> cq=em.createNamedQuery("Segmento.findByIdLineaDescendente",Segmento.class);
            cq.setParameter("idLinea", idLinea);
             return cq.getResultList();
           } finally {
            em.close();
        }   
    }
 public Segmento buscarSegmentoPK(int idLinea, double idPkInicial) {
       EntityManager em=getEntityManager();
        try {
            
            TypedQuery<Segmento> cq=em.createNamedQuery("Segmento.findBySegmentoPK",Segmento.class);
            cq.setParameter("idLinea", idLinea);
            cq.setParameter("idPkInicial", idPkInicial);
             return cq.getSingleResult();
           } finally {
            em.close();
        }   
    }
    
}
