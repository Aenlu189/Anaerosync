package anaerosync;

import java.util.Scanner;

public class optionsMenu {
    public static Scanner input = new Scanner(System.in);
    private static final String GREEN = "\u001B[32m";
    private static final String BLUE = "\u001B[34m";
    private static final String RED = "\u001B[31m";
    private static final String MAGENTA = "\u001B[35m";
    private static final String WHITE = "\u001B[37m";

    public static void main(String[] args) {
        clearScreen();
        boolean running = true;

        while (running) {
            clearScreen();
            displayHeader();
            displayMenu();

            String choice = input.nextLine();
            clearScreen();

            switch(choice) {
                case "1":
                    startGame();
                    return;
                case "2":
                    displayCredits();
                    break;
                case "3":
                    running = confirmQuit();
                    break;
                default:
                    System.out.println(RED + """
                            Invalid choice. Please try again!""" + "\n");
                    sleep();
                    clearScreen();
            }
        }
        input.close();
    }

    private static void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("The thread was interrupted.");
        }
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void displayHeader() {
        System.out.println(GREEN + """
                ╔═════════════════════════╗
                ║        ANAEROSYNC       ║
                ╚═════════════════════════╝
                """);
    }

    public static void displayMenu() {
        System.out.print(BLUE + """
                1. Start
                2. Credits
                3. Quit
                
                Enter your choice:""" + " ");
    }

    public static void startGame(){

    }

    public static void displayCredits() {

    }

    public static boolean confirmQuit() { 
        System.out.print(RED + """ 
                Are you sure you want to quit? """ + "\n");
        System.out.print(BLUE + """
                    Type "Yes" or "No": """ + " ");
        String confirm = input.nextLine();         
        if (confirm.toLowerCase().charAt(0) == 'y') {
            clearScreen();
            System.out.println(GREEN + """
                ╔══════════════════════════════════════╗
                ║        Thank you for playing!        ║
                ╚══════════════════════════════════════╝
                    """ + " ");         
            sleep();         
            return false;
        }  
        return true;
    }
}