package de.ait.gethelp.dto;

import de.ait.gethelp.models.Card;
import de.ait.gethelp.models.User;
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

    private Long id;
    private String username;
    private String email;
    private String phone;
    private String role;
    private Boolean isHelper;

    public static ProfileDto from(User user) {
        return ProfileDto.builder()
                .id(user.getId())
                .username(user.getUsername())
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
