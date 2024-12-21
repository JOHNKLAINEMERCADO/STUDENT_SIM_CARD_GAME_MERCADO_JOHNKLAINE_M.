package cardgame;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.Scanner;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import jdk.jfr.Description;

public class Decks {

    // protected static Connection connection;
    protected static DatabaseHandler dbhandler;
    protected static int selecteddeck;
    protected static ArrayList<Deck> decks;

    Decks() {

    }

    public void setDbhandler(DatabaseHandler dbhandler) {
        this.dbhandler = dbhandler;
    }

    public Deck getDeck() {
        return decks.get(selecteddeck);
    }

    public int getSelectedDeck() {
        return selecteddeck;
    }

    public int count() {
        return decks.size();
    }

    public Deck getDeck(int i) {
        return decks.get(i);
    }

    public String DeckName() {
        return decks.get(selecteddeck).getDeckName();
    }

    public ArrayList<Actions> Cards() {
        return decks.get(selecteddeck).getCards();
    }

    public static Actions ActionsCard(int cardid) {
        int r = 0;
        // GameScreen.printAt(40+r, 40, "Start ACtions Card");
        for (Actions card : Main.cards) {
            r++;
            // GameScreen.printAt(40+r, 40, card.title + "[" + card.code + "]" + );
            if (card.code == cardid) {
                return card;
            }
        }
        return new Actions();
    }

    public static int ActionsCardIndex(Actions selcard) {
        for (int i = 0; i < Main.cards.size(); i++) {
            if (Main.cards.get(i).code == selcard.code) {
                return i;
            }
        }
        return 0;
    }

    public void setDeck(int selecteddeck) {
        Decks.selecteddeck = selecteddeck;
    }

    public static void refresh() {

        dbhandler.setupConnection(Main.url, Main.user, Main.password);
        String query = "SELECT * FROM Decks";
        ResultSet resultSet = dbhandler.executeQuery(query);

        // Process the result set
        Deck deck;
        ArrayList<Actions> cards;
        decks = new ArrayList();
        int r = 0;
        // GameScreen.printAt(60 + r, 10, "Start Get DECKS");
        Connection connection = dbhandler.getConnection();
        try {
            while (resultSet != null && resultSet.next()) {
                // r++;
                // GameScreen.printAt(60 + r, 10, " Decks ->" + resultSet.getString("DeckName"));

                deck = new Deck();
                deck.setDbhandler(dbhandler);
                deck.setDeckName(resultSet.getString("DeckName"));

                cards = new ArrayList();
                cards.add(ActionsCard(resultSet.getInt("Card1")));
                cards.add(ActionsCard(resultSet.getInt("Card2")));
                cards.add(ActionsCard(resultSet.getInt("Card3")));
                cards.add(ActionsCard(resultSet.getInt("Card4")));
                cards.add(ActionsCard(resultSet.getInt("Card5")));
                cards.add(ActionsCard(resultSet.getInt("Card6")));
                cards.add(ActionsCard(resultSet.getInt("Card7")));
                cards.add(ActionsCard(resultSet.getInt("Card8")));
                cards.add(ActionsCard(resultSet.getInt("Card9")));
                cards.add(ActionsCard(resultSet.getInt("Card10")));
                deck.setCards(cards);

                decks.add(deck);
            }
            
            // int y = 0;
            // for (Actions i : Main.cards) {
            //     y++;
            //     GameScreen.printAt(40 + y, 80, String.valueOf(i.code) + "->" + i.title);
            // }

        } catch (SQLException e) {
            System.err.println("Error processing the result set: " + e.getMessage());
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

    public static void displayDecks() {
        ArrayList<Integer> decks = new ArrayList<Integer>();
        String deckname;
        int i = 0;
        try {
            File deckFile = new File("deckfile.txt");
            Scanner deckScanner = new Scanner(deckFile);
            deckScanner.useDelimiter(",");

            while (deckScanner.hasNextLine()) {
                if (i == 0) {
                    deckname = deckScanner.nextLine();
                    System.out.println("(" + (i + 1) + ") " + deckname);
                } else {
                    decks.add(Integer.valueOf(deckScanner.nextLine()));
                }

                i++;

                if (i == 8) {
                    CardList.displayCards(decks);
                    decks.clear();
                    System.out.print("\n\n");
                    i = 0;
                }
            }
            deckScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    //access file of decks to read values
    public static ArrayList<Integer> selectDeck(int decknum) {
        ArrayList<Integer> deck = new ArrayList<Integer>();
        String deckname = "value not found";
        int i = 0;
        int j = 1;
        try {
            File deckFile = new File("deckfile.txt");
            Scanner deckScanner = new Scanner(deckFile);
            deckScanner.useDelimiter(",");

            while (deckScanner.hasNextLine()) {
                if (i == 0) {
                    deckname = deckScanner.nextLine();
                } else {
                    deck.add(Integer.valueOf(deckScanner.nextLine()));
                }

                i++;

                if (j == decknum) {
                    break;
                }
                if (i == 8) {
                    j++;
                    deck.clear();
                    i = 0;
                }
            }
            deckScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        System.out.println(deckname + "was successfully selected!");
        return deck;
    }

    //access file of decks to add values
    public static void addDeck(String deckname, ArrayList<Integer> deck) {
        try {
            FileWriter deckScanner = new FileWriter("filename.txt");
            deckScanner.write(deckname + ",");
            for (int i = 0; i <= 8; i++) {
                deckScanner.write(deck.get(i) + ",");
            }
            deckScanner.write("\n");
            deckScanner.close();
            System.out.println("Deck has been added");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
