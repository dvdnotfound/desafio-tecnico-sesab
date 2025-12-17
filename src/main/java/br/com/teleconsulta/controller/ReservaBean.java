package br.com.teleconsulta.controller;

import br.com.teleconsulta.model.Reserva;
import br.com.teleconsulta.model.Sala;
import br.com.teleconsulta.model.Usuario;
import br.com.teleconsulta.service.ReservaService;
import br.com.teleconsulta.service.SalaService;
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
public class ReservaBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Reserva reserva;
    private List<Reserva> reservas;
    private List<Sala> salas;
    private List<Usuario> usuarios;
    
    private ReservaService reservaService;
    private SalaService salaService;
    private UsuarioService usuarioService;
    
    @PostConstruct
    public void init() {
        reservaService = new ReservaService();
        salaService = new SalaService();
        usuarioService = new UsuarioService();
        
        reserva = new Reserva();
        carregarReservas();
        carregarSalas();
        carregarUsuarios();
    }
    
    public void carregarReservas() {
        try {
            reservas = reservaService.listarTodos();
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro ao carregar reservas", e.getMessage());
        }
    }
    
    public void carregarSalas() {
        try {
            salas = salaService.listarTodos();
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro ao carregar salas", e.getMessage());
        }
    }
    
    public void carregarUsuarios() {
        try {
            usuarios = usuarioService.listarTodos();
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro ao carregar usuários", e.getMessage());
        }
    }
    
    public void novo() {
        reserva = new Reserva();
    }
    
    /**
     * MÉTODO DO DESAFIO: Criar reserva com validação de conflitos
     */
    public void salvar() {
        try {
            reservaService.salvar(reserva);
            addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Reserva criada com sucesso!");
            novo();
            carregarReservas();
        } catch (IllegalArgumentException e) {
            // Erros de validação (incluindo conflitos)
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro de validação", e.getMessage());
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar", e.getMessage());
        }
    }
    
    public void editar(Reserva reserva) {
        this.reserva = reserva;
    }
    
    public void excluir(Reserva reserva) {
        try {
            reservaService.deletar(reserva.getId());
            addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Reserva excluída com sucesso!");
            carregarReservas();
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir", e.getMessage());
        }
    }
    
    /**
     * MÉTODO DO DESAFIO: Cancelar reserva
     */
    public void cancelar(Reserva reserva) {
        try {
            reservaService.cancelarReserva(reserva.getId());
            addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Reserva cancelada com sucesso!");
            carregarReservas();
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro ao cancelar", e.getMessage());
        }
    }
    
    /**
     * Retorna o nome da unidade da sala da reserva
     */
    public String getNomeUnidadeSala(Reserva reserva) {
        if (reserva != null && reserva.getSala() != null && 
            reserva.getSala().getUnidadeSaude() != null) {
            return reserva.getSala().getUnidadeSaude().getNome();
        }
        return "-";
    }
    
    /**
     * Retorna o status da reserva (Ativa/Cancelada)
     */
    public String getStatusReserva(Reserva reserva) {
        return reserva.getAtiva() ? "Ativa" : "Cancelada";
    }
    
    /**
     * Retorna a classe CSS para o status
     */
    public String getStatusClass(Reserva reserva) {
        return reserva.getAtiva() ? "status-ativa" : "status-cancelada";
    }
    
    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(severity, summary, detail));
    }
    
    // Getters e Setters
    
    public Reserva getReserva() {
        return reserva;
    }
    
    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }
    
    public List<Reserva> getReservas() {
        return reservas;
    }
    
    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }
    
    public List<Sala> getSalas() {
        return salas;
    }
    
    public void setSalas(List<Sala> salas) {
        this.salas = salas;
    }
    
    public List<Usuario> getUsuarios() {
        return usuarios;
    }
    
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
