package com.javamonkeys.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {

    private static final ThreadLocal session = new ThreadLocal();
    private static final SessionFactory sessionFactory =
            new AnnotationConfiguration().configure().buildSessionFactory();

    public static Session getSession() {
        Session session = (Session) HibernateUtil.session.get();
        if (session == null) {
            session = sessionFactory.openSession();
            HibernateUtil.session.set(session);
        }
        return session;
    }

    public static void begin() {
        getSession().beginTransaction();
    }

    public static void commit() {
        getSession().getTransaction().commit();
    }

    public static void rollback() {
        try {
            getSession().getTransaction().rollback();
        } catch (HibernateException e) {
            throw e;
        }
        try {
            getSession().close();
        } catch (HibernateException e) {
            throw e;
        }
        HibernateUtil.session.set(null);
    }

    public static void close() {
        getSession().close();
        HibernateUtil.session.set(null);
    }

}
