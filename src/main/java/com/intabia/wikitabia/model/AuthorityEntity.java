package com.intabia.wikitabia.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Сущность для работы с authority.
 */
@Entity
@Table(name = "authority")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityEntity {

    /**
     * authority id.
     */
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    /**
     * название authority.
     */
    @Column(name = "name")
    private String name;
}
