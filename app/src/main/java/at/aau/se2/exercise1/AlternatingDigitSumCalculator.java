package at.aau.se2.exercise1;

public class AlternatingDigitSumCalculator {

    public int alternatingDigitSum(int input) {
        if (input < 0) {
            throw new IllegalArgumentException("Input must be >= 0");
        }

        int result = 0;

        int temp = input;
        while (temp != 0) {
            result += temp % 10;
            temp = -temp / 10;
        }

        return result;
    }
}