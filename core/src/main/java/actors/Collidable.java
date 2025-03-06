package actors;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public interface Collidable {
//    Rectangle getBoundingBox();
    Circle getBoundingCircle();
    void setPositionCollidable(float x, float y);
}
