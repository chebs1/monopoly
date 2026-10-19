# Proposed source code

These replacements are awaiting approval. Original files remain unchanged.


## AssetEnum.java

```java
package Java.MONOPOLY;

public enum AssetEnum {
    BOARD("Monopoly board.jpg"),
    SPLASHSCREEN("startScreen.jpg"),
    PLAYER1("player1.png"),
    PLAYER2("player2.png"),
    PLAYER3("player3.png"),
    PLAYER4("player4.png"),
    SETTINGS("settings.png"),
    DICE("dice.png");

    private final String path;
    AssetEnum(String path) { this.path = path; }
    public String getPath() { return path; }
}
```

## AssetManager.java

```java
package Java.MONOPOLY;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public final class AssetManager {
    private AssetManager() { }
    public static BufferedImage loadImage(AssetEnum asset) throws IOException {
        return loadImage(asset.getPath());
    }
    public static BufferedImage loadImage(String path) throws IOException {
        try (InputStream input = AssetManager.class.getResourceAsStream("images/" + path)) {
            if (input == null) throw new FileNotFoundException("Missing image: images/" + path);
            BufferedImage image = ImageIO.read(input);
            if (image == null) throw new IOException("Unsupported image: " + path);
            return image;
        }
    }
}
```

## Board.java

```java
package Java.MONOPOLY;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public final class Board {
    private final List<Spaces> spaces = new ArrayList<>();
    public Board() {
        Color brown = new Color(139, 69, 19), blue = new Color(146, 185, 202);
        Color pink = new Color(149, 47, 87), orange = new Color(188, 98, 38);
        Color red = new Color(170, 47, 39), yellow = new Color(220, 198, 35);
        Color green = new Color(2, 132, 45), darkBlue = new Color(30, 60, 150);
        space("GO", Spaces.Kind.GO);
        street("Mediterranean Ave", 60, 2, brown);
        space("Community Chest", Spaces.Kind.CHEST);
        street("Baltic Avenue", 60, 4, brown);
        space("Income Tax", Spaces.Kind.TAX);
        railroad("Reading Railroad");
        street("Oriental Avenue", 100, 6, blue);
        space("Chance", Spaces.Kind.CHANCE);
        street("Vermont Avenue", 100, 6, blue);
        street("Connecticut Avenue", 120, 8, blue);
        space("Jail / Just Visiting", Spaces.Kind.JAIL);
        street("St Charles Place", 140, 10, pink);
        utility("Electric Company");
        street("States Avenue", 140, 10, pink);
        street("Virginia Avenue", 160, 12, pink);
        railroad("Pennsylvania Railroad");
        street("Little St James", 180, 14, orange);
        space("Community Chest", Spaces.Kind.CHEST);
        street("Tennessee Avenue", 180, 14, orange);
        street("New York Avenue", 200, 16, orange);
        space("Free Parking", Spaces.Kind.FREE_PARKING);
        street("Kentucky Avenue", 220, 18, red);
        space("Chance", Spaces.Kind.CHANCE);
        street("Indiana Avenue", 220, 18, red);
        street("Illinois Avenue", 240, 20, red);
        railroad("B&O Railroad");
        street("Atlantic Avenue", 260, 22, yellow);
        street("Ventnor Avenue", 260, 22, yellow);
        utility("Water Works");
        street("Marvin Gardens", 280, 24, yellow);
        space("Go To Jail", Spaces.Kind.GO_TO_JAIL);
        street("Pacific Avenue", 300, 26, green);
        street("North Carolina Avenue", 300, 26, green);
        space("Community Chest", Spaces.Kind.CHEST);
        street("Pennsylvania Avenue", 320, 28, green);
        railroad("Short Line");
        space("Chance", Spaces.Kind.CHANCE);
        street("Park Place", 350, 35, darkBlue);
        space("Luxury Tax", Spaces.Kind.TAX);
        street("Boardwalk", 400, 50, darkBlue);
    }
    private void space(String name, Spaces.Kind kind) {
        spaces.add(new Spaces(name, spaces.size(), kind));
    }
    private void street(String name, int price, int rent, Color color) {
        spaces.add(new Property(name, spaces.size(), price, rent, color, Property.Type.STREET));
    }
    private void railroad(String name) {
        spaces.add(new Property(name, spaces.size(), 200, 25, Color.GRAY, Property.Type.RAILROAD));
    }
    private void utility(String name) {
        spaces.add(new Property(name, spaces.size(), 150, 0, Color.LIGHT_GRAY, Property.Type.UTILITY));
    }
    public Spaces getSpace(int position) { return spaces.get(position); }
    public List<Spaces> getSpaces() { return List.copyOf(spaces); }
}
```

