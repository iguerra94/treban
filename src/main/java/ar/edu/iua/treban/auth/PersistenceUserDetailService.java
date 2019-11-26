package ar.edu.iua.treban.auth;

import ar.edu.iua.treban.model.User;
import ar.edu.iua.treban.model.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PersistenceUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.print(username);
		User r = userDAO.findByUsername(username);
		if (r == null)
			throw new UsernameNotFoundException("No se encuentra " + username);
		return r;
	}
}