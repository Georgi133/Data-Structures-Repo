package implementations;

public class TheMatrix {
    private char[][] matrix;
    private char fillChar;
    private char toBeReplaced;
    private int startRow;
    private int startCol;

    public TheMatrix(char[][] matrix, char fillChar, int startRow, int startCol) {
        this.matrix = matrix;
        this.fillChar = fillChar;
        this.startRow = startRow;
        this.startCol = startCol;
        this.toBeReplaced = this.matrix[this.startRow][this.startCol];
    }

    public void solve() {
        char startSymbol = matrix[startRow][startCol];
        recursion(startSymbol,fillChar,startRow,startCol);

    }

    private void recursion(char startSymbol,char fillChar,int startRow, int startCol) {
        if(isNotValid(startRow,startCol) || matrix[startRow][startCol] != startSymbol){
            return;
        }

        matrix[startRow][startCol] = fillChar;

        recursion(startSymbol,fillChar,startRow + 1, startCol); // D
        recursion(startSymbol,fillChar,startRow - 1, startCol); // U
        recursion(startSymbol,fillChar,startRow, startCol + 1); // R
        recursion(startSymbol,fillChar,startRow, startCol - 1); // L


    }

    private boolean isNotValid(int startRow,int startCol) {
        return startRow < 0 || startRow >= matrix.length || startCol < 0 || startCol >= matrix[startRow].length;
    }

    public String toOutputString() {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i <matrix.length ; i++) {
            for (int j = 0; j <matrix[i].length ; j++) {
                sb.append(matrix[i][j]);
            }
            sb.append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
