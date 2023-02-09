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

        // entry point for the vending machine
    }


    public void printMainMenu() {
        System.out.println("(1) Display Vending Machine Items");
        System.out.println("(2) Purchase");
        System.out.println("(3) Exit");
        System.out.println("Please select your option");


        String menuInput = userInput.nextLine();

        if (menuInput.equals("1")) {
            printDisplayItems();

        } else if (menuInput.equals("2")) {
            printPurchaseMenu();
        } else {
            if (menuInput.equals("3")) {
                System.out.println("Thank you. Have a nice day.");
            }

        }
    }

    public void printPurchaseMenu() {
        System.out.println("(1) Feed Money");
        System.out.println("(2) Select Product");
        System.out.println("(3) Finish Transaction");
        String purchaseInput = userInput.nextLine();

        if (purchaseInput.equals("1")) {
            //need to do this section

        }
        if (purchaseInput.equals("2")) {
            printDisplayItems();

            System.out.println("Enter code Ex.(A1)");
            String itemCodeToPurchase = userInput.nextLine();
            if (inventory.containsKey(itemCodeToPurchase)) {
                inventory.get(itemCodeToPurchase);
            }


        }

    }



    private void printDisplayItems() {
        for (Map.Entry<String, Items> item : inventory.entrySet()) {
            Items anItem = item.getValue();
            System.out.println(anItem.getSlot() + " " + anItem.getName() + " " + anItem.getPrice() + " " + anItem.getType());
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
}
