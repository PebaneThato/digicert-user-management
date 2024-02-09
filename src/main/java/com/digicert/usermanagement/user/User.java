package com.digicert.usermanagement.user;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "users")
public class User {
    @Id
    long id;

    String firstName;
    String lastName;

    @Column(length = 50, nullable = false, unique = true)
    @NotBlank(message = "E-mail address must not be empty")
    @Email(message = "User must have valid email address")
    String email;
}
