package travnich.entity;

public class Student {


    private int id;

    public int getId() {
        return id;
    }

    private String name;
    private int group;

    public Student(int id, String strName, int group) {

        this.id = id;
        this.name = strName;
        this.group = group;

    }

    public static String getColumns(){

        return "\t\tid" + "\t\tname" + "\t\tgroup";

    }

    public String toString() {
        return "\t\t" + id +
                "\t\t" + name  +
                "\t\t  " + group;
    }
}
