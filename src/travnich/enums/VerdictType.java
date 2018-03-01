package travnich.enums;

import static travnich.constants.ConsoleColorTemplates.*;

public enum VerdictType { Passed, Failed;

    private static final String FAILED = "Failed";
    private static final String PASSED = "Passed";

    public static VerdictType getVerdictType(String verdict){

        switch (verdict){
            case FAILED:
                return Failed;
            default:
                return Passed;
        }

    }

    @Override
    public String toString() {
        switch (this){
            case Failed:
                return ANSI_RED + FAILED + ANSI_RESET;
            case Passed:
                return ANSI_GREEN + PASSED + ANSI_RESET;
            default:
                break;
        }
        return super.toString();
    }

}
