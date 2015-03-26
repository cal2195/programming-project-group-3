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

                        //info from http://www.timeanddate.com/sun/usa/new-york?month=1&year=2013
    //1st - 10th Jan
    int[] dawnOffsets =
    {
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        //11th - 20th Jan
        1, 1, 1, 2, 2, 3, 3, 4, 4, 5,
        //21st - 31st Jan
        5, 6, 7, 7, 8, 9, 10, 11, 11, 12, 13,
        //1st - 10th Feb
        14, 15, 16, 17, 18, 19, 21, 22, 23, 24,
        //11th - 20th Feb
        25, 27, 28, 29, 30, 32, 33, 34, 36, 37,
        //21st - 28th Feb +1 dummy value
        39, 40, 41, 43, 44, 46, 47, 58
    };

    //1st - 10th Jan
    int[] sunsetOffsets =
    {
        0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8,
        //11th - 20th Jan
        9, 10, 11, 13, 14, 15, 16, 17, 18, 19,
        //21st - 31st Jan
        21, 22, 23, 24, 25, 27, 28, 29, 30, 32, 33,
        //1st - 10th Feb
        34, 35, 36, 38, 39, 40, 41, 43, 44, 45,
        //11th - 20th Feb
        46, 48, 49, 50, 51, 52, 55, 56, 57,
        //21st - 28th Feb +1 dummy value
        58, 59, 61, 62, 63, 64, 65, 66, 67
    };

    //have tried to keep this fairly accurate but still works best with speed factors < 100, 
    public static short speedFactor = 1;
    RenderArea renderArea;
    MapGraphs mapGraphs;
    
    int taxisOnScreen = 0;

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

        gradient.addColor(renderArea.color(5, 42, 87));//sunrise1
        gradient.addColor(renderArea.color(43, 65, 115));//sunrise2
        gradient.addColor(renderArea.color(181, 181, 191));//sunrise3
        gradient.addColor(renderArea.color(206, 195, 191));//sunrise4
        gradient.addColor(renderArea.color(252, 206, 173));//sunrise5
        gradient.addColor(renderArea.color(253, 173, 88));//max sunrise
        gradient.addColor(renderArea.color(252, 206, 173));//sunrise5
        gradient.addColor(renderArea.color(206, 195, 230));//sunrise4 modified
        gradient.addColor(renderArea.color(179, 209, 255));//standard sky
        gradient.addColor(renderArea.color(179, 209, 255));//standard sky

        gradient.addColor(renderArea.color(179, 209, 255));//standard sky

        this.renderArea = renderArea;
        this.mapGraphs = mapGraphs;
    }

    @Override
    public void draw(PGraphics3D buffer)
    {

        buffer.pushStyle();
        buffer.pushMatrix();
        
        int taxis = 0;

        int dayIndex = (int) animatorSecondsPassed / DateTime.SECONDS_PER_DAY;

        //jan 01 sunrise at 7.20 so start at 6.50
        double dawnStart = ((6 + 0.833333333) * DateTime.SECONDS_PER_HOUR) - dawnOffsets[dayIndex] * 60;
        double transitionLength = 1 * DateTime.SECONDS_PER_HOUR;
        double dawnEnd = dawnStart + transitionLength;
        double eachTransitionSegmentLength = transitionLength / gradient.colors.size();

        //jan 01 sunset at 16.40 so start at 16.10
        double sunsetStart = ((16.0 + 0.16666666666) * DateTime.SECONDS_PER_HOUR) + sunsetOffsets[dayIndex] * 60;
        double sunsetEnd = sunsetStart + transitionLength;

        double currentTime = (animatorSecondsPassed % DateTime.SECONDS_PER_DAY);

        //print statements for dawn and sunset time current day
        /*
         System.out.println("Dawn " + DateTime.secsToHourAndMinute((long)dawnStart + 1800));
         System.out.println("Sunset " + DateTime.secsToHourAndMinute((long)sunsetStart + 1800));
         */
        if (currentTime < dawnStart || currentTime > sunsetEnd)
        {
            this.mapGraphs.setBackground(gradient.getGradient(0));
            mapGraphs.setAmbientLight(gradient.getGradient(0));
        } else if (currentTime > dawnStart && currentTime < dawnEnd)
        {
            this.mapGraphs.setBackground(gradient.getGradient((float) (currentTime - dawnStart) / (float) eachTransitionSegmentLength));
            mapGraphs.setAmbientLight(gradient.getGradient((float) (currentTime - dawnStart) / (float) eachTransitionSegmentLength));
            //   System.out.println((currentTime - dawnStart)/360);
        } else if (currentTime > dawnEnd && currentTime < sunsetStart)
        {
            this.mapGraphs.setBackground(gradient.getGradient(gradient.colors.size()));
            mapGraphs.setAmbientLight(gradient.getGradient(gradient.colors.size()));
        } else if (currentTime > sunsetStart && currentTime < sunsetEnd)
        {
            this.mapGraphs.setBackground(gradient.getGradient((float) (gradient.colors.size() - (currentTime - sunsetStart) / (float) eachTransitionSegmentLength)));
            mapGraphs.setAmbientLight(gradient.getGradient((float) (gradient.colors.size() - (currentTime - sunsetStart) / (float) eachTransitionSegmentLength)));
            // System.out.println((currentTime - dawnStart)/360);
        }

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
            if (car.draw(buffer, (int) animatorSecondsPassed) && isOnScreen(car.x, car.y, 4, buffer))
                taxis++;
            car.moveAndCheck((int) animatorSecondsPassed);
            buffer.popMatrix();
        }
        animatorSecondsPassed += speedFactor * delta;

        buffer.fill(20);
        //buffer.textFont(this.renderArea.createFont("Calibri", 50, false));
        buffer.textSize(50);
        if (MODE == 0)
        {
            buffer.text(DateTime.secsToHourAndMinute((int) animatorSecondsPassed), -320f, 50f, 3f);
        } else if (MODE == 1)
        {
            String[] dateAndTime = DateTime.secsToDateTime((int) animatorSecondsPassed).split(" ");
            buffer.text(dateAndTime[0], -350f, -10f, 3f);
            buffer.text(dateAndTime[1], -320f, 50, 3f);
        }
        buffer.textSize(25);
        buffer.text(speedFactor + "x realtime", -300f, 100f, 3f);
        
        taxisOnScreen = taxis;
        
        System.out.println("Taxis On Screen: " + taxisOnScreen);
        
        buffer.popMatrix();
        buffer.popStyle();
        if (animatorSecondsPassed >= DateTime.SECONDS_PER_DAY && MODE == 0)
        {
            animatorSecondsPassed = 0;
        } else if (animatorSecondsPassed >= DateTime.SECONDS_TILL_MONTH_STARTS[2] && MODE == 1)
        {
            animatorSecondsPassed = 0;
        }
    }

    public void switchData(ArrayList<Trip> data)
    {
        trips = data;
        System.out.println("TRIP SIZE: " + trips.size());
    }

    public boolean isOnScreen(float x, float y, float z, PGraphics3D buffer)
    {
        float screenX = buffer.screenX(x, y, z);
        float screenY = buffer.screenY(x, y, z);

        return (screenX >= 0 && screenX <= buffer.width && screenY >= 0 && screenY <= buffer.height);
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
            setData(renderArea.query.getTripsForMonth(1, 100000));
        } else if (e.getKeyCode() == KeyEvent.VK_2)
        {
            setData(renderArea.query.getTripsForMonth(1, 200000));
        } else if (e.getKeyCode() == 77) //if m is pressed
        {
            MODE++;
            if (MODE > MAX_MODE)
            {
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
            if (e.isShiftDown())
            {
                animatorSecondsPassed += DateTime.SECONDS_PER_DAY;
            } else
            {
                animatorSecondsPassed += DateTime.SECONDS_PER_HOUR;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            if (animatorSecondsPassed > DateTime.SECONDS_PER_HOUR)
            {
                if (e.isShiftDown() && animatorSecondsPassed > DateTime.SECONDS_PER_DAY)
                {
                    animatorSecondsPassed -= DateTime.SECONDS_PER_DAY;
                } else
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
            } else if (speedFactor < MAX_SPEEDFACTOR)
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
            } else if (speedFactor > MIN_SPEEDFACTOR)
            {
                speedFactor -= SPEEDSTEP;
            }
        }
    }
}