## BoardPanel.java

```java
package Java.MONOPOLY;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import javax.swing.*;

@SuppressWarnings("serial")
public final class BoardPanel extends JPanel {
    private final Game game = new Game();
    private final CardLayout screens = new CardLayout();
    private final JPanel screenPanel = new JPanel(screens);
    private final EnumMap<AssetEnum, BufferedImage> images = new EnumMap<>(AssetEnum.class);
    private final List<String> missingImages = new ArrayList<>();
    private final JTextArea status = new JTextArea(7, 28);
    private final JButton roll = new JButton("Roll Dice");
    private final JButton buy = new JButton("Buy Property");
    private final JButton end = new JButton("End Turn");
    private final BoardCanvas canvas = new BoardCanvas();

    public BoardPanel() {
        super(new BorderLayout());
        for (AssetEnum asset : AssetEnum.values()) {
            try {
                images.put(asset, AssetManager.loadImage(asset));
            } catch (IOException ex) {
                missingImages.add(asset.getPath());
                System.err.println(ex.getMessage());
            }
        }
        game.configure(List.of("Player 1", "Player 2"), 1500, 20);
        Settings settings = new Settings(game, () -> screens.show(screenPanel, "menu"));
        JPanel menu = new JPanel(new GridBagLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                BufferedImage splash = images.get(AssetEnum.SPLASHSCREEN);
                if (splash != null) g.drawImage(splash, 0, 0, getWidth(), getHeight(), this);
            }
        };
        JPanel menuButtons = new JPanel(new GridLayout(0, 1, 8, 8));
        menuButtons.setOpaque(false);
        JButton start = new JButton("Start Game");
        JButton configure = new JButton("Settings");
        menuButtons.add(start);
        menuButtons.add(configure);
        menu.add(menuButtons);
        configure.addActionListener(event -> screens.show(screenPanel, "settings"));
        start.addActionListener(event -> runAction(() -> {
            game.start();
            screens.show(screenPanel, "game");
        }));

        JPanel gameplay = new JPanel(new BorderLayout(8, 8));
        gameplay.add(canvas, BorderLayout.CENTER);
        JPanel sidebar = new JPanel(new BorderLayout(8, 8));
        sidebar.setBorder(BorderFactory.createEmptyBorder(12, 8, 12, 12));
        status.setEditable(false);
        status.setLineWrap(true);
        status.setWrapStyleWord(true);
        sidebar.add(new JScrollPane(status), BorderLayout.CENTER);
        JPanel actions = new JPanel(new GridLayout(0, 1, 8, 8));
        actions.add(roll);
        actions.add(buy);
        actions.add(end);
        sidebar.add(actions, BorderLayout.SOUTH);
        gameplay.add(sidebar, BorderLayout.EAST);
        roll.addActionListener(event -> runAction(game::roll));
        buy.addActionListener(event -> runAction(game::buy));
        end.addActionListener(event -> runAction(game::endTurn));
        screenPanel.add(menu, "menu");
        screenPanel.add(settings, "settings");
        screenPanel.add(gameplay, "game");
        add(screenPanel, BorderLayout.CENTER);
        setPreferredSize(new Dimension(1150, 820));
        refresh();
        screens.show(screenPanel, "menu");
    }
    private void runAction(Runnable action) {
        try {
            action.run();
        } catch (IllegalArgumentException | IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Cannot do that", JOptionPane.WARNING_MESSAGE);
        }
        refresh();
    }
    private void refresh() {
        roll.setEnabled(game.getPhase() == Game.Phase.READY_TO_ROLL);
        buy.setEnabled(game.canBuy());
        end.setEnabled(game.getPhase() == Game.Phase.TURN_COMPLETE);
        StringBuilder text = new StringBuilder("Round " + game.getRound() + "/" + game.getRoundLimit() + "\n\n");
        for (int i = 0; i < game.getPlayers().size(); i++) {
            Player player = game.getPlayers().get(i);
            text.append(i == game.getCurrentTurn() ? "> " : "  ")
                .append(player.getName()).append(": $").append(player.getMoney())
                .append(player.isBankrupt() ? " (bankrupt)" : player.isJailed() ? " (jailed)" : "")
                .append("\n");
            for (Property property : player.getProperties()) {
                text.append("    ").append(property.getName()).append("\n");
            }
        }
        text.append("\n").append(game.getMessage());
        text.append("\n\nSimplified rules: four random card outcomes; no houses, hotels, mortgages, auctions or trades. ")
            .append("Bankrupt players return property to the bank. Round-limit score is cash plus purchase values. ")
            .append("An unbought property stays with the bank.");
        if (!missingImages.isEmpty()) text.append("\n\nMissing images: ").append(String.join(", ", missingImages));
        status.setText(text.toString());
        status.setCaretPosition(0);
        canvas.repaint();
    }

    // Board image corners occupy approximately 198 / 1500 of each edge.
    static Point tokenCenter(int position, int size) {
        if (position < 0 || position >= 40) throw new IllegalArgumentException("Invalid position.");
        double corner = size * 198.0 / 1500;
        double step = (size - 2 * corner) / 9;
        double near = corner / 2, far = size - near;
        int side = position / 10, offset = position % 10;
        double x, y;
        if (side == 0) { x = offset == 0 ? far : size - corner - (offset - 0.5) * step; y = far; }
        else if (side == 1) { x = near; y = offset == 0 ? far : size - corner - (offset - 0.5) * step; }
        else if (side == 2) { x = offset == 0 ? near : corner + (offset - 0.5) * step; y = near; }
        else { x = far; y = offset == 0 ? near : corner + (offset - 0.5) * step; }
        return new Point((int) Math.round(x), (int) Math.round(y));
    }
    private final class BoardCanvas extends JPanel {
        @Override protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D g = (Graphics2D) graphics.create();
            try {
                int size = Math.min(getWidth(), getHeight()) - 20;
                if (size <= 0) return;
                int x = (getWidth() - size) / 2, y = (getHeight() - size) / 2;
                g.translate(x, y);
                BufferedImage board = images.get(AssetEnum.BOARD);
                if (board != null) g.drawImage(board, 0, 0, size, size, this);
                else {
                    g.setColor(new Color(205, 230, 207));
                    g.fillRect(0, 0, size, size);
                    g.setColor(Color.DARK_GRAY);
                    for (Spaces space : game.getBoard().getSpaces()) {
                        Point center = tokenCenter(space.getPosition(), size);
                        g.drawString(Integer.toString(space.getPosition()), center.x, center.y);
                    }
                }
                AssetEnum[] tokens = {AssetEnum.PLAYER1, AssetEnum.PLAYER2, AssetEnum.PLAYER3, AssetEnum.PLAYER4};
                Color[] colors = {Color.RED, Color.BLUE, new Color(0, 110, 0), Color.MAGENTA};
                int piece = Math.max(8, size / 35);
                for (int i = 0; i < game.getPlayers().size(); i++) {
                    Player player = game.getPlayers().get(i);
                    if (player.isBankrupt()) continue;
                    Point center = tokenCenter(player.getPosition(), size);
                    int px = center.x + (i % 2 == 0 ? -piece : 0);
                    int py = center.y + (i / 2 == 0 ? -piece : 0);
                    BufferedImage token = images.get(tokens[i]);
                    if (token != null) g.drawImage(token, px, py, piece, piece, this);
                    else { g.setColor(colors[i]); g.fillOval(px, py, piece, piece); }
                    g.setColor(i == game.getCurrentTurn() ? Color.BLACK : colors[i]);
                    g.drawRect(px, py, piece, piece);
                    g.drawString(Integer.toString(i + 1), px, py);
                }
            } finally {
                g.dispose();
            }
        }
    }
}
```

