package betting.Players;

public class Validator {

    /**
     * Checks if a player action is valid based on the available account balance.
     *
     * @param accountBalance The current balance in the player's account.
     * @param betAmount      The amount the player intends to bet or withdraw.
     * @return True if the player action is valid (betAmount <= accountBalance), otherwise false.
     */
    public static boolean isPlayerActionValid(long accountBalance, int betAmount) {
        return betAmount <= accountBalance;
    }
}
