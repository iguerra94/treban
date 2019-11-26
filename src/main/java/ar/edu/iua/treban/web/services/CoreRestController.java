package ar.edu.iua.treban.web.services;

import ar.edu.iua.treban.auth.exception.AuthenticationException;
import ar.edu.iua.treban.business.IUserBusiness;
import ar.edu.iua.treban.business.exception.BusinessException;
import ar.edu.iua.treban.business.exception.UserEmailNotRegisteredException;
import ar.edu.iua.treban.model.exception.EmptyFieldsException;
import ar.edu.iua.treban.model.exception.UserEmailInvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CoreRestController {

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

//    @GetMapping(value = "/signup")
//    public ModelAndView signUpPage(HttpServletRequest request) {
//        return new ModelAndView("signup");
//    }

}