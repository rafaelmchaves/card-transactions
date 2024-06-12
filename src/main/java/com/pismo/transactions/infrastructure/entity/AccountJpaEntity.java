package com.pismo.transactions.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Table(name = "accounts")
@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class AccountJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //TODO maybe change to UUID
    private Long id;

    @Column(name = "document_number")
    private String documentNumber;

    @Column(name = "creation_on")
    private LocalDateTime creation;

}
