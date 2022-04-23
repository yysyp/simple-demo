package ps.demo.monkey.model;

import com.github.kwhat.jnativehook.NativeInputEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseWheelEvent;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class MkRecord implements Serializable {
    public static final String KEY_TYPE = "Key";
    public static final String MOUSE_TYPE = "Mouse";
    public static final String MOUSE_WHEEL_TYPE = "Wheels";

    private String eventType;
    private int id;
    private int modifiers;
    private int rawCode;
    private int keyCode;
    private char keyChar;
    private int keyLocation;

    private int x;
    private int y;
    private int clickCount;
    private int button;

    private int scrollType;
    private int scrollAmount;
    private int wheelRotation;
    private int wheelDirection;



    public void key(int id, int modifiers, int rawCode, int keyCode, char keyChar, int keyLocation) {
        this.eventType = KEY_TYPE;
        this.id = id;
        this.modifiers = modifiers;
        this.rawCode = rawCode;
        this.keyCode = keyCode;
        this.keyChar = keyChar;
        this.keyLocation = keyLocation;
    }

    public void key(NativeKeyEvent e) {
        key(e.getID(), e.getModifiers(), e.getRawCode(), e.getKeyCode(), e.getKeyChar(), e.getKeyLocation());
    }

    public void mouse(int id, int modifiers, int x, int y, int clickCount, int button) {
        this.eventType = MOUSE_TYPE;
        this.id = id;
        this.modifiers = modifiers;
        this.x = x;
        this.y = y;
        this.clickCount = clickCount;
        this.button = button;
    }

    public void mouse(NativeMouseEvent e) {
        mouse(e.getID(), e.getModifiers(), e.getX(), e.getY(), e.getClickCount(), e.getButton());
    }

    public void wheel(int id, int modifiers, int x, int y, int clickCount, int scrollType, int scrollAmount, int wheelRotation, int wheelDirection) {
        this.eventType = MOUSE_WHEEL_TYPE;
        this.id = id;
        this.modifiers = modifiers;
        this.x = x;
        this.y = y;
        this.clickCount = clickCount;
        this.scrollType = scrollType;
        this.scrollAmount = scrollAmount;
        this.wheelRotation = wheelRotation;
        this.wheelDirection = wheelDirection;
    }

    public void wheel(NativeMouseWheelEvent e) {
        wheel(e.getID(), e.getModifiers(), e.getX(), e.getY(), e.getClickCount(), e.getScrollType(), e.getScrollAmount(), e.getWheelRotation(), e.getWheelDirection());
    }




}
