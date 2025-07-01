package br.com.triagem_ia_sus.triagem_ia_sus.repository;

import br.com.triagem_ia_sus.triagem_ia_sus.domain.Atendimento;
import br.com.triagem_ia_sus.triagem_ia_sus.domain.Paciente;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AtendimentoRepository extends MongoRepository<Atendimento, String> {
    List<Atendimento> findByPaciente(Paciente paciente);
    List<Atendimento> findByPacienteId(String pacienteId);
}
