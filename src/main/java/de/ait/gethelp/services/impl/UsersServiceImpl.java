package de.ait.gethelp.services.impl;

import de.ait.gethelp.dto.ProfileDto;
import de.ait.gethelp.dto.TasksPage;
import de.ait.gethelp.models.Task;
import de.ait.gethelp.models.User;
import de.ait.gethelp.repositories.TasksRepository;
import de.ait.gethelp.repositories.UsersRepository;
import de.ait.gethelp.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static de.ait.gethelp.dto.TaskDto.from;

@RequiredArgsConstructor
@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final TasksRepository tasksRepository;

    @Override
    public ProfileDto getProfile(Long currentUserId) {
        User user = usersRepository.findById(currentUserId)
                .orElseThrow(IllegalArgumentException::new);

        return ProfileDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole().name())
                .isHelper(user.getIsHelper())
                .cards(user.getCards())
                .build();
    }

    @Override
    public TasksPage getTasksByUser(Long currentUserId) {
        List<Task> tasks = tasksRepository.findAllByUser_Id(currentUserId);

        return TasksPage.builder()
                .tasks(from(tasks))
                .build();

    }

}
