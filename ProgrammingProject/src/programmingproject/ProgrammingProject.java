package programmingproject;

import java.util.Random;
import javax.swing.JFrame;
import processing.core.PApplet;
import processing.core.PImage;

/**
 *
 * @author cal
 */
public class ProgrammingProject extends PApplet
{

    PImage bg;
    Tower[] towers = new Tower[30];
    Tower[] otherTowers = new Tower[30];
    float percent = 0;
    Random random = new Random();
    
    //n.b. these values are currently all bullshit, to be used to find the relative x and y of a taxi
    double ORIGIN_LONGITUDE = 73.8;
    double ORIGIN_LATITUDE = 40.6;
    double SCALE_FACTOR = 1000;
    
    Data data;

    @Override
    public void setup()
    {
        size(1000, 600, P3D);
        bg = loadImage("res/newyork.png");
        
        data = new Data("res/trip_data_small.csv", this);
        
        for (int i = 0; i < towers.length; i++)
        {
            towers[i] = new Tower(random.nextInt(840) - 480, random.nextInt(540) - 280, random.nextInt(100));
        }
        for (int i = 0; i < otherTowers.length; i++)
        {
            otherTowers[i] = new Tower(random.nextInt(840) - 480, random.nextInt(540) - 280, random.nextInt(100));
        }
    }

    @Override
    public void draw()
    {
        background(0);
        if (percent < 1)
        {
            percent += 0.005;
        }

        translate(bg.width / 2, bg.height / 2, -00);
        rotateX(mouseY / 300f);
        rotateZ(mouseX / 300f);
        image(bg, -bg.width / 2, -bg.height / 2, 1000, 600);
        fill(255, 0, 0);

        for (int i = 0; i < towers.length; i++)
        {
            pushMatrix();
            translate(towers[i].x, towers[i].y, towers[i].z * percent / 2);
            rotateZ(radians(30));
            box(20, 20, towers[i].z * percent);
            popMatrix();
        }

        fill(255, 255, 0);
        for (int i = 0; i < otherTowers.length; i++)
        {
            pushMatrix();
            translate(otherTowers[i].x, otherTowers[i].y, otherTowers[i].z * percent / 2);
            rotateZ(radians(30));
            box(20, 20, otherTowers[i].z * percent);
            popMatrix();
        }
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Programming Project");
        ProgrammingProject programmingProject = new ProgrammingProject();
        frame.setSize(1000, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(programmingProject);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        programmingProject.init();
    }
}
