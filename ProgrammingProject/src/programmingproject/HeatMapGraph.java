package programmingproject;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import processing.core.PImage;

/**
 *
 * @author cal
 */
public class HeatMapGraph
{

    RenderArea renderArea;

    //Constants
    final int GRID_WIDTH = 135;
    final int GRID_HEIGHT = 100;
    final int SCALE = 10;

    Gradient gradient;

    ArrayList<Trip> trips = new ArrayList<>();
    ArrayList<Trip> queuedTrips = new ArrayList<>();

    PImage bg;
    Tower[][] gridOfTowers;
    float percent = 1f;
    Random random = new Random();

    //Camera Rotation
    float cameraX, cameraY;
    MouseEvent lastMousePosition;
    float MOUSE_SENSITIVITY = 300f;
    boolean demoMode = true;
    boolean minimize = false;

    public HeatMapGraph(RenderArea renderArea)
    {
        this.renderArea = renderArea;
        bg = renderArea.loadImage("res/newyork.png");

        gradient = new Gradient(renderArea);
        gradient.addColor(renderArea.color(0, 0, 0));
        gradient.addColor(renderArea.color(102, 0, 102));
        gradient.addColor(renderArea.color(0, 144, 255));
        gradient.addColor(renderArea.color(0, 255, 207));
        gradient.addColor(renderArea.color(51, 204, 102));
        gradient.addColor(renderArea.color(111, 255, 0));
        gradient.addColor(renderArea.color(191, 255, 0));
        gradient.addColor(renderArea.color(255, 240, 0));
        gradient.addColor(renderArea.color(255, 153, 102));
        gradient.addColor(renderArea.color(204, 51, 0));
        gradient.addColor(renderArea.color(153, 0, 0));

        gridOfTowers = new Tower[GRID_WIDTH][GRID_HEIGHT];

        resetTowers();
    }

    public void resetTowers()
    {
        for (int i = 0; i < GRID_WIDTH; i++)
        {
            for (int ii = 0; ii < GRID_HEIGHT; ii++)
            {
                gridOfTowers[i][ii] = new Tower(0);
            }
        }
    }

    public void calculateTowers()
    {
        for (Trip trip : trips)
        {
            float latitude = trip.dropoffLat;
            float longitude = trip.dropoffLong;
            int x = (int) Data.longToXPos(longitude, GRID_WIDTH);
            int y = (int) Data.latToYPos(latitude, GRID_HEIGHT);
            if (x < GRID_WIDTH && x > 0 && y < GRID_HEIGHT && y > 0)
            {
                gridOfTowers[x][y].height += 10;
            }
        }
    }

    public void setData(ArrayList<Trip> data)
    {
        minimize = true;
        queuedTrips = data;
    }

    public void switchData()
    {
        trips = queuedTrips;
        resetTowers();
        calculateTowers();
        System.out.println("TRIP SIZE: " + trips.size());
    }

    public void draw()
    {
        renderArea.pushMatrix();
        renderArea.background(179, 209, 255);//renderArea.background(0);
        if (minimize)
        {
            if (percent > 0f)
            {
                percent -= 0.05f;
            } else
            {
                minimize = false;
                switchData();
            }
        }
        if (!minimize && percent < 1)
        {
            percent += 0.05;
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

        for (int i = 0; i < GRID_WIDTH; i++)
        {
            for (int ii = 0; ii < GRID_HEIGHT; ii++)
            {
                if (gridOfTowers[i][ii].height != 0)
                {
                    renderArea.pushMatrix();
                    renderArea.translate((float) i * (renderArea.width / (float) GRID_WIDTH), (float) ii * (renderArea.height / (float) GRID_HEIGHT), (float) Math.log((gridOfTowers[i][ii].height)) * SCALE * percent / 2);
                    renderArea.fill(gradient.getGradient((float) Math.log10((gridOfTowers[i][ii].height)) * 1.8f));
                    //renderArea.fill(255, (float) Math.log10((gridOfTowers[i][ii].height)) * 40, (float) Math.log10((gridOfTowers[i][ii].height)) * 15);
                    renderArea.box(renderArea.width / GRID_WIDTH, renderArea.height / GRID_HEIGHT, (float) Math.log((double) (gridOfTowers[i][ii].height)) * SCALE * percent);
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

    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_1)
        {
            setData(renderArea.query.getTripsForMonth(1));
        } else if (e.getKeyCode() == KeyEvent.VK_2)
        {
            setData(renderArea.query.getTripsForMonth(2));
        } else if (e.getKeyCode() == KeyEvent.VK_3)
        {
            setData(renderArea.query.getTripsForMonth(3));
        }
    }
}
