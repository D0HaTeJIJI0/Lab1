package travnich.constants;

public class CommandsSQLTemplates {

    public final static String SHOW_STUDENT = "select * from Students;";
    public final static String SHOW_RESULT = "select * from Results";
    public final static String ADD_STUDENT = "insert into Students (name, groupNum) values (?, ?);";
    public final static String ADD_RESULT = "insert into Results (math, programming, PE, verdict, studentID) values (?, ?, ?, ?, ?);";
    public final static String DEL_ID_STUDENT = "delete from Students where id = ?;";
    public final static String GET_BAD_STUDENT_ID = "delete from Results where verdict = ?;";
    public final static String GET_LAST_STUDENT_ID = "select max(id) from students;";

}
