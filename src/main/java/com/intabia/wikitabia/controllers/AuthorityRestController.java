package com.intabia.wikitabia.controllers;

import com.intabia.wikitabia.dto.AuthorityDto;
import com.intabia.wikitabia.services.interfaces.AuthorityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthorityRestController {

    private final AuthorityService authorityService;

    @PostMapping(value = "/authorities", consumes = "application/json", produces = "application/json")
    public AuthorityDto saveAuthority(@RequestBody AuthorityDto authorityDto) {
        return authorityService.createAuthority(authorityDto);
    }

    @GetMapping("/authorities/{id}")
    public AuthorityDto getAuthority(@PathVariable UUID id) {
        return authorityService.getAuthority(id);
    }

    @PutMapping(value = "/authorities", consumes = "application/json", produces = "application/json")
    public AuthorityDto updateAuthority(@RequestBody AuthorityDto authorityDto) {
        return authorityService.updateAuthority(authorityDto);
    }

    @DeleteMapping("/authorities/{id}")
    public String deleteAuthority(@PathVariable UUID id) {
        authorityService.deleteAuthority(id);
        return "Authority was deleted";
    }
}
