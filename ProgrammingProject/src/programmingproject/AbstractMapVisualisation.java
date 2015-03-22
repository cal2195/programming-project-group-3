package programmingproject;

import java.awt.event.KeyEvent;
import processing.opengl.PGraphics3D;

/**
 *
 * @author Cal
 */
public class AbstractMapVisualisation
{
    CurrentQuery currentQuery;
    
    public void reloadData()
    {
        System.out.println("ERROR - reloadData() not defined in this class!");
    }
    
    public void clearMemory()
    {
        System.out.println("ERROR - clearMemory() not defined in this class!");
    }
    
    public void draw(PGraphics3D buffer)
    {
        System.out.println("ERROR - draw(PGraphics3D buffer) not defined in this class!");
    }
    
    public void keyPressed(KeyEvent e)
    {
        System.out.println("INFO - keyPressed(KeyEvent e) not defined in this class!");
    }
}
