package de.ait.gethelp.services;


import de.ait.gethelp.dto.CardDto;
import de.ait.gethelp.dto.CardsPage;
import de.ait.gethelp.dto.NewProfileDto;
import de.ait.gethelp.dto.ProfileDto;

public interface UsersService {

    ProfileDto getProfile(Long currentUserId);

    CardDto editCardStatus(Long currentUserId, Long cardId, Boolean cardStatus);

    ProfileDto editProfile(Long currentUserId, NewProfileDto editedProfile);

    CardsPage getUserCards(Long currentUserId);
}
