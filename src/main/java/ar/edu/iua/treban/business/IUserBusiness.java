package ar.edu.iua.treban.business;

import ar.edu.iua.treban.business.exception.*;
import ar.edu.iua.treban.model.User;
import ar.edu.iua.treban.model.exception.*;

import java.util.List;

public interface IUserBusiness {
    List<User> getUsers() throws BusinessException;
    User getUser(String email) throws BusinessException, NotFoundException;
    User addUser(User user) throws BusinessException, EmptyFieldsException, AlreadyExistsException, UserEmailInvalidException;
}