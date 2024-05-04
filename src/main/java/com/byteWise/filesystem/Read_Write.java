package src.main.java.com.byteWise.filesystem;

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
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonParseException;
import com.google.gson.JsonDeserializationContext;
import src.main.java.com.byteWise.courses.Course;
import src.main.java.com.byteWise.courses.TextCourse;
import src.main.java.com.byteWise.courses.VideoCourse;
import src.main.java.com.byteWise.users.Instructor;
import src.main.java.com.byteWise.users.Student;
import src.main.java.com.byteWise.users.User;

public final class Read_Write {

    private static String FILEPATH; // sets the file to main working directory (ASU_JuniorProject)
    private static String SYSTEM_FILEPATH = "\\src\\main\\java\\com\\byteWise\\filesystem\\";
    private static String USERS_CSV_FILEPATH;
    private static String ID_FILE;
    private static String Courses_FILEPATH;

       
    public static String getFILEPATH() {
        return FILEPATH;
    }
    public static void setFilePath(){
        FILEPATH = System.getProperty("user.dir"); // set the file path to the current directory
        USERS_CSV_FILEPATH = FILEPATH + SYSTEM_FILEPATH + "Users.csv";
        ID_FILE = FILEPATH + SYSTEM_FILEPATH + "last_id.txt";
        Courses_FILEPATH = FILEPATH + SYSTEM_FILEPATH + "Courses.csv";
    }

    public static synchronized int generateId() {
        int lastId = 0;
        File file = new File(ID_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String lastIdStr = reader.readLine();
                if (lastIdStr != null && !lastIdStr.isEmpty()) {
                    lastId = Integer.parseInt(lastIdStr.trim());
                }
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
                return -1; // Handle this case in your application
            }
        }

        // Increment the last ID and update the file
        lastId++;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(Integer.toString(lastId));
        } catch (IOException e) {
            e.printStackTrace();
            return -1; // Handle this case in your application
        }
        
        return lastId;
    }

    public static void Signup(String username ,String password, int role) throws UserAlreadyExistsException{
        //should check if the user already exists and throw an exception if they do
        try{Login(username, password);
            throw new UserAlreadyExistsException("User already exists");}
        catch(UserNotFoundException e){ 
            //if the user is not found, write the user to the file
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_CSV_FILEPATH, true))) {
                writer.write(username + "," + password + "," + role + "\n");
            } catch (IOException ee) {
                System.out.println("Error appending data to CSV file: " + ee.getMessage());
            }    
        }

    }

    public static int Login(String userName,String password)throws UserNotFoundException{
        try (Scanner scanner = new Scanner(new File(USERS_CSV_FILEPATH))) {
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

    public static void initializeJSON(String userName, int role) {
        User user = null;
        if (role == 0) {
            user = new Student(generateId(), userName);
        } else {
            user = new Instructor(generateId(), userName);
        }
        try (FileWriter writer = new FileWriter( FILEPATH +SYSTEM_FILEPATH + userName + ".json")) {
            writer.write(new Gson().toJson(user));
            System.out.println("User JSON written to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }      
    }
    
    public static void writeToJson(User user) {
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(Course.class, new CourseAdapter())
            .create();

        try (Writer writer = new FileWriter(FILEPATH +SYSTEM_FILEPATH + user.getName() + ".json")) {
            gson.toJson(user, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    public static User readFromJson(String userName,int role) {
        Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Course.class, new CourseAdapter())
                    .create();
    
        try (Reader reader = new FileReader(FILEPATH +SYSTEM_FILEPATH + userName + ".json")) {
            // Try to deserialize the JSON as a Student
            if (role == 0) {
                Student student = gson.fromJson(reader, Student.class);
                if (student != null) {
                    return student;
                }
            }
            // If the JSON couldn't be deserialized as a Student, try to deserialize it as an Instructor
            else {
                Instructor instructor = gson.fromJson(reader, Instructor.class);
                if (instructor != null) {
                    return instructor;
                }
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

        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_CSV_FILEPATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1 && parts[0].equals(usernameToDelete)) {
                    continue; // Skip this line
                }
                lines.add(line);
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_CSV_FILEPATH))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }
    
    public static void WriteToCoursesFile(Course course) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Courses_FILEPATH, true))) {
            writer.write(course.getClass().getSimpleName() + ","
            + course.getCourseId() + "," 
            + course.getCourseTitle() + "," 
            + course.getDescription() + "," 
            + course.getLink() + "\n");  // FIX ME
        } catch (IOException e) {
            System.out.println("Error appending data to CSV file: " + e.getMessage());
        }
    }

    public static ArrayList<Course> ReadFromCoursesFile() {
        ArrayList<Course> courses = new ArrayList<>();
        File file = new File(Courses_FILEPATH);
        try (Scanner scanner = new Scanner(file)) {
            if(scanner.hasNextLine()){
                scanner.nextLine(); // Skip the header
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue; // Skip empty lines
    
                String[] data = line.split(",", -1); // Split with limit to keep empty elements
                if (data.length < 4) {  // Ensure there are at least 4 elements
                    System.out.println("Skipping incomplete course record: " + Arrays.toString(data));
                    continue;
                }
    
                String type = data[0].trim();
                String courseId = data[1].trim();
                String courseTitle = data[2].trim();
                String description = data[3].trim();
                String link = data.length > 4 ? data[4].trim() : null; // Handle optional link
    
                Course course;
                if ("TextCourse".equals(type)) {
                    course = new TextCourse(courseId, courseTitle, description, link);
                } else if ("VideoCourse".equals(type)) {
                    course = new VideoCourse(courseId, courseTitle, description, link);
                } else {
                    System.out.println("Unknown course type: " + type);
                    continue; // Skip if the course type is unknown
                }
                courses.add(course);
            }
        } catch (IOException e) {
            System.out.println("Error reading data from CSV file: " + e.getMessage());
        }
        return courses;
    }
    
    


    public static void deleteCourse(Course course){
        ArrayList<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(Courses_FILEPATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1 && parts[2].equals(course.getCourseTitle())) {
                    continue; // Skip this line
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading data from CSV file: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Courses_FILEPATH))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing data to CSV file: " + e.getMessage());
        }
    }

    public static class CourseNotFoundException extends Exception {
        public CourseNotFoundException(String message) {
            super(message);
        }
    }

    public static class UserNotFoundException extends Exception {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    public static class UserAlreadyExistsException extends Exception {
        public UserAlreadyExistsException(String message) {
            super(message);
        }
    }
}