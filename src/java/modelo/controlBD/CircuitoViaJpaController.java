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
import modelo.entity.CircuitoVia;
import modelo.entity.CircuitoViaPK;
import modelo.entity.Linea;

/**
 *
 * @author Kelvins Insua
 */
public class CircuitoViaJpaController implements Serializable {

    public CircuitoViaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CircuitoVia circuitoVia) throws PreexistingEntityException, Exception {
        if (circuitoVia.getCircuitoViaPK() == null) {
            circuitoVia.setCircuitoViaPK(new CircuitoViaPK());
        }
        circuitoVia.getCircuitoViaPK().setIdLinea(circuitoVia.getLinea().getIdLinea());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Linea linea = circuitoVia.getLinea();
            if (linea != null) {
                linea = em.getReference(linea.getClass(), linea.getIdLinea());
                circuitoVia.setLinea(linea);
            }
            em.persist(circuitoVia);
            if (linea != null) {
                linea.getCircuitoViaList().add(circuitoVia);
                linea = em.merge(linea);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCircuitoVia(circuitoVia.getCircuitoViaPK()) != null) {
                throw new PreexistingEntityException("CircuitoVia " + circuitoVia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CircuitoVia circuitoVia) throws NonexistentEntityException, Exception {
        circuitoVia.getCircuitoViaPK().setIdLinea(circuitoVia.getLinea().getIdLinea());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
//            CircuitoVia persistentCircuitoVia = em.find(CircuitoVia.class, circuitoVia.getCircuitoViaPK());
//            Linea lineaOld = persistentCircuitoVia.getLinea();
//            Linea lineaNew = circuitoVia.getLinea();
//            if (lineaNew != null) {
//                lineaNew = em.getReference(lineaNew.getClass(), lineaNew.getIdLinea());
//                circuitoVia.setLinea(lineaNew);
//            }
            circuitoVia = em.merge(circuitoVia);
//            if (lineaOld != null && !lineaOld.equals(lineaNew)) {
//                lineaOld.getCircuitoViaList().remove(circuitoVia);
//                lineaOld = em.merge(lineaOld);
//            }
//            if (lineaNew != null && !lineaNew.equals(lineaOld)) {
//                lineaNew.getCircuitoViaList().add(circuitoVia);
//                lineaNew = em.merge(lineaNew);
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                CircuitoViaPK id = circuitoVia.getCircuitoViaPK();
                if (findCircuitoVia(id) == null) {
                    throw new NonexistentEntityException("The circuitoVia with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(CircuitoViaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CircuitoVia circuitoVia;
            try {
                circuitoVia = em.getReference(CircuitoVia.class, id);
                circuitoVia.getCircuitoViaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The circuitoVia with id " + id + " no longer exists.", enfe);
            }
//            Linea linea = circuitoVia.getLinea();
//            if (linea != null) {
//                linea.getCircuitoViaList().remove(circuitoVia);
//                linea = em.merge(linea);
//            }
            em.remove(circuitoVia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CircuitoVia> findCircuitoViaEntities() {
        return findCircuitoViaEntities(true, -1, -1);
    }

    public List<CircuitoVia> findCircuitoViaEntities(int maxResults, int firstResult) {
        return findCircuitoViaEntities(false, maxResults, firstResult);
    }

    private List<CircuitoVia> findCircuitoViaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CircuitoVia.class));
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

    public CircuitoVia findCircuitoVia(CircuitoViaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CircuitoVia.class, id);
        } finally {
            em.close();
        }
    }

    public int getCircuitoViaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CircuitoVia> rt = cq.from(CircuitoVia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
     public List<CircuitoVia> buscarCircuitoVia(int idLinea) {
       EntityManager em=getEntityManager();
        try {
            
            TypedQuery<CircuitoVia> cq=em.createNamedQuery("CircuitoVia.findByIdLinea",CircuitoVia.class);
            cq.setParameter("idLinea", idLinea);
             return cq.getResultList();
           } finally {
            em.close();
        }   
    }
     public CircuitoVia buscarCircuitoViaPK(int idLinea, double idPkInicialCircuito) {
       EntityManager em=getEntityManager();
        try {
            
            TypedQuery<CircuitoVia> cq=em.createNamedQuery("CircuitoVia.findByCircuitoViaPK",CircuitoVia.class);
            cq.setParameter("idLinea", idLinea);
            cq.setParameter("idPkInicialCircuito", idPkInicialCircuito);
             return cq.getSingleResult();
           } finally {
            em.close();
        }   
    }
    
}
