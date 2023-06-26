package de.ait.gethelp.controllers;

import de.ait.gethelp.controllers.api.UsersApi;
import de.ait.gethelp.dto.CardDto;
import de.ait.gethelp.dto.NewProfileDto;
import de.ait.gethelp.dto.ProfileDto;
import de.ait.gethelp.security.details.AuthenticatedUser;
import de.ait.gethelp.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UsersController implements UsersApi {

    private final UsersService usersService;

    @PreAuthorize("isAuthenticated()")
    @Override
    public ResponseEntity<ProfileDto> getProfile(AuthenticatedUser currentUser) {
        Long currentUserId = currentUser.getUser().getId();
        ProfileDto profile = usersService.getProfile(currentUserId);
        return ResponseEntity.ok(profile);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity<ProfileDto> editProfile(AuthenticatedUser currentUser, NewProfileDto editedProfile) {
        Long currentUserId = currentUser.getUser().getId();
        ProfileDto profile = usersService.editProfile(currentUserId, editedProfile);
        return ResponseEntity.ok(profile);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity<CardDto> editCardStatus(AuthenticatedUser currentUser, Long cardId, Boolean cardStatus) {
        Long currentUserId = currentUser.getUser().getId();
        return ResponseEntity.ok(usersService.editCardStatus(currentUserId, cardId, cardStatus));
    }
}
