package wendydeluca.u5d11.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import wendydeluca.u5d11.entities.User;
import wendydeluca.u5d11.exceptions.UnauthorizedException;
import wendydeluca.u5d11.payloads.LoginDTO;
import wendydeluca.u5d11.security.JWTTools;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    public JWTTools jwttTools;
    public String authenticateUserAndGenerateToken(LoginDTO payload){
        // 1. Fare un controllo delle credenziali d'accesso dell'utente
        // 1.1 Verificare che l'email del db sia quella ricevuta nel payload
        User user = userService.findByEmail(payload.email());
        // 1.2 Verificare che la password del db corrisponda a quella in entrata nel payload
        if(user.getPassword().equals(payload.password())){
            // 2. Se i controlli sono OK, genera il token
            return this.jwttTools.createToken(user);
        } else{
            // 3. Se non sono ok, 401 (unauthorized)
            throw new UnauthorizedException("Invalid login credentials! Try again.");
        }

        



    }
}
