import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;

public class Ex2 {
    public static String[] createTextFiles(int n, int seed, int bound) {
        //This function creates n text files with random number of lines.
        //The number of lines in each file is obtained using a helper function and the seed and bound parameters.
        //Each line in the text files consists of a random sentence with at least 10 characters.
        //The function returns an array of the file names.
        String[] fileNames = new String[n];
        Random rand = new Random(seed);
        for (int i = 0; i < n; i++) {
            String fileName = "file_" + (i + 1);
            fileNames[i] = fileName;
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
                int numLines = rand.nextInt(bound) + 1;
                for (int j = 0; j < numLines; j++) {
                    bw.write("World Hello ");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileNames;
    }

    public static int getNumOfLines(String[] fileNames) {
        //This function returns the total number of lines in all the files specified in the fileNames array.
        int numLines = 0;
        for (String fileName : fileNames) {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                while (br.readLine() != null) {
                    numLines++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return numLines;
    }

    public static int getNumOfLinesThreads(String[] fileNames) {
        //This function extends the Thread class and overrides the run() function.
        //Inside the run() function, the total number of lines in the file specified by the fileName parameter is calculated.
        //The function returns the total number of lines in all the files.
        int numLines = 0;
        for (String fileName : fileNames) {
            LineCounterThread thread = new LineCounterThread(fileName);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            numLines += thread.getNumLines();
        }
        return numLines;
    }

    private static class LineCounterThread extends Thread {
        //This function measures the time it takes to run the getNumOfLines() function.
        //The execution time is printed to the console.
        private String fileName;
        private int numLines;

        public LineCounterThread(String fileName) {
            this.fileName = fileName;
        }

        public int getNumLines() {
            return numLines;
        }

        @Override
        public void run() {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                while (br.readLine() != null) {
                    numLines++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createTextFilesTimed(int n, int seed, int bound) {
        long startTime = System.currentTimeMillis();
        createTextFiles(n, seed, bound);
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken to create files: " + (endTime - startTime) + " milliseconds");
    }

    public static void getNumOfLinesTimed(String[] fileNames) {
        long startTime = System.currentTimeMill.currentTimeMillis();
        getNumOfLines(fileNames);
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Execution time for getNumOfLinesTimed: " + totalTime + " milliseconds");
    }
}
