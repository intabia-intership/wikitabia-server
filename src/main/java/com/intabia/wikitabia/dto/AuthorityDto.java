package com.intabia.wikitabia.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * dto класс для передачи сущности authority между frontend и backend.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityDto {
    /**
     * название authority.
     */
    @NotBlank
    private String name;
}
