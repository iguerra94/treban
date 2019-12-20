package ar.edu.iua.treban.web.services;

import ar.edu.iua.treban.business.IUserBusiness;
import ar.edu.iua.treban.business.exception.BusinessException;
import ar.edu.iua.treban.business.exception.UserEmailNotRegisteredException;
import ar.edu.iua.treban.model.CustomResponse;
import ar.edu.iua.treban.model.ErrorDetails;
import ar.edu.iua.treban.model.exception.EmptyFieldsException;
import ar.edu.iua.treban.model.exception.UserEmailInvalidException;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
public class CoreRestController extends BaseRestController {

    @Autowired
    private IUserBusiness userBusiness;

    @GetMapping(Constantes.URL_VERIFY_EMAIL)
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
    public ResponseEntity deny(HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Unauthorized. Authentication required.", request.getServletPath());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
    }

    @GetMapping(Constantes.URL_SIGNIN_FAILURE)
    public ResponseEntity loginFailure(HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Authentication Failed. Review the password entered.", request.getServletPath());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorDetails);
    }

    @GetMapping(Constantes.URL_LOGOUT)
    public ResponseEntity logoutok() {
        CustomResponse customResponse = new CustomResponse("LOGOUT OK");
        return ResponseEntity.ok(customResponse);
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