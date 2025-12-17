package br.com.teleconsulta.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "reservas")
public class Reserva implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Data/hora de início é obrigatória")
    @Column(name = "data_hora_inicio", nullable = false)
    private LocalDateTime dataHoraInicio;
    
    @NotNull(message = "Data/hora de término é obrigatória")
    @Column(name = "data_hora_fim", nullable = false)
    private LocalDateTime dataHoraFim;
    
    @NotNull(message = "Sala é obrigatória")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;
    
    @NotNull(message = "Usuário é obrigatório")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    @Column(name = "ativa", nullable = false)
    private Boolean ativa = true;
    
    public Reserva() {
    }
    
    public Reserva(LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, Sala sala, Usuario usuario) {
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.sala = sala;
        this.usuario = usuario;
        this.ativa = true;
    }
    
    // Getters e Setters
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }
    
    public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }
    
    public LocalDateTime getDataHoraFim() {
        return dataHoraFim;
    }
    
    public void setDataHoraFim(LocalDateTime dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }
    
    public Sala getSala() {
        return sala;
    }
    
    public void setSala(Sala sala) {
        this.sala = sala;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public Boolean getAtiva() {
        return ativa;
    }
    
    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return Objects.equals(id, reserva.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", dataHoraInicio=" + dataHoraInicio +
                ", dataHoraFim=" + dataHoraFim +
                ", ativa=" + ativa +
                '}';
    }
}
