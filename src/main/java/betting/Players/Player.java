package betting.Players;

import betting.BettingCalculator;
import betting.Matches.Match;
import betting.Matches.MatchSide;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
public class Player {
    private UUID playerId;
    private long accountBalance;
    private Map<UUID, PlayerBetInfo> bets = new HashMap<>();
    private int totalWinGame;
    private int totalBets;

    public Player(UUID playerId) {
        this.playerId = playerId;
        this.accountBalance = 0;
    }

    public void deposit(int coinAmount){
        accountBalance += coinAmount;
    }

    public void withdraw(int coinAmount){
        accountBalance -= coinAmount;
    }


    public void incrementWinGame() {
        totalWinGame++;
    }

    public void bet(Match match, int betAmount, MatchSide betSide){
        if (!bets.containsKey(match.getMatchId())) {
            totalBets++;

            accountBalance -= betAmount;

            PlayerBetInfo playerBetInfo = BettingCalculator.calculateBetting(this, match, betAmount, betSide);

            bets.put(match.getMatchId(), playerBetInfo);
        } else {
            System.err.println("Cannot place Bet as Already placed bet for this match");
        }
    }

    public BigDecimal getWinRate(){
        BigDecimal winGames = BigDecimal.valueOf(totalWinGame);
        BigDecimal bets = BigDecimal.valueOf(totalBets);

        return winGames.divide(bets, 2, RoundingMode.DOWN);
    }
}

