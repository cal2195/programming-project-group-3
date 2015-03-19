package programmingproject;

import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.utils.ScreenPosition;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import processing.core.PGraphics;
import processing.opengl.PGraphics3D;

/**
 *
 * @author cal
 */
public class HeatMapGraph
{

    RenderArea renderArea;
    MapGraphs mapGraphs;

    //Constants
    final int GRID_WIDTH = 300;
    final int GRID_HEIGHT = 300;
    final int SCALE = 10;

    Gradient gradient;

    ArrayList<Trip> trips = new ArrayList<>();
    ArrayList<Trip> queuedTrips = new ArrayList<>();

    Tower[][] gridOfTowers;
    float percent = 1f;
    Random random = new Random();
    boolean minimize = false;

    PGraphics buffer;

    public HeatMapGraph(RenderArea renderArea, MapGraphs mapGraphs)
    {
        this.renderArea = renderArea;
        this.mapGraphs = mapGraphs;
        buffer = renderArea.createGraphics(renderArea.width, renderArea.height, RenderArea.P3D);

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
            ScreenPosition pos = mapGraphs.map.getScreenPosition(new Location(latitude, longitude));
            int relX = (int) (pos.x);
            int relY = (int) (pos.y);

            int x = (int) RenderArea.map(relX, -mapGraphs.mapWidth / 2, mapGraphs.mapWidth / 2, 0, GRID_WIDTH);
            int y = (int) RenderArea.map(relY, -mapGraphs.mapHeight / 2, mapGraphs.mapHeight / 2, 0, GRID_HEIGHT);

            if (x < GRID_WIDTH && x > 0 && y < GRID_HEIGHT && y > 0)
            {
                gridOfTowers[x][y].height += 10;
            } else
            {
                System.out.println("GRID ERROR - OUT OF BOUNDS");
                System.out.println("relX = " + relX + " relY = " + relY);
                System.out.println("x = " + x + " y = " + y);
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
        renderArea.pushStyle();
        renderArea.pushMatrix();

        int currentID = drawBuffer();
        //System.out.println(currentID);

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

        renderArea.translate(-mapGraphs.mapWidth / 2, -mapGraphs.mapHeight / 2, 0);

        renderArea.fill(255, 0, 0, 100f);

        int id = 1;
        for (int i = 0; i < GRID_WIDTH; i++)
        {
            for (int ii = 0; ii < GRID_HEIGHT; ii++)
            {
                if (gridOfTowers[i][ii].height != 0)
                {
                    renderArea.pushMatrix();
                    renderArea.translate((float) i * (mapGraphs.mapWidth / (float) GRID_WIDTH), (float) ii * (mapGraphs.mapHeight / (float) GRID_HEIGHT), (float) ((gridOfTowers[i][ii].height)) * SCALE * percent / 2 / 500f);
                    if (currentID == id)
                    {
                        renderArea.fill(255, 0, 0);
                    } else
                    {
                        renderArea.fill(gradient.getGradient((float) Math.log10((gridOfTowers[i][ii].height)) * 1.8f));
                    }
                    renderArea.box(mapGraphs.mapWidth / GRID_WIDTH, mapGraphs.mapHeight / GRID_HEIGHT, (float) ((double) (gridOfTowers[i][ii].height)) * SCALE * percent / 500f);
                    if (currentID == id)
                    {
                        renderArea.translate(0, 0, (float) ((double) (gridOfTowers[i][ii].height)) * SCALE * percent / 500f / 2);
                        renderArea.fill(0);
                        
                        renderArea.rotateZ(-mapGraphs.cameraX);
                        renderArea.rotateX(-mapGraphs.cameraY);
                        renderArea.pushMatrix();
                        renderArea.fill(0,255,0);
                        renderArea.noStroke();
                        renderArea.rect(-5, -15, 60, 18);
                        renderArea.fill(0);
                        renderArea.textFont(renderArea.createFont("Calibri", 30, false));
                        renderArea.textSize(15);
                        
                        renderArea.text((int) (gridOfTowers[i][ii].height / 10) + " taxis", -2, -2);
                        renderArea.popMatrix();
                        renderArea.stroke(0);
                    }
                    renderArea.popMatrix();
                    id++;
                }
            }
        }

        renderArea.popMatrix();
        renderArea.popStyle();
    }

    public int drawBuffer()
    {
        buffer.beginDraw();
        buffer.pushStyle();
        buffer.background(0);
        buffer.translate(renderArea.width / 2, renderArea.height / 2, mapGraphs.zoom);

        buffer.rotateX(mapGraphs.cameraY);
        buffer.rotateZ(mapGraphs.cameraX);

        buffer.translate(mapGraphs.cameraTransX, mapGraphs.cameraTransY, 0);
        buffer.translate(-mapGraphs.mapWidth / 2, -mapGraphs.mapHeight / 2, 0);

        int id = 1;
        buffer.noStroke();
        for (int i = 0; i < GRID_WIDTH; i++)
        {
            for (int ii = 0; ii < GRID_HEIGHT; ii++)
            {
                if (gridOfTowers[i][ii].height != 0)
                {
                    buffer.pushMatrix();
                    buffer.translate((float) i * (mapGraphs.mapWidth / (float) GRID_WIDTH), (float) ii * (mapGraphs.mapHeight / (float) GRID_HEIGHT), (float) ((gridOfTowers[i][ii].height)) * SCALE * percent / 2 / 500f);
                    buffer.fill(id++ - 16777215);
                    buffer.box(mapGraphs.mapWidth / GRID_WIDTH, mapGraphs.mapHeight / GRID_HEIGHT, (float) ((double) (gridOfTowers[i][ii].height)) * SCALE * percent / 500f);
                    buffer.popMatrix();
                }
            }
        }
        buffer.popStyle();
        buffer.endDraw();
        //Used to fix a bug in processing
        renderArea.pushMatrix();
        renderArea.translate(-2500, -2500);
        renderArea.text("processing you confuse me...", 0, 0);
        renderArea.popMatrix();
        return buffer.get(renderArea.mouseX, renderArea.mouseY) + 16777215;
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
        } else if (e.getKeyCode() == KeyEvent.VK_4)
        {
            setData(renderArea.query.getTripsForMonth(4));
        }
    }
}
