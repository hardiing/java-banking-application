/** J. McCune, (c) 2021, All rights reserved.
    Modifications by N. Harding, 2021;
 */

import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

public class SwingValidator {    
    private final Component parentComponent;
    
    public SwingValidator(Component parent) {
        this.parentComponent = parent;
    }
    
    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(parentComponent, message, 
                "Error: ", JOptionPane.ERROR_MESSAGE);
    }

    public boolean hasAccount(Customer c) {
        if (c.getAccountNumber() != "") {
            showErrorDialog("Customer already has an account");
            return false;
        } else {
            return true;
        }
    }

    public boolean isInteger(JTextComponent c, String fieldName) {
        try {
            Integer.parseInt(c.getText());
            return true;
        } catch (NumberFormatException e) {
            showErrorDialog(fieldName + " must be an integer.");
            c.requestFocusInWindow();
            return false;
        }
    }

    public boolean isDouble(JTextComponent c, String fieldName) {
        try {
            Double.parseDouble(c.getText());
            return true;
        } catch (NumberFormatException e) {
            showErrorDialog(fieldName + " must be a valid number.");
            c.requestFocusInWindow();
            return false;
        }
    }
}
