import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;


public class Subset {
    
    public static void main(String[] args) {
        int numberToPrint = Integer.parseInt(args[0]);
        String input = StdIn.readAll();
        String[] splitInput = input.replaceAll("\n", " ")
                .split(",");
         
        int y = numberToPrint;
        while (y-- > 0) { 
            System.out.println(splitInput[StdRandom.uniform(splitInput.length)]);
        }
    }
}
