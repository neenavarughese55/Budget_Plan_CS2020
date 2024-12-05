import Budget.UI.BudgetState;
import Budget.UI.UndoBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import static org.junit.jupiter.api.Assertions.*;

class UndoBaseTest {

    private UndoBase undoBase;

    @BeforeEach
    void setUp() {
        undoBase = new UndoBase();
    }

    @Test
    void testSaveState() {
        BudgetState state = new BudgetState(500, 200, 100, 50, 300, 20);
        undoBase.saveState(state);
        assertTrue(undoBase.canUndo(), "Undo stack should not be empty after saving a state");
    }

    @Test
    void testUndoSingleAction() {
        BudgetState state1 = new BudgetState(500, 200, 100, 50, 300, 20);
        undoBase.saveState(state1);

        BudgetState undoneState = undoBase.undo();
        assertEquals(state1, undoneState, "The undone state should match the last saved state");
    }

    @Test
    void testUndoMultipleActions() {
        BudgetState state1 = new BudgetState(500, 200, 100, 50, 300, 20);
        BudgetState state2 = new BudgetState(600, 250, 150, 70, 320, 30);
        BudgetState state3 = new BudgetState(700, 300, 200, 90, 350, 40);

        undoBase.saveState(state1);
        undoBase.saveState(state2);
        undoBase.saveState(state3);

        assertEquals(state3, undoBase.undo(), "Undo should return the most recently saved state (state3)");
        assertEquals(state2, undoBase.undo(), "Undo should return the second most recent state (state2)");
        assertEquals(state1, undoBase.undo(), "Undo should return the earliest saved state (state1)");
    }

    @Test
    void testUndoOnEmptyStack() {
        BudgetState undoneState = undoBase.undo();
        assertNull(undoneState, "Undo on an empty stack should return null");
    }

    @Test
    void testStateIntegrity() {
        BudgetState state = new BudgetState(500, 200, 100, 50, 300, 20);
        undoBase.saveState(state);

        // Modify the original state after saving
        state.wages = 999;

        BudgetState savedState = undoBase.undo();
        assertEquals(500, savedState.wages,
                "The saved state should remain unchanged despite modifications to the original object");
    }
}
