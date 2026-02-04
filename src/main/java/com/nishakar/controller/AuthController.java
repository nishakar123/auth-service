package com.nishakar.controller;

import com.nishakar.commons.dto.AuthUserDTO;
import com.nishakar.commons.records.JwtToken;
import com.nishakar.mapper.AuthUserMapper;
import com.nishakar.service.AuthUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthUserMapper authUserMapper;

    private final AuthUserService authUserService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String rootPath(){
        log.info("Thread : {}", Thread.currentThread());
        log.info("Is Thread Virtual : {}", Thread.currentThread().isVirtual());
        return "Root API, try diff API";
    }

    @PostMapping("/user/create")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthUserDTO save(@RequestBody AuthUserDTO authUserDTO){
        log.info("AuthController -> save !");
        return authUserMapper.entityTODTO(authUserService.save(authUserDTO));
    }

    @PutMapping("/user/update")
    @ResponseStatus(HttpStatus.OK)
    public AuthUserDTO update(@RequestBody AuthUserDTO authUserDTO){
        log.info("AuthController -> update !");
        return authUserMapper.entityTODTO(authUserService.update(authUserDTO));
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<AuthUserDTO> findALl(){
        log.info("AuthController -> findALl !");
        return authUserMapper.entityListToDTOList(authUserService.getUsers());
    }

    @PostMapping("/token")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtToken token(@RequestBody AuthUserDTO authUserDTO) throws Exception {
        log.info("AuthController -> token !");
        String token = authUserService.generateJwtToken(authUserDTO);
        return new JwtToken(token, "Bearer");
    }
}
