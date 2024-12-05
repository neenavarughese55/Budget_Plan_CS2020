import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Budget.Main.BudgetBase;

// Swing imports
import javax.swing.*;

/**
 * Unit test for BudgetBase functionality.
 */
public class BudgetBaseTests {

    private BudgetBase budgetBase; // Instance of BudgetBase for testing
    private JFrame frame; // Parent JFrame

    // Set up the testing environment before each test
    @BeforeEach
    public void setUp() {
        frame = new JFrame(); // Initialise a new JFrame
        budgetBase = new BudgetBase(frame); // Initialise a new BudgetBase instance
    }

    /**
     * Test for calculateTotalIncome.
     */
    @Test
    public void shouldTotal() {
        budgetBase.incomeBase.wagesField.setText("500.0");
        budgetBase.incomeBase.loansField.setText("200.0");
        budgetBase.incomeBase.savingsField.setText("100.0");
        double expectedTotal = 500 + 200 + 100;
        assertEquals(expectedTotal, budgetBase.incomeBase.calculateTotalIncome());
    }

    /**
     * Test for Undo functionality.
     */
    @Test
    public void shouldUndoLastChange() {
        // Set initial values and save state
        budgetBase.incomeBase.wagesField.setText("500.0");
        budgetBase.incomeBase.loansField.setText("200.0");
        budgetBase.incomeBase.savingsField.setText("100.0");
        budgetBase.spendingBase.foodField.setText("50.0");
        budgetBase.spendingBase.rentField.setText("300.0");
        budgetBase.spendingBase.transportField.setText("20.0");

        budgetBase.saveCurrentState(budgetBase.undoBase); // Save the current state

        // Modify fields
        budgetBase.incomeBase.wagesField.setText("600.0");
        budgetBase.incomeBase.loansField.setText("250.0");

        // Undo changes
        budgetBase.undoLastChange(budgetBase.undoBase);

        // Verify fields reverted to the previous state
        assertEquals("500.0", budgetBase.incomeBase.wagesField.getText());
        assertEquals("200.0", budgetBase.incomeBase.loansField.getText());
        assertEquals("100.0", budgetBase.incomeBase.savingsField.getText());
        assertEquals("50.0", budgetBase.spendingBase.foodField.getText());
        assertEquals("300.0", budgetBase.spendingBase.rentField.getText());
        assertEquals("20.0", budgetBase.spendingBase.transportField.getText());
    }

    /**
     * Test for multiple Undo functionality.
     */
    @Test
    public void shouldUndoMultipleChanges() {
        // Set initial values and save state
        budgetBase.incomeBase.wagesField.setText("500.0");
        budgetBase.incomeBase.loansField.setText("200.0");
        budgetBase.incomeBase.savingsField.setText("100.0");
        budgetBase.spendingBase.foodField.setText("50.0");
        budgetBase.spendingBase.rentField.setText("300.0");
        budgetBase.spendingBase.transportField.setText("20.0");

        budgetBase.saveCurrentState(budgetBase.undoBase); // Save first state

        // Modify fields and save state
        budgetBase.incomeBase.wagesField.setText("600.0");
        budgetBase.incomeBase.loansField.setText("250.0");
        budgetBase.saveCurrentState(budgetBase.undoBase); // Save second state

        // Modify fields again
        budgetBase.incomeBase.wagesField.setText("700.0");
        budgetBase.incomeBase.loansField.setText("300.0");

        // Undo last change
        budgetBase.undoLastChange(budgetBase.undoBase);

        // Verify fields reverted to the second state
        assertEquals("600.0", budgetBase.incomeBase.wagesField.getText());
        assertEquals("250.0", budgetBase.incomeBase.loansField.getText());

        // Undo again
        budgetBase.undoLastChange(budgetBase.undoBase);

        // Verify fields reverted to the first state
        assertEquals("500.0", budgetBase.incomeBase.wagesField.getText());
        assertEquals("200.0", budgetBase.incomeBase.loansField.getText());
        assertEquals("100.0", budgetBase.incomeBase.savingsField.getText());
        assertEquals("50.0", budgetBase.spendingBase.foodField.getText());
        assertEquals("300.0", budgetBase.spendingBase.rentField.getText());
        assertEquals("20.0", budgetBase.spendingBase.transportField.getText());
    }

    /**
     * Test Undo with no saved state.
     */
    @Test
    public void shouldNotUndoIfNoState() {
        // Undo with no prior save
        budgetBase.undoLastChange(budgetBase.undoBase);

        // Verify fields remain unchanged
        assertEquals("", budgetBase.incomeBase.wagesField.getText());
        assertEquals("", budgetBase.incomeBase.loansField.getText());
        assertEquals("", budgetBase.incomeBase.savingsField.getText());
        assertEquals("", budgetBase.spendingBase.foodField.getText());
        assertEquals("", budgetBase.spendingBase.rentField.getText());
        assertEquals("", budgetBase.spendingBase.transportField.getText());
    }
}
