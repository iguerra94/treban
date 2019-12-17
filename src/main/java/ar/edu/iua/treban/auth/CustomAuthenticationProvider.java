package ar.edu.iua.treban.auth;

import ar.edu.iua.treban.model.Role;
import ar.edu.iua.treban.model.User;
import ar.edu.iua.treban.model.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        Set<Role> userRoles = getUserIfAuthenticated(username, password);

        return new UsernamePasswordAuthenticationToken(username, password, getGrantedAuthorities(userRoles));
    }

    private Collection<? extends GrantedAuthority> getGrantedAuthorities(Set<Role> userRoles) {
        List<GrantedAuthority> authorities = userRoles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return authorities;
    }

    private Set<Role> getUserIfAuthenticated(final String username, final String password) {
        User user = userRepository.findByUsername(username);

        if (user == null || !user.getPassword().equals(password)) {
            throw new BadCredentialsException("The password entered is not correct.");
        }

        return user.getRoles();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}