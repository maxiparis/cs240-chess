package ui;

import chess.*;
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
import webSocketMessages.userCommands.LeaveMessage;
import webSocketMessages.userCommands.MakeMoveMessage;
import webSocketMessages.userCommands.ResignMessage;

import javax.swing.text.rtf.RTFEditorKit;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

public class Client implements ServerMessageObserver {
    public ServerFacade facade;
    private String usernameLoggedIn = "";
    private String authTokenLoggedIn = "";
    private Game[] gamesFromDB;
    private ChessGame.TeamColor currentTeamColor;
    private ChessGame currentGame = null;
    private int currentGameID;


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
                if(currentGame != null){ //there is a game going on
                    leaveGame();
                }
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
            currentGameID = gameID;
            printAlertMessage("You joined the game successfully.");
            gamePlayPlayer();
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
            currentGameID = gameID;
            printAlertMessage("You joined the game as observer successfully.");
            gamePlayObserver();
        }
    }

    private void gamePlayObserver() {
        boolean continueLoop = true;
        do {
            displayGameplayObserverOptions();
            askForInput("Enter a number from 1 - 2");
            switch (inputNext()){
                case "1":
                    gamePlayHelpObserver();
                    break;
                case "2":
                    if(leaveGame()) {
                        continueLoop = false;
                    };
                    break;
                case "3":
                    redrawChessBoard();
                    break;
                case "4":
                    highlightLegalMovements();
                    break;
                default:
                    wrongInput("numbers from 1 - 2");
                    break;
            }
        } while (continueLoop);
    }

    private void gamePlayHelpObserver() {
        System.out.println("Type in your keyboard the numbers 1,2,3 or 4 and then press 'Enter' to start");

    }

    private void displayGameplayObserverOptions() {
        System.out.println("Gameplay Observer Menu " +
                "\n 1. Help" +
                "\n 2. Leave" +
                "\n 3. Redraw board" +
                "\n 4. Highlight Legal Moves");
    }

    private void gamePlayPlayer() {
        boolean continueLoop = true;
        do {
            displayGameplayPlayerOptions();
            askForInput("Enter a number from 1 - 6");
            switch (inputNext()){
                case "1":
                    gamePlayHelp();
                    break;
                case "2":
                    redrawChessBoard();
                    break;
                case "3":
                    if(leaveGame()) {
                        continueLoop = false;
                    };
                    break;
                case "4":
                    makeMove();
                    break;
                case "5":
                    if(resignGame()) {
                        continueLoop = false;
                    };
                    break;
                case "6":
                    highlightLegalMovements();
                    break;
                default:
                    wrongInput("numbers from 1 - 6");
                    break;
            }
        } while (continueLoop);


    }

    private void gamePlayHelp() {
        System.out.println("Type in your keyboard the numbers 1,2,3,4,5 or 6 and then press 'Enter' to start");

    }

    private void highlightLegalMovements() {
        String positionUnformatted = getInputWithPrompt("What position do you want to check? (for help type h)");

        ChessPositionImpl positionToCheck = null;
        if (positionUnformatted.equals("h")) {
            System.out.println("Format your move in this way [column as letter][row as number], for example: b3");
            highlightLegalMovements();
        } else {
            positionToCheck=StringToChessPosition(positionUnformatted);
            if (positionToCheck == null) {
                wrongInput("Format your move in this way [column as letter][row as number], for example: b3");
                return;
            }
        }

        Set<ChessMoveImpl> validMoves = currentGame.validMoves(positionToCheck);
        loadGameHighlightingMoves(positionToCheck, validMoves);
    }

    private void loadGameHighlightingMoves(ChessPositionImpl positionToCheck, Set<ChessMoveImpl> validMoves) {
        BoardDrawer drawer = new BoardDrawer((ChessBoardImpl) currentGame.getBoard());
        if(currentTeamColor == null || currentTeamColor.equals(ChessGame.TeamColor.WHITE)){
            drawer.drawBoardWhiteHighlighting(positionToCheck, validMoves);
        } else if (currentTeamColor.equals(ChessGame.TeamColor.BLACK)) {
            drawer.drawBoardBlackHighlighting(positionToCheck, validMoves);
        }
    }

    private void redrawChessBoard() {
        loadGame();
    }

    private void makeMove() {
        String moveUnformatted = getInputWithPrompt("What move do you want to do? (for help type h)");
        try {
            if(moveUnformatted.equals("h")){
                System.out.println("Format your move in this way: [source]:[destine]:[promotion piece], for example: " +
                        "a7:a8:queen. If your move will not end in promotion, do not include a promotion piece: b3:b7");
                makeMove();
            } else {
                String[] splitString = moveUnformatted.split(":");

                String startAsString = null;
                String endAsString = null;
                String promotionPieceAsString = null;
                ChessPositionImpl startPosition = null;
                ChessPositionImpl endPosition = null;

                if(splitString.length == 2){ //no promotion
                    startAsString = splitString[0];
                    startPosition = StringToChessPosition(startAsString);

                    endAsString = splitString[1];
                    endPosition = StringToChessPosition(endAsString);

                    if(startPosition == null || endPosition == null){
                        wrongInput("Format your move in this way: [source]:[destine]:[promotion piece], " +
                                "for example: a7:a8:queen. If your move will not end in promotion, do not include a" +
                                " promotion piece: b3:b7");
                        return;
                    }

                    ChessMoveImpl move = new ChessMoveImpl(startPosition, endPosition);
                    MakeMoveMessage moveMessage = new MakeMoveMessage(authTokenLoggedIn, currentGameID, move);
                    facade.makeMoveWS(moveMessage);
                } else if (splitString.length == 3) { //with promotion
                    startAsString = splitString[0];
                    startPosition = StringToChessPosition(startAsString);

                    endAsString = splitString[1];
                    endPosition = StringToChessPosition(endAsString);

                    promotionPieceAsString = splitString[2];
                    ChessPiece.PieceType promotionPiece = StringToPromotionPiece(promotionPieceAsString);

                    if(startPosition == null || endPosition == null || promotionPiece == null){
                        wrongInput("Format your move in this way: [source]:[destine]:[promotion piece], " +
                                "for example: a7:a8:queen. If your move will not end in promotion, do not include a" +
                                " promotion piece: b3:b7");
                        return;
                    }

                    ChessMoveImpl move = new ChessMoveImpl(startPosition, endPosition, promotionPiece);
                    MakeMoveMessage moveMessage = new MakeMoveMessage(authTokenLoggedIn, currentGameID, move);
                    facade.makeMoveWS(moveMessage);
                } else {
                    wrongInput("Format your move in this way: [source]:[destine]:[promotion piece], " +
                            "for example: a7:a8:queen. If your move will not end in promotion, do not include a" +
                            " promotion piece: b3:b7");
                }
            }
        } catch (IOException e) {
            printAlertMessage("Error: " + e.getMessage());
        }
    }

    public ChessPiece.PieceType StringToPromotionPiece(String promotionPieceAsString) {
        try {
            switch (promotionPieceAsString.toLowerCase()){
                case "queen": {
                    return ChessPiece.PieceType.QUEEN;
                }
                case "bishop": {
                    return ChessPiece.PieceType.BISHOP;
                }
                case "rook": {
                    return ChessPiece.PieceType.ROOK;
                }
                case "knight": {
                    return ChessPiece.PieceType.KNIGHT;
                }
                default: {
                    return null;
                }
            }
        } catch (Exception e) {
            return null;
        }
    }

    public ChessPositionImpl StringToChessPosition(String positionAsString) {
        //"a5"   where a = column  and 5 = row
        try {
            if(positionAsString.length() != 2){
                return null;
            }
            int rowNumber = Integer.parseInt(String.valueOf(positionAsString.charAt(1)));

            if(rowNumber < 1 || rowNumber > 8){
                return null;
            }

            int columnNumber = 0;
            switch(positionAsString.charAt(0)){
                case 'a': {
                    columnNumber = 1;
                    break;
                }
                case 'b': {
                    columnNumber = 2;
                    break;
                }
                case 'c': {
                    columnNumber = 3;
                    break;
                }
                case 'd': {
                    columnNumber = 4;
                    break;
                }
                case 'e': {
                    columnNumber = 5;
                    break;
                }
                case 'f': {
                    columnNumber = 6;
                    break;
                }
                case 'g': {
                    columnNumber = 7;
                    break;
                }
                case 'h': {
                    columnNumber = 8;
                    break;
                }
                default: {
                    return null; //to let know it was incorrect
                }
            }

            return new ChessPositionImpl(rowNumber, columnNumber);
        } catch (Exception e) {
            return null;
        }
    }

    private boolean resignGame() {
        //if succesfull then make sure to make currentTeamColor is null or reseted
        String confirmation = getInputWithPrompt("Do you want to resign? (answer yes or no)");
        if(confirmation.toLowerCase().equals("yes")){
            ResignMessage message = new ResignMessage(authTokenLoggedIn, currentGameID);
            try {
                facade.resignWS(message);
                currentGame = null;
                currentGameID = 0;
                currentTeamColor = null;
                return true;
            } catch (IOException e) {
                System.out.println("There was a problem and you could not resign. ");
            }
        }
        return false;
    }

    private boolean leaveGame() {
        //if succesfull then make sure to make currentTeamColor is null or reseted
        LeaveMessage message = new LeaveMessage(authTokenLoggedIn, currentGameID);
        try {
            facade.leaveGameWS(message);
            printAlertMessage("You have left the game succesfully");
            currentTeamColor = null;
            currentGame = null;
            currentGameID = 0;
            return true;
        } catch (IOException e) {
            printAlertMessage("Leave Game error: " + e.getMessage());
            return false;
        }
    }

    private void displayGameplayPlayerOptions() {
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
                System.out.println();
                printAlertMessage(converted.getErrorMessage());
            }
            case LOAD_GAME -> {
                LoadGameMessage converted = (LoadGameMessage) notification;
                currentGame = converted.getGame();

                loadGame();
            }
        }
    }

    private void loadGame() {
        BoardDrawer drawer = new BoardDrawer((ChessBoardImpl) currentGame.getBoard());
        if(currentTeamColor == null || currentTeamColor.equals(ChessGame.TeamColor.WHITE)){
            drawer.drawBoardWhite();
        } else if (currentTeamColor.equals(ChessGame.TeamColor.BLACK)) {
            drawer.drawBoardBlack();
        }
        if(currentGame.getTeamTurn().equals(ChessGame.TeamColor.WHITE_WON)){
            printAlertMessage("White has won!. Game is over.");
        } else if (currentGame.getTeamTurn().equals(ChessGame.TeamColor.BLACK_WON)) {
            printAlertMessage("Black has won!. Game is over.");
        } else if (currentGame.getTeamTurn().equals(ChessGame.TeamColor.STALEMATE)) {
            printAlertMessage("This game is in stalemate. Game is over.");
        } else {
            System.out.println("Is " + currentGame.getTeamTurn() + "'s turn");
        }

    }
}
