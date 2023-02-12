package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import  java.time.LocalDateTime;

public class VendingMachineCLI {

    final Scanner userInput = new Scanner(System.in);
    Map<String, Items> inventory = new HashMap<>();

    public VendingMachineCLI() {
    }

    public static void main(String[] args) {
        VendingMachineCLI cli = new VendingMachineCLI();
        cli.run();
    }

    public void run() {
        readFromFile("C:vendingmachine.csv");
        printMainMenu();
        purchaseWorkflow();
        // entry point for the vending machine
    }

    public void printMainMenu() {
        boolean mainMenuSelection = true;
        while (mainMenuSelection) {
            System.out.println("Please select your option");
            System.out.println("(1) Display Vending Machine Items");
            System.out.println("(2) Purchase");
            System.out.println("(3) Exit");

            String menuInput = userInput.nextLine();
            if (menuInput.equals("1")) {
                printDisplayItems();
                printMainMenu();

            } else if (menuInput.equals("2")) {
                purchaseWorkflow();
            } else {
                if (menuInput.equals("3")) {
                    System.out.println("Thank you. Have a nice day.");
                    printMainMenu();
                }
            }
            mainMenuSelection = false;
        }
    }

    public void purchaseWorkflow() {
        MoneyManagement moneyHandle = new MoneyManagement();
        String giveChange= "Give Change: ";
        boolean purchasing = true;
        while (purchasing) {
            System.out.println("Current Money Provided: "  + "$"+ moneyHandle.getBalance());
            System.out.println("(1) Feed Money");
            System.out.println("(2) Select Product");
            System.out.println("(3) Finish Transaction");
            String purchaseInput = userInput.nextLine();

            if (purchaseInput.equals("1")) {
                addfunds(moneyHandle);
            } else if (purchaseInput.equals("2")) {
                sellItems(moneyHandle);
            } else if (purchaseInput.equals("3")) {
                //GIVE CHANGE////////////
                LocalDateTime currentDateAndTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
                String formatDateTime = currentDateAndTime.format(formatter);
                try(PrintWriter toLogFile = new PrintWriter(new FileOutputStream("C:\\Users\\Student\\workspace\\capstone-1-team-3\\capstone\\target\\log.txt", true))) {
                    toLogFile.println(formatDateTime +" "+ giveChange + "$" + moneyHandle.getBalance() + " " + " $0.00");
                    moneyHandle.EndTransaction();

                } catch(FileNotFoundException e) {
                    System.err.println("Can't open file");
                }


                purchasing = false;
            }
        }
        printMainMenu();

    }

    private void addfunds(MoneyManagement moneyHandle) {
        Log transactionLog = new Log();
        String feedMoney=" Feed Money: ";
        System.out.println("Please enter cash in $1 bills only");
        String input = userInput.nextLine();
        BigDecimal amountGiven = new BigDecimal(input);
        //TODO-validate the amount given


                          LocalDateTime currentDateAndTime = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
                    String formatDateTime = currentDateAndTime.format(formatter);
                    try(PrintWriter toLogFile = new PrintWriter(new FileOutputStream("C:\\Users\\Student\\workspace\\capstone-1-team-3\\capstone\\target\\log.txt", true))) {
                        toLogFile.println(formatDateTime + feedMoney + "$" + moneyHandle.credit(amountGiven) + "," + "$" +moneyHandle.getBalance());
                    } catch(FileNotFoundException e) {
                        System.err.println("Can't open file");
                    }

    }






    private void sellItems(MoneyManagement moneyHandle) {


        printDisplayItems();
        System.out.println("PLease enter the slot code!");

        try {
        String itemCodeToPurchase = userInput.nextLine();
        Items itemBeingPurchased = getItemsFromSlotCode(itemCodeToPurchase.toUpperCase());

            itemBeingPurchased.getSlot();


            if (itemBeingPurchased.getQuantityOnHand() == Items.getSoldOut()) {
                System.out.println("Sorry,that item is out of stock!");
            }

            if (itemBeingPurchased.getQuantityOnHand() != Items.getSoldOut()) {
                if (moneyHandle.getBalance().compareTo(itemBeingPurchased.getPrice()) >= 0) {
                    itemBeingPurchased.itemSold();
                    moneyHandle.debit(itemBeingPurchased.getPrice());
                    makeSound(itemBeingPurchased);

                    LocalDateTime currentDateAndTime = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
                    String formatDateTime = currentDateAndTime.format(formatter);
                    try (PrintWriter toLogFile = new PrintWriter(new FileOutputStream("C:\\Users\\Student\\workspace\\capstone-1-team-3\\capstone\\target\\log.txt", true))) {
                        toLogFile.println(formatDateTime + " " + itemBeingPurchased.getName() + " " + itemBeingPurchased.getSlot() + "" + " $" + itemBeingPurchased.getPrice() + "," + "$" + moneyHandle.getBalance());
                    } catch (FileNotFoundException e) {
                        System.err.println("Can't open file");
                    }

                } else if ((moneyHandle.getBalance().compareTo(itemBeingPurchased.getPrice()) <= 0)) {
                    System.out.println("Sorry, insufficient funds!");

                }

            }

        } catch (Exception e) {
            System.out.println("Please enter a valid item code.");
        }

    }

    private void makeSound(Items itemBeingPurchased) {
        if (itemBeingPurchased.getType().equals("Chip")) {
            System.out.println("Crunch, Crunch, Yum!");
        }
        else if (itemBeingPurchased.getType().equals("Candy")) {
            System.out.println("Munch Munch, Yum!");
        }
        else if (itemBeingPurchased.getType().equals("Drink")) {
            System.out.println("Glug Glug, Yum!");
        }
        else if (itemBeingPurchased.getType().equals("Gum")) {
            System.out.println("Chew Chew, Yum!");
        }
    }



    private void printDisplayItems() {
        for (Map.Entry<String, Items> item : inventory.entrySet()) {
            Items anItem = item.getValue();
            System.out.println(anItem.getSlot() + " " + anItem.getName() + " " + anItem.getPrice() + " " + anItem.getType() + " " + anItem.getQuantityOnHand());
        }
    }

    public void readFromFile(String filePath) {
        File vendingFile = new File(filePath);
        try (Scanner vendingItems = new Scanner(vendingFile)) {
            while (vendingItems.hasNextLine()) {
                String item = vendingItems.nextLine();
                String[] itemArr = item.split("\\|");
                String itemCode = itemArr[0];
                String itemName = itemArr[1];
                BigDecimal itemPrice = new BigDecimal(itemArr[2]);
                String itemType = itemArr[3];
                inventory.put(itemCode, new Items(itemCode, itemName, itemPrice, itemType));
            }

        } catch (FileNotFoundException e) {
            System.out.println("Items unavailable");

        }
    }

    //For referencing desired product purchase:
//    String itemCode = userInput.nextLine();
//    Items selected = inventory.get(itemCode);


    public Items getItemsFromSlotCode(String slotCode) {
        Items itemToPurchase;
        if (inventory.containsKey(slotCode)) {
            itemToPurchase = inventory.get(slotCode);
            return itemToPurchase;
        }
        return null;
    }
}


