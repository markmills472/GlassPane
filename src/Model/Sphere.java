package Model;

import java.util.ArrayList;
import java.util.List;

import Helpers.Vector3f;

public class Sphere implements ViewableModel {

	CoordinateSystem coordSystem = null;
	List<Vector3f> allPositionVectorsInOCS = null;
	
	private float radius;
	float[] sphereVerticies;
	int[] sphereSurfaceTriangleIndicies;
	final int numOfPointsPerRow;
	
	/**
	 * 
	 * @param width
	 * @param layers - must be even and >=4
	 */
	public Sphere(float width, int layers, int numOfPointsPerRow, CoordinateSystem coordSystem){
		
		this.coordSystem = coordSystem;
		this.coordSystem.addToViewables(this);
		this.allPositionVectorsInOCS = new ArrayList<Vector3f>();
		
		layers = layers*2;
		this.radius = width / 2.0f;
//		int totalNumSquares = (int) (Math.pow(2,layers));
		this.numOfPointsPerRow = numOfPointsPerRow;
		
		float[] sphereVerticiesHold;
		int[] sphereSurfaceTriangleIndiciesHold;
		
		sphereVerticiesHold = makeSphereVerticies(layers);
		sphereSurfaceTriangleIndiciesHold = makeSphereIndicies(layers);
		
		sphereSurfaceTriangleIndicies = sphereSurfaceTriangleIndiciesHold;
		this.sphereVerticies = sphereVerticiesHold;
	}

	private int[] makeSphereIndicies(int layers) {
	//	int numOfVerticies = (int) (Math.pow(2,layers/2+1)+2*(Math.pow(2, (layers+2))-1)/3);
		int numOfVerticies = numOfPointsPerRow*layers+2;
		int sphereArraySize = numOfVerticies; //must play nicely with 4 cause square rendering
		if(numOfVerticies%4 != 0){
			sphereArraySize = numOfVerticies + (4 - numOfVerticies%4);
		}
		int[] sphereSurfaceTriangleIndiciesHold = new int[4*sphereArraySize];
		int indicieHold=0;
		
		//Make Main Part
		for(int i = 1; i < numOfVerticies/2.0 ; i++){
			if(!(i*2+2 >= numOfVerticies)){
				sphereSurfaceTriangleIndiciesHold[indicieHold]=i*2;
				indicieHold++;
				sphereSurfaceTriangleIndiciesHold[indicieHold]=i*2+2;
				indicieHold++;
				if(!(i*2+2*numOfPointsPerRow+2 >= numOfVerticies)){
					sphereSurfaceTriangleIndiciesHold[indicieHold]=i*2+2*numOfPointsPerRow+2;
					indicieHold++;
					sphereSurfaceTriangleIndiciesHold[indicieHold]=i*2+2*numOfPointsPerRow;
					indicieHold++;
				} else {
					sphereSurfaceTriangleIndiciesHold[indicieHold]=i*2+2;
					indicieHold++;
					sphereSurfaceTriangleIndiciesHold[indicieHold]=i*2+0;
					indicieHold++;
				}
			}
		}
		for(int i = 1; i < numOfVerticies/2.0 ; i++){
			if(!(i*2+1 >= numOfVerticies)){
				sphereSurfaceTriangleIndiciesHold[indicieHold]=i*2-1;
				indicieHold++;
				sphereSurfaceTriangleIndiciesHold[indicieHold]=i*2+1;
				indicieHold++;
				if(!(i*2+2*numOfPointsPerRow+1 >= numOfVerticies)){
					sphereSurfaceTriangleIndiciesHold[indicieHold]=i*2+2*numOfPointsPerRow+1;
					indicieHold++;
					sphereSurfaceTriangleIndiciesHold[indicieHold]=i*2+2*numOfPointsPerRow-1;
					indicieHold++;
				} else {
					sphereSurfaceTriangleIndiciesHold[indicieHold]=i*2+1;
					indicieHold++;
					sphereSurfaceTriangleIndiciesHold[indicieHold]=i*2-1;
					indicieHold++;
				}
			}
		}
		
		//Make Top and Bottom
		
		//if wasn't divisible by 4, then last square rendered must drawn on same point
		if(numOfVerticies != sphereArraySize){
			for(int i = 0 ; i < 4; i ++){
				int index = numOfVerticies/4*4;
				sphereSurfaceTriangleIndiciesHold[index+i]=sphereSurfaceTriangleIndiciesHold[index];
				indicieHold++;
			}
		}
		return sphereSurfaceTriangleIndiciesHold;
	}

