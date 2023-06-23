package de.ait.gethelp.services;


import de.ait.gethelp.dto.CardDto;
import de.ait.gethelp.dto.ProfileDto;
import de.ait.gethelp.dto.TasksPage;
import de.ait.gethelp.security.details.AuthenticatedUser;

public interface UsersService {

    ProfileDto getProfile(Long currentUserId);

    TasksPage getTasksByUser(Long currentUserId);

    CardDto editCardStatus(Long currentUserId, Long cardId, Boolean cardStatus);
}
