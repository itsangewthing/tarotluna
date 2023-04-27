
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



    private Integer nhits;
    private String cListId;
    private List<String> items = new LinkedList<>();



    public CardList(Types types) {
        super(types);
        //TODO Auto-generated constructor stub
    }
 ////////////////////////////   

   
 

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

    public List<String> getItems() {
        return this.items;
    }

    public void setItems(List<String> items) {
        List<String> newItemList = new LinkedList<>();
        for (String i : items){
            newItemList.add(i);
        }
        
        this.items = newItemList;
    }

    
    //  public List<String> getCourtsRank() {
    //             return this.getCourtsRank();
    //         }

    //  public void setCourtsRank(List<String> courtsRank) {
    //     List<String> newCRList = new LinkedList<>();
    //     for (String cr : courtsRank){
    //         newCRList.add(cr);
    //         }
    //         this.courtsRank = courtsRank;
    //     }

    //  public List<String> getSuit() {
    //             return this.suit;
    //         }

    //   public void setSuit(List<String> suit) {
    //     List<String> newSList = new LinkedList<>();
    //     for (String s : suit){
    //         newSList.add(s);
    //         }
    //         this.suit = suit;
    //     }

           
        

//  ---------------------           
        
        public static List<CardList> getCardListsByName(String name, Integer nhits) {
            CardList cList = new CardList(null)<>();
            // cList.setNhits(String.valueOf(SqlRowSet) cListResult)

        return cList;
        }
    
            public static CardList convert(JsonObject jsonObject) {
                CardList cList = new CardList();
                cList.setCid(jsonObject.getString("clistId", ""));
                cList.setName(jsonObject.getString("strName", ""));
              
                return (Card) cList;
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
                for (String nhits : items) {
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


    
    
}