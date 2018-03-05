/**
 * The Fruit Rage! -An Intelligent Gameplaying agent
 *
 *
  A game that captures the nature of a zero-sum two player game with strict limitation on allocated time for
 reasoning.

  Developed the game playing agent using Alpha–beta pruning algorithm .
 */






import java.io.*;
import java.util.*;
import java.util.Map.Entry;


class Board {
    int n;                          //Size of the Board
    int fruits;                     //Number of types of fruits
    float time;                     //Time remaining
    char board[][];
    char comp[][];
    char step[][];
    Map<String, Integer> sorted;
    int amn[];
    int depth = 2;                   //Depth of the MiniMax tree Changes dynamically with timer-emaining and board Size.
    int islands = 0;
    int islandCount;
    int score;



    /**
     * Returns the Depth of the MiniMax Tree
     */
    void getDepth() {
        getIslandMap();
        int i = 0;


        double tat = (double) time / (double) islandCount;


        if (time > 10)
            if (islandCount < 16) {
                if (tat < 0.5) {
                    depth = 2;
                } else if (tat < 1) {
                    depth = 3;
                } else if (tat > 1) {
                    depth = 9;
                }
            } else if (islandCount < 25) {
                if (tat < 0.5) {
                    depth = 2;
                } else if (tat < 1) {
                    depth = 3;
                } else if (tat > 1) {
                    depth = 7;
                }
            } else if (islandCount < 49) {
                if (tat < 0.5) {
                    depth = 2;
                } else if (tat < 1) {
                    depth = 3;
                } else if (tat > 1) {
                    depth = 4;
                } else if (tat > 8) {
                    depth = 5;
                }
            } else if (tat < 144) {
                if (tat < 1) {
                    depth = 2;
                } else if (tat > 1) {
                    depth = 3;
                } else if (tat > 4) {
                    depth = 4;
                }
            } else if (islandCount < 256) {
                if (tat < 1) {
                    depth = 2;
                } else if (tat > 1) {
                    depth = 3;
                }
            } else if (islandCount < 324) {
                depth = 2;
            } else {
                if (tat < 0.5) {
                    depth = 0;
                } else if (tat > 1) {
                    depth = 2;
                }
            }

        if (time < 10) {
            if (time > 4)
                if (islandCount < 49) {
                    depth = 3;
                } else {
                    depth = 0;
                }
            if (time < 4) {
                depth = 0;
            }

        }


    }


    /**
     * starts the execution of the MiniMax algorithm
     */
    void kickStart() {
        amn = maxValue(depth, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0);
        score = nextmove(amn[1], amn[2], step);
    }


    Board() {
        input();            //call the input fucntion which reads the input.txt

    }

