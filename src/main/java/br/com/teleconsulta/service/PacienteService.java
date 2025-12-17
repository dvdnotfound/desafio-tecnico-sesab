package br.com.teleconsulta.service;

import br.com.teleconsulta.dao.PacienteDAO;
import br.com.teleconsulta.model.Paciente;

import java.util.List;

public class PacienteService {
    
    private final PacienteDAO pacienteDAO;
    
    public PacienteService() {
        this.pacienteDAO = new PacienteDAO();
    }
    
    public void salvar(Paciente paciente) {
        validarPaciente(paciente);
        
        if (paciente.getId() == null) {
            pacienteDAO.save(paciente);
        } else {
            pacienteDAO.update(paciente);
        }
    }
    
    public void atualizar(Paciente paciente) {
        validarPaciente(paciente);
        pacienteDAO.update(paciente);
    }
    
    public void deletar(Long id) {
        pacienteDAO.delete(id);
    }
    
    public Paciente buscarPorId(Long id) {
        return pacienteDAO.findById(id);
    }
    
    public List<Paciente> listarTodos() {
        return pacienteDAO.findAll();
    }
    
    public Paciente buscarPorCpf(String cpf) {
        return pacienteDAO.findByCpf(cpf);
    }
    
    public Paciente buscarPorCns(String cns) {
        return pacienteDAO.findByCns(cns);
    }
    
    public List<Paciente> buscarPorNome(String nome) {
        return pacienteDAO.findByNome(nome);
    }
    
    public long contar() {
        return pacienteDAO.count();
    }
    
    private void validarPaciente(Paciente paciente) {
        if (paciente.getNome() == null || paciente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        
        if (paciente.getSexo() == null || paciente.getSexo().trim().isEmpty()) {
            throw new IllegalArgumentException("Sexo é obrigatório");
        }
        
        if (paciente.getCpf() == null || paciente.getCpf().trim().isEmpty()) {
            throw new IllegalArgumentException("CPF é obrigatório");
        }
        
        if (paciente.getDataNascimento() == null) {
            throw new IllegalArgumentException("Data de nascimento é obrigatória");
        }
        
        // Validar duplicidade de CPF
        Paciente pacienteExistente = pacienteDAO.findByCpf(paciente.getCpf());
        if (pacienteExistente != null && !pacienteExistente.getId().equals(paciente.getId())) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }
        
        // Validar duplicidade de CNS se informado
        if (paciente.getCns() != null && !paciente.getCns().trim().isEmpty()) {
            pacienteExistente = pacienteDAO.findByCns(paciente.getCns());
            if (pacienteExistente != null && !pacienteExistente.getId().equals(paciente.getId())) {
                throw new IllegalArgumentException("CNS já cadastrado");
            }
        }
    }
}
