import betting.Players.InputPlayerAction;
import betting.Players.Player;
import betting.Players.PlayerManager;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class TestOutputResult extends BaseTest{

    private List<Player> legitimatePlayers;

    @Test
    public void testIllegitimatePlayerAction() {
        logger.info("Illegitimate players represented by their first illegal operation");
        String expectedFirstIllegitimatePlayerAction = "4925ac98-833b-454b-9342-13ed3dfd3ccf WITHDRAW null 8093 null";
        AtomicReference<String> firstIllegitimatePlayerAction = new AtomicReference<>("");
        Map<UUID, InputPlayerAction> illegitimatePlayersAction = PlayerManager.getIllegitimatePlayerActions();
        illegitimatePlayersAction.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    InputPlayerAction inputPlayerAction = entry.getValue();
                    firstIllegitimatePlayerAction.set(inputPlayerAction.formatAsStringWithSpaceDelimiter());
                });
        Assert.assertEquals(firstIllegitimatePlayerAction.get(), expectedFirstIllegitimatePlayerAction, "Both string should be same");
    }

    @Test
    public void casinoBalancetest() {
        logger.info("Casino balance impact only on winning and losing bet of legitimate players");
        legitimatePlayers = PlayerManager.getLegitimatePlayers();
        long expectedCasinoBalance = 75;
        long casinoBalance = bettingProcessor.getCasinoBalance(legitimatePlayers);
        Assert.assertEquals(casinoBalance, expectedCasinoBalance, "Casino balance should be 75");
    }

    @Test
    public void testWinningRate() {
        logger.info("There is one legitimate player and the player has only won 1 time out of 7 bets");
        int totalBets = 0;
        int winningGame = 0;
        BigDecimal winRate = null;

        legitimatePlayers = PlayerManager.getLegitimatePlayers();
        for (Player player : legitimatePlayers) {
            totalBets = player.getTotalBets();
            winningGame = player.getTotalWinGame();
            winRate = player.getWinRate();
        }

        Assert.assertEquals(totalBets, 7, "Player should place 7 bets");
        Assert.assertEquals(winningGame, 1, "Player should only win 1 time");
        Assert.assertEquals(winRate, new BigDecimal("0.14").setScale(2, RoundingMode.DOWN), "The winning rate should be 1/7 = 0.14");
    }
}
