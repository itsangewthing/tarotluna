package com.tarotluna.tarotluna.models;

public enum CourtsRank {
    QUEEN("Queen"), QUEENS("Queens"), KING("King"), KINGS("Kings"), 
    KNIGHT("Knight"),KNIGHTS("Knights"),PAGE("Page"),PAGES("Pages");

    private final String courtsRank;

    CourtsRank(String courtsRank){this.courtsRank = courtsRank; }

    public String getCourtsRank(){return courtsRank;}
}