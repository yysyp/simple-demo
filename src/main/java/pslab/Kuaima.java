package pslab;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;
import ps.demo.util.MyThymeleafUtil;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Kuaima {

    public static void main(String[] args) {
        String content = "[(${name})]:" +
                "[# th:each=\"item : ${items}\"]\n" +
                "  - [(${item})]\n" +
                "[/]" +
                "[( ${env.OS} )]";
        Map<String, Object> params = new HashMap<>();
        params.put("name", "Patrick");
        System.out.println(MyThymeleafUtil.processText(content, params));
    }

//    public static void main1(String[] args) {
//        TemplateEngine templateEngine = new TemplateEngine();
//        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
//        resolver.setPrefix("/templates/");
//        resolver.setSuffix(".html");
//        resolver.setCharacterEncoding("UTF-8");
//        resolver.setTemplateMode(TemplateMode.HTML);
//        templateEngine.setTemplateResolver(resolver);
//        Context ct = new Context();
//        ct.setVariable("name", "foo");
//        ct.setVariable("date", LocalDateTime.now().toString());
//        System.out.println(templateEngine.process("greeting.html", ct));
//    }

}
