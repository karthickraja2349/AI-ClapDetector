# AI Clap Detector

This project is an AI-based sound classification system that detects **clap sounds** using:

- **MFCC (Mel Frequency Cepstral Coefficients)** for audio feature extraction
- **KNN (K-Nearest Neighbors)** algorithm for classification

## Features
- Detects clap sounds from audio file
- Extracts MFCC features using TarsosDSP
- Classifies between clap and noise

## Technologies Used
- Java
- TarsosDSP
- Git & GitHub

## Important Java Sources
(For Records the Audio)
- javax.sound.sampled.AudioFormat
-javax.sound.sampled.DataLineInfo
-javax.sound.sampled.DataLineInfo.Info

(For Preparing the Dataset)
- be.tarsos.dsp.AudioDispatcher;
- be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
- be.tarsos.dsp.mfcc.MFCC;
- be.tarsos.dsp.AudioEvent;
- be.tarsos.dsp.AudioProcessor;

(in Project)
- com.clapdetector.AudioRecorder                  ---!  for Records the Audio
- com.clapdetector.DatasetPreparer               ---!  for Preparing the DataSet
- com.clapdetector.CSVLoader                          ---! for Loads the DataSet 
- com.clapdetector.feature.MFCCExtractor     ---! for extract the frequencies from the Audio File
- com.clapdetector.KNNClassifier                     ---! for Train the model 
- com.clapdetector.SoundApp                            ---! Main file
 

## How to Run
1. Record audio samples of clap and noise.
2. Extract MFCC features.
3. Train the KNN model.
4. Test with new audio input.

## Author
👤 Karthick Raja  
📧 karthickraja182356@gmail.com

---

> This is part of my learning journey in sound-based AI detection.

