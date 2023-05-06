package com.tarotluna.tarotluna.models;


import java.util.Optional;

public enum CourtsRank {
    QUEEN("Queen"), QUEENS("Queens"), KING("King"), KINGS("Kings"), 
    KNIGHT("Knight"),KNIGHTS("Knights"),PAGE("Page"),PAGES("Pages");

    private final String courtsRank;

    CourtsRank(String courtsRank){this.courtsRank = courtsRank; }

    public String getCourtsRank(){return this.courtsRank;}
    public String setCourtsRank(){return this.courtsRank;}

    public static Optional<CourtsRank> fromString(String courtsRank){
        try {
            return Optional.of(CourtsRank.valueOf(courtsRank));
        } catch (Exception e) {
            return Optional.empty();
        }

    }
}