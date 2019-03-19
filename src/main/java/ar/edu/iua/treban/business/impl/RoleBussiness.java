package ar.edu.iua.treban.business.impl;

import ar.edu.iua.treban.business.exception.BusinessException;
import ar.edu.iua.treban.business.IRoleBussines;
import ar.edu.iua.treban.model.Role;
import ar.edu.iua.treban.model.exception.NotFoundException;
import ar.edu.iua.treban.model.persistence.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleBussiness implements IRoleBussines {


    @Autowired
    private RoleRepository roleDAO;

    @Override
    public List<Role> getAll() throws BusinessException {
        try {
            return roleDAO.findAll();
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public Role getOne(int id) throws BusinessException, NotFoundException {
        Optional<Role> p = null;

        try {
            p = roleDAO.findById(id);
        } catch (Exception e) {
            throw new BusinessException(e);
        }

        if (!p.isPresent())
            throw new NotFoundException("No ha sido encontrado");
        return p.get();

    }


}
