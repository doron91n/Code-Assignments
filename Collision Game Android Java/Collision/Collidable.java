package fluffybun.game.Collision;


import android.util.Pair;

import fluffybun.game.Geometry.Point;
import fluffybun.game.Side;

public interface Collidable<T> {

    Pair<Boolean,Point> collidedWith(Collidable other);

    Side getCollisionSide(Point collision_point);

    double collisionResponse(CollisionReport collision_report);

    T getShape();

    int getID();
}