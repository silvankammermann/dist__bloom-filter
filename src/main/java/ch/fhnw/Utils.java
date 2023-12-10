package ch.fhnw;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Utils {

    /**
     * Read file from resources folder
     * 
     * @param fileName Filename of file to read
     * @return A list containing all lines as elements
     */
    public static List<String> readFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Utils.class.getClassLoader().getResourceAsStream(fileName), "UTF-8"))) {
            List<String> words = reader.lines().collect(Collectors.toList());
            return words;
        } catch (Exception e) {
            System.err.println(e);
        }
        return new ArrayList<>();
    }

    /**
     * Generate random string with lengths 1 - 10
     * 
     * @param numberOfStrings Number of strings to generate
     * @return List of random generated strings
     */
    public static List<String> generateRandomStrings(int numberOfStrings) {
        List<String> randomStrings = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < numberOfStrings; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            int length = (int) (Math.random() * 10) + 1;
            for (int j = 0; j < length; j++) {
                char randomChar = (char) ('a' + random.nextInt(26)); // Generating a random lowercase letter
                stringBuilder.append(randomChar);
            }
            randomStrings.add(stringBuilder.toString());
        }
        return randomStrings;
    }
}