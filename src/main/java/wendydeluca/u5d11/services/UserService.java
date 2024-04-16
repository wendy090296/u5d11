package wendydeluca.u5d11.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import wendydeluca.u5d11.entities.User;
import wendydeluca.u5d11.exceptions.BadRequestException;
import wendydeluca.u5d11.exceptions.NotFoundException;
import wendydeluca.u5d11.payloads.UserDTO;
import wendydeluca.u5d11.repositories.UserDAO;



import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;





    public Page<User> getAllUsers(int page, int size, String sortBy) { //GET ALL CON PAGINAZIONE
        if (size < 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return userDAO.findAll(pageable);
    }



    public User getUserById(UUID userId) {
        return userDAO.findById(userId).orElseThrow(() -> new NotFoundException(userId));
    }

    public User saveUser(UserDTO body) {
        // 1. Se l'email dello user non é presente,
        if (!userDAO.existsByEmail(body.email())) {
            // 2. creo un nuovo oggetto User "modellato" sul body
            User newUser = new User(body.name(), body.surname(), body.username(), body.email(),body.password(),"https://ui-avatars.com/api/?name="+ body.name() + "+" + body.surname());
            return userDAO.save(newUser);
            // Se é già presente, lancio eccezione :
        } else throw new BadRequestException("User with email '" + body.email() + "  already exists.");



    }


    public User updateUser(UUID userId, UserDTO body) {
        User found = this.getUserById(userId);
        found.setName(body.name());
        found.setSurname(body.surname());
        found.setUsername(body.username());
        found.setEmail(body.email());
        userDAO.save(found);
        return found;

    }

    public void deleteUser(UUID userId){
        User found = this.getUserById(userId);
        userDAO.delete(found);
    }

    public User findByEmail(String email){
        return userDAO.findByEmail(email).orElseThrow(()-> new NotFoundException("User with email " + email + " not found!"));
    }


}

