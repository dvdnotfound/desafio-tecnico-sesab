package br.com.teleconsulta.controller;

import br.com.teleconsulta.model.UnidadeSaude;
import br.com.teleconsulta.service.UnidadeSaudeService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class UnidadeSaudeBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private UnidadeSaude unidadeSaude;
    private List<UnidadeSaude> unidadesSaude;
    private UnidadeSaudeService unidadeSaudeService;
    
    @PostConstruct
    public void init() {
        unidadeSaudeService = new UnidadeSaudeService();
        unidadeSaude = new UnidadeSaude();
        carregarUnidades();
    }
    
    public void carregarUnidades() {
        try {
            unidadesSaude = unidadeSaudeService.listarTodos();
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro ao carregar unidades", e.getMessage());
        }
    }
    
    public void novo() {
        unidadeSaude = new UnidadeSaude();
    }
    
    public void salvar() {
        try {
            unidadeSaudeService.salvar(unidadeSaude);
            addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Unidade de Saúde salva com sucesso!");
            novo();
            carregarUnidades();
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar", e.getMessage());
        }
    }
    
    public void editar(UnidadeSaude unidadeSaude) {
        this.unidadeSaude = unidadeSaude;
    }
    
    public void excluir(UnidadeSaude unidadeSaude) {
        try {
            unidadeSaudeService.deletar(unidadeSaude.getId());
            addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Unidade de Saúde excluída com sucesso!");
            carregarUnidades();
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir", e.getMessage());
        }
    }
    
    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(severity, summary, detail));
    }
    
    // Getters e Setters
    
    public UnidadeSaude getUnidadeSaude() {
        return unidadeSaude;
    }
    
    public void setUnidadeSaude(UnidadeSaude unidadeSaude) {
        this.unidadeSaude = unidadeSaude;
    }
    
    public List<UnidadeSaude> getUnidadesSaude() {
        return unidadesSaude;
    }
    
    public void setUnidadesSaude(List<UnidadeSaude> unidadesSaude) {
        this.unidadesSaude = unidadesSaude;
    }
}
