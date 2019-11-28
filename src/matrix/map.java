package matrix;

public final class map {
    private char[][] array;

    public map(final int n,final int m){
        array = new char[n][m];
    }
    public char[][] getArray() {
        return array;
    }
    public char getArray(int x, int y) {
        return array[x][y];
    }

    public void setArray(char t, int i, int j) {
        array[i][j] = t;
    }

    private static map instance = null;

    public static map getInstance(int n, int m) {
        if (instance == null) {
            instance = new map(n,m);
        }
        return instance;
    }
    public static map getInstance() {
        return instance;
    }


}
