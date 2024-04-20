package src.main.java.com.byteWise.users;
public abstract class User {
    private String id;
    private String name;
    private String email;

    // Constructor
    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Abstract method to display user details
    public abstract void displayDetails();

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
