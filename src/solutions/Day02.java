package src.solutions;

import src.meta.DayTemplate;

import java.math.BigInteger;
import java.util.*;

/* BigInteger version for handling numbers up to 10^29 and beyond
 * Uses the same divisibility approach but with BigInteger arithmetic
 */

public class Day02 extends DayTemplate {

    // Cache for divisors by length (stored as BigInteger)
    private static final Map<Integer, BigInteger> part1Divisors = new HashMap<>();
    private static final Map<Integer, Set<BigInteger>> part2PrimeDivisors = new HashMap<>();

    // Precompute divisor for part 1
    private BigInteger getPart1Divisor(int len) {
        return part1Divisors.computeIfAbsent(len, l -> {
            // 10^(len/2) + 1
            return BigInteger.TEN.pow(l / 2).add(BigInteger.ONE);
        });
    }

    // Precompute prime divisors for part 2
    private Set<BigInteger> getPart2PrimeDivisors(int len) {
        return part2PrimeDivisors.computeIfAbsent(len, l -> {
            // First generate all divisors
            Set<BigInteger> allDivisors = new HashSet<>();
            for (int patternLen = 1; patternLen < len; patternLen++) {
                if (len % patternLen != 0) continue;

                // (10^len - 1) / (10^patternLen - 1)
                BigInteger numerator = BigInteger.TEN.pow(len).subtract(BigInteger.ONE);
                BigInteger denominator = BigInteger.TEN.pow(patternLen).subtract(BigInteger.ONE);
                BigInteger divisor = numerator.divide(denominator);
                allDivisors.add(divisor);
            }

            // Filter out divisors that are multiples of other divisors
            Set<BigInteger> primeDivisors = new HashSet<>();
            for (BigInteger d : allDivisors) {
                boolean isMultiple = false;
                for (BigInteger other : allDivisors) {
                    if (!other.equals(d) && d.mod(other).equals(BigInteger.ZERO)) {
                        isMultiple = true;
                        break;
                    }
                }
                if (!isMultiple) {
                    primeDivisors.add(d);
                }
            }

            return primeDivisors;
        });
    }

    private BigInteger gcd(BigInteger a, BigInteger b) {
        return a.gcd(b); // BigInteger has built-in GCD
    }

    private BigInteger lcm(BigInteger a, BigInteger b) {
        return a.multiply(b).divide(gcd(a, b));
    }

    public String solve(boolean part1, Scanner in) {
        BigInteger answer = BigInteger.ZERO;
        String[] lines = in.nextLine().split(",");

        for (String line : lines) {
            String[] parts = line.split("-");
            BigInteger low = new BigInteger(parts[0]);
            BigInteger high = new BigInteger(parts[1]);

            int lowLen = parts[0].length();
            int highLen = parts[1].length();

            // Process each length separately
            for (int len = lowLen; len <= highLen; len++) {
                // 10^(len-1) is the smallest len-digit number
                BigInteger rangeStart = low.max(BigInteger.TEN.pow(len - 1));
                // 10^len - 1 is the largest len-digit number
                BigInteger rangeEnd = high.min(BigInteger.TEN.pow(len).subtract(BigInteger.ONE));

                if (rangeStart.compareTo(rangeEnd) > 0) continue;

                if (part1) {
                    if (len % 2 == 1) continue;
                    BigInteger divisor = getPart1Divisor(len);
                    answer = answer.add(sumArithmeticSequence(rangeStart, rangeEnd, divisor));
                } else {
                    Set<BigInteger> primeDivisors = getPart2PrimeDivisors(len);

                    if (primeDivisors.size() == 1) {
                        answer = answer.add(sumArithmeticSequence(rangeStart, rangeEnd,
                                primeDivisors.iterator().next()));
                    } else if(primeDivisors.size() == 2) {
                        BigInteger[] divArray = primeDivisors.toArray(new BigInteger[2]);
                        BigInteger d1 = divArray[0];
                        BigInteger d2 = divArray[1];
                        BigInteger lcmDiv = lcm(d1, d2);

                        answer = answer.add(sumArithmeticSequence(rangeStart, rangeEnd, d1));
                        answer = answer.add(sumArithmeticSequence(rangeStart, rangeEnd, d2));
                        answer = answer.subtract(sumArithmeticSequence(rangeStart, rangeEnd, lcmDiv));
                    }
                }
            }
        }
        return answer.toString();
    }

    // Arithmetic sequence sum with BigInteger
    private BigInteger sumArithmeticSequence(BigInteger low, BigInteger high, BigInteger divisor) {
        // kStart = ceil(low / divisor) = (low + divisor - 1) / divisor
        BigInteger kStart = low.add(divisor).subtract(BigInteger.ONE).divide(divisor);
        // kEnd = floor(high / divisor)
        BigInteger kEnd = high.divide(divisor);

        if (kStart.compareTo(kEnd) > 0) return BigInteger.ZERO;

        BigInteger count = kEnd.subtract(kStart).add(BigInteger.ONE);
        BigInteger firstTerm = divisor.multiply(kStart);
        BigInteger lastTerm = divisor.multiply(kEnd);

        // sum = (firstTerm + lastTerm) * count / 2
        return firstTerm.add(lastTerm).multiply(count).divide(BigInteger.TWO);
    }
}