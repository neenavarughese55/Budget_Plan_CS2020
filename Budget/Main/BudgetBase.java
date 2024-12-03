// base code for student budget assessment
// Students do not need to use this code in their assessment, fine to junk it and do something different!
//
// Your submission must be a maven project, and must be submitted via Codio, and run in Codio
//
// user can enter in wages and loans and calculate total income
//
// run in Codio 
// To see GUI, run with java and select Box Url from Codio top line menu
//
// Layout - Uses GridBag layout in a straightforward way, every component has a (column, row) position in the UI grid
// Not the prettiest layout, but relatively straightforward
// Students who use IntelliJ or Eclipse may want to use the UI designers in these IDEs , instead of GridBagLayout
package Budget.Main;

// Swing imports
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

// class definition
public class BudgetBase extends JPanel { // based on Swing JPanel

    // high level UI stuff
    JFrame topLevelFrame; // top-level JFrame
    GridBagConstraints layoutConstraints = new GridBagConstraints(); // used to control layout

    // widgets which may have listeners and/or values
    private JButton calculateButton; // Calculate button
    private JButton exitButton; // Exit button
    private JTextField wagesField; // Wages text field
    private JTextField loansField; // Loans text field
    private JTextField savingsField;
    private JTextField foodField;
    private JTextField rentField;
    private JTextField transportField;
    private JTextField totalIncomeField; // Total Income field
    private JTextField totalSpendField;
    private JLabel leftoverField;

    // constructor - create UI (dont need to change this)
    public BudgetBase(JFrame frame) {
        topLevelFrame = frame; // keep track of top-level frame
        setLayout(new GridBagLayout()); // use GridBag layout
        initComponents(); // initalise components
    }

    // initialise componenents
    // Note that this method is quite long. Can be shortened by putting Action
    // Listener stuff in a separate method
    // will be generated automatically by IntelliJ, Eclipse, etc
    private void initComponents() {
        // Top row (0) - "INCOME" label
        JLabel incomeLabel = new JLabel("INCOME");
        addComponent(incomeLabel, 0, 0);

        // Row 1 - Wages label followed by wages textbox
        JLabel wagesLabel = new JLabel("Wages");
        addComponent(wagesLabel, 1, 0);

        // set up text field for entering wages
        // Could create method to do below (since this is done several times)
        wagesField = new JTextField("", 10); // blank initially, with 10 columns
        wagesField.setHorizontalAlignment(JTextField.RIGHT); // number is at right end of field
        addComponent(wagesField, 1, 1);

        // Row 2 - Loans label followed by loans textbox
        JLabel loansLabel = new JLabel("Loans");
        addComponent(loansLabel, 2, 0);

        // set up text box for entering loans
        loansField = new JTextField("", 10); // blank initially, with 10 columns
        loansField.setHorizontalAlignment(JTextField.RIGHT); // number is at right end of field
        addComponent(loansField, 2, 1);

        JLabel savingsLabel = new JLabel("savings");
        addComponent(savingsLabel, 3, 0);

        savingsField = new JTextField("", 10); // blank initially, with 10 columns
        savingsField.setHorizontalAlignment(JTextField.RIGHT); // number is at right end of field
        addComponent(savingsField, 3, 1);

        // Row 3 - Total Income label followed by total income field
        JLabel totalIncomeLabel = new JLabel("Total Income");
        addComponent(totalIncomeLabel, 4, 0);

        // set up text box for displaying total income. Users cam view, but cannot
        // directly edit it
        totalIncomeField = new JTextField("0", 10); // 0 initially, with 10 columns
        totalIncomeField.setHorizontalAlignment(JTextField.RIGHT); // number is at right end of field
        totalIncomeField.setEditable(false); // user cannot directly edit this field (ie, it is read-only)
        addComponent(totalIncomeField, 4, 1);

        // Top row (0) - "INCOME" label
        JLabel spendingLabel = new JLabel("SPENDING");
        addComponent(spendingLabel, 0, 4);

        // Row 1 - Wages label followed by wages textbox
        JLabel foodLabel = new JLabel("Food");
        addComponent(foodLabel, 1, 4);

        // set up text field for entering wages
        // Could create method to do below (since this is done several times)
        foodField = new JTextField("", 10); // blank initially, with 10 columns
        foodField.setHorizontalAlignment(JTextField.LEFT); // number is at right end of field
        addComponent(foodField, 1, 5);

        // Row 1 - Wages label followed by wages textbox
        JLabel rentLabel = new JLabel("Rent");
        addComponent(rentLabel, 2, 4);

        // set up text field for entering wages
        // Could create method to do below (since this is done several times)
        rentField = new JTextField("", 10); // blank initially, with 10 columns
        rentField.setHorizontalAlignment(JTextField.LEFT); // number is at right end of field
        addComponent(rentField, 2, 5);

        // Row 1 - Wages label followed by wages textbox
        JLabel transportLabel = new JLabel("Transport");
        addComponent(transportLabel, 3, 4);

        // set up text field for entering wages
        // Could create method to do below (since this is done several times)
        transportField = new JTextField("", 10); // blank initially, with 10 columns
        transportField.setHorizontalAlignment(JTextField.LEFT); // number is at right end of field
        addComponent(transportField, 3, 5);

        // Row 3 - Total Income label followed by total income field
        JLabel totalSpendingLabel = new JLabel("Total Spend");
        addComponent(totalSpendingLabel, 4, 4);

        // set up text box for displaying total income. Users cam view, but cannot
        // directly edit it
        totalSpendField = new JTextField("0", 10); // 0 initially, with 10 columns
        totalSpendField.setHorizontalAlignment(JTextField.LEFT); // number is at right end of field
        totalSpendField.setEditable(false); // user cannot directly edit this field (ie, it is read-only)
        addComponent(totalSpendField, 4, 5);

        // Row 3 - Total Income label followed by total income field
        JLabel leftoverLabel = new JLabel("Leftover");
        addComponent(leftoverLabel, 5, 4);

        // set up text box for displaying total income. Users cam view, but cannot
        // directly edit it
        leftoverField = new JLabel("0"); // 0 initially, with 10 columns
        leftoverField.setHorizontalAlignment(SwingConstants.LEFT); // number is at right end of field // user cannot
                                                                   // directly edit this field (ie, it is read-only)
        addComponent(leftoverField, 5, 5);

        // Row 4 - Calculate Button
        calculateButton = new JButton("Calculate");
        addComponent(calculateButton, 6, 0);

        // Row 5 - Exit Button
        exitButton = new JButton("Exit");
        addComponent(exitButton, 7, 0);

        // set up listeners (in a spearate method)
        initListeners();
    }

