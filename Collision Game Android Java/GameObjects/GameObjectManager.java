package fluffybun.game.GameObjects;

import android.graphics.Canvas;
import android.util.SparseArray;

import fluffybun.game.Collision.Collidable;

public class GameObjectManager {
    private SparseArray m_game_object_array;

    public GameObjectManager() {
        this.m_game_object_array = new SparseArray();
    }


    public void drawAll(Canvas canvas) {
        GameObject array_object;
        int size = this.m_game_object_array.size();
        for (int i = 0; i < size; i++) {
            array_object = (GameObject) this.m_game_object_array.valueAt(i);
         //   System.out.println("$$$$$$$$$$$$$$ GameObject manager draw id: "+array_object.getID());
            array_object.draw(canvas);
        }
    }

    public void updateAll() {
        GameObject array_object ;
        int size = this.m_game_object_array.size();
        for (int i = 0; i < size; i++) {
            array_object = (GameObject) this.m_game_object_array.valueAt(i);
            array_object.update();
        }
    }

    public void addGameObject( Collidable object) {
        this.m_game_object_array.append(object.getID(), object);
    }

    public void removeObject(int object_id) {
        this.m_game_object_array.remove(object_id);
    }

    public GameObject getGameObject(int object_id) {
        return (GameObject) this.m_game_object_array.get(object_id);
    }

    public void clearGameObjectArray() {
        this.m_game_object_array.clear();
    }
}
