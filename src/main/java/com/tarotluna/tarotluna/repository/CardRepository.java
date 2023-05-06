package com.tarotluna.tarotluna.repository;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.tarotluna.tarotluna.models.Card;
import com.tarotluna.tarotluna.models.CardList;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Repository
public class CardRepository {
    
// SQLQUERIES

    
    @Autowired
    @Qualifier("cardcache") // match bean name in Redisconfig;
    private JdbcTemplate template;

    public void setKey(String name, JsonObject obj) {
        ValueOperations<String, String> ops = template.opsForValue();

        ops.set(name, obj.toString(), Duration.ofSeconds(3600));
    }

    public Optional<Card> getCardByName(String name) {
        ValueOperations<String, String> ops = template.opsForValue();

        String value = ops.get(name);
        // System.out.println(value);
        if (null == value) {
            return Optional.empty();
        }

        Card c = new Card();
        try (InputStream is = new ByteArrayInputStream(value.getBytes())) {
            JsonReader r = Json.createReader(is);
            c = Card.fromCache(r.readObject());
            return Optional.of(c);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }

    }


 ///////////////////////////////////



 private static final String INSERT_NEW_CARDLIST = "insert into cardlist (nhits, cListId, cardItems) values (?, ?, ?);";
 private static final String SELECT_USERNAME_BY_ID = "select * from User where user_id = ?;";
 private static final String SELECT_CARDS_BY_NAME = "select * from card where name = ?;";
 private static final String SELECT_CARDLISTS_BY_USER_ID = "select * from cardlist where user_id =?;";

 private static final String DELETE_CARDLIST_BY_ID = "delete from cardlist where user_id = ?;";
 private static final String UPDATE_CARDLIST = "";
 private static final String DELETE_CARD_BY_NAME = "delete from cardlist where name = ?;";

    public int insertNewCardList(byte[] file, int userId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(INSERT_NEW_CARDLIST, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1,file.getName());
                ps.setString(2,file.getName_short());
                ps.setString(3,file.getValue());
                ps.setString(5,file.getMeaning_up());
                ps.setString(6,file.getMeaning_reverse());
                ps.setString(7,file.getDesc());    
                ps.setInt(8, userId);
                return ps;
            }
        }, keyHolder);
        BigInteger bigInt = (BigInteger)keyHolder.getKey();
        if(bigInt == null) 
            return -1;
        return bigInt.intValue();
    }

    public boolean insertNewCardList(Object object, String insertNewCardList) {
        if(object.size() != sqlRowSet.size()) {
            throw new IllegalArgumentException();
        }

        List<Object[]> params = new ArrayList<>();
        for(int i = 0; i < object.size(); i++) {
            Object[] row = new Object[3];
            row[0] = object.get(i);
            row[1] = sqlRowSet.get(i);
            row[2] = insertNewCardList;
            params.add(row);
        }

        int[] results = template.batchUpdate(INSERT_NEW_CARDLIST, params);

        for(int i : results) {
            if(i <= 0) {
                throw new IllegalArgumentException();
            }
        }

        return true;
    }

    public Integer insertNewCardList(Object object, int userId) {
		return null;
	}



    public Optional<Card> getCardListById(Integer userId) {
        final SqlRowSet cardListResult = template.queryForRowSet(SELECT_CARDLISTS_BY_USER_ID, userId);
        
        if(cardListResult.next()) {
            Card c = Card.convert(cardListResult);

            final SqlRowSet username = template.queryForRowSet(SELECT_USERNAME_BY_ID, userId);
            // if(username.next()) {
            //     c.setCreatedBy(username.getString("username"));
            // } else {
            //     return Optional.empty();
            // }

            final SqlRowSet cListResult = template.queryForRowSet(SELECT_CARDS_BY_NAME, userId);
            List<String> cards = new ArrayList<String>();
            List<String> cardList = new ArrayList<String>();
            
            while(cListResult.next()) {
                cards.add(cListResult.getString("name"));
                cardList.add(cListResult.getString("cardListResult"));
            }

            c.setCard(cards);
            c.setCardList(cListResult);



            return Optional.of(c);
        } else {
            return Optional.empty();
        }
    }

    public List<CardList> getAllCardListsById(Integer userId) {
        final SqlRowSet result = template.queryForRowSet(SELECT_CARDLISTS_BY_USER_ID, userId);

        List<CardList> cardLists = new ArrayList<>();

        while(result.next()) {
            CardList cl = CardList.convert(result);
            cardLists.add(cl);
        }

        return cardLists;
    }

//     public List<CardList> getCardListsByName(String name) {
//         final SqlRowSet result = template.queryForRowSet(SELECT_CARD_LISTS_BY_NAME, "%"+name+"%");
//         List<CardList> cardLists= new ArrayList<>();
//         while(result.next()) {
//             CardList cs = CardList.convert(result);
//             cardLists.add(cs);
//         }
//         return cardLists;
//     }



    public boolean deleteCardListById(Integer cardListId) {
        int result = template.update(DELETE_CARDLIST_BY_ID, cardListId);
        System.out.println("DELETE LIST: " + result);
        return result > 0;
    }

    public boolean deleteCardByName() {
        int result = template.update(DELETE_CARD_BY_NAME);
        System.out.println("DELETE CARD: " + result);
        return result > 0;
    }

 

    public boolean editCardList(Card card, Integer userId) {
        int result = template.update(UPDATE_CARDLIST, 
         card.getCard(),
         card.getCId(),
        //  card.getCreatedBy(),
         card.getDesc(),
         ((CardList) card).getNhits(),
         card.getTypes(),
            userId);

        return result > 0;
    }



//     public Boolean insertNewCardList(Class<? extends Object> class1, String string) {
//         return null;
//     }


    

}
