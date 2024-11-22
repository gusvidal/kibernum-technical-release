package SpringSecurityJwt.utils;

import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor
public class EmailValidator {
    public static Boolean validaEmail (String email, String regularExp) {
        Pattern pattern = Pattern.compile(regularExp);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
