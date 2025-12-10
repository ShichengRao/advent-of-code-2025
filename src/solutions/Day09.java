package src.solutions;

import src.meta.DayTemplate;
import src.objects.Coordinate;

import java.util.*;

public class Day09 extends DayTemplate {

    public String solve(boolean part1, Scanner in) {
        long answer = 0;
        List<Coordinate> coords = new ArrayList<>();
        List<Integer> xs = new ArrayList<>();
        List<Integer> ys = new ArrayList<>();
        while (in.hasNextLine()) {
            String[] line = in.nextLine().split(",");
            xs.add(Integer.parseInt(line[0]));
            ys.add(Integer.parseInt(line[1]));
            coords.add(new Coordinate(xs.getLast(), ys.getLast()));
        }
        Collections.sort(xs);
        Collections.sort(ys);

        for (int i = 0; i < coords.size(); i++) {
            for (int k = i + 1; k < coords.size(); k++) {
                Coordinate first = coords.get(i);
                Coordinate second = coords.get(k);
                long size = (long) (Math.abs(first.x - second.x) + 1) * (Math.abs(first.y - second.y) + 1);
                if (size > answer) {
                    if (part1) {
                        answer = size;
                    } else {
                        boolean allEdgesIn = true;

                        int minX = Math.min(first.x, second.x);
                        int maxX = Math.max(first.x, second.x);

                        // Check only x-coordinates that exist in the range
                        for (int x : xs) {
                            if (x >= minX && x <= maxX) {
                                if (!inLoop(x, first.y, coords) || !inLoop(x, second.y, coords)) {
                                    allEdgesIn = false;
                                    break;
                                }
                            }
                        }

                        if (allEdgesIn) {
                            int minY = Math.min(first.y, second.y);
                            int maxY = Math.max(first.y, second.y);

                            for (int y : ys) {
                                if (y >= minY && y <= maxY) {
                                    if (!inLoop(first.x, y, coords) || !inLoop(second.x, y, coords)) {
                                        allEdgesIn = false;
                                        break;
                                    }
                                }
                            }
                        }

                        if (allEdgesIn) {
                            answer = size;
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
