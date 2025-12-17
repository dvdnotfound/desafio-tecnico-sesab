package br.com.teleconsulta.dao;

import br.com.teleconsulta.model.UnidadeSaude;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UnidadeSaudeDAO extends GenericDAO<UnidadeSaude> {
    
    public UnidadeSaudeDAO() {
        super(UnidadeSaude.class);
    }
    
    /**
     * Busca unidade por CNPJ usando Criteria API
     */
    public UnidadeSaude findByCnpj(String cnpj) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<UnidadeSaude> cq = cb.createQuery(UnidadeSaude.class);
            Root<UnidadeSaude> root = cq.from(UnidadeSaude.class);
            
            cq.select(root).where(cb.equal(root.get("cnpj"), cnpj));
            
            List<UnidadeSaude> result = em.createQuery(cq).getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }
    
    /**
     * Busca unidades por nome (like) usando Criteria API
     */
    public List<UnidadeSaude> findByNome(String nome) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<UnidadeSaude> cq = cb.createQuery(UnidadeSaude.class);
            Root<UnidadeSaude> root = cq.from(UnidadeSaude.class);
            
            cq.select(root).where(cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%"));
            cq.orderBy(cb.asc(root.get("nome")));
            
            return em.createQuery(cq).getResultList();
        } finally {
            em.close();
        }
    }
}
