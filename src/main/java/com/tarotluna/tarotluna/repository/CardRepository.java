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
    private JdbcTemplate template;

    private RedisTemplate<String, String> redisTemplate;

    public void setKey(String name, JsonObject obj) {
        ValueOperations<String, String> ops = ((RedisOperations<String, String>) template).opsForValue();

        ops.set(name, obj.toString(), Duration.ofSeconds(3600));
    }

    public Optional<Card> getCards(String name) {
        // System.out.println(goods_id);
        ValueOperations<String, String> ops = ((RedisOperations<String, String>) template).opsForValue();

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

 private static final String SQL_INSERT_RECIPE = "insert into recipe (name, category, country, instructions, youtubeLink, user_id) values (?, ?, ?, ?, ?, ?);";
 private static final String SQL_INSERT_INGREDIENT = "insert into ingredient (name, measurement, recipe_id) values (?, ?, ?);";
 private static final String SQL_SELECT_RECIPE_BY_ID = "select * from recipe where recipe_id = ?;";
 private static final String SQL_SELECT_RECIPE_BY_USER_ID = "select * from recipe where user_id = ?;";
 private static final String SQL_SELECT_USERNAME_BY_RECIPE_ID = 
 """
 select u.username
 from user as u
 join recipe as r
 on u.user_id = r.user_id
 where r.recipe_id = ?;
 """;

    public int insertNewCardList(byte[] file, int userId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(INSERT_NEW_CARD_LIST, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1,file.getName());
                ps.setString(2,file.getName_short());
                ps.setString(3,file.getValue());
                ps.setInt(4,file.getValue_int());
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

    public Integer insertNewCardList(Object object, SqlRowSet sqlRowSet, String insertNewCardList) {
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

        int[] results = template.batchUpdate(INSERT_NEW_CARD_LIST, params);

        for(int i : results) {
            if(i <= 0) {
                throw new IllegalArgumentException();
            }
        }

        return true;
    }



    public Optional<Card> getCardListById(Integer cardListId) {
        final CardList cardListResult = template.queryForRowSet(SELECT_CARD_LIST_BY_ID, cardListId);
        
        if(cardListResult.next()) {
            Card c = Card.convert(cardListResult);

            final SqlRowSet username = template.queryForRowSet(SELECT_USERNAME_BY_ID, cardListId);
            if(username.next()) {
                c.setCreatedBy(username.getString("username"));
            } else {
                return Optional.empty();
            }

            final SqlRowSet cListResult = template.queryForRowSet(SELECT_CARDS_BY_ID, cardListId);
            List<String> cards = new ArrayList<String>();
            List<String> cardList = new ArrayList<String>();
            
            while(cListResult.next()) {
                cards.add(cListResult.getString("name"));
                cardList.add(cListResult.getString("cardListResult"));
            }

            c.setCard(cards);
            c.setCardList(cardListResult);



            return Optional.of(c);
        } else {
            return Optional.empty();
        }
    }

    public List<CardList> getAllCardListsBy(Integer userId) {
        final SqlRowSet result = template.queryForRowSet(SELECT_CARD_LISTS_BY_USER_ID, userId);

        List<CardList> cardLists = new ArrayList<>();

        while(result.next()) {
            CardList cl = CardList.convert(result);
            cardLists.add(cl);
        }

        return cardLists;
    }

    public List<CardList> getCardListsByName(String name) {
        final SqlRowSet result = template.queryForRowSet(SELECT_CARD_LISTS_BY_NAME, "%"+name+"%");
        List<CardList> cardLists= new ArrayList<>();
        while(result.next()) {
            CardList cs = CardList.convert(result);
            cardLists.add(cs);
        }
        return cardLists;
    }



    public boolean deleteCardListById(Integer cardListId) {
        int result = template.update(DELETE_CARD_LIST_BY_ID, cardListId);
        System.out.println("DELETE LIST: " + result);
        return result > 0;
    }

    public boolean deleteCardByName() {
        int result = template.update(DELETE_CARD_BY_NAME);
        System.out.println("DELETE CARD: " + result);
        return result > 0;
    }

 

    public boolean editCardList(Card card, Integer userId) {
        int result = template.update(UPDATE_CARD_LIST, 
         card.getCard(),
         card.getCId(),
         card.getCreatedBy(),
         card.getDesc(),
         card.getNhits(),
         card.getTypes(),
            userId);

        return result > 0;
    }

    public Boolean insertNewCardList(Class<? extends Object> class1, String string) {
        return null;
    }


    

}
