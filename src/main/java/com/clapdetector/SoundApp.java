/*package com.clapdetector;

import com.clapdetector.feature.MFCCExtractor;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

public class SoundApp {
    public static void main(String[] args) {
       String datasetPath = "/home/ayyappankalai/ClapDetector/dataset.csv";
        CSVLoader.load(datasetPath);
        List<Sample> trainingSamples = CSVLoader.samples;
        AudioRecorder recorder = new AudioRecorder();
        MFCCExtractor extractor = new MFCCExtractor();
        
        KNNClassifier knn = new KNNClassifier(trainingSamples, 3); // ✅ replace 3 with your desired K

        String filename = "recordings/input"; // Make sure "recordings/" folder exists

        while (true) {
            System.out.println("\n🔴 Press Enter to record or type 'exit':");
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("exit")) break;

            String path = recorder.recordAudio(filename);
            if (path == null) continue;

            float[] mfcc = extractor.extractMFCC(path); // ✅ Fixed: removed invalid cast
            System.out.println(Arrays.toString(mfcc));
            if (mfcc == null) {
                System.out.println("⚠️ Could not extract features.");
                continue;
            }

            // ✅ Convert float[] to double[] for prediction
            double[] mfccDouble = new double[mfcc.length];
            for (int i = 0; i < mfcc.length; i++) {
                mfccDouble[i] = mfcc[i];
            }
     double[] audioInput = {-99.93321,-10.799833,10.336783,-0.5828301,0.82533246,-4.2361946,2.0976863,0.9426222,3.537805,-2.3473585,3.0217986,-5.806735,2.8121274}; // low, scattered values likely to represent background noise
            String result = knn.predict(audioInput); // ✅ Fixed: match expected argument type
            System.out.println("🔊 Detected: " + result);
        }
    }
}
*/

package com.clapdetector;

import com.clapdetector.feature.MFCCExtractor;
import java.util.List;
import java.util.Arrays;

public class SoundApp {
    public static void main(String[] args) {
        String datasetPath = "/home/ayyappankalai/ClapDetector/dataset.csv";
        String audioFilePath = "/home/ayyappankalai/ClapDetector/voice6.wav";

        // ✅ Load training data
        CSVLoader.load(datasetPath);
        List<Sample> trainingSamples = CSVLoader.samples;

        // ✅ Create KNN classifier
        KNNClassifier knn = new KNNClassifier(trainingSamples, 3);

        // ✅ Extract MFCC features from the given file
        MFCCExtractor extractor = new MFCCExtractor();
        float[] mfcc = extractor.extractMFCC(audioFilePath);
        
        if (mfcc == null) {
            System.out.println("⚠️ Could not extract features from the audio file.");
            return;
        }

        System.out.println("🎧 Extracted MFCC: " + Arrays.toString(mfcc));

        // ✅ Convert float[] to double[]
        double[] mfccDouble = new double[mfcc.length];
        for (int i = 0; i < mfcc.length; i++) {
            mfccDouble[i] = mfcc[i];
        }

        // ✅ Predict label
        String result = knn.predict(mfccDouble);
        System.out.println("🔊 Detected: " + result);
        
        if(result.equalsIgnoreCase("clap")){
           int clapCount = ClapDetector.detectClap(mfccDouble);
           System.out.println(clapCount);
        }
    }
}

/*
package com.clapdetector;

import com.clapdetector.feature.MFCCExtractor;
import java.util.List;
import java.util.Arrays;

public class SoundApp {
    public static void main(String[] args) {
        // ✅ Get path from args or fallback
       String audioFilePath = (args.length > 0) ? args[0] : "/home/ayyappankalai/ClapDetector/voice2.wav";
        String datasetPath = "/home/ayyappankalai/ClapDetector/dataset.csv";

        // ✅ Load training data
        CSVLoader.load(datasetPath);
        List<Sample> trainingSamples = CSVLoader.samples;

        // ✅ Create KNN classifier
        KNNClassifier knn = new KNNClassifier(trainingSamples, 3);

        // ✅ Extract MFCC features from the given file
        MFCCExtractor extractor = new MFCCExtractor();
        float[] mfcc = extractor.extractMFCC(audioFilePath);
        
        if (mfcc == null) {
            System.out.println("⚠️ Could not extract features from the audio file.");
            return;
        }

     //   System.out.println("🎧 Extracted MFCC: " + Arrays.toString(mfcc));

        // ✅ Convert float[] to double[]
        double[] mfccDouble = new double[mfcc.length];
        for (int i = 0; i < mfcc.length; i++) {
            mfccDouble[i] = mfcc[i];
        }

        // ✅ Predict label
        String result = knn.predict(mfccDouble);
        System.out.println("🔊 Detected: " + result);

        // ✅ If it's a clap, count claps
        if (result.equalsIgnoreCase("clap")) {
            int clapCount = ClapDetector.detectClap(mfccDouble);
            System.out.println("👏 Total claps detected: " + clapCount);

            if (clapCount >= 10) {
                System.out.println("🎉 Great job! That's a lot of claps!");
            }
        } else {
            System.out.println("❌ No clap detected.");
        }
    }
}

*/
/*
package com.clapdetector;

import com.clapdetector.feature.MFCCExtractor;
import java.util.List;

public class SoundApp {
    public static void main(String[] args) {
        String datasetPath = "/home/ayyappankalai/ClapDetector/dataset.csv";
        String audioFilePath = "/home/ayyappankalai/ClapDetector/basic5.wav";

        // ✅ Load training data
        CSVLoader.load(datasetPath);
        List<Sample> trainingSamples = CSVLoader.samples;

        // ✅ Create KNN classifier
        KNNClassifier knn = new KNNClassifier(trainingSamples, 13);

        // ✅ Extract MFCC sequence from the given file (frame by frame)
        MFCCExtractor extractor = new MFCCExtractor();
        List<float[]> mfccSequence = extractor.extractMFCCSequence(audioFilePath);

        if (mfccSequence == null || mfccSequence.isEmpty()) {
            System.out.println("⚠️ Could not extract MFCC sequence from the audio file.");
            return;
        }

        // ✅ For classification, use the average of all frames
        int numCoefficients = mfccSequence.get(0).length;
        float[] avgMFCC = new float[numCoefficients];

        for (float[] frame : mfccSequence) {
            for (int i = 0; i < numCoefficients; i++) {
                avgMFCC[i] += frame[i];
            }
        }
        for (int i = 0; i < numCoefficients; i++) {
            avgMFCC[i] /= mfccSequence.size();
        }

        // ✅ Convert to double[]
        double[] mfccDouble = new double[avgMFCC.length];
        for (int i = 0; i < avgMFCC.length; i++) {
            mfccDouble[i] = avgMFCC[i];
        }

        // ✅ Predict label
        String result = knn.predict(mfccDouble);
        System.out.println("🔊 Detected: " + result);

        // ✅ Count claps over frames
        if (result.equalsIgnoreCase("clap")) {
            int clapCount = ClapDetector.detectClap(mfccSequence);
            System.out.println("👏 Total claps detected: " + clapCount);
        }
    }
}

*/
