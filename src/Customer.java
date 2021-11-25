/**
   Murach, J. (2017). Murachs Java Programming, Training and Reference, 
   5th Edition, Fresno, CA: Mike Murach & Associates. Inc.
   Modifications by N. Harding, 2021
 */

public class Customer {

    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String accountNumber;
    private String balance;
    private String interestRate;

    public Customer() {
        this("", "", "", "", "", "", "");
    }

    public Customer(String firstName, String lastName, String address, String phoneNumber,
                    String accountNumber, String balance, String interestRate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.interestRate = interestRate;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public void setBalance(String balance) {
        this.balance = balance;
    }
    
    public String getBalance() {
        return balance;
    }
    
    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }
    
    public String getInterestRate() {
        return interestRate;
    }
}