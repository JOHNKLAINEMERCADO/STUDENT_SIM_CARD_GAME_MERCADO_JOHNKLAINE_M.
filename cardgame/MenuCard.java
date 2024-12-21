package cardgame;

import java.io.IOException;

public class MenuCard {

    static int selectedcard = 0;

    private static final String[] MENU_ITEMS_CARD_SELECT = {
        " << ",
        " + ",
        " - ",
        " >> ",
        " Main Menu ",
        " Start Game "
    };

    private static final String[] MENU_ITEMS_CARD_SELECT_DESC = {
        " Show Previous Card  ",
        " Add Card            ",
        " Remove from Deck    ",
        " Show Next Card      ",
        " Goto Main Menu      ",
        " Start your game     ",};

    public static void renderMenuAddCard(int selectedIndex) {
        // GameScreen.printAt(0, 0, "start renderMenuAddCard", 30);
        int iPrev = selectedcard - 1;
        int iNext = selectedcard + 1;
        int card;
        if (selectedcard == 0) {
            iPrev = Main.cards.size() - 1;
        }
        if (selectedcard == Main.cards.size() - 1) {
            iNext = 0;
        }
        Main.cards.get(iPrev).drawCard(3, 20, 12);
        Main.cards.get(selectedcard).drawCard(1, 57, 14, 2);
        Main.cards.get(iNext).drawCard(3, 94, 12);

        GameScreen.printAt(8, 46, "<-");
        GameScreen.printAt(8, 86, "->");

        GameScreen.printAt(1, 1, String.valueOf(selectedcard));
        int nextmargin = 3;
        int downmargin = 23;
        for (int i = 0; i < Main.avatar.drawpile.size(); i++) {
            Main.cards.get(Main.avatar.drawpile.get(i)).drawCard(downmargin, nextmargin, 12);
            nextmargin += 27;
            if (i == 4) {
                nextmargin = 3;
                downmargin += 15;
            }
        }
        for (int i = Main.avatar.drawpile.size(); i < 10; i++) {
            for (int l = 0; l < 12; l++) {
                GameScreen.printAt(downmargin + l, nextmargin, " ".repeat(20));
            }
            nextmargin += 27;
            if (i == 4) {
                nextmargin = 3;
                downmargin += 15;
            }
        }

        int l = 15;
        int c = 58;
        // GameScreen.printAt(l+3, 1, "_".repeat(ScreenMaxWidth-2));

        for (int i = 0; i < MENU_ITEMS_CARD_SELECT.length - 2; i++) {
            if (i == selectedIndex) {
                GameScreen.printAt(l, c, Main.HIGHLIGHT + MENU_ITEMS_CARD_SELECT[i] + Main.RESET, 30);
            } else {
                GameScreen.printAt(l, c, MENU_ITEMS_CARD_SELECT[i], 30);
            }
            c = c + MENU_ITEMS_CARD_SELECT[i].length() + 1;
        }

        c = 55;
        GameScreen.printAt(l + 2, c, MENU_ITEMS_CARD_SELECT_DESC[selectedIndex], 30, ConsoleColors.WHITE, ConsoleColors.BLACK_BACKGROUND);
        l++;
        for (int i = MENU_ITEMS_CARD_SELECT.length - 2; i < MENU_ITEMS_CARD_SELECT.length; i++) {
            if (i == selectedIndex) {
                GameScreen.printAt(l, c, Main.HIGHLIGHT + MENU_ITEMS_CARD_SELECT[i] + Main.RESET, 30);
            } else {
                GameScreen.printAt(l, c, MENU_ITEMS_CARD_SELECT[i], 30);
            }
            c = c + MENU_ITEMS_CARD_SELECT[i].length() + 1;
        }

        GameScreen.drawLine(l + 4);
        GameScreen.printAtCenter(l + 4, "| [ " + Main.DeckName + " ] |");
    }

