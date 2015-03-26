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
 *
 * Adapting HeatMapGrpah.java 2015-03-18
 */
public class ComparisionVisual extends AbstractVisualisation
{

    RenderArea renderArea;
    MapGraphs mapGraphs;

    //Constants
    final int GRID_WIDTH = 300;
    final int GRID_HEIGHT = 300;
    final int SCALE = 1000;

    // 0 = telescope,   1 = side by side
    int visualStyle = 1;

    ArrayList<Trip> trips1 = new ArrayList<>();
    ArrayList<Trip> trips2 = new ArrayList<>();
    ArrayList<Trip> queuedTrips1 = new ArrayList<>();
    ArrayList<Trip> queuedTrips2 = new ArrayList<>();
    Tower[][] gridOfTowers1;
    Tower[][] gridOfTowers2;

    float percent = 1f;
    Random random = new Random();
    boolean minimize = false;

    PGraphics buffer;

    public ComparisionVisual(RenderArea renderArea, MapGraphs mapGraphs)
    {
        this.renderArea = renderArea;
        this.mapGraphs = mapGraphs;
        buffer = renderArea.createGraphics(renderArea.width, renderArea.height, RenderArea.P3D);

        gridOfTowers1 = new Tower[GRID_WIDTH][GRID_HEIGHT];
        gridOfTowers2 = new Tower[GRID_WIDTH][GRID_HEIGHT];

        resetTowers(gridOfTowers1);
        resetTowers(gridOfTowers2);
    }

    public void resetTowers(Tower[][] gridOfTowers)
    {
        for (int i = 0; i < GRID_WIDTH; i++)
        {
            for (int ii = 0; ii < GRID_HEIGHT; ii++)
            {
                gridOfTowers[i][ii] = new Tower(0);
            }
        }
    }

