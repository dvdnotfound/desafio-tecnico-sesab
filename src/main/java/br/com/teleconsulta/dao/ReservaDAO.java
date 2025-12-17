package br.com.teleconsulta.dao;

import br.com.teleconsulta.model.Reserva;
import br.com.teleconsulta.model.Sala;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO extends GenericDAO<Reserva> {
    
    public ReservaDAO() {
        super(Reserva.class);
    }
    
    /**
     * CRITERIA API - Verifica conflito de horário para uma sala
     * Esta é a consulta OBRIGATÓRIA usando Criteria API do desafio
     * 
     * Lógica: Duas reservas conflitam se:
     * - São para a mesma sala
     * - Estão ativas
     * - Os períodos se sobrepõem
     * 
     * Sobreposição ocorre quando:
     * (dataInicio < reserva.dataFim) AND (dataFim > reserva.dataInicio)
     */
    public List<Reserva> findConflitos(Long salaId, LocalDateTime dataHoraInicio, 
                                       LocalDateTime dataHoraFim, Long reservaIdExcluir) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Reserva> cq = cb.createQuery(Reserva.class);
            Root<Reserva> root = cq.from(Reserva.class);
            
            List<Predicate> predicates = new ArrayList<>();
            
            // Mesma sala
            predicates.add(cb.equal(root.get("sala").get("id"), salaId));
            
            // Reserva ativa
            predicates.add(cb.isTrue(root.get("ativa")));
            
            // Sobreposição de horário
            Predicate overlap = cb.and(
                cb.lessThan(root.get("dataHoraInicio"), dataHoraFim),
                cb.greaterThan(root.get("dataHoraFim"), dataHoraInicio)
            );
            predicates.add(overlap);
            
            // Excluir a própria reserva (para edição)
            if (reservaIdExcluir != null) {
                predicates.add(cb.notEqual(root.get("id"), reservaIdExcluir));
            }
            
            cq.select(root).where(predicates.toArray(new Predicate[0]));
            
            return em.createQuery(cq).getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * CRITERIA API - Busca reservas ativas por sala
     */
    public List<Reserva> findBySala(Sala sala) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Reserva> cq = cb.createQuery(Reserva.class);
            Root<Reserva> root = cq.from(Reserva.class);
            
            cq.select(root).where(
                cb.and(
                    cb.equal(root.get("sala"), sala),
                    cb.isTrue(root.get("ativa"))
                )
            );
            cq.orderBy(cb.desc(root.get("dataHoraInicio")));
            
            return em.createQuery(cq).getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * CRITERIA API - Busca reservas por período
     */
    public List<Reserva> findByPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Reserva> cq = cb.createQuery(Reserva.class);
            Root<Reserva> root = cq.from(Reserva.class);
            
            List<Predicate> predicates = new ArrayList<>();
            
            predicates.add(cb.isTrue(root.get("ativa")));
            
            if (dataInicio != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dataHoraInicio"), dataInicio));
            }
            
            if (dataFim != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dataHoraFim"), dataFim));
            }
            
            cq.select(root).where(predicates.toArray(new Predicate[0]));
            cq.orderBy(cb.asc(root.get("dataHoraInicio")));
            
            return em.createQuery(cq).getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * CRITERIA API - Busca reservas ativas por usuário
     */
    public List<Reserva> findByUsuarioId(Long usuarioId) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Reserva> cq = cb.createQuery(Reserva.class);
            Root<Reserva> root = cq.from(Reserva.class);
            
            cq.select(root).where(
                cb.and(
                    cb.equal(root.get("usuario").get("id"), usuarioId),
                    cb.isTrue(root.get("ativa"))
                )
            );
            cq.orderBy(cb.desc(root.get("dataHoraInicio")));
            
            return em.createQuery(cq).getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Cancela uma reserva (soft delete)
     */
    public void cancelar(Long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Reserva reserva = em.find(Reserva.class, id);
            if (reserva != null) {
                reserva.setAtiva(false);
                em.merge(reserva);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao cancelar reserva: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}
