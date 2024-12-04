package Budget.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;

public class IncomeBase extends JPanel {

    public JTextField wagesField; // Wages text field
    public JComboBox<String> wagesDurationBox; // Wages Duration Selection

    public JTextField loansField; // Loans text field
    public JComboBox<String> loansDurationBox; // Loans Duration Selection

    public JTextField savingsField;
    public JComboBox<String> savingsDurationBox; // Savings Duration Selection

    public JTextField totalIncomeField;

    JFrame topLevelFrame; // top-level JFrame
    GridBagConstraints layoutConstraints = new GridBagConstraints();

    public IncomeBase(JFrame frame) {
        topLevelFrame = frame; // keep track of top-level frame
        setLayout(new GridBagLayout()); // use GridBag layout
        initIncomeComponents(); // initalise components
        addListeners();
    }

    private void initIncomeComponents() {
        String[] durations = { "Per Week", "Per Month", "Per Year" };

        JLabel incomeLabel = new JLabel("INCOME");
        layoutConstraints.gridwidth = 2;
        addComponent(incomeLabel, 0, 2);

        // Row 1 - Wages label followed by wages textbox
        JLabel wagesLabel = new JLabel("Wages");
        addComponent(wagesLabel, 1, 2);

        // set up text field for entering wages
        // Could create method to do below (since this is done several times)
        wagesField = new JTextField("", 10); // blank initially, with 10 columns
        wagesField.setHorizontalAlignment(JTextField.RIGHT); // number is at right end of field
        addComponent(wagesField, 1, 3);

        wagesDurationBox = new JComboBox<>(durations);
        addComponent(wagesDurationBox, 1, 6);

        // Row 2 - Loans label followed by loans textbox
        JLabel loansLabel = new JLabel("Loans");
        addComponent(loansLabel, 2, 2);

        // set up text box for entering loans
        loansField = new JTextField("", 10); // blank initially, with 10 columns
        loansField.setHorizontalAlignment(JTextField.RIGHT); // number is at right end of field
        addComponent(loansField, 2, 3);

        loansDurationBox = new JComboBox<>(durations);
        addComponent(loansDurationBox, 2, 6);

        JLabel savingsLabel = new JLabel("savings");
        addComponent(savingsLabel, 3, 2);

        savingsField = new JTextField("", 10); // blank initially, with 10 columns
        savingsField.setHorizontalAlignment(JTextField.RIGHT); // number is at right end of field
        addComponent(savingsField, 3, 3);

        savingsDurationBox = new JComboBox<>(durations);
        addComponent(savingsDurationBox, 3, 6);

        JLabel totalIncomeLabel = new JLabel("Total Income");
        addComponent(totalIncomeLabel, 5, 2);

        // set up text box for displaying total income. Users cam view, but cannot
        // directly edit it
        totalIncomeField = new JTextField("0", 10); // 0 initially, with 10 columns
        totalIncomeField.setHorizontalAlignment(JTextField.RIGHT); // number is at right end of field
        totalIncomeField.setEditable(false); // user cannot directly edit this field (ie, it is read-only)
        addComponent(totalIncomeField, 5, 3);
    }

    private void addListeners() {
        FocusListener focusListener = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                calculateTotalIncome();
            }
        };

        wagesField.addFocusListener(focusListener);
        loansField.addFocusListener(focusListener);
        savingsField.addFocusListener(focusListener);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateTotalIncome();
            }
        };

        wagesDurationBox.addActionListener(actionListener);
        loansDurationBox.addActionListener(actionListener);
        savingsDurationBox.addActionListener(actionListener);
    }

    public void addLabelandField(String labelText, int row, JTextField field) {
        JLabel label = new JLabel(labelText);
        addComponent(label, row, 0);

        field.setHorizontalAlignment(JTextField.RIGHT);
        addComponent(field, row, 2);
    }

    public double calculateTotalIncome() {

        // get values from income text fields. valie is NaN if an error occurs
        double wages = adjustDuration(getTextFieldValue(wagesField), (String) wagesDurationBox.getSelectedItem());
        double loans = adjustDuration(getTextFieldValue(loansField), (String) loansDurationBox.getSelectedItem());
        double savings = adjustDuration(getTextFieldValue(savingsField), (String) savingsDurationBox.getSelectedItem());

        // clear total field and return if any value is NaN (error)
        if (Double.isNaN(wages) || Double.isNaN(loans) || Double.isNaN(savings)) {
            totalIncomeField.setText(""); // clear total income field
            return 0.0; // exit method and do nothing
        }

        // otherwise calculate total income and update text field
        double totalIncome = wages + loans + savings;
        totalIncomeField.setText(String.format("%.2f", totalIncome)); // format with 2 digits after the .
        return totalIncome;
    }

    private double adjustDuration(double value, String duration) {
        switch (duration) {
            case "Per Year":
                return value / 52;
            case "Per Month":
                return value / 4.3333333;
            default: // Per Week
                return value;
        }
    }

    private double getTextFieldValue(JTextField field) {

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
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = col;
        gbc.gridy = row;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(component, gbc);
    }

}