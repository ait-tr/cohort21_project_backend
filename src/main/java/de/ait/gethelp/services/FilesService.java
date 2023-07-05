package de.ait.gethelp.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FilesService {

    String getUserImage(Long currentUserId);
    String saveUserImage(Long currentUserId, MultipartFile image) throws IOException;

}
