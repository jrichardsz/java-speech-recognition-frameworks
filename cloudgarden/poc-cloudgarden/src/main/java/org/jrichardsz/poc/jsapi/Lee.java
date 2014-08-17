package org.jrichardsz.poc.jsapi;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author RM-RCM
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Cmop
 */
import javax.speech.*;
import javax.speech.synthesis.*;
import java.util.*;

public class Lee {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {

            String say = "Hello Richard";

            SynthesizerModeDesc required = new SynthesizerModeDesc();
            required.setLocale(Locale.ROOT);

            Voice voice = new Voice(null, Voice.GENDER_FEMALE, Voice.GENDER_FEMALE, null);

            required.addVoice(voice);

            Synthesizer synth = Central.createSynthesizer(null);

            synth.allocate();
            synth.resume();

            synth.speakPlainText(say, null);

            synth.waitEngineState(Synthesizer.QUEUE_EMPTY);
            synth.deallocate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