## Dice.java

```java
package Java.MONOPOLY;

import java.util.Objects;
import java.util.Random;

public final class Dice {
    private final Random random;
    private int first;
    private int second;
    public Dice() { this(new Random()); }
    Dice(Random random) { this.random = Objects.requireNonNull(random); }
    public int roll() {
        first = random.nextInt(6) + 1;
        second = random.nextInt(6) + 1;
        return getTotal();
    }
    public int getFirst() { return first; }
    public int getSecond() { return second; }
    public int getTotal() { return first + second; }
    public boolean isDouble() { return first > 0 && first == second; }
}
```

## Game.java

```java
package Java.MONOPOLY;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;

public final class Game {
    public enum Phase { SETUP, READY_TO_ROLL, TURN_COMPLETE, FINISHED }
    private final Dice dice;
    private final Random cards;
    private Board board = new Board();
    private List<Player> players = new ArrayList<>();
    private Phase phase = Phase.SETUP;
    private int currentTurn;
    private int round = 1;
    private int roundLimit = 20;
    private int consecutiveDoubles;
    private Property pendingProperty;
    private String message = "Configure players, then start.";

    public Game() { this(new Dice(), new Random()); }
    Game(Dice dice, Random cards) { this.dice = dice; this.cards = cards; }

    public void configure(List<String> names, int startingMoney, int rounds) {
        if (phase != Phase.SETUP) throw new IllegalStateException("A game is already in progress.");
        if (names == null || names.size() < 1 || names.size() > 4) {
            throw new IllegalArgumentException("Choose 1 to 4 players.");
        }
        if (startingMoney < 1 || startingMoney > 1000000 || rounds < 1 || rounds > 1000) {
            throw new IllegalArgumentException("Money must be 1–1,000,000 and rounds 1–1,000.");
        }
        List<Player> configured = new ArrayList<>();
        HashSet<String> seen = new HashSet<>();
        for (String name : names) {
            Player player = new Player(name, startingMoney);
            if (!seen.add(player.getName().toLowerCase(Locale.ROOT))) {
                throw new IllegalArgumentException("Player names must be different.");
            }
            configured.add(player);
        }
        players = configured; // Commit only after every input has been validated.
        board = new Board();
        roundLimit = rounds;
        currentTurn = 0;
        round = 1;
        consecutiveDoubles = 0;
        pendingProperty = null;
        message = "Settings saved. Ready to start.";
    }
    public void start() {
        if (phase != Phase.SETUP || players.isEmpty()) {
            throw new IllegalStateException("Configure players before starting.");
        }
        phase = Phase.READY_TO_ROLL;
        message = getCurrentPlayer().getName() + "'s turn.";
    }
    public void roll() {
        if (phase != Phase.READY_TO_ROLL) throw new IllegalStateException("You cannot roll now.");
        pendingProperty = null; // Rolling again declines an unpurchased property.
        Player player = getCurrentPlayer();
        int total = dice.roll();
        message = player.getName() + " rolled " + dice.getFirst() + " + " + dice.getSecond() + ". ";
        phase = Phase.TURN_COMPLETE;
        boolean wasJailed = player.isJailed();
        if (wasJailed) {
            if (dice.isDouble()) {
                player.leaveJail();
                message += "Released from jail. ";
            } else if (player.failJailRoll() >= 3) {
                charge(player, 50, null);
                if (player.isBankrupt()) return;
                player.leaveJail();
                message += "Paid $50 to leave jail. ";
            } else {
                message += "Still in jail; try again next turn.";
                return;
            }
        } else if (dice.isDouble() && ++consecutiveDoubles == 3) {
            player.sendToJail();
            message += "Three doubles: go to jail.";
            return;
        }
        int laps = player.move(total);
        if (laps > 0) {
            player.addMoney(200 * laps);
            message += "Collected $200 for passing GO. ";
        }
        resolveLanding(player, total);
        if (phase != Phase.FINISHED && !player.isBankrupt() && !player.isJailed()
                && !wasJailed && dice.isDouble()) {
            phase = Phase.READY_TO_ROLL;
            message += " Doubles: roll again after deciding whether to buy.";
        }
    }
    private void resolveLanding(Player player, int total) {
        Spaces space = board.getSpace(player.getPosition());
        message += "Landed on " + space.getName() + ". ";
        if (space instanceof Property property) {
            if (!property.isOwned()) {
                pendingProperty = property;
                message += "Available for $" + property.getPrice() + ".";
            } else if (property.getOwner() != player) {
                int rent = property.getRent(total, board);
                message += "Rent: $" + rent + " to " + property.getOwner().getName() + ". ";
                charge(player, rent, property.getOwner());
            }
            return;
        }
        switch (space.getKind()) {
            case TAX -> {
                int tax = space.getPosition() == 4 ? 200 : 100;
                message += "Tax: $" + tax + ". ";
                charge(player, tax, null);
            }
            case GO_TO_JAIL -> { player.sendToJail(); message += "Sent to jail."; }
            case CHANCE, CHEST -> drawCard(player);
            default -> { }
        }
    }
    // Small, explicitly simplified card pool for this version.
    private void drawCard(Player player) {
        switch (cards.nextInt(4)) {
            case 0 -> { player.addMoney(100); message += "Card: collect $100."; }
            case 1 -> { message += "Card: pay $50. "; charge(player, 50, null); }
            case 2 -> {
                player.move((40 - player.getPosition()) % 40);
                player.addMoney(200);
                message += "Card: advance to GO and collect $200.";
            }
            case 3 -> { player.sendToJail(); message += "Card: go to jail."; }
            default -> throw new AssertionError();
        }
    }
    private void charge(Player payer, int amount, Player recipient) {
        int paid = Math.min(payer.getMoney(), amount);
        payer.removeMoney(paid);
        if (recipient != null) recipient.addMoney(paid);
        if (paid < amount) {
            payer.declareBankrupt(); // Simplified rule: properties return to the bank.
            pendingProperty = null;
            message += payer.getName() + " is bankrupt; properties return to the bank. ";
            long active = players.stream().filter(p -> !p.isBankrupt()).count();
            if (active == 0 || (players.size() > 1 && active == 1)) finish();
        }
    }
    public boolean canBuy() {
        return phase != Phase.SETUP && phase != Phase.FINISHED && pendingProperty != null
                && !pendingProperty.isOwned() && !getCurrentPlayer().isBankrupt()
                && getCurrentPlayer().getMoney() >= pendingProperty.getPrice();
    }
    public void buy() {
        if (!canBuy()) throw new IllegalStateException("This property cannot be purchased.");
        String name = pendingProperty.getName();
        if (!getCurrentPlayer().buyProperty(pendingProperty)) {
            throw new IllegalStateException("Purchase failed.");
        }
        pendingProperty = null;
        message = getCurrentPlayer().getName() + " bought " + name + ". "
                + (phase == Phase.READY_TO_ROLL ? "Roll again." : "End your turn.");
    }
    public void endTurn() {
        if (phase != Phase.TURN_COMPLETE) throw new IllegalStateException("Finish rolling first.");
        pendingProperty = null;
        consecutiveDoubles = 0;
        do {
            currentTurn = (currentTurn + 1) % players.size();
            if (currentTurn == 0 && ++round > roundLimit) {
                finish();
                return;
            }
        } while (getCurrentPlayer().isBankrupt());
        phase = Phase.READY_TO_ROLL;
        message = getCurrentPlayer().getName() + "'s turn."
                + (getCurrentPlayer().isJailed() ? " Roll doubles to leave jail; $50 after three attempts." : "");
    }
    private void finish() {
        phase = Phase.FINISHED;
        pendingProperty = null;
        long best = players.stream().filter(p -> !p.isBankrupt())
                .mapToLong(Player::getNetWorth).max().orElse(-1);
        String winners = players.stream().filter(p -> !p.isBankrupt() && p.getNetWorth() == best)
                .map(Player::getName).collect(Collectors.joining(", "));
        message += best < 0 ? " Game over: no solvent players."
                : " Game over. Winner(s): " + winners + " with $" + best + " in cash and property value.";
    }
    public Phase getPhase() { return phase; }
    public Board getBoard() { return board; }
    public Dice getDice() { return dice; }
    public List<Player> getPlayers() { return List.copyOf(players); }
    public Player getCurrentPlayer() { return players.get(currentTurn); }
    public int getCurrentTurn() { return currentTurn; }
    public int getRound() { return Math.min(round, roundLimit); }
    public int getRoundLimit() { return roundLimit; }
    public String getMessage() { return message; }
}
```

