package Budget.UI;

import javax.swing.*;
import java.awt.*;

public class SpendingBase extends JPanel {

    JFrame topLevelFrame; // top-level JFrame
    GridBagConstraints layoutConstraints = new GridBagConstraints(); // used to control layout

    public JTextField foodField;
    public JTextField rentField;
    public JTextField transportField;
    public JTextField totalSpendField;
    public JComboBox<String> durationBox;

    public SpendingBase(JFrame frame) {
        topLevelFrame = frame; // keep track of top-level frame
        setLayout(new GridBagLayout()); // use GridBag layout
        initComponents(); // initalise components
    }

    private void initComponents() {
        // Top row (0) - "INCOME" label
        JLabel spendingLabel = new JLabel("SPENDING");
        layoutConstraints.gridwidth = 2;
        addComponent(spendingLabel, 1, 1);

        // Row 1 - Wages label followed by wages textbox
        JLabel foodLabel = new JLabel("Food");
        addComponent(foodLabel, 2, 1);

        // set up text field for entering wages
        // Could create method to do below (since this is done several times)
        foodField = new JTextField("", 10); // blank initially, with 10 columns
        foodField.setHorizontalAlignment(JTextField.RIGHT); // number is at right end of field
        addComponent(foodField, 2, 3);

        // Row 1 - Wages label followed by wages textbox
        JLabel rentLabel = new JLabel("Rent");
        addComponent(rentLabel, 3, 1);

        // set up text field for entering wages
        // Could create method to do below (since this is done several times)
        rentField = new JTextField("", 10); // blank initially, with 10 columns
        rentField.setHorizontalAlignment(JTextField.RIGHT); // number is at right end of field
        addComponent(rentField, 3, 3);

        // Row 1 - Wages label followed by wages textbox
        JLabel transportLabel = new JLabel("Transport");
        addComponent(transportLabel, 4, 1);

        // set up text field for entering wages
        // Could create method to do below (since this is done several times)
        transportField = new JTextField("", 10); // blank initially, with 10 columns
        transportField.setHorizontalAlignment(JTextField.RIGHT); // number is at right end of field
        addComponent(transportField, 4, 3);

        JLabel durationLabel = new JLabel("duration");
        addComponent(durationLabel, 5, 2);

        // blank initially, with 10 columns
        durationBox = new JComboBox<>(new String[] { "Per Week", "Per Month", "Per Year" }); // number is at right end
                                                                                             // // // // // of field
        addComponent(durationBox, 5, 3);

        // Row 3 - Total Income label followed by total income field
        JLabel totalSpendingLabel = new JLabel("Total Spend");
        addComponent(totalSpendingLabel, 6, 1);

        // set up text box for displaying total income. Users cam view, but cannot
        // directly edit it
        totalSpendField = new JTextField("0", 10); // 0 initially, with 10 columns
        totalSpendField.setHorizontalAlignment(JTextField.RIGHT); // number is at right end of field
        totalSpendField.setEditable(false); // user cannot directly edit this field (ie, it is read-only)
        addComponent(totalSpendField, 6, 3);
    }

    public void addLabelandField(String labelText, int row, JTextField field) {
        JLabel label = new JLabel(labelText);
        addComponent(label, row, 0);

        field.setHorizontalAlignment(JTextField.RIGHT);
        addComponent(field, row, 1);
    }

    public double calculateTotalSpend() {
        double food = getTextFieldValue(foodField);
        double rent = getTextFieldValue(rentField);
        double transport = getTextFieldValue(transportField);

        // clear total field and return if any value is NaN (error)
        if (Double.isNaN(food) || Double.isNaN(rent) || Double.isNaN(transport)) {
            totalSpendField.setText(""); // clear total income field
            return 0.0; // exit method and do nothing
        }

        double totalSpend = food + rent + transport;
        totalSpendField.setText(String.format("%.2f", totalSpend)); // format with 2 digits after the .
        return adjustDuration(totalSpend, (String) durationBox.getSelectedItem());

    }

    private double getTextFieldValue(JTextField field) {

        // get value as String from field
        String fieldString = field.getText(); // get text from text field

        if (fieldString.isBlank()) { // if text field is blank, return 0
            return 0;
        }

        else { // if text field is not blank, parse it into a double
            try {
                return Double.parseDouble(fieldString); // parse field number into a double
            } catch (java.lang.NumberFormatException ex) { // catch invalid number exception
                JOptionPane.showMessageDialog(topLevelFrame, "Please enter a valid number"); // show error message
                return Double.NaN; // return NaN to show that field is not a number
            }
        }
    }

    private double adjustDuration(double value, String duration) {
        switch (duration) {
            case "Per Year":
                return value * 52;
            case "Per Month":
                return value * 4.3333333;
            default:
                return value;
        }
    }

    private void addComponent(Component component, int gridrow, int gridcol) {
        layoutConstraints.fill = GridBagConstraints.HORIZONTAL; // always use horixontsl filll
        layoutConstraints.gridx = gridcol;
        layoutConstraints.gridy = gridrow;
        add(component, layoutConstraints);
    }

}
