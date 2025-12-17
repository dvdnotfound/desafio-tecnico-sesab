package br.com.teleconsulta.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nome;
    
    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    
    @NotBlank(message = "CPF é obrigatório")
    @Size(min = 11, max = 14, message = "CPF inválido")
    @Column(nullable = false, unique = true, length = 14)
    private String cpf;
    
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas = new ArrayList<>();
    
    public Usuario() {
        this.dataCadastro = LocalDateTime.now();
    }
    
    public Usuario(String nome, String email, String cpf) {
        this();
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
    }
    
    @PrePersist
    protected void onCreate() {
        if (dataCadastro == null) {
            dataCadastro = LocalDateTime.now();
        }
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
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
    
    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
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
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", cpf='" + cpf + '\'' +
                '}';
    }
}
