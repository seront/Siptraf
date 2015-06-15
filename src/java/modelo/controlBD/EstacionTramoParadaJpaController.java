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
import modelo.entity.EstacionTramoParada;
import modelo.entity.EstacionTramoParadaPK;
import modelo.entity.PreCirculacion;

/**
 *
 * @author Kelvins Insua
 */
public class EstacionTramoParadaJpaController implements Serializable {

    public EstacionTramoParadaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstacionTramoParada estacionTramoParada) throws PreexistingEntityException, Exception {
        if (estacionTramoParada.getEstacionTramoParadaPK() == null) {
            estacionTramoParada.setEstacionTramoParadaPK(new EstacionTramoParadaPK());
        }
        estacionTramoParada.getEstacionTramoParadaPK().setIdProgramacionHoraria(estacionTramoParada.getPreCirculacion().getPreCirculacionPK().getIdProgramacionHoraria());
        estacionTramoParada.getEstacionTramoParadaPK().setIdPreCirculacion(estacionTramoParada.getPreCirculacion().getPreCirculacionPK().getIdPreCirculacion());
        estacionTramoParada.getEstacionTramoParadaPK().setIdRuta(estacionTramoParada.getPreCirculacion().getPreCirculacionPK().getIdRuta());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PreCirculacion preCirculacion = estacionTramoParada.getPreCirculacion();
            if (preCirculacion != null) {
                preCirculacion = em.getReference(preCirculacion.getClass(), preCirculacion.getPreCirculacionPK());
                estacionTramoParada.setPreCirculacion(preCirculacion);
            }
            em.persist(estacionTramoParada);
            if (preCirculacion != null) {
                preCirculacion.getEstacionTramoParadaList().add(estacionTramoParada);
                preCirculacion = em.merge(preCirculacion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstacionTramoParada(estacionTramoParada.getEstacionTramoParadaPK()) != null) {
                throw new PreexistingEntityException("EstacionTramoParada " + estacionTramoParada + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EstacionTramoParada estacionTramoParada) throws NonexistentEntityException, Exception {
        estacionTramoParada.getEstacionTramoParadaPK().setIdProgramacionHoraria(estacionTramoParada.getPreCirculacion().getPreCirculacionPK().getIdProgramacionHoraria());
        estacionTramoParada.getEstacionTramoParadaPK().setIdPreCirculacion(estacionTramoParada.getPreCirculacion().getPreCirculacionPK().getIdPreCirculacion());
        estacionTramoParada.getEstacionTramoParadaPK().setIdRuta(estacionTramoParada.getPreCirculacion().getPreCirculacionPK().getIdRuta());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
//            EstacionTramoParada persistentEstacionTramoParada = em.find(EstacionTramoParada.class, estacionTramoParada.getEstacionTramoParadaPK());
//            PreCirculacion preCirculacionOld = persistentEstacionTramoParada.getPreCirculacion();
//            PreCirculacion preCirculacionNew = estacionTramoParada.getPreCirculacion();
//            if (preCirculacionNew != null) {
//                preCirculacionNew = em.getReference(preCirculacionNew.getClass(), preCirculacionNew.getPreCirculacionPK());
//                estacionTramoParada.setPreCirculacion(preCirculacionNew);
//            }
            estacionTramoParada = em.merge(estacionTramoParada);
//            if (preCirculacionOld != null && !preCirculacionOld.equals(preCirculacionNew)) {
//                preCirculacionOld.getEstacionTramoParadaList().remove(estacionTramoParada);
//                preCirculacionOld = em.merge(preCirculacionOld);
//            }
//            if (preCirculacionNew != null && !preCirculacionNew.equals(preCirculacionOld)) {
//                preCirculacionNew.getEstacionTramoParadaList().add(estacionTramoParada);
//                preCirculacionNew = em.merge(preCirculacionNew);
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                EstacionTramoParadaPK id = estacionTramoParada.getEstacionTramoParadaPK();
                if (findEstacionTramoParada(id) == null) {
                    throw new NonexistentEntityException("The estacionTramoParada with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(EstacionTramoParadaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstacionTramoParada estacionTramoParada;
            try {
                estacionTramoParada = em.getReference(EstacionTramoParada.class, id);
                estacionTramoParada.getEstacionTramoParadaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estacionTramoParada with id " + id + " no longer exists.", enfe);
            }
//            PreCirculacion preCirculacion = estacionTramoParada.getPreCirculacion();
//            if (preCirculacion != null) {
//                preCirculacion.getEstacionTramoParadaList().remove(estacionTramoParada);
//                preCirculacion = em.merge(preCirculacion);
//            }
            em.remove(estacionTramoParada);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EstacionTramoParada> findEstacionTramoParadaEntities() {
        return findEstacionTramoParadaEntities(true, -1, -1);
    }

    public List<EstacionTramoParada> findEstacionTramoParadaEntities(int maxResults, int firstResult) {
        return findEstacionTramoParadaEntities(false, maxResults, firstResult);
    }

    private List<EstacionTramoParada> findEstacionTramoParadaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstacionTramoParada.class));
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

    public EstacionTramoParada findEstacionTramoParada(EstacionTramoParadaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstacionTramoParada.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstacionTramoParadaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstacionTramoParada> rt = cq.from(EstacionTramoParada.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    public List<EstacionTramoParada> buscarTEPS(int idProgramacionHoraria, int idPreCirculacion) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<EstacionTramoParada> cq = em.createNamedQuery("EstacionTramoParada.findByIdProgramacionHoraria&&IdPreCirculacion", EstacionTramoParada.class);
            cq.setParameter("idProgramacionHoraria", idProgramacionHoraria);
            cq.setParameter("idPreCirculacion", idPreCirculacion);
            return cq.getResultList();
        } finally {
            em.close();
        }
    }
    public List<EstacionTramoParada> buscarTEPSPH(int idProgramacionHoraria) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<EstacionTramoParada> cq = em.createNamedQuery("EstacionTramoParada.findByIdProgramacionHoraria", EstacionTramoParada.class);
            cq.setParameter("idProgramacionHoraria", idProgramacionHoraria);            
            return cq.getResultList();
        } finally {
            em.close();
        }
    }
}
