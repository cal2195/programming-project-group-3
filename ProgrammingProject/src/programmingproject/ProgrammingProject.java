package programmingproject;

import javax.swing.JFrame;
import processing.core.PApplet;


/**
 *
 * @author cal
 */
public class ProgrammingProject extends PApplet
{
    RenderArea renderArea;

    
    public ProgrammingProject()
    {
        createUserInterface();
    }
    
    public void createUserInterface()
    {
        JFrame mainWindow = new JFrame("Programming Project");
        
        renderArea = new RenderArea();
        
        mainWindow.setSize(1000, 600);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.add(renderArea);
        
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setVisible(true);
        
        renderArea.init();
    }
    
    public static void main(String[] args)
    {
        new ProgrammingProject();
    }
}
