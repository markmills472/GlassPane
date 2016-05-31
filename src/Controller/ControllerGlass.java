package Controller;

import java.awt.Dimension;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Helpers.Vector3f;
import Model.CoordinateSystem;
import Model.ModelGlassInt;
import View.ViewGlassInt;

/**
 * Controller class.
 *  
 * @author Mark Mills
 */
public final class ControllerGlass implements ControllerGlassInt {

    public ModelGlassInt model = null;
    public ViewGlassInt view = null;
    protected boolean inParallelSpace = false;
    

    /**
     * Ties in model and view
     * 
     * @param model
     *            model to connect to
     * @param view
     *            view to connect to
     */
    public ControllerGlass(ModelGlassInt model, ViewGlassInt view) {
        this.model = model;
        this.view = view;
    }


	@Override
	public void begin() {
		model.startModel();
		view.startRepaintFlashTimer();
	}
    
	@Override
	public Map<Vector3f,Shape> getVectorsToProjMapToDraw(int id) {
		if(inParallelSpace)
			return model.getAllCurrentParallel2DScreenProjections(view.getViewPortDimensions(), id);
		//else in camera space by default
		return model.getAllCurrentCamera2DScreenProjections(view.getViewPortDimensions(), id);
	}
	
	@Override
	public List<Vector3f> getAllCoordPositions(){
		List<Vector3f> retVal = new ArrayList<Vector3f>();
		List<CoordinateSystem> allCoordSystems = model.getAllCoordSystems();
		for(int i = 0; i < allCoordSystems.size(); i ++){
			retVal.add(allCoordSystems.get(i).getDistanceVectorFromOrg());
		}
		return retVal;
	}
	
	@Override
	public Dimension viewPortDims(){
		
		return new Dimension(view.getViewPortDimensions());
	}


	@Override
	public void toggleToParrellelView(int panelID) {
		model.clearDataMap(panelID);
		if(inParallelSpace)
			inParallelSpace = false;
		else
			inParallelSpace = true;
	}
	


}
