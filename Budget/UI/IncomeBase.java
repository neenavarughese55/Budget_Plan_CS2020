package Budget.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;

// Class to handle income related inputs and calculations
public class IncomeBase extends JPanel {

    // Components for income inputs
    public JTextField wagesField; // Wages text field
    public JComboBox<String> wagesDurationBox; // Wages Duration Selection

    public JTextField loansField; // Loans text field
    public JComboBox<String> loansDurationBox; // Loans Duration Selection

    public JTextField savingsField;
    public JComboBox<String> savingsDurationBox; // Savings Duration Selection

    public JTextField totalIncomeField; // Field to display total income

    JFrame topLevelFrame; // Reference to the parent JFrame
    GridBagConstraints layoutConstraints = new GridBagConstraints(); // For layout positioning

    // Constructor to intialise the IncomeBase panel
    public IncomeBase(JFrame frame) {
        topLevelFrame = frame; // keep track of top-level frame
        setLayout(new GridBagLayout()); // use GridBag layout
        initIncomeComponents(); // initalise components
        addListeners(); // Add event listeners to the components
    }

    // Method to initialise income related components
    private void initIncomeComponents() {
        // Duration options for the dropdowns
        String[] durations = { "Per Week", "Per Month", "Per Year" };

        // Add "INCOME" label
        JLabel incomeLabel = new JLabel("INCOME");
        layoutConstraints.gridwidth = 2;
        addComponent(incomeLabel, 0, 2);

        // Wages label followed by wages textbox
        JLabel wagesLabel = new JLabel("Wages");
        addComponent(wagesLabel, 1, 2);

        // set up text field for entering wages
        wagesField = new JTextField("", 10); // blank initially, with 10 columns
        wagesField.setHorizontalAlignment(JTextField.RIGHT); // number is at right end of field
        addComponent(wagesField, 1, 3);

        wagesDurationBox = new JComboBox<>(durations); // Dropdown for duration selection
        addComponent(wagesDurationBox, 1, 6);

        // Loans label followed by loans textbox
        JLabel loansLabel = new JLabel("Loans");
        addComponent(loansLabel, 2, 2);

        // set up text box for entering loans
        loansField = new JTextField("", 10); // blank initially, with 10 columns
        loansField.setHorizontalAlignment(JTextField.RIGHT); // number is at right end of field
        addComponent(loansField, 2, 3);

        loansDurationBox = new JComboBox<>(durations); // Dropdown for duration selection
        addComponent(loansDurationBox, 2, 6);

        // Savings label followed by loans textbox
        JLabel savingsLabel = new JLabel("savings");
        addComponent(savingsLabel, 3, 2);

        // set up text box for entering savings
        savingsField = new JTextField("", 10); // blank initially, with 10 columns
        savingsField.setHorizontalAlignment(JTextField.RIGHT); // number is at right end of field
        addComponent(savingsField, 3, 3);

        savingsDurationBox = new JComboBox<>(durations); // Dropdown for duration selection
        addComponent(savingsDurationBox, 3, 6);

        // Total Income label
        JLabel totalIncomeLabel = new JLabel("Total Income");
        addComponent(totalIncomeLabel, 5, 2);

        // set up text box for displaying total income. Users cam view, but cannot
        // directly edit it
        totalIncomeField = new JTextField("0", 10); // 0 initially, with 10 columns
        totalIncomeField.setHorizontalAlignment(JTextField.RIGHT); // number is at right end of field
        totalIncomeField.setEditable(false); // user cannot directly edit this field (ie, it is read-only)
        addComponent(totalIncomeField, 5, 3);
    }

    // Method to add event listeners for components
    private void addListeners() {
        // Listener to calculate total income when focus is lost
        FocusListener focusListener = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                calculateTotalIncome(); // Recalulate total income
            }
        };

        // Add focus listeners to text fields
        wagesField.addFocusListener(focusListener);
        loansField.addFocusListener(focusListener);
        savingsField.addFocusListener(focusListener);

        // Listener for dropdown selection changes
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateTotalIncome(); // Recalculate total income
            }
        };

        // Add action listeners to dropdowns
        wagesDurationBox.addActionListener(actionListener);
        loansDurationBox.addActionListener(actionListener);
        savingsDurationBox.addActionListener(actionListener);
    }

    // Calculate the total income based on user inputs
    public double calculateTotalIncome() {
        // get values from income text fields. value is NaN if an error occurs
        double wages = adjustDuration(getTextFieldValue(wagesField), (String) wagesDurationBox.getSelectedItem());
        double loans = adjustDuration(getTextFieldValue(loansField), (String) loansDurationBox.getSelectedItem());
        double savings = adjustDuration(getTextFieldValue(savingsField), (String) savingsDurationBox.getSelectedItem());

        // clear total field and return if any value is NaN (error)
        if (Double.isNaN(wages) || Double.isNaN(loans) || Double.isNaN(savings)) {
            totalIncomeField.setText(""); // clear total income field
            return 0.0; // exit method
        }

        // otherwise calculate total income and update text field
        double totalIncome = wages + loans + savings;
        totalIncomeField.setText(String.format("%.2f", totalIncome)); // format with 2 digits after the .
        return totalIncome;
    }

    private double adjustDuration(double value, String duration) {
        switch (duration) {
            case "Per Year":
                return value / 52; // Convert yearly value to weekly
            case "Per Month":
                return value / 4.3333333; // Convert monthly value to weekly
            default: // Per Week
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
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = col;
        gbc.gridy = row;
        gbc.insets = new Insets(5, 5, 5, 5); // Add padding
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill horizontally
        add(component, gbc);
    }

}