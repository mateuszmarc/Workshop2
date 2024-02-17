package pl.coderslab.entity;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.dbmanagement.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class UserDao {
    public static final String CREATE_USER_QUERY = "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
    public static final String UPDATE_USER_QUERY = "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?";
    public static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id = ?";
    public static final String READ_USER_QUERY = "SELECT * FROM users WHERE id = ?";
    public static final String READ_ALL_USERS = "SELECT * FROM users";

    public User create(User user) {
        String username = user.getUsername();
        String email = user.getEmail();
        String plainPassword = user.getPassword();

        if (username == null || email == null || plainPassword == null) {
            System.out.println("Incorrect values. User data fields must not be null values");
            return null;
        }

        try (Connection connection = DbUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {
            String hashedPassword = hashPassword(plainPassword);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, hashedPassword);

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                user.setId(id);
            }
            return user;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    public User read(int userId) {
        try (Connection connection = DbUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(READ_USER_QUERY)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            User[] retrievedUsers = createUsersFromResultSet(resultSet);
            if (retrievedUsers.length != 0) {
                return retrievedUsers[0];
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("There was no user with given id = " + userId);
        return null;
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public void update(User user) {
        int id = user.getId();
        String username = user.getUsername();
        String email = user.getEmail();
        String password = user.getPassword();
        int updatedRows = 0;

        if (username == null || username.isBlank() || email == null || email.isBlank() || password == null || password.isBlank()) {
            System.out.println("Invalid data provided for update");
            System.out.println(updatedRows + " rows updated");

            return;
        }

        try (Connection connection = DbUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_QUERY)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            String hashedPassword = hashPassword(password);
            preparedStatement.setString(3, hashedPassword);
            preparedStatement.setInt(4, id);

            updatedRows = preparedStatement.executeUpdate();

            if (updatedRows == 0) {
                System.out.println("There was no record to update that has id = " + id);
            }

            System.out.println(updatedRows + " rows updated");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(int userId) {
        try (Connection connection = DbUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_QUERY)) {
            preparedStatement.setInt(1, userId);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("There is no user record having id = " + userId);
            }
            System.out.println(affectedRows + " rows affected");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public User[] findAll() {
        User[] retrievedUsers = new User[0];
        try (Connection connection = DbUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(READ_ALL_USERS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            retrievedUsers = createUsersFromResultSet(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return retrievedUsers;
    }

    private User[] addToArray(User user, User[] users) {
        User[] tempArray = Arrays.copyOf(users, users.length +1);
        tempArray[users.length] = user;
        return tempArray;
    }

    private User[] createUsersFromResultSet(ResultSet resultSet) throws SQLException {
        User[] retrievedUsers = new User[0];
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            User user = new User(username, email, password);
            user.setId(id);
            retrievedUsers = addToArray(user, retrievedUsers);
        }
        return retrievedUsers;
    }


}
