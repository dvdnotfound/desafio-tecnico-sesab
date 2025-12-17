package br.com.teleconsulta.service;

import br.com.teleconsulta.dao.ReservaDAO;
import br.com.teleconsulta.model.Reserva;

import java.time.LocalDateTime;
import java.util.List;

public class ReservaService {
    
    private final ReservaDAO reservaDAO;
    
    public ReservaService() {
        this.reservaDAO = new ReservaDAO();
    }
    
    /**
     * MÉTODO PRINCIPAL DO DESAFIO:
     * Cria uma reserva validando conflitos de horário
     */
    public void criarReserva(Reserva reserva) {
        validarReserva(reserva);
        validarConflitos(reserva);
        reservaDAO.save(reserva);
    }
    
    public void salvar(Reserva reserva) {
        validarReserva(reserva);
        validarConflitos(reserva);
        
        if (reserva.getId() == null) {
            reservaDAO.save(reserva);
        } else {
            reservaDAO.update(reserva);
        }
    }
    
    public void atualizar(Reserva reserva) {
        validarReserva(reserva);
        validarConflitos(reserva);
        reservaDAO.update(reserva);
    }
    
    public void deletar(Long id) {
        reservaDAO.delete(id);
    }
    
    /**
     * MÉTODO DO DESAFIO: Cancelar reserva
     */
    public void cancelarReserva(Long id) {
        Reserva reserva = reservaDAO.findById(id);
        if (reserva == null) {
            throw new IllegalArgumentException("Reserva não encontrada");
        }
        
        if (!reserva.getAtiva()) {
            throw new IllegalArgumentException("Reserva já está cancelada");
        }
        
        reservaDAO.cancelar(id);
    }
    
    public Reserva buscarPorId(Long id) {
        return reservaDAO.findById(id);
    }
    
    public List<Reserva> listarTodos() {
        return reservaDAO.findAll();
    }
    
    public List<Reserva> buscarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return reservaDAO.findByPeriodo(dataInicio, dataFim);
    }
    
    public List<Reserva> buscarPorUsuarioId(Long usuarioId) {
        return reservaDAO.findByUsuarioId(usuarioId);
    }
    
    public long contar() {
        return reservaDAO.count();
    }
    
    /**
     * Valida os dados básicos da reserva
     */
    private void validarReserva(Reserva reserva) {
        if (reserva.getDataHoraInicio() == null) {
            throw new IllegalArgumentException("Data/hora de início é obrigatória");
        }
        
        if (reserva.getDataHoraFim() == null) {
            throw new IllegalArgumentException("Data/hora de término é obrigatória");
        }
        
        if (reserva.getSala() == null) {
            throw new IllegalArgumentException("Sala é obrigatória");
        }
        
        if (reserva.getUsuario() == null) {
            throw new IllegalArgumentException("Usuário é obrigatório");
        }
        
        // Validar lógica de datas
        if (reserva.getDataHoraInicio().isAfter(reserva.getDataHoraFim())) {
            throw new IllegalArgumentException("Data/hora de início deve ser anterior à data/hora de término");
        }
        
        if (reserva.getDataHoraInicio().isEqual(reserva.getDataHoraFim())) {
            throw new IllegalArgumentException("Data/hora de início não pode ser igual à data/hora de término");
        }
        
        // Validar se a reserva é no passado
        if (reserva.getDataHoraInicio().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Não é possível criar reserva para data/hora no passado");
        }
    }
    
    /**
     * VALIDAÇÃO CRÍTICA DO DESAFIO:
     * Verifica se há conflitos de horário para a sala
     * 
     * Usa o método findConflitos do DAO que implementa Criteria API
     */
    private void validarConflitos(Reserva reserva) {
        List<Reserva> conflitos = reservaDAO.findConflitos(
            reserva.getSala().getId(),
            reserva.getDataHoraInicio(),
            reserva.getDataHoraFim(),
            reserva.getId() // Para excluir a própria reserva em caso de edição
        );
        
        if (!conflitos.isEmpty()) {
            StringBuilder mensagem = new StringBuilder("Conflito de horário detectado! A sala já possui reserva(s) no período:\n");
            
            for (Reserva conflito : conflitos) {
                mensagem.append(String.format("- De %s até %s (Usuário: %s)\n",
                    conflito.getDataHoraInicio(),
                    conflito.getDataHoraFim(),
                    conflito.getUsuario().getNome()
                ));
            }
            
            throw new IllegalArgumentException(mensagem.toString());
        }
    }
    
    /**
     * Verifica se há conflito sem lançar exceção
     * Útil para validações na UI
     */
    public boolean temConflito(Long salaId, LocalDateTime dataHoraInicio, 
                              LocalDateTime dataHoraFim, Long reservaIdExcluir) {
        List<Reserva> conflitos = reservaDAO.findConflitos(
            salaId, dataHoraInicio, dataHoraFim, reservaIdExcluir
        );
        return !conflitos.isEmpty();
    }
}
