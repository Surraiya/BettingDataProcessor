import betting.BettingProcessor;
import betting.Players.PlayerManager;
import org.testng.annotations.BeforeClass;
import utilities.TxtFileReader;

import java.util.List;
import java.util.logging.Logger;

public class BaseTest {

    protected static final Logger logger = Logger.getLogger(TestPlayer1Actions.class.getName());
    protected static List<String> playerInfos;
    protected static BettingProcessor bettingProcessor = new BettingProcessor();

    @BeforeClass
    public void saveMatchInfo() {
        bettingProcessor.processMatchDataFile("src/main/resources/match_data.txt");
    }

    @BeforeClass(dependsOnMethods = "saveMatchInfo")
    public void players() {
        playerInfos = TxtFileReader.readInputFile("src/main/resources/player_data.txt");
        for (String playerAction : playerInfos) {
            PlayerManager.processPlayerActionData(playerAction);
        }
    }
}
