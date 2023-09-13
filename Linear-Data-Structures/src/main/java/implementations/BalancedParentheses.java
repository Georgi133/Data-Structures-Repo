package implementations;

import interfaces.Solvable;

public class BalancedParentheses implements Solvable {
    private String parentheses;

    public BalancedParentheses(String parentheses) {
        this.parentheses = parentheses;
    }

    @Override
    public Boolean solve() {
        ArrayDeque<Character> stack = new ArrayDeque<>();

        for (char symbol : parentheses.toCharArray()) {
            if (symbol == '{') {
                stack.push(symbol);
            } else if (symbol == '(') {
                stack.push(symbol);
            } else if (symbol == '[') {
                stack.push(symbol);
            }else {
                if(stack.isEmpty() && (int)symbol > 1){
                    return false;
                }
                int current = stack.pop();

                if (symbol == '}' && current != '{') {
                    return false;
                } else if (symbol == ')' && current != '(') {
                    return false;
                } else if (symbol == ']' && current != '[') {
                    return false;
                }
            }
        }

        if(stack.isEmpty()) {
            return true;
        }else {
            return false;
        }


    }
}
