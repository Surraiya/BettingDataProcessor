package betting;

import betting.Matches.MatchManager;
import betting.Players.InputPlayerAction;
import betting.Players.Player;
import betting.Players.PlayerManager;
import lombok.Data;
import utilities.OutputGenerator;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static utilities.TxtFileReader.readInputFile;

@Data
public class BettingProcessor {
    private long finalCasinoBalance;

    /**
     * Processes the match data from a specified file, initiating the extraction and handling of match information.
     *
     * @param matchesDataFileName The name of the file containing match data.
     */
    public void processMatchDataFile(String matchesDataFileName) {
        List<String> matchesData = readInputFile(matchesDataFileName);

        MatchManager.processMatchData(matchesData);
    }

    /**
     * Processes player data from a specified file, iterating through each line representing a player action
     * and delegating the processing of each action to the PlayerManager.
     *
     * @param playersDataFileName The name of the file containing player data.
     */
    public void processPlayerDataFile(String playersDataFileName) {
        List<String> playersData = readInputFile(playersDataFileName);

        for (String playerAction : playersData) {
            PlayerManager.processPlayerActionData(playerAction);
        }
    }

    /**
     * Calculates the final casino balance based on the bets of legitimate players.
     *
     * @param legitimatePlayers A list of legitimate players whose bets contribute to the casino balance.
     * @return The calculated final casino balance.
     */
    public long getCasinoBalance(List<Player> legitimatePlayers) {
        for (Player legitimatePlayer : legitimatePlayers) {
            finalCasinoBalance = CasinoBalanceManager.updateCasinoBalance(legitimatePlayer.getBets());
        }
        return finalCasinoBalance;
    }

    /**
     * Generates a result file based on the data of legitimate and illegitimate player actions.
     *
     * @param filename The name of the file to be generated.
     */
    public void generateResultFile(String filename) {
        List<Player> legitimatePlayers = PlayerManager.getLegitimatePlayers();
        Map<UUID, InputPlayerAction> illegitimatePlayersAction = PlayerManager.getIllegitimatePlayerActions();
        finalCasinoBalance = getCasinoBalance(legitimatePlayers);

        OutputGenerator.generateResultFile(filename, legitimatePlayers, illegitimatePlayersAction, finalCasinoBalance);
    }
}
