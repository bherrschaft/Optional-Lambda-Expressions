import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class TextProcessor {
    public static void main(String[] args) {
        // Use relative paths for input and output files
        String inputFilePath = "src/main/java/input.txt";
        String outputFilePath = "src/main/java/sorted_words.txt";

        try {
            // Check if the input file exists
            Path inputPath = Paths.get(inputFilePath);
            if (!Files.exists(inputPath)) {
                throw new FileNotFoundException("The file " + inputFilePath + " does not exist.");
            }

            // Read all lines from the input file
            List<String> lines = Files.readAllLines(inputPath);
            if (lines.isEmpty()) {
                System.out.println("The file is empty.");
                return;
            }

            // 1. Count the number of lines in the file
            long lineCount = lines.size();
            System.out.println("Number of lines: " + lineCount);

            // 2. Count the number of words in the file
            long wordCount = lines.stream()
                    .flatMap(line -> Arrays.stream(line.split("\\s+")))
                    .filter(word -> !word.isEmpty())
                    .count();
            System.out.println("Number of words: " + wordCount);

            // 3. Find the longest word in the file
            Optional<String> longestWord = lines.stream()
                    .flatMap(line -> Arrays.stream(line.split("\\s+")))
                    .filter(word -> !word.isEmpty())
                    .max(Comparator.comparingInt(String::length));
            longestWord.ifPresent(word -> System.out.println("Longest word: " + word));

            // 4. Sort the words in alphabetical order (case-insensitive) and write to a new file
            List<String> sortedWords = lines.stream()
                    .flatMap(line -> Arrays.stream(line.split("\\s+")))
                    .map(word -> word.replaceAll("[^a-zA-Z]", "")) // Remove non-letter characters
                    .filter(word -> !word.isEmpty())
                    .map(String::toLowerCase) // Convert words to lowercase for case-insensitive sorting
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());

            // Write sorted words to the output file
            Files.write(Paths.get(outputFilePath), sortedWords);
            System.out.println("Sorted words have been written to: " + outputFilePath);

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
        }
    }
}
