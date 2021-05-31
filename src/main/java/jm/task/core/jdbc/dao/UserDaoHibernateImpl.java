package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl extends Util implements UserDao {

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users ( " +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(20) NOT NULL, " +
                "lastName VARCHAR(20) NOT NULL, " +
                "age INT NOT NULL);";


        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery(sql).executeUpdate();
        session.getTransaction().commit();
        session.close();
        System.out.println("Таблица создана");


    }

    @Override
    public void dropUsersTable() {

        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
        session.getTransaction().commit();
        session.close();
        System.out.println("Таблица удалена");


    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);

        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
        System.out.println("Пользователь с имененм " + name + " добавлен");

    }

    @Override
    public void removeUserById(long id) {

        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(session.get(User.class, id));
        session.getTransaction().commit();
        session.close();
        System.out.println("Пользователь удален");

    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        String sql = "from User";

        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        result = session.createQuery(sql).list();
        session.getTransaction().commit();
        session.close();

        return result;
    }

    @Override
    public void cleanUsersTable() {

        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery("delete from users").executeUpdate();
        session.getTransaction().commit();
        session.close();
        System.out.println("Таблица очищена");

    }
}
