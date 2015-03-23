package programmingproject;

/**
 *
 * @author Aran Nolan <nolanar@tcd.ie>
 */
public class StatusBarGUI
{
    RenderArea renderArea;
    
    private int sampleSize;
    private long[] janRange, febRange;
    private boolean[] weekdays;
    private int[] hours;
    private int[] passengers;
    private int vendorID;   // 0 = none; 1 = CMT; 2 = VTS; 3 = both;
    
    public StatusBarGUI(RenderArea renderArea, int sampleSize, long janLower, long janUpper, long febLower, long febUpper, boolean[] weekdays, int hourLower, int hourUpper, int passengersLower, int passengersUpper, int vendorID)
    {
        this.renderArea = renderArea;
        this.sampleSize = sampleSize;
        this.janRange = new long[] {janLower, janUpper};
        this.febRange = new long[] {febLower, febUpper};
        this.weekdays = weekdays;
        this.hours = new int[] {hourLower, hourUpper};
        this.passengers = new int[] {passengersLower, passengersUpper};
        this.vendorID = vendorID;
    }
    
    public void draw()
    {
        
    }
}
