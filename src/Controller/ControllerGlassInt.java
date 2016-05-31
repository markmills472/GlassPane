package Controller;

import java.awt.Dimension;
import java.awt.Shape;
import java.util.List;
import java.util.Map;

import Helpers.Vector3f;

/**
 * Controller interface.
 * 
 * Controller for FunShapes
 * 
 * @author Mark Mills
 * 
 * </pre>
 */
public interface ControllerGlassInt {

	void begin();

	Map<Vector3f, Shape> getVectorsToProjMapToDraw(int panelNum);

	List<Vector3f> getAllCoordPositions();

	Dimension viewPortDims();

	void toggleToParrellelView(int panelID);
	
}
