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
                words = line.split(",");
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
            int arrayLocation = 0;
            int int1,int2;
            boolean success = false;
            DEque.Node currentState;
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            while((line = reader.readLine()) != null)
            {
                words = line.split("");

                //reset the FSM for the next line of input
                FSMLocation = 0;

                //first state, make first possible state
                queue.addToFront(FSMLocation, next1[FSMLocation], next2[FSMLocation], inputSymbol[FSMLocation]);

                //for(int i = 0; i<words.length; i++)
                ///MAIN LOOP
                while(!success)
                {
                    //if run out of input
                    if(arrayLocation >= words.length)
                    {
                        break;
                    }

                    //first check if this is the final state
                    if(next1[FSMLocation] == 0 && next2[FSMLocation] == 0)
                    {
                        //reached end of FSM, found match, print current line
                        System.out.println(line);
                        success = true;
                    }

                    //if still on first state
                    else if(FSMLocation == 0)
                    {
                        //pop first element of deque
                       currentState =  queue.removeFirst();


                       //check if SCAN is the only element left in the queue
                        if(currentState.symbol.equals("SCAN"))
                        {
                            break;
                        }
                       //check if it matches the current element
                        //not a branching state so next1 and next2 match
                        if(currentState.symbol.equals(words[arrayLocation]))
                        {
                            FSMLocation = next1[FSMLocation];
                            queue.addToEnd(FSMLocation, next1[FSMLocation], next2[FSMLocation], inputSymbol[FSMLocation]);
                            System.out.println("MATCH");
                        }
                        else
                        {
                            //check if it is a branching state
                            if(currentState.symbol.equals(" "))
                            {
                                int i = next1[FSMLocation];
                                //add both possible next states to end of queue
                                queue.addToEnd(i, next1[i], next2[i], inputSymbol[i]);
                                i = next2[FSMLocation];
                                queue.addToEnd(i, next1[i], next2[i], inputSymbol[i]);
                                //queue.addToEnd(FSMLocation, next1[FSMLocation], next2[FSMLocation], inputSymbol[FSMLocation]);
                            }
                            //else doesn't match starting state
                            else
                            {
                                //put possible current state back on to the front of the queue
                                queue.addToFront(currentState.state,currentState.n1,currentState.n2,currentState.symbol);
                                System.out.println("HELLO");
                            }

                        }
                    }
                    //somewhere in the FSM, check for current states, if none pop next states onto front and check through them
                    else
                    {


                        //No current state, pop the two next states
                        //means we're in a branching state
                        if(queue.first.symbol.equals("SCAN"))
                        {

                            //No more states, failed to find match
                            if(queue.last.symbol.equals("SCAN"))
                            {
                                System.out.println("OH NO");
                            }
                            //pop 1 state from end to possible current states
                            else
                            {
                                currentState = queue.removeLast();

                                //if this is a branching state
                                //put as next states
                                if(currentState.symbol.equals(" "))
                                {
                                    int i = currentState.state;
                                    i = next1[i];
                                    queue.addToFront(i,next1[i],next2[i],inputSymbol[i]);

                                    i = next2[currentState.state];
                                    queue.addToEnd(i,next1[i],next2[i],inputSymbol[i]);
                                }
                                //not a branching state, check input
                                else
                                {
                                    //if match
                                    if(currentState.symbol.equals(inputSymbol[FSMLocation]))
                                    {
                                        System.out.println("Match in middle");
                                    }
                                    else
                                    {
                                        System.out.println("No Match in middle");
                                    }

                                }
                                System.out.println("Symbol: " + currentState.symbol);
                            }

                        }

                    }


                    //increments array counter
                    arrayLocation++;

                }

                //now run out of input
                //if search was not successful
                if(!success)
                {
                    System.out.println("No match found");
                }
                //reset the deque for the next line of input
                queue = new DEque();


            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}