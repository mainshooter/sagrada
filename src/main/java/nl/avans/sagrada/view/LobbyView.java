package nl.avans.sagrada.view;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.controller.AccountController;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.Invite;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class LobbyView extends BorderPane implements ViewInterface {
    private AccountController accountController;
    private ArrayList<Game> games;
    private ArrayList<Invite> invites;
    
    private InviteOverviewView inviteOverview;
    private GameOverviewView gameOverview;
    private Button newGameButton;
    private Button logoutButton;
    
    private final int BUTTON_WIDTH = 150;
    private final int BUTTON_HEIGHT = 40;
    
    /**
     * Constructor
     * @param accountController
     */
    public LobbyView(AccountController accountController) {
        this.accountController = accountController;
        setPrefSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
    }
    
    /**
     * Set all the invites that need to be presented
     * @param invites
     */
    public void setInvites(ArrayList<Invite> invites) {
        this.invites = invites;
    }
    
    /**
     * Set all the game the current account has
     * @param games
     */
    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    @Override
    public void render() {
        buildInviteOverview();
        buildGamesOverview();
        buildNewGameBtn();
        buildLogout();
        
        VBox vbox = new VBox();
        Label inviteLabel = new Label("Invites van spelers");
        Label gameOverviewLabel = new Label("Je openstaande spellen");
        vbox.getChildren().addAll(inviteLabel, inviteOverview, gameOverviewLabel, gameOverview);
        setLeft(vbox);
        setCenter(newGameButton);
        setRight(logoutButton);
    }
    
    /**
     * Build the button to make a new game
     */
    private void buildNewGameBtn() {
        newGameButton = new Button("Maak nieuw spel");
        newGameButton.setOnAction(e->accountController.setupNewGame());
        newGameButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
    }
    
    /**
     * Builds the invite overview
     */
    private void buildInviteOverview() {
        inviteOverview = new InviteOverviewView(accountController);
        inviteOverview.setInvites(invites);
        inviteOverview.render();
    }
    
    /**
     * Builds the overview of all the games
     */
    private void buildGamesOverview() {
        gameOverview = new GameOverviewView(accountController);
        gameOverview.setGames(games);
        gameOverview.render();
    }
    
    /**
     * Builds to button to logout
     */
    private void buildLogout() {
        logoutButton = new Button("Logout");
        logoutButton.setOnAction(e->accountController.logout());
    }
}