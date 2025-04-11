package com.clapdetector;
import java.util.List;
public class ClapDetector {
   public static  int detectClap(double[] data) {
         
           //-101.855774,-0.18912072,6.7670794,-2.1911235,3.022114,-4.8858495,4.18655,-1.7915273,1.3262424,-2.2650583,5.7452717,-1.9472444,5.1724534
        

        double threshold = 8.0;  // You can adjust this
        int clapCount = 0;

        for (int i = 1; i < data.length; i++) {
            double diff = Math.abs(data[i] - data[i - 1]);
            if (diff > threshold) {
                clapCount++;
                System.out.printf("Clap detected at index %d (%.2f -> %.2f)\n", i - 1, data[i - 1], data[i]);
            }
        }

     System.out.println("Total number of claps detected: " + clapCount);
       return clapCount;
    }
    
    /*
    public static int detectClap(List<float[]> mfccSequence) {
    double threshold = 10.0;
    int clapCount = 0;

    for (int i = 1; i < mfccSequence.size(); i++) {
        float[] prev = mfccSequence.get(i - 1);
        float[] curr = mfccSequence.get(i);

        // For example, only use the 0th coefficient (energy-related)
        double diff = Math.abs(curr[0] - prev[0]);
        if (diff > threshold) {
            clapCount++;
            System.out.printf("Clap detected at frame %d\n", i);
        }
    }

    return clapCount;
}
*/
}



