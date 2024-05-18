package Project;

import java.util.ArrayList;
import java.util.Scanner;

public class Courses {

    private String name; // name of the course
    private String code; // code of the course

    Courses(){}

    public Courses(String name, String code) {
        this.name = name;
        this.code = code;
    }

    @Override
    public String toString()
    {
        return name+ "-"+code;
    }

    public ArrayList<String> Courses_List(){
        ArrayList<String> Courses_List = new ArrayList<>();
        
        Courses_List.add(new Courses("Programming II","00001").toString());
        Courses_List.add(new Courses("Calculus II","00002").toString());
        Courses_List.add(new Courses("Physics II","00006").toString());
        Courses_List.add(new Courses("Discrete Mathematics","00008").toString());
        Courses_List.add(new Courses("Linear Algebra","00010").toString());
        Courses_List.add(new Courses("German II","00012").toString());
        Courses_List.add(new Courses("Atatürk","00001").toString());
        Courses_List.add(new Courses("Turkish II","00004").toString());
        return Courses_List;
    }


    // to add courses to the student
    public ArrayList<String> Courses_Taken(){
        ArrayList<String> Taken= new ArrayList<>();
        try{
            String Subject = "";
            Scanner input = new Scanner(System.in);

            while(true){
                System.out.println("Enter the course code: (Or press Q to exit)");
                String code = input.nextLine();

                if(code.equalsIgnoreCase("q")){
                    break;
                }

                boolean Id = false;
                boolean CourseTaken = false;

                for(String line : Courses_List()){
                    String[] parts = line.split("-");
                    if(parts.length > 1 && parts[1].equals(code)){
                        CourseTaken = true;
                        if(Taken.contains(line)){
                            CourseTaken = true;
                        }
                        Subject = line;
                        break;
                    }
                }
                if(Id){
                    if(CourseTaken){
                        System.out.println("this course is already taken");
                    } else {
                        Taken.add(Subject);
                        System.out.println("Course "+Subject+" Has been added");
                    }
                }
            }

        System.out.println("all subjects have been added ");

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
            return Taken;
        }
    }