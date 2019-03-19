package ar.edu.iua.treban.dao;

import java.io.Serializable;

public interface IGenericDAO<T, ID extends Serializable> {
    T add(T object);
    T findByName(String name);
}