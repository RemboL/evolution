package pl.rembol.jme3.evolution.card;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

import static com.jme3.math.FastMath.pow;

public class MovementControl extends AbstractControl {

    private Vector3f v1;
    private Vector3f v2;
    private Vector3f v3;
    private Vector3f v4;
    private Quaternion r1;
    private Quaternion r2;
    private float time;
    private float pathTime;

    private Vector3f queuedV2;
    private Vector3f queuedV3;
    private Vector3f queuedV4;
    private Quaternion queuedR2;
    private float queuedPathTime;

    public void setMovement(Vector3f v2, Vector3f v3, Vector3f v4, Quaternion r2, float pathTime) {
        enqueue(v2, v3, v4, r2, pathTime);
    }

    private void enqueue(Vector3f v2, Vector3f v3, Vector3f v4, Quaternion r2, float pathTime) {
        if (!isMoving()) {
            v1 = getSpatial().getLocalTranslation().clone();
            r1 = getSpatial().getLocalRotation().clone();

            this.v2 = v1.add(v2);
            this.v4 = v4;
            this.v3 = v4.add(v3);

            this.r2 = r2;

            this.pathTime = pathTime;
        } else {
            queuedV2 = v2;
            queuedV3 = v3;
            queuedV4 = v4;
            queuedR2 = r2;
            queuedPathTime = pathTime;
        }
    }

    private void clear() {
        v1 = null;
        v2 = null;
        v3 = null;
        v4 = null;
        r1 = null;
        r2 = null;
        time = 0;
    }

    private void dequeue() {
        clear();

        if (isQueued()) {
            enqueue(queuedV2, queuedV3, queuedV4, queuedR2, queuedPathTime);
            queuedV2 = null;
            queuedV3 = null;
            queuedV4 = null;
            queuedR2 = null;
        }
    }


    private boolean isMoving() {
        return v4 != null;
    }

    private boolean isQueued() {
        return queuedV4 != null;
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (isMoving()) {
            time += tpf;

            if (time < pathTime) {
                updatePosition();
            } else {
                finishPath();
            }
        }
    }

    private void finishPath() {
        getSpatial().setLocalTranslation(v4);
        getSpatial().setLocalRotation(r2);

        dequeue();
    }

    private void updatePosition() {
        float t1 = time / pathTime;
        float t2 = (pathTime - time) / pathTime;

        float x = pow(t2, 3) * v1.x + 3 * pow(t2, 2) * t1 * v2.x + 3 * t2 * pow(t1, 2) * v3.x + pow(t1, 3) * v4.x;
        float y = pow(t2, 3) * v1.y + 3 * pow(t2, 2) * t1 * v2.y + 3 * t2 * pow(t1, 2) * v3.y + pow(t1, 3) * v4.y;
        float z = pow(t2, 3) * v1.z + 3 * pow(t2, 2) * t1 * v2.z + 3 * t2 * pow(t1, 2) * v3.z + pow(t1, 3) * v4.z;

        getSpatial().setLocalTranslation(x, y, z);

        getSpatial().setLocalRotation(new Quaternion().slerp(r1, r2, t1));

    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
}
