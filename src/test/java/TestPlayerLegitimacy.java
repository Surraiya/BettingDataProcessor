import betting.Players.InputPlayerAction;
import betting.Players.Player;
import betting.Players.PlayerManager;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class TestPlayerLegitimacy extends BaseTest{

    @Test
    public void testLegitimatePlayer() {
        List<Player> legitimatePlayers = PlayerManager.getLegitimatePlayers();
        logger.info("There should be only one legitimate player");
        Assert.assertTrue(legitimatePlayers.contains(UUID.fromString("163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4")), "The given player should be the legitimate player");
        Assert.assertFalse(legitimatePlayers.contains(UUID.fromString("4925ac98-833b-454b-9342-13ed3dfd3ccf")), "The given player should not be the legitimate player");
    }

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
}
