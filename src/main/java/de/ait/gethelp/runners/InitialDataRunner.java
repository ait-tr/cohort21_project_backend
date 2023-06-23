package de.ait.gethelp.runners;

import de.ait.gethelp.models.Card;
import de.ait.gethelp.models.Category;
import de.ait.gethelp.models.User;
import de.ait.gethelp.repositories.CardsRepository;
import de.ait.gethelp.repositories.CategoriesRepository;
import de.ait.gethelp.repositories.UsersRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.CommandLineRunner;

import java.time.LocalDateTime;

@RequiredArgsConstructor
//@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class InitialDataRunner implements CommandLineRunner {

    UsersRepository usersRepository;
    CategoriesRepository categoriesRepository;
    // TODO добавить SubCatRepo
    CardsRepository cardsRepository;

    @Override
    public void run(String... args) {

        /* Класс для удобных тестов. Создает готовую базу данных автоматически при каждом запуске программы.
           Для активации - необходимо снять комментарий с аннотации @Component
           Использовать только для временной базы данных H2

        Будет создано:
        * 5 пользователей - 1 админ и 4 юзера. Логин пароль совпадают (например alex-alex, bob-bob ...)
                Юзер paul - является заблокированным и не должен иметь возможность "залогиниться",
                а также выполнять какие-либо действия доступные аутентифицированным пользователям.

        * 3 категории и 6 подкатегорий

        * 5 карточек помощи:
        admin - не может иметь карточек
        alex - 0 карточек, статус у юзера isHelper = false
        bob - 1 карточка (isActive = true), статус у юзера isHelper = true
        anna - 3 карточки (две из них isActive = true, а у третьей isActive = false), статус у юзера isHelper = true
        paul - 1 карточка (isActive = false), статус у юзера isHelper = false
        */

        User admin = null;
        User alex = null;
        User bob = null;
        User anna = null;
        User paul = null;
        Category category1 = null;
        Category category2 = null;
        Category category3 = null;
        // TODO SubCats
        Card card1 = null;
        Card card2 = null;
        Card card3 = null;
        Card card4 = null;
        Card card5 = null;

        if (!usersRepository.existsById(1L)) {
            admin = User.builder()
                    .createdAt(LocalDateTime.now())
                    .username("admin")
                    .hashPassword("$2a$10$qtPCgV0Nuxt3ytTnc9Nt0eE2jfmdEpnmJEqeb8tzmWPz/g29iL86C") // admin
                    .email("admin@ait-tr.de")
                    .phone("+49-111-999-111")
                    .role(User.Role.ADMIN)
                    .isHelper(false)
                    .isBlocked(false)
                    .build();

            alex = User.builder()
                    .createdAt(LocalDateTime.now())
                    .username("alex")
                    .hashPassword("$2a$10$dFXB0Xk4Le1hqiua4DojK.OyqJiSejClTqGHsC0OBMeqe/3Qyk7/y") // alex
                    .email("alex@ait-tr.de")
                    .phone("+49-222-999-222")
                    .role(User.Role.USER)
                    .isHelper(false)
                    .isBlocked(false)
                    .build();

            bob = User.builder()
                    .createdAt(LocalDateTime.now())
                    .username("bob")
                    .hashPassword("$2a$10$isvLi2u.Q9JFkKD3Cgrkdue09xdg5tyfcUuQ7GtzrQFMdsdJFLjCm") // bob
                    .email("bob@ait-tr.de")
                    .phone("+49-333-999-333")
                    .role(User.Role.USER)
                    .isHelper(true)
                    .isBlocked(false)
                    .build();

            anna = User.builder()
                    .createdAt(LocalDateTime.now())
                    .username("anna")
                    .hashPassword("$2a$10$MHZ0oJVgMEXBPcvdMNeoROnIVZzibbH7tt6.IkujM5YtAaZsj/Jdq") // anna
                    .email("anna@ait-tr.de")
                    .phone("+49-444-999-444")
                    .role(User.Role.USER)
                    .isHelper(true)
                    .isBlocked(false)
                    .build();

            paul = User.builder()
                    .createdAt(LocalDateTime.now())
                    .username("paul")
                    .hashPassword("$2a$10$QcroOlk9idGY9W5QilUCfumgbal/4tdesB43BcQ1cYX5cNWGgr5BS") // paul
                    .email("paul@ait-tr.de")
                    .phone("+49-555-999-555")
                    .role(User.Role.USER)
                    .isHelper(false)
                    .isBlocked(true)
                    .build();

            usersRepository.save(admin);
            usersRepository.save(alex);
            usersRepository.save(bob);
            usersRepository.save(anna);
            usersRepository.save(paul);
        }

        if (!categoriesRepository.existsById(1L)) {
            category1 = Category.builder()
                    .createdAt(LocalDateTime.now())
                    .title("Education")
                    .description("Тестовое описание категории Education")
                    .build();

            category2 = Category.builder()
                    .createdAt(LocalDateTime.now())
                    .title("Care")
                    .description("Тестовое описание категории Care")
                    .build();

            category3 = Category.builder()
                    .createdAt(LocalDateTime.now())
                    .title("Home")
                    .description("Тестовое описание категории Home")
                    .build();

            categoriesRepository.save(category1);
            categoriesRepository.save(category2);
            categoriesRepository.save(category3);
        }

        // TODO добавить подкатегории

        if (!cardsRepository.existsById(1L)) {
            card1 = Card.builder()
                    .createdAt(LocalDateTime.now())
                    .user(anna)
                    .category(category1)
                    // TODO SubCat
                    .price(27.0)
                    .description("Тестовое описание 1й карточки помощи от anna")
                    .isActive(true)
                    .build();

            card2 = Card.builder()
                    .createdAt(LocalDateTime.now())
                    .user(bob)
                    .category(category2)
                    // TODO SubCat
                    .price(16.0)
                    .description("Тестовое описание 2й карточки помощи от bob")
                    .isActive(true)
                    .build();

            card3 = Card.builder()
                    .createdAt(LocalDateTime.now())
                    .user(paul)
                    .category(category3)
                    // TODO SubCat
                    .price(21.0)
                    .description("Тестовое описание 3й карточки помощи от paul")
                    .isActive(false)
                    .build();

            card4 = Card.builder()
                    .createdAt(LocalDateTime.now())
                    .user(anna)
                    .category(category1)
                    // TODO SubCat
                    .price(24.0)
                    .description("Тестовое описание 4й карточки помощи от anna")
                    .isActive(false)
                    .build();

            card5 = Card.builder()
                    .createdAt(LocalDateTime.now())
                    .user(anna)
                    .category(category1)
                    // TODO SubCat
                    .price(32.0)
                    .description("Тестовое описание 5й карточки помощи от anna")
                    .isActive(true)
                    .build();

            cardsRepository.save(card1);
            cardsRepository.save(card2);
            cardsRepository.save(card3);
            cardsRepository.save(card4);
            cardsRepository.save(card5);
        }
    }
}