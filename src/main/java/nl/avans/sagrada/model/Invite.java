package nl.avans.sagrada.model;

public class Invite {
    private Account invitedAccount;
    private Game game;
    private boolean accepted;
    private boolean denied;
    private Player player;
    
    public Invite() {
        accepted = false;
        denied = false;
    }

    
    /**
     * Set the account that the invite is for
     * @param account
     */
    public void setInvitedAccount(Account account) {
        invitedAccount = account;
    }
    
    /**
     * Set the game that is connected to the invite
     * @param game
     */
    public void setGame(Game game) {
        this.game = game;
    }
    
    /**
     * Accepts the invite
     */
    public void acceptInvite() {
        accepted = true;
        denied = false;
    }
    
    /**
     * Denies the invite request
     */
    public void denyInvite() {
        accepted = false;
        denied = true; 
    }
    
    /**
     * Gets the status of the invite as a String
     * @return String
     */
    public String getStatus() {
        if (accepted) {
            return "accepted";
        }
        else if (denied) {
            return "refused";
        }
        else {
            return "challengee";
        }
    }
    
    /**
     * Checks if the invite has not been responded to
     * @return boolean
     */
    public boolean isPending() {
        return (!accepted && !denied);
    }
    
    /**
     * Get the account that is invited
     * @return Account
     */
    public Account getInvitedAccount() {
        return invitedAccount;
    }
    
    /**
     * Get the game that the invite is for
     * @return Game
     */
    public Game getGame() {
        return game;
    }

    /**
     * Sets a player for who the invite is
     * @param player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
  
    /**
     * Gets the player that the invite is for
     * @return
     */
    public Player getPlayer() {
        return player;
    }
}
