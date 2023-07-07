package de.ait.gethelp.services.impl;

import de.ait.gethelp.exceptions.NotFoundException;
import de.ait.gethelp.models.Card;
import de.ait.gethelp.models.User;
import de.ait.gethelp.repositories.CardsRepository;
import de.ait.gethelp.repositories.UsersRepository;
import de.ait.gethelp.services.FilesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FilesServiceImpl implements FilesService {

    private final UsersRepository usersRepository;
    private final CardsRepository cardsRepository;

    @Value("${upload.path}")
    private String UPLOAD_PATH;


    @Override
    public String saveUserImage(Long currentUserId, MultipartFile image) {
        User user = usersRepository.findById(currentUserId)
                .orElseThrow(IllegalArgumentException::new);
        String fileName = user.getAvatar();
        fileName = saveImage(image, fileName);
        user.setAvatar(fileName);
        usersRepository.save(user);
        return fileName;
    }

    @Override
    public String saveCardImage(Long cardId, MultipartFile image) {
        Card card = cardsRepository.findById(cardId).orElseThrow(
                () -> new NotFoundException("Card <" + cardId + "> not found"));
        String fileName = card.getImage();
        fileName = saveImage(image, fileName);
        System.out.println(fileName);
        card.setImage(fileName);
        cardsRepository.save(card);
        return fileName;
    }

    private String saveImage(MultipartFile image, String fileName) {
        if (image == null) throw new RuntimeException("No file");
        File uploadDir = new File(UPLOAD_PATH);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        if (fileName == null) {
            fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
        }
        Path path = uploadDir.toPath().resolve(fileName);
        try {
            image.transferTo(path);
        } catch (IOException e) {
            throw new RuntimeException("Saving image error", e);
        }
        return fileName;
    }


}
