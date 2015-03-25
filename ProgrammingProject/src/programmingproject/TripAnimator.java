package programmingproject;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import processing.opengl.PGraphics3D;

/**
 *
 * @author Dan
 */
public class TripAnimator extends AbstractVisualisation
{

    public static final short MAX_SPEEDFACTOR = 1000;
    public static final short MIN_SPEEDFACTOR = 0;
    public static final short SPEEDSTEP = 1;

    //have tried to keep this fairly accurate but still works best with speed factors < 100, 
    public static short speedFactor = 1;
    RenderArea renderArea;
    MapGraphs mapGraphs;

    long lastTime = 0;
    static double delta;
    
    
    Gradient gradient;

    //mode 0 will show all trips in parallel through the day
    //mode 1 shows all the trips when and where they happened
    //add more modes if you want, just update max modes and it'll iterate through on pressing "m"
    public static int MODE = 1;
    private static final int MAX_MODE = 1;



    ArrayList<Trip> trips = new ArrayList<>();
    ArrayList<Trip> queuedTrips = new ArrayList<>();
    ArrayList<TaxiDrawable> cars = new ArrayList<>();
    

    double animatorSecondsPassed = 0;

    public TripAnimator(RenderArea renderArea, MapGraphs mapGraphs)
    {
        
        gradient = new Gradient(renderArea);
        
        gradient.addColor(renderArea.color(0, 17, 60));//dark blue for night
        gradient.addColor(renderArea.color(5, 42, 87));//darkish blue for night
        gradient.addColor(renderArea.color(43, 65, 115));//dawning blue for night
        gradient.addColor(renderArea.color(71, 93, 142));//dawning blue for start sunrise2
        gradient.addColor(renderArea.color(136, 150, 179));//dawning blue for start sunrise3
        gradient.addColor(renderArea.color(181, 181, 191));//dawning blue for start sunrise3
        gradient.addColor(renderArea.color(206, 195, 191));//dawning blue for start sunrise3
        gradient.addColor(renderArea.color(226, 203, 187));//dawning blue for start sunrise3
        gradient.addColor(renderArea.color(252, 206, 173));//dawning blue for start sunrise3
        gradient.addColor(renderArea.color(253, 173, 88));//dawning blue for start sunrise3

        
        this.renderArea = renderArea;
        this.mapGraphs = mapGraphs;
    }

    @Override
    public void draw(PGraphics3D buffer)
    {
        
        buffer.pushStyle();
        buffer.pushMatrix();
        /* not working right now!
        buffer.background(gradient.getGradient((float) animatorSecondsPassed/1000));
        */
        delta = 1;
        if (lastTime == 0)
        {
            lastTime = System.currentTimeMillis();
        } else
        {
            delta = (System.currentTimeMillis() - lastTime) / 1000f;
            lastTime = System.currentTimeMillis();
            //   System.out.println(delta);
        }

        buffer.stroke(0);
//        buffer.translate(mapGraphs.mapWidth / 2, mapGraphs.mapHeight / 2, 0);
        for (TaxiDrawable car : cars)
        {
            buffer.pushMatrix();
            car.draw(buffer, (int) animatorSecondsPassed);
            car.moveAndCheck((int) animatorSecondsPassed);
            buffer.popMatrix();
        }
        animatorSecondsPassed += speedFactor * delta;

        buffer.fill(20);
        //buffer.textFont(this.renderArea.createFont("Calibri", 50, false));
        buffer.textSize(50);
        if(MODE == 0)
        {
            buffer.text(DateTime.secsToHourAndMinute((int) animatorSecondsPassed), -320f, 50f, 3f);
        }
        else if(MODE == 1)
        {
            String[] dateAndTime = DateTime.secsToDateTime((int) animatorSecondsPassed).split(" ");
            buffer.text(dateAndTime[0], -350f, -10f, 3f);
            buffer.text(dateAndTime[1], -320f, 50, 3f); 
        }
        buffer.textSize(25);
        buffer.text(speedFactor + "x realtime", -300f, 100f, 3f);
        buffer.popMatrix();
        buffer.popStyle();
        if (animatorSecondsPassed >= DateTime.SECONDS_PER_DAY && MODE == 0)
        {
            animatorSecondsPassed = 0;
        }
        else if (animatorSecondsPassed >= DateTime.SECONDS_TILL_MONTH_STARTS[2] && MODE == 1)
        {
            animatorSecondsPassed = 0;
        }
    }

    public void switchData(ArrayList<Trip> data)
    {
        trips = data;
        System.out.println("TRIP SIZE: " + trips.size());
    }

    public void setData(ArrayList<Trip> data)
    {
        queuedTrips = data;
        reset();
        switchData();
    }
    
    @Override
    public void reloadData()
    {
        cars.clear();
        switchData();
    }

    public void reset()
    {
        cars.clear();
        animatorSecondsPassed = 0;
    }

    public void switchData()
    {
        trips = queuedTrips;
        for (Trip trip : trips)
        {
            TaxiDrawable tempCar = new TaxiDrawable(trip, mapGraphs.map);
            cars.add(tempCar);
        }
        System.out.println("TRIP SIZE: " + trips.size());
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_1)
        {
            setData(renderArea.query.getTripsForMonth(1, 500000));
        } else if (e.getKeyCode() == KeyEvent.VK_2)
        {
            setData(renderArea.query.getTripsForMonth(1, 1000000));
        }else if (e.getKeyCode() == 77) //if m is pressed
        {
            MODE++;
            if(MODE > MAX_MODE){
                MODE = 0;
            }
            
            //recomputes cars to allow hotswapping between modes
            cars.clear();
            for (Trip trip : trips)
            {
                TaxiDrawable tempCar = new TaxiDrawable(trip, mapGraphs.map);
                cars.add(tempCar);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_3)
        {
            setData(renderArea.query.GIVEME500LATENIGHTTAXISPLEASE(true));
        } else if (e.getKeyCode() == KeyEvent.VK_UP)
        {
            if(e.isShiftDown())
                {
                    animatorSecondsPassed += DateTime.SECONDS_PER_DAY;
                }
                else
                {
                    animatorSecondsPassed += DateTime.SECONDS_PER_HOUR;
                }
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            if (animatorSecondsPassed > DateTime.SECONDS_PER_HOUR)
            {
                if(e.isShiftDown() && animatorSecondsPassed > DateTime.SECONDS_PER_DAY){
                    animatorSecondsPassed -= DateTime.SECONDS_PER_DAY;
                }
                else
                {
                    animatorSecondsPassed -= DateTime.SECONDS_PER_HOUR;
                }
                for (TaxiDrawable car : cars)
                {
                    car.resetTaxi();
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            if (e.isShiftDown())
            {
                if (speedFactor < MAX_SPEEDFACTOR)
                {
                    speedFactor += SPEEDSTEP * 10;
                }
            }
            else if (speedFactor < MAX_SPEEDFACTOR)
            {
                speedFactor += SPEEDSTEP;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            if (e.isShiftDown())
            {
                if (speedFactor > MIN_SPEEDFACTOR + SPEEDSTEP * 10)
                {
                    speedFactor -= SPEEDSTEP * 10;
                }
            }
            else if (speedFactor > MIN_SPEEDFACTOR)
            {
                speedFactor -= SPEEDSTEP;
            }
        }
    }
}
