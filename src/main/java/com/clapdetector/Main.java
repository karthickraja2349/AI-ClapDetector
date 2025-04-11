package com.clapdetector;

import java.util.List;

public class Main {
    public static void main(String[] args) {
       String datasetPath = "/home/ayyappankalai/ClapDetector/dataset.csv";
        // String datasetPath = "/home/ayyappankalai/ClapDetector/userSet.csv";

        // ✅ Load data
        CSVLoader.load(datasetPath);
        List<Sample> trainingSamples = CSVLoader.samples;

        // ✅ Create classifier with training data and k=3
        KNNClassifier knn = new KNNClassifier(trainingSamples, 3);

        // ✅ Accuracy check
        int correct = 0;
        for (Sample sample : trainingSamples) {
            String predicted = knn.predict(sample.features);
            if (predicted.equals(sample.label)) {
                correct++;
            }
        }

        double accuracy = (correct * 100.0) / trainingSamples.size();
        System.out.printf("✅ Accuracy: %.2f%%\n", accuracy);

        // ✅ Test prediction with a new sample
     //   double[] input = {0.25, 0.14, 0.76, 0.22}; // example values
     double[] input = {-99.93321,-10.799833,10.336783,-0.5828301,0.82533246,-4.2361946,2.0976863,0.9426222,3.537805,-2.3473585,3.0217986,-5.806735,2.8121274}; // low, scattered values likely to represent background noise

        String prediction = knn.predict(input);
        System.out.println("🎤 Predicted Label: " + prediction);
    }
}

/*
package com.clapdetector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String datasetPath = "/home/ayyappankalai/ClapDetector/userSet.csv";
        String userInputPath = "/home/ayyappankalai/ClapDetector/mfcc_output1.csv";

        // ✅ Load training dataset
        CSVLoader.load(datasetPath);
        List<Sample> trainingSamples = CSVLoader.samples;

        // ✅ Create classifier with training data and k=3
        KNNClassifier knn = new KNNClassifier(trainingSamples, 3);

        // ✅ Accuracy check
        int correct = 0;
        for (Sample sample : trainingSamples) {
            String predicted = knn.predict(sample.features);
            if (predicted.equals(sample.label)) {
                correct++;
            }
        }

        double accuracy = (correct * 100.0) / trainingSamples.size();
        System.out.printf("✅ Accuracy: %.2f%%\n", accuracy);

        // ✅ Read user input feature vector (just one row from mfcc_output.csv)
        try (BufferedReader br = new BufferedReader(new FileReader(userInputPath))) {
            String line;
            if ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                double[] input = new double[tokens.length];
                for (int i = 0; i < tokens.length; i++) {
                    input[i] = Double.parseDouble(tokens[i]);
                }

                // ✅ Predict using KNN
                String prediction = knn.predict(input);
                System.out.println("🎤 Predicted Label: " + prediction);
            } else {
                System.err.println("❌ Error: No data found in mfcc_output.csv.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
*/
