package com.nishakar.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class, BaseEntityListener.class})
public class BaseEntity {

    @Column(name = "created", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime created;

    @Column(name = "updated", nullable = false)
    @LastModifiedDate
    private LocalDateTime updated;

    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    @Column(name = "updated_by", nullable = false)
    private String updatedBy;
}
