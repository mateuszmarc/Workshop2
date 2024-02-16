package pl.coderslab.classtest;

import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;

public class MainDao {
    public static void main(String[] args) {
        User user1 = new User(" ", " ", "");
        System.out.println(user1.getId());
        System.out.println(user1.getUsername());
        System.out.println(user1.getEmail());
        System.out.println(user1.getPassword());
        System.out.println(user1);
        System.out.println();
        user1.setUsername("Mateusz");
        user1.setEmail("mati@gmail.com");
        user1.setPassword("12345678901");
        System.out.println("User after changes : " + user1);

        UserDao userDao = new UserDao();

        User userWithId = userDao.create(user1);
        System.out.println("User after adding record to database: " + userWithId);

//        Adding user with the same email:
        User nextUser = new User("Tomasz", "mati@gmail.com", "mati12fffffffffffff3");
        System.out.println(nextUser);

        User userNotAdded = userDao.create(nextUser);
        if (userNotAdded == null) {
            System.out.println("New user not added");
        }

        System.out.println("\n\n\n\n\n");

//        for (int i = 0; i < 10; i++) {
//            String username = "test" + i;
//            String email = "test" + i + "@gmail.com";
//            String password = "test1234567" + i;
//            User userToAdd = new User(username, email, password);
//            userDao.create(userToAdd);
//        }

//        Testing read(int id) method
        for (int i = -1; i < 20; i++) {
            System.out.println(userDao.read(i));
        }
    }
}
