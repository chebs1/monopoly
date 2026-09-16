package Java.MONOPOLY;


public enum AssetEnum
{
    BOARD("Monopoly board.jpg"),
    SPLASHSCREEN("startScreen.jpg"),
    PLAYER1("player1.png"),
    PLAYER2("player2.png"),
    PLAYER3("player3.png"),
    PLAYER4("player4.png"),
    SETTINGS("settings.png"),
    DICE("dice.png"),
    ;
    
    private final String path;
    
    AssetEnum(String path)
    {
        this.path = path;
    }
    
    public String getPath()
    {
        return path;
    }
}
