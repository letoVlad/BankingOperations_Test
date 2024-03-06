package com.example.bankingoperations.service.impl;

import com.example.bankingoperations.dto.*;
import com.example.bankingoperations.service.UserService;
import com.example.bankingoperations.service.entities.BankAccountEntity;
import com.example.bankingoperations.service.entities.UserEmailEntity;
import com.example.bankingoperations.service.entities.UserEntity;
import com.example.bankingoperations.service.entities.UserPhoneEntity;
import com.example.bankingoperations.service.repositories.BankAccountRepository;
import com.example.bankingoperations.service.repositories.UserEmailRepository;
import com.example.bankingoperations.service.repositories.UserPhoneRepository;
import com.example.bankingoperations.service.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final UserPhoneRepository userPhoneRepository;
    private final UserEmailRepository userEmailRepository;
    private final BankAccountRepository bankAccountRepository;

    public UserServiceImpl(UserRepository userRepository,
                           UserPhoneRepository userPhoneRepository,
                           UserEmailRepository userEmailRepository,
                           BankAccountRepository bankAccountRepository) {
        this.userRepository = userRepository;
        this.userPhoneRepository = userPhoneRepository;
        this.userEmailRepository = userEmailRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public void addUser(UserDTO userDTO) {
        checkEmail(userDTO.getEmail());
        checkLogin(userDTO.getLogin());
        checkPhone(userDTO.getPhone());

        UserEntity newUserEntity = createUserEntity(userDTO);
        BankAccountEntity newBankAccountEntity = createBankAccountEntity(userDTO, newUserEntity);
        UserPhoneEntity newUserPhoneEntity = createUserPhone(userDTO, newUserEntity);
        UserEmailEntity newUserEmailEntity = createUserEmail(userDTO, newUserEntity);

        bankAccountRepository.saveAndFlush(newBankAccountEntity);
        userPhoneRepository.saveAndFlush(newUserPhoneEntity);
        userEmailRepository.saveAndFlush(newUserEmailEntity);
        userRepository.saveAndFlush(newUserEntity);
    }

    public void updateUserPhone(UpdateUserPhoneDTO updateUserPhoneDTO) {
        UserEntity user = getCurrentUser();

        if (updateUserPhoneDTO.getOldPhone() != null && !updateUserPhoneDTO.getOldPhone().isEmpty()) {
            UserPhoneEntity userPhone = userPhoneRepository.findByPhone(updateUserPhoneDTO.getOldPhone());

            if (userPhone == null) {
                log.error("Вы ввели неправильный номер телефона");
                throw new IllegalArgumentException("Вы ввели неправильный номер телефона");
            } else if (!user.getId().equals(userPhone.getUser().getId())) {
                log.error("Телефон принадлежит другому пользователю");
                throw new IllegalArgumentException("Телефон принадлежит другому пользователю.");
            } else {
                log.info("Телефон обновлен");
                userPhone.setPhone(updateUserPhoneDTO.getNewPhone());
                userPhoneRepository.saveAndFlush(userPhone);
            }
        }
    }

    @Override
    public void updateUserEmail(UpdateUserEmailDTO updateUserEmailDTO) {
        UserEntity user = getCurrentUser();

        if (updateUserEmailDTO.getOldEmail() != null && !updateUserEmailDTO.getOldEmail().isEmpty()) {
            UserEmailEntity userEmail = userEmailRepository.findByEmail(updateUserEmailDTO.getOldEmail());

            if (userEmail == null) {
                log.error("Вы ввели неправильный Email");
                throw new IllegalArgumentException("Вы ввели неправильный Email");
            } else if (!user.getId().equals(userEmail.getUser().getId())) {
                log.error("Email принадлежит другому пользователю.");
                throw new IllegalArgumentException("Email принадлежит другому пользователю.");
            } else {
                log.info("Email обновлен");
                userEmail.setEmail(updateUserEmailDTO.getNewEmail());
                userEmailRepository.saveAndFlush(userEmail);
            }
        }
    }

    @Override
    public void updateUser(UpdateUserDTO updateUserDTO) {
        UserEntity user = getCurrentUser();

        UserPhoneEntity userPhone = null;
        UserEmailEntity userEmail = null;

        if (updateUserDTO.getPhone() != null && !updateUserDTO.getPhone().isEmpty()) {
            userPhone = userPhoneRepository.findByPhone(updateUserDTO.getPhone());
        }

        if (updateUserDTO.getEmail() != null && !updateUserDTO.getEmail().isEmpty()) {
            userEmail = userEmailRepository.findByEmail(updateUserDTO.getEmail());
        }

        if (userPhone != null) {
            if (!user.getId().equals(userPhone.getUser().getId())) {
                log.error("Телефон принадлежит другому пользователю");
                throw new IllegalArgumentException("Телефон принадлежит другому пользователю.");
            }
            log.info("Информация обновлена");
            userPhone.setPhone(updateUserDTO.getPhone());
            userPhoneRepository.saveAndFlush(userPhone);
        }

        if (userEmail != null) {
            if (!user.getId().equals(userEmail.getUser().getId())) {
                log.error("Email принадлежит другому пользователю.");
                throw new IllegalArgumentException("Email принадлежит другому пользователю.");
            }
            log.info("Информация обновлена");
            userEmail.setEmail(updateUserDTO.getEmail());
            userEmailRepository.saveAndFlush(userEmail);
        }
    }

    @Override
    public void addEmailUser(AddUserEmailDTO addUserEmailDTO) {
        checkEmail(addUserEmailDTO.getEmail());
        String login = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<UserEntity> userOptional = userRepository.findByLogin(login);
        UserEntity user = userOptional.get();

        UserEmailEntity newUserEmailEntity = UserEmailEntity.builder()
                .email(addUserEmailDTO.getEmail())
                .user(user)
                .build();

        userEmailRepository.save(newUserEmailEntity);
    }

    @Override
    public void addPhoneUser(AddUserPhoneDTO addUserPhoneDTO) {
        checkPhone(addUserPhoneDTO.getPhone());
        String login = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<UserEntity> userOptional = userRepository.findByLogin(login);
        UserEntity user = userOptional.get();

        UserPhoneEntity newUserPhoneEntity = UserPhoneEntity.builder()
                .phone(addUserPhoneDTO.getPhone())
                .user(user)
                .build();

        userPhoneRepository.save(newUserPhoneEntity);
    }

    @Override
    public void deleteUserEmail(DeleteUserEmailDTO deleteUserEmailDTO) {
        UserEntity user = getCurrentUser();

        if (user.getUserEmailEntityList().size() <= 1) {
            log.error("Невозможно удалить последний Email у пользователя.");
            throw new IllegalStateException("Невозможно удалить последний Email у пользователя.");
        }

        List<UserEmailEntity> userEmailsToDelete = user.getUserEmailEntityList().stream()
                .filter(userEmailEntity -> Objects.equals(userEmailEntity.getEmail(), deleteUserEmailDTO.getEmail()))
                .collect(Collectors.toList());

        if (!userEmailsToDelete.isEmpty()) {
            userEmailRepository.deleteAll(userEmailsToDelete);
            user.getUserEmailEntityList().removeAll(userEmailsToDelete);
        } else {
            log.error("Email \"%s\" не найден у пользователя", deleteUserEmailDTO.getEmail());
            throw new EntityNotFoundException(String.format("Email \"%s\" не найден у пользователя", deleteUserEmailDTO.getEmail()));
        }
    }

    @Override
    public void deleteUserPhone(DeleteUserPhoneDTO deleteUserPhoneDTO) {
        UserEntity user = getCurrentUser();

        if (user.getUserPhoneEntityList().size() <= 1) {
            log.error("Невозможно удалить последний телефон у пользователя.");
            throw new IllegalStateException("Невозможно удалить последний телефон у пользователя.");
        }

        List<UserPhoneEntity> userPhoneToDelete = user.getUserPhoneEntityList().stream()
                .filter(userPhoneEntity -> Objects.equals(userPhoneEntity.getPhone(), deleteUserPhoneDTO.getPhone()))
                .collect(Collectors.toList());

        if (!userPhoneToDelete.isEmpty()) {
            userPhoneRepository.deleteAll(userPhoneToDelete);
            user.getUserPhoneEntityList().removeAll(userPhoneToDelete);
        } else {
            log.error("Phone \"%s\" не найден у пользователя", deleteUserPhoneDTO.getPhone());
            throw new EntityNotFoundException(String.format("Phone \"%s\" не найден у пользователя", deleteUserPhoneDTO.getPhone()));
        }
    }

    private UserEntity getCurrentUser() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
    }


    private UserPhoneEntity createUserPhone(UserDTO userDTO, UserEntity newUserEntity) {
        UserPhoneEntity newUserPhoneEntity = new UserPhoneEntity();
        newUserPhoneEntity.setPhone(userDTO.getPhone());
        newUserPhoneEntity.setUser(newUserEntity);
        return newUserPhoneEntity;
    }

    private UserEmailEntity createUserEmail(UserDTO userDTO, UserEntity newUserEntity) {
        UserEmailEntity newUserEmailEntity = new UserEmailEntity();
        newUserEmailEntity.setEmail(userDTO.getEmail());
        newUserEmailEntity.setUser(newUserEntity);
        return newUserEmailEntity;
    }

    private UserEntity createUserEntity(UserDTO userDTO) {
        UserEntity newUserEntity = new UserEntity();
        newUserEntity.setLogin(userDTO.getLogin());
        newUserEntity.setLastName(userDTO.getLastName());
        newUserEntity.setFirstName(userDTO.getFirstName());
        newUserEntity.setMiddleName(userDTO.getMiddleName());
        newUserEntity.setDateOfBirth(userDTO.getDateOfBirth());
        newUserEntity.setPassword(userDTO.getPassword());
        return newUserEntity;
    }

    private BankAccountEntity createBankAccountEntity(UserDTO userDTO, UserEntity newUserEntity) {
        BankAccountEntity newBankAccountEntity = new BankAccountEntity();
        newBankAccountEntity.setAmount(userDTO.getAmount());
        newBankAccountEntity.setUser(newUserEntity);
        return newBankAccountEntity;
    }

    private void checkEmail(String email) {
        if (userEmailRepository.existsByEmail(email)) {
            throw new EntityExistsException(String.format("Данный email \"%s\" уже используется.", email));
        }
    }

    private void checkLogin(String login) {
        if (userRepository.existsByLogin(login)) {
            throw new EntityExistsException(String.format("Данный Login \"%s\" уже используется.", login));
        }
    }

    private void checkPhone(String phone) {
        if (userPhoneRepository.existsByPhone(phone)) {
            throw new EntityExistsException(String.format("Данный номер телефона \"%s\" уже зарегистрирован.", phone));
        }
    }

    public Optional<UserEntity> findByUserName(String userLogin) {
        return userRepository.findByLogin(userLogin);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userLogin) throws UsernameNotFoundException {
        UserEntity userEntity = findByUserName(userLogin).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Пользователь \"%s\" не найден ", userLogin))
        );

        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        return new User(userEntity.getLogin(), userEntity.getPassword(), authorities);
    }
}
