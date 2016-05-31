package Model;

import java.util.ArrayList;
import java.util.List;

import Helpers.Vector3f;

public class Cube implements ViewableModel {

	CoordinateSystem coordSystem = null;
	List<Vector3f> allPositionVectorsInOCS = null;
	
	private float radius;
	float[] cubeVerticies;
//	int[] cubeSurfaceTriangleIndicies = {
//			4 , 5 , 6 , 7,	//back	
//			5 , 4 , 1 , 0,	//bottom
//			1 , 2 , 7 , 4,	//right 
//			5 , 0 , 3 , 6,	//left
//			3 , 2 , 7 , 6,	//top
//			0 , 1 , 2 , 3	//Front
//	};
	int[] cubeSurfaceTriangleIndicies = {
			0,1,2,3,4,5,6,7
	};
	
	public Cube(float width, CoordinateSystem coordSystem){
		
		this.coordSystem = coordSystem;
		this.coordSystem.addToViewables(this);
		this.allPositionVectorsInOCS = new ArrayList<Vector3f>();
		
		this.radius = width / 2.0f;
		
		float[] cubeVerticiesHold = {
				-radius, -radius, radius,		//0
				radius, -radius, radius,		//1
				radius, radius, radius,		//2
				-radius, radius, radius,		//3
				
				radius, -radius, -radius,	//4
				-radius, -radius, -radius,		//5
				-radius, radius, -radius,		//6
				radius, radius, -radius,		//7
		};
		
		this.cubeVerticies = cubeVerticiesHold;
	}
/*
	@Override
	public float[] getVertexLocations() {
		return this.cubeVerticies;
	}

	@Override
	public int[] getVertexIndicies() {
		return this.cubeSurfaceTriangleIndicies;
	}

	*/
	@Override
	public CoordinateSystem getObjectCoordSystem() {
		return this.coordSystem;
	}
	

	@Override
	public List<Vector3f> getAllPositionVectorsInOCS(long t) {
		
		for(int i = 0; i < this.cubeSurfaceTriangleIndicies.length ; i++){
			
			Vector3f distVector = new Vector3f(
					this.cubeVerticies[cubeSurfaceTriangleIndicies[i]],
					this.cubeVerticies[cubeSurfaceTriangleIndicies[i]+1],
					this.cubeVerticies[cubeSurfaceTriangleIndicies[i]+2]);
			
			this.allPositionVectorsInOCS.add(distVector);
		}
		
		return this.allPositionVectorsInOCS;
	}
	
}
