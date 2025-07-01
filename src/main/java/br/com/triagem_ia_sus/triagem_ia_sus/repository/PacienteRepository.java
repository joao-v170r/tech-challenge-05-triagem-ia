package br.com.triagem_ia_sus.triagem_ia_sus.repository;

import br.com.triagem_ia_sus.triagem_ia_sus.domain.Paciente;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PacienteRepository extends MongoRepository<Paciente, String> {
    public Optional<Paciente> findByCpf(String cpf);
}
