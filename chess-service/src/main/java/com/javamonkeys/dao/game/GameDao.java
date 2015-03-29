package com.javamonkeys.dao.game;

import com.javamonkeys.hibernate.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.javamonkeys.dao.user.User;
import java.util.Date;

@Repository
public class GameDao implements IGameDao{
    @Override
    public Game getGame(int id) {

        HibernateUtil.begin();
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("from Game where id = :id");
        query.setParameter("id", id);
        Game currentGame = (Game)query.uniqueResult();
        HibernateUtil.commit();
        HibernateUtil.close();

        return currentGame;
    }

    @Override
    public Game createGame(User author) {

        try {

            HibernateUtil.begin();
            Session session = HibernateUtil.getSession();
            Game newGame = new Game(author);
            session.save(newGame);
            HibernateUtil.commit();
            HibernateUtil.close();

            return newGame;

        } catch (HibernateException e) {
            HibernateUtil.rollback();
            HibernateUtil.close();
            throw e;
        }

    }

    @Override
    public Game updateGame(Game game) {

        try {

            HibernateUtil.begin();
            Session session = HibernateUtil.getSession();
            session.update(game);
            HibernateUtil.commit();
            HibernateUtil.close();

            return game;

        } catch (HibernateException e) {
            HibernateUtil.rollback();
            HibernateUtil.close();
            throw e;
        }
    }

    @Override
    public void saveTurn(int id, String turn) {

        Game currentGame = getGame(id);
        String newMoveText = currentGame.getMoveText()+turn;
        currentGame.setMoveText(newMoveText);
        updateGame(currentGame);
    }
}
