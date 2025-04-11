package com.clapdetector;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.mfcc.MFCC;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DatasetPreparer1 {

    private static final int SAMPLE_RATE = 16000;
    private static final int BUFFER_SIZE = 512;
    private static final int OVERLAP = 256;

    private static final String[] CLASSES = { "clap", "noise" };

    public static void main(String[] args) {
        String baseDir = "data";
        String outputFile = "dataset.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            // Write CSV header
            for (int i = 1; i <= 13; i++) {
                writer.write("mfcc_" + i + ",");
            }
            writer.write("label\n");

            for (String label : CLASSES) {
                File classDir = new File(baseDir + "/" + label);
                if (!classDir.exists() || !classDir.isDirectory()) {
                    System.err.println("Directory not found: " + classDir.getAbsolutePath());
                    continue;
                }

                for (File file : classDir.listFiles((dir, name) -> name.endsWith(".wav"))) {
                    System.out.println("Processing: " + file.getAbsolutePath());
                    List<float[]> mfccList = extractMFCC(file);
                    for (float[] mfcc : mfccList) {
                        if (mfcc != null && mfcc.length > 0 && !isAllZero(mfcc)) {
                            for (float val : mfcc) {
                                writer.write(val + ",");
                            }
                            writer.write(label + "\n");
                        }
                    }
                }
            }

            System.out.println("Dataset creation completed: " + outputFile);
        } catch (IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    private static List<float[]> extractMFCC(File audioFile) throws UnsupportedAudioFileException, IOException {
        List<float[]> featureList = new ArrayList<>();

        AudioDispatcher dispatcher = AudioDispatcherFactory.fromPipe(
                audioFile.getAbsolutePath(), SAMPLE_RATE, BUFFER_SIZE, OVERLAP);

        MFCC mfcc = new MFCC(BUFFER_SIZE, SAMPLE_RATE, 13, 30, 300, 3000);

        dispatcher.addAudioProcessor(new AudioProcessor() {
            @Override
            public boolean process(AudioEvent audioEvent) {
                mfcc.process(audioEvent); // required to update MFCC
                float[] mfccFeatures = mfcc.getMFCC();

                if (mfccFeatures != null && mfccFeatures.length > 0 && !isAllZero(mfccFeatures)) {
                    featureList.add(mfccFeatures.clone());

                    System.out.print("MFCC: ");
                    for (float f : mfccFeatures) {
                        System.out.printf("%.2f ", f);
                    }
                    System.out.println();
                } else {
                    System.out.println("Skipped empty or zero MFCC frame.");
                }

                return true;
            }

            @Override
            public void processingFinished() {}
        });

        dispatcher.run();
        return featureList;
    }

    private static boolean isAllZero(float[] arr) {
        for (float v : arr) {
            if (v != 0.0f) return false;
        }
        return true;
    }
}

