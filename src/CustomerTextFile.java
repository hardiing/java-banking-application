/**
   Murach, J. (2017). Murachs Java Programming, Training and Reference, 
   5th Edition, Fresno, CA: Mike Murach & Associates. Inc.
   Modifications by N. Harding, 2021
 */

import java.util.*;
import java.io.*;
import java.nio.file.*;

public final class CustomerTextFile implements DAO<Customer> {

    private List<Customer> customers = null;
    private Path customersPath = null;
    private File customersFile = null;

    private final String FIELD_SEP = "\t";

    public CustomerTextFile() {
        // initialize the class variables
        customersPath = Paths.get("customers.txt");
        customersFile = customersPath.toFile();
        customers = this.getAll();
    }

    @Override
    public List<Customer> getAll() {
        // if the customers file has already been read, don't read it again
        if (customers != null) {
            return customers;
        }

        customers = new ArrayList<>();

        // load the array list with Customer objects created from
        // the data in the file
        if(Files.exists(customersPath)) {
            // read from file
            try(BufferedReader in = new BufferedReader(
                                    new FileReader(customersFile))) {
                String line = in.readLine();
                while(line != null) { // while the line has content
                    // separate fields at /t
                    String[] fields = line.split(FIELD_SEP);
                    String firstName = fields[0];
                    String lastName = fields[1];
                    String address = fields[2];
                    String phoneNumber = fields[3];
                    String accountNumber = fields[4];
                    String balance = fields[5];
                    String interestRate = fields[6];
                    
                    // load customer objects into array list
                    Customer c = new Customer(firstName, lastName, address, phoneNumber,
                                    accountNumber, balance, interestRate);
                    customers.add(c);
                    line = in.readLine();
                }
            } catch (IOException e) { // print exception and return null if it occurs
                System.out.println(e);
                return null;
            }
        } else { // throw error if path doesn't exist
            System.out.println(customersPath.toAbsolutePath() + " doesn't exist.");
            return null;
        }
        return customers;
    }

    @Override
    public Customer get(String accountNumber) {
        for (Customer c : customers) {
            if (c.getAccountNumber().equals(accountNumber)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public boolean add(Customer c) {
        customers.add(c);
        return this.saveAll();
    }

    @Override
    public boolean delete(Customer c) {
        customers.remove(c);
        return this.saveAll();
    }

    @Override
    public boolean update(Customer newCustomer) {
        // get the old customer and remove it
        Customer oldCustomer = this.get(newCustomer.getAccountNumber());
        int i = customers.indexOf(oldCustomer);
        customers.remove(i);

        // add the updated customer
        customers.add(i, newCustomer);

        return this.saveAll();
    }

    private boolean saveAll() {
        // save the Customer objects in the array list to the file
        try (PrintWriter out = new PrintWriter(
                              new BufferedWriter(
                              new FileWriter(customersFile)))) {
            // write objects to file with tab in between fields
            for (Customer c : customers) {
                out.print(c.getFirstName() + FIELD_SEP);
                out.print(c.getLastName() + FIELD_SEP);
                out.print(c.getAddress() + FIELD_SEP);
                out.print(c.getPhoneNumber() + FIELD_SEP);
                out.print(c.getAccountNumber() + FIELD_SEP);
                out.print(c.getBalance() + FIELD_SEP);
                out.println(c.getInterestRate());
            }
            return true;
        } catch (IOException e) { // print exception and return false if it occurs
            System.out.println(e);
            return false;
        }
    }
}
