
package Model;

import java.awt.Dimension;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Helpers.Matrix4x4f;
import Helpers.Vector3f;
import View.ViewGlass;
import View.ViewGlassInt;

/**
 * Model for Glass Pane 
 * 
 * @author Mark Mills
 * 
 * </pre>
 */
public final class ModelGlass implements ModelGlassInt {
	
	List<CoordinateSystem> allCoords = new ArrayList<CoordinateSystem>();
	Map<Integer,Map<Vector3f,Shape>> allGlassPaneVectData = new HashMap<Integer,Map<Vector3f, Shape>>();
	private long startTime;
	
	float xVel = 1;
	float yVel = 1;
	float zVel = 1;
	
    public ModelGlass() {
    	
    	CoordinateSystem csHold;
    	float placementRadius = 4.0f;
    	float pointRadius = 0.55f;
    	float zOffset = 127.5f;
    	int count = 0;  	
    	
    }
    
    @Override 
    public void startModel(){
    	this.startTime = System.currentTimeMillis();
    }
    
    @Override
    public void addPattern( String pattern ) {
    	
    	CoordinateSystem csHold;
    	float placementRadius = 2.0f;
    	float pointRadius = 0.55f;
    	float zOffset = 127.5f;
    	int count = 0;
    	
    	switch (pattern) {
//	    	case "SwirlPatternModule1" :
//	    		for(double theta = 1 ; theta < 2.0*Math.PI ; theta += (2*Math.PI/ 13.0)){
//	        		for(double phi = 1 ; phi < 2.0*Math.PI ; phi += (2*Math.PI/ 7.0)){
//	    				csHold = new CoordinateSystem(count);
//	    				csHold.setDistanceVectorFromOrg(new Vector3f(
//	    						(float)((ViewGlass.controller.viewPortDims().getWidth() / 16.0f) +
//			    						(ViewGlass.controller.viewPortDims().getWidth() / 24.0f) *
//										Math.sin(theta)*Math.cos(phi)),
//	    						
//	    						(float)((ViewGlass.controller.viewPortDims().getHeight() / 16.0f) +
//			    						(ViewGlass.controller.viewPortDims().getHeight() / 24.0f) *
//										Math.sin(theta)*Math.cos(phi)),
//	    						
//	    						(float)(zOffset +
//	    								placementRadius*Math.cos(theta)
//	    								)));
//	    				SwirlDroplet droplet = new SwirlDroplet(csHold);
//	    	//			Sphere sphere = new Sphere(4.0f, 4, 16, csHold);
//	    	    		this.allCoords.add(csHold);
//	    	    		count++;
//	        		}
//	    		}
//	    	break;
    	}
    	
    	
	//    	for(double phi = 0 ; phi < 2.0*Math.PI ; phi += (2*Math.PI/ 64.0)){
	//		for(double theta = 1 ; theta < 2.0*Math.PI ; theta += (2*Math.PI/ 32.0)){
	//			csHold = new CoordinateSystem(count);
	//			
	//			csHold.setDistanceVectorFromOrg(new Vector3f(
	//					(float)(placementRadius*Math.sin(theta)*Math.cos(phi)),
	//					(float)(placementRadius*Math.sin(theta)*Math.cos(phi)),
	//					(float)(placementRadius*Math.cos(phi) + zOffset)));
	//
	//			ConwayGameOfLife3DMatrix conwayGame = new ConwayGameOfLife3DMatrix(csHold,pointRadius);
	//			
	//			Vector3f seedLocation = new Vector3f(
	//					(int)(placementRadius*Math.sin(theta)*Math.cos(phi)),
	//					(int)(placementRadius*Math.sin(theta)*Math.cos(phi)),
	//					(int)(placementRadius*Math.cos(phi)));
	//			
	//			conwayGame.addNewSeed(seedLocation);
	//			
	//			for ( float x = -5f ; x <= 5f ; x += 1f ) {
	//				for ( float y = -5f ; y <= 5f ; y += 1f ) {
	//					for ( float z = -5f ; z <= 5f ; z += 1f ) {
	//						seedLocation = new Vector3f( (float) (Math.random() * x), (float) (Math.random() * y), (float) (Math.random() * z) );
	//						conwayGame.addNewSeed(seedLocation);
	//					}
	//				}
	//			}
	//			
	//			csHold.addToViewables(conwayGame);
	//    		allCoords.add(csHold);
	//    		count++;
	//		}
	//	}
	//	
	//	csHold = new CoordinateSystem(count);
	//	csHold.setDistanceVectorFromOrg(new Vector3f(
	//			(float)(0),
	//			(float)(0),
	//			(float)(zOffset)));
	//	Cube cube = new Cube(2f,csHold);
	//	Sphere sphere = new Sphere(4.0f, 4, 16, csHold);
	//	allCoords.add(csHold);
    	
	}

