package de.ait.gethelp.services;


import de.ait.gethelp.dto.ProfileDto;
import de.ait.gethelp.dto.TasksPage;

public interface UsersService {

    ProfileDto getProfile(Long currentUserId);

    TasksPage getTasksByUser(Long currentUserId);
}
