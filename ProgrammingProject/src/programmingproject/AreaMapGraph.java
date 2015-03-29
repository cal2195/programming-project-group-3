package programmingproject;

import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.utils.ScreenPosition;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author cal
 */
public class AreaMapGraph extends AbstractVisualisation
{

    RenderArea renderArea;
    MapGraphs mapGraphs;

    //Constants
    final int GRID_WIDTH = 300;
    final int GRID_HEIGHT = 300;

    int MODE = 0;

    Tower[][] gridOfTowers;
    float percent = 0f;
    Random random = new Random();

    ArrayList<Trip> trips = new ArrayList<>();
    ArrayList<Trip> queuedTrips = new ArrayList<>();

    public AreaMapGraph(RenderArea renderArea, MapGraphs mapGraphs)
    {
        this.renderArea = renderArea;
        this.mapGraphs = mapGraphs;
        
        super.setCurrentQuery(renderArea.currentQuery);

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
        queuedTrips = data;
        switchData();
    }
    
    public void addData(ArrayList<Trip> data)
    {
        trips.addAll(data);
        resetTowers();
        calculateTowers();
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
        renderArea.noStroke();

        if (percent < 1)
        {
            percent += 0.005;
        }

        renderArea.translate(-mapGraphs.mapWidth / 2, -mapGraphs.mapHeight / 2, 0);

        for (int i = 0; i < GRID_WIDTH; i++)
        {
            for (int ii = 0; ii < GRID_HEIGHT; ii++)
            {
                if (gridOfTowers[i][ii].height != 0)// && gridOfTowers[i][ii].height != 10)
                {
                    renderArea.pushMatrix();
                    renderArea.translate((float) i * (mapGraphs.mapWidth / (float) GRID_WIDTH), (float) ii * (mapGraphs.mapHeight / (float) GRID_HEIGHT), (float) (gridOfTowers[i][ii].height) / 10);
                    renderArea.fill(15f * (float) Math.log((gridOfTowers[i][ii].height)));
                    renderArea.ellipse(0, 0, (float) Math.log10((gridOfTowers[i][ii].height)) * 6 * percent, (float) Math.log10((gridOfTowers[i][ii].height)) * 6 * percent);
                    renderArea.popMatrix();
                }
            }
        }

        renderArea.popMatrix();
        renderArea.popStyle();
    }
    
    public void keyPressed(KeyEvent e)
    {
        super.keyPressed(e);
    }
}
