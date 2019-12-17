package ar.edu.iua.treban.web.services;

import ar.edu.iua.treban.business.IUserBusiness;
import ar.edu.iua.treban.business.exception.AlreadyExistsException;
import ar.edu.iua.treban.business.exception.BusinessException;
import ar.edu.iua.treban.model.User;
import ar.edu.iua.treban.model.exception.EmptyFieldsException;
import ar.edu.iua.treban.model.exception.UserEmailInvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
@RequestMapping(Constantes.URL_USERS)
public class UserRestController {

    @Autowired
    private IUserBusiness userBusiness;

    @PostMapping(value = {"", "/"})
    public ResponseEntity<User> addUser(@RequestBody User user, UriComponentsBuilder uriComponentsBuilder, HttpServletRequest request) {
        try {
            User userCreated = userBusiness.addUser(user);

            URI locationURI = uriComponentsBuilder
                    .path(Constantes.URL_USERS + "/{id}")
                    .buildAndExpand(userCreated.getId())
                    .toUri();

            return ResponseEntity.created(locationURI).body(userCreated);
        } catch (EmptyFieldsException e) {
            return new CustomizedResponseEntityExceptionHandler().handleEmptyFieldsException(e, request);
        } catch (AlreadyExistsException e) {
            return new CustomizedResponseEntityExceptionHandler().handleAlreadyExistsException(e, request);
        } catch (UserEmailInvalidException e) {
            return new CustomizedResponseEntityExceptionHandler().handleUserEmailInvalidException(e, request);
        } catch (BusinessException e) {
            return new CustomizedResponseEntityExceptionHandler().handleAllExceptions(e, request);
        }

    }

}