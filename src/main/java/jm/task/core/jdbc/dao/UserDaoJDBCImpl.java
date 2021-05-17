package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {

    //Connection connection = getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable()  {
        String sql = "CREATE TABLE `users`.`users` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NOT NULL,\n" +
                "  `lastname` VARCHAR(45) NOT NULL,\n" +
                "  `age` INT(3) NOT NULL,\n" +
                "  PRIMARY KEY (`id`))\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8";

        try(Connection connection = getConnection();
            Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {

        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE `users`.`users`";

        try(Connection connection = getConnection();
            Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {

        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "insert into users (name, lastname, age) values (?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);

            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {

        }
    }

    public void removeUserById(long id) {
        String sql = "delete from users where id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(3, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {

        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        String sql = "select id, name, lastname, age from users";

        try(Connection connection = getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));

                userList.add(user);
            }
        } catch (SQLException e) {

        }

        return userList;
    }

    public void cleanUsersTable() {
        String sql = "delete from users";

        try(Connection connection = getConnection();
            Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {

        }
    }
}
