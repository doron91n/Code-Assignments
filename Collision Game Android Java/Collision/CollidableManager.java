package fluffybun.game.Collision;

import android.util.Pair;
import android.util.SparseArray;

import fluffybun.game.Geometry.Point;

public class CollidableManager {
    private static CollidableManager m_collidable_manager = null;
    private SparseArray m_collidable_array;

    private CollidableManager() {
        this.m_collidable_array = new SparseArray();
    }

    public static CollidableManager getCollidableManager() {
        if (m_collidable_manager == null) {
            m_collidable_manager = new CollidableManager();
        }
        return m_collidable_manager;
    }


    public void checkCollision(Collidable other_object) {
        Collidable array_object = null;
      //  System.out.println("&&&&&&&&&&&&&&&&&  collidable manager checkCollision of  object id: "+other_object.getID());

        int size = this.m_collidable_array.size();
        for (int i = 0; i < size; i++) {
            array_object = (Collidable) this.m_collidable_array.valueAt(i);

            Pair<Boolean,Point> collision_check= array_object.collidedWith(other_object);
            if (array_object.getID() != other_object.getID() && collision_check.first) {
                CollisionReport collision_report = new CollisionReport(other_object.getID(),array_object.getID(),collision_check.second,other_object.getCollisionSide(collision_check.second),array_object.getCollisionSide(collision_check.second));
                array_object.collisionResponse(collision_report);
                other_object.collisionResponse(collision_report);
            }
        }
        if (array_object == null) {
            System.out.println("CollidableManager: returning null , no collision detected with object id: " + array_object.getID());
        }
     }

    public void addCollidable( Collidable object) {
        this.m_collidable_array.append(object.getID(), object);
    }

    public void removeCollidable(int object_id) {
        this.m_collidable_array.remove(object_id);
    }

    public Collidable getCollidable(int object_id) {
        return (Collidable) this.m_collidable_array.get(object_id);
    }

    public void clearCollidableArray() {
        this.m_collidable_array.clear();
    }


}
