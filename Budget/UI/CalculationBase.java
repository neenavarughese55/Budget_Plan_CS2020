package Budget.UI;

import javax.swing.*;
import java.awt.*;

// Class to handle the calculation and display of leftover funds
public class CalculationBase extends JPanel {

    public JLabel leftoverLabel; // label to display the leftover amount

    // Constructor to initialise the CalculatioBase panel
    public CalculationBase(JFrame frame, IncomeBase incomeBase, SpendingBase spendingBase) {
        setLayout(new GridBagLayout()); // use GridBag layout
        GridBagConstraints gbc = new GridBagConstraints(); // Constraints for layout positioning

        // Initialise and add the leftover label
        leftoverLabel = new JLabel("Leftover: 0.00"); // Default value
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5); // Add padding
        add(leftoverLabel, gbc);
    }

    public double updateLeftover(double totalIncome, double totalSpend) {
        double leftover = totalIncome - totalSpend;

        // Update the label with formatted leftover value
        if (leftover < 0) {
            // Display leftover in red if it's neagtive
            leftoverLabel
                    .setText(String.format("<html><span style='color:red;'>Leftover: %.2f</span></html>", leftover));
        } else {
            // Display leftover in black if it's positive or zero
            leftoverLabel
                    .setText(String.format("<html><span style='color:black;'>Leftover: %.2f</span></html>", leftover));
        }
        return leftover;
    }

}
