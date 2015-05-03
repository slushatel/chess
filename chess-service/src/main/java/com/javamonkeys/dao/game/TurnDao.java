package com.javamonkeys.dao.game;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class TurnDao implements ITurnDao{

    @Autowired
    private SessionFactory hibernateSessionFactory;

    private Session getSession() {
        return hibernateSessionFactory.getCurrentSession();
    }

    private void save(Object entity) {
        getSession().save(entity);
    }
    @Transactional
    public void SaveTurn(Turn turn) {
        save(turn);
    }
}
