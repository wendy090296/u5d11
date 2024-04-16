package wendydeluca.u5d11.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wendydeluca.u5d11.entities.User;
import wendydeluca.u5d11.payloads.LoginDTO;
import wendydeluca.u5d11.payloads.UserLoginResponseDTO;
import wendydeluca.u5d11.services.AuthService;
import wendydeluca.u5d11.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

@Autowired
public AuthService authService;

//    @Autowired
//    private UserService userService;


    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody LoginDTO payload){
        return new UserLoginResponseDTO(this.authService.authenticateUserAndGenerateToken(payload));

    }
}
