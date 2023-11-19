package utilities;

import betting.Players.InputPlayerAction;
import betting.Players.Player;
import exceptions.FileException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OutputGenerator {

    /**
     * Generates a result file with information about legitimate players, illegitimate player actions,
     * and casino balance changes, and writes the output to the specified file.
     *
     * @param filename                The name of the file to be generated.
     * @param legitimatePlayers       A list of legitimate players to include in the result file.
     * @param illegitimatePlayersAction A map of illegitimate player actions to include in the result file.
     * @param casinoBalance           The final casino balance to include in the result file.
     * @throws FileException          If there is an error writing the output to the result file.
     */
    public static void generateResultFile(String filename, List<Player> legitimatePlayers, Map<UUID, InputPlayerAction> illegitimatePlayersAction, long casinoBalance) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {

            writeLegitimatePlayers(writer, legitimatePlayers);

            writer.write(System.lineSeparator());

            writeIllegitimatePlayers(writer, illegitimatePlayersAction);

            writer.write(System.lineSeparator());

            writeCasinoBalanceChanges(writer, casinoBalance);

        } catch (IOException e) {
            throw new FileException("Error writing output in the result.txt file");
        }
    }

    /**
     * Writes information about legitimate players to the provided BufferedWriter.
     *
     * @param writer            The BufferedWriter to which the player information will be written.
     * @param legitimatePlayers A list of legitimate players to be included in the output.
     * @throws FileException    If there is an error writing legitimate players data to the result file.
     */
    private static void writeLegitimatePlayers(BufferedWriter writer, List<Player> legitimatePlayers) {
        try {
            if (legitimatePlayers.isEmpty()) {
                writer.newLine();
            } else {
                legitimatePlayers.stream()
                        .sorted(Comparator.comparing(player -> player.getPlayerId()))
                        .forEach(player -> {
                            try {
                                UUID playerId = player.getPlayerId();
                                long balance = player.getAccountBalance();
                                BigDecimal winRate = player.getWinRate();

                                writer.write(String.format("%s %d %.2f%n", playerId, balance, winRate));
                            } catch (IOException e) {
                                throw new FileException("Error writing legitimate players data in the result.txt file");
                            }
                        });
            }
        } catch (IOException e) {
            throw new FileException("Error writing legitimate players data in the result.txt file");
        }
    }

    /**
     * Writes information about illegitimate player actions to the provided BufferedWriter.
     *
     * @param writer                   The BufferedWriter to which the illegitimate player actions will be written.
     * @param illegitimatePlayersAction A map of illegitimate player actions to be included in the output.
     * @throws FileException           If there is an error writing illegitimate player actions data to the result file.
     */
    private static void writeIllegitimatePlayers(BufferedWriter writer, Map<UUID, InputPlayerAction> illegitimatePlayersAction) {
        try {
            if (illegitimatePlayersAction.isEmpty()) {
                writer.newLine();
            } else {
                illegitimatePlayersAction.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByKey())
                        .forEach(entry -> {
                            try {
                                InputPlayerAction inputPlayerAction = entry.getValue();

                                writer.write(String.format("%s%n",inputPlayerAction.formatAsStringWithSpaceDelimiter()));
                            } catch (IOException e) {
                                throw new FileException("Error writing illegitimate player actions in the result.txt file");
                            }
                        });
            }
        } catch (IOException e) {
            throw new FileException("Error writing illegitimate player actions in the result.txt file");
        }

    }

    /**
     * Writes information about casino balance changes to the provided BufferedWriter.
     *
     * @param writer        The BufferedWriter to which the casino balance change information will be written.
     * @param casinoBalance The final casino balance to be included in the output.
     * @throws FileException If there is an error writing casino balance change data to the result file.
     */
    private static void writeCasinoBalanceChanges(BufferedWriter writer, long casinoBalance) {
        try {
            writer.write(String.format("%d%n", casinoBalance));
        } catch (IOException e) {
            throw new FileException("Error writing casino balance in the result.txt file");
        }
    }
}
