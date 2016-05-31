package Model;

import java.util.ArrayList;
import java.util.List;

import Helpers.Vector3f;

/**
 * Coordinate System distFromOrigMCS = 
 */
public class CoordinateSystem {
	
	
	private Vector3f currentDistFromOrigMCS;
	private Vector3f startDistFromOrigMCS;
	//HACK XXX FIXME FOR FUN
	boolean makeOutgoingWave = true;
	double velocityZ = -2;
	double velocityY = -.03;
	List<ViewableModel> containedViewables = new ArrayList<ViewableModel>();
	int lowerZBound = 15;	//NOTE: must match color bounds to see model
	int upperZBound = 25;
	int ID;
				
	public CoordinateSystem(int i){
		this.ID = i;
	}
	
	public void addToViewables(ViewableModel vo){
		this.containedViewables.add(vo);
	}
	
	public void setLinearMultiplier(Vector3f distFromOrig) {
		this.currentDistFromOrigMCS = distFromOrig;
	}
	
	public List<ViewableModel> getViewables(){
		return this.containedViewables;
	}
	
	public Vector3f getDistanceVectorFromOrg(long l) {
		if(makeOutgoingWave){
	//		distFromOrigMCS.setX(distFromOrigMCS.x()+l/200000.0);
/*			if(distFromOrigMCS.y() < -10 || distFromOrigMCS.y() > 10){
				velocityY*=-1;
				if(distFromOrigMCS.y() < -10)
					distFromOrigMCS.setY(-10);
				else
					distFromOrigMCS.setY(10);
			}
			distFromOrigMCS.setY(distFromOrigMCS.y()+velocityY);
			*/
			
			//NOTE: Uncomment to move coordinate system
//			if(distFromOrigMCS.z() < lowerZBound || distFromOrigMCS.z() > upperZBound){
//				velocityZ*=-1;
//				if(distFromOrigMCS.z() < lowerZBound)
//					distFromOrigMCS.setZ(lowerZBound);
//				else
//					distFromOrigMCS.setZ(upperZBound);
//			}
//			distFromOrigMCS.setZ(distFromOrigMCS.z()+velocityZ*(l%1000)/200.0);
			
//			distFromOrigMCS.setY(Math.cos(l)*Math.cos(l));
//			distFromOrigMCS.setZ(20+Math.sin(l));
			
			//spins graph in X
			double omegaT = (l)*2*Math.PI/(1024*4);
//			if(this.ID % 2 == 0)
//				doCSModule4(omegaT);
//			else
//				doCSModule5(omegaT);
			doCSModule6(omegaT);
		}
		return currentDistFromOrigMCS;
	}

	private void doCSModule6(double omegaT) {
		currentDistFromOrigMCS.setX((float)this.startDistFromOrigMCS.x());
		currentDistFromOrigMCS.setY((float)this.startDistFromOrigMCS.y());
		currentDistFromOrigMCS.setZ((float)this.startDistFromOrigMCS.z());
	}

	private void doCSModule1(double omegaT) {
		currentDistFromOrigMCS.setX((float)(this.startDistFromOrigMCS.x()+Math.sin(omegaT/2.0)*Math.sin(omegaT/2.0)));
		currentDistFromOrigMCS.setY((float)(this.startDistFromOrigMCS.y()+Math.sin(omegaT/2.0)*Math.cos(omegaT/2.0)));
		currentDistFromOrigMCS.setZ((float)(this.startDistFromOrigMCS.z()+omegaT/100));		
	}

	private void doCSModule2(double omegaT) {
		currentDistFromOrigMCS.setX((float)(this.startDistFromOrigMCS.x()+Math.sin(omegaT/2.0)*Math.sin(omegaT/2.0)*Math.cos(omegaT/2.0)));
		currentDistFromOrigMCS.setY((float)(this.startDistFromOrigMCS.y()+Math.sin(omegaT/2.0)*Math.cos(omegaT/2.0)*Math.cos(omegaT/2.0)));
		currentDistFromOrigMCS.setZ((float)(this.startDistFromOrigMCS.z()+20*Math.cos(omegaT)));		
	}
	
	private void doCSModule3(double omegaT) {
		currentDistFromOrigMCS.setX((float)(this.startDistFromOrigMCS.x()+7.5*Math.sin(omegaT/2.0)));
		currentDistFromOrigMCS.setY((float)(this.startDistFromOrigMCS.y()+7.5*Math.sin(omegaT/2.0)*Math.cos(omegaT/2.0)*Math.cos(omegaT/2.0)));
		currentDistFromOrigMCS.setZ((float)(this.startDistFromOrigMCS.z()+5*Math.cos(omegaT)));		
	}
	
	private void doCSModule4(double omegaT) {
		
		double sin = Math.sin(omegaT);
		double cos = Math.cos(omegaT);
		currentDistFromOrigMCS.setX((float)(this.startDistFromOrigMCS.x()+15*cos));
		currentDistFromOrigMCS.setY((float)(this.startDistFromOrigMCS.y()+25*sin*cos));
		currentDistFromOrigMCS.setZ((float)(this.startDistFromOrigMCS.z()+cos));		
	}	
	
	
	private void doCSModule5(double omegaT) {
		double cos = this.startDistFromOrigMCS.x()*Math.cos(omegaT+Math.PI/2.0);
		double sinX = this.startDistFromOrigMCS.x()*Math.cos(omegaT+Math.PI/2.0);
		double sinY =this.startDistFromOrigMCS.y()*Math.sin(omegaT);
		if(startDistFromOrigMCS.x() < 0)
			currentDistFromOrigMCS.setX((float)(1.5*cos));
		else
			currentDistFromOrigMCS.setX((float)(-1.5*cos));
		if(startDistFromOrigMCS.y() < 0)
			currentDistFromOrigMCS.setY((float)(sinY));
		else
			currentDistFromOrigMCS.setY((float)(-sinY));	
		if(startDistFromOrigMCS.z() < 0)
			currentDistFromOrigMCS.setZ((float)(this.startDistFromOrigMCS.z()+sinX));
		else
			currentDistFromOrigMCS.setZ((float)(this.startDistFromOrigMCS.z()-sinX));	
		
	}
	
	public Vector3f getDistanceVectorFromOrg() {
		return currentDistFromOrigMCS;
	}
	
	public void setDistanceVectorFromOrg(Vector3f distFromOrig) {
		this.currentDistFromOrigMCS =  new Vector3f(distFromOrig);
		this.startDistFromOrigMCS = new Vector3f(distFromOrig);
	}
	
}
