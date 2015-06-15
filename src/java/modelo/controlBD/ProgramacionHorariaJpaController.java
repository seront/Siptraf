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
import modelo.entity.Ruta;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import modelo.controlBD.exceptions.IllegalOrphanException;
import modelo.controlBD.exceptions.NonexistentEntityException;
import modelo.controlBD.exceptions.PreexistingEntityException;
import modelo.entity.ProgramacionHoraria;

/**
 *
 * @author Kelvins Insua
 */
public class ProgramacionHorariaJpaController implements Serializable {

    public ProgramacionHorariaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProgramacionHoraria programacionHoraria) throws PreexistingEntityException, Exception {
        if (programacionHoraria.getRutaList() == null) {
            programacionHoraria.setRutaList(new ArrayList<Ruta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Ruta> attachedRutaList = new ArrayList<Ruta>();
            for (Ruta rutaListRutaToAttach : programacionHoraria.getRutaList()) {
                rutaListRutaToAttach = em.getReference(rutaListRutaToAttach.getClass(), rutaListRutaToAttach.getRutaPK());
                attachedRutaList.add(rutaListRutaToAttach);
            }
            programacionHoraria.setRutaList(attachedRutaList);
            em.persist(programacionHoraria);
            for (Ruta rutaListRuta : programacionHoraria.getRutaList()) {
                ProgramacionHoraria oldProgramacionHorariaOfRutaListRuta = rutaListRuta.getProgramacionHoraria();
                rutaListRuta.setProgramacionHoraria(programacionHoraria);
                rutaListRuta = em.merge(rutaListRuta);
                if (oldProgramacionHorariaOfRutaListRuta != null) {
                    oldProgramacionHorariaOfRutaListRuta.getRutaList().remove(rutaListRuta);
                    oldProgramacionHorariaOfRutaListRuta = em.merge(oldProgramacionHorariaOfRutaListRuta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProgramacionHoraria(programacionHoraria.getIdProgramacionHoraria()) != null) {
                throw new PreexistingEntityException("ProgramacionHoraria " + programacionHoraria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProgramacionHoraria programacionHoraria) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
//            ProgramacionHoraria persistentProgramacionHoraria = em.find(ProgramacionHoraria.class, programacionHoraria.getIdProgramacionHoraria());
//            List<Ruta> rutaListOld = persistentProgramacionHoraria.getRutaList();
//            List<Ruta> rutaListNew = programacionHoraria.getRutaList();
//            List<String> illegalOrphanMessages = null;
//            for (Ruta rutaListOldRuta : rutaListOld) {
//                if (!rutaListNew.contains(rutaListOldRuta)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain Ruta " + rutaListOldRuta + " since its programacionHoraria field is not nullable.");
//                }
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            List<Ruta> attachedRutaListNew = new ArrayList<Ruta>();
//            for (Ruta rutaListNewRutaToAttach : rutaListNew) {
//                rutaListNewRutaToAttach = em.getReference(rutaListNewRutaToAttach.getClass(), rutaListNewRutaToAttach.getRutaPK());
//                attachedRutaListNew.add(rutaListNewRutaToAttach);
//            }
//            rutaListNew = attachedRutaListNew;
//            programacionHoraria.setRutaList(rutaListNew);
            programacionHoraria = em.merge(programacionHoraria);
//            for (Ruta rutaListNewRuta : rutaListNew) {
//                if (!rutaListOld.contains(rutaListNewRuta)) {
//                    ProgramacionHoraria oldProgramacionHorariaOfRutaListNewRuta = rutaListNewRuta.getProgramacionHoraria();
//                    rutaListNewRuta.setProgramacionHoraria(programacionHoraria);
//                    rutaListNewRuta = em.merge(rutaListNewRuta);
//                    if (oldProgramacionHorariaOfRutaListNewRuta != null && !oldProgramacionHorariaOfRutaListNewRuta.equals(programacionHoraria)) {
//                        oldProgramacionHorariaOfRutaListNewRuta.getRutaList().remove(rutaListNewRuta);
//                        oldProgramacionHorariaOfRutaListNewRuta = em.merge(oldProgramacionHorariaOfRutaListNewRuta);
//                    }
//                }
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = programacionHoraria.getIdProgramacionHoraria();
                if (findProgramacionHoraria(id) == null) {
                    throw new NonexistentEntityException("The programacionHoraria with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProgramacionHoraria programacionHoraria;
            try {
                programacionHoraria = em.getReference(ProgramacionHoraria.class, id);
                programacionHoraria.getIdProgramacionHoraria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The programacionHoraria with id " + id + " no longer exists.", enfe);
            }
//            List<String> illegalOrphanMessages = null;
//            List<Ruta> rutaListOrphanCheck = programacionHoraria.getRutaList();
//            for (Ruta rutaListOrphanCheckRuta : rutaListOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This ProgramacionHoraria (" + programacionHoraria + ") cannot be destroyed since the Ruta " + rutaListOrphanCheckRuta + " in its rutaList field has a non-nullable programacionHoraria field.");
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
            em.remove(programacionHoraria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProgramacionHoraria> findProgramacionHorariaEntities() {
        return findProgramacionHorariaEntities(true, -1, -1);
    }

    public List<ProgramacionHoraria> findProgramacionHorariaEntities(int maxResults, int firstResult) {
        return findProgramacionHorariaEntities(false, maxResults, firstResult);
    }

    private List<ProgramacionHoraria> findProgramacionHorariaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProgramacionHoraria.class));
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

    public ProgramacionHoraria findProgramacionHoraria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProgramacionHoraria.class, id);
        } finally {
            em.close();
        }
    }

    public int getProgramacionHorariaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProgramacionHoraria> rt = cq.from(ProgramacionHoraria.class);
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
            TypedQuery<Integer> cq = em.createNamedQuery("ProgramacionHoraria.listarId", Integer.class);
            return cq.getResultList();
        } finally {
            em.close();
        }
    }
}
