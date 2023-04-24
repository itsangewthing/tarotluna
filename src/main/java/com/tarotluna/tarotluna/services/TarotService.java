package com.tarotluna.tarotluna.services;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

// import com.amazonaws.services.s3.metrics.S3ServiceMetric;
import com.tarotluna.tarotluna.models.Card;
import com.tarotluna.tarotluna.models.CardList;
import com.tarotluna.tarotluna.models.User;
import com.tarotluna.tarotluna.repository.AccountRepository;
import com.tarotluna.tarotluna.repository.CardRepository;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class TarotService{

    @Autowired
    private CardRepository cardRepo;
    
    @Autowired
    private AccountRepository accRepo;

    // @Autowired
    // private S3ServiceMetric s3Svc;

    @Value("${API_KEY}")
    private String apiKey;

    final static String BASE_URL = "https://tarot-api.onrender.com/api/v1";
    private static final String URL_SEARCH_NAME = "https://tarot-api.onrender.com/api/v1/search";

    private static final String GET_ALL_CARDS = "https://tarot-api.onrender.com/api/v1/cards";
    private static final String GET_A_RANDOM_CARD = "https://tarot-api.onrender.com/api/v1/cards/random?n=1";
    private static final String INSERT_NEW_CARD_LIST = " ";
    private static final String SELECT_CARD_LIST_BY_ID ="";
    private static final String SELECT_USERNAME_BY_ID = "";
    private static final String SELECT_CARDS_BY_ID ="";
    private static final String SELECT_CARD_LISTS_BY_USER_ID = "";
    private static final String SELECT_CARD_LISTS_BY_NANE ="";
    private static final String DELETE_CARD_LIST_BY_ID =" ";
    private static final String DELETE_CARD_BY_NAME = "";
    private static final String UPDATE_CARD_LIST = "";


/* GET ALL CARDS */    
public List<Card> search(String searchCards) {
    String uri = UriComponentsBuilder.fromUriString(BASE_URL)
                        .queryParam("q", searchCards)
                        .queryParam("suit", "wands")
                        .queryParam("value", "5")
                        .queryParam("type", "major","minor")
                        .toUriString();

    System.out.println(searchCards);
    RestTemplate restTemplate = new RestTemplate();

    MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
    headers.add("X-RapidAPI-Key", "ddf652f3-9cdc-41d8-a1ae-4fd741185668");
    headers.add("X-RapidAPI-Host", "https://tarot-api.onrender.com/api/v1");

    HttpEntity<?> entity = new HttpEntity<Object>(headers);
    HttpEntity<String> resp = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
    String payload = resp.getBody();

    JsonReader reader = Json.createReader(new StringReader(payload));
    JsonObject result = reader.readObject();
    JsonArray data = result.getJsonObject("info").getJsonArray("cards");
    
    List<Card> cList = new LinkedList<>();
    for (Integer i = 0; i < data.size(); i++) {
        cList.add(Card.create(data.getJsonObject(i)));
    }

    return cList;
 }


/* GET 1 RANDOM CARD */

public List<Card> getRandomCardByValue(Integer n) {
    
    Long ts = System.currentTimeMillis();
    // String signature = "%d%s%s".formatted(ts, apiKey);
    String hash = "";
/*  GET_A_RANDOM_CARD_URL = "https://tarot-api.onrender.com/api/v1/cards/random?n=1";
    returns 1 number of unique random cards, else returns all cards in random order
    ?n = 1  

     */

     String searchUrl = UriComponentsBuilder.fromUriString(GET_A_RANDOM_CARD)
        .path("n")
        .queryParam("ts", ts)
        .queryParam("apikey", apiKey)
        .queryParam("hash", hash)
        .toUriString();

        if (searchUrl.contains("%7D")) {
            searchUrl = searchUrl.replace("%7D", "");
        }
        System.out.println("url > " + searchUrl);
 
        RequestEntity<Void> req = RequestEntity.get(searchUrl).accept(MediaType.APPLICATION_JSON).build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);
        String payload = resp.getBody();
        
        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject result = reader.readObject();
        JsonArray data = result.getJsonObject("data").getJsonArray("results");

        List<Card> cList = new LinkedList<>();
            for (Integer i = 0; i < data.size(); i++) {
                cList.add(Card.create(data.getJsonObject(i)));
            }

            return cList;
}




