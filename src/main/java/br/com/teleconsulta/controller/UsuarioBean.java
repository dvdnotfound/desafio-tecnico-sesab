package br.com.teleconsulta.controller;

import br.com.teleconsulta.model.Usuario;
import br.com.teleconsulta.service.UsuarioService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class UsuarioBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Usuario usuario;
    private List<Usuario> usuarios;
    private UsuarioService usuarioService;
    
    @PostConstruct
    public void init() {
        usuarioService = new UsuarioService();
        usuario = new Usuario();
        carregarUsuarios();
    }
    
    public void carregarUsuarios() {
        try {
            usuarios = usuarioService.listarTodos();
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro ao carregar usuários", e.getMessage());
        }
    }
    
    public void novo() {
        usuario = new Usuario();
    }
    
    public void salvar() {
        try {
            usuarioService.salvar(usuario);
            addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Usuário salvo com sucesso!");
            novo();
            carregarUsuarios();
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar", e.getMessage());
        }
    }
    
    public void editar(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public void excluir(Usuario usuario) {
        try {
            usuarioService.deletar(usuario.getId());
            addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Usuário excluído com sucesso!");
            carregarUsuarios();
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir", e.getMessage());
        }
    }
    
    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(severity, summary, detail));
    }
    
    // Getters e Setters
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public List<Usuario> getUsuarios() {
        return usuarios;
    }
    
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
