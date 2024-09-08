package com.luigivismara.modeldomain.interceptors;

import com.luigivismara.modeldomain.entity.AuditLogEntity;
import com.luigivismara.modeldomain.entity.AuditableEntity;
import com.luigivismara.modeldomain.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.luigivismara.modeldomain.enums.AuditActionType.*;

@Aspect
@Service
@RequiredArgsConstructor
public class AuditLogAspect {

    private static final Logger log = LoggerFactory.getLogger(AuditLogAspect.class);
    private final AuditLogRepository auditLogRepository;

    @Before("execution(* com.luigivismara.modeldomain.repository.*.save(..))")
    public void logBeforeSave(JoinPoint joinPoint) {
        var entity = joinPoint.getArgs()[0];
        if (entity instanceof AuditableEntity auditable) {
            var entityType = entity.getClass().getSimpleName();
            var entityId = auditable.getId();

            var action = (entityId == null) ? CREATE : UPDATE;

            var log = AuditLogEntity.builder()
                    .entityType(entityType)
                    .entityId(entityId)
                    .action(action)
                    .username(SecurityContextHolder.getContext().getAuthentication().getName())
                    .timestamp(LocalDateTime.now())
                    .build();

            if (action.equals(UPDATE)) {
                log.setOldValues(auditable.getOldValues());
            }

            log.setNewValues(auditable.getNewValues());

            auditLogRepository.save(log);
        }
    }

    @Before("execution(* com.luigivismara.modeldomain.repository.*.delete(..))")
    public void logBeforeDelete(JoinPoint joinPoint) {
        forDeleteOrDisableMethod(joinPoint);
    }

    @Before("execution(* com.luigivismara.modeldomain.repository.*.deleteById(..))")
    public void logBeforeDeleteById(JoinPoint joinPoint) {
        final var id = joinPoint.getArgs()[0];
        if (id instanceof UUID || id instanceof Long) {
            final var repository = joinPoint.getTarget();
            try {
                final var findByIdMethod = repository.getClass().getMethod("findById", id.getClass());
                final var entityOptional = (Optional<?>) findByIdMethod.invoke(repository, id);
                entityOptional.ifPresent(entity -> {
                    if (entity instanceof AuditableEntity auditable) {
                        final var log = AuditLogEntity.builder()
                                .entityType(entity.getClass().getSimpleName())
                                .entityId(auditable.getId())
                                .action(DELETE)
                                .username(SecurityContextHolder.getContext().getAuthentication().getName())
                                .timestamp(LocalDateTime.now())
                                .oldValues(auditable.getOldValues())
                                .build();

                        auditLogRepository.save(log);
                    }
                });
            } catch (Exception e) {
                log.error("Error during deleting entity", e);
            }
        }
    }

    private void forDeleteOrDisableMethod(JoinPoint joinPoint) {
        var entity = joinPoint.getArgs()[0];
        if (entity instanceof AuditableEntity auditable) {
            var log = AuditLogEntity.builder()
                    .entityType(entity.getClass().getSimpleName())
                    .entityId(auditable.getId())
                    .action(DELETE)
                    .username(SecurityContextHolder.getContext().getAuthentication().getName())
                    .timestamp(LocalDateTime.now())
                    .oldValues(auditable.getOldValues())
                    .build();

            auditLogRepository.save(log);
        }
    }
}

