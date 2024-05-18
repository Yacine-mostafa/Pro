package Project;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Student extends Courses {
    private String fullname;
    private static String id = "10001"; // fixed number
    private ArrayList<String>  courses;
   // private ArrayList<Courses> courses = new ArrayList<Courses>();

   Student(){}

    public Student(String fullname ,ArrayList<String> courses)//List<Courses> courses) // constructor
    {   
        this.fullname = fullname;
        this.courses = courses;
        Increment_Id();  // so the id number will go up by one starting from 000000
    }
    public Student(String fullname)
    {
        this.fullname = fullname;
        Increment_Id();
    }

    

    // big endein 
    private static void WriteInBigEndian(OutputStream output, int value) throws Exception{ // output for entering the data and the value int that we want to write in big endian
        output.write(value >> 24);
        output.write(value >> 16);
        output.write(value >> 8);
        output.write(value);
    }

        private static int ReadSizeOfNextString(InputStream input)throws Exception{
        int byte1 = input.read();
        int byte2 = input.read();
        int byte3 = input.read();
        int byte4 = input.read();
        return (byte1 << 24) | (byte2 << 16) | (byte3 << 8) | byte4;
    }
    // function to increment 
    private void Increment_Id(){
        // array to store the ids extracted from a file
        ArrayList<String> ids = new ArrayList<>();
        int i = 0;

        try{
            // it will loop and read all lines of data from the bin file
            while (i < Display_info().size()){

                String line = Display_info().get(i); // exact line of data 
                String[] data = line.split(","); 
                ids.add(data[0]);
                i++;
            }
            i = 0; // resetting the variable to zero

           
            while (i < ids.size()){
                int TempId = (Integer.parseInt(id)+ i+1);

                if(!(ids.contains(String.valueOf(TempId)))){
                    id = String.valueOf(TempId);
                    break;
                }
                i++;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    
    // displaying info
   public ArrayList<String> Display_info() throws Exception{

    ArrayList<String> data_list = new ArrayList<>(); // to store every thing from the info.bin file and then display it as a readable code 

    try{
        FileInputStream input = new FileInputStream("info.bin"); // to read from a bin file

        // to read from byte to string 
        while(input.available() > 0){

            StringBuilder builder = new StringBuilder();
            
            // reading id
            byte[] IdByte = new byte[4];
            input.read(IdByte);
            String id = new String(IdByte);

            // reading name 
            byte[] NameByte = new byte[ReadSizeOfNextString(input)];
            input.read(NameByte);
            String fullname = new String(NameByte);

            // now building the student 
            builder.append(id).append("_").append(fullname);

            int i; // as the number of courses taken by the student
            if((i = input.read()) != '0' ){
                builder.append("_"); // separating from name ,id from courses

                for(int j = Integer.parseInt(String.valueOf((char)i)); j > 0 ; j--){
                    byte[] courseByte = new byte[ReadSizeOfNextString(input)];
                    input.read(courseByte);
                    builder.append(new String(courseByte)).append(":"); // separating every course from each other

                    byte[] courseCode = new byte[10];
                    input.read(courseCode);
                    if(j-1 != 0){
                        builder.append(new String(courseCode)).append(",");
                    }else{
                        builder.append(new String(courseCode));
                    }
                }
            }
            data_list.add(builder.toString());
        }
        input.close();  
        return data_list;
    }
    catch (IOException e){
        System.out.println("Error in reading file.");
    }
        return data_list;
    }
    
    public String toString(){
        return fullname+"," +id+"-"+courses.toString()+"-";
    }


    // 1) function (creating a student)
    public void Create_Student(boolean add) throws Exception{
        try{

            // writing to a file a student full name and its id separated by coma
            FileOutputStream output = new FileOutputStream("info.bin",true);
    
            output.write(id.getBytes());
            WriteInBigEndian(output,fullname.length());
            output.write(fullname.getBytes());

            // checking and adding courses
            if(courses != null){
                output.write(String.valueOf(courses.size()).getBytes()); // get courses and write them in bytes
            } else {
                output.write("0".getBytes()); // write zero in bytes if there is no courses 
            }

            if(add){

                // Iterate over (check)the list of courses and write each course to the file
                for(String courses : this.courses){
                    String[] coursesInfo = courses.split("-");
                    WriteInBigEndian(output,coursesInfo[0].length());
                    output.write(coursesInfo[0].getBytes());
                    output.write(coursesInfo[1].getBytes());
                    
                    }
                }
                output.close();
            } catch (Exception e){
                System.out.println("Error in writing to file.");
            }
    }
    
    // 2) function to search for a student by id or name

    public String Searching_Id_or_Name() throws Exception
    {
        int i = 0; // for counting in the loop
        Scanner input = new Scanner(System.in);

            
        System.out.println("------------a_list_of_the_students_available------------");
        // loop to print all the student
        for(String stud : Display_info()){
            String[] info = stud.split(",");
        System.out.println(info[0] +" "+info[1]); // info[0] id + info[1] name.
        }
                
                // prompt the user to enter the name or id
                System.out.println("_____enter the id or name (id is more efficient)_____");
                String id = input.nextLine();
                
                boolean found = false;
                
                // checks if the entered id is in the system
                for(String example : Display_info()){
                    String[] info = example.split(","); // search the info array and if its info[0]or info[1] it says its found
                    if(info[0].equals(id) || info[1].equals(id))
                    {
                        found = true; // this means that we found the student
                        break;
                    }
                }
                
                if(found){
                    try{
                        int Id = Integer.parseInt(id);
                        while(i < Display_info().size())
                        {
                            String line = Display_info().get(i); // to store the line read from the file
                            String[] data = line.split(",");
                            if(Id == Integer.parseInt(data[0])) // starts from zero and increments by one if the id is not found
                            {
                                return line;
                            }
                            i++;
                        }
                        
                        // if the id is not found treat is as a name (the user can search for the for the student info using id or name)
                    } catch (Exception e){
                        while (i < Display_info().size()) {
                            String line = Display_info().get(i);
                            String[] data = line.split(",");
                            if (Objects.equals(id, data[1])) {
                                return line; // returns the student information such as a fullname and an id
                            }
                            i++;
                        }
                    }
                }else{
                    return "this student is not in the system";
                }
        return "";
    }

    // function 3) deleting a student
    public void Delete_Student_using_id(String id) throws Exception{
        int i = 0;
        ArrayList<String> List = Display_info();
        
        // a try block to find the id 
        try{
            while(i < List.size())
            {
                String data = List.get(i); // to get data
                String[] store = data.split(","); // to store data and split it to get the id 
                if(id.equals(store[0])){
                    List.remove(i); // remove from list
                    break;
                } 
                i++;
            }
            i = 0;
            //  now we rewrite the list after deleting the student (updating the list)
            FileOutputStream output = new FileOutputStream("info.bin");
            while(i < List.size()){
                String[] Line = List.get(i).split(",");
                output.write(Line[0].getBytes()); // to write every student info (updated list) into the bin file 
                WriteInBigEndian(output,Line[1].length());
                output.write(Line[1].getBytes());

                if(Line.length > 2){
                    String[] courses = Line[2].split(":");
                    output.write(String.valueOf(courses.length).getBytes());
                    for(String subs : courses){
                        String[] cors = subs.split("-");
                        WriteInBigEndian(output,cors[0].length());
                        output.write(cors[0].getBytes());
                        output.write(cors[1].getBytes());
                    }
                } else {
                    output.write("0".getBytes()); 
                }
                i++;
            }
            output.close(); 
        } catch (Exception e){
            System.out.println(e.getMessage()); // if the file not found
        }
    }

    // function 4) display all students (names + ids)
    public void Display_all_Students() throws Exception
    {
        int i;
        ArrayList<String> InfoList = Display_info();
        System.out.println("-----------Students_in_system--------");
        i = 0;

        while(i < InfoList.size()){ 
            String[] Line = InfoList.get(i).split(",");
            System.out.println(Line[0]); // display the first info from the student array (which is the name) (fullname , id : course - .... |)
            i++;
        }

    }

    // function 5) displaying all student subjects 
    public String Display_Subjects() throws Exception{
        String[] line = Searching_Id_or_Name().split("-"); // anything after : are the courses of the student 
        if(line.length <= 2){
            return"this student did not apply for any subjects yet :("; // this means that the student does not have subjects
        }else{
            return line[2];
        }
    }

    // function 6) adding a course for a student
    private ArrayList<String> add_course(ArrayList<String> stud_courses){ // student courses
        Courses course  = new Courses();
        System.out.println(" Courses *second semester only! *");

        // while loop to print all the courses that could be taken 
        for(String courses : course.Courses_List()){
            if(!(stud_courses).contains(courses)){
                System.out.println(courses);
            }
        }
        return course.Courses_List();
    }

    // function to see if the system does or does not have students
    public boolean system_empty() throws Exception
    {
        return !Display_info().isEmpty();
    }

    // function 7) updating a student info
    public void Update() throws Exception{
        ArrayList<String> course = new ArrayList<>();
        String line = Searching_Id_or_Name();
        String[] line1 = line.split("-");
        if(line1.length == 3){ // which indicates that the student is already taking courses (even one)
            String[] Subjects = line1[2].split("-");
            String[] name = line1[1].split(",");

            System.out.println(name[0]+"s Courses: ");
            for(String subject : Subjects){
                course.add(subject);
                System.out.println(subject);
            }
            System.out.println("----------------------------------------");
            boolean update = true;
            Scanner input = new Scanner(System.in);

            while(update){
                System.out.println("a) Add Subject.");
                System.out.println("b) Remove a Subject.");
                System.out.println("c) Remove All Subjects.");
                System.out.println("d) Exit.");

                System.out.print("Enter your choice : ");
                String choice = input.nextLine();

                switch(choice){
                    case "a": // adding a subject to the student 
                    Delete_Student_using_id(line1[0]);
                    course.addAll(add_course(course));
                    this.fullname = line1[1];
                    Student.id = line1[0];
                    this.courses = course;
                    Create_Student(!courses.isEmpty());
                    break;

                    case "b":
                    Delete_Student_using_id(line1[0]);
                    Scanner sc = new Scanner(System.in);
                    System.out.print("Enter the subject you want to remove : ");
                    String subject = sc.nextLine();
                    for(String i : course){
                        String[] parsing = i.split("-");
                        if(parsing[1].equals(subject)){
                            course.remove(i);
                            break;
                        }
                    }
                    System.out.println("Course Successfully removed");
                    // after removing the student data (temporary) we restore it 
                    this.fullname = line1[1];
                    Student.id = line1[0];
                    this.courses = course;
                    Create_Student(!courses.isEmpty());
                    break;

                    case "c":
                    course.clear();
                    Delete_Student_using_id(line1[0]);
                    this.fullname= line1[1];
                    Student.id = line1[0];
                    Create_Student(!course.isEmpty());
                    System.out.println("All Courses Been removed !");
                    break;

                    case "d":
                    update = false;
                    break;

                    default:
                    System.out.println("Invalid Choice");
                }
                System.out.println("Info Has Been Updated Successfully");
            }
            input.close();
        } else {
            System.out.println("A student Must take courses in order to update his info");
            System.out.println("(----------------------------------------------------------------)");
            Delete_Student_using_id(line1[0]);
            course.addAll(add_course(course));
            this.fullname = line1[1];
            Student.id = line1[0];
            this.courses = course;
            Create_Student(!course.isEmpty());
            System.out.println("Done !");
        }
    }
}