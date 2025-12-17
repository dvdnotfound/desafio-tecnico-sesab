package br.com.teleconsulta.service;

import br.com.teleconsulta.dao.UnidadeSaudeDAO;
import br.com.teleconsulta.model.UnidadeSaude;

import java.util.List;

public class UnidadeSaudeService {
    
    private final UnidadeSaudeDAO unidadeSaudeDAO;
    
    public UnidadeSaudeService() {
        this.unidadeSaudeDAO = new UnidadeSaudeDAO();
    }
    
    public void salvar(UnidadeSaude unidadeSaude) {
        validarUnidadeSaude(unidadeSaude);
        
        if (unidadeSaude.getId() == null) {
            unidadeSaudeDAO.save(unidadeSaude);
        } else {
            unidadeSaudeDAO.update(unidadeSaude);
        }
    }
    
    public void atualizar(UnidadeSaude unidadeSaude) {
        validarUnidadeSaude(unidadeSaude);
        unidadeSaudeDAO.update(unidadeSaude);
    }
    
    public void deletar(Long id) {
        unidadeSaudeDAO.delete(id);
    }
    
    public UnidadeSaude buscarPorId(Long id) {
        return unidadeSaudeDAO.findById(id);
    }
    
    public List<UnidadeSaude> listarTodos() {
        return unidadeSaudeDAO.findAll();
    }
    
    public UnidadeSaude buscarPorCnpj(String cnpj) {
        return unidadeSaudeDAO.findByCnpj(cnpj);
    }
    
    public List<UnidadeSaude> buscarPorNome(String nome) {
        return unidadeSaudeDAO.findByNome(nome);
    }
    
    public long contar() {
        return unidadeSaudeDAO.count();
    }
    
    private void validarUnidadeSaude(UnidadeSaude unidadeSaude) {
        if (unidadeSaude.getNome() == null || unidadeSaude.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        
        if (unidadeSaude.getRazaoSocial() == null || unidadeSaude.getRazaoSocial().trim().isEmpty()) {
            throw new IllegalArgumentException("Razão social é obrigatória");
        }
        
        if (unidadeSaude.getCnpj() == null || unidadeSaude.getCnpj().trim().isEmpty()) {
            throw new IllegalArgumentException("CNPJ é obrigatório");
        }
        
        // Validar duplicidade de CNPJ
        UnidadeSaude unidadeExistente = unidadeSaudeDAO.findByCnpj(unidadeSaude.getCnpj());
        if (unidadeExistente != null && !unidadeExistente.getId().equals(unidadeSaude.getId())) {
            throw new IllegalArgumentException("CNPJ já cadastrado");
        }
    }
}
