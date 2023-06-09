public class StringAnalyzer {

    public int countVowels(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Input string must not be null");
        }

        int count = 0;
        for (char c : str.toLowerCase().toCharArray()) {
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                count++;
            }
        }
        return count;
    }

    public boolean isPalindrome(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Input string must not be null");
        }

        int start = 0;
        int end = str.length() - 1;
        while (start < end) {
            if (str.charAt(start) != str.charAt(end)) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

    public String reverse(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Input string must not be null");
        }

        StringBuilder reversed = new StringBuilder(str.length());
        for (int i = str.length() - 1; i >= 0; i--) {
            reversed.append(str.charAt(i));
        }
        return reversed.toString();
    }
}