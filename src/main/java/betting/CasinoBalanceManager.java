package betting;

import betting.Players.BetOutcome;
import betting.Players.PlayerBetInfo;

import java.util.Map;
import java.util.UUID;

public class CasinoBalanceManager {
    private static long casinoBalance = 0;

    /**
     * Updates the casino balance based on the outcomes of player bets. If player loses increment casino balance,
     * If player wins decrement casino balance.
     *
     * @param bets A map containing player bet information with match outcomes.
     * @return The updated casino balance after processing the player bets.
     */
    public static long updateCasinoBalance(Map<UUID, PlayerBetInfo> bets) {
        for (Map.Entry<UUID, PlayerBetInfo> entry : bets.entrySet()) {
            PlayerBetInfo betInfo = entry.getValue();
            if (betInfo.getOutcome() == BetOutcome.LOSE) {
                casinoBalance += betInfo.getBetResultCoins();
            } else if (betInfo.getOutcome() == BetOutcome.WIN) {
                casinoBalance -= betInfo.getBetResultCoins();
            }
        }
        return casinoBalance;
    }
}
