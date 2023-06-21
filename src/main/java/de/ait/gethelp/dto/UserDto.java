package de.ait.gethelp.dto;

import de.ait.gethelp.models.Card;
import de.ait.gethelp.models.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Зарегистрированный пользователь")
public class UserDto {

    @Schema(description = "идентификатор пользователя", example = "1")
    private Long id;

    //TODO @Schema(description = "аватар пользователя")
    //private Image avatar;

    @Schema(description = "имя пользователя", example = "username")
    private String email;

    @Schema(description = "является ли пользователь хэлпером", example = "true")
    private Boolean isHelper;

    @Schema(description = "список карточек пользователя", example = "true")
    private List<Card> cards;

    public static UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }

    public static List<UserDto> from(List<User> users) {
        return users.stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }
}
