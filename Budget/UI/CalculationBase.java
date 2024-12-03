package Budget.UI;

import javax.swing.*;

import Budget.Main.BudgetBase;
import Budget.UI.IncomeBase;
import Budget.UI.SpendingBase;

import java.awt.*;

public class CalculationBase extends JLabel {

    JFrame topLevelFrame; // top-level JFrame
    GridBagConstraints layoutConstraints = new GridBagConstraints();

    private JLabel leftoverField;

    public CalculationBase(JFrame frame) {
        topLevelFrame = frame; // keep track of top-level frame
        setLayout(new GridBagLayout()); // use GridBag layout
        CalculationinitComponents(); // initalise components
    }

    private void CalculationinitComponents() {

    }

    public double IncomeMinusSpending() {
        double totalIncome = getTextFieldValue(IncomeBase.totalIncomeField);
        double totalSpend = getTextFieldValue(SpendingBase.totalSpendField);

        if (Double.isNaN(totalIncome) || Double.isNaN(totalSpend)) {
            SpendingBase.totalSpendField.setText(""); // clear total income field
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

}
