package com.api.leituraservidores.repositories;

import com.api.leituraservidores.models.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, UUID> {

    boolean existsByEmail (String email);
    boolean existsBySenha (String senha);
    Optional<UsuarioModel> findByEmailAndSenha (String email, String senha);

}
