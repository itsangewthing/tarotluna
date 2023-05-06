package com.tarotluna.tarotluna.models;

public enum Suit {
    WANDS("Wands"), SWORDS("Swords"), CUPS("Cups"), PENTACLES("Pentacles");

    private final String suit;

    Suit(String suit){this.suit = suit; }

    public String getSuit(){return this.suit;}

    public static Suit fromString(String suit){
        for(Suit s : Suit.values()){
            if(s.suit.equalsIgnoreCase(suit)){
                return s;
            }
        }
        return null;
    }
}