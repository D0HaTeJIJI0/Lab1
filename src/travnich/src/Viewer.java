package travnich.src;

import travnich.entity.Result;
import travnich.entity.Student;
import travnich.enums.ResultType;
import travnich.enums.VerdictType;
import travnich.lib.Controller;
import java.util.ArrayList;
import java.util.Scanner;

public class Viewer{

    private Thread t;
    Controller controller;
    Scanner sc = new Scanner(System.in);

    private Viewer(){

        t = new Thread(() -> {

            controller = new Controller();
            while (true) {
                while (sc.hasNext() == false);
                String commandLine = sc.nextLine();
                ResultType res = controller.send(commandLine);
                ArrayList<Student> students;
                ArrayList<Result> results;
                switch (res) {
                    case SHOW_LIST:
                        students = controller.getStudents();
                        showList(students);
                        break;
                    case SHOW_BAD:
                        students = controller.getStudents();
                        results = controller.getResults();
                        showBad(students, results);
                        break;
                    case SHOW_ALL:
                        students = controller.getStudents();
                        results = controller.getResults();
                        showAll(students, results);
                        break;
                    case NOTHING:
                        break;
                    case EXIT:
                        return;
                    default:
                        break;
                }
            }

        });
        t.start();

    }

    private void showAll(ArrayList<Student> students, ArrayList<Result> results) {

        System.out.println("Student's list:");
        System.out.print(Student.getColumns());
        System.out.println(Result.getColumns());
        for (Student s: students){
            System.out.print(s.toString());
            for (Result r : results){
                if (r.getStudentID() == s.getId()){
                    System.out.println(r.toString());
                }
            }
        }

    }

    private void showBad(ArrayList<Student> students, ArrayList<Result> results) {

        System.out.println("List of bad students:");
        System.out.print(Student.getColumns());
        System.out.println(Result.getVerdictColumn());
        boolean hasBad = false;
        for (Result r : results){
            VerdictType v = r.getVerdict();
            if (v.equals(VerdictType.Failed)){
                hasBad = true;
                int studentID = r.getStudentID();
                for (Student s: students){
                    if (studentID == s.getId()) {
                        System.out.print(s.toString());
                        System.out.println("\t\t" + v);
                    }
                }
            }
        }
        if (hasBad) suggestDelBad();

    }

    private void suggestDelBad() {

        System.out.print("Do you want them to be excluded?(Y/N): ");
        while (sc.hasNext() == false);
        String answer = sc.nextLine();
        switch (answer.toString()){
            case "Y":
                String command = "del /bad";
                controller.send(command);
                break;
            default:
                break;
        }

    }

    private void showList(ArrayList<Student> students) {

        System.out.println("Student's list:");
        System.out.println(Student.getColumns());
        for (Student s: students){
            System.out.println(s.toString());
        }

    }

    public static void main(String args[]){

        new Viewer();

    }

    @Override
    protected void finalize() throws Throwable {
        sc.close();
    }
}
