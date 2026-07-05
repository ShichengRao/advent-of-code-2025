package src.solutions;

import src.meta.DayTemplate;
import src.objects.Coordinate;

import java.util.*;

public class Day09 extends DayTemplate {

    public String solve(boolean part1, Scanner in) {
        LoopData loopData = readLoop(in);
        long answer = largestRectangleArea(part1, loopData);
        return answer + "";
    }

    private LoopData readLoop(Scanner in) {
        LoopData loopData = new LoopData();
        while (in.hasNextLine()) {
            String[] line = in.nextLine().split(",");
            int x = Integer.parseInt(line[0]);
            int y = Integer.parseInt(line[1]);
            loopData.xs.add(x);
            loopData.ys.add(y);
            loopData.coords.add(new Coordinate(x, y));
        }
        Collections.sort(loopData.xs);
        Collections.sort(loopData.ys);
        return loopData;
    }

    private long largestRectangleArea(boolean part1, LoopData loopData) {
        long answer = 0;
        List<Coordinate> coords = loopData.coords;

        for (int i = 0; i < coords.size(); i++) {
            for (int k = i + 1; k < coords.size(); k++) {
                Coordinate first = coords.get(i);
                Coordinate second = coords.get(k);
                long size = rectangleArea(first, second);
                if (size > answer) {
                    if (part1 || allEdgesInLoop(first, second, loopData)) {
                        answer = size;
                    }
                }
            }
        }
        return answer;
    }

    private long rectangleArea(Coordinate first, Coordinate second) {
        return (long) (Math.abs(first.x - second.x) + 1) * (Math.abs(first.y - second.y) + 1);
    }

    private boolean allEdgesInLoop(Coordinate first, Coordinate second, LoopData loopData) {
        return horizontalEdgesInLoop(first, second, loopData) && verticalEdgesInLoop(first, second, loopData);
    }

    private boolean horizontalEdgesInLoop(Coordinate first, Coordinate second, LoopData loopData) {
        int minX = Math.min(first.x, second.x);
        int maxX = Math.max(first.x, second.x);
        for (int x : loopData.xs) {
            if (x >= minX && x <= maxX
                    && (!inLoop(x, first.y, loopData.coords) || !inLoop(x, second.y, loopData.coords))) {
                return false;
            }
        }
        return true;
    }

    private boolean verticalEdgesInLoop(Coordinate first, Coordinate second, LoopData loopData) {
        int minY = Math.min(first.y, second.y);
        int maxY = Math.max(first.y, second.y);
        for (int y : loopData.ys) {
            if (y >= minY && y <= maxY
                    && (!inLoop(first.x, y, loopData.coords) || !inLoop(second.x, y, loopData.coords))) {
                return false;
            }
        }
        return true;
    }

    private boolean onLoopBoundary(int x, int y, List<Coordinate> loop) {
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
        return false;
    }

    private boolean inLoop(int x, int y, List<Coordinate> loop) {
        if (onLoopBoundary(x, y, loop)) {
            return true;
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

    private static class LoopData {
        List<Coordinate> coords = new ArrayList<>();
        List<Integer> xs = new ArrayList<>();
        List<Integer> ys = new ArrayList<>();
    }
}
