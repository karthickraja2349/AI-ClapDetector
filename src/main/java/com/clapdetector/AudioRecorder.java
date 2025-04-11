/*import javax.sound.sampled.*;
import java.io.*;
import java.util.Scanner;

public class AudioRecorder {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter file name (without .wav): ");
        String filename = scanner.nextLine().trim();

        File wavFile = new File(filename + ".wav");

        AudioFormat format = new AudioFormat(44100, 16, 1, true, false);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        try {
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported.");
                return;
            }

            final TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            System.out.println("Recording started... Press Enter to stop.");
            AudioInputStream ais = new AudioInputStream(line);

            Thread recordingThread = new Thread(() -> {
                try {
                    AudioSystem.write(ais, AudioFileFormat.Type.WAVE, wavFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            recordingThread.start();
            System.in.read(); // Wait for Enter
            line.stop();
            line.close();
            System.out.println("Recording stopped. Saved as " + wavFile.getAbsolutePath());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
*/

package com.clapdetector;

import javax.sound.sampled.*;
import java.io.*;
import java.util.Scanner;

public class AudioRecorder {
  
  public static void main(String[]args){
            Scanner scanner = new Scanner(System.in);

          System.out.print("Enter file name (without .wav): ");
          String filename = scanner.nextLine().trim();
          System.out.println(recordAudio(filename));
   }
    public  static  String recordAudio(String filename) {
        File wavFile = new File(filename + ".wav");

        AudioFormat format = new AudioFormat(44100, 16, 1, true, false);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        try {
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported.");
                return null;
            }

            final TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            System.out.println("🎙️ Recording started... Press Enter to stop.");
            AudioInputStream ais = new AudioInputStream(line);

            Thread recordingThread = new Thread(() -> {
                try {
                    AudioSystem.write(ais, AudioFileFormat.Type.WAVE, wavFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            recordingThread.start();
            System.in.read(); // Wait for Enter key
            line.stop();
            line.close();
            System.out.println("✅ Recording stopped. Saved as " + wavFile.getAbsolutePath());

            return wavFile.getAbsolutePath();

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}

