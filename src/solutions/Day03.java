package src.solutions;

import src.meta.DayTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day03 extends DayTemplate {

    public String solve(boolean part1, Scanner in) {
        long answer = 0;
        while(in.hasNextLine()){
            String line = in.nextLine();
            List<Integer> tmp = new ArrayList<>();
            for(String s: line.split("")){
                tmp.add(Integer.parseInt(s));
            }
            answer+= findLargest(tmp, part1?2:12);
        }
        return answer+ "";
    }

    private int boundedMaxIndex(List<Integer> nums, int bound1, int bound2){
        int largest = 0;
        int index = 0;
        for(int i = bound1; i <= nums.size() - bound2; i++){
            if (nums.get(i) > largest){
                largest = nums.get(i);
                index = i;
            }
        }
        return index;
    }

    private long findLargest(List<Integer> nums, int numDigits){
       StringBuilder s = new StringBuilder();
       int currIndex = 0;
       for(int i = numDigits; i > 0; i--){
           currIndex = boundedMaxIndex(nums, currIndex, i) + 1;
           s.append(nums.get(currIndex - 1));
       }
       return Long.parseLong(s.toString());
    }
}
