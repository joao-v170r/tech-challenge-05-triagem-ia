package br.com.triagem_ia_sus.triagem_ia_sus.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor // Para o Spring Data e deserialização
@AllArgsConstructor
@Document(collection = "tb_colaborador")
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Colaborador implements UserDetails {
    @Id
    @EqualsAndHashCode.Include
    private String id;
    private String nome;
    private LocalDate dataNascimento;
    @Indexed(unique = true)
    @EqualsAndHashCode.Include
    private String email;
    private String senhaHash;
    private TipoColaborador tipoColaborador;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.tipoColaborador.getRole().equals(Roles.ADMIN.name())) return List.of(
                new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER")
        );
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senhaHash;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}