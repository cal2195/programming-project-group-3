/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programmingproject;

import processing.opengl.PGraphics3D;

/**
 *
 * @author John Milsom
 */
public class VerticalLine {
    int xPos;
    int yPos;
    int height;
    float buildStage;
    boolean built;
    
    VerticalLine(int xPos, int yPos,int height)
    {
        this.height = height;
        this.xPos = xPos;
        this.yPos = yPos;
        buildStage = 0;
        built = false;
    }
    
    public void draw(PGraphics3D buffer)
    {
        if(!built)
        {
            buildLines(buffer);
        }
        else
        {
            buffer.pushStyle();
            buffer.pushMatrix();
            buffer.fill(0, 120, 255);
            buffer.translate(xPos, yPos, height/2);
            buffer.box(1, 1, height);
            buffer.stroke(0, 120, 255);
            buffer.popMatrix();
            buffer.popStyle();
        }
    }
    
    public void buildLines(PGraphics3D buffer)
    {
        if(buildStage < 100)
        {
            float currentHeight = height*buildStage/100;
            buffer.pushStyle();
            buffer.pushMatrix();
            buffer.fill(0, 120, 255);
            buffer.translate(xPos, yPos, currentHeight/2);
            buffer.box(1, 1, currentHeight);
            buffer.stroke(0, 120, 255);
            buffer.popMatrix();
            buffer.popStyle();
            buildStage += 0.5;
        }
        else
        {
            built = true;
        }
    }
    
}
