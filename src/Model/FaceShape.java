package Model;

import java.util.ArrayList;
import java.util.List;

import Helpers.Vector3f;

/**
 * In Vector space of coord system
 * @author Mark Mills
 *
 */
public class FaceShape implements ViewableModel {

	public final CoordinateSystem objCoordSystem;
	//position vector points to top left front
	private Vector3f positionVector = new Vector3f(-1, 1, 1);
	private Vector3f minVector = new Vector3f(-3, -3, -1);
	private Vector3f maxVector = new Vector3f(3, 3, 1);
	private float width, height, depth;
	private float[] deltas = {-.05f,.05f,.05f};
	private double cubeRadius;
	private int swirlPeriod;
	
	
	public FaceShape(CoordinateSystem objCoordSystem){
		this.objCoordSystem = objCoordSystem;
		this.objCoordSystem.addToViewables(this);
		this.cubeRadius = .05f;
		this.swirlPeriod = 100; //in milisec
		width = maxVector.x() - minVector.x();
		height = maxVector.y() - minVector.y();
		depth = maxVector.z() - minVector.z();
	}

	/**
	 * returns list of distance vectors from observer object origin of things to be drawn.
	 * 
	 * 		SEE Level 1 abstraction part b) -- Returns a list of green arrows for a particular 
	 * 											SwirlDroplet (viewable object)
	 * 
	 * OCS = Object Coordinate System
	 * 
	 */
	@Override
	public List<Vector3f> getAllPositionVectorsInOCS(long t) {
		List<Vector3f> allGreenArrows = new ArrayList<Vector3f>();
		Vector3f distVectInOCS;
		float x,y,z;
		double omegaT;
		//four second theta period
		omegaT = 2.0*Math.PI*t/this.swirlPeriod ;
		
		x = positionVector.x();
		y = positionVector.y();
		z = positionVector.z();
		
		distVectInOCS = new Vector3f(x, y, z); //green arrow in Level 1 part B
		allGreenArrows.add(distVectInOCS);
		return allGreenArrows;
	}
	
	public Vector3f getXYZPositionVector(){
		return positionVector;
	}

	public Vector3f getXYZMinVector(){
		return minVector;
	}
	
	public Vector3f getXYZMaxVector(){
		return maxVector;
	}
	@Override
	public CoordinateSystem getObjectCoordSystem() {
		return this.objCoordSystem;
	}


}
