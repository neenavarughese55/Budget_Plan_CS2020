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
import java.awt.*;

import Budget.UI.IncomeBase;
import Budget.UI.SpendingBase;
import Budget.UI.CalculationBase;
import Budget.UI.UndoBase;
import Budget.UI.BudgetState;

// class definition
public class BudgetBase extends JPanel { // based on Swing JPanel
    private UndoBase undoBase = new UndoBase(); // Muliple undo

    // widgets which may have listeners and/or values
    public JButton calculateButton; // Calculate button
    public JButton exitButton; // Exit button
    public JButton undoButton; // Undo button

    public IncomeBase incomeBase;
    public SpendingBase spendingBase;
    public CalculationBase calculationBase;

    // constructor - create UI (dont need to change this)
    public BudgetBase(JFrame frame) {
        setLayout(new GridBagLayout()); // use GridBag layout
        GridBagConstraints gbc = new GridBagConstraints(); // initalise components

        // initialise componenents
        // Note that this method is quite long. Can be shortened by putting Action
        // Listener stuff in a separate method
        // will be generated automatically by IntelliJ, Eclipse, etc
        // Row 4 - Calculate Button
        incomeBase = new IncomeBase(frame);
        spendingBase = new SpendingBase(frame);
        calculationBase = new CalculationBase(frame, incomeBase, spendingBase);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(incomeBase, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(spendingBase, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(calculationBase, gbc);

        // Row for buttons
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 3;

        calculateButton = new JButton("Calculate");
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(calculateButton, gbc);

        gbc.gridx = 3;
        gbc.gridwidth = 1;
        exitButton = new JButton("Exit");
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(exitButton, gbc);

        gbc.gridx = 6;
        gbc.gridwidth = 1;
        undoButton = new JButton("Undo");
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(undoButton, gbc);

        // set up listeners (in a spearate method)
        initListeners();
    }

    public void saveCurrentState(UndoBase undoBase) {
        double wages = incomeBase.getTextFieldValue(incomeBase.wagesField);
        double loans = incomeBase.getTextFieldValue(incomeBase.loansField);
        double savings = incomeBase.getTextFieldValue(incomeBase.savingsField);

        double food = spendingBase.getTextFieldValue(spendingBase.foodField);
        double rent = spendingBase.getTextFieldValue(spendingBase.rentField);
        double transport = spendingBase.getTextFieldValue(spendingBase.transportField);

        BudgetState state = new BudgetState(wages, loans, savings, food, rent, transport);
        undoBase.saveState(state);
    }

    public void undoLastChange(UndoBase undoBase) {
        BudgetState state = undoBase.undo();
        if (state != null) {
            incomeBase.wagesField.setText(String.valueOf(state.wages));
            incomeBase.loansField.setText(String.valueOf(state.loans));
            incomeBase.savingsField.setText(String.valueOf(state.savings));

            spendingBase.foodField.setText(String.valueOf(state.food));
            spendingBase.rentField.setText(String.valueOf(state.rent));
            spendingBase.transportField.setText(String.valueOf(state.transport));
        }
    }

    // set up listeners
    // initially just for buttons, can add listeners for text fields
    public void initListeners() {

        // calculateButton - call calculateTotalIncome() when pressed
        calculateButton.addActionListener(e -> {
            saveCurrentState(undoBase);
            double totalIncome = incomeBase.calculateTotalIncome();
            double totalSpending = spendingBase.calculateTotalSpend();
            calculationBase.updateLeftover(totalIncome, totalSpending);
        });

        // exitButton - exit program when pressed
        exitButton.addActionListener(e -> System.exit(0));

        undoButton.addActionListener(e -> undoLastChange(undoBase));
    }

    // add a component at specified row and column in UI. (0,0) is top-left corner

    // update totalIncomeField (eg, when Calculate is pressed)
    // use double to hold numbers, so user can type fractional amounts such as
    // 134.50

    // return the value if a text field as a double
    // --return 0 if field is blank
    // --return NaN if field is not a number
    // below is standard code to set up Swing, which students shouldnt need to edit
    // much
    // standard mathod to show UI
    public static void createAndShowGUI() {

        // Create and set up the window.
        JFrame frame = new JFrame("Budget Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane.
        frame.setContentPane(new BudgetBase(frame));
        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    // standard main class to set up Swing UI
    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        SwingUtilities.invokeLater(BudgetBase::createAndShowGUI);
    }
}
