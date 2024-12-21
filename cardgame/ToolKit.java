package cardgame;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class ToolKit {
    // Helper method to pad a string with spaces to the specified length

    static String padRight(String text, int length) {
        if (text.length() < length) {
            return text + " ".repeat(length - text.length());
        }
        return text;
    }

    public static String centerString(int totalWidth, String text) {
        int padding = (totalWidth - text.length()) / 2;
        String centeredText = String.format("%" + padding + "s%s%" + padding + "s", "", text, "");
        return centeredText;
    }

    public static ArrayList<Integer> generateRandomNumbers(int size, int min, int max) {
        ArrayList<Integer> numbers = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            int randomNumber = random.nextInt(max - min + 1) + min;
            numbers.add(randomNumber);
        }

        return numbers;
    }

    public static String readRawInput() throws IOException {
        StringBuilder input = new StringBuilder();

        // Read the first character
        char firstChar = (char) System.in.read();
        input.append(firstChar);

        // If it's an ESC sequence, read the next two characters
        if (firstChar == '\033') { // ESC
            input.append((char) System.in.read());
            input.append((char) System.in.read());
        }

        // Debug log to see raw input
        // System.out.println("Raw Input Captured: " + input + " (ASCII: " + (int)
        // firstChar + ")");
        return input.toString();
    }

    /**
     * Displays a text input dialog with a label, reversed color scheme, and
     * validation.
     *
     * @param label the label for the input field
     * @param maxLength the maximum allowed length for the input
     * @return the entered string, or null if the user cancels the operation
     */
    public static String getTextInput(String label, int maxLength) {
        // Create a panel to hold components
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(5, 5));

        // Create and configure the label
        JLabel inputLabel = new JLabel(label);
        panel.add(inputLabel, BorderLayout.NORTH);

        // Create and configure the text field with reversed colors
        JTextField textField = new JTextField();
        textField.setBackground(Color.BLACK); // Reversed background color
        textField.setForeground(Color.WHITE); // Reversed text color
        textField.setCaretColor(Color.WHITE); // Caret color matches text color

        // Limit the text field to the specified maximum length
        textField.setDocument(new javax.swing.text.PlainDocument() {
            @Override
            public void insertString(int offs, String str, javax.swing.text.AttributeSet a) throws javax.swing.text.BadLocationException {
                if (str == null || (getLength() + str.length()) > maxLength) {
                    Toolkit.getDefaultToolkit().beep(); // Sound a beep if the input exceeds maxLength
                    return;
                }
                super.insertString(offs, str, a);
            }
        });

        panel.add(textField, BorderLayout.CENTER);

        // Show an input dialog with the panel
        int result = JOptionPane.showConfirmDialog(null, panel, "Input Required",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            return textField.getText(); // Return the input text
        }
        return null; // Return null if the operation was canceled
    }
}
