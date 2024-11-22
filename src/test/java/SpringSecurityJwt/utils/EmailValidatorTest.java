package SpringSecurityJwt.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EmailValidatorTest {
    String emailOk = "email.de.prueba@gmail.com";
    String emailFail = "correo.sin.arroba.com";
    String regex = "^([0-9a-zA-Z]+[-._+&])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$";

    @Test
    public void validaEmailOkTest(){
        EmailValidator validator = new EmailValidator();
        assertEquals(validator.validaEmail(emailOk,regex),true);
    }

    @Test
    public void validaEmailFailTest(){
        EmailValidator validator = new EmailValidator();
        assertEquals(validator.validaEmail(emailFail,regex),false);
    }

}