package wendydeluca.u5d11.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import wendydeluca.u5d11.entities.User;
import wendydeluca.u5d11.exceptions.BadRequestException;
import wendydeluca.u5d11.payloads.UserDTO;
import wendydeluca.u5d11.services.UserService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    public UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')") // concedo solo all'admin di visionare la lista utenti
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "name") String sortBy){
        return userService.getAllUsers(page,size,sortBy);
    }

    @GetMapping("/me")
    public User getProfile(@AuthenticationPrincipal User currentAuthUser){
        return currentAuthUser;
    }

    @PutMapping("/me")
    public User updateProfile(@AuthenticationPrincipal User currentAuthUser, @RequestBody UserDTO updateUser){
        return this.userService.updateUser(currentAuthUser.getId(),updateUser);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal User currentAuthUser){
        this.userService.deleteUser(currentAuthUser.getId());
    }



    @GetMapping("/{userId}")
    public User findUserById(@PathVariable UUID userId){
        return userService.getUserById(userId);

    }

    @PutMapping("/{userId}")
    public User findUserByIdAndUpdate(@PathVariable UUID userId, @RequestBody @Validated UserDTO body, BindingResult validation){
        if(validation.hasErrors()){
            System.out.println(validation.getAllErrors());
            throw new BadRequestException(validation.getAllErrors());
        }
        return userService.updateUser(userId,body);
    }



    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable UUID userId){
        userService.deleteUser(userId);

    }







}
