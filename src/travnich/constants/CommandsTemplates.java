package travnich.constants;

public class CommandsTemplates {

    public final static String COMM_SHOW = "show /[a-z]+";
    public final static String COMM_ADD = "add [a-zA-Z ]+ [0-9]+ [0-9]+ [0-9]+ [0-9]+";
    public final static String COMM_DEL = "del ((/bad)|(/id [0-9]+))";
    public final static String COMM_EXIT = "exit";
    public final static String PARAM_DEL_BAD = "/bad";
    public final static String PARAM_DEL_ID = "/id";
    public final static String PARAM_SHOW_ALL = "/all";
    public final static String PARAM_SHOW_BRIEF = "/bad";
    public final static String PARAM_SHOW_LIST = "/list";
}
