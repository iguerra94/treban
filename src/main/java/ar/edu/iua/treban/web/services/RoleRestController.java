package ar.edu.iua.treban.web.services;

import ar.edu.iua.treban.business.exception.BusinessException;
import ar.edu.iua.treban.business.IRoleBussines;
import ar.edu.iua.treban.model.Role;
import ar.edu.iua.treban.model.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Constantes.URL_ROLES)
public class RoleRestController {


    @Autowired
    private IRoleBussines roleBussines;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Role>> all() {
        try {

            return new ResponseEntity(roleBussines.getAll(), HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Role> one(@PathVariable("id") int id) {
        try {
            return new ResponseEntity(roleBussines.getOne(id), HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }


}
