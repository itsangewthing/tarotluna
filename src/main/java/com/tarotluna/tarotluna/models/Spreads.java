package com.tarotluna.tarotluna.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Spreads extends CardList {


    private int spId;
    private String SpreadName;

    
    public int getspId() {
        return spId;
    }
    public void setspId(int spId) {
        this.spId = spId;
    }
    public String getSpreadName() {
        return SpreadName;
    }
    public void setSpreadName(String spreadName) {
        SpreadName = spreadName;
    }

    public static Spreads create(SqlRowSet rs){
        Spreads s = new Spreads(); 
        s.setspId(((SqlRowSet) s).getInt("id"));
        s.setSpreadName(rs.getString("SpreadsName"));

        return s;
    }

    public JsonObject toJson(){
        return Json.createObjectBuilder()
                    .add("spId", getspId())
                    .add("SpreadName", getSpreadName())
                    .build();
    }

    
    
}
