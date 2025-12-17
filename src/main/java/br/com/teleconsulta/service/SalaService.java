package br.com.teleconsulta.service;

import br.com.teleconsulta.dao.ReservaDAO;
import br.com.teleconsulta.dao.SalaDAO;
import br.com.teleconsulta.model.Reserva;
import br.com.teleconsulta.model.Sala;
import br.com.teleconsulta.model.UnidadeSaude;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SalaService {
    
    private final SalaDAO salaDAO;
    private final ReservaDAO reservaDAO;
    
    public SalaService() {
        this.salaDAO = new SalaDAO();
        this.reservaDAO = new ReservaDAO();
    }
    
    public void salvar(Sala sala) {
        validarSala(sala);
        
        if (sala.getId() == null) {
            salaDAO.save(sala);
        } else {
            salaDAO.update(sala);
        }
    }
    
    public void atualizar(Sala sala) {
        validarSala(sala);
        salaDAO.update(sala);
    }
    
    public void deletar(Long id) {
        salaDAO.delete(id);
    }
    
    public Sala buscarPorId(Long id) {
        return salaDAO.findById(id);
    }
    
    public List<Sala> listarTodos() {
        return salaDAO.findAll();
    }
    
    public List<Sala> buscarPorUnidadeSaude(UnidadeSaude unidadeSaude) {
        return salaDAO.findByUnidadeSaude(unidadeSaude);
    }
    
    public List<Sala> buscarPorUnidadeSaudeId(Long unidadeSaudeId) {
        return salaDAO.findByUnidadeSaudeId(unidadeSaudeId);
    }
    
    public List<Sala> buscarPorNome(String nome) {
        return salaDAO.findByNome(nome);
    }
    
    public long contar() {
        return salaDAO.count();
    }
    
    /**
     * MÉTODO PRINCIPAL DO DESAFIO:
     * Lista salas disponíveis por unidade e período
     * 
     * Uma sala está disponível se não possui reservas ativas
     * que conflitem com o período solicitado
     */
    public List<Sala> listarDisponibilidade(Long unidadeSaudeId, 
                                           LocalDateTime dataHoraInicio, 
                                           LocalDateTime dataHoraFim) {
        
        if (unidadeSaudeId == null) {
            throw new IllegalArgumentException("Unidade de Saúde é obrigatória");
        }
        
        if (dataHoraInicio == null || dataHoraFim == null) {
            throw new IllegalArgumentException("Período é obrigatório");
        }
        
        if (dataHoraInicio.isAfter(dataHoraFim) || dataHoraInicio.isEqual(dataHoraFim)) {
            throw new IllegalArgumentException("Data/hora início deve ser anterior à data/hora fim");
        }
        
        // Buscar todas as salas da unidade
        List<Sala> todasSalas = salaDAO.findByUnidadeSaudeId(unidadeSaudeId);
        List<Sala> salasDisponiveis = new ArrayList<>();
        
        // Para cada sala, verificar se há conflitos
        for (Sala sala : todasSalas) {
            List<Reserva> conflitos = reservaDAO.findConflitos(
                sala.getId(), 
                dataHoraInicio, 
                dataHoraFim, 
                null
            );
            
            // Se não há conflitos, a sala está disponível
            if (conflitos.isEmpty()) {
                salasDisponiveis.add(sala);
            }
        }
        
        return salasDisponiveis;
    }
    
    /**
     * Verifica se uma sala específica está disponível em um período
     */
    public boolean verificarDisponibilidade(Long salaId, 
                                           LocalDateTime dataHoraInicio, 
                                           LocalDateTime dataHoraFim) {
        List<Reserva> conflitos = reservaDAO.findConflitos(salaId, dataHoraInicio, dataHoraFim, null);
        return conflitos.isEmpty();
    }
    
    private void validarSala(Sala sala) {
        if (sala.getNome() == null || sala.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        
        if (sala.getCapacidade() == null || sala.getCapacidade() < 1) {
            throw new IllegalArgumentException("Capacidade deve ser no mínimo 1");
        }
        
        if (sala.getUnidadeSaude() == null) {
            throw new IllegalArgumentException("Unidade de Saúde é obrigatória");
        }
    }
}