## Main.java

```java
package Java.MONOPOLY;

import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public final class Main {
    private Main() { }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame("Monopoly");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setContentPane(new BoardPanel());
            window.pack();
            Rectangle screen = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
            window.setSize(new Dimension(Math.min(window.getWidth(), screen.width),
                    Math.min(window.getHeight(), screen.height)));
            window.setLocationRelativeTo(null);
            window.setVisible(true);
        });
    }
}
```

## Player.java

```java
package Java.MONOPOLY;

import java.util.ArrayList;
import java.util.List;

public final class Player {
    private String name;
    private int money;
    private int position;
    private boolean jailed;
    private int jailAttempts;
    private boolean bankrupt;
    private final List<Property> properties = new ArrayList<>();

    public Player(String name, int money) {
        setName(name);
        if (money <= 0) throw new IllegalArgumentException("Starting money must be positive.");
        this.money = money;
    }
    public void setName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Enter a player name.");
        this.name = name.trim();
    }
    public String getName() { return name; }
    public int getMoney() { return money; }
    public int getPosition() { return position; }
    public boolean isJailed() { return jailed; }
    public boolean isBankrupt() { return bankrupt; }
    public List<Property> getProperties() { return List.copyOf(properties); }
    public long getNetWorth() {
        return (long) money + properties.stream().mapToLong(Property::getPrice).sum();
    }
    // Returns the number of times GO was passed; the game awards the money.
    int move(int spaces) {
        if (spaces < 0) throw new IllegalArgumentException("Movement must be nonnegative.");
        long destination = (long) position + spaces;
        position = (int) (destination % 40);
        return (int) (destination / 40);
    }
    void addMoney(int amount) {
        requireAmount(amount);
        money = Math.addExact(money, amount);
    }
    boolean removeMoney(int amount) {
        requireAmount(amount);
        if (amount > money) return false;
        money -= amount;
        return true;
    }
    private static void requireAmount(int amount) {
        if (amount < 0) throw new IllegalArgumentException("Amount must be nonnegative.");
    }
    boolean buyProperty(Property property) {
        if (bankrupt || property == null || property.isOwned() || !removeMoney(property.getPrice())) {
            return false;
        }
        property.assignOwner(this);
        properties.add(property);
        return true;
    }
    void sendToJail() { position = 10; jailed = true; jailAttempts = 0; }
    void leaveJail() { jailed = false; jailAttempts = 0; }
    int failJailRoll() { return ++jailAttempts; }
    void declareBankrupt() {
        bankrupt = true;
        for (Property property : properties) property.assignOwner(null);
        properties.clear();
    }
}
```

