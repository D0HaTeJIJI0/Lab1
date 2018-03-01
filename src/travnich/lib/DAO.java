package travnich.lib;

import travnich.entity.Result;
import travnich.enums.VerdictType;
import travnich.exceptions.WrongMarkException;

import java.sql.*;

import static travnich.constants.CommandsSQLTemplates.*;
import static travnich.constants.SettingsTemplates.*;

public class DAO {

    private Connection con;
    private Statement showSt, getSt, showRes;
    private PreparedStatement addSt, delStId, getBadStId, addRes;

    public DAO(){

        try {
            con = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            showSt = con.createStatement();
            showRes = con.createStatement();
            addSt = con.prepareStatement(ADD_STUDENT);
            addRes = con.prepareStatement(ADD_RESULT);
            delStId = con.prepareStatement(DEL_ID_STUDENT);
            getBadStId = con.prepareStatement(GET_BAD_STUDENT_ID);
            getSt = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public ResultSet showStudents() {

        try {
            ResultSet resultSet = showSt.executeQuery(SHOW_STUDENT);
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ResultSet showResults() {

        try {
            ResultSet resultSet = showRes.executeQuery(SHOW_RESULT);
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public void add(String studentName, int group, int math, int programming, int pe) {

        try {
            con.setAutoCommit(false);
            addSt.setString(1, studentName);
            addSt.setInt(2, group);
            addSt.executeUpdate();
            int id = getStId();
            if (id == -1) return;
            addRes.setInt(1, math);
            addRes.setInt(2, programming);
            addRes.setInt(3, pe);
            addRes.setString(4, Result.getVerdictType(math, programming, pe).name());
            addRes.setInt(5, id);
            addRes.executeUpdate();
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (WrongMarkException e) {
            e.printStackTrace();
        }

    }

    public int getStId() {

        try {
            ResultSet rs = getSt.executeQuery(GET_LAST_STUDENT_ID);
            rs.next();
            return rs.getInt("max(id)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;

    }


    public void delId(int id) {

        try {
            delStId.setInt(1, id);
            delStId.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delBad(VerdictType vt) {

        try {
            getBadStId.setString(1, vt.name());
            ResultSet rs = getBadStId.executeQuery();
            while (rs.next()){
                int id = rs.getInt("StudentID");
                delId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    protected void finalize() throws Throwable{

        showSt.close();
        showRes.close();
        addSt.close();
        addRes.close();
        delStId.close();
        getBadStId.close();
        getSt.close();
        con.close();

    }
}
