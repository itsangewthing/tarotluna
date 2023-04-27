package com.tarotluna.tarotluna.models;

public enum Suit {
    WANDS("Wands"), SWORDS("Swords"), CUPS("Cups"), PENTACLES("Pentacles");

    private final String suit;

    Suit(String suit){this.suit = suit; }

    public String getSuit(){return suit;}
}