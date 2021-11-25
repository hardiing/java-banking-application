/**
   Murach, J. (2017). Murachs Java Programming, Training and Reference, 
   5th Edition, Fresno, CA: Mike Murach & Associates. Inc.
   Modifications by N. Harding, 2021
 */

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class HardingFinalProject extends JFrame {
    
    // declare text fields for interface
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField addressField;
    private JTextField phoneNumberField;
    private JTextField accountNumberField;
    private JTextField balanceField;
    private JTextField withdrawDepositField;
    private JTextField interestMonthField;
    private JTextField calculatedInterestField;
    private List<Customer> results = null; // list for search results
    private List<Customer> customers = null; // list for pulling all customers
    private int index; // index variable for stepping through customers
    
    private static DAO<Customer> customerDAO = null;
    
        public HardingFinalProject() {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e);
        }
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Banking Application");
        setLocationByPlatform(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        // initialize fields
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        addressField = new JTextField();
        phoneNumberField = new JTextField();
        accountNumberField = new JTextField();
        balanceField = new JTextField();
        withdrawDepositField = new JTextField();
        
        balanceField.setEditable(false); // make balance non-editable
        
        // set preferred and minimum size for fields
        Dimension dim = new Dimension(100, 20);
        firstNameField.setPreferredSize(dim);
        firstNameField.setMinimumSize(dim);
        lastNameField.setPreferredSize(dim);
        lastNameField.setMinimumSize(dim);
        addressField.setPreferredSize(dim);
        addressField.setMinimumSize(dim);
        phoneNumberField.setPreferredSize(dim);
        phoneNumberField.setMinimumSize(dim);
        accountNumberField.setPreferredSize(dim);
        accountNumberField.setMinimumSize(dim);
        balanceField.setPreferredSize(dim);
        balanceField.setMinimumSize(dim);
        withdrawDepositField.setPreferredSize(dim);
        withdrawDepositField.setMinimumSize(dim);
        
        // create a new panel and add the labels/fields to a grid layout
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridBagLayout());
        textPanel.add(new JLabel("First Name"), getConstraints(0, 0));
        textPanel.add(firstNameField, getConstraints(1, 0));
        textPanel.add(new JLabel("Last Name"), getConstraints(2, 0));
        textPanel.add(lastNameField, getConstraints(3, 0));
        textPanel.add(new JLabel("Address"), getConstraints(0, 1));
        textPanel.add(addressField, getConstraints(1, 1));
        textPanel.add(new JLabel("Phone Number"), getConstraints(2, 1));
        textPanel.add(phoneNumberField, getConstraints(3, 1));
        textPanel.add(new JLabel("Account Number"), getConstraints(0, 2));
        textPanel.add(accountNumberField, getConstraints(1, 2));
        textPanel.add(new JLabel("Balance"), getConstraints(2, 2));
        textPanel.add(balanceField, getConstraints(3, 2));
        textPanel.add(new JLabel("Withdraw/Deposit"), getConstraints(0, 4));
        textPanel.add(withdrawDepositField, getConstraints(1, 4));
        
        // declare/initialize buttons and their event handlers
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(e -> {
            withdrawButtonClicked();
        });
        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(e -> {
            depositButtonClicked();
        });
        JButton calcInterestButton = new JButton("Calculate Interest");
        calcInterestButton.addActionListener(e -> {
            calcButtonClicked();
        });
        
        // add interest fields and make the calculated field non-editable
        interestMonthField = new JTextField();
        calculatedInterestField = new JTextField();
        calculatedInterestField.setEditable(false);
        
        // set a smaller preferred/minimum size for these fields
        Dimension monthDim = new Dimension(50, 20);
        interestMonthField.setPreferredSize(monthDim);
        interestMonthField.setMinimumSize(monthDim);
        calculatedInterestField.setPreferredSize(dim);
        calculatedInterestField.setMinimumSize(dim);
        
        // make a new panel for this set of components to a grid layout
        JPanel buttonInterestPanel = new JPanel();
        buttonInterestPanel.setLayout(new GridBagLayout());
        buttonInterestPanel.add(withdrawButton, getConstraints(1, 0));
        buttonInterestPanel.add(depositButton, getConstraints(2, 0));
        buttonInterestPanel.add(calcInterestButton, getConstraints(3, 0));
        buttonInterestPanel.add(new JLabel("Interest Month"), getConstraints(1, 1));
        buttonInterestPanel.add(interestMonthField, getConstraints(2, 1));
        buttonInterestPanel.add(new JLabel("Calculated Interest"), getConstraints(3, 1));
        buttonInterestPanel.add(calculatedInterestField, getConstraints(4, 1));
        
        // declare/initialize the rest of the buttons and their event handlers
        JButton searchButton = new JButton("Search Customer");
        searchButton.addActionListener(e -> {
            searchButtonClicked();
        });
        JButton previousButton = new JButton("Previous Customer");
        previousButton.addActionListener(e -> {
            previousButtonClicked();
        });
        JButton nextButton = new JButton("Next Customer");
        nextButton.addActionListener(e -> {
            nextButtonClicked();
        });
        JButton addButton = new JButton("Add Customer");
        addButton.addActionListener(e -> {
            addButtonClicked();
        });
        JButton updateButton = new JButton("Update Customer");
        updateButton.addActionListener(e -> {
            updateButtonClicked();
        });
        JButton openButton = new JButton("Open Account");
        openButton.addActionListener(e -> {
            openButtonClicked();
        });
        
        // create a panel for the buttons and add them with a flow layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(searchButton);
        buttonPanel.add(previousButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(openButton);
        
        // add the three panels to the main panel, laid out top/middle/bottom
        add(textPanel, BorderLayout.NORTH);
        add(buttonInterestPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // set size of window so all of the components are visible by default
        setSize(750, 250);
    }
    
    private GridBagConstraints getConstraints(int x, int y) {
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5, 5, 0, 5);
        c.gridx = x;
        c.gridy = y;
        return c;
    }
    
    private void addButtonClicked() {
        // when the add button is clicked, take input in customer fields and set defaults
        // for the account fields
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String address = addressField.getText();
        String phoneNumber = phoneNumberField.getText();
        String accountNumber = "";
        String balance = "";
        String interestRate = "";
        
        // create a new customer object with the above inputs and add them to the text file
        Customer c = new Customer();
        c.setFirstName(firstName);
        c.setLastName(lastName);
        c.setAddress(address);
        c.setPhoneNumber(phoneNumber);
        c.setAccountNumber(accountNumber);
        c.setBalance(balance);
        c.setInterestRate(interestRate);
        customerDAO.add(c);
    }
    
    private void updateButtonClicked() {
        // when update is clicked, take all current inputs
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String address = addressField.getText();
        String phoneNumber = phoneNumberField.getText();
        String accountNumber = accountNumberField.getText();
        String balance = balanceField.getText();
        String interestRate = "1";
        
        // get the customer object that matches the account number, and update 
        // with the above inputs
        Customer c = customerDAO.get(accountNumber);
        c.setFirstName(firstName);
        c.setLastName(lastName);
        c.setAddress(address);
        c.setPhoneNumber(phoneNumber);
        c.setAccountNumber(accountNumber);
        c.setBalance(balance);
        c.setInterestRate(interestRate);
        customerDAO.update(c);
    }
    
    private void searchButtonClicked() {
        // when search is clicked, initialize a list of all customers, search
        // for a first/last name match, and populate the fields with data from
        // the customer object
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        Customer c = new Customer();
        customers = customerDAO.getAll();
        results = new ArrayList<>();
        for (Customer x : customers) {
            c = x;
            if (c.getLastName().equals(lastName) && c.getFirstName().equals(firstName)) {
                results.add(c);
            }
        }
        index = 0;
        c = results.get(index);
        firstNameField.setText(c.getFirstName());
        addressField.setText(c.getAddress());
        phoneNumberField.setText(c.getPhoneNumber());
        accountNumberField.setText(c.getAccountNumber());
        balanceField.setText(c.getBalance());
    }
    
    private void nextButtonClicked() {
        // when next is clicked, step through the results list if a search was
        // made. if not, step through the whole list of customers
        Customer c = new Customer();
        if (results != null) {
            c = results.get(index += 1);
            firstNameField.setText(c.getFirstName());
            lastNameField.setText(c.getLastName());
            addressField.setText(c.getAddress());
            phoneNumberField.setText(c.getPhoneNumber());
            accountNumberField.setText(c.getAccountNumber());
            balanceField.setText(c.getBalance());
        } else {
            customers = customerDAO.getAll();
            c = customers.get(index += 1);
            firstNameField.setText(c.getFirstName());
            lastNameField.setText(c.getLastName());
            addressField.setText(c.getAddress());
            phoneNumberField.setText(c.getPhoneNumber());
            accountNumberField.setText(c.getAccountNumber());
            balanceField.setText(c.getBalance());
        }
    }
    
    private void previousButtonClicked() {
        // when previous is clicked, step through the results list if a search
        // was made. if not, step through the whole list of customers
        Customer c = new Customer();
        if (results != null) {
            c = results.get(index -= 1);
            firstNameField.setText(c.getFirstName());
            lastNameField.setText(c.getLastName());
            addressField.setText(c.getAddress());
            phoneNumberField.setText(c.getPhoneNumber());
            accountNumberField.setText(c.getAccountNumber());
            balanceField.setText(c.getBalance());
        } else {
            customers = customerDAO.getAll();
            c = customers.get(index -= 1);
            firstNameField.setText(c.getFirstName());
            lastNameField.setText(c.getLastName());
            addressField.setText(c.getAddress());
            phoneNumberField.setText(c.getPhoneNumber());
            accountNumberField.setText(c.getAccountNumber());
            balanceField.setText(c.getBalance());
        }
    }
    
    private void openButtonClicked() {
        // when open is clicked, if the current customer does not yet have an
        // account, open an account with their account number input, set balance
        // to zero, and set a default interest rate
        SwingValidator sv = new SwingValidator(this);
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String accountNumber = accountNumberField.getText();
        String balance = "0";
        String interestRate = "1";
        customers = customerDAO.getAll();
        Customer c = new Customer();
        
        // search through list of customers to ensure a first/last name match
        // before opening an account
        for (Customer x : customers) {
            c = x;
            if (c.getLastName().equals(lastName) && c.getFirstName().equals(firstName)) {
                int i = customers.indexOf(c);
                c = customers.get(i);
            }
        }
        
        // swing validator to display error message if customer has an existing
        // account
        if (sv.hasAccount(c)) {
            balanceField.setText(balance);
            c.setAccountNumber(accountNumber);
            c.setBalance(balance);
            c.setInterestRate(interestRate);
        }
    }
    
    private void withdrawButtonClicked() {
        // when withdraw is clicked, turn balance and withdrawl amount into
        // doubles, calculate, and display updated balance in field, and reset
        // withdrawl field before updating customer object
        String accountNumber = accountNumberField.getText();
        String balance = balanceField.getText();
        String amount = withdrawDepositField.getText();
        double balanceDbl = Double.parseDouble(balance);
        double amountDbl = Double.parseDouble(amount);
        double newBalance = balanceDbl - amountDbl;
        balance = String.valueOf(newBalance);
        balanceField.setText(balance);
        withdrawDepositField.setText("0");
        
        customers = customerDAO.getAll();
        Customer c = new Customer();
        for (Customer x : customers) {
            c = x;
            if (c.getAccountNumber().equals(accountNumber)) {
                int i = customers.indexOf(c);
                c = customers.get(i);
            }
        }
        c.setBalance(balance);
        customerDAO.update(c);
    }
    
    private void depositButtonClicked() {
        // when deposit is clicked, turn balance and deposit amount into doubles,
        // calculate, and display updated balance in field, and reset deposit
        // field before updating customer object
        String accountNumber = accountNumberField.getText();
        String balance = balanceField.getText();
        String amount = withdrawDepositField.getText();
        double balanceDbl = Double.parseDouble(balance);
        double amountDbl = Double.parseDouble(amount);
        double newBalance = balanceDbl + amountDbl;
        balance = String.valueOf(newBalance);
        balanceField.setText(balance);
        withdrawDepositField.setText("0");
        
        customers = customerDAO.getAll();
        Customer c = new Customer();
        for (Customer x : customers) {
            c = x;
            if (c.getAccountNumber().equals(accountNumber)) {
                int i = customers.indexOf(c);
                c = customers.get(i);
            }
        }
        c.setBalance(balance);
        customerDAO.update(c);
    }
    
    private void calcButtonClicked() {
        // when calculate interest button is clicked, get doubles out of the
        // interest rate, month input, and balance
        Customer c = new Customer();
        c = results.get(index);
        String interestRate = c.getInterestRate();
        double interestRateDbl = Double.parseDouble(interestRate);
        String balance = balanceField.getText();
        double balanceDbl = Double.parseDouble(balance);
        String month = interestMonthField.getText();
        double monthDbl = Double.parseDouble(month);
        int year = 12;
        // if input is invalid, reset field and don't calculate
        if (monthDbl < 1 || monthDbl > 13) {
            interestMonthField.setText("");
        }
        // calculate interest earned, convert to string, and display in field
        double interestDbl = (balanceDbl * (interestRateDbl / 100)) * monthDbl ;
        String interest = String.valueOf(interestDbl);
        calculatedInterestField.setText(interest);
    }
    
    public static void main(String[] args) { 
        java.awt.EventQueue.invokeLater(() -> {
            HardingFinalProject frame = new HardingFinalProject();
            frame.setVisible(true);
            customerDAO = new CustomerTextFile();
        });  
    } 
}
