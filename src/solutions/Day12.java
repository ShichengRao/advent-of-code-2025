package src.solutions;

import src.meta.DayTemplate;

import java.util.Scanner;

public class Day12 extends DayTemplate {

    public String solve(boolean part1, Scanner in) {
        if (!part1) {
            return "Merry Christmas!";
        }
        for (int i = 0; i < 6; i++) {
            in.nextLine();
            in.nextLine();
            in.nextLine();
            in.nextLine();
            in.nextLine();
        }

        int possible = 0;
        while (in.hasNextLine()) {
            String[] line = in.nextLine().split(":");
            int length = Integer.parseInt(line[0].split("x")[0]);
            int width = Integer.parseInt(line[0].split("x")[1]);
            String[] nums = line[1].split(" ");
            int naiveUpper = 0;
            for (int i = 1; i < nums.length; i++) {
                int num = Integer.parseInt(nums[i]);
                naiveUpper += num;
            }
            if (naiveUpper <= length / 3 * width / 3) {
                possible++;
            }
        }
        return possible + "";
    }
}