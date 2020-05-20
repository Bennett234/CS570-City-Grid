package util;

import javafx.util.Pair;

public class AnimationUtil {
    public static Pair<Float, Float> linearApprox(float x1, float y1, float x2, float y2, float state) {
        return new Pair<>(linearApprox1D(x1, x2, state), linearApprox1D(y1, y2, state));
    }

    public static float linearApprox1D(float a, float b, float state) {
        return a + state * (b - a);
    }
}
