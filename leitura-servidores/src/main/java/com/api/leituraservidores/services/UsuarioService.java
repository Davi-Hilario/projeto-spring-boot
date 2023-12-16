package com.api.leituraservidores.services;

import com.api.leituraservidores.models.UsuarioModel;
import com.api.leituraservidores.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Optional<UsuarioModel> consultarUsuario (String email, String senha) {
        return usuarioRepository.findByEmailAndSenha(email, senha);
    }

    @Transactional
    public UsuarioModel inserirUsuario(UsuarioModel usuarioModel) {
        return usuarioRepository.save(usuarioModel);
    }

    public Boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Boolean existsBySenha(String senha) {
        return usuarioRepository.existsBySenha(senha);
    }
}