	@Override 
    public List<CoordinateSystem> getAllCoordSystems(){
    	return this.allCoords;
    }
    
	/**
	 * @requires |positionVectorsInOCS| = |distVectorsToViewables|
	 * 
	 * @param vo
	 * @param positionVectorsInOCS
	 * @param distVectorsToViewables
	 * @param transforms 
	 * @return
	 */
	private void get2DShapeProjectionForViewableObject(ViewableModel vo,
			List<Vector3f> positionVectorsInOCS,
			List<Vector3f> distVectorsToViewables,Map<Vector3f, Shape> shapeMap,
			Dimension drawingBoundsForPort, List<Matrix4x4f> transforms) {
		
		Rectangle2D rect;
		int x = 0,y = 0,width = 0,height = 0;
		
		// Don't add anything to map if it's full
		if(shapeMap.size() > positionVectorsInOCS.size()){
			shapeMap.clear();
			return;
		}
		
		//Only draw shapes if there are position vectors
		if(positionVectorsInOCS.size() == 0){
			return;
		}
	
		//draw dot
		Vector3f origin = new Vector3f(distVectorsToViewables.get(0));
		origin.sub(positionVectorsInOCS.get(0));
		origin.add(new Vector3f(this.xVel,this.yVel,this.zVel + (float)(15.5f*Math.sin(this.startTime))) );
		Vector3f pixVector = mapVectorToPixelVector(origin,drawingBoundsForPort);
	//	shapeMap.put(origin,new Ellipse2D.Double((int) pixVector.x()-10, (int) pixVector.y()-10, 10, 10));
		/* remember -- screen uses x y --> positive going right then down on computer screen
		 * ... say middle of screen is at point (0,0,.5) (.5 meters back from user) 
		 * 	after getting x,y,z in this system --> translate to computer system*/
		
		for(int i = 0; i < distVectorsToViewables.size(); i ++){
			Vector3f point = distVectorsToViewables.get(i);
			Vector3f topLeft = new Vector3f(point);
			Vector3f topRight = new Vector3f(point);
			
			if(vo instanceof SwirlDroplet ){
				SwirlDroplet sd = (SwirlDroplet)vo;
				double cubeRadius = sd.getSphereRadius();
				//top left corner of each cube center for projection... say cube radius = .5 meters
				// LOOKS LIKE POST-IT ON TOP LEFT CORNER OF CUBE... WITH SAME AREA AS FACE... tehe
				topLeft.add(new Vector3f((float)(-cubeRadius),
						(float)(cubeRadius),
						(float)(-cubeRadius)));
				topRight.add(new Vector3f((float)cubeRadius,(float)cubeRadius,(float)(-cubeRadius)));
				/* Apply transforms if they exist */
				if(transforms != null){
					topLeft = applyTransforms(topLeft,transforms);
					topRight = applyTransforms(topRight,transforms);
				}
				Vector3f pixVectorLeft = mapVectorToPixelVector(topLeft,drawingBoundsForPort);
				Vector3f pixVectorRight = mapVectorToPixelVector(topRight,drawingBoundsForPort);
				
				x = (int) pixVectorLeft.x();
				y = (int) pixVectorLeft.y();
				
				pixVectorRight.sub(pixVectorLeft);
				width = (int) pixVectorRight.length();
				height = width;
				
			} else if (vo instanceof Sphere){
				
				Sphere sd = (Sphere)vo;
				float cubeRadius = sd.getSphereRadius();
				//top left corner of each cube center for projection... say cube radius = .5 meters
				// LOOKS LIKE POST-IT ON TOP LEFT CORNER OF CUBE... WITH SAME AREA AS FACE... tehe
				topLeft.add(new Vector3f((float)(-cubeRadius),
						(float)(cubeRadius),
						(float)(-cubeRadius)));
				topRight.add(new Vector3f((float)cubeRadius,(float)cubeRadius,(float)(-cubeRadius)));
				/* Apply transforms if they exist */
				if(transforms != null){
					topLeft = applyTransforms(topLeft,transforms);
					topRight = applyTransforms(topRight,transforms);
				}
				Vector3f pixVectorLeft = mapVectorToPixelVector(topLeft,drawingBoundsForPort);
				Vector3f pixVectorRight = mapVectorToPixelVector(topRight,drawingBoundsForPort);
				
				x = (int) pixVectorLeft.x();
				y = (int) pixVectorLeft.y();
				
				pixVectorRight.sub(pixVectorLeft);
				width = (int) pixVectorRight.length();
				height = width;
				
			} else if (vo instanceof FaceShape){
				FaceShape fs = (FaceShape)vo;
//				Vector3f posVect = fs.xYZPositionVector();
				//top front left corner of rectangular shape 
//				topLeft.add(new Vector3f(posVect.x(),posVect.y(),-posVect.z()));
//				topRight.add(new Vector3f(-posVect.x(),posVect.y(),-posVect.z()));
				Vector3f pixVectorLeft = mapVectorToPixelVector(topLeft,drawingBoundsForPort);
				Vector3f pixVectorRight = mapVectorToPixelVector(topRight,drawingBoundsForPort);
				
				x = (int) pixVectorLeft.x();
				y = (int) pixVectorLeft.y();
				
				pixVectorRight.sub(pixVectorLeft);
				width = (int) pixVectorRight.length();
				height = width;
			} else if ( vo instanceof ConwayGameOfLife3DMatrix ){
				ConwayGameOfLife3DMatrix sd = (ConwayGameOfLife3DMatrix) vo;
				float sphereRadius = sd.getPointRadius();
				//top left corner of each cube center for projection... say cube radius = .5 meters
				// LOOKS LIKE POST-IT ON TOP LEFT CORNER OF CUBE... WITH SAME AREA AS FACE... tehe
				topLeft.add(new Vector3f((float)(-sphereRadius),
						(float)(sphereRadius),
						(float)(-sphereRadius)));
				topRight.add(new Vector3f((float)sphereRadius,(float)sphereRadius,(float)(-sphereRadius)));
				/* Apply transforms if they exist */
				if(transforms != null){
					topLeft = applyTransforms(topLeft,transforms);
					topRight = applyTransforms(topRight,transforms);
				}
				Vector3f pixVectorLeft = mapVectorToPixelVector(topLeft,drawingBoundsForPort);
				Vector3f pixVectorRight = mapVectorToPixelVector(topRight,drawingBoundsForPort);
				
				x = (int) pixVectorLeft.x();
				y = (int) pixVectorLeft.y();
				
				pixVectorRight.sub(pixVectorLeft);
				width = (int) pixVectorRight.length();
				height = width;
				
			}
				//FIXME --- point is to middle of a cube, but projection is just front side parallel to viewer
			if(x >= -drawingBoundsForPort.width && x < 2*drawingBoundsForPort.width && y >= -1.5*drawingBoundsForPort.height && y <= 1.5*drawingBoundsForPort.height )
//				shapeMap.put(point,new Rectangle2D.Double(x,y,width,height));
				shapeMap.put(point,new Ellipse2D.Double(x,y,width,height));

		}	
	}
	private Vector3f applyTransforms(Vector3f vector, List<Matrix4x4f> transforms) {
		for(int k = transforms.size() - 1; k >= 0 ; k--){
			Matrix4x4f holderMatrix = new Matrix4x4f();
			holderMatrix.setMatrix(
					vector.x(), 0, 0, 0,
					0, vector.y(), 0, 0,
					0, 0, vector.z(), 0,
					0, 0, 0, 0);
			
			float[][] holderMatrix2DArray = holderMatrix.getMatrixAs2DFloatArray();
			vector = new Vector3f(
					holderMatrix2DArray[0][0],
					holderMatrix2DArray[1][1],
					holderMatrix2DArray[2][2]);
			
		}
		return vector;
	}

