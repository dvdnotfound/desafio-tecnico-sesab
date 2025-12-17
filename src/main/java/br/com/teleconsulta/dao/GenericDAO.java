package br.com.teleconsulta.dao;

import br.com.teleconsulta.config.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class GenericDAO<T> {
    
    private final Class<T> entityClass;
    
    public GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    protected EntityManager getEntityManager() {
        return JPAUtil.getEntityManager();
    }
    
    public void save(T entity) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao salvar entidade: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
    
    public void update(T entity) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao atualizar entidade: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
    
    public void delete(Long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            T entity = em.find(entityClass, id);
            if (entity != null) {
                em.remove(entity);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao deletar entidade: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
    
    public T findById(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(entityClass, id);
        } finally {
            em.close();
        }
    }
    
    public List<T> findAll() {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(entityClass);
            Root<T> root = cq.from(entityClass);
            cq.select(root);
            return em.createQuery(cq).getResultList();
        } finally {
            em.close();
        }
    }
    
    public long count() {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<T> root = cq.from(entityClass);
            cq.select(cb.count(root));
            return em.createQuery(cq).getSingleResult();
        } finally {
            em.close();
        }
    }
}
