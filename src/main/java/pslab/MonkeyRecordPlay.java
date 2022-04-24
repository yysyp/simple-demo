package pslab;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.NativeInputEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import lombok.extern.slf4j.Slf4j;
import ps.demo.monkey.MkRecordPlayMan;
import ps.demo.monkey.model.MkRecord;
import ps.demo.util.MyArgsUtil;
import ps.demo.util.MyFileUtil;
import ps.demo.util.MyReadWriteUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class MonkeyRecordPlay {

    public static void main(String[] args) throws NativeHookException, InterruptedException {
        Map<String, String> argsMap = MyArgsUtil.assemberArgs(args);
//        if (argsMap.containsKey("file")) {
//
//        }
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GlobalScreen.unregisterNativeHook();
                } catch (NativeHookException e) {
                    e.printStackTrace();
                }
            }
        }));
        GlobalScreen.registerNativeHook();
//`1234567890-=   qwertyuiop[]\asdfghjkl;'
//    zxcvbnm,./[ ]~!@#$%^&*()_+QWERTYUIOP{}|ASDFGHJKL:"ZXCVBNM<>?

        //File file = MyFileUtil.getFileInHomeDir("2022-04-24_135849-mkscript.txt");
        List<MkRecord> mkRecordList = new ArrayList<>();//MyReadWriteUtil.readObjectsFromFile(file, MkRecord.class);
        mkRecordList.addAll(MkRecordPlayMan.type(
                "`1234567890-=" +
                "\tqwertyuiop[]\\" +
                "asdfghjkl;'\n" +
                        "zxcvbnm,./" +
                        "[ ]"+
                "~!@#$%^&*()_+"+
                "QWERTYUIOP{}|" +
                        "ASDFGHJKL:\"" +
                        "ZXCVBNM<>?"));
        long i = 0;
        for (MkRecord mkRecord : mkRecordList) {
            i++;
            log.info("Mk processing line[{}]: {}", i, mkRecord);
            GlobalScreen.postNativeEvent(MkRecordPlayMan.getAsNativeEvent(mkRecord));
            Thread.sleep(50);
        }

        System.exit(0);
    }

}
