package com.javamonkeys.dao.game;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
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
    public void saveTurn(Turn turn) {
        save(turn);
    }
    @Transactional
    public Turn getLastTurn(Game game) {

        Query query = getSession().createQuery("from Turn where game_id = :id order by id desc");
        query.setParameter("id", game.getId());
        query.setMaxResults(1);
        Turn lastTurn = (Turn) query.uniqueResult();

        return lastTurn;
    }
}
