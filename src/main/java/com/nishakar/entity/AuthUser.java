package com.nishakar.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
@Entity(name = "auth_user")
@NoArgsConstructor
@AllArgsConstructor
public class AuthUser extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -17071232548379614L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    public String username;

    public String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "id")
    )
    @Column(name = "role")
    public List<String> roles;
}
