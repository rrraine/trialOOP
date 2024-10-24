package main;

import entity.Entity;
import entity.NPC;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {

    // ~ FIELDS
    GamePanel gp;

    // ~ METHODS
    public AssetSetter (GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        // SET SUPEROBJECT ITEMS IN THE WORLD
        gp.obj[0] = new OBJ_Key(gp);
        gp.obj[0].worldX = gp.tileSize * 23; // COL 23
        gp.obj[0].worldY = gp.tileSize * 7; // ROW 7

        gp.obj[1] = new OBJ_Key(gp);
        gp.obj[1].worldX = gp.tileSize * 23;
        gp.obj[1].worldY = gp.tileSize * 40;

        gp.obj[2] = new OBJ_Key(gp);
        gp.obj[2].worldX = gp.tileSize * 38;
        gp.obj[2].worldY = gp.tileSize * 8;

        gp.obj[3] = new OBJ_Door(gp);
        gp.obj[3].worldX = gp.tileSize * 10;
        gp.obj[3].worldY = gp.tileSize * 11;

        gp.obj[4] = new OBJ_Door(gp);
        gp.obj[4].worldX = gp.tileSize * 8;
        gp.obj[4].worldY = gp.tileSize * 28;

        gp.obj[5] = new OBJ_Door(gp);
        gp.obj[5].worldX = gp.tileSize * 12;
        gp.obj[5].worldY = gp.tileSize * 22;

        gp.obj[6] = new OBJ_Chest(gp);
        gp.obj[6].worldX = gp.tileSize * 10;
        gp.obj[6].worldY = gp.tileSize * 7;

        gp.obj[7] = new OBJ_Boots(gp);
        gp.obj[7].worldX = gp.tileSize * 37;
        gp.obj[7].worldY = gp.tileSize * 42;
    }

    public void setNPC() {

        try {

            gp.npc.add(0, new NPC.StudentFemale(gp));
            gp.npc.get(0).worldX = gp.tileSize * 21;
            gp.npc.get(0).worldY = gp.tileSize * 21;
        }
        catch (NullPointerException e) {
            System.err.println("Accessing null element at gp.npc: " + e.getMessage());
        }

    }
}
