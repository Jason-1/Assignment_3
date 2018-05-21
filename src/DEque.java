public class DEque {

    private int size;
    public Node first;
    public Node last;

    public DEque()
    {
        first = new Node(-1,-1,-1,"SCAN");
        last = first;
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

