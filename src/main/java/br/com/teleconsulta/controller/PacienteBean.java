package br.com.teleconsulta.controller;

import br.com.teleconsulta.model.Paciente;
import br.com.teleconsulta.service.PacienteService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class PacienteBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Paciente paciente;
    private List<Paciente> pacientes;
    private PacienteService pacienteService;
    
    // Opções para dropdown de sexo
    private final String[] sexoOptions = {"Masculino", "Feminino", "Outro"};
    
    @PostConstruct
    public void init() {
        pacienteService = new PacienteService();
        paciente = new Paciente();
        carregarPacientes();
    }
    
    public void carregarPacientes() {
        try {
            pacientes = pacienteService.listarTodos();
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro ao carregar pacientes", e.getMessage());
        }
    }
    
    public void novo() {
        paciente = new Paciente();
    }
    
    public void salvar() {
        try {
            pacienteService.salvar(paciente);
            addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Paciente salvo com sucesso!");
            novo();
            carregarPacientes();
            FacesContext.getCurrentInstance().getExternalContext()
                .getRequestMap().put("validationFailed", false);
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar", e.getMessage());
            FacesContext.getCurrentInstance().validationFailed();
        }
    }
    
    public void editar(Paciente paciente) {
        this.paciente = paciente;
    }
    
    public void excluir(Paciente paciente) {
        try {
            pacienteService.deletar(paciente.getId());
            addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Paciente excluído com sucesso!");
            carregarPacientes();
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir", e.getMessage());
        }
    }
    
    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(severity, summary, detail));
    }
    
    // Getters e Setters
    
    public Paciente getPaciente() {
        return paciente;
    }
    
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
    
    public List<Paciente> getPacientes() {
        return pacientes;
    }
    
    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }
    
    public String[] getSexoOptions() {
        return sexoOptions;
    }
}
