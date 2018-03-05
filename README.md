# The-Fruit-Rage--An-Intelligent-Gameplaying-agent
**Rules of the game**

The Fruit Rage is a two player game in which each player tries to maximize his/her share from a batch of fruits randomly placed in a box. The box is divided into cells and each cell is either empty or filled with one fruit of a specific type. At the beginning of each game, all cells are filled with fruits. Players play in turn and can pick a cell of the box in their own turn and claim all fruit of the same type, in all cells that are connected to the selected cell through horizontal and vertical paths. For each selection or move the agent is rewarded a numeric value which is the square of the number of fruits claimed in that move. Once an agent picks the fruits from the cells, their empty place will be filled with other fruits on top of them (which will fall down due to gravity), if any. In this game, no fruit is added during gameplay. Hence, players play until all fruits have been claimed.

Another big constraint of this game is that every agent has a limited amount of time to spend for thinking during the whole game. Spending more than the original allocated time will be penalized harshly. Each player is allocated a fixed total amount of time. When it is your turn to play, you will also be told how much remaining time you have. The time you take on each move will be subtracted from your total remaining time. If your remaining time reaches zero, your agent will automatically lose the game. Hence you should think about strategies for best use of your time (spend a lot of time on early moves, or on later moves?) The overall score of each player is the sum of rewards gained for every turn. The game will terminate when there is no fruit left in the box or when a player has run out of time.





**Input:** The file input.txt in the current directory of your program will be formatted as follows:

First line: integer n, the width and height of the square board (0 &lt; n £ 26)

Second line: integer p, the number of fruit types (0 &lt; p £ 9)

Third line: strictly positive floating point number, your remaining time in seconds

Next n lines: the n x n board, with one board row per input file line, and n characters (plus end of- line marker) on each line. Each character can be either a digit from 0 to p-1, or a \* to denote an empty cell. Note: for ease of parsing, the extra horizontal and vertical lines shown in figures 1 – 5 will not be present in the actual input.txt (see below for examples).







**Output:** The file output.txt which your program creates in the current directory should be

formatted as follows:

First line: your selected move, represented as two characters:

**A letter** from A to Z representing the column number (where A is the leftmost

column, B is the next one to the right, etc), and

**A number** from 1 to 26 representing the row number (where 1 is the top row, 2 is

the row below it, etc).

Next n lines: the n x n board just after your move and after gravity has been applied to make

any fruits fall into holes created by your move taking away some fruits.



**Example 1:**

For this input.txt:

2

3

123.6

01

21

one possible correct output.txt is:

B1

0\*

2\*

(remember: B1 means second column, top row, i.e., the agent picked the top right corner cell

and got 2 fruits of type 1. It received 2^2 = 4 points for that move).

**Example 2:**

For this input.txt:

3

1

257.2

\*\*\*

\*\*\*

\*0\*

one possible correct output.txt is:

B3

\*\*\*

\*\*\*

\*\*\*

**Example 3:**

For this input.txt:

3

2

24.345

\*\*\*

\*10

000

one possible correct output.txt is:

C2

\*\*\*

\*\*\*

\*1\*

**Example 4:**

For this input.txt:

3

10

7.24

444

444

444

one possible correct output.txt is:

A1

\*\*\*

\*\*\*

\*\*\*

**Example 5:**

For this input.txt (same layout as in Figure 1):

10

4

1.276

3102322310

0121232013

3021111113

0221031132

0230011012

0323321010

2003022012

2202200021

0130000020

2200022231

one possible correct output.txt is (same move as in Figure 1, and same resulting layout as in

Figure 3):

G8

31\*\*\*\*\*\*10

010\*\*\*\*\*13

3022322\*13

0221232\*32

0221111\*12

0331031310

2020011012

2203321121

0103022120

2232222231
