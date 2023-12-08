package ui;

import chess.ChessBoardImpl;
import chess.ChessGame;
import model.Game;
import net.ServerFacade;
import net.ServerMessageObserver;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import requests.LoginRequest;
import requests.RegisterRequest;
import responses.*;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;

import java.util.Scanner;
public class Client implements ServerMessageObserver {
    public ServerFacade facade;
    private String usernameLoggedIn = "";
    private String authTokenLoggedIn = "";
    private Game[] gamesFromDB;
    private ChessGame.TeamColor currentTeamColor;
    private ChessGame currentGame = null;


    public Client() {
        facade = new ServerFacade(this);
    }

    public String getUsernameLoggedIn() {
        return usernameLoggedIn;
    }

    public void setUsernameLoggedIn(String user) {
        usernameLoggedIn = user;
    }

    public String getAuthTokenLoggedIn() {
        return authTokenLoggedIn;
    }

    public void setAuthTokenLoggedIn(String token) {
        authTokenLoggedIn = token;
    }

    public void run() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Perform actions or cleanup logic here
            if(!authTokenLoggedIn.equals("")){
                System.out.println();
                logout();
            }
        }));

        preLogin();
    }

    private void preLogin() {
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
                    printAlertMessage("Thank you for playing!");
                    continueLoop = false;
                    break;
                default:
                    wrongInput("numbers from 1 - 4");
                    break;
            }
        } while (continueLoop);
    }

    private void printAlertMessage(String message){
        System.out.println("---------------------\n" + message + "\n---------------------\n");
    }

    private void wrongInput(String expectedInput) {
        System.out.println("**** Invalid input. Please enter the following input: " + expectedInput);
    }

    private String inputNext() {
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }

    private String inputNextLine() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private String getInputWithPrompt(String prompt) {
        askForInput(prompt);
        return inputNext();
    }

    private String getInputWithPromptNextLine(String prompt) {
        askForInput(prompt);
        return inputNextLine();
    }

    private void askForInput(String message) {
        System.out.print(message + ": ");
    }

    private void preLoginHelp() {
        System.out.println("Type in your keyboard the numbers 1,2,3 or 4 and then press 'Enter' to start");


//        //JUST FOR TESTING WEBSOCKET
//        try {
//            var ws = new WebSocketCommunicator();
//            System.out.println("Sending message to server WS");
//            ws.send("now is working");
//        } catch (Exception e) {
//            System.out.println("The WSComunicator failed: " + e.getMessage());
//        }
    }

    private void displayPreLoginOptions() {
        System.out.println("Pre-Login Menu " +
                "\n 1. Help" +
                "\n 2. Login" +
                "\n 3. Register" +
                "\n 4. Quit\n");
    }

    private void register() {
        String username = getInputWithPrompt("Please enter the username");
        String password = getInputWithPrompt("Please enter the password");
        String email = getInputWithPrompt("Please enter the email");

        RegisterRequest request = new RegisterRequest(username, password, email);

        RegisterResponse response = facade.register(request);
        if(response.getMessage() != null){
            printAlertMessage("There was a problem registering you: " + response.getMessage());
        } else {
            setUsernameLoggedIn(response.getUsername());
            setAuthTokenLoggedIn(response.getAuthToken());
            printAlertMessage("Registered successfully. Welcome " + usernameLoggedIn);
            postLogin();
        }

    }

    private void login() {
        String username = getInputWithPrompt("Please enter your username");
        String password = getInputWithPrompt("Please enter your password");

        LoginRequest request = new LoginRequest(username, password);
        //here do the login request with the information received
        LoginResponse loginResponse = facade.login(request);
        if(loginResponse.getMessage() != null){
            printAlertMessage("There was a problem logging you in: " + loginResponse.getMessage());
        } else {
            setUsernameLoggedIn(loginResponse.getUsername());
            setAuthTokenLoggedIn(loginResponse.getAuthToken());
            printAlertMessage("Logged in successfully. Welcome " + usernameLoggedIn);
            postLogin();
        }
    }

    private void postLogin() {
        System.out.println("Your are now logged in.");
        boolean continueLoop = true;
        do {
            displayPostLoginOptions();
            askForInput("Enter a number from 1 - 6");
            switch (inputNext()){
                case "1":
                    postLoginHelp();
                    break;
                case "2":
                    logout();
                    continueLoop = false;
                    break;
                case "3":
                    createGame();
                    break;
                case "4":
                    listGames();
                    break;
                case "5":
                    joinGame();
                    break;
                case "6":
                    joinAsObserver();
                    break;
                default:
                    wrongInput("numbers from 1 - 6");
                    break;
            }
        } while (continueLoop);
    }


    private void displayPostLoginOptions() {
        System.out.println("Post-Login Menu " +
                "\n 1. Help" +
                "\n 2. Logout" +
                "\n 3. Create Game" +
                "\n 4. List Games" +
                "\n 5. Join Game" +
                "\n 6. Join as Observer");
    }

    private void postLoginHelp() {
        printAlertMessage("Type in your keyboard the numbers 1,2,3,4,5 or 6 and then press 'Enter' to select" +
                " that option. ");
    }

    private void logout() {
        LogoutResponse response = facade.logout(authTokenLoggedIn);
        if(response.getMessage() != null){
            printAlertMessage("There was a problem logging you out: " + response.getMessage());
        } else {
            authTokenLoggedIn = "";
            printAlertMessage("Logged out successfully.");
        }
    }

    private void createGame() {
        String gameName = getInputWithPromptNextLine("Enter the name for your new game");
        CreateGameRequest request = new CreateGameRequest(gameName);
        CreateGameResponse response = facade.createGame(request, authTokenLoggedIn);
        if(response.getMessage() != null){
            printAlertMessage("There was a problem creating a new game: " + response.getMessage());
        } else {
            printAlertMessage("Game " + gameName + " created successfully.");
            //take the user to the game play
        }
    }

    private void listGames() {
        ListGamesResponse response = facade.listGames(authTokenLoggedIn);

        if(response.getMessage() != null){
            printAlertMessage("There was a problem listing all the games: " + response.getMessage());
        } else {
            printAlertMessage("Available games: ");
            System.out.printf("%-5s %-20s %-20s %-20s%n", "#", "Game Name", "White User", "Black User");
            System.out.println("---------------------------------------------------------");

            gamesFromDB = new Game[response.getGames().size()];
            int index = 0;
            for (Game game : response.getGames()) {
                gamesFromDB[index] = game;
                index++;
            }

            for (int i = 0; i < gamesFromDB.length; i++) {
                String whiteUser = gamesFromDB[i].getWhiteUsername();
                String blackUser = gamesFromDB[i].getBlackUsername();
                if (whiteUser == null){
                    whiteUser = "-";
                }

                if (blackUser == null){
                    blackUser = "-";
                }


                System.out.printf("%-5s %-20s %-20s %-20s%n", i, gamesFromDB[i].getGameName(), whiteUser, blackUser);
            }
            System.out.println("");
        }
    }



    private void joinGame() {
        listGames();
        int gameID = 0;
        ChessGame.TeamColor color = null;

        boolean gameNumberContinue = true;
        while (gameNumberContinue) {
            String gameNumberChosen = getInputWithPrompt("Which game do you want to join? (Enter game number or q " +
                    "to go to previous menu)");

            if (gameNumberChosen.equals("q")){
                return;
            }

            try {
                int indexOfGameChosen = Integer.parseInt(gameNumberChosen);
                gameID = gamesFromDB[indexOfGameChosen].getGameID();
                currentGame = gamesFromDB[indexOfGameChosen].getGame();
                gameNumberContinue = false;
            } catch (Exception e){
                printAlertMessage("Invalid input. Error: " + e.getMessage());
            }
        }

        boolean gameTeamContinue = true;
        while (gameTeamContinue){
            String colorChosen = getInputWithPrompt("What color would you like to be? (Enter 1 for white or 0 for black)");
            if (colorChosen.equals("1")) {
                color = ChessGame.TeamColor.WHITE;
                gameTeamContinue = false;
            } else if (colorChosen.equals("0")){
                color = ChessGame.TeamColor.BLACK;
                gameTeamContinue = false;
            } else {
                wrongInput("1 for white or 0 for black");
            }
        }

        JoinGameRequest request = new JoinGameRequest(color, gameID);
        JoinGameResponse response = facade.joinGame(request, authTokenLoggedIn);

        if(response.getMessage() != null){
            printAlertMessage("There was a problem joining the game: " + response.getMessage());
        } else {
            //keep copy of the team the player is playing
            currentTeamColor= color;
            printAlertMessage("You joined the game successfully.");
            gamePlay();
        }

    }

    private void joinAsObserver() {
        listGames();
        int gameID = 0;

        boolean gameNumberContinue = true;
        while (gameNumberContinue) {
            String gameNumberChosen = getInputWithPrompt("Which game do you want to join? (Enter game number or q " +
                    "to go to previous menu)");

            if (gameNumberChosen.equals("q")){
                return;
            }

            try {
                //try to convert to int
                //try to get the gameID from the gamesFromDB
                int indexOfGameChosen = Integer.parseInt(gameNumberChosen);
                gameID = gamesFromDB[indexOfGameChosen].getGameID();
                currentGame = gamesFromDB[indexOfGameChosen].getGame();
                gameNumberContinue = false;
            } catch (Exception e){
                printAlertMessage("Invalid input. Error: " + e.getMessage());
            }
        }

        JoinGameRequest request = new JoinGameRequest(null, gameID);
        JoinGameResponse response = facade.joinGame(request, authTokenLoggedIn);

        if(response.getMessage() != null){
            printAlertMessage("There was a problem joining (as observer) the game: " + response.getMessage());
        } else {
            printAlertMessage("You joined the game as observer successfully.");
            gamePlay();
        }
    }

    private void gamePlay() {
        boolean continueLoop = true;
        do {
            displayGameplayOptions();
            askForInput("Enter a number from 1 - 6");
            switch (inputNext()){
                case "1":
                    //gamePlayHelp();
                    break;
                case "2":
                    //redrawChessBoard();
                    continueLoop = false;
                    break;
                case "3":
                    leaveGame();
                    break;
                case "4":
//                    makeMove();
                    break;
                case "5":
                    resignGame();
                    break;
                case "6":
                    //highlightLegalMovements();
                    break;
                default:
                    wrongInput("numbers from 1 - 6");
                    break;
            }
        } while (continueLoop);


    }

    private void resignGame() {
        //if succesfull then make sure to make currentTeamColor is null or reseted
    }

    private void leaveGame() {
        //if succesfull then make sure to make currentTeamColor is null or reseted
    }

    private void displayGameplayOptions() {
        System.out.println("Gameplay Menu " +
                "\n 1. Help" +
                "\n 2. Re-draw chess board" +
                "\n 3. Leave" +
                "\n 4. Make move" +
                "\n 5. Resign" +
                "\n 6. Highlight legal movements");
    }

    @Override
    public void notify(ServerMessage notification) {
        switch (notification.getServerMessageType()){
            case NOTIFICATION -> {
                NotificationMessage converted = (NotificationMessage) notification;
                System.out.println(converted.getMessage());
            }
            case ERROR -> {
                ErrorMessage converted = (ErrorMessage) notification;
                System.out.println(converted.getErrorMessage());
            }
            case LOAD_GAME -> {
                LoadGameMessage converted = (LoadGameMessage) notification;
                currentGame = converted.getGame();

                BoardDrawer drawer = new BoardDrawer((ChessBoardImpl) currentGame.getBoard());
                if(currentTeamColor.equals(ChessGame.TeamColor.WHITE)){
                    drawer.drawBoardWhite();
                } else if (currentTeamColor.equals(ChessGame.TeamColor.BLACK)) {
                    drawer.drawBoardBlack();
                }
            }
        }
    }
}
