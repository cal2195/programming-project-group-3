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
public class HeightMapGraph
{
    RenderArea renderArea;
    
    PImage bg;
    Tower[] towers = new Tower[30];
    Tower[] otherTowers = new Tower[30];
    float percent = 0;
    Random random = new Random();

    //Camera Rotation
    float cameraX, cameraY;
    MouseEvent lastMousePosition;
    float MOUSE_SENSITIVITY = 300f;

    Data data;
    
    public HeightMapGraph(RenderArea renderArea)
    {
        this.renderArea = renderArea;
        bg = renderArea.loadImage("res/newyork.png");
        
        for (int i = 0; i < towers.length; i++)
        {
            towers[i] = new Tower(random.nextInt(renderArea.width - 20) - (renderArea.width / 2) - 20, random.nextInt(renderArea.height - 20) - (renderArea.height / 2) - 20, random.nextInt(100));
        }
        for (int i = 0; i < otherTowers.length; i++)
        {
            otherTowers[i] = new Tower(random.nextInt(renderArea.width - 20) - (renderArea.width / 2) - 20, random.nextInt(renderArea.height - 20) - (renderArea.height / 2) - 20, random.nextInt(100));
        }
    }

    public void draw()
    {
        renderArea.background(0);
        if (percent < 1)
        {
            percent += 0.005;
        }

        renderArea.translate(renderArea.width / 2, renderArea.height / 2, 0);
        renderArea.rotateX(cameraY);
        renderArea.rotateZ(cameraX);
        renderArea.image(bg, -renderArea.width / 2, -renderArea.height / 2, renderArea.width, renderArea.height);
        renderArea.fill(255, 0, 0);

        for (int i = 0; i < towers.length; i++)
        {
            renderArea.pushMatrix();
            renderArea.translate(towers[i].x, towers[i].y, towers[i].z * percent / 2);
            renderArea.rotateZ(radians(30));
            renderArea.box(20, 20, towers[i].z * percent);
            renderArea.popMatrix();
        }

        renderArea.fill(255, 255, 0);
        for (int i = 0; i < otherTowers.length; i++)
        {
            renderArea.pushMatrix();
            renderArea.translate(otherTowers[i].x, otherTowers[i].y, otherTowers[i].z * percent / 2);
            renderArea.rotateZ(radians(30));
            renderArea.box(20, 20, otherTowers[i].z * percent);
            renderArea.popMatrix();
        }
        //testCoords();
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

//    public void testCoords()
//    {
//        float lat = 40.664000f;
//        float lon = -73.716696f;
//        float ZPos = Data.latToYPos(lat, height);
//        float XPos = Data.longToXPos(lon, width);
//        System.out.println(lat + ", " + lon);
//        fill(0, 0, 255);
//        pushMatrix();
//        translate(XPos, ZPos, 50);
//        box(5, 5, 100);
//        popMatrix();
//    }
}
