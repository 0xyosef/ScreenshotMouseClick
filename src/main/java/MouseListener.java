
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;


public class MouseListener implements NativeMouseInputListener {
    public void nativeMousePressed(NativeMouseEvent e) {
        System.out.println("Mouse Pressed: " + e.getButton());
        if (e.getButton() == 1 || e.getButton() == 2) {
            MainController.takeScreenshot();
            MainController.saveScreenshot();
        }
    }
}