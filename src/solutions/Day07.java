package src.solutions;

import src.meta.DayTemplate;
import src.objects.Coordinate;

import java.util.*;

public class Day07 extends DayTemplate {

    public String solve(boolean part1, Scanner in) {
        long answer = 0;
        List<Coordinate> splitters = new ArrayList<>();
        Map<Coordinate, Long> beams = new HashMap<>();
        int row = 0;
        while (in.hasNextLine()) {
            String[] line = in.nextLine().split("");
            for (int i = 0; i < line.length; i++) {
                if (line[i].equals("^")) {
                    splitters.add(new Coordinate(row, i));
                }
                if (line[i].equals("S")) {
                    beams.put(new Coordinate(row, i), 1L);
                }
            }
            row++;
        }
        while (beams.keySet().iterator().next().x < row) {
            Map<Coordinate, Long> oldBeams = new HashMap<>(beams);
            beams = new HashMap<>();
            for (Coordinate beam : oldBeams.keySet()) {
                Coordinate nextBeam = new Coordinate(beam.x + 1, beam.y);
                if (splitters.contains(nextBeam)) {
                    answer += part1 ? 1 : oldBeams.get(beam);
                    beams.merge(new Coordinate(beam.x + 1, beam.y + 1), oldBeams.get(beam), Long::sum);
                    beams.merge(new Coordinate(beam.x + 1, beam.y - 1), oldBeams.get(beam), Long::sum);
                } else {
                    beams.merge(nextBeam, oldBeams.get(beam), Long::sum);
                }
            }
        }
        if (!part1) {
            answer++; //account for original beam
        }
        return answer + "";
    }
}
