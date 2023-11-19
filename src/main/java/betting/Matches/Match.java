package betting.Matches;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Match {
    private UUID matchId;
    private BigDecimal rateA;
    private BigDecimal rateB;
    private MatchResult matchResult;
}
