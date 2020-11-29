//look into the method "private value get()..not completed"

import java.util.*;

public class Solution1<Key extends Comparable<Key>, Value> {
    private Node root; // root of BST

    private class Node {
        private Key key; // sorted by key
        private Value val; // associated data
        private Node left, right; // left and right subtrees
        private int size; // number of nodes in subtree
        private int count;

        public Node(Key key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    // /**
    // * Initializes an empty symbol table.
    // */
    public Solution1() {
        root = null;
        // size=0;
    }

    /**
     * Returns true if this symbol table is empty.
     * 
     * @return {@code true} if this symbol table is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        if (size() == 0) {
            System.out.println("The given symbol table is empty");
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     * 
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return size(root);

    }

    // return number of key-value pairs in BST rooted at x
    private int size(Node x) {
        if (x == null) {
            return 0;
        } else {
            return x.size;// even count ccan be used here .
        }
    }

    /**
     * Does this symbol table contain the given key?
     *
     * @param key the key
     * @return {@code true} if this symbol table contains {@code key} and
     *         {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(Key key) {
        if (key == null)
            throw new IllegalArgumentException("THe argument to contain() is null or empty");
        return get(key) != null;
    }

    /**
     * Returns the value associated with the given key.
     *
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol
     *         table and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(Key key) {
        Node x = root; // x is current node
        while (x.key != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0)
                x = x.left;
            else if (cmp > 0)
                x = x.right;
            else
                return x.val;
        }
        return null;
    }
    // private Value get(Node x, Key key) {

    // }
    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the
     * old value with the new value if the symbol table already contains the
     * specified key. Deletes the specified key (and its associated value) from this
     * symbol table if the specified value is {@code null}.
     *
     * @param key the key
     * @param val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Key key, Value val) {
        if (key == null)
            throw new IllegalArgumentException("Key Cannot be Null");
        if (root == null) {
            root = new Node(key, val, 0);
            return;
        }
        if (value(root, key, val))
            return;

        Node node = null, x = root, z = new Node(key, val, 0);
        while (x != null) {
            node = x;

            int temp = key.compareTo(node.key);
            if (temp < 0) {
                node.size++;
                x = x.left;

            }
            if (temp > 0) {
                node.size++;
                x = x.right;
            }
        }
        int temp = key.compareTo(node.key);
        if (temp < 0) {
            node.left = z;
        } else {
            node.right = z;
        }
        node.size = 1 + size(node.left) + size(node.right);

    }

    private boolean value(Node x, Key key, Value val) {
        while (x != null) {
            int temp = key.compareTo(x.key);
            if (temp < 0) {
                x = x.left;
            } else if (temp > 0) {
                x = x.right;
            } else {
                x.val = val;
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the smallest key in the symbol table.
     *
     * @return the smallest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key min() {
        if (isEmpty()) {
            throw new IllegalArgumentException("symbol table is empty");
        } else {
            return min(root).key;
        }
    }

    private Node min(Node x) {
        if (x.left == null) {
            return x;
        }
        while (x != null) {
            x = x.left;
            if (x.left == null) {
                return x;
            }
        }
        return x;
    }

    // //utility function to create a new node
    // static Node newNode(Key key)
    // private Node min(Node x) {
    // if(x==null){
    // System.out.println("The given BST is empty");
    // //return...?
    // }

    // }

    /**
     * Returns the largest key in the symbol table less than or equal to
     * {@code key}.
     *
     * @param key the key
     * @return the largest key in the symbol table less than or equal to {@code key}
     * @throws NoSuchElementException   if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Key floor(Key key) {
        if (key == null)
            throw new IllegalArgumentException("Key is Null");
        if (isEmpty())
            throw new NoSuchElementException("Empty symbol table");
        Node x = floor(root, key);
        if (x == null)
            return null;
        else
            return x.key;
    }

    private Node floor(Node x, Key key) {
        Node z = null, floor = min(x);

        while (x != null) {
            int com = key.compareTo(x.key);
            if (com < 0)
                x = x.left;

            else if (com > 0) {
                int temp = floor.key.compareTo(x.key);

                if (temp <= 0) {
                    floor = x;
                    z = floor;
                }
                x = x.right;
            } else
                return x;
        }
        return z;

    }

    /**
     * Return the key in the symbol table whose rank is {@code k}. This is the
     * (k+1)st smallest key in the symbol table.
     *
     * @param k the order statistic
     * @return the key in the symbol table of rank {@code k}
     * @throws IllegalArgumentException unless {@code k} is between 0 and
     *                                  <em>n</em>–1
     */
    public Key select(int k) {
        if (k < 0 || k >= size())
            throw new NoSuchElementException("that is not a rank");

        Node temp = select(root, k);
        if (temp != null) {
            return temp.key;
        } else {
            return null;
        }
    }

    // Return key of rank k.
    private Node select(Node x, int k) {
        while (x != null) {
            int a = size(x.left);
            if (a > k) {
                x = x.left;
            } else if (a < k) {
                x = x.right;
                k = k - a - 1;
            } else
                return x;
        }
        return null;
    }

    /**
     * Returns all keys in the symbol table in the given range, as an
     * {@code Iterable}.
     *
     * @param lo minimum endpoint
     * @param hi maximum endpoint
     * @return all keys in the symbol table between {@code lo} (inclusive) and
     *         {@code hi} (inclusive)
     * @throws IllegalArgumentException if either {@code lo} or {@code hi} is
     *                                  {@code null}
     */
    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo == null)
            throw new IllegalArgumentException("First Argument to the key() is empty or null");
        if (hi == null)
            throw new IllegalArgumentException("First Argument to the key() is empty or null");
        ArrayList<Key> queue = new ArrayList<Key>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node x, ArrayList<Key> queue, Key lo, Key hi) {
        if (lo == null)
            return;
        int cmplow = lo.compareTo(x.key);
        int cmphigh = hi.compareTo(x.key);
        if (cmplow < 0)
            keys(x.left, queue, lo, hi);
        if (cmplow <= 0 && cmphigh >= 0)
            queue.add(x.key);
        if (cmphigh > 0)
            keys(x.right, queue, lo, hi);
    }

