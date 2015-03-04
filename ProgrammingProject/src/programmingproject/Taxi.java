/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programmingproject;
import java.util.ArrayList;
/**
 *
 * @author Dan
 */
public class Taxi {
    private String medallion, hack_license, vendor_id;
    private ArrayList<Trip> trips;
    Taxi(String medallion, String hackLicense, String vendorID){
        this.medallion = medallion;
        this.hack_license = hackLicense;
        this.vendor_id = vendorID;
    }
}
