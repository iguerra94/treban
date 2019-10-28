package ar.edu.iua.treban.business.impl;

import ar.edu.iua.treban.business.IUserBusiness;
import ar.edu.iua.treban.business.exception.AlreadyExistsException;
import ar.edu.iua.treban.business.exception.BusinessException;
import ar.edu.iua.treban.model.Role;
import ar.edu.iua.treban.model.User;
import ar.edu.iua.treban.model.exception.EmptyFieldsException;
import ar.edu.iua.treban.model.exception.NotFoundException;
import ar.edu.iua.treban.model.exception.UserEmailInvalidException;
import ar.edu.iua.treban.model.persistence.RoleRepository;
import ar.edu.iua.treban.model.persistence.UserRepository;
import ar.edu.iua.treban.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserBusinessImpl implements IUserBusiness {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userDAO;

    @Autowired
    private RoleRepository roleDAO;

    @Override
    public List<User> getUsers() throws BusinessException {
        return null;
    }

    @Override
    public User getUser(String email) throws BusinessException, NotFoundException {
        return null;
    }

    @Override
    public User addUser(User user) throws BusinessException, EmptyFieldsException, AlreadyExistsException, UserEmailInvalidException {
        log.info("Info when adding the User: Starting method logs.");

        if (user.getName() == null || user.getEmail() == null || user.getUsername() == null || user.getPassword() == null) {
            log.error("Error when adding the User: name, email, username and password fields must be entered.");
            log.info("Info when adding the User: Finished method logs.");
            throw new BusinessException("name, email, username and password fields must be entered.");
        }

        if (user.getName().trim().length() == 0 || user.getEmail().trim().length() == 0 || user.getUsername().trim().length() == 0 || user.getPassword().trim().length() == 0) {
            log.error("Error when adding the User: Neither the name, email, username and password might be empty.");
            log.info("Info when adding the User: Finished method logs.");
            throw new EmptyFieldsException("Neither the name, email, username and password might be empty.");
        }

        User userEmailExists = userDAO.findByEmail(user.getEmail());

        if (userEmailExists != null) {
            log.error("Error when adding the User: The email entered already exists.");
            log.info("Info when adding the User: Finished method logs.");
            throw new AlreadyExistsException("The email entered already exists.");
        }

        User userUsernameExists = userDAO.findByUsername(user.getUsername());

        if (userUsernameExists != null) {
            log.error("Error when adding the User: The username entered already exists.");
            log.info("Info when adding the User: Finished method logs.");
            throw new AlreadyExistsException("The username entered already exists.");
        }

        boolean isEmailValid = UserUtils.isEmailValid(user.getEmail());

        if (!isEmailValid) {
            log.error("Error when adding the User: The email entered is not valid.");
            log.info("Info when adding the User: Finished method logs.");
            throw new UserEmailInvalidException("The email entered is not valid.");
        }

        log.info("Info when adding the User: email entered is valid.");

        try {
            user.setEnabled(true);
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);

            Role devRole = roleDAO.findByName("DEV");

            Set<Role> userRoles = new HashSet<>();
            userRoles.add(devRole);

            user.setRoles(userRoles);

            User userSaved = userDAO.save(user);
            log.info("Info when adding the User: The User with id = {} was saved succesfully.", userSaved.getId());
            log.info("Info when adding the User: Finished method logs.");

            return userSaved;
        } catch (Exception e) {
            log.error("Error when adding the User: {}.", e.getMessage());
            throw new BusinessException(e);
        }
    }

}
