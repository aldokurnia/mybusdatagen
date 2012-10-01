/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjsu.mybus.datagen;

import java.text.SimpleDateFormat;
import java.util.Random;

/**
 *
 * @author aldok
 */
public class MyBusDataBuilder {

    String[] latitude = {"37.235966296999997","37.244565059999999","37.257423011","37.273900980000001","37.311256778999997","37.329809220000001","37.336005585000002","37.351380186","37.370363515000001","37.387196977999999","37.408055890999997","37.403299046000001","37.407795215999997","37.401188060000003","37.394082160000004","37.381936947","37.374609102999997","37.362544086","37.403554501999999","37.338358407999998","37.344963720999999","37.236668131999998","37.242731595999999","37.248054576999998"};
    String[] longitude = {"-121.789490994","-121.870709508","-121.860278761","-121.86296378599999","-121.884300454","-121.89053837500001","-121.890306491","-121.901956508","-121.915989884","-121.928714644","-121.943997756","-121.978870637","-121.962924656","-121.938971915","-121.93383731999999","-121.924755492","-121.919317361","-121.910160014","-121.97405776799999","-121.890805169","-121.897115907","-121.78914085300001","-121.803159131","-121.831338555"};
    String[] operationCodeArr = {"out of service", "in service", "mechanical alert", "medical alert","police alert"};
    String[] routeStatusArr = {"in route", "at stop"};
    
    int stopCount = latitude.length;
    int lastStop = 0;
    
    public String getData(int busId){
        
        Random rand = new Random(); 
        String ret;
        
        
        //construct bus data
        int stop = (lastStop+busId+1)%stopCount;
        String location = latitude[stop] + "," + longitude[stop];
        
        String driverId = String.valueOf(busId) + "0001";
        
        int opCode = rand.nextInt(operationCodeArr.length);
        String opCodeStr = operationCodeArr[opCode];
        
        String message = "hello world";
        
        int routeStatus = rand.nextInt(routeStatusArr.length);
        String routeStatusStr = routeStatusArr[routeStatus];
        
        int passengerCount = rand.nextInt(35);
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
        java.util.Date today = new java.util.Date();
        String timeStamp = formatter.format(new java.sql.Timestamp(today.getTime()));
        
        //combine into a JSON string
        ret = "{\"busid\":\"" + busId + "\",\"location\":\"" + location + "\",\"driverid\":\"" + driverId + "\",\"operationcode\":\"" + opCodeStr + "\",\"message\":\"" + message + "\",\"routestatus\":\"" + routeStatusStr + "\",\"passengercount\":\"" + passengerCount + "\",\"timestamp\":\"" + timeStamp + "\"}";
        
        
        lastStop++;
        
        //System.out.println(ret);
        return ret;
        
    }
}
