package src.solutions;

import src.meta.DayTemplate;

import javax.swing.*;
import java.util.*;

public class Day08 extends DayTemplate {

    public String solve(boolean part1, Scanner in) {
        List<Box> boxes = new ArrayList<>();
        int index = 0;
        while (in.hasNextLine()) {
            String[] line = in.nextLine().split(",");
            boxes.add(new Box(Integer.parseInt(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]), index));
            index++;
        }
        Set<Pair> closest = new HashSet<>();
        int applies = 0;
        int tries = 0;
        while(tries < (part1?1000:1000000)){
            Pair pair = closest(boxes, closest);
            closest.add(pair);
            if(apply(pair, boxes)){
                applies++;
                if(applies == 999 && !part1){
                    return ((long)boxes.get(pair.a).x * boxes.get(pair.b).x) + "";
                }
            }
            tries++;
        }
        Map<Integer, Integer> cycles = new HashMap<>();
        for(Box box: boxes){
            int id = box.id;
            if(cycles.containsKey(id)){
                cycles.put(id, cycles.get(id) + 1);
            }
            else{
                cycles.put(id, 1);
            }
        }
        List<Integer> c = new ArrayList<>(cycles.values());
        Collections.sort(c);
        Collections.reverse(c);
        return (c.get(0) * c.get(1) * c.get(2))+ "";
    }

    private Pair closest(List<Box> boxes, Set<Pair> closest){
        long distance = Long.MAX_VALUE;
        Pair answer = null;
        for(int i = 0; i < boxes.size(); i++){
            for(int j = i + 1; j < boxes.size(); j++){
                Pair candidate = new Pair(i,j);
                if(closest.contains(candidate)){
                    continue;
                }
                Box box1 = boxes.get(i);
                Box box2 = boxes.get(j);
                long dist = (((long)box1.x - box2.x) * (box1.x - box2.x))+ (((long)box1.y - box2.y) * (box1.y - box2.y))+(((long)box1.z - box2.z) * (box1.z - box2.z));
                if(dist < distance){
                    distance = dist;
                    answer = candidate;
                }
            }
        }
        return answer;
    }

    private boolean apply(Pair pair, List<Box> boxes){
        boolean changed = false;
        int a = boxes.get(pair.a).id;
        int b = boxes.get(pair.b).id;
        if(a == b){
            return false;
        }
        for(Box box: boxes){
            if(box.id == a){
                box.id = b;
                changed = true;
            }
        }
        return changed;
    }
}

class Box{
    int x;
    int y;
    int z;
    int id;
    int immutableId;

    public Box(int x, int y, int z, int id){
        this.x = x;
        this.y = y;
        this.z = z;
        this.id = id;
        this.immutableId = id;
    }

    public String toString(){
        return x + " " + y + " " + z + " " + id;
    }
}

class Pair{
    int a;
    int b;
    public Pair(int first, int second){
        a = first;
        b = second;
    }
    @Override
    public boolean equals(Object other){
        if(other instanceof Pair pair){
            return (a == pair.a && b == pair.b) || (a == pair.b && b == pair.a);
        }
        return false;
    }

    public int hashCode(){
        return a * b;
    }
}
