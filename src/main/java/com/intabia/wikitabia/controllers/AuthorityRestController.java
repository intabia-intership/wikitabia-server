package com.intabia.wikitabia.controllers;

import com.intabia.wikitabia.dto.AuthorityDto;
import com.intabia.wikitabia.services.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

/**
 * REST контроллер для операций над authority.
 */
@RestController
@RequestMapping("/api/authorities")
@RequiredArgsConstructor
public class AuthorityRestController {
    private final AuthorityService authorityService;

    /**
     * создание новой authority.
     *
     * @param authorityDto - создаваемый authority
     * @return возвращает созданный authority
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthorityDto saveAuthority(@RequestBody @Valid AuthorityDto authorityDto) {
        return authorityService.createAuthority(authorityDto);
    }

    /**
     * нахождение authority по id.
     *
     * @param id - authority id
     * @return возвращает найденный authority
     */
    @GetMapping("/{id}")
    public AuthorityDto getAuthority(@PathVariable UUID id) {
        return authorityService.getAuthority(id);
    }

    /**
     * модификация authority по id.
     *
     * @param authorityDto - новый authority
     * @param id - authority id
     * @return возвращает измененный authority
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthorityDto updateAuthority(@RequestBody @Valid AuthorityDto authorityDto, @PathVariable UUID id) {
        return authorityService.updateAuthority(authorityDto, id);
    }

    /**
     * удаление authority по id.
     *
     * @param id - authority id
     * @return возвращает authority id
     */
    @DeleteMapping("/{id}")
    public UUID deleteAuthority(@PathVariable UUID id) {
        authorityService.deleteAuthority(id);
        return id;
    }
}
