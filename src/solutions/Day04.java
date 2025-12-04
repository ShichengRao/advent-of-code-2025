package src.solutions;

import src.meta.DayTemplate;
import src.objects.Coordinate;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Day04 extends DayTemplate {

    public String solve(boolean part1, Scanner in) {
        long answer = 0;
        Set<Coordinate> rolls = new HashSet<>();
        int row = 0;
        while (in.hasNextLine()) {
            String[] line = in.nextLine().split("");
            for (int col = 0; col < line.length; col++) {
                if (line[col].equals("@")) {
                    rolls.add(new Coordinate(row, col));
                }
            }
            row++;
        }
        while (true) {
            Set<Coordinate> forklift = new HashSet<>();
            for (Coordinate c : rolls) {
                int neighbors = 0;
                for (Coordinate neighbor : c.allNeighbors()) {
                    if (rolls.contains(neighbor)) {
                        neighbors++;
                    }
                }
                if (neighbors < 4) {
                    answer++;
                    forklift.add(c);
                }
            }
            if (forklift.isEmpty() || part1) {
                break;
            }
            rolls.removeAll(forklift);
        }

        return answer + "";
    }
}
