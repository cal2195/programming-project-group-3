package programmingproject;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.Google;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import processing.core.PVector;
import processing.opengl.PGraphics3D;

/**
 *
 * @author cal
 */
public class MapGraphs
{

    RenderArea renderArea;

    UnfoldingMap map;
    int mapWidth = 3000, mapHeight = 3000;

    //Camera Rotation
    float cameraX, cameraY;
    float cameraTransX, cameraTransY;
    float zoom;
    MouseEvent lastMousePosition;
    float MOUSE_SENSITIVITY = 300f;
    boolean demoMode = true;
    AreaMapGraph areaMapGraph;
    //Graphs
    int currentGraph = 0; //0: heatMapGraph; 1: TripAnimator
    HeatMapGraph heatMapGraph;
    TripAnimator tripAnimator;
    LocationVisualization location;
    VendorVisual vendorVisual;

    public MapGraphs(RenderArea renderArea, PGraphics3D buffer)
    {
        this.renderArea = renderArea;

        //Wanna try a different map?
        //Replace the last parameter with one of these!! http://unfoldingmaps.org/javadoc/index.html?de/fhpotsdam/unfolding/providers/package-summary.html
        map = new UnfoldingMap(renderArea, -mapWidth / 2, -mapHeight / 2, mapWidth, mapHeight, new Google.GoogleMapProvider());
        map.zoomAndPanTo(12, new Location(40.731416f, -73.990667f));
        map.mapDisplay.setInnerTransformationCenter(new PVector(0, 0));

        heatMapGraph = new HeatMapGraph(renderArea, this);
        tripAnimator = new TripAnimator(renderArea, this);
        location = new LocationVisualization(renderArea, this);
        vendorVisual = new VendorVisual(renderArea, this);
        areaMapGraph = new AreaMapGraph(renderArea, this);
    }

    public void draw(PGraphics3D buffer)
    {
        buffer.pushStyle();
        buffer.pushMatrix();
        buffer.background(179, 209, 255);

        if (demoMode)
        {
            if (cameraY < 1)
            {
                cameraY += 0.005f;
            }
            cameraX += 0.001f;
        }

        buffer.translate(buffer.width / 2, buffer.height / 2, zoom);

        buffer.rotateX(cameraY);
        buffer.rotateZ(cameraX);

        buffer.translate(cameraTransX, cameraTransY, 0);

//        this.renderArea.beginRecord(renderArea);
        buffer.pushMatrix();
        buffer.translate(-mapWidth / 2, -mapHeight / 2, 0);
        map.draw();
        buffer.image(map.mapDisplay.getOuterPG(), 0, 0);
        buffer.popMatrix();
//        this.renderArea.endRecord();
        //renderArea.translate(-renderArea.width / 2, -renderArea.height / 2, 0);

        //renderArea.translate(-mapWidth / 2, -mapHeight / 2, 0);
        //Draw whichever visualisation is active
        switch (currentGraph)
        {
            case 0:
                heatMapGraph.draw(buffer);
                break;
            case 1:
                tripAnimator.draw(buffer);
                break;
            case 2:
                location.draw(buffer);
                break;
            case 3:
                vendorVisual.draw(buffer);
                break;
        }

        buffer.popMatrix();
        buffer.popStyle();
        //renderArea.image(heatMapGraph.buffer, 0, 0);
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

        if (e.getModifiersEx() == MouseEvent.BUTTON1_DOWN_MASK)
        {
            //excellent comments ahead, praise the sun!
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

    public void mouseWheelMoved(MouseWheelEvent e)
    {
        zoom -= e.getWheelRotation() * 20;
    }

    public void setCamera(float lat, float lon, int zoomLevel, float cameraX, float cameraY, float cameraTransX, float cameraTransY, float zoom)
    {
        map.zoomAndPanTo(zoomLevel, new Location(lat, lon));
        this.cameraX = cameraX;
        this.cameraY = cameraY;
        this.cameraTransX = cameraTransX;
        this.cameraTransY = cameraTransY;
        this.zoom = zoom;
        switch (currentGraph)
        {
            case 0:
                heatMapGraph.reloadData();
                break;
            case 1:
                tripAnimator.reloadData();
                break;
            case 2:
                location.reloadData();
                break;
            case 3:
                vendorVisual.reloadData();
                break;
        }
    }

    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_EQUALS)
        {
            map.zoomLevelIn();
            switch (currentGraph)
            {
                case 0:
                    heatMapGraph.reloadData();
                    break;
                case 1:
                    tripAnimator.reloadData();
                    break;
                case 2:
                    location.reloadData();
                    break;
                case 3:
                    vendorVisual.reloadData();
                    break;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_MINUS)
        {
            map.zoomLevelOut();
            switch (currentGraph)
            {
                case 0:
                    heatMapGraph.reloadData();
                    break;
                case 1:
                    tripAnimator.reloadData();
                    break;
                case 2:
                    location.reloadData();
                    break;
                case 3:
                    vendorVisual.reloadData();
                    break;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_I)
        {
            map.panUp();
            map.mapDisplay.setInnerTransformationCenter(new PVector(0, 0));
            switch (currentGraph)
            {
                case 0:
                    heatMapGraph.reloadData();
                    break;
                case 1:
                    tripAnimator.reloadData();
                    break;
                case 2:
                    location.reloadData();
                    break;
                case 3:
                    vendorVisual.reloadData();
                    break;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_J)
        {
            map.panLeft();
            map.mapDisplay.setInnerTransformationCenter(new PVector(0, 0));
            switch (currentGraph)
            {
                case 0:
                    heatMapGraph.reloadData();
                    break;
                case 1:
                    tripAnimator.reloadData();
                    break;
                case 2:
                    location.reloadData();
                    break;
                case 3:
                    vendorVisual.reloadData();
                    break;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_K)
        {
            map.panDown();
            map.mapDisplay.setInnerTransformationCenter(new PVector(0, 0));
            switch (currentGraph)
            {
                case 0:
                    heatMapGraph.reloadData();
                    break;
                case 1:
                    tripAnimator.reloadData();
                    break;
                case 2:
                    location.reloadData();
                    break;
                case 3:
                    vendorVisual.reloadData();
                    break;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_L)
        {
            map.panRight();
            map.mapDisplay.setInnerTransformationCenter(new PVector(0, 0));
            switch (currentGraph)
            {
                case 0:
                    heatMapGraph.reloadData();
                    break;
                case 1:
                    tripAnimator.reloadData();
                    break;
                case 2:
                    location.reloadData();
                    break;
                case 3:
                    vendorVisual.reloadData();
                    break;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_QUOTE)
        {
            System.out.println(map.getCenter().getLat() + "f, " + map.getCenter().getLon() + "f, " + map.getZoomLevel() + ", " + cameraX + "f, " + cameraY + "f, " + cameraTransX + "f, " + cameraTransY + "f, " + zoom + "f");
        } else if (e.getKeyCode() == KeyEvent.VK_Q)
        {
            setCamera(40.770947f, -73.87256f, 16, 2.67433f, 1.0016665f, -154.64786f, 393.83865f, 1.0f);
        } else
        {
            switch (currentGraph)
            {
                case 0:
                    heatMapGraph.keyPressed(e);
                    break;
                case 1:
                    tripAnimator.keyPressed(e);
                    break;
                case 2:
                    location.keyPressed(e);
                    break;
                case 3:
                    vendorVisual.keyPressed(e);
                    break;
            }
        }
    }
}
