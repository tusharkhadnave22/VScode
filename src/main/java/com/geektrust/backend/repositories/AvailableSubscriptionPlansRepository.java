package com.geektrust.backend.repositories;

import java.util.HashMap;
import java.util.Map;

public class AvailableSubscriptionPlansRepository {
    private Map<String,Integer> musicPrice=new HashMap<>();
    private Map<String,Integer> videoPrice=new HashMap<>();
    private Map<String,Integer> podCastPrice=new HashMap<>();
    private Map<String,Integer> validity_month=new HashMap<>();
    private Map<String,Integer> topupPricePerMonth=new HashMap<>();

    public AvailableSubscriptionPlansRepository(){
        musicPrice.put("FREE",0);
        musicPrice.put("PERSONAL",100);
        musicPrice.put("PREMIUM",250);

        videoPrice.put("FREE",0);
        videoPrice.put("PERSONAL",200);
        videoPrice.put("PREMIUM",500);

        podCastPrice.put("FREE",0);
        podCastPrice.put("PERSONAL",100);
        podCastPrice.put("PREMIUM",300);

        validity_month.put("FREE",1);
        validity_month.put("PERSONAL",1);
        validity_month.put("PREMIUM",3);

        topupPricePerMonth.put("FOUR_DEVICE",50);
        topupPricePerMonth.put("TEN_DEVICE",100);
    }
        

   

    public Map<String, Integer> getMusicPriceMap() {
        return musicPrice;
    }
    public void setMusicPrice(String type,int price) {
        musicPrice.put(type,price);
    }
    public Map<String, Integer> getVideoPriceMap() {
        return videoPrice;
    }
    public void setVideoPrice(String type,int price) {
        videoPrice.put(type,price);
        
    }
    public Map<String, Integer> getPodCastPriceMap() {
        return podCastPrice;
    }
    public void setPodCastPrice(String type,int price) {
        podCastPrice.put(type,price);
        
    }

    
    public Map<String, Integer> getValidity_month() {
        return validity_month;
    }
    public void setValidity_month(String type,int price) {
        validity_month.put(type,price);
        
    }
    public Map<String, Integer> getTopupPricePerMonth() {
        return topupPricePerMonth;
    }
    public void setTopupPricePerMonth(String type,int price) {
       
        topupPricePerMonth.put(type,price);
    }
  

   

}
