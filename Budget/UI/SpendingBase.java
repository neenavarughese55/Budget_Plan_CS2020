package Budget.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;

// Class to handle spending related inputs and calculations
public class SpendingBase extends JPanel {

    // Components for spending inputs
    public JTextField foodField; // Food text field
    public JComboBox<String> foodDurationBox; // Food duration selection

    public JTextField rentField; // Rent text field
    public JComboBox<String> rentDurationBox; // Rent duration selection

    public JTextField transportField; // Transport text field
    public JComboBox<String> transportDurationBox; // Transport duration selection

    public JTextField totalSpendField; // Field to display total spend

    JFrame topLevelFrame; // Reference to the parent JFrame
    GridBagConstraints layoutConstraints = new GridBagConstraints(); // For layout positioning

    // Constructor to intialise the SpendingBase panel
    public SpendingBase(JFrame frame) {
        topLevelFrame = frame; // keep track of top-level frame
        setLayout(new GridBagLayout()); // use GridBag layout
        initSpendingComponents(); // initalise components
        addListeners(); // Add event listeners to the components
    }

    // Method to initialise spending related components
    private void initSpendingComponents() {
        // Duration options for the dropdowns
        String[] durations = { "Per Week", "Per Month", "Per Year" };

        // Add "SPENDING" label
        JLabel spendingLabel = new JLabel("SPENDING");
        layoutConstraints.gridwidth = 2;
        addComponent(spendingLabel, 1, 1);

        // food label followed by food textbox
        JLabel foodLabel = new JLabel("Food");
        addComponent(foodLabel, 2, 1);

        // set up text field for entering food
        foodField = new JTextField("", 10); // blank initially, with 10 columns
        foodField.setHorizontalAlignment(JTextField.RIGHT); // number is at right end of field
        addComponent(foodField, 2, 3);

        foodDurationBox = new JComboBox<>(durations); // Dropdown for duration selection
        addComponent(foodDurationBox, 2, 6);

        // rent label followed by rent textbox
        JLabel rentLabel = new JLabel("Rent");
        addComponent(rentLabel, 3, 1);

        // set up text field for entering rent
        rentField = new JTextField("", 10); // blank initially, with 10 columns
        rentField.setHorizontalAlignment(JTextField.RIGHT); // number is at right end of field
        addComponent(rentField, 3, 3);

        rentDurationBox = new JComboBox<>(durations); // Dropdown for duration selection
        addComponent(rentDurationBox, 3, 6);

        // transport label followed by transport textbox
        JLabel transportLabel = new JLabel("Transport");
        addComponent(transportLabel, 4, 1);

        // set up text field for entering transport
        transportField = new JTextField("", 10); // blank initially, with 10 columns
        transportField.setHorizontalAlignment(JTextField.RIGHT); // number is at right end of field
        addComponent(transportField, 4, 3);

        transportDurationBox = new JComboBox<>(durations); // Dropdown for duration selection
        addComponent(transportDurationBox, 4, 6);

        // Total Spend label
        JLabel totalSpendingLabel = new JLabel("Total Spend");
        addComponent(totalSpendingLabel, 6, 1);

        // set up text box for displaying total spend. Users cam view, but cannot
        // directly edit it
        totalSpendField = new JTextField("0", 10); // 0 initially, with 10 columns
        totalSpendField.setHorizontalAlignment(JTextField.RIGHT); // number is at right end of field
        totalSpendField.setEditable(false); // user cannot directly edit this field (ie, it is read-only)
        addComponent(totalSpendField, 6, 3);
    }

    // Method to add event listeners for components
    private void addListeners() {
        // Listener to calculate total spend when focus is lost
        FocusListener focusListener = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                calculateTotalSpend(); // Recalulate total spend
            }
        };

        // Add focus listeners to text fields
        foodField.addFocusListener(focusListener);
        rentField.addFocusListener(focusListener);
        transportField.addFocusListener(focusListener);

        // Listener for dropdown selection changes
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateTotalSpend();
            }
        };

        // Add action listeners to dropdowns
        foodDurationBox.addActionListener(actionListener);
        rentDurationBox.addActionListener(actionListener);
        transportDurationBox.addActionListener(actionListener);
    }

    // Calculate the total income based on user inputs
    public double calculateTotalSpend() {
        // get values from income text fields. value is NaN if an error occurs
        double food = adjustDuration(getTextFieldValue(foodField), (String) foodDurationBox.getSelectedItem());
        double rent = adjustDuration(getTextFieldValue(rentField), (String) rentDurationBox.getSelectedItem());
        double transport = adjustDuration(getTextFieldValue(transportField),
                (String) transportDurationBox.getSelectedItem());

        // clear total field and return if any value is NaN (error)
        if (Double.isNaN(food) || Double.isNaN(rent) || Double.isNaN(transport)) {
            totalSpendField.setText(""); // clear total income field
            return 0.0; // exit method
        }

        // otherwise calculate total spend and update text field
        double totalSpend = food + rent + transport;
        totalSpendField.setText(String.format("%.2f", totalSpend)); // format with 2 digits after the .
        return (totalSpend);

    }

    private double adjustDuration(double value, String duration) {
        switch (duration) {
            case "Per Year":
                return value / 52; // Convert yearly value to weekly
            case "Per Month":
                return value / 4.3333333; // Convert monthly value to weekly
            default:
                return value; // No adjustment needed
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

    // Utility method to add a component to the panel with layout constraints
    private void addComponent(Component component, int row, int col) {
        GridBagConstraints gbc = new GridBagConstraints(); // always use horixontsl filll
        gbc.gridx = col;
        gbc.gridy = row;
        gbc.insets = new Insets(5, 5, 5, 5); // Add padding
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill horizontally
        add(component, gbc);
    }

}
