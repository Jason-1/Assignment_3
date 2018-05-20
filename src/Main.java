import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

public class Main {

    int finalState;

    public static void main(String[] args)
    {
        try
        {
            Main REsearch = new Main();
            REsearch.run(args);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void run(String[] args)
    {
        DEque queue = new DEque();

        String[] inputSymbol;
        int[] next1;
        int[] next2;
        int arrayCounter;
        try
        {
            BufferedReader br = new BufferedReader(new FileReader("Input.txt"));
            String s;
            String[] w;
            int last = 0;
            while((s = br.readLine()) != null)
            {
                last++;
            }

            br = new BufferedReader(new FileReader("Input.txt"));

            inputSymbol = new String[last];
            next1 = new int[last];
            next2 = new int[last];



            String line;
            String[] words;
            while((line = br.readLine()) != null)
            {
                words = line.split(" ");
                for(int i = 0;i < words.length; i++)
                {

                    if(i == 0)
                    {
                        finalState = Integer.parseInt(words[i]);
                        System.out.print(finalState + " ");
                    }
                    else if(i == 1)
                    {
                        inputSymbol[finalState] = words[i];
                        System.out.print( inputSymbol[finalState] + " ");
                    }
                    else if(i == 2)
                    {
                        next1[finalState] = Integer.parseInt(words[i]);

                        if(next1[finalState]>=last)
                        {
                            System.err.println("Invalid state found, Attempting to jump to state " + next1[finalState] + " from state " + finalState);
                        }
                        System.out.print( next1[finalState] + " ");
                    }
                    else if(i == 3)
                    {
                        next2[finalState] = Integer.parseInt(words[i]);

                        if(next2[finalState]>=last)
                        {
                            System.err.println("Invalid state found, Attempting to jump to state " + next2[finalState] + " from state " + finalState);
                        }
                        System.out.println( next2[finalState]);
                    }
                    //too many arguments in line
                    if(i >= 4)
                    {
                        System.err.println("Too many arguments in one line");
                    }

                }
            }
            ///Arrays filled, begin search

            int FSMLocation = 0;
            int int1,int2;
            boolean success = false;
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            while((line = reader.readLine()) != null)
            {
                words = line.split("");

                for(int i = 0; i<words.length; i++)
                {
                    //first check if this is the final state
                    if(next1[FSMLocation] == 0)
                    {
                        //reached end of FSM, found match, print current line
                        System.out.println(line);
                        success = true;
                        break;
                    }

                    //if still on first state
                    else if(FSMLocation == 0)
                    {
                        boolean literal = false;
                        //while current state is not a literal (i.e. not yet found the first possible letter)
                        while(!literal)
                        {

                            if(!inputSymbol[FSMLocation].equals("."))
                            {
                                literal = true;
                            }
                            else
                            {
                                FSMLocation = next1[FSMLocation];
                            }

                        }
                        //now check if the state matches the first current letter in the input
                        if(inputSymbol[FSMLocation].equals(words[i]))
                        {
                            //move to the next state in the FSM

                            //if both match, just move on
                            if(next1[FSMLocation] == next2[FSMLocation])
                            {
                                FSMLocation = next1[FSMLocation];
                            }
                            //else add this state to the deque and move to the first next state
                            else
                            {
                                queue.addToFront(FSMLocation, next1[FSMLocation], next2[FSMLocation], inputSymbol[FSMLocation]);
                                FSMLocation = next1[FSMLocation];
                            }

                        }
                        else
                        {
                            //if the current input does not match, reset the FSM until we find a character matching the first state
                            System.out.println("No match");
                            FSMLocation = 0;
                        }


                    }
                    //else check if the current letter fits into the finite state machine
                    //general case
                    else
                    {
                        System.out.println(words[i]);
                    }
                }

                //now run out of input
                //if search was not successful
                if(!success)
                {
                    System.out.println("No match found");
                }



            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}



