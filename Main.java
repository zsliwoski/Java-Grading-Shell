
/**
* The final for CS-410 Databases
*
* A Java application for managing grades in a class.
* It's used as a command shell application.
*
* @class  CS-410 Databases
* @authors  Zarek Sliwoski, Simon Arca Costas
* @version 1.0
* @since   4-18-2022 
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import java.util.Scanner;

public class Main {
    static String currentClass = "";
    static boolean exit = false;

    public static Connection makeConnection() {
        try {
            Connection conn = null;
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/school_db?useSSL=false", "student",
                    "user");
            // Do something with the Connection
            System.out.println("Database [test db] connection succeeded!");
            System.out.println();
            return conn;
        } catch (SQLException ex) {
            // handle any errors
            System.err.println("SQLException: " + ex.getMessage());
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("VendorError: " + ex.getErrorCode());
        }
        return null;
    }

    public static ResultSet runQuery(Connection conn, String statement) {

        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Persons");

        } catch (SQLException ex) {
            // handle any errors
            System.err.println("SQLException: " + ex.getMessage());
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("VendorError: " + ex.getErrorCode());
        } finally {
            // it is a good idea to release resources in a finally{} block
            // in reverse-order of their creation if they are no-longer needed
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore
                stmt = null;
            }
        }
        return rs;
    }

    /**
     * Creates a new class for students
     * 
     * @param args
     */
    public static void NewClass(String[] args) {
        int op = args.length;
        for (String string : args) {
            System.out.println(string);
        }
        switch (op) {
            case 5:
                try {

                    Connection conn = makeConnection();
                    ResultSet res = runQuery(conn,
                            "INSERT INTO class (course_number, term, section_number, description) VALUES('course_number', 'term', 1, 'description');");
                    res.conn.close();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                break;
            default:
                System.out.println("Error : Invalid Parameters");
                return;
        }
        System.out.println(args[1]);
    }

    /**
     * List classes, with the # of students in each
     * 
     * @param args
     */
    public static void ListClasses(String[] args) {
        int op = args.length;

        switch (op) {
            case 1:
                break;
            default:
                System.out.println("Error : Invalid Parameters");
                return;
        }

        System.out.println(args[0]);
    }

    /**
     * Activate a class
     * 
     * @param args
     */
    public static void SelectClass(String[] args) {
        int op = args.length;

        switch (op) {
            case 2:
                for (String string : args) {
                    System.out.println(string);
                }
                break;
            case 3:
                for (String string : args) {
                    System.out.println(string);
                }
                break;
            case 4:
                for (String string : args) {
                    System.out.println(string);
                }
                break;
            default:
                System.out.println("Error : Invalid Parameters");
                return;
        }
    }

    /**
     * Shows the currently-active class
     * 
     * @param args
     */
    public static void ShowClass(String[] args) {
        int op = args.length;

        switch (op) {
            case 1:
                for (String string : args) {
                    System.out.println(string);
                }
                break;
            default:
                System.out.println("Error : Invalid Parameters");
                return;
        }

        System.out.println(args[0]);
    }

    /**
     * List the categories with their weights
     * 
     * @param args
     */
    public static void ShowCategories(String[] args) {
        int op = args.length;

        switch (op) {
            case 1:
                for (String string : args) {
                    System.out.println(string);
                }
                break;
            default:
                System.out.println("Error : Invalid Parameters");
                return;
        }

        System.out.println(args[0]);
    }

    /**
     * Add a new category
     * 
     * @param args
     */
    public static void AddCategory(String[] args) {
        int op = args.length;

        switch (op) {
            case 3:
                for (String string : args) {
                    System.out.println(string);
                }
                break;
            default:
                System.out.println("Error : Invalid Parameters");
                return;
        }

        System.out.println(args[0]);
    }

    /**
     * List the assignments with their point values, grouped by category
     * 
     * @param args
     */
    public static void ShowAssignment(String[] args) {
        int op = args.length;

        switch (op) {
            case 1:
                for (String string : args) {
                    System.out.println(string);
                }
                break;
            default:
                System.out.println("Error : Invalid Parameters");
                return;
        }

        System.out.println(args[0]);
    }

    /**
     * Add a new assignment
     * 
     * @param args
     */
    public static void AddAssignment(String[] args) {
        int op = args.length;

        switch (op) {
            case 5:
                for (String string : args) {
                    System.out.println(string);
                }
                break;
            default:
                System.out.println("Error : Invalid Parameters");
                return;
        }

        System.out.println(args[0]);
    }

    /**
     * Adds a student and enrolls them in the current class
     * 
     * @param args
     */
    public static void AddStudent(String[] args) {
        int op = args.length;

        switch (op) {
            case 5:
                for (String string : args) {
                    System.out.println(string);
                }
                break;
            case 2:
                for (String string : args) {
                    System.out.println(string);
                }
                break;
            default:
                System.out.println("Error : Invalid Parameters");
                return;
        }

        System.out.println(args[0]);
    }

    /**
     * Show all students in the current class
     * 
     * @param args
     */
    public static void ShowStudents(String[] args) {
        int op = args.length;

        switch (op) {
            case 1:
                for (String string : args) {
                    System.out.println(string);
                }
                break;
            case 2:
                for (String string : args) {
                    System.out.println(string);
                }
                break;
            default:
                System.out.println("Error : Invalid Parameters");
                return;
        }

        System.out.println(args[0]);
    }

    /**
     * Assign the grade ‘grade’ for student with user name ‘username’ for assignment
     * ‘assignmentname’.
     * 
     * @param args
     */
    public static void Grade(String[] args) {
        int op = args.length;

        switch (op) {
            case 4:
                for (String string : args) {
                    System.out.println(string);
                }
                break;
            default:
                System.out.println("Error : Invalid Parameters");
                return;
        }

        System.out.println(args[0]);
    }

    /**
     * Show student’s current grade: all assignments, visually grouped by category,
     * with the student’s grade (if they have one).
     * 
     * @param args
     */
    public static void StudentGrades(String[] args) {
        int op = args.length;

        switch (op) {
            case 2:
                for (String string : args) {
                    System.out.println(string);
                }
                break;
            default:
                System.out.println("Error : Invalid Parameters");
                return;
        }

        System.out.println(args[0]);
    }

    /**
     * Show the current class’s gradebook:
     * 
     * @param args
     */
    public static void Gradebook(String[] args) {
        int op = args.length;

        switch (op) {
            case 1:
                for (String string : args) {
                    System.out.println(string);
                }
                break;
            default:
                System.out.println("Error : Invalid Parameters");
                return;
        }
    }

    /**
     * Handles direct input from our commandline
     * 
     * @param input
     */
    public static void HandleInput(String input) {

        String[] args = input.split(" ", 5);
        String command = args[0];
        switch (command) {
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

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        while (!exit) {
            System.out.print("Databases-Final >> ");
            HandleInput(s.nextLine());
        }
        s.close();
    }
}
