package de.ait.gethelp.controllers;

import de.ait.gethelp.controllers.api.FilesApi;
import de.ait.gethelp.security.details.AuthenticatedUser;
import de.ait.gethelp.services.FilesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/files/upload")
public class FileController implements FilesApi {
    private final FilesService filesService;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public ResponseEntity<String> saveUserImage(AuthenticatedUser authenticatedUser, MultipartFile image) {
        Long currentUserId = authenticatedUser.getUser().getId();
        return ResponseEntity.status(201)
                .body(filesService.saveUserImage(currentUserId, image));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/{card-id}")
    public ResponseEntity<String> saveCardImage(AuthenticatedUser authenticatedUser, @PathVariable("card-id")  Long cardId, MultipartFile image) {
        return ResponseEntity.status(201)
                .body(filesService.saveCardImage(cardId, image));
    }
}
