package cardgame;

import java.io.IOException;
import java.util.ArrayList;

public class MainMenu {

    int selectedcard = 0;
    public ArrayList<Integer> deck = new ArrayList<>();

    private static final String[] MENU_ITEMS = {
        "New Game    ",
        "Load Game   ",
        "Game History",
        "Exit        "
    };

    private static final String[] MENU_ITEMS_NEWGAME = {
        "New Deck    ",
        "Select Deck ",
        "Exit        "
    };

    private void FrontPage() {
        int r = 25;
        GameScreen.printAt(r++, 20, "        _________ __            .___             __      _________.__           ");
        GameScreen.printAt(r++, 20, "       /   _____//  |_ __ __  __| _/____   _____/  |_   /   _____/|__| _____        ");
        GameScreen.printAt(r++, 20, "       \\_____  \\\\   __\\  |  \\/ __ |/ __ \\ /    \\   __\\  \\_____  \\ |  |/     \\       ");
        GameScreen.printAt(r++, 20, "       /        \\|  | |  |  / /_/ \\  ___/|   |  \\  |    /        \\|  |  Y Y  \\      ");
        GameScreen.printAt(r++, 20, "      /_______  /|__| |____/\\____ |\\___  >___|  /__|   /_______  /|__|__|_|  /      ");
        GameScreen.printAt(r++, 20, "             \\/                 \\/    \\/     \\/               \\/          \\/       ");
        GameScreen.printAt(r++, 20, "_________     _____ __________________      ________    _____      _____  ___________");
        GameScreen.printAt(r++, 20, "\\_   ___ \\   /  _  \\\\______   \\______ \\    /  _____/   /  _  \\    /     \\ \\_   _____/");
        GameScreen.printAt(r++, 20, "/    \\  \\/  /  /_\\  \\|       _/|    |  \\  /   \\  ___  /  /_\\  \\  /  \\ /  \\ |    __)_ ");
        GameScreen.printAt(r++, 20, "\\     \\____/    |    \\    |   \\|    `   \\ \\    \\_\\  \\/    |    \\/    Y    \\|        \\");
        GameScreen.printAt(r++, 20, "\\______  /\\____|__  /____|_  /_______  /  \\______  /\\____|__  /\\____|__  /_______  /");
        GameScreen.printAt(r++, 20, "       \\/         \\/       \\/        \\/          \\/         \\/         \\/        \\/ ");
    }

    public void show() throws IOException {
        enableRawMode(); // Enable raw mode for input
        int selectedIndex = 0;

        FrontPage();
        // GameScreen.printAt(r++,20,"_________     _____ __________________      ________    _____      _____  ___________");
        // GameScreen.printAt(r++,20,"\\_   ___ \\   /  _  \\\\______   \\______ \\    /  _____/   /  _  \\    /     \\ \\_   _____/");
        // GameScreen.printAt(r++,20,"/    \\  \\/  /  /_\\  \\|       _/|    |  \\  /   \\  ___  /  /_\\  \\  /  \\ /  \\ |    __)_ ");
        // GameScreen.printAt(r++,20,"\\     \\____/    |    \\    |   \\|    `   \\ \\    \\_\\  \\/    |    \\/    Y    \\|        \\");
        // GameScreen.printAt(r++,20," \\______  /\\____|__  /____|_  /_______  /  \\______  /\\____|__  /\\____|__  /_______  /");
        // GameScreen.printAt(r++,20,"       \\/         \\/       \\/        \\/          \\/         \\/         \\/        \\/ ");
        // GameScreen.printAt(r++,20,"  _____                _________ __            .___             __                   ");
        // GameScreen.printAt(r++,20,"_/ ____\\___________   /   _____//  |_ __ __  __| _/____   _____/  |_                 ");
        // GameScreen.printAt(r++,20,"\\   __\\/  _ \\_  __ \\  \\_____  \\\\   __\\  |  \\/ __ |/ __ \\ /    \\   __\\                ");
        // GameScreen.printAt(r++,20,"|  | (  <_> )  | \\/  /        \\|  | |  |  / /_/ \\  ___/|   |  \\  |                  ");
        // GameScreen.printAt(r++,20,"|__|  \\____/|__|    /_______  /|__| |____/\\____ |\\___  >___|  /__|                  ");
        // GameScreen.printAt(r++,20,"                            \\/                 \\/    \\/     \\/                      ");

        try {
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
                        selectedIndex = (selectedIndex - 1 + MENU_ITEMS.length) % MENU_ITEMS.length;
                        break;
                    case "\033[B": // Arrow Down
                        selectedIndex = (selectedIndex + 1) % MENU_ITEMS.length;
                        break;
                    case "\n": // Enter key (Unix-style)
                    case "\r": // Enter key (Windows-style)
                        handleSelection(selectedIndex); // Perform action for selected item
                        if (selectedIndex == 3) {
                            return; // Exit menu loop
                        }
                        break;
                    default:
                        GameScreen.printAt(0, 0, "Invalid input: " + input, 15);
                }
            }
        } finally {
            disableRawMode(); // Ensure raw mode is disabled on exit
        }
    }

    private static final int MENU_WIDTH = 20;

    private void renderMenu(int selectedIndex) {
        int row = 10;
        int col = 10;
        GameScreen.drawBox(row, col, MENU_WIDTH + 11, MENU_ITEMS.length + 2, "MAIN MENU");
        col += 2;
        row += 1;
        for (int i = 0; i < MENU_ITEMS.length; i++) {
            if (i == selectedIndex) {
                GameScreen.printAt(i + row, col, Main.HIGHLIGHT + ToolKit.padRight(MENU_ITEMS[i], MENU_WIDTH) + Main.RESET);
            } else {
                GameScreen.printAt(i + row, col, ToolKit.padRight(MENU_ITEMS[i], MENU_WIDTH));
            }
        }
    }

    private void handleSelection(int selectedIndex) throws IOException {
        GameScreen.printAt(1, 20, "\nYou selected: " + MENU_ITEMS[selectedIndex], 30);

        switch (selectedIndex) {
            case 0: // "New Game ":
                GameScreen.printAt(0, 0, "Starting a new game...", 30);
                MenuDeck.show();
                FrontPage();
                break;
            case 1: // "Load Game ":
                // GameScreen.printAt(0, 0, "Loading a game...", 20);
                break;
            case 2: // "Game History":
                // GameScreen.printAt(0, 0, "Displaying game history...", 30);
                break;
            case 3: // "Exit ":
                GameScreen.printAt(0, 0, "Exiting...", 30);
                return;
        }
    }

    private void enableRawMode() throws IOException {
        try {
            Runtime.getRuntime().exec(new String[]{"sh", "-c", "stty raw < /dev/tty"}).waitFor();
        } catch (InterruptedException e) {
            throw new IOException("Failed to enable raw mode", e);
        }
    }

    private void disableRawMode() throws IOException {
        try {
            Runtime.getRuntime().exec(new String[]{"sh", "-c", "stty sane < /dev/tty"}).waitFor();
        } catch (InterruptedException e) {
            throw new IOException("Failed to disable raw mode", e);
        }
    }
}

