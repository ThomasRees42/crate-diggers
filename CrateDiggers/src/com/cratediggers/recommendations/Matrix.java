package com.cratediggers.recommendations;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Matrix {

	private double[][] matrix;
	
	@SuppressWarnings("unused")
	private Matrix() {}
	
	public Matrix(int m, int n) {
		matrix = new double[m][n];
	}
	
	public Matrix(double[][] arr) {
		matrix = arr;
	}
	
	public static Matrix Random(int m, int n, int min, int max) {
		double random;
		Matrix matrix = new Matrix(m, n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				random = (ThreadLocalRandom.current().nextDouble() * (max - min)) + min;
				matrix.set(random, i, j);
			}
		}
		return matrix;
	}
	
	public void set(double num, int row, int col) {
		matrix[row][col] = num;
	}
	
	public double get(int row, int col) {
		return matrix[row][col];
	}
	
	public double[][] get() {
		return matrix;
	}
	
	public double[] getRow(int rowNum) {
		double[] row = new double[this.getColumnNumber()];
		for (int col = 0; col < this.getColumnNumber(); col++) {
			row[col] = this.get(rowNum, col);
		}
		return row;
	}
	
	public double[] getColumn(int colNum) {
		double[] col = new double[this.getRowNumber()];
		for (int row = 0; row < this.getRowNumber(); row++) {
			col[row] = this.get(colNum, row);
		}
		return col;
	}
	
	public Matrix getTranspose() {
		Matrix transpose = new Matrix(this.getColumnNumber(), this.getRowNumber());
		for (int i = 0; i < this.getRowNumber(); i++) {
			for (int j = 0; j < this.getColumnNumber(); j++) {
				transpose.set(this.get(i, j), j, i);
			}
		}
		return transpose;
	}
	
	public int getColumnNumber() {
		return matrix[0].length;
	}
	
	public int getRowNumber() {
		return matrix.length;
	}
	
	public Matrix getSubMatrix(int rowStart, int rowEnd, int colStart, int colEnd) {
		Matrix subMatrix = new Matrix(rowEnd - rowStart, colEnd - colStart);
		for (int i = rowStart; i < rowEnd; i++) {
			for (int j = colStart; j < colEnd; j++) {
				try {
					subMatrix.set(this.get(i, j), i - rowStart, j - colStart);
				} catch (ArrayIndexOutOfBoundsException e) {
					throw new MatrixException("Submatrix must be within the bounds of the matrix.", e);
				}
			}
		}
		return subMatrix;
	}
	
	public Matrix dotProduct(Matrix matrixB) {
		//check row and column lengths
		if (this.getColumnNumber() != matrixB.getRowNumber()) {
			throw new MatrixException("To perform dot product multiplication on two matrices, " +
					"the number of columns of matrix A must be equivalent to the number of rows " +
					"of matrix B.");
		}
		Matrix matrixC = new Matrix(this.getRowNumber(), matrixB.getColumnNumber());
		double total;
		for (int i = 0; i < this.getRowNumber(); i++) {
			for (int j = 0; j < matrixB.getColumnNumber(); j++) {
				total = 0;
				for (int k = 0; k < this.getColumnNumber(); k++) {
					total += this.get(i, k) * matrixB.get(k, j);
				}
				matrixC.set(total, i, j);
			}
		}
		return matrixC;
	}
	
	public String print() {
		String str = "";
		for (int i = 0; i < this.getRowNumber(); i++) {
			for (int j = 0; j < this.getColumnNumber(); j++) {
				str.concat(Double.toString(this.get(i, j))).concat(" ");
				System.out.print(Double.toString(this.get(i, j)) + ", ");
			}
			str.concat("\n");
			System.out.print("\n");
		}
		return str;
	}
	
	public Map<String, Matrix> factorise(int kDimension, int maxSteps, double alpha, double beta, double threshold, int randMin, int randMax) {
		Matrix factorA = Matrix.Random(this.getRowNumber(), kDimension, randMin, randMax);
		Matrix factorB = Matrix.Random(this.getColumnNumber(), kDimension, randMin, randMax).getTranspose();
		Matrix subMatrixA;
		Matrix subMatrixB;
		double error;
		double totalError;
		double updateValA;
		double updateValB;
		Map<String, Matrix> factors = new HashMap<String, Matrix>();
		
		for (int step = 0; step < maxSteps; step++) {
			for (int i = 0; i < this.getRowNumber(); i++) {
				for (int j = 0; j < this.getColumnNumber(); j++) {
					if (this.get(i, j) > 0) {
						subMatrixA = factorA.getSubMatrix(i, i + 1, 0, factorA.getColumnNumber());
						subMatrixB = factorB.getSubMatrix(0, factorB.getRowNumber(), j, j + 1);
						error = this.get(i, j) - subMatrixA.dotProduct(subMatrixB).get(0, 0);
						for (int k = 0; k < kDimension; k++) {
							updateValA = factorA.get(i, k) + alpha * (2 * error * factorB.get(k, j) - beta * factorA.get(i, k));
							updateValB = factorB.get(k, j) + alpha * (2 * error * factorA.get(i, k) - beta * factorB.get(k, j));
							factorA.set(updateValA, i, k);
							factorB.set(updateValB, k, j);
						}
					}
				}
			}
			totalError = 0;
			for (int i = 0; i < this.getRowNumber(); i++) {
				for (int j = 0; j < this.getColumnNumber(); j++) {
					if (this.get(i, j) > 0) {
						subMatrixA = factorA.getSubMatrix(i, i + 1, 0, factorA.getColumnNumber());
						subMatrixB = factorB.getSubMatrix(0, factorB.getRowNumber(), j, j + 1);
						totalError += Math.pow(this.get(i, j) - subMatrixA.dotProduct(subMatrixB).get(0, 0), 2);
						for (int k = 0; k < kDimension; k++) {
							totalError += (beta / 2) * (Math.pow(factorA.get(i, k), 2) + Math.pow(factorB.get(k, j), 2));
						}
					}
				}
			}
			
			if (totalError < threshold) {
				break;
			}
		}
		
		factors.put("A", factorA);
		factors.put("B", factorB.getTranspose());
		return factors;
	}


}
