package src.solutions;

import src.meta.DayTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day12 extends DayTemplate {

    public String solve(boolean part1, Scanner in) {
        if(!part1){
            return "Merry Christmas!";
        }
        List<Integer> shapes = new ArrayList<>();
        for(int i = 0; i < 6; i++){
            in.nextLine();
            int totalOccupied = 0;
            totalOccupied += (int)Arrays.stream(in.nextLine().split("")).filter(a -> a.equals("#")).count();
            totalOccupied += (int)Arrays.stream(in.nextLine().split("")).filter(a -> a.equals("#")).count();
            totalOccupied += (int)Arrays.stream(in.nextLine().split("")).filter(a -> a.equals("#")).count();
            in.nextLine();
            shapes.add(totalOccupied);
        }

        int impossible = 0;
        int possible = 0;
        int inconclusive = 0;
        while (in.hasNextLine()) {
            String[] line = in.nextLine().split(":");
            int length = Integer.parseInt(line[0].split("x")[0]);
            int width = Integer.parseInt(line[0].split("x")[1]);
            String[] nums = line[1].split(" ");
            int naiveUpper = 0;
            int naiveLower = 0;
            for(int i = 1; i < nums.length; i++){
                int num = Integer.parseInt(nums[i]);
                naiveUpper += num;
                naiveLower += num * hardcode.get(i - 1);
            }
            if(naiveUpper <= length/3 * width/3){
                possible++;
            }
            else{
                if(naiveLower >= length * width){
                    impossible++;
                }
                else{
                    inconclusive++;
                }
            }


        }
        return possible+ "";
    }
}