    // private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {

    // }

    /*
     * Run the program by giving the approriate command obtained from input files
     * through input.txt files. The output should be displayed exactly like the file
     * output.txt shows it to be.
     */

    public static void main(String[] args) {
        Solution1<String, Integer> object = new Solution1<String, Integer>();
        object.put("ABDUL", 1);
        int a = object.get("ABDUL");
        System.out.println(a);

        object.put("HRITIK", 2);
        object.put("SAI", 3);
        object.put("SAMAL", 6);
        int b = object.get("SAI");
        System.out.println(b);

        object.put("TASHI", 4);
        int size = object.size();
        System.out.println(size);
        String min = object.min();
        System.out.println(min);
        String f1 = object.floor("HRITIK");
        System.out.println(f1);
        String f2 = object.floor("HAHA");
        System.out.println(f2);
        String s1 = object.select(2);
        System.out.println(s1);

        for (String s : object.keys("ABDUL", "TASHI"))
            System.out.println(s + " ");
        // new line may be required ,, see when you execute the codes

        object.put("CHIMI", 5);
        object.put("SAMAL", 4);
        int c = object.get("SAMAL");
        System.out.println("\n" + c);
        object.put("NIMA", 7);
        int size1 = object.size();
        System.out.println(size1);
        int d = object.get("CHIMI");
        System.out.println(d);
        String f3 = object.floor("CHIMA");
        System.out.println(f3);
        object.put("SONAM", 8);

        for (String s : object.keys("ABDUL", "TASHI"))
            System.out.print(s + " ");
        System.out.println(" ");

        System.out.println("The size is:" + size);
        // System.out.println("The maxium value is :" + max);
    }

}
