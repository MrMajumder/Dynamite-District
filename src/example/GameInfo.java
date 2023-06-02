/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;


public enum GameInfo {
    ScreenWidth (980),
    ScreenHeight (980),
    TileWidth (70),
    TileHeight (70),
    WorldWidth (7000),
    WorldHeight (7000),
    PlayerWidth (150),
    PlayerHeight (150);

    private final int value;

    GameInfo(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
