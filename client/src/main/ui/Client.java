import java.util.Scanner;
public class Client {
    private Scanner scanner = new Scanner(System.in);

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

    private static void askForInput(String message) {
        System.out.print(message + ": ");
    }

    private static void preLoginHelp() {
        //TODO improve the login help
    }

    private static void displayPreLoginOptions() {
        System.out.println("Menu " +
                "\n 1. Help" +
                "\n 2. Login" +
                "\n 3. Register" +
                "\n 4. Quit\n");
    }

    private static void login() {
        //example
        System.out.println("Please enter your username: ");
        inputNext();
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
