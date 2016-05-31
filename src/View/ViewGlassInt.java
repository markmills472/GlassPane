package View;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import Controller.ControllerGlassInt;

/**
 * View interface.
 * 
 * View for FunShapes
 * 
 * @author Mark Mills
 * 
 * </pre>
 */
public interface ViewGlassInt extends ActionListener {

	void setController(ControllerGlassInt controller);

	void startRepaintFlashTimer();

	Dimension getViewPortDimensions();

   

}
