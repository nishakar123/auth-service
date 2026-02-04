package com.nishakar.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/*@EnableMethodSecurity replaces the old @EnableGlobalMethodSecurity used in Spring Boot 2.x.
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    // your SecurityFilterChain bean etc.
}

Spring Security distinguishes between:
hasRole('ADMIN') → expects the GrantedAuthority to be "ROLE_ADMIN"
hasAuthority('ADMIN') → expects exactly "ADMIN"
So if your user is stored with "ADMIN", but your annotation uses hasRole('ADMIN'), it will fail.
✅ Options:
Either prefix roles with ROLE_ in DB, or
Use hasAuthority('ADMIN') in annotation.*/

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String hello(){
        return "Hello";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PostMapping("/save")
    public String save(){
        return "Saved";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete")
    public String delete(){
        return "Deleted";
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @PutMapping("/update")
    public String update(){
        return "Updated";
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @PatchMapping("/patch")
    public String patch(){
        return "Patch";
    }
}
