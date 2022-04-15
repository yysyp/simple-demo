package ps.demo.common;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyBaseController {

    public final static String DATE_FORMAT_STR_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";


    @InitBinder
    protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_STR_ISO8601);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new MyCustomDateEditor(dateFormat, true));
    }

}
