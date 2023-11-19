package utilities;

/**
 * Enum representing the fields in player data and their corresponding indices.
 * This enum provides named constants for the indices, offering a more readable and maintainable
 * way to reference specific fields when processing player data. It helps avoid hardcoded indices
 * and enhances code clarity by making the purpose of each field explicit.
 */
public enum PlayerDataField {
    PLAYER_ID(0),
    PLAYER_ACTION(1),
    MATCH_ID(2),
    COIN_AMOUNT(3),
    BET_SIDE(4);

    private final int index;

    PlayerDataField(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
