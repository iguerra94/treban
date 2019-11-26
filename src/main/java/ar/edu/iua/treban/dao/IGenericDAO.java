package ar.edu.iua.treban.dao;

import java.io.Serializable;
import java.util.List;

public interface IGenericDAO<T, ID extends Serializable> {
    List<T> getAll();
    T findByName(String name);
    T add(T object);
}