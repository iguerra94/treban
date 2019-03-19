package ar.edu.iua.treban.web.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import ar.edu.iua.treban.model.User;

public class BaseRestController {
	protected User getUserPrincipal () {
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		User u=(User)auth.getPrincipal();
		return u;
	}
}
