package com.tarotluna.tarotluna.models;

import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class CardList{

    private Integer nhits;
    private String cardListId;
    private SqlRowSet cardList;
    private String name_short;
    private String name;
    private String value;
    private Integer value_int;
    private String meaning_up;
    private String meaning_reverse;
    private String desc;
    protected Object cards;
    private JsonValue type;

            public Integer getNhits() {
                return this.nhits;
            }

            public void setNhits(Integer nhits) {
                this.nhits = nhits;
            }

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

            public Object getCards() {
                return this.cards;
            }

            public void setCards(Object cards) {
                this.cards = cards;
            }

            public JsonValue getType() {
                return this.type;
            }

            public void setType(JsonValue type) {
                this.type = type;
            }

            public String getCardListId() {
                return cardListId;
            }

            public void setCardListId(String cardListId) {
                this.cardListId = cardListId;
            }

            public SqlRowSet getCardList() {
                return cardList;
            }

            public void setCardList(CardList cl) {
                this.cardList = (SqlRowSet) cl;
            }

//  ---------------------           
        
    
            public static Card convert(JsonObject jsonObject) {
                CardList cardList = new CardList();
                cardList.setCardListId(jsonObject.getString("cardlistId", ""));
                cardList.setName(jsonObject.getString("strName", ""));
              
                return (Card) cardList;
            }
        
            public static CardList convert(final SqlRowSet rs) {
                CardList cardList = new CardList();
                cardList.setCardListId(String.valueOf(rs.getInt("cardlist_id")));
                cardList.setName(rs.getString("name"));

                return cardList;
            }
        
            public JsonObject toJson() {
                return Json.createObjectBuilder()
                .add("cardlistId", cardListId)
                .add("name", name)
                .build();
            }

			public static void add(CardList cl) {

                return;
			}

            public static List<CardList> getCardListsByName(String name2) {
                return null;
            }

			public void setEmail(String email) {
			}

			public boolean next() {
				return false;
			}


    
    
}