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
import modelo.entity.CurvaEsfuerzo;
import modelo.entity.CurvaEsfuerzoPK;
import modelo.entity.MaterialRodante;

/**
 *
 * @author Kelvins Insua
 */
public class CurvaEsfuerzoJpaController implements Serializable {

    public CurvaEsfuerzoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CurvaEsfuerzo curvaEsfuerzo) throws PreexistingEntityException, Exception {
        if (curvaEsfuerzo.getCurvaEsfuerzoPK() == null) {
            curvaEsfuerzo.setCurvaEsfuerzoPK(new CurvaEsfuerzoPK());
        }
        curvaEsfuerzo.getCurvaEsfuerzoPK().setIdMaterialRodante(curvaEsfuerzo.getMaterialRodante().getIdMaterialRodante());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MaterialRodante materialRodante = curvaEsfuerzo.getMaterialRodante();
            if (materialRodante != null) {
                materialRodante = em.getReference(materialRodante.getClass(), materialRodante.getIdMaterialRodante());
                curvaEsfuerzo.setMaterialRodante(materialRodante);
            }
            em.persist(curvaEsfuerzo);
            if (materialRodante != null) {
                materialRodante.getCurvaEsfuerzoList().add(curvaEsfuerzo);
                materialRodante = em.merge(materialRodante);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCurvaEsfuerzo(curvaEsfuerzo.getCurvaEsfuerzoPK()) != null) {
                throw new PreexistingEntityException("CurvaEsfuerzo " + curvaEsfuerzo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CurvaEsfuerzo curvaEsfuerzo) throws NonexistentEntityException, Exception {
        curvaEsfuerzo.getCurvaEsfuerzoPK().setIdMaterialRodante(curvaEsfuerzo.getMaterialRodante().getIdMaterialRodante());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
//            CurvaEsfuerzo persistentCurvaEsfuerzo = em.find(CurvaEsfuerzo.class, curvaEsfuerzo.getCurvaEsfuerzoPK());
//            MaterialRodante materialRodanteOld = persistentCurvaEsfuerzo.getMaterialRodante();
//            MaterialRodante materialRodanteNew = curvaEsfuerzo.getMaterialRodante();
//            if (materialRodanteNew != null) {
//                materialRodanteNew = em.getReference(materialRodanteNew.getClass(), materialRodanteNew.getIdMaterialRodante());
//                curvaEsfuerzo.setMaterialRodante(materialRodanteNew);
//            }
            curvaEsfuerzo = em.merge(curvaEsfuerzo);
//            if (materialRodanteOld != null && !materialRodanteOld.equals(materialRodanteNew)) {
//                materialRodanteOld.getCurvaEsfuerzoList().remove(curvaEsfuerzo);
//                materialRodanteOld = em.merge(materialRodanteOld);
//            }
//            if (materialRodanteNew != null && !materialRodanteNew.equals(materialRodanteOld)) {
//                materialRodanteNew.getCurvaEsfuerzoList().add(curvaEsfuerzo);
//                materialRodanteNew = em.merge(materialRodanteNew);
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                CurvaEsfuerzoPK id = curvaEsfuerzo.getCurvaEsfuerzoPK();
                if (findCurvaEsfuerzo(id) == null) {
                    throw new NonexistentEntityException("The curvaEsfuerzo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(CurvaEsfuerzoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CurvaEsfuerzo curvaEsfuerzo;
            try {
                curvaEsfuerzo = em.getReference(CurvaEsfuerzo.class, id);
                curvaEsfuerzo.getCurvaEsfuerzoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The curvaEsfuerzo with id " + id + " no longer exists.", enfe);
            }
//            MaterialRodante materialRodante = curvaEsfuerzo.getMaterialRodante();
//            if (materialRodante != null) {
//                materialRodante.getCurvaEsfuerzoList().remove(curvaEsfuerzo);
//                materialRodante = em.merge(materialRodante);
//            }
            em.remove(curvaEsfuerzo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CurvaEsfuerzo> findCurvaEsfuerzoEntities() {
        return findCurvaEsfuerzoEntities(true, -1, -1);
    }

    public List<CurvaEsfuerzo> findCurvaEsfuerzoEntities(int maxResults, int firstResult) {
        return findCurvaEsfuerzoEntities(false, maxResults, firstResult);
    }

    private List<CurvaEsfuerzo> findCurvaEsfuerzoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CurvaEsfuerzo.class));
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

    public CurvaEsfuerzo findCurvaEsfuerzo(CurvaEsfuerzoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CurvaEsfuerzo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCurvaEsfuerzoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CurvaEsfuerzo> rt = cq.from(CurvaEsfuerzo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
     public List<CurvaEsfuerzo> curvaDelMaterialRodante(Integer idMaterialRodante) {
        EntityManager em=getEntityManager();
        try {
            
            TypedQuery<CurvaEsfuerzo> cq=em.createNamedQuery("CurvaEsfuerzo.findByIdMaterialRodante",CurvaEsfuerzo.class);
            cq.setParameter("idMaterialRodante", idMaterialRodante);
             return cq.getResultList();
           } finally {
            em.close();
        }
    }
    
}
