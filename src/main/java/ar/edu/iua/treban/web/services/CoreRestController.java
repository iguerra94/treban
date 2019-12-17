package ar.edu.iua.treban.web.services;

import ar.edu.iua.treban.business.IUserBusiness;
import ar.edu.iua.treban.business.exception.BusinessException;
import ar.edu.iua.treban.business.exception.UserEmailNotRegisteredException;
import ar.edu.iua.treban.model.exception.EmptyFieldsException;
import ar.edu.iua.treban.model.exception.UserEmailInvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CoreRestController extends BaseRestController {

    @Autowired
    private IUserBusiness userBusiness;

    @GetMapping(value = "/verify_email")
    public ResponseEntity<Object> verifyUserEmail(
            @RequestParam(value = "email", defaultValue = "*") String email,
             HttpServletRequest request) {
        try {
            return ResponseEntity.ok(userBusiness.verifyUserByEmail(email));
        } catch (EmptyFieldsException e) {
            return new CustomizedResponseEntityExceptionHandler().handleEmptyFieldsException(e, request);
        } catch (UserEmailInvalidException e) {
            return new CustomizedResponseEntityExceptionHandler().handleUserEmailInvalidException(e, request);
        } catch (UserEmailNotRegisteredException e) {
            return new CustomizedResponseEntityExceptionHandler().handleUserEmailNotRegisteredException(e, request);
        } catch (BusinessException e) {
            return new CustomizedResponseEntityExceptionHandler().handleAllExceptions(e, request);
        }
    }

    @GetMapping(Constantes.URL_DENY)
    public ResponseEntity<String> deny(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping(Constantes.URL_LOGOUT)
    public ResponseEntity<String> logoutok() {
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = Constantes.URL_LOGINOK)
    public ResponseEntity<String> loginok() {
        String u = userToJson(getUserPrincipal()).toString();
        return ResponseEntity.ok(u);
    }

    @GetMapping(value = Constantes.URL_AUTH_INFO)
    public ResponseEntity<String> authInfo() {
        String u = userToJson(getUserPrincipal()).toString();
        return ResponseEntity.ok(u);
    }

}