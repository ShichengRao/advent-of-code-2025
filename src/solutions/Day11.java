package src.solutions;

import src.meta.DayTemplate;

import java.util.*;

public class Day11 extends DayTemplate {

    public String solve(boolean part1, Scanner in) {
        long answer = 1;
        List<Node> nodes = new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();
        while (in.hasNextLine()) {
            String[] line = in.nextLine().split(" ");
            nodes.add(new Node(line));
            map.put(nodes.getLast().name, nodes.getLast().childNodes);
        }
        Map<String, Long> cache = new HashMap<>();
        if(part1){
            return helper("you", "out", cache, map) + "";
        }
        answer*= helper("svr", "fft", cache, map);
        cache = new HashMap<>();
        answer*= helper("fft", "dac", cache, map);
        cache = new HashMap<>();
        answer*= helper("dac", "out", cache, map);

        return answer + "";
    }

    private long helper(String curr, String goal, Map<String, Long> cache, Map<String, List<String>> map){
        long answer = 0;
        if(curr.equals(goal)){
            return 1;
        }
        else{
            if(curr.equals("out")){
                return 0;
            }
            for(String child: map.get(curr)){
                if(cache.containsKey(child)){
                    answer+=cache.get(child);
                }
                else{
                    answer+=helper(child, goal, cache, map);
                }
            }
        }
        cache.put(curr, answer);
        return answer;
    }
}

class Node{
    String name;
    List<String> childNodes = new ArrayList<>();
    List<String> path = new ArrayList<>();

    public Node(String[] line){
        childNodes = new ArrayList<>();
        name = line[0].substring(0, line[0].length() - 1);
        for(int i = 1; i < line.length; i++){
            childNodes.add(line[i]);
        }
        path.add(name);
    }
}
