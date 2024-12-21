package cardgame;

import java.io.IOException;

public class MenuPlay {

    static int selectedcard = 0;
    static int selectedcardHand = 0;
    
    private static final String[] MENU_ITEMS_PLAY = {
        " << ",
        " PLAY CARD ",
        " END TURN ",
        " >> ",
        " EXIT GAME "
    };

    public static void play() throws IOException {
        GameScreen.clearScreen();
        // GameScreen.printAt(3, 0, "newgameMenu", 30);
        int selectedIndex = 0;

        while (true) {
            renderPlay(selectedIndex);

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
                    selectedIndex = (selectedIndex - 1 + MENU_ITEMS_PLAY.length) % MENU_ITEMS_PLAY.length;
                    break;
                case "\033[B": // Arrow Down
                    selectedIndex = (selectedIndex + 1) % MENU_ITEMS_PLAY.length;
                    break;
                case "\033[C": // Arrow Right
                    selectedIndex = (selectedIndex + 1) % MENU_ITEMS_PLAY.length;
                    break;
                case "\033[D": // Arrow Left
                    selectedIndex = (selectedIndex - 1 + MENU_ITEMS_PLAY.length) % MENU_ITEMS_PLAY.length;
                    break;
                case "\n": // Enter key (Unix-style)
                case "\r": // Enter key (Windows-style)
                    handleSelectionPlay(selectedIndex); // Perform action for selected item
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


    private static void renderPlay(int selectedIndex) throws IOException {
        GameScreen.clearScreen();
        GameScreen.drawLine(26);
        // GameScreen.printAt(l+3, 1, "_".repeat(ScreenMaxWidth-2));

        int colStartTime = Main.ScreenMaxWidth - 27 * 3;

        if (Main.currentTime > Main.avatar.getMaxTurns()) {
            //end of day
            for (Subject subject : Main.subjects.subList(0, Main.currentDay)) {
                if (subject.getItems() > 0) {
                    Main.avatar.useAP(subject);
                    if (subject.getItems() > 0) {
                        subject.induceAnxiety();
                        if(Main.avatar.getMP() < 0){
                        String error;
                            error  = "You're too stressed to act, YOU LOST";
                            GameScreen.MessageBoxOK(error);
                        }
                    }
                } 
            }
            Main.avatar.academicpoints = 0;
        
            Main.currentTime = Main.starttime;
            Main.currentDay++;
            Main.avatar.refreshstats();
            if(Main.currentDay > 4){
                for(int i = 0; i >= 4; i++){
                    if (Main.subjects.get(i).items > 0) {
                        String error;
                        error  = "Your project is past due, YOU LOST";
                        GameScreen.MessageBoxOK(error);
                    }
                }
                GameScreen.MessageBoxOK("You submitted all projects in time, You Won!");
            }
            
            for (Subject subject : Main.subjects.subList(0, Main.currentDay)) {
                //subject at the start of day
                if (subject.getItems() > 0) {
                    subject.performAction(Main.avatar);
                } 
            }

            // for (int i = 0; i <= Main.currentDay; i++) {
            //     if (Main.subjects.get(i).items > 0) {
            //         if (Main.avatar.useAP(Main.subjects.get(i))) {
            //             i = Main.currentDay;
            //         }
            //     }
            // }
            //Main.avatar.refreshstats();
        }
        GameScreen.drawDay(1, colStartTime + Main.currentTime * 3 - 3, Main.currentDay);

        for (int i = 1; i <= 24; i++) {
            GameScreen.printAt(2, colStartTime + i * 3 + 3, String.valueOf((i)));
        }
// Avatar
        GameScreen.drawAvatar(4, 8);
        GameScreen.printAt(5, 35, "Academic");
        GameScreen.printAt(6, 35, " Points");
        GameScreen.printAt(7, 37, String.valueOf(Main.avatar.academicpoints));
        GameScreen.progressBar(15, 17, "MP", Main.avatar.mentalprowess, 10);
        GameScreen.progressBar(18, 17, "FP", Main.avatar.focuspoints, 10);

// Books
        int j = 1;
        for (Subject subject : Main.subjects.subList(0, Main.currentDay)) {
            if (subject.getItems() <= 0) {
            } else {
                GameScreen.drawBook(12, colStartTime + 18 * j - 18, subject.subjectnum, subject);
                j++;
            }
        }

        int nextmargin = 3;
        int downmargin = 27;

        // Clear CARD Text
        for (int i = 0; i < 25; i++) {
            GameScreen.printAt(downmargin + i, nextmargin, " ".repeat(120));
        }

        for (int i = 0; i < Main.avatar.hand.size(); i++) {
            if (selectedcardHand != i) {
                Main.cards.get(Main.avatar.hand.get(i)).drawCard(downmargin + 3, nextmargin, 12);
                nextmargin += 5;
            } else {
                nextmargin += 15;
            }
            downmargin += 1;
        }
        if (!Main.avatar.hand.isEmpty()) {
            Main.cards.get(Main.avatar.hand.get(selectedcardHand)).drawCard(27, 3 + selectedcardHand * 5, 14);
        }

        GameScreen.drawLine(Main.ScreenMaxHeight-3);

// Menu
        int l = Main.ScreenMaxHeight - 2;
        int c = 58;

        for (int i = 0; i < MENU_ITEMS_PLAY.length; i++) {
            if (i == selectedIndex) {
                GameScreen.printAt(l, c, Main.HIGHLIGHT + MENU_ITEMS_PLAY[i] + Main.RESET, 30);
            } else {
                GameScreen.printAt(l, c, MENU_ITEMS_PLAY[i], 30);
            }
            c = c + MENU_ITEMS_PLAY[i].length() + 1;
        }

// History
        GameScreen.drawBox(28, Main.ScreenMaxWidth-65, 64, 20, "Action History");
        l = 30;
        for (int i = Main.actionhistory.size()-1; i >= 0; i--) {
            GameScreen.printAt(l++, Main.ScreenMaxWidth-63, Main.actionhistory.get(i));
            if (i < Main.actionhistory.size()-15) {
                break;
            }
            
        }

    }

    private static void handleSelectionPlay(int selectedIndex) throws IOException {
        int maxqty = Main.cards.get(selectedcardHand).Number;
        int cardqty = Main.cards.get(selectedcardHand).qty;
        String error = "";
        switch (selectedIndex) {
            case 0: // "Previous":
                // newgameMenu();
                selectedcardHand--;
                if (selectedcardHand < 0) {
                    selectedcardHand = Main.avatar.hand.size() - 1;
                }
                // newgameMenu();
                break;
            case 1: // "Play Card:
                // GameScreen.printAt(0, 0, MENU_ITEMS_CARD_SELECT[selectedIndex], 20);
                error = Main.avatar.DropCard(selectedcardHand);
                if (!"".equals(error)) {
                    GameScreen.MessageBoxOK(error);
                }
                selectedcardHand = 0;

                break;
            case 2: // "End Turn":
                Main.currentTime += 1;
                Main.avatar.addFP(1);

                error = Main.avatar.GetCard();
                if (!"".equals(error)) {
                    GameScreen.MessageBoxOK(error);
                }
                break;
            case 3: // "Next Card":
                // GameScreen.printAt(0, 0,MENU_ITEMS_CARD_SELECT[selectedIndex], 30);
                selectedcardHand++;
                if (selectedcardHand >= Main.avatar.hand.size()) {
                    selectedcardHand = 0;
                }
                break;
            case 5: // Start Game
                play();
                break;
            // case 5: // "Main Menu":
            // // GameScreen.printAt(40, 0, MENU_ITEMS_CARD_SELECT[selectedIndex], 30);
            // GameScreen.clearScreen();
            // show();
            // break;
            // if(Main.avatar.drawpile.size() < 8){}
            // Display Selected Avatar
            }
        for (int x = 0; x < 20; x++) {
            GameScreen.printAt(x, 140, "   ");
            GameScreen.printAt(x, 150, "   ");
        }
        for (int x = 0; x < Main.avatar.ofhand.size(); x++) {
            GameScreen.printAt(x, 140, String.valueOf(Main.avatar.ofhand.get(x)));
        }
        for (int x = 0; x < Main.avatar.hand.size(); x++) {
            GameScreen.printAt(x, 150, String.valueOf(Main.avatar.hand.get(x)));
        }
        // GameScreen.printAt(1, 0, "\nYou selected: " + MENU_ITEMS_CARD_SELECT[selectedIndex] + "->" + String.valueOf(selectedcard), 30);
    }
}