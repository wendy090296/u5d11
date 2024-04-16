package wendydeluca.u5d11.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import wendydeluca.u5d11.entities.User;
import wendydeluca.u5d11.exceptions.BadRequestException;
import wendydeluca.u5d11.payloads.LoginDTO;
import wendydeluca.u5d11.payloads.UserDTO;
import wendydeluca.u5d11.payloads.UserLoginResponseDTO;
import wendydeluca.u5d11.services.AuthService;
import wendydeluca.u5d11.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

@Autowired
public AuthService authService;
@Autowired
private UserService userService;

    @PostMapping("/register") //SAVE
    @ResponseStatus(HttpStatus.CREATED) //STATUS 201 OK
    public User saveUser(@RequestBody @Validated UserDTO body, BindingResult validation){
        if(validation.hasErrors()){
            System.out.println(validation.getAllErrors());
            throw new BadRequestException(validation.getAllErrors());
        }
        return userService.saveUser(body);
    }


    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody LoginDTO payload){
        return new UserLoginResponseDTO(this.authService.authenticateUserAndGenerateToken(payload));

    }
}
