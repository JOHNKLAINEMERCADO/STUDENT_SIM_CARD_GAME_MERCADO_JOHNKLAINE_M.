package cardgame;

import java.io.IOException;
import java.util.ArrayList;

public class MenuDeck {

    int selectedcard = 0;

    private static ArrayList<String> menuitems;

    public static void show() throws IOException {
        Main.decks = new Decks();
        menuitems = new ArrayList();
        Main.decks.setDbhandler(Main.dbHandler);
        Main.decks.refresh();

// Deck Name
        for (int i = 0; i < Main.decks.count(); i++) {
            menuitems.add(String.valueOf(i+1) + ". " + Main.decks.getDeck(i).getDeckName());
        }

// add new Deck upto 10
        for (int i = Main.decks.count(); i < 10; i++) {
            menuitems.add(String.valueOf(i+1) + ". Define New Deck");
        }
        menuitems.add("Back to Main Menu");

        int selectedIndex = 0;
        while (true) {
            // GameScreen.clearScreen();
            renderMenu(selectedIndex);

            // Capture raw input
            String input = ToolKit.readRawInput();

            // Handle Ctrl + X for termination
            if (input.equals("\u0018")) { // ASCII for Ctrl + X
                GameScreen.printAt(0, 0, "Exiting with Ctrl + X...", 30);
                break;
            }

            // Handle input cases
            switch (input) {
                case "\033[A": // Arrow Up
                    selectedIndex = (selectedIndex - 1 + menuitems.size()) % menuitems.size();
                    break;
                case "\033[B": // Arrow Down
                    selectedIndex = (selectedIndex + 1) % menuitems.size();
                    break;
                case "\n": // Enter key (Unix-style)
                case "\r": // Enter key (Windows-style)
                    // handleSelection(selectedIndex); // Perform action for selected item
                    if (selectedIndex == 10) {
                        return; // Exit menu loop
                    }
                    String deckname;
                    Main.avatar = new Avatar();
                    int r = 0;
                    Main.decks.setDeck(selectedIndex);
                    // Get the Decks from Card
                    if (selectedIndex <= Main.decks.count()-1) {
                        Main.DeckName = Main.decks.DeckName();
                        deckname = Main.DeckName;
                        for (int i = 0; i < Main.decks.getDeck().getCards().size(); i++) {
                            Main.avatar.add2DrawPile(Main.decks.ActionsCardIndex(Main.decks.getDeck().getAction(i)));
                            r++;
                            // GameScreen.printAt(60+r, 80, Decks.ActionsCard(i).title);
                        }
                    } else {
                        deckname = ToolKit.getTextInput("Enter Deck Name.:", 20);
                        if (deckname==null) {
                            deckname="";
                        } else {
                            if (!"".equals(Main.isExistDeck(deckname))) {
                                GameScreen.MessageBoxOK("[" + deckname +"] Name Already Exists.");
                                break;
                            }
                        }
                        Main.DeckName = deckname;
                    }
                    if (!"".equals(deckname)) {
                        MenuCard.newgameMenu();
                    }
                    break;
                default:
                    GameScreen.printAt(0, 0, "Invalid input: " + input, 15);
            }
        }
    }

    private static final int MENU_WIDTH = 20;

    private static void renderMenu(int selectedIndex) {
        int row = 10;
        int col = 60;
        GameScreen.drawBox(row, col, MENU_WIDTH + 11, menuitems.size() + 2, "MAIN MENU");
        col += 2;
        row += 1;
        for (int i = 0; i < menuitems.size(); i++) {
            if (i == selectedIndex) {
                GameScreen.printAt(i + row, col, Main.HIGHLIGHT + ToolKit.padRight(menuitems.get(i), MENU_WIDTH) + Main.RESET);
            } else {
                GameScreen.printAt(i + row, col, ToolKit.padRight(menuitems.get(i), MENU_WIDTH));
            }
        }
    }

    // private static void handleSelection(int selectedIndex) throws IOException {
    //     GameScreen.printAt(1, 20, "\nYou selected: " + menuitems.get(selectedIndex), 30);

    //     // switch (selectedIndex) {
    //     //     case 0: // "New Deck":
    //     //         GameScreen.printAt(0, 0, "Starting a new game...", 30);
    //     //         MenuCard.newgameMenu();
    //     //         break;
    //     //     case 1: // "Select Deck":
    //     //         break;
    //     //     case 2: // "Exit":
    //     //         return;
    //     // }
    // }
}