	private float[] makeSphereVerticies(int layers) {
		
	//	int numOfVerticies = (int) (Math.pow(2,layers/2+1)+2*(Math.pow(2, (layers+2))-1)/3);
		int numOfVerticies = numOfPointsPerRow*layers+2;
		float[] sphereVerticiesHold = new float[3*numOfVerticies];
		int indicieHold = 0;
		int numVerticiesInRow=16;
		for(int i = 0; i < layers/2; i++){
			if(i==0)
				numVerticiesInRow = 1;
			else
				numVerticiesInRow = numOfPointsPerRow;
//			int numVerticiesInRow = (int) Math.pow(2, 2*i);
			double phi = i*Math.PI/layers;
			for(int k = 0; k < numVerticiesInRow ; k++){
				double theta = k*2*Math.PI/numVerticiesInRow;
				
				//Top
				float x = (float) (this.radius*Math.sin(phi)*Math.cos(theta));
				sphereVerticiesHold[indicieHold] = x;
				indicieHold++;
				
				float y = (float) (this.radius*Math.cos(phi));
				sphereVerticiesHold[indicieHold] = y;
				indicieHold++;
				
				float z = (float) (this.radius*Math.sin(phi)*Math.sin(theta));
				sphereVerticiesHold[indicieHold] = z;
				indicieHold++;
				
				//Bottom
				x = (float) (this.radius*Math.sin(Math.PI-phi)*Math.cos(theta));
				sphereVerticiesHold[indicieHold] = x;
				indicieHold++;
				
				y = (float) (this.radius*Math.cos(Math.PI-phi));
				sphereVerticiesHold[indicieHold] = y;
				indicieHold++;
				
				z = (float) (this.radius*Math.sin(Math.PI-phi)*Math.sin(theta));
				sphereVerticiesHold[indicieHold] = z;
				indicieHold++;
			}
		}		
		
		//middle strip
//		int numVerticiesInRow = (int) Math.pow(2, layers);
//		double theta = 0;
		double phi = Math.PI/2;
		for(int k = 0; k < numVerticiesInRow ; k++){
			double thetaHold = k*2*Math.PI/numVerticiesInRow;
			double phiHold = phi;
		
			float x = (float) (this.radius*Math.sin(phiHold)*Math.cos(thetaHold));
			sphereVerticiesHold[indicieHold] = x;
			indicieHold++;
			
			float y = (float) (this.radius*Math.cos(phiHold));
			sphereVerticiesHold[indicieHold] = y;
			indicieHold++;
			
			float z = (float) (this.radius*Math.sin(phiHold)*Math.sin(thetaHold));
			sphereVerticiesHold[indicieHold] = z;
			indicieHold++;
			
			x = (float) (this.radius*Math.sin(phiHold)*Math.cos(thetaHold));
			sphereVerticiesHold[indicieHold] = x;
			indicieHold++;
			
			y = (float) (this.radius*Math.cos(phiHold));
			sphereVerticiesHold[indicieHold] = y;
			indicieHold++;
			
			z = (float) (this.radius*Math.sin(phiHold)*Math.sin(thetaHold));
			sphereVerticiesHold[indicieHold] = z;
			indicieHold++;
		}
		return sphereVerticiesHold;
	}
	
/*
	@Override
	public float[] getVertexLocations() {
		return this.sphereVerticies;
	}

	@Override
	public int[] getVertexIndicies() {
		return this.sphereSurfaceTriangleIndicies;
	}
	*/

	@Override
	public CoordinateSystem getObjectCoordSystem() {
		return this.coordSystem;
	}

	@Override
	public List<Vector3f> getAllPositionVectorsInOCS(long time) {
		
		List<Vector3f> indexedPositionsToVectorInOCS = new ArrayList<Vector3f>();
		
		for(int i = 0; i < sphereSurfaceTriangleIndicies.length - 2; i+=3){
			
			Vector3f coordSystemLocation = coordSystem.getDistanceVectorFromOrg(time);
			
			float x = sphereVerticies[sphereSurfaceTriangleIndicies[i]];
			float y = sphereVerticies[sphereSurfaceTriangleIndicies[i+1]];
			float z = sphereVerticies[sphereSurfaceTriangleIndicies[i+2]];
			
			Vector3f locationAdjustedToCoordSystem = new Vector3f(
					coordSystemLocation.x() + x,
					coordSystemLocation.y() + y,
					coordSystemLocation.z() + z);
			
			indexedPositionsToVectorInOCS.add(i/3, locationAdjustedToCoordSystem);
		}
		
		this.allPositionVectorsInOCS = indexedPositionsToVectorInOCS;
		
		return this.allPositionVectorsInOCS;
	}

	public float getSphereRadius() {
		return this.radius;
	}
}
