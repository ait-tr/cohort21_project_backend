package de.ait.gethelp.controllers;

import de.ait.gethelp.controllers.api.FilesApi;
import de.ait.gethelp.security.details.AuthenticatedUser;
import de.ait.gethelp.services.FilesService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/files")
public class FileController implements FilesApi {
    private final FilesService filesService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    @Override
    public ResponseEntity<String> getUserImage(AuthenticatedUser authenticatedUser) {
        Long currentUserId = authenticatedUser.getUser().getId();
        return ResponseEntity
                .ok(filesService.getUserImage(currentUserId));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/upload")
    public ResponseEntity<String> saveUserImage(@Parameter(hidden = true) @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
                                            @RequestParam("image") MultipartFile image){
        Long currentUserId = authenticatedUser.getUser().getId();
        try {
            return ResponseEntity.status(201)
                    .body(filesService.saveUserImage(currentUserId, image));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
