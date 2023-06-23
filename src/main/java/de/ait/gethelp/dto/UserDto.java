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

    @Schema(description = "имя пользователя", example = "username")
    private String username;

    // @Schema(description = "аватар пользователя")
    // private Image/String avatar;

    @Schema(description = "почта пользователя", example = "user@gmail.com")
    private String email;

    @Schema(description = "телефон пользователя", example = "+1234567890")
    private String phone;

    @Schema(description = "является ли пользователь хэлпером", example = "true")
    private Boolean isHelper;

    public static UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .isHelper(user.getIsHelper())
                .build();
    }

    public static List<UserDto> from(List<User> users) {
        return users.stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }
}
