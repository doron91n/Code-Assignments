package fluffybun.game.Movement;

public interface Movable {

    void move();

    //void updateMovmentListener();

    Velocity getVelocity();

    void setVelocity(Velocity velocity);

    int getID();
}
