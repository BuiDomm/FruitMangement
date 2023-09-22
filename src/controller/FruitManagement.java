/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import Common.Library;
import java.util.ArrayList;
import java.util.Hashtable;
import model.Fruit;
import model.Order;
import view.Menu;

public class FruitManagement extends Menu<String> {

    static String[] mc = {"Create Fruit", "View Orders", "Shopping (for buyer)"};
    Library lib = new Library();
    ArrayList<Fruit> listFruit = new ArrayList<>();

    Hashtable<String, ArrayList<Order>> hashTableFruit = new Hashtable<>();

    public FruitManagement() {
        super("=======================FRUIT MANAGEMENT====================", mc, "Exit");
    }

    @Override
    public void execute(int n) {
        switch (n) {
            case 1: {
                inputFruit();
                break;
            }
            case 2: {
                listOrderItem();
                break;
            }
            case 3: {
                buyItem();
                break;
            }
        }
    }

    public int autoCreasingID() {
        int id = 0;
        if (listFruit.size() == 0) {
            return 1;
        } else {
            for (Fruit f : listFruit) {
                if (f.getId() == listFruit.size()) {
                    id = f.getId() + 1;
                }
            }
        }
        return id;
    }

    public void inputFruit() {
        while (true) {
            int id = autoCreasingID();
            String name = lib.getValue("Enter fruit name: ");
            double price = lib.getDouble("Enter fruit price");
            int quantity = lib.checkInt("Enter fruit quantity", 1, 100);
            String origin = lib.getValue("Enter fruit origin: ");
            listFruit.add(new Fruit(id, name, price, quantity, origin));
            String con = lib.getValue("Do you want to continue Y/N:");
            if (con.equals("Y")) {
                continue;
            } else {
                return;
            }
        }
    }

    public void listOrderItem() {
        if (hashTableFruit.isEmpty()) {
            System.out.println("No Order");
            return;
        }
        for (String name : hashTableFruit.keySet()) {
            System.out.println("Customer: " + name);
            ArrayList<Order> arrayOrder = hashTableFruit.get(name);
            displayListOrder(arrayOrder);
        }
    }

    public void displayListFruit() {
        System.out.println("======================LIST FRUIT WE HAVE=============================");
        System.out.printf("%-7s|%-20s|%-9s|%-15s|%-10s\n", " ++ Item ++ ", "++ Fruit Name ++ ", "++ Origin ++ ", "++ Price ++", "++ Quantity ++| ");
        for (Fruit f : listFruit) {
            if (f.getQuantity() <= 0) {
                continue;
            }
            System.out.printf("%-14s%-20s%-17s%-15f%-10d\n", f.getId(), f.getName(), f.getOrigin(), f.getPrice(), f.getQuantity());
        }
    }

    public void buyItem() {
        while (true) {
            if (listFruit.isEmpty()) {
                System.out.println("List is empty.");
                return;
            }
            displayListFruit();
            int item = lib.getInt("Select item:", 1, listFruit.size());
            System.out.println("You selected:" + listFruit.get(item - 1).getName());
            int quantityOrder = lib.checkInt("Please input quantity", 1, 100);
            ArrayList<Order> listOrder = new ArrayList<>();
            for (Fruit f : listFruit) {
                if (item == f.getId()) {
                    int id = f.getId();
                    String name = f.getName();
                    double price = f.getPrice();
                    int quantity = f.getQuantity();
                    if (quantityOrder > quantity) {
                        System.out.println("Quantity Order more than quantity");
                        buyItem();
                    } else {
                        quantity = quantity - quantityOrder;
                        f.setQuantity(quantity);
                        listOrder.add(new Order(id, name, quantityOrder, price));
                        displayListOrder(listOrder);
                        String customer = lib.getValue("Enter Customer of name: ");
                        hashTableFruit.put(customer, listOrder);
                        System.out.println("Add Successfull");
                    }
                    break;
                }
            }
            String con = lib.getValue("Do you want to continue Y/N:");
            if (con.equalsIgnoreCase("y")) {
                continue;
            }
            else if (con.equalsIgnoreCase("n")) {
                return;
            }
        }
    }

    private void displayListOrder(ArrayList<Order> listOrder) {
        double total = 0;
        System.out.printf("%-10s|%-15s|%-9s|%-5s\n", " Product", "Quantity  ", "Price  ", "Amount");
        for (Order o : listOrder) {
            System.out.printf("%-10s%-18d%-9s%-5f\n", o.getId(), o.getQuanlity(), o.getPrice() + "$", o.getPrice() * o.getQuanlity());
            total += o.getPrice() * o.getQuanlity();
        }
        System.out.println("Total: " + total + "$");
    }

}
