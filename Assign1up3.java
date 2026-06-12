/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.assign1up3;

/**
 *
 * @author User
 */

import java.util.Scanner;
import java.util.ArrayList;

// ==================== Message Class ====================
class Message {

    String recipient;
    String sender;
    String messageText;
    String flag;
    String messageHash;
    String messageID;

    public Message(String recipient, String sender, String messageText, String flag) {
        this.recipient = recipient;
        this.sender = sender;
        this.messageText = messageText;
        this.flag = flag;
        this.messageHash = createHash();
        this.messageID = createID();
    }

    public String createHash() {
        int total = 0;
        for (int i = 0; i < recipient.length(); i++) {
            total = total + recipient.charAt(i);
        }
        for (int i = 0; i < messageText.length(); i++) {
            total = total + messageText.charAt(i);
        }
        String hash = "" + total;
        return hash;
    }

    public String createID() {
        int id = (int) (Math.random() * 9000) + 1000;
        return "" + id;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSender() {
        return sender;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getFlag() {
        return flag;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getMessageID() {
        return messageID;
    }
}

// ==================== MessageManager Class ====================
class MessageManager {

    ArrayList<Message> sentMessages = new ArrayList<>();
    ArrayList<Message> disregardedMessages = new ArrayList<>();
    ArrayList<Message> storedMessages = new ArrayList<>();
    ArrayList<String> messageHashes = new ArrayList<>();
    ArrayList<String> messageIDs = new ArrayList<>();

    public String addMessage(String recipient, String sender, String text, String flag) {

        if (!recipient.startsWith("+")) {
            return "Cell number is not in the correct format, please ensure that the number includes a country code";
        }

        if (recipient.length() < 10) {
            return "Cell number is not in the correct format, please ensure that the number includes a country code";
        }

        if (text.length() > 250) {
            return "Type a message that is less than 250 characters";
        }

        Message newMessage = new Message(recipient, sender, text, flag);
        messageHashes.add(newMessage.getMessageHash());
        messageIDs.add(newMessage.getMessageID());

        if (flag.equals("Sent")) {
            sentMessages.add(newMessage);
            return "Message successfully sent.\nMessage Hash: " + newMessage.getMessageHash() + "\nMessage ID: " + newMessage.getMessageID();
        } else if (flag.equals("Stored")) {
            storedMessages.add(newMessage);
            return "Message successfully stored.\nMessage Hash: " + newMessage.getMessageHash() + "\nMessage ID: " + newMessage.getMessageID();
        } else if (flag.equals("Disregard")) {
            disregardedMessages.add(newMessage);
            return "Message disregarded.";
        } else {
            return "Error: invalid flag";
        }
    }

    public void displayStoredSendersAndRecipients() {
        if (storedMessages.size() == 0) {
            System.out.println("There are no stored messages");
            return;
        }

        System.out.println("Sender and Recipient of Stored Messages:");
        System.out.println("----------------------------------------");

        for (int i = 0; i < storedMessages.size(); i++) {
            System.out.println("Sender: " + storedMessages.get(i).getSender());
            System.out.println("Recipient: " + storedMessages.get(i).getRecipient());
            System.out.println();
        }
    }

    public void displayLongestStoredMessage() {
        if (storedMessages.size() == 0) {
            System.out.println("There are no stored messages");
            return;
        }

        Message longest = storedMessages.get(0);

        for (int i = 0; i < storedMessages.size(); i++) {
            if (storedMessages.get(i).getMessageText().length() > longest.getMessageText().length()) {
                longest = storedMessages.get(i);
            }
        }

        System.out.println("The longest message is: ");
        System.out.println(longest.getMessageText());
    }

    public void searchByMessageID(String id) {
        boolean found = false;

        for (int i = 0; i < storedMessages.size(); i++) {
            if (storedMessages.get(i).getMessageID().equals(id)) {
                System.out.println("Message found!");
                System.out.println("Recipient: " + storedMessages.get(i).getRecipient());
                System.out.println("Message: " + storedMessages.get(i).getMessageText());
                found = true;
            }
        }

        for (int i = 0; i < sentMessages.size(); i++) {
            if (sentMessages.get(i).getMessageID().equals(id)) {
                System.out.println("Message found!");
                System.out.println("Recipient: " + sentMessages.get(i).getRecipient());
                System.out.println("Message: " + sentMessages.get(i).getMessageText());
                found = true;
            }
        }

        if (found == false) {
            System.out.println("Message ID not found");
        }
    }

    public void searchByRecipient(String recipient) {
        boolean found = false;

        System.out.println("Messages sent to " + recipient + ":");

        for (int i = 0; i < sentMessages.size(); i++) {
            if (sentMessages.get(i).getRecipient().equals(recipient)) {
                System.out.println(sentMessages.get(i).getMessageText());
                found = true;
            }
        }

        for (int i = 0; i < storedMessages.size(); i++) {
            if (storedMessages.get(i).getRecipient().equals(recipient)) {
                System.out.println(storedMessages.get(i).getMessageText());
                found = true;
            }
        }

        if (found == false) {
            System.out.println("No messages found for that number");
        }
    }

    public void deleteByHash(String hash) {
        boolean found = false;

        for (int i = 0; i < sentMessages.size(); i++) {
            if (sentMessages.get(i).getMessageHash().equals(hash)) {
                System.out.println("Message: \"" + sentMessages.get(i).getMessageText() + "\" successfully deleted");
                sentMessages.remove(i);
                messageHashes.remove(hash);
                found = true;
                break;
            }
        }

        for (int i = 0; i < storedMessages.size(); i++) {
            if (storedMessages.get(i).getMessageHash().equals(hash)) {
                System.out.println("Message: \"" + storedMessages.get(i).getMessageText() + "\" successfully deleted");
                storedMessages.remove(i);
                messageHashes.remove(hash);
                found = true;
                break;
            }
        }

        if (found == false) {
            System.out.println("Hash not found");
        }
    }

    public void displayReport() {
        if (storedMessages.size() == 0) {
            System.out.println("No stored messages to display");
            return;
        }

        System.out.println("====== STORED MESSAGES REPORT ======");
        System.out.println();

        for (int i = 0; i < storedMessages.size(); i++) {
            System.out.println("Message Hash: " + storedMessages.get(i).getMessageHash());
            System.out.println("Message Identity: " + storedMessages.get(i).getMessageID());
            System.out.println("Sender: " + storedMessages.get(i).getSender());
            System.out.println("Recipient: " + storedMessages.get(i).getRecipient());
            System.out.println("Message: " + storedMessages.get(i).getMessageText());
            System.out.println("Flag: " + storedMessages.get(i).getFlag());
            System.out.println("------------------------------------");
        }
    }

    public ArrayList<Message> getSentMessages() {
        return sentMessages;
    }

    public ArrayList<Message> getStoredMessages() {
        return storedMessages;
    }

    public ArrayList<Message> getDisregardedMessages() {
        return disregardedMessages;
    }

    public ArrayList<String> getMessageHashes() {
        return messageHashes;
    }

    public ArrayList<String> getMessageIDs() {
        return messageIDs;
    }
}

// ==================== Main Class ====================
public class Assign1up3 {

    // save details of registered users
    static String storedUsername = "";
    static String storedPassword = "";
    static String storedFirstName = "";
    static String storedLastName = "";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // ===== REGISTRATION =====
        System.out.println("=== REGISTRATION ===");

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter username (max 5 chars, must include _): ");
        String username = scanner.nextLine();

        System.out.print("Enter password (min 8 chars, capital, number, special char): ");
        String password = scanner.nextLine();

        System.out.print("Enter cell phone number (+27XXXXXXXXX): ");
        String cellPhone = scanner.nextLine();

        String result = registerUser(username, password, cellPhone, firstName, lastName);
        System.out.println("\n" + result);

        if (!result.contains("successfully")) {
            System.out.println("Registration not successful. Restart program.");
            scanner.close();
            return;
        }

        // ====== LOGIN ======
        boolean isLoggedIn = false;

        while (!isLoggedIn) {

            System.out.println("\n=== LOGIN ===");

            System.out.print("Enter username: ");
            String loginUsername = scanner.nextLine();

            System.out.print("Enter password: ");
            String loginPassword = scanner.nextLine();

            if (loginUser(loginUsername, loginPassword)) {

                System.out.println("Successful login! Welcome " + storedFirstName + " " + storedLastName);
                isLoggedIn = true;

            } else {
                System.out.println("Login failed! Try again.\n");
            }
        }

        // ===== QUICKCHAT MENU =====
        MessageManager manager = new MessageManager();
        String senderNumber = cellPhone;
        int choice = 0;

        System.out.println("\nWelcome to QuickChat!");

        while (choice != 5) {

            System.out.println();
            System.out.println("Please select an option:");
            System.out.println("1) Send Message");
            System.out.println("2) Show Sent Messages");
            System.out.println("3) Show Report");
            System.out.println("4) Stored Messages");
            System.out.println("5) Quit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {

                System.out.print("Enter recipient cell number: ");
                String recipient = scanner.nextLine();

                System.out.print("Enter your message: ");
                String message = scanner.nextLine();

                System.out.print("Enter flag (Sent/Stored/Disregard): ");
                String flag = scanner.nextLine();

                String addResult = manager.addMessage(recipient, senderNumber, message, flag);
                System.out.println(addResult);

            } else if (choice == 2) {

                if (manager.getSentMessages().size() == 0) {
                    System.out.println("No sent messages");
                } else {
                    System.out.println("Sent Messages:");
                    for (int i = 0; i < manager.getSentMessages().size(); i++) {
                        System.out.println("To: " + manager.getSentMessages().get(i).getRecipient());
                        System.out.println("Message: " + manager.getSentMessages().get(i).getMessageText());
                        System.out.println();
                    }
                }

            } else if (choice == 3) {

                manager.displayReport();

            } else if (choice == 4) {

                storedMessagesMenu(manager, scanner);

            } else if (choice == 5) {

                System.out.println("Goodbye!");

            } else {

                System.out.println("Invalid choice, please try again");
            }
        }

        scanner.close();
    }

    // Check username
    public static boolean checkUserName(String username) {
        return username.length() <= 5 && username.contains("_");
    }

    // Check FOR password complexity
    public static boolean checkPasswordComplexity(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!]).{8,}$");
    }

