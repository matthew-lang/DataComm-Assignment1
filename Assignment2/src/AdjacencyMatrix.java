import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class AdjacencyMatrix {
	private int[][] matrix;
	public AdjacencyMatrix(boolean bidirectional, int[][] matrix) {
		this.matrix = matrix;
		validateMatrix(bidirectional);
	}
	public AdjacencyMatrix(boolean bidirectional, String filename) throws FileNotFoundException, NumberFormatException {
		System.out.println("Reading matrix from file...");
		File f = new File(filename);
		Scanner sc = new Scanner(f);
		int y=0, x=0;
		while(sc.hasNextLine()) {
			
			String line = sc.nextLine();
			String[] tokens = line.split(":|;|,| ");
			if(matrix == null) {
				matrix = new int[tokens.length][tokens.length];
			}
			for(String token : tokens) {
				matrix[y][x] = Integer.parseInt(token);
				x++;
			}
			y++;
			x=0;
		}
		sc.close();
		validateMatrix(bidirectional);
	}
	private void validateMatrix(boolean bidirectional) {
		int length = matrix.length;
		System.out.println("Matrix: ");
		for(int y = 0;y<matrix.length;y++) {
			if(matrix[y].length != length) {
				System.out.println();
				throw new IllegalArgumentException("2d array is not square!");
			}
			for(int x = 0;x<matrix[y].length;x++) {
				System.out.print(matrix[y][x] + " ");
				if(matrix[y][x] < 0) {
					System.out.println();
					throw new IllegalArgumentException("weight must be non-negative");
				}
				if(!bidirectional && matrix[y][x] != matrix[x][y]) {
					System.out.println();
					throw new IllegalArgumentException("Bidirectional matrix must have symmetry on the line x=y");
				}
			}
			System.out.println();
		}
	}
	//c(x,y)
	public int cost(int from, int to){
		return matrix[from][to];
	}
	public int nodeCount() {
		return matrix.length;
	}
}
