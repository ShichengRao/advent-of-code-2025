package src.solutions;

import src.meta.DayTemplate;
import src.objects.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day09 extends DayTemplate {

    public String solve(boolean part1, Scanner in) {
        long answer = 0;
        List<Coordinate> coords = new ArrayList<>();
        while (in.hasNextLine()) {
            String[] line = in.nextLine().split(",");
            coords.add(new Coordinate(Integer.parseInt(line[0]), Integer.parseInt(line[1])));
        }
        for (int i = 0; i < coords.size(); i++) {
            for (int k = i + 1; k < coords.size(); k++) {
                Coordinate first = coords.get(i);
                Coordinate second = coords.get(k);
                long size = (long) (Math.abs(first.x - second.x) + 1) * (Math.abs(first.y - second.y) + 1);
                if (size > answer) {
                    if (part1) {
                        answer = size;
                    } else {
                        //check the four edges
                        boolean firstTwoEdges = true;
                        for (int j = Math.min(first.x, second.x); j <= Math.max(first.x, second.x); j++) {
                            if (!inLoop(j, first.y, coords) || !inLoop(j, second.y, coords)) {
                                firstTwoEdges = false;
                                break;
                            }
                        }
                        if (firstTwoEdges) {
                            boolean finalTwoEdges = true;
                            for (int j = Math.min(first.y, second.y); j <= Math.max(first.y, second.y); j++) {
                                if (!inLoop(first.x, j, coords) || !inLoop(second.x, j, coords)) {
                                    finalTwoEdges = false;
                                    break;
                                }
                            }
                            if (finalTwoEdges) {
                                answer = size;
                            }
                        }
                    }
                }
            }
        }
        return answer + "";
    }

    private boolean inLoop(int x, int y, List<Coordinate> loop) {
        for (int i = 0; i < loop.size(); i++) {
            Coordinate curr = loop.get(i);
            Coordinate next = loop.get((i + 1) % loop.size());

            if (curr.x == next.x && curr.x == x) {
                if (y >= Math.min(curr.y, next.y) && y <= Math.max(curr.y, next.y)) {
                    return true;
                }
            } else if (curr.y == next.y && curr.y == y) {
                if (x >= Math.min(curr.x, next.x) && x <= Math.max(curr.x, next.x)) {
                    return true;
                }
            }
        }

        int crossings = 0;
        for (int i = 0; i < loop.size(); i++) {
            Coordinate curr = loop.get(i);
            Coordinate next = loop.get((i + 1) % loop.size());

            if (curr.y == next.y) {
                continue;
            }

            int minY = Math.min(curr.y, next.y);
            int maxY = Math.max(curr.y, next.y);

            if (y >= minY && y < maxY && x < curr.x) {
                crossings++;
            }
        }
        return crossings % 2 == 1;
    }
}
