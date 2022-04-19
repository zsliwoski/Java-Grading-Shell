

import java.util.Scanner;

public class Main {
    static boolean exit = false;

    
    /** 
     * @param args
     */
    public static void NewClass(String[] args){

    }

    
    /** 
     * @param args
     */
    public static void ListClasses(String[] args){

    }

    
    /** 
     * @param args
     */
    public static void SelectClass(String[] args){

    }
    
    
    /** 
     * @param args
     */
    public static void ShowClass(String[] args){

    }

    
    /** 
     * @param args
     */
    public static void ShowCategories(String[] args){

    }

    
    /** 
     * @param args
     */
    public static void AddCategory(String[] args){

    }

    
    /** 
     * @param args
     */
    public static void ShowAssignment(String[] args){

    }
    
    
    /** 
     * @param args
     */
    public static void AddAssignment(String[] args){

    }

    
    /** 
     * @param args
     */
    public static void AddStudent(String[] args){

    }

    
    /** 
     * @param args
     */
    public static void ShowStudents(String[] args){

    }

    
    /** 
     * @param args
     */
    public static void Grade(String[] args){

    }
    
    
    /** 
     * @param args
     */
    public static void StudentGrades(String[] args){

    }

    
    /** 
     * @param args
     */
    public static void Gradebook(String[] args){

    }
    
    
    /** 
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
