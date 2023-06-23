package de.ait.gethelp.runners;

import de.ait.gethelp.models.Task;
import de.ait.gethelp.models.User;
import de.ait.gethelp.repositories.TasksRepository;
import de.ait.gethelp.repositories.UsersRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.CommandLineRunner;

import java.util.Arrays;

@RequiredArgsConstructor
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class InitialDataRunner implements CommandLineRunner {

    UsersRepository usersRepository;

    TasksRepository tasksRepository;

    @Override
    public void run(String... args) {

        User alisher = null;

        if (!usersRepository.existsById(1L)) {
            User admin = User.builder()
                    .email("admin@ait-tr.de")
                    .role(User.Role.ADMIN)
                    .hashPassword("$2a$10$YijmlwvWMcfIhT2qQOQ7EeRuMiByNjPtKXa78J7Y8z7XZWJJQTDa.") // admin
                    .build();

            alisher = User.builder()
                    .email("alisher@ait-tr.de")
                    .role(User.Role.USER)
                    .hashPassword("$2a$10$RVSHTssubxIkoAl3rQ58UedU8sPMM6FZRxg1icrJg07f.MQAMRpDy") // alisher
                    .build();
            usersRepository.save(admin);
            usersRepository.save(alisher);
        }

        if (tasksRepository.count() == 0) {
            tasksRepository.saveAll(Arrays.asList(
                    Task.builder().name("Name 1").description("Description 1").user(alisher).build(),
                    Task.builder().name("Name 1").description("Description 1").user(alisher).build(),
                    Task.builder().name("Name 1").description("Description 1").user(alisher).build(),
                    Task.builder().name("Name 1").description("Description 1").user(alisher).build()
            ));
        }


    }
}