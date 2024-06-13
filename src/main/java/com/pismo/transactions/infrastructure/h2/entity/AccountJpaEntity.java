package com.pismo.transactions.infrastructure.h2.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Table(name = "accounts")
@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class AccountJpaEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "ID", updatable = false, nullable = false)
    @ColumnDefault("random_uuid()")
    private UUID id;

    @Column(name = "document_number")
    private String documentNumber;

    @Column(name = "creation_on")
    private LocalDateTime creation;

}
