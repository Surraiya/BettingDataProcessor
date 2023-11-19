import betting.Players.InputPlayerAction;
import betting.Players.Player;
import org.testng.Assert;
import org.testng.annotations.Test;


import static betting.Players.PlayerManager.createPlayer;
import static betting.Players.PlayerManager.processPlayerActionData;

public class TestPlayer1Actions extends BaseTest{
    String playerAction1 = "163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4,DEPOSIT,,4000,";
    String playerAction2 = "163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4,BET,abae2255-4255-4304-8589-737cdff61640,500,A";
    String playerAction3 = "163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4,BET,a3815c17-9def-4034-a21f-65369f6d4a56,200,A";
    String playerAction4 = "163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4,BET,2b20e5bb-9a32-4d33-b304-a9c7000e6de9,100,A";
    String playerAction5 = "163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4,BET,de0785e2-58cb-413c-8e4b-1d135857733b,300,B";
    String playerAction6 = "163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4,BET,292b3cd6-b463-4c98-94a7-468e03740af0,500,A";
    String playerAction7 = "163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4,BET,f407bca9-cf69-4343-a489-6cee72384297,50,A";
    String playerAction8 = "163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4,WITHDRAW,,200,";
    String playerAction9 = "163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4,WITHDRAW,,1000,";
    String playerAction10 = "163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4,BET,d6c8b5a4-31ce-4bf8-8511-206cfd693440,50,A";


    @Test
    public void testPlayerAction1() {
        //1. deposit
        InputPlayerAction inputPlayerAction = processPlayerActionData(playerAction1);
        Player player = createPlayer(inputPlayerAction.getPlayerId());
        logger.info("Account balance after deposit: " + player.getAccountBalance());
        Assert.assertEquals(player.getAccountBalance(), 4000, "Account balance should be 4000");

        //2.Bet(win) - winning amount = 725
        processPlayerActionData(playerAction2);
        logger.info("Account balance after first winning bet: "+ player.getAccountBalance());
        Assert.assertEquals(player.getAccountBalance(), 4225);

        //3.Bet(lose) - loosing amount = 200
        processPlayerActionData(playerAction3);
        logger.info("Account balance after losing: "+ player.getAccountBalance());
        Assert.assertEquals(player.getAccountBalance(), 4025);

        //4.Bet(draw) amount = 100 so no change
        processPlayerActionData(playerAction4);
        logger.info("Account balance after a draw match: "+ player.getAccountBalance());
        Assert.assertEquals(player.getAccountBalance(), 4025);

        //5.Bet(draw) amount = 300 so no change
        processPlayerActionData(playerAction5);
        logger.info("Account balance after a draw match: "+ player.getAccountBalance());
        Assert.assertEquals(player.getAccountBalance(), 4025);

        //6. Bet(lose) -losing amount = 500
        processPlayerActionData(playerAction6);
        logger.info("Account balance after losing: "+ player.getAccountBalance());
        Assert.assertEquals(player.getAccountBalance(), 3525);

        //7.bet(lose)-losing amount = 50
        processPlayerActionData(playerAction7);
        logger.info("Account balance after losing: "+ player.getAccountBalance());
        Assert.assertEquals(player.getAccountBalance(), 3475);

        //8. withdraw - 200
        processPlayerActionData(playerAction8);
        logger.info("Account balance after withdrawing: " + player.getAccountBalance());
        Assert.assertEquals(player.getAccountBalance(), 3275);

        //9. Withdraw- 1000
        processPlayerActionData(playerAction9);
        logger.info("Account balance after withdrawing: " + player.getAccountBalance());
        Assert.assertEquals(player.getAccountBalance(), 2275);

        //Bet(lose) - losing amount = 50
        processPlayerActionData(playerAction10);
        logger.info("Account balance after losing: " + player.getAccountBalance());
        Assert.assertEquals(player.getAccountBalance(), 2225);
    }
}
