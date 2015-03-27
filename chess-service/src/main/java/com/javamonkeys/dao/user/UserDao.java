package com.javamonkeys.dao.user;

import com.javamonkeys.hibernate.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Class which implemented IUserDao interface.
 * Use this class for manage next type of user data:
 *   - class "User" (create/get/delete)
 *   - class "UserAccessGroup" (create/get/delete)
 */
@Repository
public class UserDao implements IUserDao {

    /**
     * Find user by email.
     * @param  email user email
     * @return user
     * @throws UserNotFoundException if user doesn't exist in DataBase
     */
    public User getUser(String email) throws UserNotFoundException {

        if(email!=null) {

            HibernateUtil.begin();
            Session session = HibernateUtil.getSession();
            Query query = session.createQuery("from User where email = :email");
            query.setParameter("email", email);
            User currentUser = (User)query.uniqueResult();
            HibernateUtil.commit();
            HibernateUtil.close();

            if (currentUser == null) {
                throw new UserNotFoundException("User with email: " + email + " was not found");
            }

            return currentUser;

        }else{
            throw new UserNotFoundException("User with null email was not found");
        }

    }

    /**
     * Create new user.
     * @param email new user email
     * @param password new user password
     * @param userAccessGroup new user access group
     * @return new user
     * @throws UserAlreadyExistException if user which this email already exist
     */
    public User createUser(String email, String password, UserAccessGroup userAccessGroup) throws UserAlreadyExistException,
            IllegalArgumentException {

        if(email==null) {
            throw new IllegalArgumentException("email: " + email + " is not valid");
        } else if (password==null) {
            throw new IllegalArgumentException("password: " + password + " is not valid");
        } else if (userAccessGroup==null) {
            throw new IllegalArgumentException("userAccessGroup: " + userAccessGroup + " is not valid");
        } else {

            try {
                User currentUser = getUser(email);
                if (currentUser != null) {
                    throw new UserAlreadyExistException("User with email: " + email + " is already exist.");
                }
            } catch (UserNotFoundException e) {
                // ok - create a new user
            }

            try {

                HibernateUtil.begin();
                Session session = HibernateUtil.getSession();
                User newUser = new User(email, password, userAccessGroup);
                session.save(newUser);
                HibernateUtil.commit();
                HibernateUtil.close();

                return newUser;

            } catch (HibernateException e) {
                HibernateUtil.rollback();
                HibernateUtil.close();
                throw e;
            }
        }
    }

    /**
     * Create new user.
     * @param email new user email
     * @param password new user password
     * @param userAccessGroup new user access group
     * @param birthDate new user birth date
     * @return new User
     * @throws UserAlreadyExistException if user which this email already exist
     */
    public User createUser(String email, String password, UserAccessGroup userAccessGroup, Date birthDate) throws UserAlreadyExistException,
            IllegalArgumentException {

        if(email==null) {
            throw new IllegalArgumentException("argument \"email\": value " + email + " is not valid");
        } else if (password==null) {
            throw new IllegalArgumentException("argument \"password\": value " + password + " is not valid");
        } else if (userAccessGroup==null) {
            throw new IllegalArgumentException("argument \"userAccessGroup\": value " + userAccessGroup + " is not valid");
        } else if (birthDate==null) {
            throw new IllegalArgumentException("argument \"birthDate\": value " + birthDate + " is not valid");
        } else {

            try {
                User currentUser = getUser(email);
                if (currentUser != null) {
                    throw new UserAlreadyExistException("User with email: " + email + " is already exist.");
                }
            } catch (UserNotFoundException e) {
                // ok - create a new user
            }

            try {

                HibernateUtil.begin();
                Session session = HibernateUtil.getSession();
                User newUser = new User(email, password, birthDate, userAccessGroup);
                session.save(newUser);
                HibernateUtil.commit();
                HibernateUtil.close();

                return newUser;

            } catch (HibernateException e) {
                HibernateUtil.rollback();
                HibernateUtil.close();
                throw e;
            }
        }
    }

