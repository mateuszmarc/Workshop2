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

        for (int i = 0; i < 10; i++) {
            String username = "test" + i;
            String email = "test" + i + "@gmail.com";
            String password = "test1234567" + i;
            User userToAdd = new User(username, email, password);
            userDao.create(userToAdd);
        }

//        Testing read(int id) method
        for (int i = -1; i < 20; i++) {
            System.out.println(userDao.read(i));
        }

//        Testing update(User user) method
        System.out.println("\n\n\n\n\n");
        User userToUpdate = new User("Mate", "mate1@mail.com", "testtesttest");
        userToUpdate.setId(1);
        System.out.println("User details before update : " + userDao.read(1));
        userDao.update(userToUpdate);
        System.out.println("User details after update : " + userDao.read(1));
        System.out.println();
        System.out.println("\n\n");
        User user2ToUpdate = new User("Emi", "emi123@gmail.com", "emi1234567890");
        user2ToUpdate.setId(5555);

        userDao.update(user2ToUpdate);

        User userBlankDataFields = new User("", "testtest", "1234567890");
        userBlankDataFields.setId(1);
        System.out.println("\n");
        userDao.update(userBlankDataFields);
        System.out.println("\n");
        userBlankDataFields.setUsername("username");
        userBlankDataFields.setEmail("");
        userDao.update(userBlankDataFields);
        System.out.println("\n");
        userBlankDataFields.setEmail("testtest@gmail.com");
        userBlankDataFields.setPassword("ddd");
        userDao.update(userBlankDataFields);
        System.out.println("\n");
        userBlankDataFields.setPassword("1234567890--");
        userBlankDataFields.setUsername(null);
        System.out.println(userBlankDataFields);
        userDao.update(userBlankDataFields);
        System.out.println("\n");
        userBlankDataFields.setUsername("mateuszmateusz");
        userBlankDataFields.setEmail(null);
        System.out.println(userBlankDataFields);

        User retrievedUser2 = userDao.read(11);
        System.out.println(retrievedUser2);

        retrievedUser2.setUsername("changedUsername");
        retrievedUser2.setEmail("changedemail@gmail.com");
        retrievedUser2.setPassword("changedPassword");
        userDao.update(retrievedUser2);

        System.out.println("User after update:" + userDao.read(11));
    }
}