// CREATE CARD READING
    @Transactional
    public int createCardList(CardList cl, String email, byte[] file) {
        Optional<User> user = accRepo.findUserByEmail(email);

        if(user.isEmpty()) {
            throw new IllegalArgumentException();
        }

        Integer cardListId = cardRepo.insertNewCardList(null, user.get().getUserId());

        if(cardListId < 0) {
            throw new IllegalArgumentException();
        }

        cl.setCardListId(cardListId.toString());

        cardRepo.insertNewCardList(null, cardListId);
        Integer result = cardRepo.insertNewCardList(file, cardListId);


        return result;
       
    }

// EDIT CARD READING    
    @Transactional
    public Boolean editCardList(Card card, String email, MultipartFile file) throws IOException {
        if(email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }

        Optional<User> userOpt = accRepo.findUserByEmail(email);

        if(userOpt.isEmpty()) {
            throw new IllegalArgumentException();
        }

        cardRepo.editCardList(card, userOpt.get().getUserId());
        Object cardE = card.getCards();


        cardRepo.deleteCardByName();
        return cardRepo.insertNewCardList(cardE.getClass(), cardE.toString());
    }

    public Optional<Card> getCardListsById(String cardListId) {
        try{
            Integer cId = Integer.parseInt(cardListId);
            return cardRepo.getCardListById(cId);
        } catch(Exception e) {
            return Optional.empty();
        }  
    }

    public List<CardList> getCardListByEmail(String email) {
        Optional<User> user = accRepo.findUserByEmail(email);

        if(user.isEmpty()) {
            return new ArrayList<CardList>();
        }

        return cardRepo.getAllCardLists(user.get().getUserId());
    }

    public List<CardList> getCardListsByName(String name) {
        return CardList.getCardListsByName(name);
    }


    @Transactional
    public boolean deleteCardListById(String cardListId) {
        int cId = Integer.parseInt(cardListId);
        if(cardRepo.deleteCardListById(cId)) {
   
            return cardRepo.deleteCardListById(cId);
        }
        return false;
    }

    public List<CardList> getCardLists(String query) {
        final String searchUrl = UriComponentsBuilder.fromUriString(URL_SEARCH_NAME)
                .queryParam("q", query)
                .toUriString();

        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<Void> req = RequestEntity.get(searchUrl)
                .accept(MediaType.APPLICATION_JSON)
                .build();
        ResponseEntity<String> resp = restTemplate.exchange(req, String.class);
        final String payload = resp.getBody();
        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonArray jTarot = null;
        List<CardList> cardLists = new ArrayList<CardList>();
        try {
            jTarot = reader.readObject().getJsonArray("cardlist");
            for (int i = 0; i < jTarot.size(); i++) {
                CardList cl = CardList.convert(jTarot.getJsonObject(i));
                cardLists.add(cl);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return cardLists;
    }

    public List<Card> getAllCards(String query) {
        final String searchUrl = UriComponentsBuilder.fromUriString(GET_ALL_CARDS)
                .queryParam("q", query)
                .toUriString();

        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<Void> req = RequestEntity.get(searchUrl)
                .accept(MediaType.APPLICATION_JSON)
                .build();
        ResponseEntity<String> resp = restTemplate.exchange(req, String.class);
        final String payload = resp.getBody();
        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonArray jTarots = null;
        List<Card> cards = new ArrayList<Card>();
        try {
            jTarots = reader.readObject().getJsonArray("allcards");
            for (int i = 0; i < jTarots.size(); i++) {
                Card cl = CardList.convert(jTarots.getJsonObject(i));
                cards.add(cl);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return cards;
    }


}
