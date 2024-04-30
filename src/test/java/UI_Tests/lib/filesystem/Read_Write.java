package lib.filesystem;

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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import lib.course.*;
import lib.users.*;



public final class Read_Write {

    private static final String FILEPATH = "lib\\filesystem\\";
    private static final String USERS_CSV_FILEName = "Users.csv";
    private static final String ID_FILE = FILEPATH + "last_id.txt"; // File to keep the last used ID


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

    public static void Signup(String username, String password, int role) throws UserAlreadyExistsException {
        try {
          Login(username, password); // Unnecessary for signup, remove this line
          throw new UserAlreadyExistsException("User already exists");
        } catch (UserNotFoundException e) {
          // User not found, append data to file
          try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILEPATH + USERS_CSV_FILEName, true))) { // XXX: ADDED append PARAMETER 
            writer.write(username + "," + password + "," + role + "\n");
          } catch (IOException ee) {
            System.out.println("Error appending data to CSV file: " + ee.getMessage());
          }
        }
      }
      

    public static int Login(String userName,String password)throws UserNotFoundException{
        try (Scanner scanner = new Scanner(new File(FILEPATH +USERS_CSV_FILEName))) {
            if(scanner.hasNextLine()){              //FIXME: first CSV line is not detected as a valid user!!
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



    public static void initializeJSON(String studnetUsername) { //FIXME: studentUsername (typo)
        Student student = new Student(User.userCount+1, studnetUsername);
        try (FileWriter writer = new FileWriter(FILEPATH+studnetUsername+".json")) {
            writer.write(new Gson().toJson(student));
            System.out.println("User JSON written to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }      
    }
    public static void writeToJson(User user) {
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(Course.class, new CourseAdapter())
            .setPrettyPrinting() // This will make the JSON output more readable.
            .create();
    
        String filename = FILEPATH + user.getName() + ".json";
        try (Writer writer = new FileWriter(filename)) {
            gson.toJson(user, writer);
            System.out.println("JSON written to " + filename + " successfully.");
        } catch (IOException e) {
            System.out.println("Failed to write JSON: " + e.getMessage());
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