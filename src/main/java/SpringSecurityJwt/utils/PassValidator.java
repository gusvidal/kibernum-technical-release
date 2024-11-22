package SpringSecurityJwt.utils;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor
public class PassValidator {
    public static Boolean validaPassword (String pass, String regularExp) {
        Pattern pattern = Pattern.compile(regularExp);
        Matcher matcher = pattern.matcher(pass);
        return matcher.matches();
    }
}
