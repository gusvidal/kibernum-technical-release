package SpringSecurityJwt.utils;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
@NoArgsConstructor
public class Fecha{

    final String FORMATO_FECHA = "yyyy/MM/dd HH:mm:ss";

    public String GetNow() {
        Date todayDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(this.FORMATO_FECHA);
        String fechaActual = sdf.format(todayDate);

        return fechaActual;
    }
}