    /**
     * Delete user.
     * @param user user which need to delete
     * @throws UserNotFoundException if this user doesn't exist in DataBase
     */
    public  void deleteUser(User user) throws UserNotFoundException, IllegalArgumentException {

        if(user!=null) {

            try {
                User currentUser = getUser(user.getEmail());

                try {

                    HibernateUtil.begin();
                    Session session = HibernateUtil.getSession();
                    session.delete(currentUser);
                    HibernateUtil.commit();
                    HibernateUtil.close();

                } catch (HibernateException e) {
                    HibernateUtil.rollback();
                    HibernateUtil.close();
                    throw e;
                }

            } catch (UserNotFoundException e) {
                // user not found - throw exception
                throw e;
            }
        } else {
            throw new UserNotFoundException("User: " + user + " was not found");
        }
    }

    /**
     * Find user access group by name.
     * @param name name of group
     * @return user access group
     * @throws UserAccessGroupNotFoundException if this user access group doesn't exist in DataBase
     */
    public UserAccessGroup getUserAccessGroup(String name) throws UserAccessGroupNotFoundException {

        if(name!=null) {

            HibernateUtil.begin();
            Session session = HibernateUtil.getSession();
            Query query = session.createQuery("from UserAccessGroup where name = :name");
            query.setParameter("name", name);
            UserAccessGroup currentGroup = (UserAccessGroup) query.uniqueResult();
            HibernateUtil.commit();
            HibernateUtil.close();

            if (currentGroup == null) {
                throw new UserAccessGroupNotFoundException("User access group with name: " + name + " not found.");
            }

            return currentGroup;

        } else {
            throw new UserAccessGroupNotFoundException("User access group with name: " + name + " not found.");
        }

    }

    /**
     * Create new user access group.
     * @param name user access group name
     * @param isAdmin if admin - true, else - false
     * @return new user access group
     * @throws UserAccessGroupAlreadyExistException if user access group which this name already exists
     */
    public UserAccessGroup createUserAccessGroup(String name, boolean isAdmin) throws UserAccessGroupAlreadyExistException,
            IllegalArgumentException {

        if (name != null) {

            try {
                UserAccessGroup currentGroup = getUserAccessGroup(name);
                if (currentGroup != null) {
                    throw new UserAccessGroupAlreadyExistException("User access group with name: " + name + " is already exist.");
                }
            } catch (UserAccessGroupNotFoundException e) {
                // ok - create a new user access group
            }

            try {

                HibernateUtil.begin();
                Session session = HibernateUtil.getSession();
                UserAccessGroup newGroup = new UserAccessGroup(name, isAdmin);
                session.save(newGroup);
                HibernateUtil.commit();
                HibernateUtil.close();

                return newGroup;

            } catch (HibernateException e) {
                HibernateUtil.rollback();
                HibernateUtil.close();
                throw e;
            }
        } else {
            throw new IllegalArgumentException("argument \"name\": value " + name + " is not valid");
        }
    }

    /**
     * Delete user access group.
     * @param userAccessGroup user access group which need to delete
     * @throws UserAccessGroupNotFoundException if this user access group doesn't exist in DataBase
     */
    public void deleteUserAccessGroup(UserAccessGroup userAccessGroup) throws UserAccessGroupNotFoundException,
            IllegalArgumentException {

        if(userAccessGroup!=null) {
            try {
                UserAccessGroup currentGroup = getUserAccessGroup(userAccessGroup.getName());

                try {

                    HibernateUtil.begin();
                    Session session = HibernateUtil.getSession();
                    session.delete(currentGroup);
                    HibernateUtil.commit();
                    HibernateUtil.close();

                } catch (HibernateException e) {
                    HibernateUtil.rollback();
                    HibernateUtil.close();
                    throw e;
                }

            } catch (UserAccessGroupNotFoundException e) {
                // user not found - throw exception
                throw e;
            }
        } else {
            throw new UserAccessGroupNotFoundException("User access group: " + userAccessGroup + " was not found");
        }
    }

}
