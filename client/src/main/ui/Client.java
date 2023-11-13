import net.ServerFacade;
import requests.LoginRequest;
import responses.LoginResponse;

import java.util.Scanner;
public class Client {
    private Scanner scanner = new Scanner(System.in);
    private static ServerFacade facade = new ServerFacade();

    public static void main(String[] args) {
        preLogin();
    }

    private static void preLogin() {
        System.out.println("******************" +
                "\nWelcome to 240 Chess Game!" +
                "\n");
        boolean continueLoop = true;
        do {
            displayPreLoginOptions();
            askForInput("Enter a number from 1 - 4");
            switch (inputNext()){
                case "1":
                    preLoginHelp();
                    break;
                case "2":
                    login();
                    break;
                case "3":
                    register();
                    break;
                case "4":
                    System.out.println("Thank you for playing!");
                    continueLoop = false;
                    break;
                default:
                    wrongInput("numbers from 1 - 4");
                    break;
            }
        } while (continueLoop);
    }

    private static void register() {
        String username = getInputWithPrompt("Please enter the username");
        String password = getInputWithPrompt("Please enter the password");
        String email = getInputWithPrompt("Please enter the email");

    }

    private static String getInputWithPrompt(String prompt) {
        askForInput(prompt);
        return inputNext();
    }

    private static void askForInput(String message) {
        System.out.print(message + ": ");
    }

    private static void preLoginHelp() {
        System.out.println("Type in your keyboard the numbers 1,2,3 or 4 and then press 'Enter' to start");
    }

    private static void displayPreLoginOptions() {
        System.out.println("Menu " +
                "\n 1. Help" +
                "\n 2. Login" +
                "\n 3. Register" +
                "\n 4. Quit\n");
    }

    private static void login() {
        String username = getInputWithPrompt("Please enter your username");
        String password = getInputWithPrompt("Please enter your password");

        LoginRequest request = new LoginRequest(username, password);
        //here do the login request with the information received
        LoginResponse loginResponse = facade.login(request);
        if(loginResponse.getMessage() != null || loginResponse == null){
            //there was an error
            System.out.println("There was a problem logging you in. ");
        } else {
            //it was successful
            System.out.println("Logged in successfully.");
            //postLogin();
        }
    }

    private static void wrongInput(String expectedInput) {
        System.out.println("Invalid input. Please enter the following input: " + expectedInput);
    }

    private static String inputNext() {
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }

    private static String inputNextLine() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

}
