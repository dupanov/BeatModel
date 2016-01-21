package headfirst.combined.djview;

/**
 * Created by Вадик on 18.01.2016.
 */
public class DJTestDrive {
    public static void main(String[] args) {
        BeatModelInterface model = new BeatModel();
        ControllerInterface controller  = new BeatController(model) ;
    }

}
