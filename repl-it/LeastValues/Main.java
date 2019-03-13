import java.util.Arrays;
import java.util.Optional;

class Main {
  public static void main(String[] args) {
    String[] arr = { "hello", "world", "these", "are different", "strings", "foo" };
    System.out.println(minVowels(arr));
    // that better print "world"
  }
  public static String minVowels(String[] words) {
    Optional<String> res = Arrays.stream(words).reduce((String acc, String current) -> {
      int accVC = countVowels(acc);
      int currentVC = countVowels(current);
      if (acc == null || accVC > currentVC || accVC == currentVC && current.length() < acc.length()) {
        return current;
      }
      return acc;
    });
    if (res.isPresent()) {
      return res.get();
    }
    throw new RuntimeException("Somehow failed to get the min vowels?");
  }
  public static int countVowels(String s) {
    int count = 0;
    for (String c : s.split("")) {
      if (isVowel(c)) {
        count++;
      }
    }
    return count;
  }
  public static boolean isVowel(String c) {
    return (
      c.equalsIgnoreCase("a")
      || c.equalsIgnoreCase("e")
      || c.equalsIgnoreCase("i")
      || c.equalsIgnoreCase("o")
      || c.equalsIgnoreCase("u")
    );
  }
}
