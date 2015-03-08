package programmingproject;

import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import processing.core.PImage;

/**
 *
 * @author cal
 */
public class HeightMapGraph
{

    RenderArea renderArea;

    //Constants
    final int GRID_WIDTH = 135;
    final int GRID_HEIGHT = 100;

    PImage bg;
    Tower[][] gridOfTowers;
    float percent = 1f;
    Random random = new Random();

    //Camera Rotation
    float cameraX, cameraY;
    MouseEvent lastMousePosition;
    float MOUSE_SENSITIVITY = 300f;
    boolean demoMode = true;

    public HeightMapGraph(RenderArea renderArea)
    {
        this.renderArea = renderArea;
        bg = renderArea.loadImage("res/newyork.png");

        gridOfTowers = new Tower[GRID_WIDTH][GRID_HEIGHT];

        for (int i = 0; i < GRID_WIDTH; i++)
        {
            for (int ii = 0; ii < GRID_HEIGHT; ii++)
            {
                gridOfTowers[i][ii] = new Tower((float) i * (renderArea.width / (float) GRID_WIDTH), (float) ii * (renderArea.height / (float) GRID_HEIGHT), 0);
            }
        }

    }

    public void draw()
    {
        renderArea.background(0);
        if (percent < 1)
        {
            percent += 0.005;
        }

        if (demoMode)
        {
            if (cameraY < 1)
            {
                cameraY += 0.005f;
            }
            cameraX += 0.001f;
        }

        renderArea.translate(renderArea.width / 2, renderArea.height / 2, 0);
        renderArea.rotateX(cameraY);
        renderArea.rotateZ(cameraX);
        renderArea.image(bg, -renderArea.width / 2, -renderArea.height / 2, renderArea.width, renderArea.height);
        renderArea.fill(255, 0, 0, 100f);

        renderArea.translate(-renderArea.width / 2, -renderArea.height / 2, 0);
//        for (int i = 0; i < GRID_WIDTH; i++)
//        {
//            renderArea.line(i * renderArea.width / GRID_WIDTH, 0, i * renderArea.width / GRID_WIDTH, renderArea.height);
//        }
//        for (int i = 0; i < GRID_HEIGHT; i++)
//        {
//            renderArea.line(0, i * renderArea.height / GRID_HEIGHT, renderArea.width, i * renderArea.height / GRID_HEIGHT);
//        }
        //if (renderArea.data.dataLoaded)
        {
            for (HashMap.Entry key : renderArea.data.taxis.entrySet())
            {
                Taxi taxi = (Taxi) key.getValue();
                for (Trip trip : taxi.getTrips())
                {
                    if (!trip.accountedFor)
                    {
                        trip.accountedFor = true;
                        float latitude = trip.pickupLat;
                        float longitude = trip.pickupLong;
                        int x = (int) Data.longToXPos(longitude, GRID_WIDTH);
                        int y = (int) Data.latToYPos(latitude, GRID_HEIGHT);
                        //System.out.println("x = " + x + " y = " + y);
                        if (x < GRID_WIDTH && x > 0 && y < GRID_HEIGHT && y > 0)
                        {
                            gridOfTowers[x][y].z += 1;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < GRID_WIDTH; i++)
        {
            for (int ii = 0; ii < GRID_HEIGHT; ii++)
            {
                if (gridOfTowers[i][ii].z != 0)
                {
                    renderArea.pushMatrix();
                    renderArea.translate(gridOfTowers[i][ii].x, gridOfTowers[i][ii].y, (float) Math.log10((gridOfTowers[i][ii].z) + 1));
                    renderArea.fill(255, 0, 0, 100f);
                    //renderArea.rotateZ(radians(30));
                    renderArea.box(renderArea.width / GRID_WIDTH, renderArea.height / GRID_HEIGHT, (float) Math.log10((gridOfTowers[i][ii].z)) * 2);
                    renderArea.popMatrix();
                }
            }
        }
//
//        renderArea.fill(255, 255, 0);
//        for (int i = 0; i < otherTowers.length; i++)
//        {
//            renderArea.pushMatrix();
//            renderArea.translate(otherTowers[i].x, otherTowers[i].y, otherTowers[i].z * percent / 2);
//            renderArea.rotateZ(radians(30));
//            renderArea.box(20, 20, otherTowers[i].z * percent);
//            renderArea.popMatrix();
//        }
        //testCoords();
    }

    public void mousePressed(MouseEvent e)
    {
        demoMode = false;
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
