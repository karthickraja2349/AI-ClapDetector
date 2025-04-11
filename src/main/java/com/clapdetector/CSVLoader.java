/*package com.clapdetector;

import java.io.*;
import java.util.*;

public class CSVLoader {

    public static List<double[]> features = new ArrayList<>();
    public static List<Integer> labels = new ArrayList<>();
    public static Map<String, Integer> labelToInt = new HashMap<>();
    public static Map<Integer, String> intToLabel = new HashMap<>();

    public static void load(String csvFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            int labelIndex = 0;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                double[] feature = new double[parts.length - 1];
                for (int i = 0; i < feature.length; i++) {
                    feature[i] = Double.parseDouble(parts[i]);
                }

                String labelStr = parts[parts.length - 1].trim();

                if (!labelToInt.containsKey(labelStr)) {
                    labelToInt.put(labelStr, labelIndex);
                    intToLabel.put(labelIndex, labelStr);
                    labelIndex++;
                }

                int labelInt = labelToInt.get(labelStr);

                features.add(feature);
                labels.add(labelInt);
            }

            System.out.println("✅ Loaded " + features.size() + " samples from CSV.");

        } catch (IOException e) {
            System.err.println("❌ Error loading CSV: " + e.getMessage());
        }
    }
}
*/
package com.clapdetector;

import java.util.*;
import java.io.*;

public class CSVLoader {
    public static List<Sample> samples = new ArrayList<>();

    public static void load(String filepath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                double[] features = new double[parts.length - 1];
                for (int i = 0; i < parts.length - 1; i++) {
                    features[i] = Double.parseDouble(parts[i]);
                }
                String label = parts[parts.length - 1];
                samples.add(new Sample(features, label));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

