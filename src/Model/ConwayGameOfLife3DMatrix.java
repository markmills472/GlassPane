package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Helpers.Vector3f;

/***
 * This class models a 3D Presentation of Conway's Game of Life.
 * 
 * 
 ** "Life" follows Conway's 4 Rules for each orthogonal dimension in Z^3 (3D space in the Z - or integer - Domain)
 **** The following are the 2D orthogonal planes that will be used to run "Life"
 ****** XY
 ****** XZ
 ****** YZ
 *
 ** With each iteration of the model:
 **** New seeds are planted at specified XYZ locations
 **** "Life" is spread to each each cell by following a slight variation of the 4 rules of Conway's Game for each Orthogonal Plane
 ****** There are 26 possible neighbors - "Life" rules are scaled accordingly by multiplying "Life" neighbor numbers by 13/4
 **** "Life" is first spread in the XY plane, followed by the XZ plane, followed by the YZ plane
 **** Running total of consecutive iterations that a cell is "Alive" are recorded in the dimensional arrays
 **** Dead Cells are set to 0
 **** Color of the cell is displayed based on the stored integer in that cell
 * 
 *
 * @author mmills
 *
 */
public class ConwayGameOfLife3DMatrix implements ViewableModel {

	public final CoordinateSystem objCoordSystem;
	public List<Vector3f> newSeedLocations;
	public Map<Vector3f,Integer> currentLiveCellLocations;
	public float pointRadius;
	
	public ConwayGameOfLife3DMatrix(CoordinateSystem objCoordSystem, float pointRadius) {
		
		this.objCoordSystem = objCoordSystem;
		this.objCoordSystem.addToViewables(this);
		this.pointRadius = pointRadius;
		this.newSeedLocations = new ArrayList<Vector3f>();
		this.currentLiveCellLocations = new HashMap<Vector3f,Integer>();
		
	}
	
	@Override
	public CoordinateSystem getObjectCoordSystem() {
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
	
		iterateLifeAfterPlantingNewSeeds();
		return getVectorsToAllLiveCells();
		
	}
	
	public float getPointRadius() {
		return this.pointRadius;
	}

	private void iterateLifeAfterPlantingNewSeeds() {
		plantNewSeeds();
		iterateLife();
		clearNewSeedList();
	}

	private void plantNewSeeds() {
		
		Map<Vector3f,Integer> localCurrentCellLocations = new HashMap<Vector3f,Integer> (this.currentLiveCellLocations);
		for ( Vector3f currentLiveCellLocation : this.currentLiveCellLocations.keySet() ) {
			localCurrentCellLocations.put( currentLiveCellLocation, this.currentLiveCellLocations.get(currentLiveCellLocation) ); 
		}
		
		for(Vector3f newSeedLoc : this.newSeedLocations) {
			
			if ( currentLiveCellLocations.size() == 0 ) {
				this.currentLiveCellLocations.put( new Vector3f (newSeedLoc.x(), newSeedLoc.y(), newSeedLoc.z() ) , 1 );
				localCurrentCellLocations.put( newSeedLoc, 1 );
			} else {
				
				for(Vector3f liveCellLoc : localCurrentCellLocations.keySet()) {

					if(newSeedLoc.distanceFrom(liveCellLoc) == 0f) {
						Integer currentWeight = this.currentLiveCellLocations.get(liveCellLoc);
						if(currentWeight == null) {
							this.currentLiveCellLocations.put( liveCellLoc, 1 );
							localCurrentCellLocations.put( liveCellLoc, 1 );
						} else {
							this.currentLiveCellLocations.put( liveCellLoc, (currentWeight + 1));
							localCurrentCellLocations.put( liveCellLoc, (currentWeight + 1));
						}
						
					} else {
						this.currentLiveCellLocations.put(newSeedLoc, 1);
					}
					
				}
				
			}

		}
		
	}
	
	/**
	 * "Life" follows Conway's 4 Rules for each orthogonal dimension in Z^3 (3D space in the Z - or integer - Domain)
	 ** The following are the 2D orthogonal planes that will be used to run "Life"
	 **** XY
	 **** XZ
	 **** YZ
	 *
	 * "Life" is first spread in the XY plane, followed by the XZ plane, followed by the YZ plane
	 * 
	 */
	public void iterateLife() {

		Map<Vector3f, Integer> currentLiveCellLocationsLocal = this.currentLiveCellLocations;
		// Spread life in following order: XY, XZ, YZ
		spreadLifeAround(currentLiveCellLocationsLocal);
		
	}
	
	// TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO
	// need to figure out quick way to determaine number of neighbors to a liveCellLocation
	private void spreadLifeAround(Map<Vector3f, Integer> currentLiveCellLocations) {
		
		Vector3f[] currentLiveCellLocationsArray = new Vector3f[currentLiveCellLocations.size()];
		currentLiveCellLocations.keySet().toArray(currentLiveCellLocationsArray);
		
		for( Vector3f liveCellLocation : currentLiveCellLocationsArray ) {
			
			int numLiveNeighbors = findLiveNeighborsCount(liveCellLocation, currentLiveCellLocationsArray);
			
			switch(numLiveNeighbors) {
				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 6:
				case 7:
				case 8:
					this.currentLiveCellLocations.remove(liveCellLocation);
					break;
				case 9:
				case 10:
				case 11:
				case 12:
				case 13:
					break;
				case 14:
				case 15:
				case 16:
				case 17:
				case 18:
					for ( int xShift = -1 ; xShift <= 1 ; xShift += 2 ) {
						for ( int yShift = -1 ; yShift <= 1 ; yShift += 2 ) {
							for ( int zShift = -1 ; zShift <= 1 ; zShift += 2 ) {
								Vector3f neighborLoc = new Vector3f ( ( liveCellLocation.x() + xShift ) ,( liveCellLocation.y() + yShift ), ( liveCellLocation.y() + yShift ));
								if (!this.currentLiveCellLocations.containsKey(neighborLoc) ) {
									this.currentLiveCellLocations.put(neighborLoc, 1);
								} else if ( this.currentLiveCellLocations.get(neighborLoc) == null ) {
									this.currentLiveCellLocations.put(neighborLoc, 1);
								} else if ( this.currentLiveCellLocations.get(neighborLoc) != null) {
									Integer weight = this.currentLiveCellLocations.get(neighborLoc);
									weight++;
									this.currentLiveCellLocations.put(neighborLoc, weight);
								}
							}
						}
					}
					break;
				case 19:
				case 20:
				case 21:
				case 22:
				case 23:
				case 24:
				case 25:
				case 26:
					this.currentLiveCellLocations.remove(liveCellLocation);
					break;
			}
			
		}
		
	}

	private int findLiveNeighborsCount( Vector3f currentLiveCellLocation, Vector3f[] allLiveCellLocations) {
		
		List<Vector3f> neighborList = new ArrayList<Vector3f>();
		int neighborCount = 0;
		
		for ( int i = 0 ; i < allLiveCellLocations.length ; i++ ) {
			
			for ( int n = 1 ; n <= 8 ; n++ ) {
				
				Vector3f possibleNeighbor = allLiveCellLocations[i];
					
				if( ( ( (int) possibleNeighbor.x() == (int) (currentLiveCellLocation.x() + 1) ) ||
						( (int) possibleNeighbor.x() == (int) (currentLiveCellLocation.x() - 1)) )
						&&
						( ( (int) possibleNeighbor.y() == (int) (currentLiveCellLocation.y() + 1) ) ||
								( (int) possibleNeighbor.y() == (int) (currentLiveCellLocation.y() - 1)) ) 
						&&
						( ( (int) possibleNeighbor.z() == (int) (currentLiveCellLocation.z() + 1) ) ||
								( (int) possibleNeighbor.z() == (int) (currentLiveCellLocation.z() - 1)) ) ) {
					
					if( !neighborList.isEmpty() && !neighborList.contains(possibleNeighbor)) {
						neighborList.add(possibleNeighbor);
						neighborCount++;
					} else if ( neighborList.isEmpty() ) {
						neighborList.add(possibleNeighbor);
						neighborCount++;
					}
					
				}
					
				break;
									
			}
			
		}
		
//		if(neighborCount != 0) {
//			neighborCount = 3;
//		}
		return neighborCount;
	}

	private void clearNewSeedList() {
		this.newSeedLocations.clear();
	}

	private List<Vector3f> getVectorsToAllLiveCells() {
		return new ArrayList<Vector3f>(this.currentLiveCellLocations.keySet());
	}	
	
	/**
	 * Returns weights with weightList.get(n) corresponding to this.currentLiveCellLocations.get(n)
	 * @return
	 */
	public Map<Vector3f,Integer> getAllPositionVectorsInOCSWithWeights () {		
		return this.currentLiveCellLocations;
	}
	
	public void addNewSeed(Vector3f seedLocation) {
		newSeedLocations.add(seedLocation);
	}
	
	public void addNewListOfSeeds(List<Vector3f> seedList) {
		newSeedLocations.addAll(seedList);
	}

//	private List<Vector3f> getSwirlPattern1(double omegaT) {
//		
//		List<Vector3f> allGreenArrows = new ArrayList<Vector3f>();
//		Vector3f distVectInOCS = new Vector3f();
//		for(int i = 0 ; i < this.numberDroplets; i++){
//			float x,y,z;
//			x = (float) ( swirlRadius*Math.sin(4*omegaT+dropletThetaPhasesOCS[i]/2.0f)*swirlRadius*Math.cos(omegaT/4.0+dropletThetaPhasesOCS[i]/2.0f) );
//			y = (float) ( swirlRadius*Math.cos(2.0*omegaT+dropletThetaPhasesOCS[i]/2.0f) );
//			z = (float) ( swirlRadius*Math.cos(8*omegaT+dropletThetaPhasesOCS[i]/4.0f)*swirlRadius*Math.cos(omegaT/4.0+dropletThetaPhasesOCS[i]/2.0f) );
//			distVectInOCS = new Vector3f(x, y, z); //green arrow in Level 1 part B
//			allGreenArrows.add(distVectInOCS);
//		}
//		return allGreenArrows;
//	}

}
