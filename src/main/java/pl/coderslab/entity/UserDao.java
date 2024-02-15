package pl.coderslab.entity;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.dbmanagement.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
