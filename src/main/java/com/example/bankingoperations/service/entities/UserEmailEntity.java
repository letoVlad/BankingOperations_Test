package com.example.bankingoperations.service.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "email")
@JsonIgnoreProperties({"user"}) // Игнорируем обратную ссылку в классе UserEmailEntity
public class UserEmailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "email")
    String email;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    UserEntity user;

}
