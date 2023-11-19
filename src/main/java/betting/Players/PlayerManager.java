package betting.Players;

import betting.Matches.Match;
import betting.Matches.MatchManager;
import betting.Matches.MatchSide;

import java.util.*;

import static utilities.PlayerDataField.*;

public class PlayerManager {
    private static final Map<UUID, Player> players = new LinkedHashMap<>();
    private static final Map<UUID, InputPlayerAction> illegitimatePlayerActions = new LinkedHashMap<>();
    private static final List<Player> legitimatePlayers = new ArrayList<>();


    /**
     * Creates a new player or returns an existing player based on the provided player ID.
     *
     * @param playerId The unique identifier of the player.
     * @return The player instance.
     */
    public static Player createPlayer(UUID playerId) {
        Player existingPlayer = players.get(playerId);

        if (existingPlayer != null) {
            return existingPlayer;
        } else {
            Player newPlayer = new Player(playerId);
            players.put(playerId, newPlayer);
            return newPlayer;
        }
    }

    /**
     * Processes the raw player action data, creates an InputPlayerAction object, and process the player action.
     *
     * @param playerActionData The raw player action data as a comma-separated string.
     * @return The processed InputPlayerAction object.
     */
    public static InputPlayerAction processPlayerActionData(String playerActionData) {
        String[] values = playerActionData.split(",", -1);

        replaceNullValues(values);

        UUID playerId = UUID.fromString(values[PLAYER_ID.getIndex()]);
        PlayerAction playerAction = getPlayerAction(values[PLAYER_ACTION.getIndex()]);
        UUID matchId = values[MATCH_ID.getIndex()].isEmpty() ? null : UUID.fromString(values[MATCH_ID.getIndex()]);
        int coinAmount = Integer.parseInt(values[COIN_AMOUNT.getIndex()]);
        MatchSide betSide = values[BET_SIDE.getIndex()].isEmpty() ? null : getBetSide(values[BET_SIDE.getIndex()]);

        Player player = createPlayer(playerId);

        InputPlayerAction inputPlayerAction = new InputPlayerAction(playerId, playerAction, matchId, coinAmount, betSide);

        processPlayerAction(player, inputPlayerAction);

        return inputPlayerAction;
    }

    /**
     * Replaces null values with empty strings in the given array.
     *
     * @param values The array of values.
     */
    private static void replaceNullValues(String[] values) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] == null) {
                values[i] = "";
            }
        }
    }

    /**
     * Processes a player action based on the specified input and updates the player's state.
     *
     * @param player            The player for whom the action is processed.
     * @param inputPlayerAction The input representing the player's action.
     */
    public static void processPlayerAction(Player player, InputPlayerAction inputPlayerAction) {
        switch (inputPlayerAction.getPlayerAction()) {
            case DEPOSIT:
                player.deposit(inputPlayerAction.getCoinAmount());
                break;
            case BET:
                if (Validator.isPlayerActionValid(player.getAccountBalance(), inputPlayerAction.getCoinAmount())) {
                    Match match = MatchManager.getMatchById(inputPlayerAction.getMatchId());
                    if (match == null) {
                        throw new IllegalArgumentException("Match not found for matchId: " + inputPlayerAction.getMatchId());
                    }
                    player.bet(match, inputPlayerAction.getCoinAmount(), inputPlayerAction.getBetSide());
                } else {
                    handleIllegitimatePlayerAction(player, inputPlayerAction);
                }
                break;
            case WITHDRAW:
                if (Validator.isPlayerActionValid(player.getAccountBalance(), inputPlayerAction.getCoinAmount())) {
                    player.withdraw(inputPlayerAction.getCoinAmount());
                } else {
                    handleIllegitimatePlayerAction(player, inputPlayerAction);
                }
                break;
        }
    }

    /**
     * Handles the first occurrence of an illegitimate player action by storing it in the
     * illegitimatePlayerActions map, associating it with the player's playerId.
     * Subsequent illegitimate actions by the same player will not be stored.
     *
     * @param player            The player involved in the illegitimate action.
     * @param inputPlayerAction The input representing the illegitimate player action.
     */
    private static void handleIllegitimatePlayerAction(Player player, InputPlayerAction inputPlayerAction) {
        if (!illegitimatePlayerActions.containsKey(player.getPlayerId())) {
            illegitimatePlayerActions.put(player.getPlayerId(), inputPlayerAction);
        }
    }

    /**
     * Converts a string representation of a player action to the corresponding PlayerAction enum.
     *
     * @param operation The string representation of the player action.
     * @return The corresponding PlayerAction enum.
     */
    public static PlayerAction getPlayerAction(String operation) {
        switch (operation.toLowerCase()) {
            case "deposit":
                return PlayerAction.DEPOSIT;
            case "withdraw":
                return PlayerAction.WITHDRAW;
            default:
                return PlayerAction.BET;
        }
    }

    /**
     * Converts a string representation of a bet side to the corresponding MatchSide enum.
     *
     * @param betSide The string representation of the bet side.
     * @return The corresponding MatchSide enum.
     */
    public static MatchSide getBetSide(String betSide) {
        if (betSide.equalsIgnoreCase("A")) {
            return MatchSide.A;
        }
        return MatchSide.B;
    }

    /**
     * Retrieves a list of legitimate players.
     *
     * @return The list of legitimate players.
     */
    public static List<Player> getLegitimatePlayers() {

        for (Player player : players.values()) {
            boolean isLegitimate = !illegitimatePlayerActions.containsKey(player.getPlayerId());

            if (isLegitimate) {
                legitimatePlayers.add(player);
            }
        }

        return legitimatePlayers;
    }

    /**
     * @return a map of the first illegitimate player action for each player.
     */
    public static Map<UUID, InputPlayerAction> getIllegitimatePlayerActions() {
        return illegitimatePlayerActions;
    }

}
