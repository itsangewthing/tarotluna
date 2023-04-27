package com.tarotluna.tarotluna.models;

public class Spreads extends CardList {

    public Spreads(Types types) {
        super(types);
        
    }
    private int sId;
    private String SpreadsName;

    
    public int getsId() {
        return sId;
    }
    public void setsId(int sId) {
        this.sId = sId;
    }
    public String getSpreadsName() {
        return SpreadsName;
    }
    public void setSpreadsName(String spreadsName) {
        SpreadsName = spreadsName;
    }

    
    
}
