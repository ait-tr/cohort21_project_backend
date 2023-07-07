package de.ait.gethelp.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FilesService {

    String saveUserImage(Long currentUserId, MultipartFile image);

    String saveCardImage(Long cardId, MultipartFile image);

}
