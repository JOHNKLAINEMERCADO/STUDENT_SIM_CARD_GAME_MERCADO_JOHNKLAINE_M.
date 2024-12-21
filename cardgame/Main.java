package cardgame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

    public static ArrayList<Actions> cards = new ArrayList<>();
    public static ArrayList<String> actionhistory = new ArrayList<>();
    public static int ScreenMaxWidth;
    public static int ScreenMaxHeight;
    public static Avatar avatar = new Avatar();
    public static Subject subject1 = new Subject(1);
    public static Subject subject2 = new Subject(2);
    public static Subject subject3 = new Subject(3);
    public static Subject subject4 = new Subject(4);
    public static ArrayList<Subject> subjects = new ArrayList<>();
    public static int currentDay = 1;
    public static int currentTime = 7;
    public static int starttime = 7;
    public static DatabaseHandler dbHandler = new DatabaseHandler();

    // ANSI escape codes for styling
    public static final String RESET = "\033[0m";
    public static final String HIGHLIGHT = "\033[7m"; // Inverted colors
    public static final String CLEAR_SCREEN = "\033[2J\033[H";
    public static int SelectedDeck;
    public static String DeckName;
    public static Decks decks;
    static final String  url = "jdbc:mysql://localhost:3306/OOPFinalProject";
    static final String user = "root";
    static final String password = "DCP-T510W";


    public static void insertCard(Actions act) {
        Connection connection = dbHandler.getConnection();
        try {
            SqlQueryBuilder builder = new SqlQueryBuilder();

            // Build INSERT query
            PreparedStatement insertStatement = builder
                    .insertInto("CardID", "Title", "CardType", "AP", "MP", "FP", "laze", "addturn", "duration", "FPCost", "MPCost", "qty")
                    .addParameter(act.code) // First parameter
                    .addParameter(act.title) // Second parameter
                    .addParameter(act.cardtype) // Second parameter
                    .addParameter(act.Red)
                    .addParameter(act.Green)
                    .addParameter(act.Blue)
                    .addParameter(act.laze)
                    .addParameter(act.addturn)
                    .addParameter(act.size)
                    .addParameter(act.Blueenergy)
                    .addParameter(act.Greenenergy)
                    .addParameter(act.Number)
                    .build(connection);

            // Debugging
            System.out.println("Query: " + builder.getQuery());
            System.out.println("Parameters: " + builder.getParameters());

            // Execute the query
            int rowsInserted = insertStatement.executeUpdate();
            System.out.println(rowsInserted + " row(s) inserted.");

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

    public static String insertDeck(ArrayList<Integer> drawpile) {
        String withDup = Main.isExistDeck(Main.DeckName);
        if ("With Duplicate".equals(withDup)) {
            return updateDeck(drawpile);            
        }
        // Set up connection
        if (!"".equals(withDup)) {
            return withDup;
        }

        dbHandler.setupConnection(url, user, password);

        Connection connection = dbHandler.getConnection();
        try {
            SqlQueryBuilder builder = new SqlQueryBuilder();

            // Build INSERT query

            PreparedStatement insertStatement = builder
                    .insertInto("Decks", "DeckName", "Card1", "Card2", "Card3", "Card4", "Card5", "Card6", "Card7", "Card8", "Card9", "Card10")
                    .addParameter(Main.DeckName)
                    .addParameter(Main.cards.get(drawpile.get(0)).code)
                    .addParameter(Main.cards.get(drawpile.get(1)).code)
                    .addParameter(Main.cards.get(drawpile.get(2)).code)
                    .addParameter(Main.cards.get(drawpile.get(3)).code)
                    .addParameter(Main.cards.get(drawpile.get(4)).code)
                    .addParameter(Main.cards.get(drawpile.get(5)).code)
                    .addParameter(Main.cards.get(drawpile.get(6)).code)
                    .addParameter(Main.cards.get(drawpile.get(7)).code)
                    .addParameter(Main.cards.get(drawpile.get(8)).code)
                    .addParameter(Main.cards.get(drawpile.get(9)).code)
                    .build(connection);

            // Debugging
            System.out.println("Query: " + builder.getQuery());
            System.out.println("Parameters: " + builder.getParameters());

            // Execute the query
            int rowsInserted = insertStatement.executeUpdate();
            System.out.println(rowsInserted + " row(s) inserted.");

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
        return "";
    }

    public static String updateDeck(ArrayList<Integer> drawpile) {
        // Set up connection
        dbHandler.setupConnection(url, user, password);

        Connection connection = dbHandler.getConnection();
        try {
 // Build UPDATE query
            SqlQueryBuilder updateBuilder = new SqlQueryBuilder();
            PreparedStatement updateStatement = updateBuilder
                .update("Decks")
                .set("DeckName")
                .addParameter(Main.DeckName)
                .set("Card1")
                .addParameter(Main.cards.get(drawpile.get(0)).code)
                .set("Card2")
                .addParameter(Main.cards.get(drawpile.get(1)).code)
                .set("Card3")
                .addParameter(Main.cards.get(drawpile.get(2)).code)
                .set("Card4")
                .addParameter(Main.cards.get(drawpile.get(3)).code)
                .set("Card5")
                .addParameter(Main.cards.get(drawpile.get(4)).code)
                .set("Card6")
                .addParameter(Main.cards.get(drawpile.get(5)).code)
                .set("Card7")
                .addParameter(Main.cards.get(drawpile.get(6)).code)
                .set("Card8")
                .addParameter(Main.cards.get(drawpile.get(7)).code)
                .set("Card9")
                .addParameter(Main.cards.get(drawpile.get(8)).code)
                .set("Card10")
                .addParameter(Main.cards.get(drawpile.get(9)).code)
                .where("DeckName = ?")
                .addParameter(Main.DeckName)
                .build(connection);

            // Debugging
            System.out.println("Query: " + updateBuilder.getQuery());
            System.out.println("Parameters: " + updateBuilder.getParameters());

            // Execute the query
            int rowsInserted = updateStatement.executeUpdate();
            System.out.println(rowsInserted + " row(s) inserted.");

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
        return "";
    }

    public static String isExistDeck(String deckName) {
        // Set up connection
        dbHandler.setupConnection(url, user, password);

        Connection connection = dbHandler.getConnection();
        try {
            SqlQueryBuilder selectBuilder = new SqlQueryBuilder();
            PreparedStatement selectStatement = selectBuilder
                .select("COUNT(*)")
                .from("Decks")
                .where("DeckName = ?")
                .addParameter(deckName)
                .build(connection);

            // Debugging
            System.out.println("Query: " + selectBuilder.getQuery());
            System.out.println("Parameters: " + selectBuilder.getParameters());

            // Execute the select query
            var resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    return "With Duplicate";
                } else {
                    return "";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return  e.getMessage();
            }
        }
        return "with Error";
    }

    public static void main(String[] args) throws IOException {

        // Set up connection
        dbHandler.setupConnection(url, user, password);

        // Execute a query
        String query = "SELECT * FROM Cards";
        ResultSet resultSet = dbHandler.executeQuery(query);

        // Process the result set
        Actions act;
        try {
            while (resultSet != null && resultSet.next()) {
                act = new Actions();
                act.code = resultSet.getInt("CardID");
                act.title = resultSet.getString("Title");
                act.cardtype = CardType.valueOf(resultSet.getString("CardType"));
                act.Red = resultSet.getInt("AP");
                act.Green = resultSet.getInt("MP");
                act.Blue = resultSet.getInt("FP");
                act.laze = resultSet.getInt("laze");
                act.addturn = resultSet.getInt("addturn");
                act.size = resultSet.getInt("duration");
                act.Blueenergy = resultSet.getInt("FPCost");
                act.Greenenergy = resultSet.getInt("MPCost");
                act.Number = resultSet.getInt("qty");
                act.description = resultSet.getString("description");
                act.message = resultSet.getString("message");
                cards.add(act);
                System.out.println(cards.get(0).Blueenergy);
                System.out.println("Row: " + resultSet.getString("Title"));
                System.out.println("Row: " + resultSet.getString("CardType"));
                System.out.println("Row: " + resultSet.getString("AP"));
                System.out.println("Row: " + resultSet.getString("MP"));
                System.out.println("Row: " + resultSet.getString("FP"));
                System.out.println("Row: " + resultSet.getString("addturn"));
                System.out.println("Row: " + resultSet.getString("duration"));
                System.out.println("Row: " + resultSet.getString("MPCost"));
                System.out.println("Row: " + resultSet.getString("qty"));
            }
        } catch (SQLException e) {
            System.err.println("Error processing the result set: " + e.getMessage());
        } finally {
            // Close the connection
            // dbHandler.closeConnection();
        }

        // Actions newact = new Actions(3, 0, 4, 0, 0, 2, CardType.GREEN, "Gaming Session", "+2 laze Stacks, Restore 4 Mental Prowess", 2,1, 2, 0, 0);
        // insertCard(newact);
        try {
            // Execute the 'stty size' command
            Process process = Runtime.getRuntime().exec(new String[]{"sh", "-c", "stty size < /dev/tty"});
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String size = reader.readLine();
            reader.close();

            if (size != null) {
                String[] dimensions = size.split(" ");
                ScreenMaxHeight = Integer.parseInt(dimensions[0]); // Height
                ScreenMaxWidth = Integer.parseInt(dimensions[1]); // Width
                ScreenMaxWidth = ScreenMaxWidth > 134 ? 134 : ScreenMaxWidth;

                System.out.println("Terminal Width: " + ScreenMaxWidth);
                System.out.println("Terminal Height: " + ScreenMaxHeight);
            } else {
                System.out.println("Could not determine terminal size.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Screen Width: " + ScreenMaxWidth);
        System.out.println("Screen Height: " + ScreenMaxHeight);

        GameScreen.clearScreen();

        // GameCharacter hero = new GameCharacter(10, 10, '@');
        // hero.draw();
        // GameScreen.printAt(10,10,"String");
        // GameScreen.printAt(0,0,"KLAINE",10);
        // cards.add( new GamingSession());
        // cards.add( new JustOneGame());
        // cards.add( new NaturalGamer());
        // cards.add( new OnTheSide());
        // cards.add( new Procrastinate());
        // cards.add( new AdvancedStudy());
        // cards.add( new LastMinuteReview());
        // cards.add( new JustAPeek());
        // cards.add( new BookReading());
        // cards.add( new ShortNap());
        // cards.add( new HotShower());
        // cards.add( new AllNighter());
        // cards.add( new MusicAndChill());
        // cards.add( new HappyPills());
        // cards.add( new MasterPlanner());
        subjects.add(subject1);
        subjects.add(subject2);
        subjects.add(subject3);
        subjects.add(subject4);

        MainMenu menu = new MainMenu();
        menu.show(); // Display the main menu
    }
}
