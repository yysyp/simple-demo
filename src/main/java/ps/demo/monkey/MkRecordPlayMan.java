package ps.demo.monkey;

import com.github.kwhat.jnativehook.NativeInputEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseWheelEvent;
import org.apache.commons.lang3.CharUtils;
import ps.demo.monkey.model.MkRecord;
import ps.demo.util.MyFileUtil;
import ps.demo.util.MyReadWriteUtil;

import javax.print.attribute.standard.MediaSize;
import java.awt.*;
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

            int modifiers = 0;
            if (shiftMaskCheck(c) != 0) {
                modifiers = modifiers | NativeKeyEvent.SHIFT_L_MASK;
                MkRecord mkRecord = new MkRecord();
                mkRecord.key(NativeKeyEvent.NATIVE_KEY_PRESSED, modifiers, 160, NativeKeyEvent.VC_SHIFT, NativeKeyEvent.CHAR_UNDEFINED, NativeKeyEvent.KEY_LOCATION_STANDARD);
                mkRecordList.add(mkRecord);
                mkRecord = new MkRecord();
                mkRecord.key(NativeKeyEvent.NATIVE_KEY_PRESSED, modifiers, c, convertToKeyCode(c), NativeKeyEvent.CHAR_UNDEFINED, NativeKeyEvent.KEY_LOCATION_STANDARD);
                mkRecordList.add(mkRecord);
                mkRecord = new MkRecord();
                mkRecord.key(NativeKeyEvent.NATIVE_KEY_TYPED, modifiers, c, 0, c, NativeKeyEvent.KEY_LOCATION_STANDARD);
                mkRecordList.add(mkRecord);
                mkRecord = new MkRecord();
                mkRecord.key(NativeKeyEvent.NATIVE_KEY_RELEASED, modifiers, 160, NativeKeyEvent.VC_SHIFT, NativeKeyEvent.CHAR_UNDEFINED, NativeKeyEvent.KEY_LOCATION_STANDARD);
                mkRecordList.add(mkRecord);
                mkRecord = new MkRecord();
                mkRecord.key(NativeKeyEvent.NATIVE_KEY_RELEASED, 0, c, convertToKeyCode(c), NativeKeyEvent.CHAR_UNDEFINED, NativeKeyEvent.KEY_LOCATION_STANDARD);
                mkRecordList.add(mkRecord);
            } else {
                MkRecord mkRecord = new MkRecord();
                mkRecord.key(NativeKeyEvent.NATIVE_KEY_PRESSED, modifiers, c, convertToKeyCode(c), NativeKeyEvent.CHAR_UNDEFINED, NativeKeyEvent.KEY_LOCATION_STANDARD);
                mkRecordList.add(mkRecord);
                mkRecord = new MkRecord();
                mkRecord.key(NativeKeyEvent.NATIVE_KEY_TYPED, modifiers, c, 0, c, NativeKeyEvent.KEY_LOCATION_STANDARD);
                mkRecordList.add(mkRecord);
                mkRecord = new MkRecord();
                mkRecord.key(NativeKeyEvent.NATIVE_KEY_RELEASED, 0, c, convertToKeyCode(c), NativeKeyEvent.CHAR_UNDEFINED, NativeKeyEvent.KEY_LOCATION_STANDARD);
                mkRecordList.add(mkRecord);
            }
        }
        return mkRecordList;
    }

    public static NativeInputEvent getAsNativeEvent(MkRecord mkRecord) {
        switch (mkRecord.getEventType()) {
            case MkRecord.KEY_TYPE:
                return new NativeKeyEvent(mkRecord.getId(), mkRecord.getModifiers(),
                        mkRecord.getRawCode(), mkRecord.getKeyCode(), (char)mkRecord.getKeyChar(),
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

    public static int shiftMaskCheck(char c) {
        switch (c) {
            case '~':
            case '!':
            case '@':
            case '#':
            case '$':
            case '%':
            case '^':
            case '&':
            case '*':
            case '(':
            case ')':
            case '_':
            case '+':
            case 'Q':
            case 'W':
            case 'E':
            case 'R':
            case 'T':
            case 'Y':
            case 'U':
            case 'I':
            case 'O':
            case 'P':
            case '{':
            case '}':
            case '|':
            case 'A':
            case 'S':
            case 'D':
            case 'F':
            case 'G':
            case 'H':
            case 'J':
            case 'K':
            case 'L':
            case ':':
            case '"':
            case 'Z':
            case 'X':
            case 'C':
            case 'V':
            case 'B':
            case 'N':
            case 'M':
            case '<':
            case '>':
            case '?':
                return NativeKeyEvent.SHIFT_L_MASK;
            default: return 0;
        }

    }

    public static int convertToKeyCode(char c) {
        switch (c) {
            case '`':
            case '~':
                return NativeKeyEvent.VC_BACKQUOTE;
            case '1':
            case '!': return NativeKeyEvent.VC_1;
            case '2':
            case '@':
                return NativeKeyEvent.VC_2;
            case '3':
            case '#':
                return NativeKeyEvent.VC_3;
            case '4':
            case '$':
                return NativeKeyEvent.VC_4;
            case '5':
            case '%':
                return NativeKeyEvent.VC_5;
            case '6':
            case '^':
                return NativeKeyEvent.VC_6;
            case '7':
            case '&':
                return NativeKeyEvent.VC_7;
            case '8':
            case '*':
                return NativeKeyEvent.VC_8;
            case '9':
            case '(':
                return NativeKeyEvent.VC_9;
            case '0':
            case ')':
                return NativeKeyEvent.VC_0;
            case 'a':
            case 'A':
                return NativeKeyEvent.VC_A;
            case 'b':
            case 'B':
                return NativeKeyEvent.VC_B;
            case 'c':
            case 'C':
                return NativeKeyEvent.VC_C;
            case 'd':
            case 'D':
                return NativeKeyEvent.VC_D;
            case 'e':
            case 'E':
                return NativeKeyEvent.VC_E;
            case 'f':
            case 'F':
                return NativeKeyEvent.VC_F;
            case 'g':
            case 'G':
                return NativeKeyEvent.VC_G;
            case 'h':
            case 'H':
                return NativeKeyEvent.VC_H;
            case 'i':
            case 'I':
                return NativeKeyEvent.VC_I;
            case 'j':
            case 'J':
                return NativeKeyEvent.VC_J;
            case 'k':
            case 'K':
                return NativeKeyEvent.VC_K;
            case 'l':
            case 'L':
                return NativeKeyEvent.VC_L;
            case 'm':
            case 'M':
                return NativeKeyEvent.VC_M;
            case 'n':
            case 'N':
                return NativeKeyEvent.VC_N;
            case 'o':
            case 'O':
                return NativeKeyEvent.VC_O;
            case 'p':
            case 'P':
                return NativeKeyEvent.VC_P;
            case 'q':
            case 'Q':
                return NativeKeyEvent.VC_Q;
            case 'r':
            case 'R':
                return NativeKeyEvent.VC_R;
            case 's':
            case 'S':
                return NativeKeyEvent.VC_S;
            case 't':
            case 'T':
                return NativeKeyEvent.VC_T;
            case 'u':
            case 'U':
                return NativeKeyEvent.VC_U;
            case 'v':
            case 'V':
                return NativeKeyEvent.VC_V;
            case 'w':
            case 'W':
                return NativeKeyEvent.VC_W;
            case 'x':
            case 'X':
                return NativeKeyEvent.VC_X;
            case 'y':
            case 'Y':
                return NativeKeyEvent.VC_Y;
            case 'z':
            case 'Z':
                return NativeKeyEvent.VC_Z;
            case '-':
            case '_':
                return NativeKeyEvent.VC_MINUS;
            case '=':
            case '+':
                return NativeKeyEvent.VC_EQUALS;
            case '[':
            case '{':
                return NativeKeyEvent.VC_OPEN_BRACKET;
            case ']':
            case '}':
                return NativeKeyEvent.VC_CLOSE_BRACKET;
            case '\\':
            case '|':
                return NativeKeyEvent.VC_BACK_SLASH;
            case ';':
            case ':':
                return NativeKeyEvent.VC_SEMICOLON;
            case '\'':
            case '"':
                return NativeKeyEvent.VC_QUOTE;
            case '\n':
                return NativeKeyEvent.VC_ENTER;
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
            default: return 0;
        }
    }

}
