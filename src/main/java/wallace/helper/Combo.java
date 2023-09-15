package wallace.helper;

import javafx.scene.control.ComboBox;

/**
 * Helper class to add a specified range of numbers to a combo box
 */
public class Combo {
    /**
     * Populates a combo-box with a range of numbers
     * @param comboBox the combo-box to add number to
     * @param startIndex the starting index
     * @param endIndex the ending index
     */
    public static void addNumbersToComboBox(ComboBox<String> comboBox, int startIndex, int endIndex) {
        for (int i = startIndex; i <= endIndex; i++) {
            String hour = String.format("%02d", i);
            comboBox.getItems().add(hour);
        }
    }
}
