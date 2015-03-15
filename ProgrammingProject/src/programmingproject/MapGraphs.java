package programmingproject;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.Google;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author cal
 */
public class MapGraphs
{

    RenderArea renderArea;

    UnfoldingMap map;
    int mapWidth = 2500, mapHeight = 2500;

    //Camera Rotation
    float cameraX, cameraY;
    float cameraTransX, cameraTransY;
    MouseEvent lastMousePosition;
    float MOUSE_SENSITIVITY = 300f;
    boolean demoMode = true;

    //Graphs
    int currentGraph = 0; //0: heatMapGraph; 1: TripAnimator
    HeatMapGraph heatMapGraph;
    TripAnimator tripAnimator;

    public MapGraphs(RenderArea renderArea)
    {
        this.renderArea = renderArea;

        //Wanna try a different map?
        //Replace the last parameter with one of these!! http://unfoldingmaps.org/javadoc/index.html?de/fhpotsdam/unfolding/providers/package-summary.html
        map = new UnfoldingMap(renderArea, -mapWidth / 2, -mapHeight / 2, mapWidth, mapHeight, new Google.GoogleMapProvider());
        map.zoomAndPanTo(12, new Location(40.731416f, -73.990667f));
        map.zoomToLevel(12);
        map.panTo(new Location(40.731416f, -73.990667f));

        heatMapGraph = new HeatMapGraph(renderArea, this);
        tripAnimator = new TripAnimator(renderArea, this);
    }

    public void draw()
    {
        renderArea.pushStyle();
        renderArea.pushMatrix();
        renderArea.background(179, 209, 255);

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
        
        renderArea.translate(cameraTransX, cameraTransY, 0);
        
        map.draw();
        //renderArea.translate(-renderArea.width / 2, -renderArea.height / 2, 0);

        //renderArea.translate(-mapWidth / 2, -mapHeight / 2, 0);
        //Draw whichever visualisation is active
        switch (currentGraph)
        {
            case 0:
                heatMapGraph.draw();
                break;
            case 1:
                tripAnimator.draw();
                break;
        }

        renderArea.popMatrix();
        renderArea.popStyle();
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
        
        System.out.println(Math.cos(cameraX));
        
        if (e.getModifiersEx() == MouseEvent.BUTTON1_DOWN_MASK)
        {
            cameraTransX += Math.cos(cameraX) * (e.getXOnScreen() - lastMousePosition.getXOnScreen()); //THIS
            cameraTransY += Math.cos(cameraX) * (e.getYOnScreen() - lastMousePosition.getYOnScreen()); //TOOK
            cameraTransX += Math.sin(cameraX) * (e.getYOnScreen() - lastMousePosition.getYOnScreen()); //ME
            cameraTransY -= Math.sin(cameraX) * (e.getXOnScreen() - lastMousePosition.getXOnScreen()); //FOREVER!
        } else if (e.getModifiersEx() == MouseEvent.BUTTON3_DOWN_MASK)
        {
            cameraX -= (e.getXOnScreen() - lastMousePosition.getXOnScreen()) / MOUSE_SENSITIVITY;
            cameraY -= (e.getYOnScreen() - lastMousePosition.getYOnScreen()) / MOUSE_SENSITIVITY;
        }
        
        lastMousePosition = e;
    }

    public void mouseReleased(MouseEvent e)
    {
        lastMousePosition = null;
    }

    public void keyPressed(KeyEvent e)
    {
        switch (currentGraph)
        {
            case 0:
                heatMapGraph.keyPressed(e);
                break;
            case 1:
                tripAnimator.keyPressed(e);
                break;
        }
    }
}
