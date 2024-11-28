import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class CompoundWordFinder {

    public static void main(String[] args) throws IOException {
        
        String filePath = "Input_01.txt"; 
        
        List<String> words = readFile(filePath);
        long startTime = System.currentTimeMillis();
        
        Result result = findLongestCompoundWords(words);

        long endTime = System.currentTimeMillis();
        
        System.out.println("Longest Compound Word: " + result.longest);
        System.out.println("Second Longest Compound Word: " + result.secondLongest);
        System.out.println("Time taken to process file " + filePath + ": " + (endTime - startTime) + " milliseconds");
    }

    private static List<String> readFile(String filePath) throws IOException {
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            return br.lines().collect(Collectors.toList());
        }
    }

    private static Result findLongestCompoundWords(List<String> words) {
        
        Set<String> wordSet = new HashSet<>(words);
        
        words.sort((a, b) -> Integer.compare(b.length(), a.length()));

        String longest = "";
        String secondLongest = "";
        
        for (String word : words) {
            if (isCompoundWord(word, wordSet)) {
                if (longest.isEmpty()) {
                    longest = word;
                } else if (secondLongest.isEmpty()) {
                    secondLongest = word;
                    break; 
                }
            }
        }

        return new Result(longest, secondLongest);
    }

    private static boolean isCompoundWord(String word, Set<String> wordSet) {
        int len = word.length();
        boolean[] dp = new boolean[len + 1];
        dp[0] = true; 

        for (int i = 1; i <= len; i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordSet.contains(word.substring(j, i)) && !word.equals(word.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[len];
    }
    
    static class Result {
        String longest;
        String secondLongest;

        public Result(String longest, String secondLongest) {
            this.longest = longest;
            this.secondLongest = secondLongest;
        }
    }
}
