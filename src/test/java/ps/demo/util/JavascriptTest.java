package ps.demo.util;

import javax.script.*;

public class JavascriptTest {

    public static String format(final String fmt, final Object... args) {
        return String.format(fmt, args);
    }


    static ForJsCall jv = new ForJsCall();
    public static void main(String[] args) {
        String jsscripts = "function add(a, b){ return a+b+''+jv.hi('yyyyMM'); }; jv.hi('gogo');";
        final ScriptEngineManager engineManager = new ScriptEngineManager();
        final ScriptEngine engine = engineManager.getEngineByName("Nashorn");
        String js1 = "var Calendar=Java.type(\"java.util.Calendar\"); Calendar.getInstance().getTime()";
        try {
            Object result = engine.eval(js1);
            System.out.println("--->>"+result);

            final String js2 = "var format=Java.type(\"" + JavascriptTest.class.getName() + "\").format; format(5);";
            result = engine.eval(js2);
            System.out.println("--->>"+result);

//            Bindings bindings = engine.getBindings(ScriptContext.GLOBAL_SCOPE);
//            bindings.put("jv", jv);
//            Object obj = engine.eval(jsscripts);
//            System.out.println("js eval result="+obj);
//            engine.eval(jsscripts);
//            if (engine instanceof Invocable) {
//                Invocable invoke = (Invocable) engine;
//                String result = (String) invoke.invokeFunction(
//                        "add",
//                        3,
//                        2);
//                System.out.println(result);
//            } else {
//                System.out.println("error");
//            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static class ForJsCall {

        public static String getNowFromJava(String haha) {
            return MyTimeUtil.getNowStr(haha);
        }

        public String hi(String strFromJs) {
            return "Hi "+strFromJs+" from java";
        }
    }

}
