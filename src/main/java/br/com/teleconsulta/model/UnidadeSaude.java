package br.com.teleconsulta.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "unidades_saude")
public class UnidadeSaude implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nome;
    
    @NotBlank(message = "Razão social é obrigatória")
    @Size(min = 3, max = 150, message = "Razão social deve ter entre 3 e 150 caracteres")
    @Column(name = "razao_social", nullable = false, length = 150)
    private String razaoSocial;
    
    @Size(max = 20, message = "Sigla deve ter no máximo 20 caracteres")
    @Column(length = 20)
    private String sigla;
    
    @NotBlank(message = "CNPJ é obrigatório")
    @Size(min = 14, max = 18, message = "CNPJ inválido")
    @Column(nullable = false, unique = true, length = 18)
    private String cnpj;
    
    @Size(max = 15, message = "CNES deve ter no máximo 15 caracteres")
    @Column(length = 15)
    private String cnes;
    
    @OneToMany(mappedBy = "unidadeSaude", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sala> salas = new ArrayList<>();
    
    public UnidadeSaude() {
    }
    
    public UnidadeSaude(String nome, String razaoSocial, String cnpj) {
        this.nome = nome;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
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
    
    public String getRazaoSocial() {
        return razaoSocial;
    }
    
    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }
    
    public String getSigla() {
        return sigla;
    }
    
    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
    
    public String getCnpj() {
        return cnpj;
    }
    
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
    public String getCnes() {
        return cnes;
    }
    
    public void setCnes(String cnes) {
        this.cnes = cnes;
    }
    
    public List<Sala> getSalas() {
        return salas;
    }
    
    public void setSalas(List<Sala> salas) {
        this.salas = salas;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnidadeSaude that = (UnidadeSaude) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "UnidadeSaude{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cnpj='" + cnpj + '\'' +
                '}';
    }
}
