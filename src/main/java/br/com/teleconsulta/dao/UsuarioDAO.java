package br.com.teleconsulta.dao;

import br.com.teleconsulta.model.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO extends GenericDAO<Usuario> {
    
    public UsuarioDAO() {
        super(Usuario.class);
    }
    
    /**
     * Busca usuário por CPF usando Criteria API
     */
    public Usuario findByCpf(String cpf) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
            Root<Usuario> root = cq.from(Usuario.class);
            
            cq.select(root).where(cb.equal(root.get("cpf"), cpf));
            
            List<Usuario> result = em.createQuery(cq).getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }
    
    /**
     * Busca usuário por email usando Criteria API
     */
    public Usuario findByEmail(String email) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
            Root<Usuario> root = cq.from(Usuario.class);
            
            cq.select(root).where(cb.equal(root.get("email"), email));
            
            List<Usuario> result = em.createQuery(cq).getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }
    
    /**
     * Busca usuários por período de cadastro usando Criteria API
     */
    public List<Usuario> findByPeriodoCadastro(LocalDateTime dataInicio, LocalDateTime dataFim) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
            Root<Usuario> root = cq.from(Usuario.class);
            
            List<Predicate> predicates = new ArrayList<>();
            
            if (dataInicio != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dataCadastro"), dataInicio));
            }
            
            if (dataFim != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dataCadastro"), dataFim));
            }
            
            cq.select(root).where(predicates.toArray(new Predicate[0]));
            cq.orderBy(cb.desc(root.get("dataCadastro")));
            
            return em.createQuery(cq).getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Busca usuários por nome (like) usando Criteria API
     */
    public List<Usuario> findByNome(String nome) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
            Root<Usuario> root = cq.from(Usuario.class);
            
            cq.select(root).where(cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%"));
            cq.orderBy(cb.asc(root.get("nome")));
            
            return em.createQuery(cq).getResultList();
        } finally {
            em.close();
        }
    }
}
