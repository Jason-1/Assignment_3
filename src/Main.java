import java.io.BufferedReader;
import java.io.FileReader;

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

                success = false;
                arrayLocation = 0;
                //reset the deque for the next line of input
                queue = new DEque();

                //first state, make first possible state
                queue.addToFront(FSMLocation, next1[FSMLocation], next2[FSMLocation], inputSymbol[FSMLocation]);

                //for(int i = 0; i<words.length; i++)
                ///MAIN LOOP
                while(!success)
                {


                    if(next1[FSMLocation] == 0 && next2[FSMLocation] == 0)
                    {
                        //reached end of FSM, found match, print current line
                        System.out.println(line);
                        success = true;
                    }
                    //if run out of input
                    else if(arrayLocation >= words.length)
                    {
                        break;
                    }






                    //first check if this is the final state







                    //find next match
                    else
                    {

                        if(queue.first.symbol.equals("SCAN"))
                        {
                            currentState = queue.removeLast();
                            FSMLocation = currentState.state;
                        }
                        else
                        {
                            currentState = queue.removeFirst();
                            FSMLocation = currentState.state;
                        }



                        //now current state is the current state

                       // System.out.println(currentState.symbol);
                        if(currentState.symbol.equals("SCAN"))
                        {
                            break;
                        }

                        //check if next 2 states are equal, if so check if it matches, if so add it as the possible next state, if not go back
                        else if(currentState.n1 == currentState.n2)
                        {



                            //if this current state matches
                            if(currentState.symbol.equals(words[arrayLocation]))
                            {
                                FSMLocation = currentState.n1;
                                queue.addToFront(FSMLocation,next1[FSMLocation],next2[FSMLocation],inputSymbol[FSMLocation]);
                                //System.out.println("YAY");
                            }



                            //if this current state does not match
                            else
                            {

                                //if this isn't the first attempt at a match
                                if(!queue.first.symbol.equals("SCAN") || !queue.last.symbol.equals("SCAN"))
                                {

                                   // System.out.println("IM HERE");
                                    arrayLocation--;
                                    DEque.Node n = queue.last;

                                    //if the element before matches
                                    if(currentState.symbol.equals(words[arrayLocation]))
                                    {
                                        arrayLocation--;
                                        currentState = queue.removeLast();
                                    }

                                }
                                //if this is the first state, check each symbol until we find a start state
                                while (FSMLocation == 0 && queue.first.symbol.equals("SCAN") && queue.last.symbol.equals("SCAN"))
                                {
                                    arrayLocation++;

                                    if(arrayLocation>=words.length)
                                    {
                                        break;
                                    }
                                   // System.out.println("START WRONG");
                                    if(words[arrayLocation].equals(currentState.symbol))
                                    {

                                        FSMLocation = next1[FSMLocation];
                                        queue.addToFront(FSMLocation,next1[FSMLocation],next2[FSMLocation],inputSymbol[FSMLocation]);

                                    }
                                }


                            }

                        }



                        //else we are in a branching state
                        else
                        {
                            arrayLocation--;
                            queue.addToEnd(next1[FSMLocation],next1[next1[FSMLocation]],next2[next1[FSMLocation]],inputSymbol[next1[FSMLocation]]);
                            queue.addToEnd(next2[FSMLocation],next1[next2[FSMLocation]],next2[next2[FSMLocation]],inputSymbol[next2[FSMLocation]]);

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



            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}