package de.ait.gethelp.services.impl;

import de.ait.gethelp.dto.NewUserDto;
import de.ait.gethelp.dto.UserDto;
import de.ait.gethelp.models.User;
import de.ait.gethelp.repositories.UsersRepository;
import de.ait.gethelp.services.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static de.ait.gethelp.dto.UserDto.from;

@RequiredArgsConstructor
@Service
public class SignUpServiceImpl implements SignUpService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto signUp(NewUserDto newUser) {
        User user = User.builder()
                .username(newUser.getUsername())
                .hashPassword(passwordEncoder.encode(newUser.getPassword()))
                .createdAt(LocalDateTime.now())
                .email("")
                .phone("")
                .isBlocked(false)
                .isHelper(false)
                .role(User.Role.USER)
                .build();

        usersRepository.save(user);

        return from(user);
    }
}
