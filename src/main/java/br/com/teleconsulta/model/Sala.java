package br.com.teleconsulta.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "salas")
public class Sala implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nome;
    
    @NotNull(message = "Capacidade é obrigatória")
    @Min(value = 1, message = "Capacidade deve ser no mínimo 1")
    @Column(nullable = false)
    private Integer capacidade;
    
    @NotNull(message = "Unidade de Saúde é obrigatória")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "unidade_saude_id", nullable = false)
    private UnidadeSaude unidadeSaude;
    
    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas = new ArrayList<>();
    
    public Sala() {
    }
    
    public Sala(String nome, Integer capacidade, UnidadeSaude unidadeSaude) {
        this.nome = nome;
        this.capacidade = capacidade;
        this.unidadeSaude = unidadeSaude;
    }
    
    // Getters e Setters
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public Integer getCapacidade() {
        return capacidade;
    }
    
    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }
    
    public UnidadeSaude getUnidadeSaude() {
        return unidadeSaude;
    }
    
    public void setUnidadeSaude(UnidadeSaude unidadeSaude) {
        this.unidadeSaude = unidadeSaude;
    }
    
    public List<Reserva> getReservas() {
        return reservas;
    }
    
    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sala sala = (Sala) o;
        return Objects.equals(id, sala.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Sala{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", capacidade=" + capacidade +
                '}';
    }
}
