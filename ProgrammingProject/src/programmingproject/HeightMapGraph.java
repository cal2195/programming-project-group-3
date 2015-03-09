package programmingproject;

import java.awt.event.MouseEvent;
import java.util.HashMap;
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
    float percent = 0f;
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
                gridOfTowers[i][ii] = new Tower(0);
            }
        }

    }

    public void draw()
    {
        renderArea.pushMatrix();
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

        renderArea.translate(renderArea.width / 2, renderArea.height / 2, 1);
        renderArea.rotateX(cameraY);
        renderArea.rotateZ(cameraX);
        renderArea.image(bg, -renderArea.width / 2, -renderArea.height / 2, renderArea.width, renderArea.height);
        renderArea.fill(255, 0, 0, 100f);

        renderArea.translate(-renderArea.width / 2, -renderArea.height / 2, 0);
        
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
                        if (x < GRID_WIDTH && x > 0 && y < GRID_HEIGHT && y > 0)
                        {
                            gridOfTowers[x][y].height += 10;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < GRID_WIDTH; i++)
        {
            for (int ii = 0; ii < GRID_HEIGHT; ii++)
            {
                if (gridOfTowers[i][ii].height != 0)
                {
                    renderArea.pushMatrix();
                    renderArea.translate((float) i * (renderArea.width / (float) GRID_WIDTH), (float) ii * (renderArea.height / (float) GRID_HEIGHT), (float) Math.log10((gridOfTowers[i][ii].height)) * 6 * percent);
                    renderArea.box(renderArea.width / GRID_WIDTH, renderArea.height / GRID_HEIGHT, (float) Math.log10((gridOfTowers[i][ii].height)) * 6 * percent);
                    renderArea.popMatrix();
                }
            }
        }

        renderArea.popMatrix();
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
}
