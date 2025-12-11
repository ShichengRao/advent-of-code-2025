package src.solutions;

import src.meta.DayTemplate;

import java.util.*;

public class Day11 extends DayTemplate {

    public String solve(boolean part1, Scanner in) {
        Map<String, List<String>> map = new HashMap<>();
        while (in.hasNextLine()) {
            String[] line = in.nextLine().split(" ");
            map.put(line[0].substring(0, line[0].length() - 1), Arrays.asList(line).subList(1, line.length));
        }
        if (part1) {
            return helper("you", "out", new HashMap<>(), map) + "";
        }
        long answer = 1;
        answer *= helper("svr", "fft", new HashMap<>(), map);
        answer *= helper("fft", "dac", new HashMap<>(), map);
        answer *= helper("dac", "out", new HashMap<>(), map);
        return answer + "";
    }

    private long helper(String curr, String goal, Map<String, Long> cache, Map<String, List<String>> map) {
        long answer = 0;
        if (curr.equals(goal)) {
            return 1;
        } else {
            if (map.get(curr) == null) {
                return 0;
            }
            for (String child : map.get(curr)) {
                answer += cache.containsKey(child) ? cache.get(child) : helper(child, goal, cache, map);
            }
        }
        cache.put(curr, answer);
        return answer;
    }
}