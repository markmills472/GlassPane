package Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import Helpers.Vector3f;

public class SwirlDroplet implements ViewableModel {

	public final CoordinateSystem objCoordSystem;
	private double[] dropletThetaPhasesOCS;
	private int numberDroplets;
	private double swirlRadius; //in meters
	private double sphereRadius;
//	private double swirlPeriod = 1.0/(2.0*Math.PI/(1024.0));
	private float zOffset = 150;
	private boolean zIncreasing;
	
	public SwirlDroplet(CoordinateSystem objCoordSystem){
		this.objCoordSystem = objCoordSystem;
		this.objCoordSystem.addToViewables(this);
		this.numberDroplets = 4;
		this.dropletThetaPhasesOCS = new double[numberDroplets];
		this.swirlRadius = 0.5f;
		this.sphereRadius = .4f;
		for(int i = 0; i < numberDroplets; i++){
			double thetaPhase = ((double)i)*2.0 * Math.PI / ((double)numberDroplets);
		//	this.dropletThetaPhasesOCS[i] = new Vector3f((float)(swirlRadius* Math.sin(theta)), 0, (float) (swirlRadius*Math.cos(theta)));
			this.dropletThetaPhasesOCS[i] = thetaPhase;
		}
	}
	
	public SwirlDroplet(CoordinateSystem objCoordSystem,Vector3f distFromOrigMCS, int numberDroplets,int swirlRadius, float cubeRadius){
		this.sphereRadius = cubeRadius;
		this.objCoordSystem = objCoordSystem;
		this.objCoordSystem.addToViewables(this);
		this.numberDroplets = numberDroplets;
		this.dropletThetaPhasesOCS = new double[numberDroplets];
		this.swirlRadius = swirlRadius;
		for(int i = 0; i < numberDroplets; i++){
			double thetaPhase = ((double)i)*2.0 * Math.PI / ((double)numberDroplets);
			this.dropletThetaPhasesOCS[i] = thetaPhase;
		}
	}
	
	@Override
	public CoordinateSystem getObjectCoordSystem() {
		
		if(zOffset >= 200 && zIncreasing ){
			zOffset -= (float) 25 * Math.abs( Math.sin( (2.0*Math.PI/(256.0)) * System.currentTimeMillis() + 0) );
			zIncreasing = false;
		} else if(zOffset <= 100 && !zIncreasing ){
			zOffset += (float) 25 * Math.abs( Math.sin( (2.0*Math.PI/(256.0)) * System.currentTimeMillis() + 0) );
			zIncreasing = true;
		}
		
		return this.objCoordSystem;
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
		
		List<Vector3f> sp;
		double omegaT = (t)*2*Math.PI/(1.0 * 1.0);
		this.swirlRadius = (omegaT % 10);
//		this.sphereRadius = (omegaT % 1.5f);
		sp = getSwirlPattern1(omegaT);
		sp.addAll(getSwirlPattern2(omegaT));
		sp.addAll(getSwirlPattern3(omegaT));
		sp.addAll(getSwirlPattern4(omegaT));
		return sp;
	}

	private List<Vector3f> getSwirlPattern1(double omegaT) {
		
		List<Vector3f> allGreenArrows = new ArrayList<Vector3f>();
		Vector3f distVectInOCS = new Vector3f();
		int numSections = 4;
		for ( float theta = 0 ; theta < (float) (2.0f * Math.PI) ; theta += (float) (2.0f * Math.PI / numSections)) {
			for ( float phi = 0 ; phi < (float) (Math.PI) ; phi += (float) (2.0f * Math.PI / numSections)) {
				for(int i = 0 ; i < this.numberDroplets; i++){
					float x,y,z;
					x = (float) ( 2.0f * swirlRadius*Math.sin(theta + 1.0f*omegaT+dropletThetaPhasesOCS[i]/8.0f) *
							swirlRadius * Math.cos(phi + omegaT+dropletThetaPhasesOCS[i]/2.0f) );
					y = (float) ( 2.0f * swirlRadius*Math.sin(theta + 1.0f*omegaT+dropletThetaPhasesOCS[i]/8.0f) *
							swirlRadius * Math.sin(phi + omegaT+dropletThetaPhasesOCS[i]/2.0f) );
					z = (float) ( 2.0f * swirlRadius*Math.cos(theta + 1.0f*omegaT+dropletThetaPhasesOCS[i]/8.0f) +
							zOffset);
					distVectInOCS = new Vector3f(x, y, z); //green arrow in Level 1 part B
					allGreenArrows.add(distVectInOCS);
				}
			}
		}
		return allGreenArrows;
	}
	
