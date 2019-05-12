package nl.avans.sagrada.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.ToolCard;

public class ToolCardDao {
    private DBConnection dbConnection;

    /**
     * Constructor, Initializes DBConnection
     */
    public ToolCardDao() {
        dbConnection = new DBConnection();
    }

    /**
     * Returns all tool cards that are stored in the database as entries, belonging to a certain
     * game.
     *
     * @param game The game to which the tool cards belong
     * @return An ArrayList of tool cards that belong to this game
     */
    public ArrayList<ToolCard> getToolCardsOfGame(Game game) {
        ArrayList<ToolCard> list = new ArrayList<>();
        try {
            ResultSet rs = dbConnection.executeQuery(new Query(
                    "SELECT toolcard.* FROM toolcard INNER JOIN gametoolcard g on toolcard.idtoolcard = g.idtoolcard WHERE g.idgame=?",
                    "query"), new QueryParameter(QueryParameter.INT, game.getId()));
            while (rs.next()) {
                ToolCard toolCard = new ToolCard(rs.getInt("idtoolcard"), rs.getString("name"),
                        rs.getInt("seqnr"),
                        rs.getString("description"));
                list.add(toolCard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Returns all tool cards that are stored in the database as entries.
     *
     * @return An ArrayList containing all Toolcard entries from the database
     */
    public ArrayList<ToolCard> getAllToolCards() {
        ArrayList<ToolCard> list = new ArrayList<>();
        try {
            ResultSet rs = dbConnection.executeQuery(new Query("SELECT * FROM toolcard", "query"));
            while (rs.next()) {
                ToolCard toolCard = new ToolCard(rs.getInt("idtoolcard"), rs.getString("name"),
                        rs.getInt("seqnr"),
                        rs.getString("description"));
                list.add(toolCard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Returns the tool card that belongs to the given id.
     *
     * @param id The to be returned tool cards id
     * @return The tool card that belongs to the id entered as parameter
     */
    public ToolCard getToolCardById(int id) {
        ToolCard toolCard = new ToolCard();
        try {
            ResultSet rs =
                    dbConnection.executeQuery(new Query("SELECT * FROM toolcard WHERE idtoolcard=?",
                            "query", new QueryParameter(QueryParameter.INT, id)));
            if (rs.next()) {
                toolCard.setId(rs.getInt("idtoolcard"));
                toolCard.setName(rs.getString("name"));
                toolCard.setSeqnr(rs.getInt("seqnr"));
                toolCard.setDescription(rs.getString("description"));
            }
        } catch (Exception e) {
            toolCard = null;
            e.printStackTrace();
        }
        return toolCard;
    }

    /**
     * Adds a tool card to a game. Both the toolcard and the game that are subject to this method
     * are given as parameters.
     *
     * @param toolCard The tool card that should be added to a game
     * @param game The game to which the toolcard should be added
     */
    public void addToolCardToGame(ToolCard toolCard, Game game) {
        try {
            ResultSet rs = dbConnection.executeQuery(new Query(
                            "INSERT INTO gametoolcard (gametoolcard, idtoolcard, idgame) VALUES (?, ?, ?)",
                            "update"), new QueryParameter(QueryParameter.INT, getNextGameToolCardId()),
                    new QueryParameter(QueryParameter.INT, toolCard.getId()),
                    new QueryParameter(QueryParameter.INT, game.getId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that returns the next gametoolcard integer that is available in the database table
     * 'gametoolcard'. This allows a new row to be added to the table, as this method ensures that a
     * possible new entry is added to an empty space in the table.
     *
     * @return The next gametoolcard int, which has not yet been used by any existing database
     * entries
     */
    public int getNextGameToolCardId() {
        int gameToolCardId = 0;
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT MAX(gametoolcard) AS highestGameToolcardId FROM gametoolcard",
                            "query"));
            if (rs.next()) {
                gameToolCardId = rs.getInt("highestGameToolcardId") + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gameToolCardId;
    }

    /**
     * Returns the gametoolcard number that is linked to a toolcard and a game, the ids of which
     * have been given as parameters.
     *
     * @param toolcardId int
     * @param gameId int
     * @return The gametoolcard number belonging to a specific game and toolcard
     */
    public int getGameToolCardForToolCardId(int toolcardId, int gameId) {
        int gameToolCardId = 0;
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT * FROM gametoolcard WHERE idtoolcard=? AND idgame=?",
                            "query"),
                    new QueryParameter(QueryParameter.INT, toolcardId),
                    new QueryParameter(QueryParameter.INT, gameId));
            if (rs.next()) {
                gameToolCardId = rs.getInt("gametoolcard");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gameToolCardId;
    }

    /**
     * Checks if a toolcard has already received payment before. If the toolcard has received
     * payment before, the method will set a flag in the toolcard, notifying the game that this
     * toolcard has already recieved payment before. Otherwise it will set this flag to false.
     *
     * @param toolCard Toolcard
     * @param game Game
     */
    public void toolCardHasPayment(ToolCard toolCard, Game game) {
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT * FROM gamefavortoken WHERE gametoolcard=? AND idgame=?",
                            "query"),
                    new QueryParameter(QueryParameter.INT,
                            getGameToolCardForToolCardId(toolCard.getId(), game.getId())),
                    new QueryParameter(QueryParameter.INT, game.getId()));
            if (rs.next()) {
                if (rs.getInt("gametoolcard") == 0) {
                    toolCard.setHasBeenPaidForBefore(false);
                } else {
                    toolCard.setHasBeenPaidForBefore(true);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}