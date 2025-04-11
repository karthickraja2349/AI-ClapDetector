package com.clapdetector;

import java.util.*;

public class KNNClassifier {
    private List<Sample> trainingData;
    private int k;

    public KNNClassifier(List<Sample> trainingData, int k) {
        this.trainingData = trainingData;
        this.k = k;
    }

    public String predict(double[] inputFeatures) {
        PriorityQueue<SampleDistance> queue = new PriorityQueue<>(new Comparator<SampleDistance>() {
            @Override
            public int compare(SampleDistance sd1, SampleDistance sd2) {
                return Double.compare(sd1.distance, sd2.distance);
            }
        });

        for (Sample sample : trainingData) {
            double distance = euclideanDistance(inputFeatures, sample.features);
            queue.add(new SampleDistance(sample.label, distance));
        }

        Map<String, Integer> labelCounts = new HashMap<>();
        for (int i = 0; i < k && !queue.isEmpty(); i++) {
            SampleDistance sd = queue.poll();
            String label = sd.label;
            labelCounts.put(label, labelCounts.getOrDefault(label, 0) + 1);
        }

        // Find label with max count (no streams)
        String majorityLabel = null;
        int maxCount = -1;
        for (Map.Entry<String, Integer> entry : labelCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                majorityLabel = entry.getKey();
            }
        }

        return majorityLabel;
    }

    private double euclideanDistance(double[] a, double[] b) {
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            sum += Math.pow(a[i] - b[i], 2);
        }
        return Math.sqrt(sum);
    }

    private static class SampleDistance {
        String label;
        double distance;

        public SampleDistance(String label, double distance) {
            this.label = label;
            this.distance = distance;
        }
    }
}

