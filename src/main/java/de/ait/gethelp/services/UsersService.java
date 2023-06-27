package de.ait.gethelp.services;


import de.ait.gethelp.dto.CardDto;
import de.ait.gethelp.dto.ProfileDto;

public interface UsersService {

    ProfileDto getProfile(Long currentUserId);

    CardDto editCardStatus(Long currentUserId, Long cardId, Boolean cardStatus);
}
