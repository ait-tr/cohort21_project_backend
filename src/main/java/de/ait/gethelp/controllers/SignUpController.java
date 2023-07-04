package de.ait.gethelp.controllers;

import de.ait.gethelp.controllers.api.SignUpApi;
import de.ait.gethelp.dto.NewUserDto;
import de.ait.gethelp.dto.ProfileDto;
import de.ait.gethelp.services.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/register")
public class SignUpController implements SignUpApi {

    private final SignUpService signUpService;

    @Override
    @PostMapping
    public ResponseEntity<ProfileDto> signUp(NewUserDto newUser) {
        return ResponseEntity
                .status(201)
                .body(signUpService.signUp(newUser));
    }
}
