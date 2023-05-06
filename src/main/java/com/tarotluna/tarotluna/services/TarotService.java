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
    private static final String GET_THREE_RANDOM_CARDS = "https://tarot-api.onrender.com/api/v1/cards/random?n=3";
    private static final String GET_FIVE_RANDOM_CARDS = "https://tarot-api.onrender.com/api/v1/cards/random?n=5";
    private static final String SELECT_CARD_BY_RANKS = "https://tarot-api.onrender.com/api/v1/cards/courts/{ranks} ";


/* GET CARD */    
public List<Card> searchKeyword(String searchKeyword, Object q, Object suit, Object value, Object type) {
    String uri = UriComponentsBuilder.fromUriString(BASE_URL)
                .queryParam("q", q)
                .queryParam("suit", suit)
                .queryParam("value", value)
                .queryParam("type", type)
                .toUriString();

    System.out.println(searchKeyword);
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

 /* GET ALL CARDS */    
public List<Card> getAllCards(String getAllCards, Object query) {

String searchUrl = UriComponentsBuilder.fromUriString(GET_ALL_CARDS)
    .queryParam("q", query)
    .toUriString();

    System.out.println(getAllCards);

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

/* SEARCH CARDS */    
public List<Card> search(String searchCards, Object query) {
            String uri = UriComponentsBuilder.fromUriString(URL_SEARCH_NAME)
                        .queryParam("q", query)
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

        public List<Card> getARandomCard(Integer n) {
            
            // Long ts = System.currentTimeMillis();
            // String signature = "%d%s%s".formatted(ts, apiKey);
            // String hash = "";
        /*  GET_A_RANDOM_CARD_URL = "https://tarot-api.onrender.com/api/v1/cards/random?n=1";
            returns 1 number of unique random cards, else returns all cards in random order
            ?n = 1  

            */

            String searchUrl = UriComponentsBuilder.fromUriString(GET_A_RANDOM_CARD)
                .queryParam("n", 1)
                .toUriString();

                // if (searchUrl.contains("%7D")) {
                //     searchUrl = searchUrl.replace("%7D", "");
                // }
                // System.out.println("url > " + searchUrl);
        
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

/* GET 3 RANDOM CARDS */

        public List<Card> getThreeRandomCards(Integer n) {
        /*  "https://tarot-api.onrender.com/api/v1/cards/random?n=3";
            */

            String searchUrl = UriComponentsBuilder.fromUriString(GET_THREE_RANDOM_CARDS)
                .queryParam("n", 3)
                .toUriString();

        
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


/* GET 5 RANDOM CARDS */

        public List<Card> getFiveRandomCards(Integer n) {
            
        /*  GET_A_RANDOM_CARD_URL = "https://tarot-api.onrender.com/api/v1/cards/random?n=5";
            */

            String searchUrl = UriComponentsBuilder.fromUriString(GET_FIVE_RANDOM_CARDS)
                .queryParam("n", 5)
                .toUriString();

        
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

/* SELECT ALL COURT RANKS */
// https://tarot-api.onrender.com/api/v1/cards/courts/{rank}

public List<Card> getCardByRank(Object rank) {
            
        String searchUrl = UriComponentsBuilder.fromUriString(SELECT_CARD_BY_RANKS)
            .queryParam("rank", rank)
            .toUriString();

    
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








//////////////////////////////////////////////////////
//////////////////////////////////////////////////////


// CREATE CARD LIST 

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

        cl.setCListId(cardListId.toString());

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
        Object cardE = card.getCard();


        cardRepo.deleteCardByName();
        return cardRepo.insertNewCardList(cardE.getClass(), cardE.toString());
    }


// GET CARDLSITS BY ID
    public Optional<Card> getCardListsById(String cardListId) {
        try{
            Integer cId = Integer.parseInt(cardListId);
            return cardRepo.getCardListById(cId);
        } catch(Exception e) {
            return Optional.empty();
        }  
    }


/////////////////// GET CARD LIST BY EMAIL
    public List<CardList> getCardListByEmail(String email) {
        Optional<User> user = accRepo.findUserByEmail(email);

        if(user.isEmpty()) {
            return new ArrayList<CardList>();
        }

        return cardRepo.getAllCardListsById(user.get().getUserId());
    }


////////// DELETE CARD LIST BY ID
    @Transactional
    public boolean deleteCardListById(String i) {
        int cId = Integer.parseInt(i);
        if(cardRepo.deleteCardListById(cId)) {
   
            return cardRepo.deleteCardListById(cId);
        }
        return false;
    }


////////// GET CARD LISTS    

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

    }
    

   