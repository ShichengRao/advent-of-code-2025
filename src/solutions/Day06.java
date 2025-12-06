package src.solutions;

import src.meta.DayTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day06 extends DayTemplate {

    public String solve(boolean part1, Scanner in) {
        long answer = 0;
        List<String> lines = new ArrayList<>();
        while(in.hasNextLine()){
            lines.add(in.nextLine());
        }
        List<Integer> indices = new ArrayList<>();
        String operatorLine = lines.getLast();
        lines.removeLast();
        for(int i = 0; i < operatorLine.length(); i++){
            if(operatorLine.charAt(i) != ' '){
                indices.add(i);
            }
        }
        indices.add(lines.getFirst().length() + 1); //faked operator to be able to get size for the last operator
        for(int i = 0; i < indices.size() - 1; i++){
            int index = indices.get(i);
            answer += helper2(lines, index, indices.get(i + 1) - index - 1, operatorLine.charAt(index), part1);
        }

        return answer+ "";
    }

    private long helper(List<Integer> ints, char operator){
        long answer = 0;
        if(operator == '+'){
            for(Integer i: ints){
                answer+= i;
            }
        }
        else{
            answer = 1;
            for(Integer i: ints){
                answer*= i;
            }
        }
        return answer;
    }

    private long helper2(List<String> strings, int index, int length, char operator, boolean part1){
        List<Integer> ints = new ArrayList<>();
        if(part1){
            for(String s: strings){
                ints.add(Integer.parseInt(s.substring(index, index + length).trim()));
            }
        }
        else{
            for(int i = length - 1; i >= 0; i--){
                StringBuilder tmp = new StringBuilder();
                for(String s: strings){
                    tmp.append(s.substring(index, index + length).charAt(i));
                }
                ints.add(Integer.parseInt(tmp.toString().trim()));
            }
        }
        return helper(ints, operator);
    }
}