## Property.java

```java
package Java.MONOPOLY;

import java.awt.Color;

public final class Property extends Spaces {
    public enum Type { STREET, RAILROAD, UTILITY }
    private final int price;
    private final int baseRent;
    private final Color color;
    private final Type type;
    private Player owner;

    public Property(String name, int position, int price, int baseRent, Color color, Type type) {
        super(name, position, Kind.PROPERTY);
        if (price <= 0 || baseRent < 0 || color == null || type == null) {
            throw new IllegalArgumentException("Invalid property.");
        }
        this.price = price;
        this.baseRent = baseRent;
        this.color = color;
        this.type = type;
    }
    public int getPrice() { return price; }
    public Color getColor() { return color; }
    public Type getType() { return type; }
    public Player getOwner() { return owner; }
    public boolean isOwned() { return owner != null; }
    // Ownership changes are coordinated by Player, not exposed to the UI.
    void assignOwner(Player owner) { this.owner = owner; }

    public int getRent(int diceTotal, Board board) {
        if (owner == null) return 0;
        long count = owner.getProperties().stream().filter(p -> p.type == type).count();
        if (type == Type.RAILROAD) return 25 * (1 << ((int) count - 1));
        if (type == Type.UTILITY) return diceTotal * (count == 2 ? 10 : 4);
        boolean monopoly = board.getSpaces().stream()
                .filter(s -> s instanceof Property)
                .map(s -> (Property) s)
                .filter(p -> p.type == Type.STREET && p.color.equals(color))
                .allMatch(p -> p.owner == owner);
        return baseRent * (monopoly ? 2 : 1);
    }
}
```

