package betting;

import betting.Matches.Match;
import betting.Matches.MatchResult;
import betting.Players.BetOutcome;
import betting.Matches.MatchSide;
import betting.Players.Player;
import betting.Players.PlayerBetInfo;

import java.math.BigDecimal;


public class BettingCalculator {

    /**
     * Calculates the outcome of a bet based on the match result and the player's chosen side.
     *
     * @param matchResult The result of the match (A, B, DRAW).
     * @param betSide     The side the player placed the bet on (A or B).
     * @return The outcome of the bet (WIN, LOSE, or DRAW).
     */
    public static BetOutcome calculateBetOutcome(MatchResult matchResult, MatchSide betSide) {
        switch (matchResult) {
            case A:
                return (betSide == MatchSide.A) ? BetOutcome.WIN : BetOutcome.LOSE;
            case B:
                return (betSide == MatchSide.B) ? BetOutcome.WIN : BetOutcome.LOSE;
            default:
                return BetOutcome.DRAW;
        }
    }

    /**
     * Calculates the winning amount for a bet based on the bet amount and the betting rate.
     *
     * @param betAmount The amount placed as the bet.
     * @param rate      The betting rate associated with the chosen match result.
     * @return The calculated winning amount as an integer.
     */
    public static int calculateWinningBetAmount(int betAmount, BigDecimal rate) {
        BigDecimal betAmountDecimal = BigDecimal.valueOf(betAmount);
        BigDecimal winningAmount = betAmountDecimal.multiply(rate);
        return winningAmount.intValue();
    }

    /**
     * Retrieves the betting rate for a given match result.
     *
     * @param match The match for which the betting rate is requested.
     * @return The betting rate associated with the match result, or null if the result is not recognized.
     */
    public static BigDecimal getBettingRate(Match match) {
        BigDecimal bettingRate = null;
        switch (match.getMatchResult()) {
            case A:
                bettingRate = match.getRateA();
                break;
            case B:
                bettingRate = match.getRateB();
                break;
        }
        return bettingRate;
    }

    /**
     * Calculates the result of a player's bet on a match, updating player statistics and account balance accordingly.
     *
     * @param player    The player placing the bet.
     * @param match     The match on which the player is betting.
     * @param betAmount The amount the player is betting.
     * @param betSide   The side the player is betting on (e.g., WIN, LOSE, DRAW).
     * @return A PlayerBetInfo object containing information about the bet result.
     */
    public static PlayerBetInfo calculateBetting(Player player, Match match, int betAmount, MatchSide betSide) {
        int betResultAmount = 0;

        long accountBalance = player.getAccountBalance();

        BetOutcome betOutcome = BettingCalculator.calculateBetOutcome(match.getMatchResult(), betSide);

        switch (betOutcome) {
            case WIN:
                player.incrementWinGame();
                BigDecimal winningRate = getBettingRate(match);
                betResultAmount = BettingCalculator.calculateWinningBetAmount(betAmount, winningRate);
                accountBalance += betResultAmount;
                player.setAccountBalance(accountBalance);
                break;
            case DRAW:
                accountBalance += betAmount;
                player.setAccountBalance(accountBalance);
                break;
            default:
                betResultAmount = betAmount;
        }

        return new PlayerBetInfo(betAmount, betResultAmount, betOutcome);
    }
}
