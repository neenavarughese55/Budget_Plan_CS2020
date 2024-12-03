package Budget.UI;

import javax.swing.*;
import java.awt.*;

public class CalculationBase extends JPanel {

    public JLabel leftoverLabel;

    public CalculationBase(JFrame frame, IncomeBase incomeBase, SpendingBase spendingBase) {
        setLayout(new GridBagLayout()); // use GridBag layout
        GridBagConstraints gbc = new GridBagConstraints(); // initalise components

        leftoverLabel = new JLabel("Leftover: 0.00");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        add(leftoverLabel, gbc);
    }

    public double updateCalculation(double totalIncome, double totalSpend) {
        double leftover = totalIncome - totalSpend;

        if (leftover < 0) {
            leftoverLabel
                    .setText(String.format("<html><span style='color:red;'>Leftover: %.2f</span></html>", leftover));
        } else {
            leftoverLabel
                    .setText(String.format("<html><span style='color:black;'>Leftover: %.2f</span></html>", leftover));
        }
        return leftover;
    }

}
