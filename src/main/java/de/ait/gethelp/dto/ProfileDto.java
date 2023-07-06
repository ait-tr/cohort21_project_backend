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
public class ProfileDto {

    @Schema(description = "идентификатор пользователя, не указывается при добавлении", example = "1")
    private Long id;
    @Schema(description = "имя пользователя", example = "example name")
    private String username;
    @Schema(description = "имя файла аватара/изображения пользователя", example = "avatar001.jpeg")
    private String avatar;
    @Schema(description = "электронная почта пользователя", example = "example@gmail.com")
    private String email;
    @Schema(description = "контакнтый телефон пользователя", example = "+1234567890")
    private String phone;
    @Schema(description = "роль пользователя", example = "USER")
    private String role;
    @Schema(description = "статус пользователя в качестве хэлпера", example = "true")
    private Boolean isHelper;

    public static ProfileDto from(User user) {
        return ProfileDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole().toString())
                .isHelper(user.getIsHelper())
                .build();
    }

    public static List<ProfileDto> from(List<User> users) {
        return users.stream()
                .map(ProfileDto::from)
                .collect(Collectors.toList());
    }
}
