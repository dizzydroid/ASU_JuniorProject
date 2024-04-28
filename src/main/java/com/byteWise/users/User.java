package src.main.java.com.byteWise.users;
public abstract class User {
    private int id;
    private String name;
    public static int userCount = 0;
    public int role; // 0 = Student, 1 = Instructor, 2 = Admin  
    // Constructor
    public User(int id, String name) {
        this.id = id;
        this.name = name;

    }

    // Abstract method to display user details
    public abstract void displayDetails();

    // Getters
    public int getId() {
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
