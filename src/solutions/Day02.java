package src.solutions;

import src.meta.DayTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day02 extends DayTemplate {

    public String solve(boolean part1, Scanner in) {
        long answer = 0;
        String[] lines = in.nextLine().split(",");
        for(String line: lines){
            String[] parts = line.split("-");
            long low = Long.parseLong(parts[0]);
            long high = Long.parseLong(parts[1]);
            for(long i = low; i <= high; i++) {
                String test = i + "";
                int length = test.length();
                if(part1){
                    if(length % 2 == 1){
                        continue;
                    }
                    long divisor = (long)Math.pow(10, length/2) + 1;
                    if(i % divisor == 0){
                        answer+=i;
                    }
                }
                else{
                    for(int j = 1; j < length; j++){
                        if(length % j == 0){
                            boolean allSame = true;
                            String s = test.substring(0,j);
                            for(int k = 0; k < length / j; k++){
                                if(!test.substring(k * j, (k + 1) * j).equals(s)){
                                    allSame = false;
                                }
                            }
                            if(allSame){
                                answer+= i;
                                break;
                            }
                        }
                    }

                }
            }
        }
        return answer+ "";
    }
}