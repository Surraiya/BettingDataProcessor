package betting.Players;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerBetInfo {
    private int betAmount;
    private int betResultCoins;
    private BetOutcome outcome;
}
