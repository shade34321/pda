import java.util.Stack;

public class pda {
    private Stack states;

    public pda() {
        states = new Stack();
    }

    public Boolean evaluatePDA(String inputString){
        char[] input = inputString.toCharArray();
        Boolean isPDA = true;
        Boolean uno = false;

        if (input.length != 0 && isPDA) {
            for(char c: input) {
                if (Character.toString(c).matches("0") && !uno) {
                    System.out.println("Pushing 0 onto the stack...");
                    states.push(c);
                } else if (Character.toString(c).matches("1")) {
                    uno = true;
                    if (!states.empty()) {
                        states.pop();
                        System.out.println("Popping 0 from the stack for the current 1...");
                    } else {
                        System.out.println("We have nothing in the stack to pop....");
                        isPDA = false; //We have more 1's then 0's
                    }
                } else {
                    System.out.println("This is not part of the language....");
                    isPDA = false;
                }

                if(!isPDA) {
                    break;
                }
            }
        }


        if(!states.empty()) {
            // Our State stack is empty. This is a PDA
            isPDA = false;
        }

        return isPDA;
    }

    public void testPDA() {
        String t1 = "000111";
        String t2 = "01010101";
        String t3 = "";
        String t4 = "ababa";
        String t5 = "00011";
        String t6 = "111000";
        String t7 = "00111";
        String t8 = "0000";

        System.out.printf("Testing the string: %s\n", t1);
        System.out.println(evaluatePDA(t1));
        System.out.printf("Testing the string: %s\n", t2);
        System.out.println(evaluatePDA(t2));
        System.out.printf("Testing the string: %s\n", t3);
        System.out.println(evaluatePDA(t3));
        System.out.printf("Testing the string: %s\n", t4);
        System.out.println(evaluatePDA(t4));
        System.out.printf("Testing the string: %s\n", t5);
        System.out.println(evaluatePDA(t5));
        System.out.printf("Testing the string: %s\n", t6);
        System.out.println(evaluatePDA(t6));
        System.out.printf("Testing the string: %s\n", t7);
        System.out.println(evaluatePDA(t7));
        System.out.printf("Testing the string: %s\n", t8);
        System.out.println(evaluatePDA(t8));
    }

    public static void main(String[] args) {
        String test_string = "000111";
        pda p = new pda();
        System.out.println(test_string + (p.evaluatePDA(test_string) ? " is a PDA." : " is not a PDA."));
        p.testPDA();
    }
}
