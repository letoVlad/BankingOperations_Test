package com.example.bankingoperations.specification;

import com.example.bankingoperations.service.entities.UserEntity;
import com.example.bankingoperations.service.entities.UserPhoneEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.time.LocalDate;

public class UserSpecifications {

    public static Specification<UserEntity> hasEmail(String email) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("email"), email);
    }

    public static Specification<UserEntity> hasPhone(String phone) {
        return (root, query, criteriaBuilder) -> {
            Join<UserEntity, UserPhoneEntity> userPhoneJoin = root.join("userPhoneEntityList");
            return criteriaBuilder.equal(userPhoneJoin.get("phone"), phone);
        };
    }

    public static Specification<UserEntity> nameLike(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.like(root.get("lastName"), name + "%"),
                        criteriaBuilder.like(root.get("firstName"), name + "%"),
                        criteriaBuilder.like(root.get("middleName"), name + "%")
                );
    }

    public static Specification<UserEntity> dobAfter(LocalDate dob) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("dateOfBirth"), dob);
    }
}
