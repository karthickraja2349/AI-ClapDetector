/*package com.clapdetector.feature;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.mfcc.MFCC;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MFCCExtractor {
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException {
        String audioFilePath = "/home/ayyappankalai/ClapDetector/user/noise/userNoise1.wav"; // Replace with your file path
        int sampleRate = 44100;
        int bufferSize = 1024;
        int bufferOverlap = 512;

        AudioDispatcher dispatcher = AudioDispatcherFactory.fromPipe(audioFilePath, sampleRate, bufferSize, bufferOverlap);

        int mfccNumCoefficients = 13;
        int mfccNumFilters = 26;
        float mfccLowerFrequency = 300;
        float mfccUpperFrequency = 8000;

        MFCC mfcc = new MFCC(bufferSize, sampleRate, mfccNumCoefficients, mfccNumFilters, mfccLowerFrequency, mfccUpperFrequency);

        final BufferedWriter[] writer = {new BufferedWriter(new FileWriter("mfcc_output1.csv", true))};

        dispatcher.addAudioProcessor(mfcc);
        dispatcher.addAudioProcessor(new AudioProcessor() {
            @Override
            public boolean process(AudioEvent audioEvent) {
                float[] mfccs = mfcc.getMFCC();
                try {
                    for (int i = 0; i < mfccs.length; i++) {
                        writer[0].write(mfccs[i] + ((i < mfccs.length - 1) ? "," : ""));
                    }
                    writer[0].newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            public void processingFinished() {
                try {
                    writer[0].close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        dispatcher.run();
    }
}
*/

package com.clapdetector.feature;
import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.mfcc.MFCC;

import java.util.concurrent.atomic.AtomicReference;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class MFCCExtractor {

     public float[] extractMFCC(String filePath) {
       List<float[]> mfccList = new ArrayList<>();

    try {
        AudioDispatcher dispatcher = AudioDispatcherFactory.fromPipe(filePath, 44100, 1024, 512);

        MFCC mfcc = new MFCC(1024, 44100, 13, 30, 300, 8000);
        dispatcher.addAudioProcessor(mfcc);
        dispatcher.addAudioProcessor(new AudioProcessor() {
            @Override
            public boolean process(AudioEvent audioEvent) {
                float[] mfccs = mfcc.getMFCC();
                mfccList.add(Arrays.copyOf(mfccs, mfccs.length));
                return true;
            }

            @Override
            public void processingFinished() {}
        });

        dispatcher.run();
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }

    if (mfccList.isEmpty()) return null;

    // Average across all MFCC frames
    int numCoefficients = mfccList.get(0).length;
    float[] avgMFCC = new float[numCoefficients];
    for (float[] mfccFrame : mfccList) {
        for (int i = 0; i < numCoefficients; i++) {
            avgMFCC[i] += mfccFrame[i];
        }
    }
    for (int i = 0; i < numCoefficients; i++) {
        avgMFCC[i] /= mfccList.size();
    }

    return avgMFCC;
}

}
/*
package com.clapdetector.feature;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.mfcc.MFCC;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class MFCCExtractor {

    // ✅ This returns the average MFCC for classification
    public float[] extractAverageMFCC(String filePath) {
        List<float[]> mfccList = extractMFCCSequence(filePath);
        if (mfccList == null || mfccList.isEmpty()) return null;

        int numCoefficients = mfccList.get(0).length;
        float[] avgMFCC = new float[numCoefficients];
        for (float[] frame : mfccList) {
            for (int i = 0; i < numCoefficients; i++) {
                avgMFCC[i] += frame[i];
            }
        }
        for (int i = 0; i < numCoefficients; i++) {
            avgMFCC[i] /= mfccList.size();
        }
        return avgMFCC;
    }

    // ✅ This returns the list of all MFCC frames for clap detection
    public List<float[]> extractMFCCSequence(String filePath) {
        List<float[]> mfccList = new ArrayList<>();

        try {
            AudioDispatcher dispatcher = AudioDispatcherFactory.fromPipe(filePath, 44100, 1024, 512);
            MFCC mfcc = new MFCC(1024, 44100, 13, 30, 300, 8000);

            dispatcher.addAudioProcessor(mfcc);
            dispatcher.addAudioProcessor(new AudioProcessor() {
                @Override
                public boolean process(AudioEvent audioEvent) {
                    float[] mfccs = mfcc.getMFCC();
                    mfccList.add(Arrays.copyOf(mfccs, mfccs.length));
                    return true;
                }

                @Override
                public void processingFinished() {}
            });

            dispatcher.run();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return mfccList;
    }
}
*/
