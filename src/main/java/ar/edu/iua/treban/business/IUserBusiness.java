package ar.edu.iua.treban.business;

import java.util.List;

import ar.edu.iua.treban.business.exception.BusinessException;
import ar.edu.iua.treban.business.exception.UserNameOrEmailExistsException;
import ar.edu.iua.treban.model.User;
import ar.edu.iua.treban.model.exception.NotFoundException;
import ar.edu.iua.treban.model.util.UserDTO;

public interface IUserBusiness {

	public User loadUserByUsername(String usernameOrEmail) throws BusinessException, NotFoundException;
	
	public List<UserDTO> listUsersSintetico(boolean enabled) throws BusinessException;

	public void setPassword(String password, String userOrEmail) throws BusinessException, NotFoundException;

    public User addUser(User user) throws BusinessException, UserNameOrEmailExistsException;

}
