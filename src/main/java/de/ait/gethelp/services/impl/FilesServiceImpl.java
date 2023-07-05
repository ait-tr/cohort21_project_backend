package de.ait.gethelp.services.impl;

import de.ait.gethelp.models.User;
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

    @Value("${upload.path}")
    private String UPLOAD_PATH;


    @Override
    public String getUserImage(Long currentUserId) {
        User user = usersRepository.findById(currentUserId)
                .orElseThrow(IllegalArgumentException::new);
        return user.getAvatar(); // нужен ли метод? возвращаем оригинальную строку?
    }

    @Override
    public String saveUserImage (Long currentUserId, MultipartFile image) throws IOException {
        if (image==null) throw new RuntimeException("No file");
        File uploadDir = new File(UPLOAD_PATH);
        if (!uploadDir.exists()){
            uploadDir.mkdirs();
        };
        User user = usersRepository.findById(currentUserId)
                .orElseThrow(IllegalArgumentException::new);
        String fileName = UUID.randomUUID().toString()+"_"+image.getOriginalFilename();
        Path path = uploadDir.toPath().resolve(fileName);
        image.transferTo(path);
        user.setAvatar(fileName); // тут делаем сейв или в отдельном методе?
        usersRepository.save(user);
        return path.toString();
    }


}
