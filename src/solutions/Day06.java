package src.solutions;

import src.meta.DayTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day06 extends DayTemplate {

    public String solve(boolean part1, Scanner in) {
        long answer = 0;
        List<Integer> row1 = new ArrayList<>();
        List<Integer> row2 = new ArrayList<>();
        List<Integer> row3 = new ArrayList<>();
        List<Integer> row4 = new ArrayList<>();
        List<String> row5 = new ArrayList<>();
        String r1 = in.nextLine();
        String r2 = in.nextLine();
        String r3 = in.nextLine();
        String r4 = in.nextLine();
        String r5 = in.nextLine();
        List<Integer> indices = new ArrayList<>();
        for(int i = 0; i < r5.length(); i++){
            if(r5.charAt(i) != ' '){
                indices.add(i);
            }
        }
        for(int i = 0; i < indices.size() - 1; i++){
            answer += helper2(r1,r2,r3,r4, indices.get(i), indices.get(i + 1) - indices.get(i) - 1, "" + r5.charAt(indices.get(i)));
        }

        return answer+ "";
    }

    private long helper(List<Integer> ints, String operator){
        long answer = 0;
        if(operator.equals("+")){
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

    private long helper2(String s1, String s2, String s3, String s4, int index, int length, String operator){
        String num1 = s1.substring(index, index+length);
        String num2 = s2.substring(index, index+length);
        String num3 = s3.substring(index, index+length);
        String num4 = s4.substring(index, index+length);
        List<Integer> ints = new ArrayList<>();
        for(int i = num1.length() - 1; i >= 0; i--){
            String tmp = "";
            tmp+=num1.charAt(i);
            tmp+=num2.charAt(i);
            tmp+=num3.charAt(i);
            tmp+=num4.charAt(i);
            ints.add(Integer.parseInt(tmp.trim()));
        }
        return helper(ints, operator);
    }
}
