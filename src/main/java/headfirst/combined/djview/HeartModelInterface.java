package headfirst.combined.djview;

/**
 * Created by Вадик on 22.01.2016.
 */
public interface HeartModelInterface {
        int getHeartRate();
        void registerObserver(BeatObserver o);
        void removeObserver(BeatObserver o);
        void registerObserver(BPMObserver o);
        void removeObserver(BPMObserver o);
    }

