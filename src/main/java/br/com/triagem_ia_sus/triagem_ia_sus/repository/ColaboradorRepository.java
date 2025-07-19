package br.com.triagem_ia_sus.triagem_ia_sus.repository;

import br.com.triagem_ia_sus.triagem_ia_sus.domain.Colaborador;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails; // Pode ser removido se não for mais usado na interface

public interface ColaboradorRepository extends MongoRepository<Colaborador, String> {
    Colaborador findByEmail(String email);
}