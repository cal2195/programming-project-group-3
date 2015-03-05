package programmingproject;

import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.JFrame;
import processing.core.PApplet;
import processing.core.PImage;

/**
 *
 * @author cal
 */
public class RenderArea extends PApplet
{

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

    @Override
    public void setup()
    {
        size(width, height, P3D);
        bg = loadImage("res/newyork.png");
        
        data = new Data("res/trip_data_small.csv", this);
        
        for (int i = 0; i < towers.length; i++)
        {
            towers[i] = new Tower(random.nextInt(width - 20) - (width / 2) - 20, random.nextInt(height - 20) - (height / 2) - 20, random.nextInt(100));
        }
        for (int i = 0; i < otherTowers.length; i++)
        {
            otherTowers[i] = new Tower(random.nextInt(width - 20) - (width / 2) - 20, random.nextInt(height - 20) - (height / 2) - 20, random.nextInt(100));
        }
    }

    @Override
    public void draw()
    {
        background(0);
        if (percent < 1)
        {
            percent += 0.005;
        }

        translate(width / 2, height / 2, -00);
        rotateX(cameraY);
        rotateZ(cameraX);
        image(bg, -width / 2, -height / 2, width, height);
        fill(255, 0, 0);

        for (int i = 0; i < towers.length; i++)
        {
            pushMatrix();
            translate(towers[i].x, towers[i].y, towers[i].z * percent / 2);
            rotateZ(radians(30));
            box(20, 20, towers[i].z * percent);
            popMatrix();
        }

        fill(255, 255, 0);
        for (int i = 0; i < otherTowers.length; i++)
        {
            pushMatrix();
            translate(otherTowers[i].x, otherTowers[i].y, otherTowers[i].z * percent / 2);
            rotateZ(radians(30));
            box(20, 20, otherTowers[i].z * percent);
            popMatrix();
        }
        testCoords();
    }

    @Override
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
    
    @Override
    public void mouseReleased(MouseEvent e)
    {
        lastMousePosition = null;
    }
    
    public void testCoords(){
        double lat = 40.664000;
        double lon = -73.716696; 
        double ZPos = Data.latToYPos(lat , height);
        double XPos = Data.longToXPos(lon , width);
        System.out.println(lat + ", " + lon); 
        fill(0,0,255);
        pushMatrix();
        translate((float)XPos,(float)ZPos,50);
        box(5,5,100);
        popMatrix();        
    }
    
}
