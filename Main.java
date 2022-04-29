
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
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

import java.util.Scanner;

public class Main {
    static int currentClass = -1;
    static boolean exit = false;

    /**
     * Creates a connection to our database
     * 
     * @returns Connection to database
     */
    public static Connection makeConnection() {
        try {
            Connection conn = null;
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/school_db?useSSL=false", "student",
                    "user");
            // Do something with the Connection
            System.out.println("[CONNECTION SUCCEEDED]");
            System.out.println("_______________________");
            return conn;
        } catch (SQLException ex) {
            // handle any errors
            System.err.println("SQLException: " + ex.getMessage());
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("VendorError: " + ex.getErrorCode());
        }
        return null;
    }

    /**
     * Creates a new class for students
     * 
     * @param args
     */
    public static void NewClass(String[] args) {
        int op = args.length;

        switch (op) {
            case 5:
                try {
                    String query = "INSERT INTO class (course_number, term, section_number, description) VALUES(?, ?, ?, ?);";

                    Connection conn = makeConnection();

                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, args[1]);
                    pstmt.setString(2, args[2]);
                    pstmt.setInt(3, Integer.parseInt(args[3]));
                    pstmt.setString(4, args[4]);

                    boolean ran = pstmt.execute();
                    System.out.println("Inserted 1 record");
                    conn.close();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                break;
            default:
                System.out.println("Error : Invalid Parameters");
                return;
        }
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
                try {
                    String query = "select count(class_enrollment.student_id), class.course_number,class.term,class.section_number "
                            +
                            "from class_enrollment " +
                            "right join class on class_enrollment.class_id = class.class_id " +
                            "group by class_enrollment.class_id,class.course_number,class.term,class.section_number;";

                    Connection conn = makeConnection();
                    Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY);

                    ResultSet res = stmt.executeQuery(query);// pstmt.execute();

                    while (res.next()) {
                        System.out.println(
                                res.getInt(1) + " " + res.getString(2) + " " + res.getString(3) + " " + res.getInt(4));
                    }

                    res.close();
                    conn.close();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                break;
            default:
                System.out.println("Error : Invalid Parameters");
                return;
        }
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
                break;
            case 3:
                for (String string : args) {
                    System.out.println(string);
                }
                break;
            case 4:
                try {
                    String query = "SELECT class_id FROM class WHERE course_number = ? AND term = ? AND section_number = ?";

                    Connection conn = makeConnection();

                    PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY);

                    pstmt.setString(1, args[1]);
                    pstmt.setString(2, args[2]);
                    pstmt.setInt(3, Integer.parseInt(args[3]));

                    ResultSet res = pstmt.executeQuery();// pstmt.execute();

                    res.first();
                    currentClass = res.getInt(1);

                    System.out.println("Selected class of ID:" + currentClass);

                    res.close();
                    conn.close();
                } catch (Exception ex) {
                    System.out.println(ex);
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
                try {
                    String query = "select count(class_enrollment.student_id), class.course_number,class.term,class.section_number "
                            +
                            "from class_enrollment " +
                            "right join class on class_enrollment.class_id = class.class_id " +
                            "group by class_enrollment.class_id,class.course_number,class.term,class.section_number HAVING class_id = ?;";

                    Connection conn = makeConnection();
                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, currentClass);

                    ResultSet res = pstmt.executeQuery();// pstmt.execute();

                    System.out.println("| # Enrolled | Course # | Term | Section # |");

                    while (res.next()) {
                        System.out.println(
                                "| " + res.getInt(1) + " | " + res.getString(2) + " | " + res.getString(3) + " | "
                                        + res.getInt(4) + " |");
                    }

                    res.close();
                    conn.close();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                break;
            default:
                System.out.println("Error : Invalid Parameters");
                return;
        }
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
                try {
                    String query = "select * from category where class_id = ?";

                    Connection conn = makeConnection();
                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, currentClass);

                    ResultSet res = pstmt.executeQuery();// pstmt.execute();

                    System.out.println("| Category Name | Grading Weight |");
                    while (res.next()) {
                        System.out.println(
                                "| " + res.getString("name") + " | " + res.getFloat("weight") + " |");
                    }

                    res.close();
                    conn.close();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                break;
            default:
                System.out.println("Error : Invalid Parameters");
                return;
        }
    }

    /**
     * Add a new category
     * 
     * @param args
     */
    public static void AddCategory(String[] args) {
        int op = args.length;
        for (String string : args) {
            System.out.println(string);
        }
        switch (op) {
            case 4:
                try {
                    String query = "insert into category (name, weight, class_id) values (?, ?, ?);";

                    Connection conn = makeConnection();

                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, args[1]);
                    pstmt.setFloat(2, Float.parseFloat(args[2]));
                    pstmt.setInt(3, currentClass);

                    boolean ran = pstmt.execute();
                    System.out.println("Inserted 1 record");
                    conn.close();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                break;
            default:
                System.out.println("Error : Invalid Parameters");
                return;
        }
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
                try {
                    String query = "select category.name as category, assignment.name,assignment.description,assignment.point_value from assignment,category "
                            +
                            "where category.category_id = assignment.category_id " +
                            "and category.class_id = ? " +
                            "group by assignment.name, description, point_value, category.name;";

                    Connection conn = makeConnection();
                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, currentClass);

                    ResultSet res = pstmt.executeQuery();// pstmt.execute();

                    System.out.println("| Assignment Name | Point Value |");
                    while (res.next()) {
                        System.out.println(
                                "| " + res.getString("name") + " | " + res.getInt("point_value") + " |");
                    }

                    res.close();
                    conn.close();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                break;
            default:
                System.out.println("Error : Invalid Parameters");
                return;
        }

    }

    /**
     * Add a new assignment
     * 
     * @param args
     */
    public static void AddAssignment(String[] args) {
        int op = args.length;

        switch (op) {
            case 7:
                try {
                    String query = "INSERT INTO assignment (class_id, category_id, name, description, point_value) VALUES(?, ?, ?, ?, ?);";
                    Connection conn = makeConnection();

                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, currentClass);
                    pstmt.setInt(2, Integer.parseInt(args[1]));
                    pstmt.setString(3, args[2]);
                    pstmt.setString(4, args[3]);
                    pstmt.setInt(5, Integer.parseInt(args[4]));

                    boolean ran = pstmt.execute();
                    System.out.println("Inserted 1 record");
                    conn.close();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                break;
            default:
                System.out.println("Error : Invalid Parameters");
                return;
        }
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
                try {
                    String query = "select username,first_name,last_name from student " +
                            "inner join class_enrollment on student.student_id = class_enrollment.student_id " +
                            "and class_enrollment.class_id = ?";

                    Connection conn = makeConnection();
                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, currentClass);

                    ResultSet res = pstmt.executeQuery();// pstmt.execute();

                    System.out.println("| Username | First Name | Last Name |");
                    while (res.next()) {
                        System.out.println(
                                "| " + res.getString("username") + " | " + res.getString("first_name") + " | "
                                        + res.getString("last_name") + " |");
                    }

                    res.close();
                    conn.close();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                break;
            case 2:
                try {
                    String query = "select username,first_name,last_name from student " +
                            "inner join class_enrollment on student.student_id = class_enrollment.student_id " +
                            "and class_enrollment.class_id = ? " +
                            "where first_name like ? " +
                            "or username like ? " +
                            "or last_name like ? ;";

                    Connection conn = makeConnection();
                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, currentClass);
                    pstmt.setString(2, "%" + args[1] + "%");
                    pstmt.setString(3, "%" + args[1] + "%");
                    pstmt.setString(4, "%" + args[1] + "%");
                    ResultSet res = pstmt.executeQuery();// pstmt.execute();

                    System.out.println("| Username | First Name | Last Name |");
                    while (res.next()) {
                        System.out.println(
                                "| " + res.getString("username") + " | " + res.getString("first_name") + " | "
                                        + res.getString("last_name") + " |");
                    }

                    res.close();
                    conn.close();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                break;
            default:
                System.out.println("Error : Invalid Parameters");
                return;
        }
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
                try {
                    String query = "select count(*) from student,class_enrollment where" +
                            "username = ?" +
                            "and student.student_id = class_enrollment.student_id" +
                            "and class_id = ?";

                    Connection conn = makeConnection();
                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, args[2]);
                    pstmt.setInt(2, currentClass);
                    ResultSet res = pstmt.executeQuery();// pstmt.execute();

                    int studentCount = 0;

                    while (res.next()) {
                        studentCount += 1;
                    }

                    res.close();
                    conn.close();

                    if (studentCount == 1) {
                        query = "insert into assignment_grade" +
                                "set points_awarded = ?, " +
                                "student_id = (select student_id from student where username = ?)," +
                                "assignment_id = (select assignment.assignment_id from assignment,category where category.category_id=assignment.category_id"
                                +
                                "and category.class_id = ?" +
                                "and assignment.name = ?);";

                        conn = makeConnection();

                        pstmt = conn.prepareStatement(query);
                        pstmt.setInt(1, Integer.parseInt(args[3]));
                        pstmt.setString(2, args[2]);
                        pstmt.setInt(3, currentClass);
                        pstmt.setString(4, args[1]);

                        boolean ran = pstmt.execute();
                        System.out.println("Inserted 1 record");
                        conn.close();

                        // Checking to make sure we didn't award too many points
                        query = "select point_value from assignment,category where category.category_id=assignment.category_id"
                                +
                                "and category.class_id = ?" +
                                "and assignment.name = ?;";

                        conn = makeConnection();
                        pstmt = conn.prepareStatement(query);
                        pstmt.setString(1, args[2]);
                        pstmt.setInt(2, currentClass);
                        res = pstmt.executeQuery();// pstmt.execute();

                        int pointCount = 0;

                        while (res.next()) {
                            studentCount += 1;
                            pointCount = res.getInt("point_value");
                        }

                        res.close();
                        conn.close();

                        if (pointCount < Integer.parseInt(args[3])) {
                            System.out.println("WARNING: Points awarded are greater than maximum assignment points.");
                        }
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                break;
            default:
                System.out.println("Error : Invalid Parameters");
                return;
        }
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
