package src.main.java.filesystem; 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.google.gson.*;
import src.main.java.com.byteWise.courses.Course;
import src.main.java.com.byteWise.users.Instructor;
import src.main.java.com.byteWise.users.Student;
import src.main.java.com.byteWise.users.User;
import src.main.java.com.byteWise.users.Admin.UserAlreadyExistsException;
import src.main.java.com.byteWise.users.Admin.UserNotFoundException;

public final class Read_Write {

    private static final String FILEPATH = "C:\\Uni_Projects\\Advanced\\ASU_JuniorProject\\src\\main\\java\\filesystem\\";
    private static final String USERS_CSV_FILEName = "Users.csv";
    public static void Signup(String username ,String password, int role) throws UserAlreadyExistsException{
        //should check if the user already exists and throw an exception if they do
        try{Login(username, password);
            throw new UserAlreadyExistsException("User already exists");}
        catch(UserNotFoundException e){ 
            //if the user is not found, write the user to the file
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILEPATH + USERS_CSV_FILEName))) {
                writer.write(username + "," + password + "," + role + "\n");
            } catch (IOException ee) {
                System.out.println("Error appending data to CSV file: " + ee.getMessage());
            }    
        }

    }

    public static int Login(String userName,String password)throws UserNotFoundException{
        try (Scanner scanner = new Scanner(new File(FILEPATH +USERS_CSV_FILEName))) {
            if(scanner.hasNextLine()){
                scanner.nextLine(); // Skip the header
            }
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                if(data[0].equals(userName) && data[1].equals(password)){
                    System.out.println("User found");
                    return Integer.parseInt(data[2]);
                }
            }
            throw new UserNotFoundException("User not found");
        
            
        } catch (IOException e) {
            System.out.println("Error reading data from CSV file: " + e.getMessage());
            return -1;
        }
    }

    public static void initializeJSON(String studnetUsername) {
        Student student = new Student(User.userCount+1, studnetUsername);
        try (FileWriter writer = new FileWriter(FILEPATH+studnetUsername+".json")) {
            writer.write(new Gson().toJson(student));
            System.out.println("User JSON written to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }      
    }
    public static void writeToJson(User user, String userName) {
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(Course.class, new CourseAdapter())
            .create();

        try (Writer writer = new FileWriter(FILEPATH+ userName+".json")) {
            gson.toJson(user, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    public static User readFromJson(String userName) {
        Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Course.class, new CourseAdapter())
                    .create();
    
        try (Reader reader = new FileReader(FILEPATH+userName+".json")) {
            // Try to deserialize the JSON as a Student
            Student student = gson.fromJson(reader, Student.class);
            if (student != null) {
                return student;
            }
    
            // If the JSON couldn't be deserialized as a Student, try to deserialize it as an Instructor
            Instructor instructor = gson.fromJson(reader, Instructor.class);
            if (instructor != null) {
                return instructor;
            }
    
            // If the JSON couldn't be deserialized as a Student or an Instructor, return null
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    static class CourseAdapter implements JsonSerializer<Course>, JsonDeserializer<Course> {
        @Override
        public JsonElement serialize(Course src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject obj = new JsonObject();
            obj.addProperty("type", src.getClass().getName());
            obj.add("data", context.serialize(src));
            return obj;
        }

        @Override
        public Course deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject obj = json.getAsJsonObject();
            String type = obj.get("type").getAsString();
            JsonElement element = obj.get("data");
            try {
                return context.deserialize(element, Class.forName(type));
            } catch (ClassNotFoundException cnfe) {
                throw new JsonParseException("Unknown element type: " + type, cnfe);
            }
        }
    }
    public static void deleteLineByUsername(String usernameToDelete) throws IOException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILEPATH + USERS_CSV_FILEName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1 && parts[0].equals(usernameToDelete)) {
                    continue; // Skip this line
                }
                lines.add(line);
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILEPATH + USERS_CSV_FILEName))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }
}
