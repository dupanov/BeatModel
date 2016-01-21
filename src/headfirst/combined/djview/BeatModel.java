package headfirst.combined.djview;

import javax.sound.midi.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Вадик on 18.01.2016.
 */
public class BeatModel implements BeatModelInterface, MetaEventListener {
    Sequencer sequencer;
    ArrayList bpmObservers = new ArrayList();
    ArrayList beatObservers = new ArrayList();
    private int bpm = 90;
    Sequence sequence;
    Track track;


    @Override
    public void initialize() {
        setUpMidi();
        buildTrackAndStrart();
    }

    private void buildTrackAndStrart() {
        int[] trackList = {35, 0, 46, 0};
    }

    private void setUpMidi() {
        try{
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.addMetaEventListener(this);
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(getBPM());

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void on() {
        sequencer.start();
        setBPM(90);
    }

    @Override
    public void off() {
        setBPM(0);
        sequencer.stop();

    }

    @Override
    public void setBPM(int bpm) {
        this.bpm = bpm;
        sequencer.setTempoInBPM(getBPM());
        notifyBPMObservers();
    }

    private void notifyBeatObservers() {
        for(int i = 1; i< beatObservers.size(); i++ ){
            BeatObserver observer = (BeatObserver) beatObservers.get(i);
            observer.updateBeat();
        }
    }


    private void notifyBPMObservers() {
        for(int i = 1; i< bpmObservers.size(); i++ ){
            BPMObserver observer = (BPMObserver) bpmObservers.get(i);
            observer.updateBPM();
        }
    }

    @Override
    public int getBPM() {
        return bpm;
    }


    @Override
    public void registerObserver(BeatObserver o) {
        beatObservers.add(o);
    }

    @Override
    public void removeObserver(BeatObserver o) {
        int index = beatObservers.indexOf(o);
        if(index>=0){
            beatObservers.remove(o);
        }
    }

    @Override
    public void registerObserver(BPMObserver o) {
        bpmObservers.add(o);
    }

    @Override
    public void removeObserver(BPMObserver o) {
        int index = bpmObservers.indexOf(o);
        if(index>=0){
            bpmObservers.remove(o);
        }
    }

    /**
     * Invoked when a <code>{@link Sequencer}</code> has encountered and processed
     * a <code>MetaMessage</code> in the <code>{@link Sequence}</code> it is processing.
     *
     * @param message the meta-message that the sequencer encountered
     */
    @Override
    public void meta(MetaMessage message) {
        if(message.getType() == 47){
            beatEvent();
            sequencer.start();
            setBPM(getBPM());
        }

    }

    private void beatEvent() {
        notifyBeatObservers();
    }

    public MidiEvent makeEvent (int comd, int chan, int one, int two, int tick){
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a, tick);

            } catch(Exception e){}
        return event;
    }

    }


