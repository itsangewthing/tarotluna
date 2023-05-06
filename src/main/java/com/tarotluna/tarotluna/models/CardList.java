
package com.tarotluna.tarotluna.models;

import java.util.LinkedList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

public class CardList extends Card {


    private String[] cardList;
    private Integer nhits;
    private String cListId;
    private CardList cardItems;
    private List<String> cards = new LinkedList<>();




 ////////////////////////////   

    public List<String> getCards() {
        return this.cards;
    }

    public void setCards(List<String> cards) {
        this.cards = cards;
    }

    public String[] getCardList() {
        return this.cardList;
    }

    public void setCardList(String[] cardList) {
        this.cardList = cardList;
    }


    public Integer getNhits() {
        return this.nhits;
    }

    public void setNhits(Integer nhits) {
        this.nhits = nhits;
    }

    public String getCListId() {
        return this.cListId;
    }

    public void setCListId(String cListId) {
        this.cListId = cListId;
    }

    public CardList getCardItems() {
        return this.cardItems;
    }

    public void setCardItems(CardList cl) {
        this.cardItems = cl;
    }
//////////////////
     public List<String> getCourtsRank() {
                return this.getCourtsRank();
            }

     public void setCourtsRank(List<String> courtsRank) {
        List<String> newCRList = new LinkedList<>();
        for (String cr : courtsRank){
            newCRList.add(cr);
            }
            this.cards = courtsRank;
        }

     public List<String> getSuit() {
                return this.getSuit();
            }

      public void setSuit(List<String> suit) {
        List<String> newSList = new LinkedList<>();
        for (String s : suit){
            newSList.add(s);
            }
            this.cards = suit;
        }

           
        

//  ---------------------       

        
        public static List<CardList> getCardListsByName(String name, Integer nhits) {
            List<CardList> cList = new LinkedList<>();
            // cList.setNhits(String.valueOf(SqlRowSet) cListResult)

        return cList;
        }
    
            public static CardList convert(JsonObject jsonObject) {
                CardList cList = new CardList();
                cList.setCid(jsonObject.getString("clistId", ""));
                cList.setName(jsonObject.getString("strName", ""));
              
                return (CardList) cList;
            }
        
            private void setCid(String string) {
            }

            public static CardList convert(final SqlRowSet rs) {
                CardList cList = new CardList();
                cList.setCid(String.valueOf(rs.getInt("clistId")));
                cList.setName(rs.getString("name"));

                return cList;
            }
        
            public JsonObject toJson() {
                return Json.createObjectBuilder()
                .add("clistId", cListId)
                .add("name", name)
                .build();
            }

            public JsonArray itemsList() {
                JsonArrayBuilder jab = Json.createArrayBuilder();
                for (String nhits : cards) {
                    jab.add(nhits);
                }
                return jab.build();
            }
        
            public static List<String> createItemsListFromJA(JsonArray ja) {
                List<String> li = new LinkedList<>();
                for (JsonValue v : ja) {
                    li.add(v.toString());
                }
        
                return li;
            }

// ------------------------------------------		



			public boolean next() {
				return false;
			}

            public void setEmail(String email) {
            }
    
}