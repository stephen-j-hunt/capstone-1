package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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

            } else if (menuInput.equals("2")) {
                purchaseWorkflow();
            } else {
                if (menuInput.equals("3")) {
                    System.out.println("Thank you. Have a nice day.");
                }
            }
            mainMenuSelection = false;
        }
    }

    public void purchaseWorkflow() {
        MoneyManagement moneyHandle = new MoneyManagement();
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
                moneyHandle.EndTransaction();

                purchasing = false;
            }
        }
        printMainMenu();

    }



    private void sellItems(MoneyManagement moneyHandle) {
        printDisplayItems();
        System.out.println("PLease enter the slot code!)");
        String itemCodeToPurchase = userInput.nextLine();
        Items itemBeingPurchased = getItemsFromSlotCode(itemCodeToPurchase.toUpperCase());
            if (itemBeingPurchased.getQuantityOnHand()==Items.getSoldOut()) {
                System.out.println("Sorry,that item is out of stock!");
            }
        if (itemBeingPurchased.getQuantityOnHand() != Items.getSoldOut()) {
            if (moneyHandle.getBalance().compareTo(itemBeingPurchased.getPrice()) >= 0) {
                itemBeingPurchased.itemSold();
                moneyHandle.debit(itemBeingPurchased.getPrice());
                makeSound(itemBeingPurchased);
            } else if ((moneyHandle.getBalance().compareTo(itemBeingPurchased.getPrice()) <= 0)) {
                System.out.println("Sorry, insufficient funds!");

            }

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

    private void addfunds(MoneyManagement moneyHandle) {
        System.out.println("Please enter cash in $1 bills only");
        String input = userInput.nextLine();
        BigDecimal amountGiven = new BigDecimal(input);
        //TODO-validate the amount given
        moneyHandle.credit(amountGiven);
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
