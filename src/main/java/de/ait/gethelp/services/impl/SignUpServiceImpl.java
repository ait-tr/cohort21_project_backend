package de.ait.gethelp.services.impl;

import de.ait.gethelp.dto.NewUserDto;
import de.ait.gethelp.dto.ProfileDto;
import de.ait.gethelp.exceptions.BadDataException;
import de.ait.gethelp.exceptions.ConflictException;
import de.ait.gethelp.models.User;
import de.ait.gethelp.repositories.UsersRepository;
import de.ait.gethelp.services.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class SignUpServiceImpl implements SignUpService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ProfileDto signUp(NewUserDto newUser) {
        if (newUser.getUsername().length() < 3) {
            throw new BadDataException("Username is too short. Must be at least 3 characters long");
        }
        if (usersRepository.existsByUsernameEquals(newUser.getUsername())) {
            throw new ConflictException("User with this username already exist");
        }
        if (newUser.getPassword().length() < 3) {
            throw new BadDataException("Password must be at least 3 characters long ");
        }
        // TODO change min password length and add pass difficulty check

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

        return ProfileDto.from(user);
    }
}
