package br.com.triagem_ia_sus.triagem_ia_sus.repository;

import br.com.triagem_ia_sus.triagem_ia_sus.domain.Colaborador;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ColaboradorRepository extends MongoRepository<Colaborador, String> {
    public Colaborador findByEmail(String email);
}
