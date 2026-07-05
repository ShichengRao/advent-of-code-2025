package src.solutions;

import src.meta.DayTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Day05 extends DayTemplate {

    public String solve(boolean part1, Scanner in) {
        List<IDRange> ranges = readRanges(in);
        Collections.sort(ranges);

        long answer;
        if (part1) {
            answer = countBlockedIds(in, ranges);
        } else {
            answer = countMergedRangeSize(ranges);
        }
        return answer + "";
    }

    private List<IDRange> readRanges(Scanner in) {
        List<IDRange> ranges = new ArrayList<>();
        while (in.hasNextLine()) {
            String l = in.nextLine();
            if (l.isEmpty()) {
                break;
            }
            String[] line = l.split("-");
            ranges.add(new IDRange(Long.parseLong(line[0]), Long.parseLong(line[1])));
        }
        return ranges;
    }

    private long countBlockedIds(Scanner in, List<IDRange> ranges) {
        long answer = 0;
        while (in.hasNextLine()) {
            long line = Long.parseLong(in.nextLine());
            for (IDRange range : ranges) {
                if (range.contains(line)) {
                    answer++;
                    break;
                }
            }
        }
        return answer;
    }

    private long countMergedRangeSize(List<IDRange> ranges) {
        long answer = 0;
        IDRange currRange = ranges.getFirst();
        for (IDRange range : ranges) {
            if (range.low > currRange.high) {
                answer += currRange.high - currRange.low + 1;
                currRange = range;
            } else {
                currRange.high = Math.max(currRange.high, range.high);
            }
        }
        answer += currRange.high - currRange.low + 1;
        return answer;
    }
}

class IDRange implements Comparable<IDRange>{
    long low;
    long high;

    public IDRange(long l, long h){
        low = l;
        high = h;
    }

    public boolean contains(long l){
        return l >= low && l <= high;
    }

    @Override
    public int compareTo(IDRange o) {
        if(low > o.low){
            return 1;
        }
        if(o.low > low){
            return -1;
        }
        return 0;
    }

    public String toString(){
        return low + " " + high;
    }
}
