package travnich.lib;

import travnich.enums.ResultType;
import travnich.entity.Result;
import travnich.entity.Student;
import travnich.enums.VerdictType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import static travnich.constants.CommandsTemplates.*;

public class Controller {

    private ArrayList<Student> students;
    private ArrayList<Result> results;
    private DAO table;
    private Pattern pShow, pAdd, pDel, pExit;
    private Matcher mShow, mAdd, mDel, mExit;
    private ResultType resType;

    public Controller(){

        table = new DAO();
        pShow = Pattern.compile(COMM_SHOW);
        pAdd = Pattern.compile(COMM_ADD);
        pDel = Pattern.compile(COMM_DEL);
        pExit = Pattern.compile(COMM_EXIT);

    }

    public ResultType send(String command) {

        handleCommand(command);
        return resType;

    }

    private void handleCommand(String command) {

        mShow = pShow.matcher(command);
        mAdd = pAdd.matcher(command);
        mDel = pDel.matcher(command);
        mExit = pExit.matcher(command);
        int length = command.length();
        StringTokenizer tokenizer = new StringTokenizer(command, " ");
        tokenizer.nextToken();
        if (mShow.matches() && length == mShow.group().length()){
            String parameter = tokenizer.nextToken().toString();
            ResultSet resultSetSt, resultSetRes;
            resultSetSt = table.showStudents();
            generateStudentList(resultSetSt);
            switch (parameter){
                case PARAM_SHOW_LIST:
                    resType = ResultType.SHOW_LIST;
                    break;
                case PARAM_SHOW_ALL:
                    resType = ResultType.SHOW_ALL;
                    resultSetRes = table.showResults();
                    generateResultList(resultSetRes);
                    break;
                case PARAM_SHOW_BRIEF:
                    resType = ResultType.SHOW_BAD;
                    resultSetRes = table.showResults();
                    generateResultList(resultSetRes);
                    break;
                default:
                    break;
            }
        }
        else if (mAdd.matches() && length == mAdd.group().length()){
            String name = tokenizer.nextToken().toString();
            String group = tokenizer.nextToken().toString();
            String math = tokenizer.nextToken().toString();
            String programming = tokenizer.nextToken().toString();
            String pe = tokenizer.nextToken().toString();
            table.add(name, Integer.parseInt(group), Integer.parseInt(math), Integer.parseInt(programming), Integer.parseInt(pe));
            resType = ResultType.NOTHING;
        }
        else if (mDel.matches() && length == mDel.group().length()){
            String parameter = tokenizer.nextToken().toString();
            switch (parameter){
                case PARAM_DEL_ID:
                    int id = Integer.parseInt(tokenizer.nextToken());
                    table.delId(id);
                    break;
                case PARAM_DEL_BAD:
                    table.delBad(VerdictType.Failed);
                    break;
                default:
                    break;
            }
            resType = ResultType.NOTHING;
        }
        else if (mExit.matches() && length == mExit.group().length()){
            resType = ResultType.EXIT;
        }
        else resType = ResultType.NOTHING;

    }

    private void generateResultList(ResultSet resultSet) {

        try {

            StringBuffer strVerdict = new StringBuffer("");
            VerdictType vt;
            int id, math, programming, pe, studentID;
            Result r;
            results = new ArrayList<>();
            while (resultSet.next()){
                id = resultSet.getInt("id");
                math = resultSet.getInt("math");
                programming = resultSet.getInt("programming");
                pe = resultSet.getInt("PE");
                strVerdict.append(resultSet.getString("verdict"));
                studentID = resultSet.getInt("studentID");
                vt = VerdictType.getVerdictType(strVerdict.toString());

                r = new Result(id, math, programming, pe, vt, studentID);
                results.add(r);

                strVerdict.delete(0, strVerdict.length());
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void generateStudentList(ResultSet resultSet) {

        try {

            StringBuffer strName = new StringBuffer("");
            int id, group;
            Student s;
            students = new ArrayList<>();
            while (resultSet.next()){
                id = resultSet.getInt("id");
                strName.append(resultSet.getString("name"));
                group = resultSet.getInt("groupNum");

                s = new Student(id, strName.toString(), group);
                students.add(s);

                strName.delete(0, strName.length());
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Student> getStudents() {

        return students;

    }

    public ArrayList<Result> getResults() {

        return results;

    }
}