    public void calculateTowers(ArrayList<Trip> trips, Tower[][] gridOfTowers)
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
                gridOfTowers[x][y].height++;
            } else
            {
                System.out.println("GRID ERROR - OUT OF BOUNDS");
                System.out.println("relX = " + relX + " relY = " + relY);
                System.out.println("x = " + x + " y = " + y);
            }
        }
    }

    public void setData(ArrayList<Trip> data1, ArrayList<Trip> data2)
    {
        minimize = true;
        queuedTrips1 = data1;
        queuedTrips2 = data2;
    }

    public void switchData()
    {
        trips1 = queuedTrips1;
        trips2 = queuedTrips2;
        resetTowers(gridOfTowers1);
        resetTowers(gridOfTowers2);
        calculateTowers(trips1, gridOfTowers1);
        calculateTowers(trips2, gridOfTowers2);
        System.out.println("TRIP SIZE: " + trips1.size());
    }
    
    @Override
    public void reloadData()
    {
        setData(renderArea.currentQuery.queryOne, renderArea.currentQuery.queryTwo);
        resetTowers(gridOfTowers1);
        resetTowers(gridOfTowers2);
        calculateTowers(trips1, gridOfTowers1);
        calculateTowers(trips2, gridOfTowers2);
    }

    @Override
    public void draw(PGraphics3D buffer)
    {
        buffer.pushStyle();
        buffer.pushMatrix();

//        int currentID = drawBuffer();
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

        buffer.translate(-mapGraphs.mapWidth / 2, -mapGraphs.mapHeight / 2, 0);

        buffer.fill(255, 0, 0, 100f);

        int id = 1;
        for (int i = 0; i < GRID_WIDTH; i++)
        {
            for (int ii = 0; ii < GRID_HEIGHT; ii++)
            {
                switch (visualStyle)
                {
                    case 0:
                        if (gridOfTowers1[i][ii].height == gridOfTowers2[i][ii].height)
                        {
                            drawTower(buffer, gridOfTowers1[i][ii], i, ii, 255, 63, 255, 0.75f, 0.75f, 0);
                        } else
                        {
                            if (gridOfTowers1[i][ii].height > gridOfTowers2[i][ii].height)
                            {
                                drawTower(buffer, gridOfTowers1[i][ii], i, ii, 63, 63, 255, 0.5f, 0.5f, 0f);
                                drawTower(buffer, gridOfTowers2[i][ii], i, ii, 255, 63, 63, 1f, 1f, 0f);
                            } else
                            {
                                drawTower(buffer, gridOfTowers1[i][ii], i, ii, 63, 63, 255, 1f, 1f, 0f);
                                drawTower(buffer, gridOfTowers2[i][ii], i, ii, 255, 63, 63, 0.5f, 0.5f, 0f);
                            }
                        }
                        break;
                    case 1:
                        drawTower(buffer, gridOfTowers1[i][ii], i, ii, 63, 63, 255, 0.4f, 0.8f, -0.2f);
                        drawTower(buffer, gridOfTowers2[i][ii], i, ii, 255, 63, 63, 0.4f, 0.8f, 0.2f);
                        break;
                }
            }
        }

        buffer.popMatrix();
        buffer.popStyle();
    }

    public void drawTower(PGraphics3D buffer, Tower tower, int x, int y, int red, int green, int blue, float baseX, float baseY, float baseOffset)
    {
        if (tower.height == 0)
        {
            return;
        }
        buffer.pushMatrix();
        buffer.translate((float) (x + baseOffset) * (mapGraphs.mapWidth / (float) GRID_WIDTH), (float) y * (mapGraphs.mapHeight / (float) GRID_HEIGHT), (float) ((tower.height)) * SCALE * percent / 2 / 500f);
        /*        if (currentID == id)
         {
         renderArea.fill(255);   // color of mouse over tower
         }*/
        // towers:
        buffer.fill(red, green, blue);
        buffer.box(mapGraphs.mapWidth / GRID_WIDTH * baseX, mapGraphs.mapHeight / GRID_HEIGHT * baseY, (float) ((double) (tower.height)) * SCALE * percent / 500f);
        /*        if (currentID == id)
         {
         renderArea.translate(0, 0, (float) ((double) (tower.height)) * SCALE * percent / 500f / 2);
         renderArea.fill(0);
         // mouse over info text:
         renderArea.rotateZ(-mapGraphs.cameraX);
         renderArea.rotateX(-mapGraphs.cameraY);
         renderArea.pushMatrix();
         renderArea.fill(0,255,0);
         renderArea.noStroke();
         renderArea.rect(-5, -15, 60, 18);
         renderArea.fill(0);
         renderArea.textFont(renderArea.createFont("Calibri", 15, false));
         renderArea.textSize(15);

         renderArea.text((int) (tower.height / 10) + " taxis", -2, -2);
         renderArea.popMatrix();
         renderArea.stroke(0);
         }
         id++; */
        buffer.popMatrix();
    }

    /*    public int drawBuffer() //to detect mouse over
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
     if (gridOfTowers[i][ii].height1 != 0)
     {
     buffer.pushMatrix();
     buffer.translate((float) i * (mapGraphs.mapWidth / (float) GRID_WIDTH), (float) ii * (mapGraphs.mapHeight / (float) GRID_HEIGHT), (float) ((gridOfTowers[i][ii].height1)) * SCALE * percent / 2 / 500f);
     buffer.fill(id++ - 16777215);
     buffer.box(mapGraphs.mapWidth / GRID_WIDTH, mapGraphs.mapHeight / GRID_HEIGHT, (float) ((double) (gridOfTowers[i][ii].height1)) * SCALE * percent / 500f);
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
     }*/
    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_1)
        {
            visualStyle = 0;
//            setData(renderArea.query.getTripsForMonth(1), renderArea.query.getTripsForMonth(2));
        } else if (e.getKeyCode() == KeyEvent.VK_2)
        {
            visualStyle = 1;
//            setData(renderArea.query.getTripsForMonth(2), renderArea.query.getTripsForMonth(3));
        }/* else if (e.getKeyCode() == KeyEvent.VK_3)
         {
         setData(renderArea.query.getTripsForMonth(3));
         } else if (e.getKeyCode() == KeyEvent.VK_4)
         {
         setData(renderArea.query.getTripsForMonth(4));
         }*/

    }
}
