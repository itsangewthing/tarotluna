package com.tarotluna.tarotluna.models;

import java.util.LinkedList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

public class Card {
   

    private List<String> card;
    private List<String> CourtsRank = new LinkedList<>();
    private List<String> Suit = new LinkedList<>();

    protected String name;
    private String value;
    private String meaning_up;
    private String meaning_reverse;
    private String desc;
    private String cId;
    private static Types types;
  
    public Types getTypes() {
        return Card.types;
    }

    public void setTypes(Types types2) {
        Card.types = types2;
    }
    private String img;

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }


    public List<String> getCard() {
        return this.card;
    }

    public void setCard(List<String> card) {
        this.card = card;
    }

    public List<String> getCourtsRank() {
        return this.CourtsRank;
    }

    public void setCourtsRank(List<String> CourtsRank) {
        this.CourtsRank = CourtsRank;
    }

    public List<String> getSuit() {
        return this.Suit;
    }

    public void setSuit(List<String> Suit) {
        this.Suit = Suit;
    }

    public String getCId() {
        return this.cId;
    }

    public void setCId(String string) {
        this.cId = string;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMeaning_up() {
        return this.meaning_up;
    }

    public void setMeaning_up(String meaning_up) {
        this.meaning_up = meaning_up;
    }

    public String getMeaning_reverse() {
        return this.meaning_reverse;
    }

    public void setMeaning_reverse(String meaning_reverse) {
        this.meaning_reverse = meaning_reverse;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }





    @Override
    public String toString() {
        return "{" +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            ", meaning_up='" + getMeaning_up() + "'" +
            ", meaning_reverse='" + getMeaning_reverse() + "'" +
            ", desc='" + getDesc() + "'" +
            ", cId='" + getCId() +"'" +
            ", type='" + getTypes() + "'" +
            "}";
    }

    public static Card fromCache(JsonObject j) {
        final Card c = new Card();

        c.setName(j.getString("name"));
        c.setValue(j.getString("value"));
        c.setMeaning_up(j.getString("meaning_up"));
        c.setMeaning_reverse(j.getString("meaning_reverse"));
        c.setDesc(j.getString("desc"));
        c.setCId(j.getString("cId"));
        c.setTypes(j.getString("types"));



        return c;
    }

    public static Card create(JsonObject j) {
        final Card c = new Card();
        c.setName(j.getString("name"));
        c.setValue(j.getString("value"));
        c.setMeaning_up(j.getString("meaning_up"));
        c.setMeaning_reverse(j.getString("meaning_reverse"));
        c.setDesc(j.getString("desc"));
        c.setCId(j.getString("cId"));

        return c;
    }



    
    public JsonObject toJSON() {
        JsonArrayBuilder cardBuilder = Json.createArrayBuilder();
       card.forEach(v->{
            cardBuilder.add(v);
        });
        
        JsonArrayBuilder cardListBuilder = Json.createArrayBuilder();
        card.forEach(v->{
             cardListBuilder.add(v);
         });

        return Json.createObjectBuilder()
                .add("cardId", getCId())
                .add("name", getName())
                .add("value", getValue())
                .add("meaning_up", getMeaning_up())
                .add("meaning_reverse", getMeaning_reverse())
                .add("types", getTypes())
                .build();
                
    }


    public static Card convert(final SqlRowSet cardListResult) {
        Card c = new Card();
        c.setCId(String.valueOf(((SqlRowSet) cardListResult).getInt("cid")));
        c.setName(((SqlRowSet) cardListResult).getString("name"));
        c.setValue(((SqlRowSet) cardListResult).getString("value"));
        c.setMeaning_up(((SqlRowSet) cardListResult).getString("meaning_up"));
        c.setMeaning_reverse(((SqlRowSet) cardListResult).getString("meaning_reverse"));
        c.setTypes(((Card) cardListResult).getTypes());
        return c;
    }


    
}
