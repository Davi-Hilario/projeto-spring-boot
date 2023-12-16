package com.api.leituraservidores.controllers;

import com.api.leituraservidores.dtos.UsuarioDto;
import com.api.leituraservidores.models.UsuarioModel;
import com.api.leituraservidores.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/usuario")
public class UsuarioController {

    final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String index() {
        return "Olá, Mundo";
    }

    @GetMapping("/login/{login}/{senha}")
    public ResponseEntity<Object> login(@PathVariable String login, @PathVariable String senha) {

        Optional<UsuarioModel> usuarioModelOptional = usuarioService.consultarUsuario(login, senha);

        return usuarioModelOptional.<ResponseEntity<Object>>map(usuarioModel ->
                ResponseEntity.status(HttpStatus.OK).body(usuarioModel)).orElseGet(() ->
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!"));
    }

    @PostMapping("/inserirUsuario")
    public ResponseEntity<Object> inserirUsuario (@RequestBody @Valid UsuarioDto usuarioDto) {

        if (usuarioService.existsByEmail(usuarioDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: já existe um usuário com este email!");
        }
        if (usuarioService.existsBySenha(usuarioDto.getSenha())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: esta senha está indisponível!");
        }

        var usuarioModel = new UsuarioModel();
        BeanUtils.copyProperties(usuarioDto, usuarioModel);
        usuarioModel.setAtivo(false);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.inserirUsuario(usuarioModel));
    }
}
