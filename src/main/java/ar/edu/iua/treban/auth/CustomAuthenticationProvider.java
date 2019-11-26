package ar.edu.iua.treban.auth;

import ar.edu.iua.treban.model.Role;
import ar.edu.iua.treban.model.User;
import ar.edu.iua.treban.model.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String username = authentication.getName();
        final String password = authentication.getCredentials().toString();

        final Set<Role> userRoles = getUserIfAuthenticated(username, password);

        return new UsernamePasswordAuthenticationToken(username, null, getGrantedAuthorities(userRoles));
    }

    private Collection<? extends GrantedAuthority> getGrantedAuthorities(final Set<Role> userRoles) {
        return userRoles
                .stream()
                .map(role -> (GrantedAuthority) role::getName)
                .collect(Collectors.toList());
    }

    private Set<Role> getUserIfAuthenticated(final String username, final String password) {
        final User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Please ");
        }

        if (!user.getPassword().equals(password)) {
            throw new UsernameNotFoundException("Authentication failed");
        }

        return user.getRoles();
    }

    @Override
    public boolean supports(final Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}