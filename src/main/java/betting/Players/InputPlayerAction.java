package betting.Players;


import betting.Matches.MatchSide;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class InputPlayerAction {
    private UUID playerId;
    private PlayerAction playerAction;
    private UUID matchId;
    private int coinAmount;
    private MatchSide betSide;

    public String formatAsStringWithSpaceDelimiter() {
        return String.format("%s %s %s %d %s",
                playerId,
                playerAction,
                matchId,
                coinAmount,
                betSide);
    }
}
