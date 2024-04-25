package src.main.java.com.byteWise.users;
public abstract class User {
    private String id;
    private String name;
    private static int userCount = 0;
    protected int role;
    // Constructor
    public User(String id, String name) {
        this.id = id;
        this.name = name;

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

    // Setters
    public void setName(String name) {
        this.name = name;
    }

}
