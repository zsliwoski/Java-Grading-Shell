
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
            // System.out.println("[CONNECTION SUCCEEDED]");
            // System.out.println("_______________________");
            // System.out.println();
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
                    System.out.println("Inserted 1 record (Class)");
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

                    System.out.println("Selected class with ID: " + currentClass);

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

        switch (op) {
            case 3:
                try {
                    String query = "insert into category (name, weight, class_id) values (?, ?, ?);";

                    Connection conn = makeConnection();

                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, args[1]);
                    pstmt.setFloat(2, Float.parseFloat(args[2]));
                    pstmt.setInt(3, currentClass);

                    boolean ran = pstmt.execute();
                    System.out.println("Inserted 1 record (Category)");
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
            case 5:
                try {
                    String query = "INSERT INTO assignment (category_id, name, description, point_value) VALUES((SELECT category_id FROM category WHERE name = ? LIMIT 1), ?, ?, ?);";
                    Connection conn = makeConnection();

                    PreparedStatement pstmt = conn.prepareStatement(query);
                    // pstmt.setInt(1, currentClass);
                    pstmt.setString(1, args[2]);
                    pstmt.setString(2, args[1]);
                    pstmt.setString(3, args[3]);
                    pstmt.setInt(4, Integer.parseInt(args[4]));

                    boolean ran = pstmt.execute();
                    System.out.println("Inserted 1 record (Assignment)");
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
                try {
                    String query = "insert into student (username,student_id,first_name,last_name) values (?,?,?,?);";

                    Connection conn = makeConnection();

                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, args[1]);
                    pstmt.setInt(2, Integer.parseInt(args[2]));
                    pstmt.setString(3, args[3]);
                    pstmt.setString(4, args[4]);

                    try {
                        boolean ran = pstmt.execute();
                        conn.close();

                        System.out.println("Inserted 1 record (Student)");

                        // Enroll student
                        query = "insert into class_enrollment(class_id, student_id) values(?, ?);";

                        conn = makeConnection();

                        pstmt = conn.prepareStatement(query);
                        pstmt.setInt(1, currentClass);
                        pstmt.setInt(2, Integer.parseInt(args[2]));

                        ran = pstmt.execute();
                        conn.close();
                        System.out.println("Inserted 1 record (Enrollment)");

                    } catch (Exception ex) {
                        query = "select * from student where student_id = ?;";

                        conn = makeConnection();
                        pstmt = conn.prepareStatement(query);
                        pstmt.setInt(1, Integer.parseInt(args[2]));

                        ResultSet res = pstmt.executeQuery();// pstmt.execute();

                        boolean matching = true;

                        while (res.next()) {
                            String u_name = res.getString("username");
                            String f_name = res.getString("first_name");
                            String l_name = res.getString("last_name");

                            if (!u_name.equals(args[1]) || !f_name.equals(args[3]) || !l_name.equals(args[4])) {
                                matching = false;
                            }
                        }
                        res.close();
                        conn.close();

                        if (!matching) {
                            // update student
                            query = "UPDATE student " +
                                    "SET first_name = ?, last_name = ?, username = ?" +
                                    "WHERE student_id = ?;";

                            conn = makeConnection();

                            pstmt = conn.prepareStatement(query);
                            pstmt.setString(1, args[3]);
                            pstmt.setString(2, args[4]);
                            pstmt.setString(3, args[1]);
                            pstmt.setInt(4, Integer.parseInt(args[2]));

                            boolean ran = pstmt.execute();

                            System.out.println("Updated 1 record (Student)");
                            conn.close();
                            System.out.println("WARNING: Student's name was changed.");
                        }

                        // Enroll student
                        query = "insert into class_enrollment(class_id, student_id) values(?, ?);";

                        conn = makeConnection();

                        pstmt = conn.prepareStatement(query);
                        pstmt.setInt(1, currentClass);
                        pstmt.setInt(2, Integer.parseInt(args[2]));

                        boolean ran = pstmt.execute();
                        System.out.println("Inserted 1 record (Enrollment)");
                        conn.close();
                    }

                    conn.close();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                break;
            case 2:
                try {
                    String query = "insert into class_enrollment " +
                            "set class_id = ?, " +
                            "student_id = (select student_id from student where username = ?);";

                    Connection conn = makeConnection();

                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, currentClass);
                    pstmt.setString(2, args[1]);

                    boolean ran = pstmt.execute();
                    System.out.println("Inserted 1 record (Enrollment)");
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
                    String query = "select count(*) from student,class_enrollment where " +
                            "username = ? " +
                            "and student.student_id = class_enrollment.student_id " +
                            "and class_id = ?;";

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
                        query = "insert into assignment_grade " +
                                "set points_awarded = ?, " +
                                "student_id = (select student_id from student where username = ?), " +
                                "assignment_id = (select assignment.assignment_id from assignment,category where category.category_id=assignment.category_id "
                                +
                                "and category.class_id = ? " +
                                "and assignment.name = ?);";

                        conn = makeConnection();

                        pstmt = conn.prepareStatement(query);
                        pstmt.setInt(1, Integer.parseInt(args[3]));
                        pstmt.setString(2, args[2]);
                        pstmt.setInt(3, currentClass);
                        pstmt.setString(4, args[1]);

                        boolean ran = pstmt.execute();
                        System.out.println("Inserted 1 record (Grade)");
                        conn.close();

                        // Checking to make sure we didn't award too many points
                        query = "select point_value from assignment,category where category.category_id=assignment.category_id "
                                +
                                "and category.class_id = ? " +
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
                try {
                    String query = "select student.username, student.first_name, student.last_name, " +
                            "category.name, sum(assignment_grade.points_awarded) as points, sum(assignment.point_value) as max_points "
                            +
                            "from assignment right join assignment_grade on assignment.assignment_id = assignment_grade.assignment_id "
                            +
                            "left outer join student on student.student_id = assignment_grade.student_id " +
                            "left join category on assignment.category_id = category.category_id " +
                            "and category.class_id = ? " +
                            "group by category.category_id having student.username = ?;";

                    Connection conn = makeConnection();
                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, currentClass);
                    pstmt.setString(2, args[1]);
                    ResultSet res = pstmt.executeQuery();// pstmt.execute();

                    int sumGrades = 0;
                    int sumMaxGrades = 0;

                    System.out.println("| Username | First Name | Last Name | Category Name | Points |");
                    while (res.next()) {
                        int categoryGrades = res.getInt("points");
                        int categoryMax = res.getInt("max_points");

                        sumGrades += categoryGrades;
                        sumMaxGrades += categoryMax;

                        String u_name = res.getString("student.username");
                        String f_name = res.getString("student.first_name");
                        String l_name = res.getString("student.last_name");
                        String c_name = res.getString("category.name");

                        System.out.println(
                                "| " + u_name + " | " + f_name + " | " + l_name + " | " + c_name + " | "
                                        + (categoryGrades + "/" + categoryMax) + " |");
                    }

                    float percentage = 100.0f;

                    if (sumMaxGrades > 0) {
                        percentage = ((float) sumGrades / sumMaxGrades) * 100;
                    }

                    System.out.println();
                    System.out.println("| Total Points | % Overall |");
                    System.out.println("| " + (sumGrades + "/" + sumMaxGrades) + " | " + percentage + "% |");

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
     * Show the current class’s gradebook:
     * 
     * @param args
     */
    public static void Gradebook(String[] args) {
        int op = args.length;

        switch (op) {
            case 1:
                try {
                    String query = "select sum(number) as total from (select (sum(assignment_grade.points_awarded)/ sum(assignment.point_value)*category.weight) as number "
                            +
                            "from assignment_grade,assignment,category " +
                            "where assignment.category_id = category.category_id " +
                            "and assignment.assignment_id = assignment_grade.assignment_id " +
                            "and category.class_id = ? " +
                            "group by assignment_grade.student_id, assignment_grade.points_awarded, assignment.point_value, category.weight) n";

                    Connection conn = makeConnection();
                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, currentClass);
                    ResultSet res = pstmt.executeQuery();// pstmt.execute();

                    System.out.println("| Total % |");
                    while (res.next()) {
                        System.out.println(
                                "| " + res.getString("total") + " |");
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
