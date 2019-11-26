package ar.edu.iua.treban.web.services;

import ar.edu.iua.treban.model.User;
import org.jooq.tools.json.JSONArray;
import org.jooq.tools.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class BaseRestController {

    protected User getUserPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }

    @SuppressWarnings("unchecked")
    protected JSONObject userToJson(User u) {
        JSONObject o = new JSONObject();
        o.put("username", u.getUsername());
        o.put("name", u.getName());
        o.put("code", 0);
        JSONArray r = new JSONArray();
        for (GrantedAuthority g : u.getAuthorities()) {
            r.add(g.getAuthority());
        }
        o.put("roles", r);

        return o;
    }


}
