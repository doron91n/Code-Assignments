package fluffybun.game.Movement;

import android.util.SparseArray;

import fluffybun.game.Collision.Collidable;
import fluffybun.game.Collision.CollidableManager;

public class MovementManager {

    private static MovementManager m_movement_manager = null;
    private SparseArray m_moving_objects_array;

    private MovementManager() {
        this.m_moving_objects_array = new SparseArray();
    }

    public static MovementManager getMovementListener() {
        if (m_movement_manager == null) {

            m_movement_manager = new MovementManager();
        }
        return m_movement_manager;
    }


    public void reportMovement(Movable moving_object) {
        // carry on to report the movement of the given object to all collidables
//System.out.println("&&&&&&&&&&&&&&&&&  Movement manager reporting movement of moving object id: "+moving_object.getID());
        CollidableManager.getCollidableManager().checkCollision((Collidable) moving_object);
    }


    public void addMovingObject(Movable moving_object) {
        this.m_moving_objects_array.append(moving_object.getID(), moving_object);
    }

    public void removeMovingObject(int moving_object_id) {
        this.m_moving_objects_array.remove(moving_object_id);
    }

    public Movable getMovingObject(int moving_object_id) {
        return (Movable) this.m_moving_objects_array.get(moving_object_id);
    }

    public void clearMovingObjectArray() {
        this.m_moving_objects_array.clear();
    }


}
