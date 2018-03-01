package travnich.entity;

import travnich.enums.VerdictType;
import travnich.exceptions.WrongMarkException;

public class Result {

    private static final int MIN_MARK = 1;
    private static final int MIN_PASS_MARK = 4;
    private static final int MAX_MARK = 10;
    private int programming;
    private int pe;
    private VerdictType verdict;
    private int studentID;
    private int math;
    private int id;

    public static String getColumns() {
        return "\t\tresultID" + "\t\tmath" + "\t\tprogramming" + "\t\tPE" + "\t\tverdict";
    }

    public static String getVerdictColumn() {
        
        return "\t\tverdict";


    }
    
    

    public String toString() {
        return "\t\t       " + id +
                "\t\t     " + math +
                "\t\t       " + programming +
                "\t\t    " + pe +
                "\t\t" + verdict;
    }

    public int getStudentID() {
        return studentID;
    }

    public Result(int id, int math, int programming, int pe, VerdictType strVerdict, int studentID) {

        this.id = id;
        this.math = math;
        this.programming = programming;
        this.pe = pe;
        this.verdict = strVerdict;
        this.studentID = studentID;
    }

    public static VerdictType getVerdictType(int math, int programming, int pe) throws WrongMarkException {

        if (math < MIN_MARK || math > MAX_MARK ||
            programming < MIN_MARK || programming > MAX_MARK ||
            pe < MIN_MARK || pe > MAX_MARK) throw new WrongMarkException();
        if (math >= MIN_PASS_MARK && programming >= MIN_PASS_MARK && pe >= MIN_PASS_MARK) return VerdictType.Passed;
        else return VerdictType.Failed;

    }

    public VerdictType getVerdict() {
        return verdict;
    }
}
