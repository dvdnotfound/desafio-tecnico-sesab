package br.com.teleconsulta.dao;

import br.com.teleconsulta.model.Paciente;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class PacienteDAO extends GenericDAO<Paciente> {
    
    public PacienteDAO() {
        super(Paciente.class);
    }
    
    /**
     * Busca paciente por CPF usando Criteria API
     */
    public Paciente findByCpf(String cpf) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Paciente> cq = cb.createQuery(Paciente.class);
            Root<Paciente> root = cq.from(Paciente.class);
            
            cq.select(root).where(cb.equal(root.get("cpf"), cpf));
            
            List<Paciente> result = em.createQuery(cq).getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }
    
    /**
     * Busca paciente por CNS usando Criteria API
     */
    public Paciente findByCns(String cns) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Paciente> cq = cb.createQuery(Paciente.class);
            Root<Paciente> root = cq.from(Paciente.class);
            
            cq.select(root).where(cb.equal(root.get("cns"), cns));
            
            List<Paciente> result = em.createQuery(cq).getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }
    
    /**
     * Busca pacientes por nome (like) usando Criteria API
     */
    public List<Paciente> findByNome(String nome) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Paciente> cq = cb.createQuery(Paciente.class);
            Root<Paciente> root = cq.from(Paciente.class);
            
            cq.select(root).where(cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%"));
            cq.orderBy(cb.asc(root.get("nome")));
            
            return em.createQuery(cq).getResultList();
        } finally {
            em.close();
        }
    }
}
