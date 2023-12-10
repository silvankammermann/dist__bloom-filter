package ch.fhnw;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class Bloom {

  private final HashFunction HASH_FUNCTION = Hashing.murmur3_128();
  private final Charset CHARSET = StandardCharsets.UTF_8;

  /**
   * Bloom set
   */
  private final boolean[] bitArray;

  /**
   * Number of hash functions
   */
  private final int k;

  /**
   * Constructor
   * 
   * @param n Number of expected elements
   * @param p Desired probability of false positives
   */
  public Bloom(int n, double p) {
    int m = m(n, p);
    k = k(m, n);
    bitArray = new boolean[m];
  }

  /**
   * Calculate the optimal size of the bit array
   * 
   * @param n Number of expected elements in the set
   * @param p Desired probability of false positives
   * @return Ideal size of bit array
   */
  private int m(int n, double p) {
    return (int) Math.ceil((n * Math.log(p) / Math.log(1 / Math.pow(2, Math.log(2)))));
  }

  /**
   * Calculate the optimal number of hash functions
   * 
   * @param m Size of the bit array
   * @param n Number of expected elements in the set
   * @return Ideal number of hash functions
   */
  private int k(int m, int n) {
    return (int) Math.round((m / n) * Math.log(2));
  }

  /**
   * Get the index of the bit array for a given string
   * 
   * @param text
   * @return Index of the bit array
   */
  private int getArrayIndexFromString(String text) {
    int index = HASH_FUNCTION.hashString(text, CHARSET).asInt() % bitArray.length;
    while (index < 0) {
      index += bitArray.length;
    }
    return index;
  }

  /**
   * Add a single String to the bloom set
   * 
   * @param text
   */
  public void add(String text) {
    for (int i = 0; i < k; i++) {
      int position = getArrayIndexFromString(text + i);
      bitArray[position] = true;
    }
  }

  /**
   * Add a list of texts to the bloom set
   * 
   * @param texts
   */
  public void addAll(List<String> texts) {
    for (String text : texts) {
      add(text);
    }
  }

  /**
   * Apply the bloom filter to a text
   * 
   * @param text Text to apply bloom filter to
   * @return true if the text is in the set, false otherwise. May return true if
   *         the text is not in the set with an approximate probability of p
   *         (provided in the constructor)
   */
  public boolean contains(String text) {
    for (int i = 0; i < k; i++) {
      if (!bitArray[getArrayIndexFromString(text + i)])
        return false;
    }
    return true;
  }
}