	private List<Vector3f> getSwirlPattern2(double omegaT) {
		List<Vector3f> allGreenArrows = new ArrayList<Vector3f>();
		Vector3f distVectInOCS = new Vector3f();
		int numSections = 8;
		for ( float theta = 0 ; theta < (float) (2.0f * Math.PI) ; theta += (float) (2.0f * Math.PI / numSections)) {
			for ( float phi = 0 ; phi < (float) (Math.PI) ; phi += (float) (2.0f * Math.PI / numSections)) {
				for(int i = 0 ; i < this.numberDroplets; i++){
					float x,y,z;
					x = (float) ( 2.0f * swirlRadius*Math.sin(theta + omegaT+dropletThetaPhasesOCS[i]/4.0f) *
							swirlRadius*Math.sin(phi + omegaT+dropletThetaPhasesOCS[i]/2.0f) );
					y = (float) ( 2.0f * swirlRadius*Math.cos(theta + omegaT+dropletThetaPhasesOCS[i])/4.0f);
					z = (float) ( 2.0f * swirlRadius*Math.sin(theta + omegaT+dropletThetaPhasesOCS[i]/4.0f) *
							swirlRadius*Math.cos(phi + omegaT+dropletThetaPhasesOCS[i]/2.0f) + zOffset );
					distVectInOCS = new Vector3f(x, y, z); //green arrow in Level 1 part B
					allGreenArrows.add(distVectInOCS);
				}
			}
		}
		return allGreenArrows;
	}
	
	private List<Vector3f> getSwirlPattern3(double omegaT) {
		List<Vector3f> allGreenArrows = new ArrayList<Vector3f>();
		Vector3f distVectInOCS = new Vector3f();
		int numSections = 16;
		for ( float theta = 0 ; theta < (float) (2.0f * Math.PI) ; theta += (float) (2.0f * Math.PI / numSections)) {
			for ( float phi = 0 ; phi < (float) (Math.PI) ; phi += (float) (2.0f * Math.PI / numSections)) {
				for(int i = 0 ; i < this.numberDroplets; i++){
					float x,y,z;
					x = (float) (4.0f*swirlRadius*Math.sin(theta + omegaT+dropletThetaPhasesOCS[i]/4.0f) *
							Math.cos( phi+omegaT+dropletThetaPhasesOCS[i]/17.0f));
					y = (float) (4.0f*swirlRadius*Math.cos(theta + omegaT+dropletThetaPhasesOCS[i]/4.0f) *
							Math.sin( phi+omegaT+dropletThetaPhasesOCS[i]/17.0f));
					z = (float) (2.0f*swirlRadius*Math.sin(theta + omegaT+dropletThetaPhasesOCS[i]/4.0f) *
							swirlRadius*Math.sin(phi + omegaT+dropletThetaPhasesOCS[i]/17.0f) +
							zOffset );
					distVectInOCS = new Vector3f(x, y, z); //green arrow in Level 1 part B
					allGreenArrows.add(distVectInOCS);
				}
			}
		}
		return allGreenArrows;
	}
	
	
	private Collection<? extends Vector3f> getSwirlPattern4(double omegaT) {
		
		List<Vector3f> allGreenArrows = new ArrayList<Vector3f>();
		Vector3f distVectInOCS = new Vector3f();
		int numSections = 32;
		for ( float theta = 0 ; theta < (float) (2.0f * Math.PI) ; theta += (float) (2.0f * Math.PI / numSections)) {
			for ( float phi = 0 ; phi < (float) (Math.PI) ; phi += (float) (2.0f * Math.PI / numSections)) {
				for(int i = 0 ; i < this.numberDroplets; i++){
					float x,y,z;
					x = (float) (2.0f*swirlRadius*Math.sin(theta+omegaT+dropletThetaPhasesOCS[i]/12.0f) *
							Math.cos(phi+omegaT+dropletThetaPhasesOCS[i]/4.0f));
					y = (float) (2.0f*swirlRadius*Math.sin(theta+omegaT+dropletThetaPhasesOCS[i]/12.0f) *
							Math.sin(phi+omegaT+dropletThetaPhasesOCS[i]/4.0f) +
							Math.sin(theta+omegaT+dropletThetaPhasesOCS[i]/12.0f));
					z = (float) (2.0f*swirlRadius *
							Math.sin(theta+omegaT+dropletThetaPhasesOCS[i]/4.0f) *
							Math.cos(phi+omegaT/2.0f+dropletThetaPhasesOCS[i]/4.0f) +
							zOffset );
					distVectInOCS = new Vector3f(x, y, z); //green arrow in Level 1 part B
					allGreenArrows.add(distVectInOCS);
				}
			}
		}
		return allGreenArrows;
	}

	public double getSphereRadius() {
		return this.sphereRadius;		
	}


}
