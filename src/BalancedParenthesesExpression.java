import java.util.Stack;

public class BalancedParenthesesExpression {
    // Any type of parentheses can be added here
    public static char[][] PARENTHESIS = {{'(', ')'}, {'{', '}'}, {'[', ']'}, {'<', '>'}};

    public static boolean isBalanced(String expression)
    {
        Stack<Character> stack = new Stack<Character>();
        for(char parenthesisType : expression.toCharArray())
        {
            if(checkOpen(parenthesisType))
            {
                stack.push(parenthesisType);
            }
            else{
                if(stack.isEmpty() || !checkClosed(stack.pop(), parenthesisType))
                {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    private static boolean checkOpen(char c) {
        for(char[] openClosed : PARENTHESIS)
        {
            if(openClosed[0] == c)
            {
                return true;
            }
        }
        return false;
    }

    private static boolean checkClosed(char opened, char closed) {
        for(char[] openClosed: PARENTHESIS)
        {
            if(openClosed[0] == opened)
            {
                return openClosed[1] == closed;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        //"{()[{({})[]()}]}([])"
        String x = "<{()[{({})[]()}]}([])>";
        String y = "<({[<]})>";
        System.out.println(isBalanced(x));
        System.out.println(isBalanced(y));
    }
}

