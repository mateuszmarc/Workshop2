package pl.coderslab.entity;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;

    public User(String name, String email, String password) {
        this.id = 0;
        setUsername(name);
        setEmail(email);
        setPassword(password);
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        if (id != 0) {
            this.id = id;
            return;
        }
        System.out.println("Incorrect value for id parameter");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username != null && !username.isBlank()) {
            this.username = username;
            return;
        }
        System.out.println("Incorrect value for username parameter");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password != null && password.length() > 10 && !password.isBlank()) {
            this.password = password;
            return;
        }
        System.out.println("Incorrect value for password parameter");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null && !email.isBlank()) {
            this.email = email;
            return;
        }
        System.out.println("Incorrect value for email parameter");
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {Id: " + id + ", name: " + this.username + ", email: " + this.email + ", password: " + this.password + "}";
    }

}