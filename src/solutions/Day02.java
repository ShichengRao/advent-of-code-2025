package src.solutions;

import src.meta.DayTemplate;

import java.util.*;

/* I'm using n-divisor to mean that many repetitions. For example, all 8 digits numbers with 4 repetitions as divisible by 1010101
So, 1010101 is the 4-divisor of 8 digit numbers.
Prime divisor is just when n is prime.
Useful since divisors follow normal divisibility rules (e.g. 6 divisor is a multiple of 3-divisor and 2-divisor)
 */

public class Day02 extends DayTemplate {

    // Cache for divisors by length
    private static final Map<Integer, Long> part1Divisors = new HashMap<>();
    private static final Map<Integer, Set<Long>> part2PrimeDivisors = new HashMap<>();

    // Precompute divisor for part 1
    private long getPart1Divisor(int len) {
        return part1Divisors.computeIfAbsent(len, l -> (long) Math.pow(10, l / 2) + 1);
    }

    // Precompute prime divisors for part 2 (those not multiples of others)
    private Set<Long> getPart2PrimeDivisors(int len) {
        return part2PrimeDivisors.computeIfAbsent(len, l -> {
            // First generate all divisors
            Set<Long> allDivisors = new HashSet<>();
            for (int patternLen = 1; patternLen < len; patternLen++) {
                if (len % patternLen != 0) continue;

                long divisor = ((long) Math.pow(10, len) - 1) / ((long) Math.pow(10, patternLen) - 1);
                allDivisors.add(divisor);
            }

            // Filter out divisors that are multiples of other divisors
            Set<Long> primeDivisors = new HashSet<>();
            for (long d : allDivisors) {
                boolean isMultiple = false;
                for (long other : allDivisors) {
                    if (other != d && d % other == 0) {
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

    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private long lcm(long a, long b) {
        return (a / gcd(a, b)) * b;
    }

    public String solve(boolean part1, Scanner in) {
        long answer = 0;
        String[] lines = in.nextLine().split(",");
        for (String line : lines) {
            String[] parts = line.split("-");
            long low = Long.parseLong(parts[0]);
            long high = Long.parseLong(parts[1]);

//            Commented out part is regex solution. Significantly shorter, but less performance
//            String regex = part1 ? "^(.+)\\1$" : "^(.+)\\1+$";
//            Pattern p = Pattern.compile(regex);
//
//            for (long i = low; i <= high; i++) {
//                if (p.matcher(i + "").matches()) {
//                    answer += i;
//                }
//            }

            int lowLen = (low + "").length();
            int highLen = (high + "").length();

            // Process each length separately
            for (int len = lowLen; len <= highLen; len++) {
                long rangeStart = Math.max(low, (long) Math.pow(10, len - 1));
                long rangeEnd = Math.min(high, (long) Math.pow(10, len) - 1);

                if (rangeStart > rangeEnd) continue;

                if (part1) {
                    if (len % 2 == 1) continue;
                    long divisor = getPart1Divisor(len);
                    answer += sumArithmeticSequence(rangeStart, rangeEnd, divisor);
                } else {
                    Set<Long> primeDivisors = getPart2PrimeDivisors(len);
                    // If we have a single prime divisor, just sum it
                    if (primeDivisors.size() == 1) {
                        answer += sumArithmeticSequence(rangeStart, rangeEnd, primeDivisors.iterator().next());
                    }
                    // If we have two prime divisors, add both and subtract their LCM
                    else if(primeDivisors.size() == 2) {
                        Long[] divArray = primeDivisors.toArray(new Long[2]);
                        long d1 = divArray[0];
                        long d2 = divArray[1];
                        long lcmDiv = lcm(d1, d2);

                        answer += sumArithmeticSequence(rangeStart, rangeEnd, d1);
                        answer += sumArithmeticSequence(rangeStart, rangeEnd, d2);
                        answer -= sumArithmeticSequence(rangeStart, rangeEnd, lcmDiv);
                    }
                    // No need to consider 3+ divisors since len will be greater than max long's length
                    // log10(max long) ~= 18, 2x3x5 = 30
                }
            }
        }
        return answer + "";
    }

    private long sumArithmeticSequence(long low, long high, long divisor) {
        long kStart = (low + divisor - 1) / divisor;
        long kEnd = high / divisor;

        if (kStart > kEnd) return 0;

        long count = kEnd - kStart + 1;
        long firstTerm = divisor * kStart;
        long lastTerm = divisor * kEnd;

        return (firstTerm + lastTerm) * count / 2;
    }

}