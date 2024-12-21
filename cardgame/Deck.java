package cardgame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class Deck {

    protected String deckname;
    protected ArrayList<Actions> cards;
    protected static DatabaseHandler dbhandler;
    // protected static int cardindex;
    private static Connection connection;

    Deck() {

    }

    Deck(DatabaseHandler dbhandler, String deckname, ArrayList<Actions> cards) {
        this.dbhandler = dbhandler;
        this.deckname = deckname;
        this.cards = cards;
    }

    public void setDbhandler(DatabaseHandler dbhandler) {
        this.dbhandler = dbhandler;
    }

    // public void setCardIndex(int cardindex) {
    //     this.cardindex = cardindex;
    // }

    // public int getCardIndex() {
    //     return this.cardindex;
    // }

    public void setDeckName(String deckname) {
        this.deckname = deckname;
    }

    public String getDeckName() {
        return this.deckname;
    }

    public void setCards(ArrayList<Actions> cards) {
        this.cards = cards;
    }

    public ArrayList<Actions> getCards() {
        return this.cards;
    }

    public Actions getAction(int i) {
        return this.cards.get(i);
    }

    public void saveDeck() {
        try {
            SqlQueryBuilder builder = new SqlQueryBuilder();

            // Build INSERT query
            PreparedStatement insertStatement = builder
                    .insertInto("DeckName", "Card1", "Card2", "Card3", "Card4", "Card5", "Card6", "Card7", "Card8", "Card9", "Card10")
                    .addParameter(deckname) // First parameter
                    .addParameter(cards.get(0)) // Second parameter
                    .addParameter(cards.get(1)) // Second parameter
                    .addParameter(cards.get(2))
                    .addParameter(cards.get(3))
                    .addParameter(cards.get(4))
                    .addParameter(cards.get(5))
                    .addParameter(cards.get(6))
                    .addParameter(cards.get(7))
                    .addParameter(cards.get(8))
                    .addParameter(cards.get(9))
                    .build(dbhandler.getConnection());

            // // Debugging
            // System.out.println("Query: " + builder.getQuery());
            // System.out.println("Parameters: " + builder.getParameters());
            // // Execute the query
            // int rowsInserted = insertStatement.executeUpdate();
            // System.out.println(rowsInserted + " row(s) inserted.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
