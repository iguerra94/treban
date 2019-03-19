package ar.edu.iua.treban.business.impl;

import java.util.List;


import ar.edu.iua.treban.SecurityConfiguration;
import ar.edu.iua.treban.business.exception.BusinessException;
import ar.edu.iua.treban.business.IUserBusiness;
import ar.edu.iua.treban.business.exception.UserNameOrEmailExistsException;
import ar.edu.iua.treban.model.User;
import ar.edu.iua.treban.model.exception.NotFoundException;
import ar.edu.iua.treban.model.persistence.UserRepository;
import ar.edu.iua.treban.model.util.UserDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBusiness implements IUserBusiness {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userDAO;

    @Autowired(required = true)
    private SecurityConfiguration securityConfiguration;

    @Override
    public User loadUserByUsername(String usernameOrEmail) throws BusinessException, NotFoundException {
        try {
            List<User> l = userDAO.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
            if (l.size() == 0) {
                throw new NotFoundException("No se encontró el usuario con id=" + usernameOrEmail);
            } else {
                return l.get(0);

            }

        } catch (Exception e) {
            throw new BusinessException(e);
        }

    }

    @Override
    public List<UserDTO> listUsersSintetico(boolean enabled) throws BusinessException {
        try {
            return userDAO.listUsersSintetico(enabled);

        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public void setPassword(String password, String userOrEmail) throws BusinessException, NotFoundException {
        try {
            if (userDAO.setPassword(password, userOrEmail, userOrEmail) == 0) {
                throw new NotFoundException("No se encontró el usuario " + userOrEmail);
            }
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public User addUser(User user) throws BusinessException, UserNameOrEmailExistsException {

        log.info("Info when adding one User: Starting method logs.");

        List<User> userNameOrEmailExists = userDAO.findByUsernameOrEmail(user.getUsername(), user.getEmail());

        if (userNameOrEmailExists.size() != 0) {
            log.info("Info when adding one User: Finished method logs.");
            throw new UserNameOrEmailExistsException("The User name or email entered already exists.");
        } else {

            try {

                user.setPassword(securityConfiguration.passwordEncoder().encode(user.getPassword()));
                User userSaved = userDAO.save(user);
                //log.info("Info when adding one new User: The User with id = {} was saved succesfully.", String.valueOf(userSaved.getIdUser()));
                return userSaved;

            } catch (Exception e) {
                throw new BusinessException(e);
            } finally {
                log.info("Info when adding one Task: Finished method logs.");
            }

        }

    }


}