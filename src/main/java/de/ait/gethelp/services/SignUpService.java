package de.ait.gethelp.services;


import de.ait.gethelp.dto.NewUserDto;
import de.ait.gethelp.dto.ProfileDto;

public interface SignUpService {
    ProfileDto signUp(NewUserDto newUser);
}
