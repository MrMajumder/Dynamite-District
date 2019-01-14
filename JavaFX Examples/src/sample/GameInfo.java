package sample;

public enum GameInfo {
    ScreenWidth (1000),
    ScreenHeight (1000),
    TileWidth (70),
    TileHeight (70),
    WorldWidth (7000),
    WorldHeight (7000);

    private final int value;

    GameInfo(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
