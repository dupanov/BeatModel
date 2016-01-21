package headfirst.combined.djview;

/**
 * Created by Вадик on 18.01.2016.
 */
public interface ControllerInterface {
    void start();

    void stop();

    void increaseBPM();

    void decreaseBPM();

    void setBPM(int bpm);

}
