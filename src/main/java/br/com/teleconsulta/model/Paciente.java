package br.com.teleconsulta.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "pacientes")
public class Paciente implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nome;
    
    @Column(name = "nome_social", length = 100)
    private String nomeSocial;
    
    @NotBlank(message = "Sexo é obrigatório")
    @Column(nullable = false, length = 20)
    private String sexo;
    
    @Column(name = "nome_mae", length = 100)
    private String nomeMae;
    
    @Column(name = "nome_pai", length = 100)
    private String nomePai;
    
    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    @Column(length = 20)
    private String telefone;
    
    @Email(message = "E-mail inválido")
    @Column(length = 100)
    private String email;
    
    @NotBlank(message = "CPF é obrigatório")
    @Size(min = 11, max = 14, message = "CPF inválido")
    @Column(nullable = false, unique = true, length = 14)
    private String cpf;
    
    @Size(max = 20, message = "RG deve ter no máximo 20 caracteres")
    @Column(length = 20)
    private String rg;
    
    @Size(max = 15, message = "CNS deve ter no máximo 15 caracteres")
    @Column(length = 15)
    private String cns;
    
    @NotNull(message = "Data de nascimento é obrigatória")
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;
    
    @Column(length = 255)
    private String endereco;
    
    public Paciente() {
    }
    
    public Paciente(String nome, String sexo, String cpf, LocalDate dataNascimento) {
        this.nome = nome;
        this.sexo = sexo;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
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
    
    public String getNomeSocial() {
        return nomeSocial;
    }
    
    public void setNomeSocial(String nomeSocial) {
        this.nomeSocial = nomeSocial;
    }
    
    public String getSexo() {
        return sexo;
    }
    
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    
    public String getNomeMae() {
        return nomeMae;
    }
    
    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }
    
    public String getNomePai() {
        return nomePai;
    }
    
    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }
    
    public String getTelefone() {
        return telefone;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
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
    
    public String getRg() {
        return rg;
    }
    
    public void setRg(String rg) {
        this.rg = rg;
    }
    
    public String getCns() {
        return cns;
    }
    
    public void setCns(String cns) {
        this.cns = cns;
    }
    
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
    public String getEndereco() {
        return endereco;
    }
    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paciente paciente = (Paciente) o;
        return Objects.equals(id, paciente.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Paciente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                '}';
    }
}
