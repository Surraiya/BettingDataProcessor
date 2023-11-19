package betting.Matches;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static utilities.MatchDataField.*;

public class MatchManager {
    // Collection to store matches with their unique IDs
    private static final Map<UUID, Match> matches = new LinkedHashMap<>();
    private static final int EXPECTED_MATCH_DATA_LENGTH = 4;

    // Private constructor to prevent instantiation, as it's a utility class
    private MatchManager(){}

    /**
     * Processes a list of match data, creating Match objects and saving them.
     *
     * @param matchesData List of strings containing match data
     */
    public static void processMatchData(List<String> matchesData) {
        for(String matchInfo : matchesData) {
            Match match = processMatchInfo(matchInfo);

            if ((match != null)) {
                saveMatchInfo(match);
            } else {
                System.err.println("Invalid match data: " + matchInfo);
            }
        }
    }

    /**
     * Processes a single line of match data and creates a Match object.
     *
     * @param matchInfo String containing match information
     * @return Match object created from the provided information
     */
    private static Match processMatchInfo(String matchInfo) {
        String[] values = matchInfo.split(",");

        if(values.length == EXPECTED_MATCH_DATA_LENGTH) {
            UUID matchId = UUID.fromString(values[MATCH_ID_INDEX.getIndex()]);
            BigDecimal sideARate = new BigDecimal(values[SIDE_A_RATE.getIndex()]);
            BigDecimal sideBRate = new BigDecimal(values[SIDE_B_RATE.getIndex()]);
            MatchResult matchResult = getMatchResult(values[RESULT.getIndex()]);

            return new Match(matchId, sideARate, sideBRate, matchResult);
        } else {
            System.err.println("Unexpected number of elements in the match data file.");
            return null;
        }
    }

    /**
     * Retrieves a match by its unique ID.
     *
     * @param matchId Unique identifier of the match
     * @return Match object associated with the provided ID, or null if not found
     */
    public static Match getMatchById(UUID matchId){
        return matches.get(matchId);
    }

    /**
     * Maps a string representation of match result to the corresponding enum value.
     *
     * @param result String representation of the match result (A, B, or DRAW)
     * @return MatchResult enum value representing the match result
     */
    private static MatchResult getMatchResult(String result) {
        switch (result.toUpperCase()){
            case "A":
                return MatchResult.A;
            case "B":
                return MatchResult.B;
            default:
                return MatchResult.DRAW;
        }
    }

    /**
     * Saves a Match object in the collection using its unique ID as the key.
     *
     * @param match Match object to be saved
     */
    private static void saveMatchInfo(Match match){
        matches.put(match.getMatchId(), match);
    }
}