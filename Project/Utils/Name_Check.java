package Project.Utils;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
public class Name_Check {
    public static boolean IsValidname(String name){
        Pattern pattern =Pattern.compile("^(?=[A-Za-z])(?=.*[A-Z])[A-Za-z0-9_]{6,20}$");
        Matcher matcher= pattern.matcher(name);
        return matcher.matches();
    }
}
