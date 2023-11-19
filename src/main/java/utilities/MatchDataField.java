package utilities;

/**
 * Enum representing the fields in match data and their corresponding indices.
 * This enum provides named constants for the indices, offering a more readable and maintainable
 * way to reference specific fields when processing match data. It helps avoid hardcoded indices
 * and enhances code clarity by making the purpose of each field explicit.
 */
public enum MatchDataField {
    MATCH_ID_INDEX(0),
    SIDE_A_RATE(1),
    SIDE_B_RATE(2),
    RESULT(3);

    private final int index;

    MatchDataField(int index){
        this.index = index;
    }

    public int getIndex(){
        return index;
    }
}
