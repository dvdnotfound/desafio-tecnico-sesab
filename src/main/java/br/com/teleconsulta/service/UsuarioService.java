package br.com.teleconsulta.service;

import br.com.teleconsulta.dao.UsuarioDAO;
import br.com.teleconsulta.model.Usuario;

import java.time.LocalDateTime;
import java.util.List;

public class UsuarioService {
    
    private final UsuarioDAO usuarioDAO;
    
    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }
    
    public void salvar(Usuario usuario) {
        validarUsuario(usuario);
        
        if (usuario.getId() == null) {
            usuarioDAO.save(usuario);
        } else {
            usuarioDAO.update(usuario);
        }
    }
    
    public void atualizar(Usuario usuario) {
        validarUsuario(usuario);
        usuarioDAO.update(usuario);
    }
    
    public void deletar(Long id) {
        usuarioDAO.delete(id);
    }
    
    public Usuario buscarPorId(Long id) {
        return usuarioDAO.findById(id);
    }
    
    public List<Usuario> listarTodos() {
        return usuarioDAO.findAll();
    }
    
    public Usuario buscarPorCpf(String cpf) {
        return usuarioDAO.findByCpf(cpf);
    }
    
    public Usuario buscarPorEmail(String email) {
        return usuarioDAO.findByEmail(email);
    }
    
    public List<Usuario> buscarPorNome(String nome) {
        return usuarioDAO.findByNome(nome);
    }
    
    public List<Usuario> buscarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return usuarioDAO.findByPeriodoCadastro(dataInicio, dataFim);
    }
    
    public long contar() {
        return usuarioDAO.count();
    }
    
    private void validarUsuario(Usuario usuario) {
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("E-mail é obrigatório");
        }
        
        if (usuario.getCpf() == null || usuario.getCpf().trim().isEmpty()) {
            throw new IllegalArgumentException("CPF é obrigatório");
        }
        
        // Validar duplicidade de CPF
        Usuario usuarioExistente = usuarioDAO.findByCpf(usuario.getCpf());
        if (usuarioExistente != null && !usuarioExistente.getId().equals(usuario.getId())) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }
        
        // Validar duplicidade de email
        usuarioExistente = usuarioDAO.findByEmail(usuario.getEmail());
        if (usuarioExistente != null && !usuarioExistente.getId().equals(usuario.getId())) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }
    }
}