	/**
	 * XXX -- FIXME Convert to pixels
	 * DOES NOT CLEAR origin
	 * @param origin
	 * @param drawingBoundsForPort 
	 * @return
	 */
	private Vector3f mapVectorToPixelVector(Vector3f origin, Dimension drawingBoundsForPort) {
		Vector3f pixVector = new Vector3f(origin);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		//scale to screen (.5 meters away)
		pixVector.scale(.5f/pixVector.z());
		//move grid system to match computers
		pixVector.setY(-1f*pixVector.y());
		//scale to pixels
		pixVector.setX((float)(pixVector.x() * screenSize.getWidth()/.34)); // meter vector * pixel/meter .. topLeft = <pixel,pixel,meter>
		pixVector.setY((float)(pixVector.y() * screenSize.getHeight()/.19));
		pixVector.add(new Vector3f(
				(float)(drawingBoundsForPort.width/2.0),
				(float)(drawingBoundsForPort.height/2.0),
				(float)(drawingBoundsForPort.height)));
		return pixVector;
	}

	/**
	 * XXX - FIXME method not yet implemented
	 */
	@Override
	public Map<Vector3f, Shape> getAllCurrentParallel2DScreenProjections(Dimension viewPortDimensions, int id) {
		return getAllCurrentCamera2DScreenProjections(viewPortDimensions,id,null);
	}
	
