package headfirst.combined.djview;

/**
 * Created by Вадик on 22.01.2016.
 */
public class HeartTestDrive {
    public static void main(String[] args) {
        HeartModel heartModel = new HeartModel();
        ControllerInterface model = new HeartController(heartModel);
    }

}
