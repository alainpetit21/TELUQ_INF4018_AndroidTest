package com.bianisoft.androittest.domain;


import java.util.ArrayList;


public class World {
    public static final int MAX_XY = 10;


    //Sized World Elements-- i.e. to be loaded from config file or such
    PlayerShip                  arPlayerShip;     //The one and only controllable ship
    public ArrayList<Entity>    arAll;
    public ArrayList<SpaceShip> arShips;
    public ArrayList<WallObstacle> arWalls;
    
    //Dynamics for Run-time Creations
    public ArrayList<Bullet> arBufferActiveBullets;
    
    
    //Default constructor - create a little test world
    public World(){
        arAll = new ArrayList<>();
        arShips = new ArrayList<>();
        arWalls = new ArrayList<>();
        arBufferActiveBullets = new ArrayList<>();
        
        // Test World
        addEntity(new PlayerShip());
        World.this.addEntity(new WallObstacle(-40, 40, 200, 80, 80, 40));

        World.this.addEntity(new WallObstacle(0, -40, 2000, 160, 80, 40));
        World.this.addEntity(new WallObstacle(0, 40, 3000, 160, 80, 40));
        World.this.addEntity(new WallObstacle(0, -40,4000, 160, 80, 40));
        World.this.addEntity(new WallObstacle(0, 40, 5000, 160, 80, 40));

        World.this.addEntity(new WallObstacle(-40, 40, 2000, 80, 80, 40));
        World.this.addEntity(new WallObstacle( 40, 40, 2500, 80, 80, 40));
        World.this.addEntity(new WallObstacle(-40,-40, 3000, 80, 80, 40));
        World.this.addEntity(new WallObstacle( 40,-40, 3500, 80, 80, 40));

        World.this.addEntity(new WallObstacle(-40, 0, 10000, 80, 160, 40));
        World.this.addEntity(new WallObstacle( 40, 0, 11000, 80, 160, 40));
        World.this.addEntity(new WallObstacle(-40, 0, 12000, 80, 160, 40));
        World.this.addEntity(new WallObstacle( 40, 0, 13000, 80, 160, 40));
    }
    
    //TODO config file loading in XML
    public World(String pstConfigFile){
        arAll = new ArrayList<>();
        arShips = new ArrayList<>();
        arWalls = new ArrayList<>();
        arBufferActiveBullets = new ArrayList<>();
    }
    
    public PlayerShip getPlayerShip(){
        return arPlayerShip;
    }

    public ArrayList<WallObstacle> getArrayWalls(){
        return arWalls;
    }

    public void addEntity(Entity pobjEntity){
        arAll.add(pobjEntity);
    }

    public void addEntity(WallObstacle pobjWall){
        arWalls.add(pobjWall);
        arAll.add(pobjWall);
    }

    public void addEntity(EnnemyShip pobjEnnemyShip){
        arShips.add(pobjEnnemyShip);
        arAll.add(pobjEnnemyShip);
    }

    public void addEntity(PlayerShip pobjPlayerShip){
        arPlayerShip = pobjPlayerShip;
        arShips.add(pobjPlayerShip);
        arAll.add(pobjPlayerShip);
    }
}
