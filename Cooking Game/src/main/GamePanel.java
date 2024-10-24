package main;

import entity.Entity;
import entity.NPC;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements Runnable {

    // ~ FIELDS

    // FRAME RATE
    final int FPS = 60;

    // SCREEN SETTINGS
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; // ACTUAL TILE: 48 x 48

    // ASPECT RATIO
    public final int maxScreenCol = 16; // (16) TILES PER ROW
    public final int maxScreenRow = 12; // (12) TILES PER COL

    // SCREEN RESOLUTION
    public final int screenWidth = tileSize * maxScreenCol; // 768
    public final int screenHeight = tileSize * maxScreenRow; // 576

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // GAME SETTINGS SYSTEM
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound sfx = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;

    // OBJECTS AND ENTITY
    public Player player = new Player(this, keyH);
    public List<NPC> npc = new ArrayList<>();
    public SuperObject obj[] = new SuperObject[10];

    // GAME STATE
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;

    // ~ FIELDS END HERE
    // ~ METHODS

    // CONSTRUCTOR
    public GamePanel() {

        // SET DIMENSIONS AND COLOR OF THE FRAME
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);

        this.setDoubleBuffered(true);

        // LISTEN FOR KEYSTROKES
        this.addKeyListener(keyH);
        // ALLOWS RECEIVING OF KEYSTROKES
        this.setFocusable(true);
    }

    // PRELOAD OBJECTS IN WORLD CALLED BY MAIN
    public void setUpGame() {

        // DEPLOY OBJECTS IN WORLD AND PLAY MUSIC
        aSetter.setObject();
        aSetter.setNPC();
        playMusic(0);
        stopMusic();
        gameState = playState;
    }

    // START THE GAME CALLED BY MAIN
    public void startGameThread() {

        // PASS GAMEPANEL AND CALLS RUN() OF RUNNABLE
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() { // a method of Runnable interface

        // DELTA ACCUMULATOR METHOD
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        long timer = 0;
        int drawCount = 0;

        // GAME LOOP
        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;

            timer += currentTime - lastTime;

            lastTime = currentTime;

            if (delta >= 1) {

                update();
                repaint(); // CALLS PAINTCOMPONENT()
                delta--;

                drawCount++;
            }
        }
    }
    // 1) UPDATE: UPDATE INFO LIKE MOVING PLAYER POSITION
    public void update() {

        if (gameState == playState) {

            player.update();

            for (NPC n : npc) {
                if (n != null) {
                    n.update();
                }
            }
        }
        if (gameState == pauseState) {
            // TODO
        }
    }
    // 2) DRAW: DRAW THE FRAME WITH UPDATED INFO
    public void paintComponent(Graphics g) {

        // CLASS GRAPHICS: PROVIDES FUNCTIONS TO DRAW OBJECTS
        super.paintComponent(g);

        // TYPECAST TO CLASS GRAPHICS2D TO PROVIDE BETTER 2D STUFF
        Graphics2D g2 = (Graphics2D) g;

        // DRAW TILE
        tileM.draw(g2);

        // DRAW ITEMS
        for (SuperObject superObject : obj) {
            if (superObject != null) {
                superObject.draw(g2, this);
            }
        }

        // DRAW NPC
        for (NPC n : npc) {
            if (n != null) {
                n.draw(g2);
            }
        }

        // DRAW PLAYER
        player.draw(g2);

        // DRAW UI
        ui.draw(g2);

        // GOOD PRACTICE TO RELEASE SYSTEM RESOURCES USED BY GRAPHICS2D AND SAVE MEMORY
        g2.dispose();
    }

    // PLAY BG MUSIC CALLED BY SETUPGAME()
    public void playMusic(int i) {

        music.setFile(i);
        music.adjustVolume(-18); // DECIBELS
        music.play();
        music.loop();
    }
    // STOP BS MUSIC WHEN GAME LOOP ENDS
    public void stopMusic() {

        music.stop();
    }
    // PLAY SFX MUSIC CALLED BY PLAYER WHEN THEY INTERACT
    public void playSFX(int i) {

        sfx.setFile(i);
        sfx.play();
    }
}