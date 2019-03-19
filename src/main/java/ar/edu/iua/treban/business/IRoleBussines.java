package ar.edu.iua.treban.business;

import ar.edu.iua.treban.business.exception.BusinessException;
import ar.edu.iua.treban.model.Role;
import ar.edu.iua.treban.model.exception.NotFoundException;

import java.util.List;

public interface IRoleBussines {

    public List<Role> getAll() throws BusinessException;
    public Role getOne(int id) throws BusinessException, NotFoundException;

}