    // Check FOR cellphone number
    public static boolean checkCellPhoneNumber(String cellPhone) {
        return cellPhone.matches("^\\+27\\d{9}$");
    }

    // Register user
    public static String registerUser(String username, String password,
            String cellPhone, String firstName, String lastName) {

        if (!checkUserName(username)) {
            return "Username is not correctly formatted.";
        }

        if (!checkPasswordComplexity(password)) {
            return "Password does not meet complexity requirements.";
        }

        if (!checkCellPhoneNumber(cellPhone)) {
            return "Cell phone number incorrectly formatted.";
        }

        storedUsername = username;
        storedPassword = password;
        storedFirstName = firstName;
        storedLastName = lastName;

        return "User registered successfully.";
    }

    // login user
    public static boolean loginUser(String username, String password) {
        return username.equals(storedUsername) && password.equals(storedPassword);
    }

    // stored messages sub-menu
    public static void storedMessagesMenu(MessageManager manager, Scanner input) {

        String option = "";

        while (!option.equals("G")) {

            System.out.println();
            System.out.println("Stored Messages Menu:");
            System.out.println("A) Display sender and recipient of all stored messages");
            System.out.println("B) Display the longest stored message");
            System.out.println("C) Search for a message by Identity");
            System.out.println("D) Search messages by recipient");
            System.out.println("E) Delete a message using message hash");
            System.out.println("F) Display report");
            System.out.println("G) Go back");
            System.out.print("Enter your choice: ");

            option = input.nextLine();

            if (option.equals("A")) {
                manager.displayStoredSendersAndRecipients();

            } else if (option.equals("B")) {
                manager.displayLongestStoredMessage();

            } else if (option.equals("C")) {
                System.out.print("Enter the message Identity: ");
                String id = input.nextLine();
                manager.searchByMessageID(id);

            } else if (option.equals("D")) {
                System.out.print("Enter the recipient number: ");
                String recipient = input.nextLine();
                manager.searchByRecipient(recipient);

            } else if (option.equals("E")) {
                System.out.print("Enter the message hash: ");
                String hash = input.nextLine();
                manager.deleteByHash(hash);

            } else if (option.equals("F")) {
                manager.displayReport();

            } else if (option.equals("G")) {
                System.out.println("Going back to main menu...");

            } else {
                System.out.println("Invalid option");
            }
        }
    }
}