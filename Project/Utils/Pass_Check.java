package Project.Utils;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
public class Pass_Check {
    public boolean IsValid(String password){
        Pattern pattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d).{8,}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
