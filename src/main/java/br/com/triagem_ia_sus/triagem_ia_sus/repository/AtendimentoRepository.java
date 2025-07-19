package br.com.triagem_ia_sus.triagem_ia_sus.repository;

import br.com.triagem_ia_sus.triagem_ia_sus.domain.Atendimento;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AtendimentoRepository extends MongoRepository<Atendimento, String> {
    Optional<Atendimento> findById(String id);
}