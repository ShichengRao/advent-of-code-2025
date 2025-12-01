package src.solutions;

import src.meta.DayTemplate;

import java.util.Scanner;

public class Day01 extends DayTemplate {

    public String solve(boolean part1, Scanner in) {
        long answer = 0;
        int original = 1000050;
        while(in.hasNextLine()){
            String line = in.nextLine();
            boolean direction = line.charAt(0) == 'R';
            int distance = Integer.parseInt(line.substring(1));
            if(part1){
                if(direction){
                    original+= distance;
                }
                if(!direction){
                    original-=distance;
                }
            }
            else{
                if(distance > 100){
                    answer += distance/100;
                    distance %= 100;
                }
                if(direction){
                    if((original % 100 + distance) > 100){
                        answer++;
                    }
                    original+= distance;
                }
                if(!direction){
                    if(original % 100 < distance && original % 100 != 0){
                        answer++;
                    }
                    original-=distance;
                }
            }
            if(original%100 == 0){
                answer++;
            }
        }
        return answer+ "";
    }
}
