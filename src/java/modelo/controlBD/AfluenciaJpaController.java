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
import modelo.entity.Afluencia;
import modelo.entity.AfluenciaPK;
import modelo.entity.Linea;

/**
 *
 * @author seront
 */
public class AfluenciaJpaController implements Serializable {

    public AfluenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Afluencia afluencia) throws PreexistingEntityException, Exception {
        if (afluencia.getAfluenciaPK() == null) {
            afluencia.setAfluenciaPK(new AfluenciaPK());
        }
        afluencia.getAfluenciaPK().setIdLinea(afluencia.getLinea().getIdLinea());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Linea linea = afluencia.getLinea();
            if (linea != null) {
                linea = em.getReference(linea.getClass(), linea.getIdLinea());
                afluencia.setLinea(linea);
            }
            em.persist(afluencia);
            if (linea != null) {
                linea.getAfluenciaList().add(afluencia);
                linea = em.merge(linea);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAfluencia(afluencia.getAfluenciaPK()) != null) {
                throw new PreexistingEntityException("Afluencia " + afluencia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Afluencia afluencia) throws NonexistentEntityException, Exception {
        afluencia.getAfluenciaPK().setIdLinea(afluencia.getLinea().getIdLinea());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
//            Afluencia persistentAfluencia = em.find(Afluencia.class, afluencia.getAfluenciaPK());
//            Linea lineaOld = persistentAfluencia.getLinea();
//            Linea lineaNew = afluencia.getLinea();
//            if (lineaNew != null) {
//                lineaNew = em.getReference(lineaNew.getClass(), lineaNew.getIdLinea());
//                afluencia.setLinea(lineaNew);
//            }
            afluencia = em.merge(afluencia);
//            if (lineaOld != null && !lineaOld.equals(lineaNew)) {
//                lineaOld.getAfluenciaList().remove(afluencia);
//                lineaOld = em.merge(lineaOld);
//            }
//            if (lineaNew != null && !lineaNew.equals(lineaOld)) {
//                lineaNew.getAfluenciaList().add(afluencia);
//                lineaNew = em.merge(lineaNew);
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                AfluenciaPK id = afluencia.getAfluenciaPK();
                if (findAfluencia(id) == null) {
                    throw new NonexistentEntityException("The afluencia with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(AfluenciaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Afluencia afluencia;
            try {
                afluencia = em.getReference(Afluencia.class, id);
                afluencia.getAfluenciaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The afluencia with id " + id + " no longer exists.", enfe);
            }
//            Linea linea = afluencia.getLinea();
//            if (linea != null) {
//                linea.getAfluenciaList().remove(afluencia);
//                linea = em.merge(linea);
//            }
            em.remove(afluencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Afluencia> findAfluenciaEntities() {
        return findAfluenciaEntities(true, -1, -1);
    }

    public List<Afluencia> findAfluenciaEntities(int maxResults, int firstResult) {
        return findAfluenciaEntities(false, maxResults, firstResult);
    }

    private List<Afluencia> findAfluenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Afluencia.class));
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

    public Afluencia findAfluencia(AfluenciaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Afluencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getAfluenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Afluencia> rt = cq.from(Afluencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Afluencia> afluenciaDiaria(int idLinea, double idPkEstacion, double fechaMinima, double fechaMaxima) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Afluencia> cq = em.createNamedQuery("Afluencia.listaPorDia", Afluencia.class);
            cq.setParameter("idLinea",idLinea );
            cq.setParameter("idPkEstacion",idPkEstacion);
            cq.setParameter("fechaMinima",fechaMinima);
            cq.setParameter("fechaMaxima",fechaMaxima);
            return cq.getResultList();
        } finally {
            em.close();
        }
    }
    public List<Afluencia> afluenciaEstacion(int idLinea, double idPkEstacion) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Afluencia> cq = em.createNamedQuery("Afluencia.findByIdLineaYIdPkEstacion", Afluencia.class);
            cq.setParameter("idLinea",idLinea );
            cq.setParameter("idPkEstacion",idPkEstacion);
            return cq.getResultList();
        } finally {
            em.close();
        }
    }
    
}
