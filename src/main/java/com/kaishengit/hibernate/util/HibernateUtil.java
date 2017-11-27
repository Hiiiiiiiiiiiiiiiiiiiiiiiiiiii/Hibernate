package com.kaishengit.hibernate.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    private static SessionFactory sessionFactory = builderSessionFactory();

    public static SessionFactory builderSessionFactory() {
        Configuration configuration = new Configuration().configure();
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    public static Session getSession(){
        return sessionFactory.getCurrentSession();
    }

}
