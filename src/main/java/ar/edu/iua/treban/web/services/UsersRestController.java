package ar.edu.iua.treban.web.services;

import java.net.URI;
import java.util.List;

import ar.edu.iua.treban.business.exception.UserNameOrEmailExistsException;
import ar.edu.iua.treban.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.iua.treban.business.exception.BusinessException;
import ar.edu.iua.treban.business.IUserBusiness;
import ar.edu.iua.treban.model.util.UserDTO;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(Constantes.URL_USERS)
public class UsersRestController {

	@Autowired
	private IUserBusiness userBusiness;

	
	//curl -X POST 'http://localhost:8080/dologin' -H 'Content-Type: application/x-www-form-urlencoded' -d 'username=admin&password=password' -c cookies.txt
	//curl 'http://localhost:8080/api/v1/users/sintetico/enabled' -b cookies.txt -v
	@GetMapping(value = "/sintetico/enabled")
	public ResponseEntity<List<UserDTO>> listSinteticoEnabled() {
		try {
			return new ResponseEntity<List<UserDTO>>(userBusiness.listUsersSintetico(true), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<List<UserDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@GetMapping(value = "/sintetico/disabled")
	public ResponseEntity<List<UserDTO>> listSinteticoDisabled() {
		try {
			return new ResponseEntity<List<UserDTO>>(userBusiness.listUsersSintetico(false), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<List<UserDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

    @PostMapping(value = {"", "/"})
    public ResponseEntity<User> addUser(@RequestBody User user, UriComponentsBuilder uriComponentsBuilder, HttpServletRequest request) {
        try {
            User userCreated = userBusiness.addUser(user);

            URI locationURI = uriComponentsBuilder
                    .path(Constantes.URL_USERS + "/{id}")
                    .buildAndExpand(userCreated.getIdUser())
                    .toUri();
            return ResponseEntity.created(locationURI).body(userCreated);
        } catch (UserNameOrEmailExistsException e) {
            return new CustomizedResponseEntityExceptionHandler().handleTaskNameExistsException(e, request);
        } catch (BusinessException e) {
            return new CustomizedResponseEntityExceptionHandler().handleAllExceptions(e, request);
        }
    }



















}
