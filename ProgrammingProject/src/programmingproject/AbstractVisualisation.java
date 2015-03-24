package programmingproject;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import processing.opengl.PGraphics3D;

/**
 *
 * @author Cal
 */
public class AbstractVisualisation
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
    
    public void mousePressed(MouseEvent e)
    {
        System.out.println("INFO - mousePressed(MouseEvent e) not defined in this class!");
    }
    
    public void mouseDragged(MouseEvent e)
    {
        System.out.println("INFO - mouseDragged(MouseEvent e) not defined in this class!");
    }
    
    public void mouseReleased(MouseEvent e)
    {
        System.out.println("INFO - mousePressed(MouseEvent e) not defined in this class!");
    }
    
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        System.out.println("INFO - mouseWheelMoved(MouseWheelEvent e) not defined in this class!");
    }
}
