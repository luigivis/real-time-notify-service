package com.luigivismara.modeldomain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.UUID;

@Entity(name = "notification_preferences")
@Data
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper = true)
public class NotificationPreferencesEntity extends AbstractAuditableEntity {

    @Id
    @GeneratedValue
    @Column(name = "preference_id", nullable = false, updatable = false)
    private UUID preferenceId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private boolean receivePromotions;

    @Column(nullable = false)
    private boolean receiveUpdates;

    @Column(nullable = false)
    private boolean receiveAlerts;

    @Override
    public UUID getId() {
        return this.preferenceId;
    }
}