    private static void handleSelectionAddCard(int selectedIndex) throws IOException {
        int maxqty = Main.cards.get(selectedcard).Number;
        int cardqty = Main.cards.get(selectedcard).qty;

        switch (selectedIndex) {
            case 0: // "Previous":
                // GameScreen.printAt(70, 0, "ljkasj dlfkjsdlakfj lskdj fsd");
                // newgameMenu();
                selectedcard--;
                if (selectedcard < 0) {
                    selectedcard = Main.cards.size() - 1;
                }
                // newgameMenu();
                break;
            case 1: // "Add Card":
                // GameScreen.printAt(0, 0, MENU_ITEMS_CARD_SELECT[selectedIndex], 20);
                if (Main.avatar.add2DrawPile(selectedcard)) {                    
                    //GameScreen.printAt(18, 90, "Max quantity of copies has been added!");
                } else {
                    GameScreen.MessageBoxOK("Max quantity of copies has been added! press (enter) to confirm");
                }
                break;
            case 2: // "Remove card":
                // GameScreen.printAt(0, 0, MENU_ITEMS_CARD_SELECT[selectedIndex], 30);
                if (Main.avatar.removeFromPile(Main.cards.get(selectedcard))) {
                    Main.cards.get(selectedcard).addQty(-1);
                    //GameScreen.printAt(18, 90, "Max quantity of copies has been added!");
                } else {
                    GameScreen.printAt(18, 90, "No selected copies to remove! press (enter) to confirm");
                }
                break;
            case 3: // "Next Card":
                // GameScreen.printAt(0, 0,MENU_ITEMS_CARD_SELECT[selectedIndex], 30);
                selectedcard++;
                if (selectedcard >= Main.cards.size()) {
                    selectedcard = 0;
                }
                break;
            case 5: // Start Game
                if (Main.avatar.drawpile.size() < 10) {
                    GameScreen.MessageBoxOK("Please complete 10 cards");
                    break;
                }

                //Main.saveDeck(Main.avatar.drawpile, deckname);
                String msg;
                msg = Main.insertDeck(Main.avatar.drawpile); 
                
                if (!"".equals(msg)) {
                    GameScreen.MessageBoxOK(msg);
                }
                Decks.refresh();
                Main.avatar.DrawFirst5();            
                MenuPlay.play();
                break;
            // case 5: // "Main Menu":
            // // GameScreen.printAt(40, 0, MENU_ITEMS_CARD_SELECT[selectedIndex], 30);
            // GameScreen.clearScreen();
            // show();
            // break;
            // if(Main.avatar.drawpile.size() < 8){}
            // Display Selected Avatar
            }
        for (int x = 0; x < Main.avatar.drawpile.size(); x++) {
            GameScreen.printAt(x, 140, String.valueOf(Main.avatar.drawpile.get(x)));

        }
        GameScreen.printAt(1, 0, "\nYou selected: " + MENU_ITEMS_CARD_SELECT[selectedIndex] + "->" + String.valueOf(selectedcard), 30);

    }

    public static void newgameMenu() throws IOException {
        GameScreen.clearScreen();
        // GameScreen.printAt(3, 0, "newgameMenu", 30);
        int selectedIndex = 0;

        while (true) {
            MenuCard.renderMenuAddCard(selectedIndex);

            // Capture raw input
            String input = ToolKit.readRawInput();

            // Handle Ctrl + X for termination
            if (input.equals("\u0018")) { // ASCII for Ctrl + X
                GameScreen.clearScreen();
                return;
            }

            // Handle input cases
            switch (input) {
                case "\033[A": // Arrow Up
                    selectedIndex = (selectedIndex - 1 + MENU_ITEMS_CARD_SELECT.length) % MENU_ITEMS_CARD_SELECT.length;
                    break;
                case "\033[B": // Arrow Down
                    selectedIndex = (selectedIndex + 1) % MENU_ITEMS_CARD_SELECT.length;
                    break;
                case "\033[C": // Arrow Right
                    selectedIndex = (selectedIndex + 1) % MENU_ITEMS_CARD_SELECT.length;
                    break;
                case "\033[D": // Arrow Left
                    selectedIndex = (selectedIndex - 1 + MENU_ITEMS_CARD_SELECT.length) % MENU_ITEMS_CARD_SELECT.length;
                    break;
                case "\n": // Enter key (Unix-style)
                case "\r": // Enter key (Windows-style)
                    handleSelectionAddCard(selectedIndex); // Perform action for selected item
                    if (selectedIndex == 4) {
                        GameScreen.clearScreen();
                        return; // Exit menu loop
                    }
                    break;
                default:
                    GameScreen.printAt(0, 0, "Invalid input: " + input, 15);
            }
        }
    }
}
