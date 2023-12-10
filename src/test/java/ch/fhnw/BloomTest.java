package ch.fhnw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class BloomTest {

    List<String> words = Utils.readFile("words.txt");
    double errorProbability = Math.random();
    double probabilityThreshold = 0.1;
    Bloom filter = new Bloom(words.size(), errorProbability);
    int numberOfWordsToTest = 10000;
    List<String> randomWords = Utils.generateRandomStrings(numberOfWordsToTest).stream()
        .filter(word -> !words.contains(word))
        .collect(Collectors.toList());


    @BeforeEach
    void setup() {
        filter.addAll(words);
    }

    @Test
    void contains() {
        // Count false positives
        int falsePositives = 0;
        for (String word : randomWords) {
            if (filter.contains(word)) falsePositives++;
        }

        // Assert false positive rate
        double falsePositiveRate = (double) falsePositives / (double) numberOfWordsToTest;
        assertTrue(
            falsePositiveRate <= errorProbability + probabilityThreshold,
            "False positive rate is " + falsePositiveRate + ", but should be about " + errorProbability
        );

        // Assert that each word that is in the set is classified as such
        for (String word : words) {
            assertTrue(filter.contains(word), word);
        }
    }
}