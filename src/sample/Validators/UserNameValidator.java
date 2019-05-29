package sample.Validators;

import java.util.regex.Pattern;

public class UserNameValidator {
    static public boolean validate(String userName){
        Pattern pattern =  Pattern.compile("^[^\\s]+$");
        return pattern.matcher(userName).matches();
    }
}
