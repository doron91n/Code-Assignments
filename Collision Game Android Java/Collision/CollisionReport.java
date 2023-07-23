package fluffybun.game.Collision;

import fluffybun.game.Geometry.Point;
import fluffybun.game.Side;

public class CollisionReport {
    private int m_hitter_ID;
    private int m_being_hit_ID;
    private Point m_collision_point;
    private Side m_hitter_collision_side;
    private Side m_being_hit_collision_side;

    public CollisionReport(int hitter_ID, int being_hit_ID, Point collision_point, Side hitter_collision_side, Side being_hit_collision_side) {
        this.setBeingHitCollisionSide(being_hit_collision_side);
        this.setBeingHitID(being_hit_ID);
        this.setHitterCollisionSide(hitter_collision_side);
        this.setHitterID(hitter_ID);
        this.setCollisionPoint(collision_point);
    }

    public int getHitterID() {
        return this.m_hitter_ID;
    }

    private void setHitterID(int hitter_ID) {
        this.m_hitter_ID = hitter_ID;
    }

    public int getBeingHitID() {
        return this.m_being_hit_ID;
    }

    private void setBeingHitID(int being_hit_ID) {
        this.m_being_hit_ID = being_hit_ID;
    }

    public Point getCollisionPoint() {
        return this.m_collision_point;
    }

    private void setCollisionPoint(Point collision_point) {
        this.m_collision_point = collision_point;
    }

    public Side getHitterCollisionSide() {
        return this.m_hitter_collision_side;
    }

    private void setHitterCollisionSide(Side hitter_collision_side) {
        this.m_hitter_collision_side = hitter_collision_side;
    }

    public Side getBeingHitCollisionSide() {
        return this.m_being_hit_collision_side;
    }

    private void setBeingHitCollisionSide(Side being_hit_collision_side) {
        this.m_being_hit_collision_side = being_hit_collision_side;
    }
}
