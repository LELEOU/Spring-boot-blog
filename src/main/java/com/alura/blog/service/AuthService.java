package com.alura.blog.service;

import com.alura.blog.dto.AuthResponseDTO;
import com.alura.blog.dto.CadastroDTO;
import com.alura.blog.dto.LoginDTO;
import com.alura.blog.model.Usuario;
import com.alura.blog.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponseDTO cadastrar(CadastroDTO cadastroDTO) {
        if (usuarioService.emailJaExiste(cadastroDTO.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(cadastroDTO.getNome());
        usuario.setEmail(cadastroDTO.getEmail());
        usuario.setSenha(passwordEncoder.encode(cadastroDTO.getSenha()));

        usuario = usuarioService.salvar(usuario);

        String token = jwtUtil.generateToken(usuario);

        return new AuthResponseDTO(token, usuario.getId(), usuario.getNome(), usuario.getEmail());
    }

    public AuthResponseDTO login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDTO.getEmail(),
                loginDTO.getSenha()
            )
        );

        Usuario usuario = (Usuario) authentication.getPrincipal();
        String token = jwtUtil.generateToken(usuario);

        return new AuthResponseDTO(token, usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}
