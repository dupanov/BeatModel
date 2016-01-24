package headfirst.combined.djview;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Вадик on 22.01.2016.
 */
public class HeartModel implements HeartModelInterface, Runnable {
    ArrayList beatObservers = new ArrayList();
    ArrayList bpmObservers = new ArrayList();
    int time = 100;
    int bpm = 90;
    Random random = new Random(System.currentTimeMillis());
    Thread thread;

    public HeartModel(){
        thread = new Thread(this);
        thread.start();
    }

    public int getHeartRate() {
        return 60000/time;
    }

    public void registerObserver(BeatObserver o) {
        beatObservers.add(o);
    }

    public void removeObserver(BeatObserver o) {
        int i = beatObservers.indexOf(o);
        if(i>=0){
            beatObservers.remove(i);
        }
    }

    public void registerObserver(BPMObserver o) {
        beatObservers.add(o);
    }

    public void removeObserver(BPMObserver o) {
        int i = bpmObservers.indexOf(o);
        if(i >= 0){
            bpmObservers.remove(i);
        }
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    public void run() {
        int lastrate = -1;

        for(;;){
            int change = random.nextInt(10);
            if(random.nextInt(2) == 0){
                change = 0 - change;
            }
            int rate = 60000/(time+change);
            if(rate < 120 && rate > 50){
                time += change;
                notifyBeatObservers();
                if(rate != lastrate){
                    lastrate = rate;
                    notifyBPMObservers();
                }
            }
            try {
                Thread.sleep(time);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void notifyBPMObservers() {
        for(int i = 0; i<bpmObservers.size();i++){
            BPMObserver observer = (BPMObserver) bpmObservers.get(i);
            observer.updateBPM();
        }
    }

    private void notifyBeatObservers() {
        for(int i = 0; i<beatObservers.size(); i++){
            BeatObserver observer = (BeatObserver) bpmObservers.get(i);
            observer.updateBeat();
        }
    }
}
