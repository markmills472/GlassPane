package Helpers;

public class Matrix4x4f {

	float[] w1_4 = {1,0,0,0}; 
	float[] x1_4 = {0,1,0,0};
	float[] y1_4 = {0,0,1,0};
	float[] z1_4 = {0,0,0,1};
	public float[][] matrix = new float[4][4];
	
	public void Matrix4x4f(){
		setMatrixToIdentityMatrix();
	}
	
	public void setMatrix(float aw0, float aw1, float aw2, float aw3,
			float ax0, float ax1, float ax2, float ax3,
			float ay0, float ay1, float ay2, float ay3,
			float az0, float az1, float az2, float az3){
		
		//FACT: w1_4.length == x1_4.length == y1_4.length == z1_4.length
		for(int i = 0; i < w1_4.length ; i++){
			switch(i){
			
			case 0:
				w1_4[i] = aw0;
				x1_4[i] = ax0;
				y1_4[i] = ay0;
				z1_4[i] = az0;
				break;
			case 1:
				w1_4[i] = aw1;
				x1_4[i] = ax1;
				y1_4[i] = ay1;
				z1_4[i] = az1;
				break;
			case 2:
				w1_4[i] = aw2;
				x1_4[i] = ax2;
				y1_4[i] = ay2;
				z1_4[i] = az2;
				break;
			case 3:
				w1_4[i] = aw3;
				x1_4[i] = ax3;
				y1_4[i] = ay3;
				z1_4[i] = az3;
				break;
				
			}
			
		}
		
	}
	
	
	
	public float[][] getMatrixAs2DFloatArray(){
		
		float[][] retMatrix = new float[4][4];
		
		for(int i = 0; i < matrix.length ; i++){ //column
			for(int k = 0; k < matrix[0].length ; k++){ //row
				
				float cellValue = 0;
				switch(k){
				
					case 0:
						cellValue = w1_4[i];
						break;
					case 1:
						cellValue = x1_4[i];
						break;
					case 2:
						cellValue = y1_4[i];
						break;
					case 3:
						cellValue = z1_4[i];
						break;
						
				}
						
				retMatrix[i][k] = cellValue;
			}
		}
		
		return retMatrix;
	}
	
	public void setMatrixToIdentityMatrix() {
		
		for(int i = 0; i < matrix.length ; i++){ //column
			for(int k = 0; k < matrix[0].length ; k++){ //row
				
				float cellValue = 0;
				switch(k){
				
					case 0:
						cellValue = w1_4[i];
						break;
					case 1:
						cellValue = x1_4[i];
						break;
					case 2:
						cellValue = y1_4[i];
						break;
					case 3:
						cellValue = z1_4[i];
						break;
						
				}
						
				matrix[i][k] = cellValue;
			}
		}
	}

	public float[][] getIdentityMatrix(){
		
		setMatrixToIdentityMatrix();
		
		return matrix;
		
	}
	
	
}
