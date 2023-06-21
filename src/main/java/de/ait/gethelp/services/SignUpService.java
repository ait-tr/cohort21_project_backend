package de.ait.gethelp.services;


import de.ait.gethelp.dto.NewUserDto;
import de.ait.gethelp.dto.UserDto;

public interface SignUpService {
    UserDto signUp(NewUserDto newUser);
}
