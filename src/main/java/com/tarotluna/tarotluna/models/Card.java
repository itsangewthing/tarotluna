package com.tarotluna.tarotluna.models;

import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

public class Card extends CardList {
    private List<String> cardList;
    private List<String> card;
    private String name_short;
    private String name;
    private String value;
    private Integer value_int;
    private String meaning_up;
    private String meaning_reverse;
    private String desc;
    private String createdBy = "";
    private JsonValue types;

    public String getName_short() {
        return this.name_short;
    }

    public void setName_short(String name_short) {
        this.name_short = name_short;
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

    public Integer getValue_int() {
        return this.value_int;
    }

    public void setValue_int(Integer value_int) {
        this.value_int = value_int;
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

    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public JsonValue getTypes() {
        return this.types;
    }

    public void setTypes(JsonValue string) {
        this.types = string;
    }



    @Override
    public String toString() {
        return "{" +
            " name_short='" + getName_short() + "'" +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            ", value_int='" + getValue_int() + "'" +
            ", meaning_up='" + getMeaning_up() + "'" +
            ", meaning_reverse='" + getMeaning_reverse() + "'" +
            ", desc='" + getDesc() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }

    public static Card fromCache(JsonObject j) {
        final Card c = new Card();

        c.setName_short(j.getString("name_short"));
        c.setName(j.getString("name"));
        c.setValue(j.getString("value"));
        c.setValue_int(j.getInt("value_int"));
        c.setMeaning_up(j.getString("meaning_up"));
        c.setMeaning_reverse(j.getString("meaning_reverse"));
        c.setDesc(j.getString("desc"));
        c.setType(j.getString("type"));



        return c;
    }

    private void setType(String string) {
        return;
    }

    
    public JsonObject toJSON() {
        JsonArrayBuilder cardBuilder = Json.createArrayBuilder();
       card.forEach(v->{
            cardBuilder.add(v);
        });
        JsonArrayBuilder cardListBuilder = Json.createArrayBuilder();
        cardList.forEach(v->{
             cardListBuilder.add(v);
         });

        return Json.createObjectBuilder()
                .add("cardListId", getCardListId())
                .add("name_short", getName_short())
                .add("name", getName())
                .add("value", getValue())
                .add("value_int", getValue_int())
                .add("meaning_up", getMeaning_up())
                .add("meaning_reverse", getMeaning_reverse())
                .add("type", (JsonValue) getTypes())
                .build();
                
    }


    public static Card convert(final CardList cardListResult) {
        Card c = new Card();
        c.setCardListId(String.valueOf(((SqlRowSet) cardListResult).getInt("cardList_id")));
        c.setName(((SqlRowSet) cardListResult).getString("name"));
        c.setValue(((SqlRowSet) cardListResult).getString("value"));
        c.setMeaning_up(((SqlRowSet) cardListResult).getString("meaning_up"));
        c.setMeaning_reverse(((SqlRowSet) cardListResult).getString("meaning_reverse"));
        c.setTypes(((Object) cardListResult).getTypes("types"));
        return c;
    }

    public static Card create(JsonObject jsonObject) {
        return null;
    }
    

}
