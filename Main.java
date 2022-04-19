/**
* The HelloWorld program implements an application that
* simply displays "Hello World!" to the standard output.
* 
* @class  CS-410 Databases
* @authors  Zarek Sliwoski, Simon Arca Costas
* @version 1.0
* @since   4-18-2022 
*/

import java.util.Scanner;

public class Main {
    static boolean exit = false;

    
    /** 
     * Creates a new class for students
     * 
     * @param args
     */
    public static void NewClass(String[] args){

    }

    
    /** 
     * List classes, with the # of students in each
     * 
     * @param args
     */
    public static void ListClasses(String[] args){

    }

    
    /** 
     * Activate a class
     * 
     * @param args
     */
    public static void SelectClass(String[] args){

    }
    
    
    /** 
     * Shows the currently-active class
     * 
     * @param args
     */
    public static void ShowClass(String[] args){

    }

    
    /**
     * List the categories with their weights 
     * 
     * @param args
     */
    public static void ShowCategories(String[] args){

    }

    
    /** 
     * Add a new category
     * 
     * @param args
     */
    public static void AddCategory(String[] args){

    }

    
    /** 
     * List the assignments with their point values, grouped by category
     * 
     * @param args
     */
    public static void ShowAssignment(String[] args){

    }
    
    
    /** 
     * Add a new assignment
     * 
     * @param args
     */
    public static void AddAssignment(String[] args){

    }

    
    /** 
     * Adds a student and enrolls them in the current class
     * 
     * @param args
     */
    public static void AddStudent(String[] args){

    }

    
    /** 
     * Show all students in the current class
     * 
     * @param args
     */
    public static void ShowStudents(String[] args){

    }

    
    /** 
     * Assign the grade ‘grade’ for student with user name ‘username’ for assignment 
     * ‘assignmentname’.
     *  
     * @param args
     */
    public static void Grade(String[] args){

    }
    
    
    /** 
     * Show student’s current grade: all assignments, visually grouped by category, 
     * with the student’s grade (if they have one).
     * 
     * @param args
     */
    public static void StudentGrades(String[] args){

    }

    
    /**
     * Show the current class’s gradebook:
     *  
     * @param args
     */
    public static void Gradebook(String[] args){

    }
    
    
    /** 
     * Handles direct input from our commandline
     * @param input
     */
    public static void HandleInput(String input){

        String[] args = input.split(" ", 5);
        String command = args[0];
        System.out.println(command);
        switch(command){
            case "exit":
                exit = true;
            break;
            case "new-class":
                NewClass(args);
            break;
            case "list-classes":
                ListClasses(args);
            break;
            case "select-class":
                SelectClass(args);
            break;
            case "show-class":
                ShowClass(args);
            break;
            case "show-categories":
                ShowCategories(args);
            break;
            case "add-category":
                AddCategory(args);
            break;
            case "show-assignment":
                ShowAssignment(args);
            break;
            case "add-assignment":
                AddAssignment(args);
            break;
            case "add-student":
                AddStudent(args);
            break;
            case "show-students":
                ShowStudents(args);
            break;
            case "grade":
                Grade(args);
            break;
            case "student-grades":
                StudentGrades(args);
            break;
            case "gradebook":
                Gradebook(args);
            break;
        }
    }

    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        while(!exit){
            System.out.print("Databases-Final >> ");
            HandleInput(s.nextLine());
        }
        s.close();
    }
}
