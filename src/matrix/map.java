package matrix;

public final class Map {
    private char[][] array;

    public Map(final int n, final int m) {
        array = new char[n][m];
    }

    public char[][] getArray() {
        return array;
    }

    public char getArray(final int x, final int y) {
        return array[x][y];
    }

    public void setArray(final char t, final int i, final int j) {
        array[i][j] = t;
    }

    private static Map instance = null;

    public static Map getInstance(final int n, final int m) {
        if (instance == null) {
            instance = new Map(n, m);
        }
        return instance;
    }

    public static Map getInstance() {
        return instance;
    }


}
