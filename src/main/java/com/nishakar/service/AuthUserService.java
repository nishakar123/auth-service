package com.nishakar.service;

import com.nishakar.commons.dto.AuthUserDTO;
import com.nishakar.commons.exception.TokenGenerationFailedException;
import com.nishakar.commons.exception.UserExistsException;
import com.nishakar.entity.AuthUser;
import com.nishakar.mapper.AuthUserMapper;
import com.nishakar.repository.AuthUserRepository;
import com.nishakar.service.jwt.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@AllArgsConstructor
@Service
public class AuthUserService {

    private final AuthUserMapper authUserMapper;

    private final AuthUserRepository authUserRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public AuthUser save(AuthUserDTO authUserDTO){
        log.info("AuthUserService -> save !");
        AuthUser authUser = authUserMapper.map(authUserDTO);
        authUser.setPassword(passwordEncoder.encode(authUser.getPassword()));
        Optional<AuthUser> optionalAuthUser = authUserRepository.findByUsername(authUser.getUsername());
        optionalAuthUser.ifPresent(user -> {throw new UserExistsException("Username already exists : " + user.getUsername());});
        return authUserRepository.save(authUser);
    }

    public AuthUser update(AuthUserDTO authUserDTO){
        log.info("AuthUserService -> update !");
        AtomicReference<AuthUser> authUser = new AtomicReference<>(authUserMapper.map(authUserDTO));
        authUser.get().setPassword(passwordEncoder.encode(authUser.get().getPassword()));
        Optional<AuthUser> optionalAuthUser = authUserRepository.findByUsername(authUser.get().getUsername());
        optionalAuthUser.map(user -> {
            user.setPassword(passwordEncoder.encode(authUser.get().getPassword()));
            user.setRoles(authUser.get().getRoles());
            return user;
        }).ifPresentOrElse(
                value -> authUser.set(authUserRepository.save(value)),
                () -> authUser.set(authUserRepository.save(authUser.get()))
        );
        return authUser.get();
    }

    public List<AuthUser> getUsers(){
        log.info("AuthUserService -> getUser !");
        List<AuthUser> authUserList = authUserRepository.findAll();
        log.info("ALl auth users : {}", authUserList);
        return authUserList;
    }

    public String generateJwtToken(AuthUserDTO authUserDTO) throws Exception {
        log.info("AuthUserService -> generateJwtToken !");
        final Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authUserDTO.getUsername(), authUserDTO.getPassword()));
        String token;
        if (authenticate.isAuthenticated()) {
            token = jwtService.generateToken(authUserDTO.getUsername());
        }else {
            throw new TokenGenerationFailedException("Token Generation Failed");
        }
        return token;
    }
}
