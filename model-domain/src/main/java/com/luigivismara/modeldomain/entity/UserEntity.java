package com.luigivismara.modeldomain.entity;

import com.luigivismara.modeldomain.enums.RolesType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.UUID;

@Data
@Entity(name = "user")
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends AbstractAuditableEntity{

    @Id
    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RolesType role;

    @Override
    public UUID getId() {
        return this.userId;
    }
}