## Settings.java

```java
package Java.MONOPOLY;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

@SuppressWarnings("serial")
public final class Settings extends JPanel {
    private final JComboBox<Integer> count = new JComboBox<>(new Integer[]{1, 2, 3, 4});
    private final JTextField[] names = {
        new JTextField("Player 1", 16), new JTextField("Player 2", 16),
        new JTextField("Player 3", 16), new JTextField("Player 4", 16)
    };
    private final JSpinner money = new JSpinner(new SpinnerNumberModel(1500, 1, 1000000, 100));
    private final JSpinner rounds = new JSpinner(new SpinnerNumberModel(20, 1, 1000, 1));
    private final JLabel error = new JLabel(" ");
    private final Game game;

    public Settings(Game game, Runnable back) {
        this.game = game;
        setLayout(new GridBagLayout());
        count.setSelectedItem(2);
        addRow(0, "Players", count);
        for (int i = 0; i < names.length; i++) addRow(i + 1, "Player " + (i + 1), names[i]);
        addRow(5, "Starting money", money);
        addRow(6, "Rounds (one turn per player)", rounds);
        JButton apply = new JButton("Apply");
        JButton cancel = new JButton("Back");
        addRow(7, "Save settings", apply);
        addRow(8, "", cancel);
        addRow(9, "", error);
        count.addActionListener(event -> updateFields());
        apply.addActionListener(event -> {
            try {
                apply();
                error.setText(" ");
                back.run();
            } catch (IllegalArgumentException ex) {
                error.setText(ex.getMessage());
            }
        });
        cancel.addActionListener(event -> back.run());
        updateFields();
    }
    private void addRow(int row, String label, JComponent field) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(7, 7, 7, 7);
        c.gridy = row;
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        add(new JLabel(label), c);
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(field, c);
    }
    private void updateFields() {
        int selected = (Integer) count.getSelectedItem();
        for (int i = 0; i < names.length; i++) names[i].setEnabled(i < selected);
    }
    public void apply() {
        try {
            money.commitEdit();
            rounds.commitEdit();
        } catch (java.text.ParseException ex) {
            throw new IllegalArgumentException("Enter whole numbers for money and rounds.");
        }
        List<String> selectedNames = new ArrayList<>();
        for (int i = 0; i < (Integer) count.getSelectedItem(); i++) {
            selectedNames.add(names[i].getText());
        }
        game.configure(selectedNames, (Integer) money.getValue(), (Integer) rounds.getValue());
    }
}
```

## Spaces.java

```java
package Java.MONOPOLY;

public class Spaces {
    public enum Kind { GO, PROPERTY, TAX, CHANCE, CHEST, JAIL, GO_TO_JAIL, FREE_PARKING }
    private final String name;
    private final int position;
    private final Kind kind;

    public Spaces(String name, int position, Kind kind) {
        if (name == null || name.isBlank() || position < 0 || position >= 40 || kind == null) {
            throw new IllegalArgumentException("Invalid board space.");
        }
        this.name = name;
        this.position = position;
        this.kind = kind;
    }
    public String getName() { return name; }
    public int getPosition() { return position; }
    public Kind getKind() { return kind; }
}
```
