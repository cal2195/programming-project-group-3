package programmingproject;

import java.awt.event.MouseEvent;
import java.util.Random;
import static processing.core.PApplet.radians;
import static processing.core.PConstants.P3D;
import processing.core.PImage;

/**
 *
 * @author cal
 */
public class DisplayWithProcessingText
{
    RenderArea renderArea;
    
    PImage bg;
    //Camera Rotation
    float cameraX, cameraY;
    MouseEvent lastMousePosition;
    float MOUSE_SENSITIVITY = 300f;

    Data data;
    
    public DisplayWithProcessingText(RenderArea renderArea)
    {
        //color background2 = color();
        this.renderArea = renderArea;
    }

    public void draw()
    {
          renderArea.textSize(15);
          renderArea.text("LOOOOOOOSE", 150, 20);

    }

    public void mouseDragged(MouseEvent e)
    {
        if (lastMousePosition == null)
        {
            lastMousePosition = e;
        }
        cameraX -= (e.getXOnScreen() - lastMousePosition.getXOnScreen()) / MOUSE_SENSITIVITY;
        cameraY -= (e.getYOnScreen() - lastMousePosition.getYOnScreen()) / MOUSE_SENSITIVITY;
        lastMousePosition = e;
    }

    public void mouseReleased(MouseEvent e)
    {
        lastMousePosition = null;
    }
}
