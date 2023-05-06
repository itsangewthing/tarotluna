package com.tarotluna.tarotluna.constants;

public class EmailTemplate {
    private static final String WELCOME_MESSAGE = """
        <div style="text-align:center;">
        <h1>Welcome to Luna Stellar</h1>
        <p>Thank you for joining Lunar Stellar, a platform that offers a seamless and interactive experience for anyone interested in tarot readings. 
          </p>
        <p>If you're ready to explore the mystical world of tarot, have your divination read today!</p>
        <p><a href="%s" style="
        background-color: white;
        color: black;
        padding: 15px 25px;
        text-decoration: none;
        cursor: pointer;
        border: none;
        border-radius: 10px;
        margin: 10px;">Start a tarot reading.</a></p>
        </div>
        """;
    
    private static final String SPREAD_CREATE_MESSAGE = """
        <div style="text-align:center;">
        <h1>Success!</h1>
        <p>You have created your own %s tarot spread reading and is now available for you to look back on.</p>
        <p><img src="%s"></p>
        <p><a href="%s" style="
        background-color: white;
        color: black;
        padding: 15px 25px;
        text-decoration: none;
        cursor: pointer;
        border: none;
        border-radius: 10px;
        margin: 10px;">View Tarot Reading</a></p>
        </div>
        """;

    private static final String SPREAD_REMOVED_MESSAGE = """
        <div style="text-align:center;">
        <h1>Tarot Reading Removed</h1>
        <p>Feel free to add more into your tarot spread collection!</p>
        <p><a href="%s" style="
        background-color: white;
        color: black;
        padding: 15px 25px;
        text-decoration: none;
        cursor: pointer;
        border: none;
        border-radius: 10px;
        margin: 10px;">Start a tarot reading</a></p>
        </div>
        """;

    
    public static String constructWelcome(final String url) {
        return WELCOME_MESSAGE.formatted(url);
    }

    public static String constructCardListCreated(final Integer integer, final String spreadUrl) {
        return SPREAD_CREATE_MESSAGE.formatted(integer, spreadUrl);
    }

    public static String constructCardListRemoved(final String SpreadName, final String url) {
        return SPREAD_REMOVED_MESSAGE.formatted(SpreadName, url);
    }


}
