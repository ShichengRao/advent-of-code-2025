package src.solutions;

import src.meta.DayTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day06 extends DayTemplate {

    public String solve(boolean part1, Scanner in) {
        long answer = 0;
        List<String> lines = readLines(in);
        String operatorLine = lines.removeLast();
        List<Integer> operatorColumns = findOperatorColumns(operatorLine, lines.getFirst().length());

        for (int i = 0; i < operatorColumns.size() - 1; i++) {
            int column = operatorColumns.get(i);
            int width = operatorColumns.get(i + 1) - column - 1;
            answer += evaluateColumn(lines, column, width, operatorLine.charAt(column), part1);
        }

        return answer + "";
    }

    private List<String> readLines(Scanner in) {
        List<String> lines = new ArrayList<>();
        while (in.hasNextLine()) {
            lines.add(in.nextLine());
        }
        return lines;
    }

    private List<Integer> findOperatorColumns(String operatorLine, int lineLength) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < operatorLine.length(); i++) {
            if (operatorLine.charAt(i) != ' ') {
                indices.add(i);
            }
        }
        indices.add(lineLength + 1);
        return indices;
    }

    private long evaluate(List<Integer> ints, char operator) {
        long answer = 0;
        if (operator == '+') {
            for (Integer i : ints) {
                answer += i;
            }
        } else {
            answer = 1;
            for (Integer i : ints) {
                answer *= i;
            }
        }
        return answer;
    }

    private long evaluateColumn(List<String> strings, int index, int length, char operator, boolean part1) {
        List<Integer> ints = new ArrayList<>();
        if (part1) {
            for (String s : strings) {
                ints.add(Integer.parseInt(s.substring(index, index + length).trim()));
            }
        } else {
            for (int i = length - 1; i >= 0; i--) {
                StringBuilder tmp = new StringBuilder();
                for (String s : strings) {
                    tmp.append(s.substring(index, index + length).charAt(i));
                }
                ints.add(Integer.parseInt(tmp.toString().trim()));
            }
        }
        return evaluate(ints, operator);
    }
}
