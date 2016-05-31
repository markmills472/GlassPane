package Model;

import java.awt.Dimension;
import java.awt.Shape;
import java.util.List;
import java.util.Map;

import Helpers.Matrix4x4f;
import Helpers.Vector3f;
import View.ViewGlassInt;

/**
 * Model interface.
 * 
 * Model for FunShapes
 * 
 * @author Mark Mills
 * 
 * </pre>
 */
public interface ModelGlassInt {

	void startModel();

	List<CoordinateSystem> getAllCoordSystems();
	
	ViewGlassInt getViewingWindow();

	Map<Vector3f, Shape> getAllCurrentCamera2DScreenProjections( Dimension drawingBoundsForPort, int id);

	Map<Vector3f, Shape> getAllCurrentParallel2DScreenProjections(Dimension viewPortDimensions, int id);

	Map<Vector3f, Shape> getAllCurrentCamera2DScreenProjections(
			Dimension drawingBoundsForPort, int glassPaneId,
			List<Matrix4x4f> transforms);

	void clearDataMap(int glassPaneId);
	
	void addPattern( String pattern );

   
}
