package com.nishakar.entity;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class BaseEntityListener {

    @Value("{spring.default.audit.user.name:subramanyam}")
    private String defaultAuditUser;

    @PrePersist
    private void beforePersist(final BaseEntity baseEntity){
        final String username = getUserName();
        log.debug("beforePersist username: {} ", username);
        baseEntity.setCreatedBy(username);
        baseEntity.setUpdatedBy(username);
        log.debug("createdBy and updatedBy value set successfully");
    }

    @PreUpdate
    private void beforeUpdate(final BaseEntity baseEntity){
        final String username = getUserName();
        log.debug("beforeUpdate username: {} ", username);
        baseEntity.setUpdatedBy(username);
        log.debug("updatedBy value set successfully");
    }

    private String getUserName(){
        return "subramanyam";
    }
}
