package Project;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        
    Menu();

    }

    public static void Menu()
    {
        boolean rewind = true;
        String fullname;
        Courses courses = new Courses();
        Student stud = new Student();
        
        
        
        while(rewind)
        {
            Scanner input = new Scanner(System.in);
            try{
                System.out.println("--------------------( Menu )-------------------------");
                System.out.println("| a) register a student in uni system");
                System.out.println("| b) search for a student in system");
                System.out.println("| c) Delete a student from uni system");
                System.out.println("| d) update the students info ");
                System.out.println("| e) print all students to the terminal");
                System.out.println("| f) print all the subjects taken by the student");
                System.out.println("| g) send data of a student via an email");
                System.out.println( "| h) save data to a file ");
                System.out.println("| i) Exit the app");
                System.out.println("-----------------------------------------------------");
                System.out.println("enter your choice : ");

                if(input.hasNextLine()) 
                {
                    String choice = input.nextLine();
                    switch (choice){

                        //registering a student (works fine )
                        case "a":
                        while(true){
                        System.out.println("please enter a student full name");
                        fullname=input.nextLine().trim();
                        if(fullname.matches("[a-zA-Z ]+") && fullname.contains(" ")){ // first statement to insure that the user only used letters
                        stud = new Student(fullname);                            // second statement to know that the user used a space to write the full name not just the name
                        stud.Create_Student(false);
                        System.out.println("Student created Successfully");

                        break;

                        }else{
                            System.out.println("please enter your whole name (name / surname)");
                        }
                    }  
                        break;
                    // search for a student
                        case "b":
                        stud = new Student();
                        if(stud.system_empty()){
                            System.out.println("please enter the student name you want to search for");
                            String Student = stud.Searching_Id_or_Name();
                            String[] StudInfo = Student.split("-");
                            String[] StudCourse = StudInfo[2].split(",");
                            System.out.println("Student Name : " + StudInfo[1].toUpperCase()+" Student ID : "+StudInfo[0]);
                            System.out.println("Student Course : ");
                            for(String subs : StudCourse){
                                System.out.println(subs);
                            }
                            }else{
                                System.out.println("The system is empty");
                                System.out.println("would you like to be our first student ? :)");
                        }
                        break;

                        // deleting student
                        case "c":
                        System.out.println("enter the student ID");
                        stud = new Student();
                        if(!stud.system_empty()){ // check if the system has students or not first
                            String[] id = stud.Searching_Id_or_Name().split(",");
                            stud.Delete_Student_using_id(id[1]);
                        }
                        else{
                            System.out.println("we still have no students in the system :/");
                            System.out.println("would you like to be the first ? :)");
                        }
                        break;

                        // updating students info
                        case "d":
                        stud = new Student();
                        if(stud.system_empty()){
                            stud.Update();
                        }else{
                            System.out.println("No students in the system JUST YET");
                        }
                        break;

                        // print only full names of the students in the system
                        case "e":
                        stud = new Student();
                        if(stud.system_empty()){
                            stud.Display_all_Students();
                        }else{
                            System.out.println("we still have no students in the system :/");
                            System.out.println("would you like to be the first ? :)");
                        }
                        break;

                        // print courses taken by the students
                        case "f":
                        stud = new Student();
                        if(stud.system_empty()){
                            String[] cors = stud.Display_Subjects().split("-");
                            System.out.println("students course :");
                            for(String cor : cors){
                                System.out.println(cor);
                            }
                        }else{
                            System.out.println("we still have no students in the system :/");
                            System.out.println("would you like to be the first ? :)");
                        }

                        break;

                        // send data via email to the user
                        case "g":

                        break;

                        // save the list of students to bin file (bin = binary)
                        case "h":

                        break;

                        // exiting the program
                        case "i":
                        rewind = false;
                        break;
                    }
                    System.out.println("---------------------------------------------");
                    //input.close();

                }
            } catch(Exception e){
                System.out.println(" please enter a valid option");
            }
        }
    }
}