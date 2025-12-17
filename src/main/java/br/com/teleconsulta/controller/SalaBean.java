package br.com.teleconsulta.controller;

import br.com.teleconsulta.model.Sala;
import br.com.teleconsulta.model.UnidadeSaude;
import br.com.teleconsulta.service.SalaService;
import br.com.teleconsulta.service.UnidadeSaudeService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Named
@ViewScoped
public class SalaBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Sala sala;
    private List<Sala> salas;
    private List<UnidadeSaude> unidadesSaude;
    private SalaService salaService;
    private UnidadeSaudeService unidadeSaudeService;
    
    // Para consulta de disponibilidade
    private Long unidadeSaudeIdConsulta;
    private LocalDateTime dataHoraInicioConsulta;
    private LocalDateTime dataHoraFimConsulta;
    private List<Sala> salasDisponiveis;
    
    @PostConstruct
    public void init() {
        salaService = new SalaService();
        unidadeSaudeService = new UnidadeSaudeService();
        sala = new Sala();
        carregarSalas();
        carregarUnidades();
    }
    
    public void carregarSalas() {
        try {
            salas = salaService.listarTodos();
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro ao carregar salas", e.getMessage());
        }
    }
    
    public void carregarUnidades() {
        try {
            unidadesSaude = unidadeSaudeService.listarTodos();
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro ao carregar unidades", e.getMessage());
        }
    }
    
    public void novo() {
        sala = new Sala();
    }
    
    public void salvar() {
        try {
            salaService.salvar(sala);
            addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Sala salva com sucesso!");
            novo();
            carregarSalas();
            FacesContext.getCurrentInstance().getExternalContext()
                .getRequestMap().put("validationFailed", false);
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar", e.getMessage());
            FacesContext.getCurrentInstance().validationFailed();
        }
    }
    
    public void editar(Sala sala) {
        this.sala = sala;
    }
    
    public void excluir(Sala sala) {
        try {
            salaService.deletar(sala.getId());
            addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Sala excluída com sucesso!");
            carregarSalas();
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir", e.getMessage());
        }
    }
    
    /**
     * MÉTODO DO DESAFIO: Consultar disponibilidade de salas
     */
    public void consultarDisponibilidade() {
        try {
            if (unidadeSaudeIdConsulta == null) {
                addMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Selecione uma unidade de saúde");
                return;
            }
            
            if (dataHoraInicioConsulta == null || dataHoraFimConsulta == null) {
                addMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Informe o período desejado");
                return;
            }
            
            salasDisponiveis = salaService.listarDisponibilidade(
                unidadeSaudeIdConsulta, 
                dataHoraInicioConsulta, 
                dataHoraFimConsulta
            );
            
            if (salasDisponiveis.isEmpty()) {
                addMessage(FacesMessage.SEVERITY_INFO, "Consulta realizada", 
                    "Nenhuma sala disponível no período informado");
            } else {
                addMessage(FacesMessage.SEVERITY_INFO, "Consulta realizada", 
                    salasDisponiveis.size() + " sala(s) disponível(is) encontrada(s)");
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro ao consultar", e.getMessage());
        }
    }
    
    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(severity, summary, detail));
    }
    
    // Getters e Setters
    
    public Sala getSala() {
        return sala;
    }
    
    public void setSala(Sala sala) {
        this.sala = sala;
    }
    
    public List<Sala> getSalas() {
        return salas;
    }
    
    public void setSalas(List<Sala> salas) {
        this.salas = salas;
    }
    
    public List<UnidadeSaude> getUnidadesSaude() {
        return unidadesSaude;
    }
    
    public void setUnidadesSaude(List<UnidadeSaude> unidadesSaude) {
        this.unidadesSaude = unidadesSaude;
    }
    
    public Long getUnidadeSaudeIdConsulta() {
        return unidadeSaudeIdConsulta;
    }
    
    public void setUnidadeSaudeIdConsulta(Long unidadeSaudeIdConsulta) {
        this.unidadeSaudeIdConsulta = unidadeSaudeIdConsulta;
    }
    
    public LocalDateTime getDataHoraInicioConsulta() {
        return dataHoraInicioConsulta;
    }
    
    public void setDataHoraInicioConsulta(LocalDateTime dataHoraInicioConsulta) {
        this.dataHoraInicioConsulta = dataHoraInicioConsulta;
    }
    
    public LocalDateTime getDataHoraFimConsulta() {
        return dataHoraFimConsulta;
    }
    
    public void setDataHoraFimConsulta(LocalDateTime dataHoraFimConsulta) {
        this.dataHoraFimConsulta = dataHoraFimConsulta;
    }
    
    public List<Sala> getSalasDisponiveis() {
        return salasDisponiveis;
    }
    
    public void setSalasDisponiveis(List<Sala> salasDisponiveis) {
        this.salasDisponiveis = salasDisponiveis;
    }
}
