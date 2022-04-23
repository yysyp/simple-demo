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
import java.util.List;
import java.util.Map;

@Slf4j
public class MonkeyRecordPlay {

    public static void main(String[] args) throws NativeHookException, InterruptedException {
        Map<String, String> argsMap = MyArgsUtil.assemberArgs(args);
        //if (argsMap.containsKey(""))
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

        File file = MyFileUtil.getFileInHomeDir("2022-04-23_215125-mkrecords.txt");
        List<MkRecord> mkRecordList = MyReadWriteUtil.readObjectsFromFile(file, MkRecord.class);
        long i = 0;
        for (MkRecord mkRecord : mkRecordList) {
            i++;
            log.info("Mk processing line[{}]: {}", i, mkRecord);
            GlobalScreen.postNativeEvent(MkRecordPlayMan.getAsNativeEvent(mkRecord));
            Thread.sleep(30);
        }

        System.exit(0);
    }

}
