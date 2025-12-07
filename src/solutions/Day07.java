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
        while(in.hasNextLine()){
            String[] line = in.nextLine().split("");
            for(int i = 0; i < line.length; i++){
                if(line[i].equals("^")){
                    splitters.add(new Coordinate(row, i));
                }
                if(line[i].equals("S")){
                    beams.put(new Coordinate(row, i), 1L);
                }
            }
            row++;
        }
        while(beams.keySet().iterator().next().x < row){
            System.out.println(beams.keySet().iterator().next().x + " " + beams.size());
            Map<Coordinate, Long> oldBeams = new HashMap<>();
            oldBeams.putAll(beams);
            beams = new HashMap<>();
            for(Coordinate beam: oldBeams.keySet()){
                Coordinate nextBeam = new Coordinate(beam.x + 1, beam.y);
                if(splitters.contains(nextBeam)){
                    answer+= oldBeams.get(beam);
                    Coordinate split1 = new Coordinate(beam.x + 1, beam.y + 1);
                    Coordinate split2 = new Coordinate(beam.x + 1, beam.y - 1);
                    if(beams.containsKey(split1)){
                        beams.put(split1,  oldBeams.get(beam) + beams.get(split1));
                    }
                    else{
                        beams.put(split1,  oldBeams.get(beam));
                    }
                    if(beams.containsKey(split2)){
                        beams.put(split2,  oldBeams.get(beam) + beams.get(split2));
                    }
                    else{
                        beams.put(split2,  oldBeams.get(beam));
                    }
                }
                else{
                    if(beams.containsKey(nextBeam)){
                        beams.put(nextBeam,  oldBeams.get(beam) + beams.get(nextBeam));
                    }
                    else{
                        beams.put(nextBeam,  oldBeams.get(beam));
                    }
                }
            }
        }
        long answer2 =0;
        for(long val: beams.values()){
            answer2+= val;
        }
        return answer+ " " + answer2;
    }
}
