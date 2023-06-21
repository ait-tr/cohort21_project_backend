package de.ait.gethelp.controllers;

import de.ait.gethelp.controllers.api.SignUpApi;
import de.ait.gethelp.dto.NewUserDto;
import de.ait.gethelp.dto.UserDto;
import de.ait.gethelp.services.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SignUpController implements SignUpApi {

    private final SignUpService signUpService;

    @Override
    public ResponseEntity<UserDto> signUp(NewUserDto newUser) {
        return ResponseEntity
                .status(201)
                .body(signUpService.signUp(newUser));
    }
}