//     private static void newdeckMenu() {
//         Scanner input = new Scanner(System.in);
//         System.out.print("   |Input Deck Name: ");
//         String deckname = input.nextLine();
//         CardList.allCards();
//         formatMenu(deckname);
//         addCards(deckname);
//     }
//     private static void addCards(String deckname) {
//         ArrayList<Integer> deck = new ArrayList<>();
//         System.out.print(
//                 """
//                         -----------Add Cards-----------
//                         --------(0)Remove Cards--------
//                         ---------(-1)Save Deck---------
//                         ----------(-2)Cancel-----------
//                         """);
//         while (true) {
//             System.out.print("   |Input here: ");
//             Scanner input = new Scanner(System.in);
//             if (input.hasNextInt()) {
//                 int choice = input.nextInt();
//                 if (choice > 0 && choice < 10) {
//                     deck.add(choice);
//                 } else {
//                     switch (choice) {
//                         case 0 -> {
//                             input.close();
//                             removeCards(deckname, deck);
//                         }
//                         case -1 -> {
//                             input.close();
//                             Decks.addDeck(deckname, deck);
//                         }
//                         case -2 -> {
//                             input.close();
//                             newdeckMenu();
//                         }
//                         default -> {
//                             System.out.println(ConsoleColors.RED + "Invalid input! Please choose from the given options"
//                                     + ConsoleColors.Main.RESET);
//                         }
//                     }
//                 }
//             } else {
//                 System.out.println("Error, Input must be an integer!");
//             }
//         }
//     }
//     private static void removeCards(String deckname, ArrayList<Integer> deck) {
//         formatMenu("Remove Cards");
//         System.out.print("""
//                 ---------Remove Cards---------
//                 ---------(0)Add Cards---------
//                 --------(-1)Save Deck---------
//                 ---------(-2)Cancel-----------
//                    """);
//         while (true) {
//             System.out.print("   |Input here: ");
//             Scanner input = new Scanner(System.in);
//             if (input.hasNextInt()) {
//                 int choice = input.nextInt();
//                 if (choice > 0 && choice < 10) {
//                     deck.remove(Integer.valueOf(choice));
//                 } else {
//                     switch (choice) {
//                         case 0 ->
//                             addCards(deckname);
//                         case -1 -> {
//                             input.close();
//                             Decks.addDeck(deckname, deck);
//                         }
//                         case -2 ->
//                             addCards(deckname);
//                         default -> {
//                             System.out.println(ConsoleColors.RED + "Invalid input! Please choose from the given options"
//                                     + ConsoleColors.Main.RESET);
//                         }
//                     }
//                 }
//             } else {
//                 System.out.println("Error, Input must be an integer!");
//             }
//         }
//     }
//     private static void formatMenu(String menutitle) {
//         int i = (int) Math.ceil((30 - menutitle.length()) / 2.0);
//         for (int j = 0; j <= 2; j++) {
//             while (i > 0) {
//                 i--;
//                 System.out.print("-");
//             }
//             i = (30 - menutitle.length()) / 2;
//         }
//         System.out.println(" ");
//     }
// }
// ----------Main Menu----------
// --------(1)New Game----------
// -------(2)Load Game----------
// ------(3)Game History--------
// Input Here:
// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//
// ________________________________________________________________________________________________
// |Enter (x) to exit
// |
// |
// |
// |
// |
// |Academic Points:
// |MP 10|----------|
// |FP 10|----------| Progress 10|----------| Progress 10|----------|
// | ________ ___________
// | | | | | |
// | |______| | | /| |
// | ___||___ | | | |
// | ||| ||| | | --- |
// | |||__||| |_|_________|
// | |||| |___________|
// | ||||
// |_______________________________________________________________________________________________
// | Actions |
// |you dealt damage |
// |subject 1 used an ability |
// |you took damage |
// |you played a card |
// |___________________________________________|
// | --------------–
// | |Card title |
// | | |
// | |-------------- |
// | |This card acts |
// | |by using this |
// | |focus and stu- |
// | |ff |
// | | |
// | |n fp/ n mp |
// | ---------------
// | (1)
// |Input Here:
// |_______________________________________________________________________________________________
