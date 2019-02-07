import java.util.Stack;
import java.util.Scanner;

class Node
{
    char data;
    Node left;
    Node right;

    Node(char item)
    {
        data = item;
        left = null;
        right = null;
    }
}


public class ExpressionTree {

    // Checks the precedence of operators based on order of operations
    static int checkPrecedence(char c)
    {
        switch(c)
        {
            case '+':
            case '-':
                return 1;

            case '*':
            case '/':
                return 2;

            case '^':
                return 3;
        }

        return -1;
    }


    static String toPostfix(String s)
    {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        String infix = s.replaceAll("\\s", "");		//creates string and takes out all white space

        // Iterates through the postfix expression
        for(int i=0; i < infix.length(); ++i)
        {
            char c = infix.charAt(i);

            if(Character.isLetterOrDigit(c))	//If letter or digit, add to postfix string
            {
                postfix.append(c);
            }

            else if(c == '(')
            {
                stack.push(c);
            }

            // pop from stack and append to postfix until an opening parenthesis occurs
            else if(c == ')')
            {
                while(!stack.isEmpty() && stack.peek() != '(')
                {
                    postfix.append(stack.pop());
                }

                if(!stack.isEmpty() && stack.peek() != '(')
                    return "This is not an infix expression";
                else
                    stack.pop();
            }

            else	//an operator has occurred
            {
                // while precedence of current char is less than what is on top of the stack
                // the top of stack will append to the postfix string
                while(!stack.isEmpty() && checkPrecedence(c) <= checkPrecedence(stack.peek()))
                {
                    postfix.append(stack.pop());
                }
                stack.push(c);		//pass the remaining operator to stack
            }

        }	//end for

        // pop the rest of the operators to the postfix string
        while(!stack.isEmpty())
        {
            postfix.append(stack.pop());
        }

        return postfix.toString();
    }

    static boolean checkOperator(char c)
    {
        if(c == '+' || c == '-' || c == '*' || c == '/' || c == '^')
        {
            return true;
        }
        else
            return false;
    }

    static void inOrder(Node node)
    {
        if(node != null)
        {
            inOrder(node.left);
            System.out.print(node.data + " ");
            inOrder(node.right);
        }
    }

    static void postOrder(Node node)
    {
        if(node != null)
        {
            postOrder(node.left);
            postOrder(node.right);
            System.out.print(node.data + " ");
        }
    }

    static void preOrder(Node node)
    {
        if(node != null)
        {
            System.out.print(node.data + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    static Node createTree(String s)
    {
        Stack<Node> stack = new Stack<Node>();
        char postfix[] = s.toCharArray();
        Node node;
        Node child1;
        Node child2;

        for (int i=0; i < postfix.length; i++)
        {
            if(checkOperator(postfix[i]))
            {
                node = new Node(postfix[i]);	//creates a new node with its data as the item
                child1 = stack.pop();			//pops two items from stack to set as children
                child2 = stack.pop();

                node.right = child1;
                node.left = child2;

                stack.push(node);	//the node with the children are pushed into the stack
            }
            else    //the item that occurred is an operand
            {
                node = new Node(postfix[i]);
                stack.push(node);				//the new node with its data is pushed to the stack
            }

        }
        node = stack.peek();	//the last item in the stack is the root of the new tree
        stack.pop();

        return node;
    }

    // Shows infix, postfix, and prefix
    static void showOrders(Node tree)
    {
        System.out.print("Infix: ");
        inOrder(tree);
        System.out.println("\n");

        System.out.print("Postfix: ");
        postOrder(tree);
        System.out.println("\n");

        System.out.print("Prefix: ");
        preOrder(tree);
        System.out.println("\n");

        System.out.print("Answer: " + calculate(tree));
    }


    public static double calculate(Node node)
    {
        if(node == null){
            return 0;
        }

        if(node.right == null && node.left == null)
        {
            return (double) Character.getNumericValue(node.data);
        }

        if(node.data == '+')
        {
            return (double)(calculate(node.left) + calculate(node.right));
        }

        if(node.data == '-')
        {
            return (double)(calculate(node.left) - calculate(node.right));
        }

        if(node.data == '*')
        {
            return (double)(calculate(node.left) * calculate(node.right));
        }

        if(node.data == '/')
        {
            return (double)(calculate(node.left) / calculate(node.right));
        }

        if(node.data == '^')
        {
            return (double)(Math.pow(calculate(node.left), calculate(node.right)));
        }

        else
            return 0;
    }



    public static void main(String[] args) {

        System.out.println("Here is an example: ");
        String exp2 = "((2^2) ^ (2*2) - 6 + 8) / 2";
        String postfix2 = toPostfix(exp2);
        Node tree2 = createTree(postfix2);
        showOrders(tree2);
        System.out.println("\n");

        Scanner input = new Scanner(System.in);
        boolean choice = true;

        while(choice == true)
        {
            System.out.println("Enter an infix expression: ");
            String exp = input.nextLine();
            String postfix = toPostfix(exp);
            Node tree = createTree(postfix);
            showOrders(tree);
            System.out.println("\n");

            System.out.println("Would you like to enter another expression?\tY/N ");
            String option = input.nextLine().toLowerCase();
            System.out.println();

            if(option.charAt(0) == 'y')
            {
                choice = true;
            }
            else
            {
                choice = false;
                System.out.println("Thanks.");
            }
        }
    }
}