    /**
     * Printing the Board to the console
     * @param board board
     */
    void boardPrint(char board[][]) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(board[i][j]);

            }
            System.out.println();
        }
        System.out.println();
    }

    void copy(char a[][], char b[][]) {
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                a[i][j] = b[i][j];
    }



    /**
     *
     * @return  Returns the Size of all the islands in the decreasing order and the starting index
     */
    Map<String, Integer> getIslandMap() {
        int i, j;
        Map<String, Integer> islandScoreMap = new HashMap<>();
        int score;
        copy(comp, step);
        for (i = 0; i < n; ++i) {
            for (j = 0; j < n; ++j) {
                if (comp[i][j] != '*') {
                    score = getIslandSize(i, j, 0, comp);
                    islandScoreMap.put(i + "." + j, score);
                }
            }
        }
        sorted = sortByComparator(islandScoreMap, false);
        if (islands == 0) {
            islands = 1;
            islandCount = islandScoreMap.size();
        }

        return sorted;
    }



    /**
     *
     * returns the size of the island consisting of claimed Fruits
     *
     * @param i     corresponds to row in board
     * @param j     corresponds to column in board
     * @param cost  initially set to zero
     * @param board1    Board
     * @return  returns the Size of the Island
     */
    int getIslandSize(int i, int j, int cost, char[][] board1) {
        if (board1[i][j] == '*')
            return cost;
        char temp = board1[i][j];
        board1[i][j] = '*';
        cost++;
        if ((i + 1 < n) && temp == board1[i + 1][j]) {
            cost = getIslandSize(i + 1, j, cost, board1);
        }
        if ((i - 1 >= 0) && temp == board1[i - 1][j]) {
            cost = getIslandSize(i - 1, j, cost, board1);
        }
        if ((j + 1 < n) && temp == board1[i][j + 1]) {
            cost = getIslandSize(i, j + 1, cost, board1);
        }
        if ((j - 1 >= 0) && temp == board1[i][j - 1]) {
            cost = getIslandSize(i, j - 1, cost, board1);
        }
        return cost;
    }


    /**
     *
     * @param unsortMap  Takes the unsorted Map
     * @param order      either True or False
     * @return             returns sorted Map based on the value
     */
    private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order) {

        List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());

        Collections.sort(list, new Comparator<Entry<String, Integer>>() {
            public int compare(Entry<String, Integer> o1,
                               Entry<String, Integer> o2) {
                if (order) {
                    return o1.getValue().compareTo(o2.getValue());
                } else {
                    if (o2.getValue() != o1.getValue())
                        return o2.getValue().compareTo(o1.getValue());
                    else
                        return o1.getKey().compareTo(o2.getKey());


                }
            }
        });


        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }


    /**/

    /**
     *Returns the count of fruits claimed when picking the particular cell in the board and changes the board Globally
     *
     *
     * @param r     row
     * @param c     column
     * @param step1   input board
     * @return the score
     */
    int nextmove(int r, int c, char[][] step1) {

        int count = 0;
        char a[][] = new char[n][n];
        copy(a, step1);
        char f = a[r][c];
        ArrayList<String> pair = new ArrayList();
        pair.add(r + "." + c);
        String s;

        while (true) {
            if (pair.size() == 0) {

                break;

            } else {
                s = pair.get(0);
                pair.remove(0);
                String split[] = s.split("\\.");
                int r1 = Integer.parseInt(split[0]);
                int c1 = Integer.parseInt(split[1]);

                if (a[r1][c1] == f) {
                    a[r1][c1] = '*';
                    count++;


                    if (r1 == 0 && c1 == 0) {
                        pair.add("0.1");
                        pair.add("1.0");

                    }
                    if (r1 == 0 && c1 == n - 1) {
                        if (n - 2 >= 0) {

                            pair.add("0." + (n - 2));
                        }
                        pair.add("1." + (n - 1));

                    }

                    if (r1 == n - 1 && c1 == 0) {

                        if (n - 2 >= 0) pair.add((n - 2) + ".0");
                        pair.add((n - 1) + ".1");
                    }


                    if (r1 == n - 1 && c1 == n - 1) {

                        if (n - 2 >= 0) {
                            pair.add((n - 1) + "." + (n - 2));
                            pair.add((n - 2) + "." + (n - 1));
                        }
                    }

                    if (r1 == 0 && c1 != 0 && c1 != n - 1) {
                        if (c1 - 1 >= 0) pair.add("0." + (c1 - 1));
                        if (c1 + 1 <= n - 1) pair.add("0." + (c1 + 1));
                        pair.add("1." + c1);
                    }

                    if (r1 == n - 1 && c1 != 0 && c1 != n - 1) {
                        if (c1 - 1 >= 0) pair.add((n - 1) + "." + (c1 - 1));
                        if (c1 + 1 <= n - 1) pair.add((n - 1) + "." + (c1 + 1));
                        if (n - 2 >= 0) pair.add((n - 2) + "." + c1);
                    }

                    if (c1 == 0 && r1 != 0 && r1 != n - 1) {
                        if (r1 - 1 >= 0) pair.add((r1 - 1) + ".0");
                        if (r1 + 1 <= n - 1) pair.add((r1 + 1) + ".0");
                        pair.add(r1 + ".1");
                    }

                    if (c1 == n - 1 && r1 != 0 && r1 != n - 1) {
                        if (r1 - 1 >= 0) pair.add((r1 - 1) + "." + (n - 1));
                        if (r1 + 1 <= n - 1) pair.add((r1 + 1) + "." + (n - 1));
                        if (n - 2 >= 0) pair.add(r1 + "." + (n - 2));
                    }

                    if (r1 > 0 && r1 < n - 1 && c1 > 0 && c1 < n - 1) {
                        pair.add((r1 - 1) + "." + (c1));
                        pair.add((r1 + 1) + "." + (c1));
                        pair.add((r1) + "." + (c1 - 1));
                        pair.add((r1) + "." + (c1 + 1));
                    }


                }


            }
        }


        for (int i = 0; i < n; i++) {
            int ctp = 0;

            for (int j = 0; j < n; j++) {
                if (a[j][i] != '*' && ctp == 0) {
                    ctp = 1;

                }
                if (ctp == 1 && a[j][i] == '*') {

                    int t = j;
                    while (t > 0) {
                        if (t != 0)
                            a[t][i] = a[t - 1][i];
                        t--;

                    }
                    a[0][i] = '*';

                }


            }
        }

        copy(step, a);
        return count;
    }



    /**
     *Reads the input.txt
     */
    void input() {
        try {

            File f = new File("input.txt");
            FileInputStream fin = new FileInputStream(f);
            BufferedReader br = new BufferedReader(new InputStreamReader(fin));
            String line = null;
            Scanner s = new Scanner(br);
            n = Integer.parseInt(br.readLine());
            board = new char[n][n];
            comp = new char[n][n];
            step = new char[n][n];


            fruits = Integer.parseInt(br.readLine());
            time = Float.parseFloat(br.readLine());

            for (int i = 0; i < n; i++) {
                line = null;
                line = s.next();

                for (int j = 0; j < n; j++) {

                    board[i][j] = line.charAt(j);
                    comp[i][j] = line.charAt(j);
                    step[i][j] = line.charAt(j);
                }
            }
            fin.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }


    //Function for Max-player

    /**
     *
     * @param depth the depth of the current node
     * @param alpha alpha value
     * @param beta     beta value
     * @param x     Score of Max player
     * @param y      score of Min player
     * @return
     */
    int[] maxValue(int depth, int alpha, int beta, int x, int y) {

        Map<String, Integer> sor = getIslandMap();
        ArrayList<String> a = new ArrayList<>(sor.keySet());

        int let = a.size();
        int v = Integer.MIN_VALUE;
        char abc[][] = new char[n][n];
        char abc1[][] = new char[n][n];
        int X = x;
        int Y = y;
        int brow = -1, bcol = -1;
        if (a.size() == 0 || depth == 0) {
            int score = x - y;
            return new int[]{score, brow, bcol};

        }
        for (String stm : a) {
            copy(abc1, step);
            X = x;


            String split[] = stm.split("\\.");
            int t = nextmove(Integer.parseInt(split[0]), Integer.parseInt(split[1]), step);
            X += Math.pow(t, 2);
            int tm = minValue(depth - 1, alpha, beta, X, Y)[0];
            if (alpha < tm) {               //alpha-beta pruning
                alpha = tm;

                brow = Integer.parseInt(split[0]);
                bcol = Integer.parseInt(split[1]);

            }
            if (alpha >= beta) {

                return new int[]{beta, brow, bcol};
            }


            copy(step, abc1);

        }


        return new int[]{alpha, brow, bcol};
    }

    //Function for Min-player

    /**
     *
     * @param depth     depth of the current node
     * @param alpha     alpha value
     * @param beta     beta value
     * @param x     Score of Max player
     * @param y      score of Min player
     * @return
     */
    int[] minValue(int depth, int alpha, int beta, int x, int y) {

        Map<String, Integer> sor = getIslandMap();
        ArrayList<String> a = new ArrayList<>(sor.keySet());

        int let = a.size();

        char abc[][] = new char[n][n];
        char abc1[][] = new char[n][n];
        int X = x;
        int Y = y;
        int brow = -1, bcol = -1;
        if (a.size() == 0 || depth == 0) {
            int score = x - y;
            return new int[]{score, brow, bcol};

        }
        for (String stm : a) {
            Y = y;
            copy(abc1, step);

            String split[] = stm.split("\\.");
            int t = nextmove(Integer.parseInt(split[0]), Integer.parseInt(split[1]), step);
            Y += Math.pow(t, 2);
            int tm = maxValue(depth - 1, alpha, beta, X, Y)[0];


            if (tm < beta) {                //alpha-beta pruning
                beta = tm;

                brow = Integer.parseInt(split[0]);
                bcol = Integer.parseInt(split[1]);

            }

            if (alpha >= beta) {
                return new int[]{alpha, brow, bcol};
            }

            copy(step, abc1);

        }
        return new int[]{beta, brow, bcol};
    }

}


public class Main {
    public static void main(String[] args) {
        double start = System.currentTimeMillis();
        Board b = new Board();
        String s = "";

        if (b.n == 1) {
            s = "A1";
            b.step[0][0] = '*';

        } else {

            b.getDepth();
            int score;
            if (b.depth == 0) {
                ArrayList<String> a = new ArrayList<>(b.sorted.keySet());
                String bd[] = a.get(0).split("\\.");
                score = b.nextmove(Integer.parseInt(bd[0]), Integer.parseInt(bd[1]), b.step);
                s += (char) (Integer.parseInt(bd[1]) + 65);
                s += (Integer.parseInt(bd[0]) + 1);
            } else {
                b.kickStart();
                s += (char) (b.amn[2] + 65);

                s += b.amn[1] + 1;
                score = b.score;

            }
        }


        double end = System.currentTimeMillis();
        double TotalTime = end - start;
        try {
            PrintWriter pw = new PrintWriter("output.txt", "UTF-8");
            pw.println(s);

            for (int i = 0; i < b.n; i++) {
                for (int j = 0; j < b.n; j++) {

                    pw.print(b.step[i][j]);
                }
                pw.println();
            }
            pw.close();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println(e);

        }

    }
}
