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
public class Visualisation1
{
    RenderArea renderArea;
    
    PImage bg;
    //Camera Rotation
    float cameraX, cameraY;
    MouseEvent lastMousePosition;
    float MOUSE_SENSITIVITY = 300f;

    Data data;
    
    public Visualisation1(RenderArea renderArea)
    {
       //like processing setup
    }

    public void draw()
    {
        //processing draw bit
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
