package ar.edu.iua.treban.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ar.edu.iua.treban.business.exception.BusinessException;
import ar.edu.iua.treban.business.IUserBusiness;
import ar.edu.iua.treban.model.exception.NotFoundException;

@Service
public class PersistenceUserDetailService implements UserDetailsService{

	@Autowired
	private IUserBusiness userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			return userService.loadUserByUsername(username);
		} catch (BusinessException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (NotFoundException e) {
			throw new UsernameNotFoundException(e.getMessage(),e);
		}
	}

}