	@Override
	public Map<Vector3f,Shape> getAllCurrentCamera2DScreenProjections(Dimension drawingBoundsForPort, int glassPaneId, List<Matrix4x4f> transforms) {
		if(!allGlassPaneVectData.containsKey(glassPaneId)){
			allGlassPaneVectData.put(glassPaneId,new HashMap<Vector3f, Shape>());
		}
		Map<Vector3f, Shape> shapeMap = allGlassPaneVectData.get(glassPaneId);
		List<Vector3f> distVectorsToViewables = new ArrayList<Vector3f>();
		List<ViewableModel> viewables;
		Vector3f distanceVectorFromOrg,hold;
		ViewableModel vo;
		List<Vector3f> positionVectorsInOCS;
		
		/* get all things that need to be drawn for every coordinate system in model -- get their distance vectors*/
		for(int i = 0; i < allCoords.size(); i++){
			viewables = allCoords.get(i).getViewables();
			distanceVectorFromOrg = allCoords.get(i).getDistanceVectorFromOrg(System.currentTimeMillis() - this.startTime);
			/* each coordninate can have multiple viewable objects -- ie: 2 swirls patterns and a cube pattern */
			for(int k = 0; k < viewables.size(); k++){
				vo = viewables.get(k);
				//list of vectors ordered in case of 2D projection depending on location of specific vertices
				positionVectorsInOCS = vo.getAllPositionVectorsInOCS(System.currentTimeMillis() - this.startTime);
				/* each patten can have multiple things that need to be drawn -- ie: a cube could have 8 vertices*/
				for(int z = 0; z < positionVectorsInOCS.size(); z++){
					/* green arrow plus blue and red arrow in documentation*/
					hold = new Vector3f();
					hold.add(positionVectorsInOCS.get(z));
					hold.add(distanceVectorFromOrg);
					distVectorsToViewables.add(hold);
				}
				/* this method decides how to assign screen shapes based on the viewable object's position data*/
				get2DShapeProjectionForViewableObject(vo,positionVectorsInOCS, distVectorsToViewables,shapeMap,drawingBoundsForPort,transforms);
				
				//there is no surface in 1 or 3
//				if(glassPaneId != 2)
//					clearDataMap(glassPaneId);
				/* DONE---Kept for documentation
				TODO Get distance vectors to viewables ... then use information to create
					2D projection shapes on computer screen
					
					(SEE MVC Overview part b - Level 1 abstraction)
					
					-getAllPositionVectorsInOCS(System.currentTimeMillis() - this.startTime) returns green vectors at given time
					-loop through green arrows to draw Black arrows are the contained in distVectorsToViewables, find
					 	by adding green vectors to red/blue -- OR (distanceVectorFromOrg + greenVector_i)
					- designate shape to black arrows found -- project to computer screen
					- translate projection in real coordinates to computer screen coordinates (top-left corner origin system)
					- add created shape to list, return to view to draw and assemble with other components
				*/
			}
		}
		
		return shapeMap;
	}

	@Override
	public ViewGlassInt getViewingWindow() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Vector3f, Shape> getAllCurrentCamera2DScreenProjections(
			Dimension drawingBoundsForPort, int id) {
		// TODO Calculate Rotation THEN offset Matricies to left multiply then Pass in list named transforms
		List<Matrix4x4f> transforms = new ArrayList<Matrix4x4f>();
		transforms.add(0,getRotationMatrixAroundZAxis(3.0*Math.PI/4.0));
		transforms.add(0,getOffsetMatrix(new Vector3f(0,0,20)));
		return getAllCurrentCamera2DScreenProjections(drawingBoundsForPort,id,transforms);
	}

	private Matrix4x4f getOffsetMatrix(Vector3f xyzOffset) {
		Matrix4x4f offsetMatrix = new Matrix4x4f();
		offsetMatrix.setMatrixToIdentityMatrix();
		return offsetMatrix;
	}

	private Matrix4x4f getRotationMatrixAroundZAxis(double theta) {
		Matrix4x4f rotationMatrix = new Matrix4x4f();
		rotationMatrix.setMatrix(
				(float)(Math.cos(theta)), (float)(Math.sin(theta)), 0, 0,
				(float)(-Math.sin(theta)), (float)(Math.cos(theta)),0,0,
				0,0,1,0,
				0,0,0,1);
		return rotationMatrix;
	}

	@Override
	public void clearDataMap(int glassPaneId) {
		allGlassPaneVectData.put(glassPaneId,new HashMap<Vector3f, Shape>());
	}

}
