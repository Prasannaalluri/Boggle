import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Random;
import java.util.TreeSet;
import java.applet.Applet;

public final class Boggle extends Applet {

    private static final NavigableSet<String> dictionary;
    private final Map<Character, List<Character>> graph = new HashMap<Character, List<Character>>();


    static {
        dictionary = new TreeSet<String>();
        try {
            FileReader fr = new FileReader("words");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                dictionary.add(line.split(":")[0]);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while reading dictionary");
        }
    }

    private Boggle() {}

    public static List<String> boggleSolver(char[][] m) {
        if (m == null) {
            throw new NullPointerException("The matrix cannot be null");
        }
        final List<String> validWords = new ArrayList<String>();
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                solve(m, i, j, m[i][j] + "", validWords);
            }
        }
        return validWords;
    }

    private static void solve(char[][] m, int i, int j, String prefix, List<String> validWords) {
        assert m != null;
        assert validWords != null;

        for (int i1 = Math.max(0, i - 1); i1 < Math.min(m.length, i + 2); i1++) {
            for (int j1 = Math.max(0, j - 1); j1 < Math.min(m[0].length, j + 2); j1++) {
                if (i1 != i || j1 != j) {
                    String word = prefix+ m[i1][j1];

                    if (dictionary.contains(word)) {
                        validWords.add(word);
                    }

                    if (dictionary.subSet(word, word + Character.MAX_VALUE).size() > 0) {
                        solve(m, i1, j1, word, validWords);
                    }
                }
            }
        }
    }

    public static void main (String[] args) throws IOException {
        Random r = new Random();
        char[][] board = new char [4][4];
        for(int i = 0; i < 4; i++) {
            for(int j= 0; j < 4; j++) {
                char c = (char)(r.nextInt(26) + 'a');
                board[i][j] = c;
            }
        }       

        for (int row = 0; row < 4; row++)
        {
            for (int col = 0; col < 4; col++)
            {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
        List<String> list = Boggle.boggleSolver(board);
        for (String str :  list) {
            if(str.length() > 2)
                System.out.println(str);
        }
    }
}