    // set up listeners
    // initially just for buttons, can add listeners for text fields
    private void initListeners() {

        // exitButton - exit program when pressed
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // calculateButton - call calculateTotalIncome() when pressed
        calculateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateTotalIncome();
                calculateTotalSpend();
                IncomeMinusSpending();
            }
        });

    }

    // add a component at specified row and column in UI. (0,0) is top-left corner
    private void addComponent(Component component, int gridrow, int gridcol) {
        layoutConstraints.fill = GridBagConstraints.HORIZONTAL; // always use horixontsl filll
        layoutConstraints.gridx = gridcol;
        layoutConstraints.gridy = gridrow;
        add(component, layoutConstraints);

    }

    // update totalIncomeField (eg, when Calculate is pressed)
    // use double to hold numbers, so user can type fractional amounts such as
    // 134.50
    public double calculateTotalIncome() {

        // get values from income text fields. valie is NaN if an error occurs
        double wages = getTextFieldValue(wagesField);
        double loans = getTextFieldValue(loansField);
        double savings = getTextFieldValue(savingsField);

        // clear total field and return if any value is NaN (error)
        if (Double.isNaN(wages) || Double.isNaN(loans) || Double.isNaN(savings)) {
            totalIncomeField.setText(""); // clear total income field
            wages = 0.0;
            return wages; // exit method and do nothing
        }

        // otherwise calculate total income and update text field
        double totalIncome = wages + loans + savings;
        totalIncomeField.setText(String.format("%.2f", totalIncome)); // format with 2 digits after the .
        return totalIncome;
    }

    public double calculateTotalSpend() {
        double food = getTextFieldValue(foodField);
        double rent = getTextFieldValue(rentField);
        double transport = getTextFieldValue(transportField);

        // clear total field and return if any value is NaN (error)
        if (Double.isNaN(food) || Double.isNaN(rent) || Double.isNaN(transport)) {
            totalSpendField.setText(""); // clear total income field
            food = 0.0;
            return food; // exit method and do nothing
        }

        double totalSpend = food + rent + transport;
        totalSpendField.setText(String.format("%.2f", totalSpend)); // format with 2 digits after the .
        return totalSpend;

    }

    public double IncomeMinusSpending() {
        double totalIncome = getTextFieldValue(totalIncomeField);
        double totalSpend = getTextFieldValue(totalSpendField);

        if (Double.isNaN(totalIncome) || Double.isNaN(totalSpend)) {
            totalSpendField.setText(""); // clear total income field
            totalIncome = 0.0;
            return totalIncome; // exit method and do nothing
        }

        double leftover = totalIncome - totalSpend;

        if (leftover < 0) {
            leftoverField.setText(String.format("<html><span style='color:red;'>%.2f</span></html>", leftover));
        } else {
            leftoverField.setText(String.format("<html><span style='color:black;'>%.2f</span></html>", leftover));
        }
        return leftover;
    }

    // return the value if a text field as a double
    // --return 0 if field is blank
    // --return NaN if field is not a number
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

    // below is standard code to set up Swing, which students shouldnt need to edit
    // much
    // standard mathod to show UI
    private static void createAndShowGUI() {

        // Create and set up the window.
        JFrame frame = new JFrame("Budget Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane.
        BudgetBase newContentPane = new BudgetBase(frame);
        newContentPane.setOpaque(true); // content panes must be opaque
        frame.setContentPane(newContentPane);

        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    // standard main class to set up Swing UI
    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}