package ar.edu.iua.treban.dao;

import org.springframework.context.annotation.Bean;

public class FactoryDAO {
    private static FactoryDAO instance;

    private FactoryDAO() {}

    @Bean
    public static FactoryDAO getInstance() {
        if (instance == null) {
            instance = new FactoryDAO();
        }
        return instance;
    }

    public static IGenericDAO getTaskListDAO() {
        return TaskListImplDAO.getInstance();
    }
}