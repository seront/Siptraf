/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.controlBD;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.entity.ProgramacionHoraria;
import modelo.entity.PreCirculacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import modelo.controlBD.exceptions.IllegalOrphanException;
import modelo.controlBD.exceptions.NonexistentEntityException;
import modelo.controlBD.exceptions.PreexistingEntityException;
import modelo.entity.Ruta;
import modelo.entity.RutaPK;

/**
 *
 * @author Kelvins Insua
 */
public class RutaJpaController implements Serializable {

    public RutaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ruta ruta) throws PreexistingEntityException, Exception {
        if (ruta.getRutaPK() == null) {
            ruta.setRutaPK(new RutaPK());
        }
        if (ruta.getPreCirculacionList() == null) {
            ruta.setPreCirculacionList(new ArrayList<PreCirculacion>());
        }
        ruta.getRutaPK().setIdProgramacionHoraria(ruta.getProgramacionHoraria().getIdProgramacionHoraria());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProgramacionHoraria programacionHoraria = ruta.getProgramacionHoraria();
            if (programacionHoraria != null) {
                programacionHoraria = em.getReference(programacionHoraria.getClass(), programacionHoraria.getIdProgramacionHoraria());
                ruta.setProgramacionHoraria(programacionHoraria);
            }
            List<PreCirculacion> attachedPreCirculacionList = new ArrayList<PreCirculacion>();
            for (PreCirculacion preCirculacionListPreCirculacionToAttach : ruta.getPreCirculacionList()) {
                preCirculacionListPreCirculacionToAttach = em.getReference(preCirculacionListPreCirculacionToAttach.getClass(), preCirculacionListPreCirculacionToAttach.getPreCirculacionPK());
                attachedPreCirculacionList.add(preCirculacionListPreCirculacionToAttach);
            }
            ruta.setPreCirculacionList(attachedPreCirculacionList);
            em.persist(ruta);
            if (programacionHoraria != null) {
                programacionHoraria.getRutaList().add(ruta);
                programacionHoraria = em.merge(programacionHoraria);
            }
            for (PreCirculacion preCirculacionListPreCirculacion : ruta.getPreCirculacionList()) {
                Ruta oldRutaOfPreCirculacionListPreCirculacion = preCirculacionListPreCirculacion.getRuta();
                preCirculacionListPreCirculacion.setRuta(ruta);
                preCirculacionListPreCirculacion = em.merge(preCirculacionListPreCirculacion);
                if (oldRutaOfPreCirculacionListPreCirculacion != null) {
                    oldRutaOfPreCirculacionListPreCirculacion.getPreCirculacionList().remove(preCirculacionListPreCirculacion);
                    oldRutaOfPreCirculacionListPreCirculacion = em.merge(oldRutaOfPreCirculacionListPreCirculacion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRuta(ruta.getRutaPK()) != null) {
                throw new PreexistingEntityException("Ruta " + ruta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ruta ruta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        ruta.getRutaPK().setIdProgramacionHoraria(ruta.getProgramacionHoraria().getIdProgramacionHoraria());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
//            Ruta persistentRuta = em.find(Ruta.class, ruta.getRutaPK());
//            ProgramacionHoraria programacionHorariaOld = persistentRuta.getProgramacionHoraria();
//            ProgramacionHoraria programacionHorariaNew = ruta.getProgramacionHoraria();
//            List<PreCirculacion> preCirculacionListOld = persistentRuta.getPreCirculacionList();
//            List<PreCirculacion> preCirculacionListNew = ruta.getPreCirculacionList();
//            List<String> illegalOrphanMessages = null;
//            for (PreCirculacion preCirculacionListOldPreCirculacion : preCirculacionListOld) {
//                if (!preCirculacionListNew.contains(preCirculacionListOldPreCirculacion)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain PreCirculacion " + preCirculacionListOldPreCirculacion + " since its ruta field is not nullable.");
//                }
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            if (programacionHorariaNew != null) {
//                programacionHorariaNew = em.getReference(programacionHorariaNew.getClass(), programacionHorariaNew.getIdProgramacionHoraria());
//                ruta.setProgramacionHoraria(programacionHorariaNew);
//            }
//            List<PreCirculacion> attachedPreCirculacionListNew = new ArrayList<PreCirculacion>();
//            for (PreCirculacion preCirculacionListNewPreCirculacionToAttach : preCirculacionListNew) {
//                preCirculacionListNewPreCirculacionToAttach = em.getReference(preCirculacionListNewPreCirculacionToAttach.getClass(), preCirculacionListNewPreCirculacionToAttach.getPreCirculacionPK());
//                attachedPreCirculacionListNew.add(preCirculacionListNewPreCirculacionToAttach);
//            }
//            preCirculacionListNew = attachedPreCirculacionListNew;
//            ruta.setPreCirculacionList(preCirculacionListNew);
            ruta = em.merge(ruta);
//            if (programacionHorariaOld != null && !programacionHorariaOld.equals(programacionHorariaNew)) {
//                programacionHorariaOld.getRutaList().remove(ruta);
//                programacionHorariaOld = em.merge(programacionHorariaOld);
//            }
//            if (programacionHorariaNew != null && !programacionHorariaNew.equals(programacionHorariaOld)) {
//                programacionHorariaNew.getRutaList().add(ruta);
//                programacionHorariaNew = em.merge(programacionHorariaNew);
//            }
//            for (PreCirculacion preCirculacionListNewPreCirculacion : preCirculacionListNew) {
//                if (!preCirculacionListOld.contains(preCirculacionListNewPreCirculacion)) {
//                    Ruta oldRutaOfPreCirculacionListNewPreCirculacion = preCirculacionListNewPreCirculacion.getRuta();
//                    preCirculacionListNewPreCirculacion.setRuta(ruta);
//                    preCirculacionListNewPreCirculacion = em.merge(preCirculacionListNewPreCirculacion);
//                    if (oldRutaOfPreCirculacionListNewPreCirculacion != null && !oldRutaOfPreCirculacionListNewPreCirculacion.equals(ruta)) {
//                        oldRutaOfPreCirculacionListNewPreCirculacion.getPreCirculacionList().remove(preCirculacionListNewPreCirculacion);
//                        oldRutaOfPreCirculacionListNewPreCirculacion = em.merge(oldRutaOfPreCirculacionListNewPreCirculacion);
//                    }
//                }
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                RutaPK id = ruta.getRutaPK();
                if (findRuta(id) == null) {
                    throw new NonexistentEntityException("The ruta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(RutaPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ruta ruta;
            try {
                ruta = em.getReference(Ruta.class, id);
                ruta.getRutaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ruta with id " + id + " no longer exists.", enfe);
            }
//            List<String> illegalOrphanMessages = null;
//            List<PreCirculacion> preCirculacionListOrphanCheck = ruta.getPreCirculacionList();
//            for (PreCirculacion preCirculacionListOrphanCheckPreCirculacion : preCirculacionListOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Ruta (" + ruta + ") cannot be destroyed since the PreCirculacion " + preCirculacionListOrphanCheckPreCirculacion + " in its preCirculacionList field has a non-nullable ruta field.");
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            ProgramacionHoraria programacionHoraria = ruta.getProgramacionHoraria();
//            if (programacionHoraria != null) {
//                programacionHoraria.getRutaList().remove(ruta);
//                programacionHoraria = em.merge(programacionHoraria);
//            }
            em.remove(ruta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ruta> findRutaEntities() {
        return findRutaEntities(true, -1, -1);
    }

    public List<Ruta> findRutaEntities(int maxResults, int firstResult) {
        return findRutaEntities(false, maxResults, firstResult);
    }

    private List<Ruta> findRutaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ruta.class));
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

    public Ruta findRuta(RutaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ruta.class, id);
        } finally {
            em.close();
        }
    }

    public int getRutaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ruta> rt = cq.from(Ruta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
      public List<Ruta> buscarRutaPorPH(int idPH) {
        EntityManager em=getEntityManager();
        try {
            
            TypedQuery<Ruta> cq=em.createNamedQuery("Ruta.findByIdProgramacionHoraria",Ruta.class);
            cq.setParameter("idProgramacionHoraria", idPH);
             return cq.getResultList();
           } finally {
            em.close();
        }  
      
        
    }
     public List<Integer> listarId() {
       EntityManager em = getEntityManager();
        try {
            TypedQuery<Integer> cq = em.createNamedQuery("ProgramacionHoraria.listarId", Integer.class);
            return cq.getResultList();
        } finally {
            em.close();
        }
    }
}
