package br.com.triagem_ia_sus.triagem_ia_sus.repository;

import br.com.triagem_ia_sus.triagem_ia_sus.domain.Colaborador;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface ColaboradorRepository extends MongoRepository<Colaborador, String> {
    Colaborador findByEmail(String email);
    UserDetails findByEmailWithDetails(String email);
}
