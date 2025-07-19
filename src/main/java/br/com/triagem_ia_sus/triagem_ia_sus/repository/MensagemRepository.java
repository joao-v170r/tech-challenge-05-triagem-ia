package br.com.triagem_ia_sus.triagem_ia_sus.repository;

import br.com.triagem_ia_sus.triagem_ia_sus.domain.Atendimento;
import br.com.triagem_ia_sus.triagem_ia_sus.domain.Mensagem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MensagemRepository extends MongoRepository<Mensagem, String> {
    Optional<Mensagem> findById(String id);
    List<Mensagem> findByAtendimento(Atendimento atendimento);
    List<Mensagem> findByAtendimentoId(String atendimentoId);
}