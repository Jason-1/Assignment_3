//1288205
//1210259

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class REsearch {

    int finalState;

    public static void main(String[] args)
    {
        try
        {
            REsearch REs = new REsearch();
            REs.run(args);
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
            BufferedReader br = new BufferedReader(new FileReader(args[0]));
			BufferedWriter writer = new BufferedWriter(new FileWriter("Output.txt"));
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

            //find 1st branching state(Start state)
            ///*ASSUMING THE FIRST BRANCHING STATE IS THE START STATE*///
            int startState = -1;
            int h = 0;
            boolean found = false;
            while(!found)
            {
                if(inputSymbol[h].equals(" "))
                {
                    found = true;
                    startState = h;
                }
                else
                {
                    h++;
                }
            }
           // System.out.println(startState);

            ///Arrays filled, begin search

            int FSMLocation = 0;
            int arrayLocation = 0;
            int int1,int2;
            boolean success = false;
            DEque.Node currentState;
            BufferedReader reader = new BufferedReader(new FileReader(args[1]));
            while((line = reader.readLine()) != null)
            {
                words = line.split("");

                //reset the FSM for the next line of input
                FSMLocation = startState;

                success = false;
                arrayLocation = 0;
                //reset the deque for the next line of input
                queue = new DEque();

                //first state, make first possible state
                queue.addToFront(FSMLocation, next1[FSMLocation], next2[FSMLocation], inputSymbol[FSMLocation]);
                currentState = null;

                ///MAIN LOOP
                while(!success)
                {


                    if(next1[FSMLocation] == 0 && next2[FSMLocation] == 0)
                    {
                        //reached end of FSM, found match, print current line
                        System.out.println(line);
						writer.write(line + System.lineSeparator());
                        success = true;
                    }
                    //if run out of input
                    else if(arrayLocation >= words.length)
                    {
                        if(currentState.n1 == 0 && currentState.n2 == 0)
                        {
                            success = true;
                        }
                        break;
                    }

                    //find next match
                    else
                    {

                        //if there is a current state
                        if(!queue.first.symbol.equals("SCAN"))
                        {
                            currentState = queue.removeFirst();
                            FSMLocation = currentState.state;
                        }


                        //there is no current state
                        //pop the first possible next state and make it the current state
                        else
                        {

                            currentState = queue.removeLast();
                            FSMLocation = currentState.state;

                            //add the other possible state as the other possible current state
                            if(!queue.last.symbol.equals("SCAN"))
                            {
                                DEque.Node node = queue.removeLast();
                                queue.addToFront(node.state,node.n1,node.n2,node.symbol);
                            }


                        }

                        if(currentState.n1 == -1 || currentState.n2 == -1)
                        {
                            break;
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



                            //if this current state matches or is a free character
                            if(currentState.symbol.equals(words[arrayLocation]) || currentState.symbol.equals(" "))
                            {
                                FSMLocation = currentState.n1;
                                queue.addToFront(FSMLocation,next1[FSMLocation],next2[FSMLocation],inputSymbol[FSMLocation]);
                            }


                            //if this current state does not match
                            else
                            {

                                //if this isn't the first attempt at a match
                                if(!queue.first.symbol.equals("SCAN") || !queue.last.symbol.equals("SCAN"))
                                {

                                    arrayLocation--;


                                }
                                //if this is the first state, check each symbol until we find a start state
                                else
                                {
                                    //if
                                    if(currentState.state == startState && queue.first.symbol.equals("SCAN") && queue.last.symbol.equals("SCAN"))
                                    {
                                        while (currentState.state == startState && queue.first.symbol.equals("SCAN") && queue.last.symbol.equals("SCAN"))
                                        {
                                            arrayLocation++;

                                            if(arrayLocation>=words.length)
                                            {
                                                break;
                                            }

                                            if(words[arrayLocation].equals(currentState.symbol))
                                            {

                                                FSMLocation = next1[FSMLocation];
                                                queue.addToFront(FSMLocation,next1[FSMLocation],next2[FSMLocation],inputSymbol[FSMLocation]);

                                            }
                                        }
                                    }
                                    else
                                    {
                                        //mismatched start
                                        //move one along the array and forget about progress so far
                                        //arrayLocation++;
                                        queue = new DEque();
                                        FSMLocation = startState;
                                        queue.addToFront(FSMLocation, next1[FSMLocation], next2[FSMLocation], inputSymbol[FSMLocation]);
                                    }
                                }



                            }

                        }



                        //else we are in a branching state
                        else
                        {
                            //check if it already exists at the end
                            //go back 2
                            if(queue.last.state == FSMLocation)
                            {
                                arrayLocation--;arrayLocation--;
                            }
                            else
                            {
                                arrayLocation--;
                                queue.addToEnd(next1[FSMLocation], next1[next1[FSMLocation]], next2[next1[FSMLocation]], inputSymbol[next1[FSMLocation]]);
                                 queue.addToEnd(next2[FSMLocation], next1[next2[FSMLocation]], next2[next2[FSMLocation]], inputSymbol[next2[FSMLocation]]);
                            }
                           // arrayLocation--;
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
			writer.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

class DEque {

    private int size;
    public Node first;
    public Node last;

    public DEque()
    {
        first = new Node(-1,-1,-1,"SCAN");
        last = first;
        size = 1;
    }



    public class Node{
        public Node next, previous;
        int state, n1, n2;
        String symbol;
        public boolean right = false;

        public Node(int State, int N1, int N2, String Symbol)
        {
            state = State;
            n1 = N1;
            n2 = N2;
            symbol = Symbol;
        }

    }

    public boolean empty()
    {
        return first == null;
    }

    public int size()
    {
        return size;
    }

    public void addToFront(int State, int N1, int N2, String Symbol)
    {
        Node temp;
        temp = new Node(State, N1, N2, Symbol);

        //check if there is already a first node
        if(first == null)
        {
            first = temp;
            last = temp;
            size = 1;
        }
        else
        {
            temp.next = first;
            first.previous = temp;
            first = temp;
            size++;
        }

    }


    public void addToEnd(int State, int N1, int N2, String Symbol)
    {
        Node temp;
        temp = new Node(State, N1, N2, Symbol);

        //if the deque is currently empty
        //make the first and last elements equal to this new node
        if(first == null)
        {
            first = temp;
            last = temp;
            size = 1;
        }
        else
        {
            temp.previous = last;
            last.next = temp;
            last = temp;
            size++;
        }
    }

    public Node removeFirst()
    {
        Node temp = first;
        first = first.next;
        size--;
        return temp;

    }

    public Node removeLast()
    {
        Node temp = last;
        last = last.previous;
        size--;
        return temp;

    }

}

