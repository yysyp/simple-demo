package ps.demo.monkey;

import com.github.kwhat.jnativehook.NativeInputEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseWheelEvent;
import ps.demo.monkey.model.MkRecord;
import ps.demo.util.MyFileUtil;
import ps.demo.util.MyReadWriteUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MkRecordPlayMan {

    private static File file;

    public static void initRecordFile() {
        file = MyFileUtil.getFileTsInHomeDir("mkrecords.txt");
    }

    public static void saveRecord(MkRecord mkRecord) {
        MyReadWriteUtil.writeObjectToFile(file, mkRecord);
    }

    public static List<MkRecord> loadRecords(File file) {
        return MyReadWriteUtil.readObjectsFromFile(file, MkRecord.class);
    }

    public static List<MkRecord> type(String str) {
        List<MkRecord> mkRecordList = new ArrayList<>();
        char[] chars = str.toCharArray();
        for (char c : chars) {
            MkRecord mkRecord = new MkRecord();
            mkRecord.key(2401, 0, c, convertToKeyCode(c), NativeKeyEvent.CHAR_UNDEFINED, NativeKeyEvent.KEY_LOCATION_STANDARD);
            mkRecord.key(2400, 0, c, 0, c, NativeKeyEvent.KEY_LOCATION_STANDARD);
            mkRecord.key(2402, 0, c, convertToKeyCode(c), NativeKeyEvent.CHAR_UNDEFINED, NativeKeyEvent.KEY_LOCATION_STANDARD);
        }
        return mkRecordList;
    }

    public static NativeInputEvent getAsNativeEvent(MkRecord mkRecord) {
        switch (mkRecord.getEventType()) {
            case MkRecord.KEY_TYPE:
                return new NativeKeyEvent(mkRecord.getId(), mkRecord.getModifiers(),
                        mkRecord.getRawCode(), mkRecord.getKeyCode(), mkRecord.getKeyChar(),
                        mkRecord.getKeyLocation());
            case MkRecord.MOUSE_TYPE:
                return new NativeMouseEvent(mkRecord.getId(), mkRecord.getModifiers(), mkRecord.getX(),
                        mkRecord.getY(), mkRecord.getClickCount(), mkRecord.getButton());
            default: MOUSE_WHEEL_TYPE:
            return new NativeMouseWheelEvent(mkRecord.getId(), mkRecord.getModifiers(), mkRecord.getX(),
                    mkRecord.getY(), mkRecord.getClickCount(), mkRecord.getScrollType(),
                    mkRecord.getScrollAmount(), mkRecord.getWheelRotation(), mkRecord.getWheelDirection());
        }
    }

    public static int convertToKeyCode(char c) {
        switch (c) {
            case '1': return NativeKeyEvent.VC_1;
            case '2': return NativeKeyEvent.VC_2;
            case '3': return NativeKeyEvent.VC_3;
            case '4': return NativeKeyEvent.VC_4;
            case '5': return NativeKeyEvent.VC_5;
            case '6': return NativeKeyEvent.VC_6;
            case '7': return NativeKeyEvent.VC_7;
            case '8': return NativeKeyEvent.VC_8;
            case '9': return NativeKeyEvent.VC_9;
            case '0': return NativeKeyEvent.VC_0;
            case 'a':
            case 'A':
                return NativeKeyEvent.VC_A;
            case 'b':
            case 'B':
                return NativeKeyEvent.VC_A;
            case 'c':
            case 'C':
                return NativeKeyEvent.VC_A;
            case 'd':
            case 'D':
                return NativeKeyEvent.VC_A;
            case 'e':
            case 'E':
                return NativeKeyEvent.VC_A;
            case 'f':
            case 'F':
                return NativeKeyEvent.VC_A;
            case 'g':
            case 'G':
                return NativeKeyEvent.VC_A;
            case 'h':
            case 'H':
                return NativeKeyEvent.VC_A;
            case 'i':
            case 'I':
                return NativeKeyEvent.VC_A;
            case 'j':
            case 'J':
                return NativeKeyEvent.VC_A;
            case 'k':
            case 'K':
                return NativeKeyEvent.VC_A;
            case 'l':
            case 'L':
                return NativeKeyEvent.VC_A;
            case 'm':
            case 'M':
                return NativeKeyEvent.VC_A;
            case 'n':
            case 'N':
                return NativeKeyEvent.VC_A;
            case 'o':
            case 'O':
                return NativeKeyEvent.VC_A;
            case 'p':
            case 'P':
                return NativeKeyEvent.VC_A;
            case 'q':
            case 'Q':
                return NativeKeyEvent.VC_A;
            case 'r':
            case 'R':
                return NativeKeyEvent.VC_A;
            case 's':
            case 'S':
                return NativeKeyEvent.VC_A;
            case 't':
            case 'T':
                return NativeKeyEvent.VC_A;
            case 'u':
            case 'U':
                return NativeKeyEvent.VC_A;
            case 'v':
            case 'V':
                return NativeKeyEvent.VC_A;
            case 'w':
            case 'W':
                return NativeKeyEvent.VC_A;
            case 'x':
            case 'X':
                return NativeKeyEvent.VC_A;
            case 'y':
            case 'Y':
                return NativeKeyEvent.VC_A;
            case 'z':
            case 'Z':
                return NativeKeyEvent.VC_A;
            case '-':
                return NativeKeyEvent.VC_MINUS;
            case '_':
                return NativeKeyEvent.VC_UNDERSCORE;
            case '=':
            case '+':
                return NativeKeyEvent.VC_EQUALS;
            case ';':
            case ':':
                return NativeKeyEvent.VC_SEMICOLON;
            case '\'':
            case '"':
                return NativeKeyEvent.VC_QUOTE;
            case ',':
            case '<':
                return NativeKeyEvent.VC_COMMA;
            case '.':
            case '>':
                return NativeKeyEvent.VC_PERIOD;
            case '/':
            case '?':
                return NativeKeyEvent.VC_SLASH;
            case '\t':
                return NativeKeyEvent.VC_TAB;
            case ' ':
                return NativeKeyEvent.VC_SPACE;
            default: return NativeKeyEvent.VC_ENTER;
        }
    }

}
