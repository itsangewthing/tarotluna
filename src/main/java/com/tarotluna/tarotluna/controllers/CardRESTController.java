package com.tarotluna.tarotluna.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonValue;
import com.tarotluna.tarotluna.EmailDetails;
import com.tarotluna.tarotluna.constants.EmailTemplate;
import com.tarotluna.tarotluna.constants.URLs;
import com.tarotluna.tarotluna.models.Card;
import com.tarotluna.tarotluna.models.CardList;
import com.tarotluna.tarotluna.models.Response;
import com.tarotluna.tarotluna.repository.CardRepository;
import com.tarotluna.tarotluna.services.EmailService;
import com.tarotluna.tarotluna.services.TarotService;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin()
public class CardRESTController {

    @Autowired
    private TarotService tarotSvc;

    @Autowired
    private CardRepository cardRepo;
    
    @Autowired
    private EmailService emailSvc;
    
    // SEARCH CARD BY NAME
    @GetMapping(path="/cards/search", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> search(@RequestParam("searchName") String searchName) {
        List<Card> cards = tarotSvc.search(searchName);
        System.out.println(searchName);

        JsonArrayBuilder jab = Json.createArrayBuilder();
        for (int i = 0; i < cards.size(); i++) {
            jab.add(cards.get(i).toJSON());
        }
        JsonArray ja = jab.build();

        return ResponseEntity.ok(ja.toString());
    }

// GET RANDOM CARD
    @GetMapping(path="/cards/random?n=1", produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin()
    public ResponseEntity<String> getRandomCard( @RequestParam(value ="1", required = false) Integer n) {
        System.out.println("scheming through %d to get random card");

        List<Card> card = tarotSvc.getRandomCardByValue(n);
        
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for (Card c : card) {
            JsonObject jo = c.toJSON();

            cardRepo.setKey(String.valueOf(jo.getInt("value")), jo);

            jab.add(jo);
        }
        JsonArray ja = jab.build();
        
        System.out.println("returning a random card");

        return ResponseEntity.ok(ja.toString());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createCardList(MultipartFile file,
            @RequestPart Integer cardList,
            @RequestPart Integer nhits,
            @RequestPart String name,
            @RequestPart String value,
            @RequestPart String desc,
            @RequestPart String meaning_up,
            @RequestPart String meaning_reverse,
            @RequestPart JsonValue Types,
            @RequestPart String email) {

            Response resp = new Response();
            CardList cl = new CardList(); 
            cl.setCardList(cl);
            cl.setNhits(nhits);
            cl.setName(name);
            cl.setValue(value);
            cl.setDesc(desc);
            cl.setMeaning_up(meaning_up);
            cl.setMeaning_reverse(meaning_reverse);
            cl.setType(Types);
            cl.setEmail(email);

            try {
                final int cardListId = tarotSvc.createCardList(cl, email, file.getBytes());
    
                if (cardListId > 0) {
                    String msgBody = EmailTemplate.constructRecipeCreated(
                                cl.getName(),
                
                                URLs.URL_HOME + "/#/divination/user/" + cardListId);
                    String subject = "New Divination Created!";
                    EmailDetails details = new EmailDetails(email, msgBody, subject);
                    emailSvc.sendEmail(details);
        
                    resp.setCode(HttpStatus.CREATED.value());
                    resp.setMessage("Divination Created");
                    JsonObject data = Json.createObjectBuilder().add("cardListId", cardListId).build();
                    resp.setData(data);
                    return ResponseEntity.status(HttpStatus.CREATED).body(resp.toJson().toString());
                } else {
                    //s3Svc.delete(thumbnailOpt.get());
                    resp.setCode(HttpStatus.BAD_REQUEST.value());
                    resp.setMessage("Something went wrong when creating recipe!");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp.toJson().toString());
                }
    
            } catch(IOException ex) {
                resp.setCode(HttpStatus.BAD_REQUEST.value());
                resp.setMessage("Something went wrong with thumbnail file!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp.toJson().toString());
            }    
        }

        @GetMapping(path = "/cardlist/{cardListId}", consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<String> getCardListsById(@PathVariable String cardListId) {
            Optional<Card> clOpt = tarotSvc.getCardListsById(cardListId);
            if (clOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok().body(clOpt.get().toJson().toString());
            }
        }
    
        @GetMapping(path = "/cardlists/{email}", consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<String> getCardListByEmail(@RequestParam String email) {
            List<CardList> cardlists = tarotSvc.getCardListByEmail(email);
            JsonArrayBuilder jArrayBuilder = Json.createArrayBuilder();
            cardlists.forEach(v -> {
                jArrayBuilder.add(v.toJson());
            });
            return ResponseEntity.ok(jArrayBuilder.build().toString());
        }
    
        @GetMapping(path = "/cardlist/{name}", consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<String> getCardListsByName(@PathVariable String name) {
            List<CardList> cardlists = tarotSvc.getCardListsByName(name);
            JsonArrayBuilder jArrayBuilder = Json.createArrayBuilder();
            cardlists.forEach(v -> {
                jArrayBuilder.add(v.toJson());
            });
            return ResponseEntity.ok(jArrayBuilder.build().toString());
        }


        @GetMapping(path = "/cardlists", consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<String> getCardLists(@RequestParam String s) {
            List<CardList> cardLists = tarotSvc.getCardLists(s);
            JsonArrayBuilder jArrayBuilder = Json.createArrayBuilder();
        cardLists.forEach(v->{
                jArrayBuilder.add(v.toJson());
            });
            return ResponseEntity.ok(jArrayBuilder.build().toString());
        }

    }
