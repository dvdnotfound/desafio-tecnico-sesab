package br.com.teleconsulta.dao;

import br.com.teleconsulta.model.Sala;
import br.com.teleconsulta.model.UnidadeSaude;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class SalaDAO extends GenericDAO<Sala> {
    
    public SalaDAO() {
        super(Sala.class);
    }
    
    /**
     * Busca salas por unidade de saúde usando Criteria API
     */
    public List<Sala> findByUnidadeSaude(UnidadeSaude unidadeSaude) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Sala> cq = cb.createQuery(Sala.class);
            Root<Sala> root = cq.from(Sala.class);
            
            cq.select(root).where(cb.equal(root.get("unidadeSaude"), unidadeSaude));
            cq.orderBy(cb.asc(root.get("nome")));
            
            return em.createQuery(cq).getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Busca salas por unidade de saúde ID usando Criteria API
     */
    public List<Sala> findByUnidadeSaudeId(Long unidadeSaudeId) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Sala> cq = cb.createQuery(Sala.class);
            Root<Sala> root = cq.from(Sala.class);
            
            cq.select(root).where(cb.equal(root.get("unidadeSaude").get("id"), unidadeSaudeId));
            cq.orderBy(cb.asc(root.get("nome")));
            
            return em.createQuery(cq).getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Busca salas por nome (like) usando Criteria API
     */
    public List<Sala> findByNome(String nome) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Sala> cq = cb.createQuery(Sala.class);
            Root<Sala> root = cq.from(Sala.class);
            
            cq.select(root).where(cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%"));
            cq.orderBy(cb.asc(root.get("nome")));
            
            return em.createQuery(cq).getResultList();
        } finally {
            em.close();
        }
    }
}
