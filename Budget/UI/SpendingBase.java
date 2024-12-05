package Budget.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;

public class SpendingBase extends JPanel {

    JFrame topLevelFrame; // top-level JFrame
    GridBagConstraints layoutConstraints = new GridBagConstraints(); // used to control layout

    public JTextField foodField; // Food text field
    public JComboBox<String> foodDurationBox; // Food duration selection

    public JTextField rentField; // Rent text field
    public JComboBox<String> rentDurationBox; // Rent duration selection

    public JTextField transportField; // Transport text field
    public JComboBox<String> transportDurationBox; // Transport duration selection

    public JTextField totalSpendField;

    public SpendingBase(JFrame frame) {
        topLevelFrame = frame; // keep track of top-level frame
        setLayout(new GridBagLayout()); // use GridBag layout
        initSpendingComponents(); // initalise components
        addListeners();
    }

    private void initSpendingComponents() {
        String[] durations = { "Per Week", "Per Month", "Per Year" };
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

        foodDurationBox = new JComboBox<>(durations);
        addComponent(foodDurationBox, 2, 6);

        // Row 1 - Wages label followed by wages textbox
        JLabel rentLabel = new JLabel("Rent");
        addComponent(rentLabel, 3, 1);

        // set up text field for entering wages
        // Could create method to do below (since this is done several times)
        rentField = new JTextField("", 10); // blank initially, with 10 columns
        rentField.setHorizontalAlignment(JTextField.RIGHT); // number is at right end of field
        addComponent(rentField, 3, 3);

        rentDurationBox = new JComboBox<>(durations);
        addComponent(rentDurationBox, 3, 6);

        // Row 1 - Wages label followed by wages textbox
        JLabel transportLabel = new JLabel("Transport");
        addComponent(transportLabel, 4, 1);

        // set up text field for entering wages
        // Could create method to do below (since this is done several times)
        transportField = new JTextField("", 10); // blank initially, with 10 columns
        transportField.setHorizontalAlignment(JTextField.RIGHT); // number is at right end of field
        addComponent(transportField, 4, 3);

        transportDurationBox = new JComboBox<>(durations);
        addComponent(transportDurationBox, 4, 6);

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

    private void addListeners() {
        FocusListener focusListener = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                calculateTotalSpend();
            }
        };

        foodField.addFocusListener(focusListener);
        rentField.addFocusListener(focusListener);
        transportField.addFocusListener(focusListener);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateTotalSpend();
            }
        };

        foodDurationBox.addActionListener(actionListener);
        rentDurationBox.addActionListener(actionListener);
        transportDurationBox.addActionListener(actionListener);
    }

    public void addLabelandField(String labelText, int row, JTextField field) {
        JLabel label = new JLabel(labelText);
        addComponent(label, row, 0);

        field.setHorizontalAlignment(JTextField.RIGHT);
        addComponent(field, row, 1);
    }

    public double calculateTotalSpend() {
        double food = adjustDuration(getTextFieldValue(foodField), (String) foodDurationBox.getSelectedItem());
        double rent = adjustDuration(getTextFieldValue(rentField), (String) rentDurationBox.getSelectedItem());
        double transport = adjustDuration(getTextFieldValue(transportField),
                (String) transportDurationBox.getSelectedItem());

        // clear total field and return if any value is NaN (error)
        if (Double.isNaN(food) || Double.isNaN(rent) || Double.isNaN(transport)) {
            totalSpendField.setText(""); // clear total income field
            return 0.0; // exit method and do nothing
        }

        double totalSpend = food + rent + transport;
        totalSpendField.setText(String.format("%.2f", totalSpend)); // format with 2 digits after the .
        return (totalSpend);

    }

    private double adjustDuration(double value, String duration) {
        switch (duration) {
            case "Per Year":
                return value / 52;
            case "Per Month":
                return value / 4.3333333;
            default:
                return value;
        }
    }

    public double getTextFieldValue(JTextField field) {

        // get value as String from field
        String fieldString = field.getText(); // get text from text field

        if (fieldString.isBlank()) { // if text field is blank, return 0
            return 0.0;
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

    private void addComponent(Component component, int row, int col) {
        GridBagConstraints gbc = new GridBagConstraints(); // always use horixontsl filll
        gbc.gridx = col;
        gbc.gridy = row;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(component, gbc);
    }

}
