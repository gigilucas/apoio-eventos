package com.eventos.apoio.controller;

import com.eventos.apoio.dto.JwtResponse;
import com.eventos.apoio.dto.LoginDTO;
import com.eventos.apoio.dto.RegisterDTO;
import com.eventos.apoio.model.Participante;
import com.eventos.apoio.model.Role;
import com.eventos.apoio.repository.ParticipanteRepository;
import com.eventos.apoio.security.JwtTokenProvider;
import com.eventos.apoio.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para autenticação e registro de utilizadores.
 * Fornece endpoints para login e registro de novos utilizadores com JWT.
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "API para autenticação e registro de usuários")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final ParticipanteRepository participanteRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    /**
     * Construtor com injeção de dependências.
     * @param authenticationManager Gerenciador de autenticação do Spring Security.
     * @param participanteRepository Repositório de participantes.
     * @param passwordEncoder Codificador de senhas.
     * @param tokenProvider Provedor de tokens JWT.
     */
    public AuthController(AuthenticationManager authenticationManager,
                         ParticipanteRepository participanteRepository,
                         PasswordEncoder passwordEncoder,
                         JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.participanteRepository = participanteRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    /**
     * Endpoint para autenticação de utilizador.
     * Autentica o utilizador com email e senha e retorna um token JWT.
     * @param loginDTO DTO com email e senha do utilizador.
     * @return ResponseEntity com token JWT e informações do utilizador.
     */
    @PostMapping("/login")
    @Operation(summary = "Login do usuário", description = "Autentica o usuário e retorna um token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                )
        );

        String jwt = tokenProvider.generateToken(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt, "Bearer", userPrincipal.getId(), userPrincipal.getEmail()));
    }

    /**
     * Endpoint para registro de novo utilizador.
     * Registra um novo utilizador com role USER por padrão.
     * @param registerDTO DTO com dados do novo utilizador.
     * @return ResponseEntity com mensagem de sucesso ou erro.
     */
    @PostMapping("/register")
    @Operation(summary = "Registro de novo usuário", description = "Registra um novo usuário com role USER")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou email já existe")
    })
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterDTO registerDTO) {
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("As senhas não coincidem");
        }

        if (participanteRepository.existsByEmail(registerDTO.getEmail())) {
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }

        Participante participante = new Participante();
        participante.setNome(registerDTO.getNome());
        participante.setEmail(registerDTO.getEmail());
        participante.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        participante.setTelefone(registerDTO.getTelefone());
        participante.setNumeroEstudante(registerDTO.getNumeroEstudante());
        participante.setCurso(registerDTO.getCurso());
        participante.setRole(Role.USER);

        Participante saved = participanteRepository.save(participante);

        return ResponseEntity.ok("Usuário registrado com sucesso");
    }
}
