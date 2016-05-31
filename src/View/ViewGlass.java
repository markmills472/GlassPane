package View;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

import Controller.ControllerGlassInt;
import Helpers.AllActionListeners;
import Helpers.Vector3f;

/**
 * 
 * View for FunShapes
 * 
 * @author Mark Mills
 * 
 * </pre>
 */
public final class ViewGlass extends JFrame implements ViewGlassInt {

    public static ControllerGlassInt controller;
    private final Timer timer = new Timer(20, this);
    public static final int GLASSPANE_HEIGHT = 800;
    public static final int GLASSPANE_WIDTH = 800;
    final static boolean RIGHT_TO_LEFT = true;

    private int flashRedrawCount = 0;
    public static boolean flashRedraw = false;
    
  //  JTextField inX;
//    private JViewport vp;
    private GlassPanel gp1;
    private GlassPanel gp2;
    private GlassPanel gp3;
    private GlassPanel gp4;
    private GlassPanel gp5;
    private GlassPanel gp6;
    private GlassPanel gp7;
    private GlassPanel gp8;
    private GlassPanel gp9;
    private ControlPanel cp;

    public ViewGlass() {
    	this.setMinimumSize(new Dimension(GLASSPANE_WIDTH+100,GLASSPANE_HEIGHT+100));
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	initializeView(this.getContentPane());
    	
    	AllActionListeners listener = new AllActionListeners(){
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getID() == KeyEvent.VK_P){
					controller.toggleToParrellelView(69);
				}
			}
		};
		this.addKeyListener(listener);
		
    }

	private void initializeView(Container contentPane) {
		
    	if(RIGHT_TO_LEFT)
    		contentPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    	
    	GridLayout gl = new GridLayout(1,1);
    	
    	contentPane.setLayout(gl);
    	GridBagConstraints gbc = new GridBagConstraints();
//    	contentPane.setLayout(gbc);
    	
    	// View 1
    	float[] shapeColor1 = {128, 255, 127, 255, 128, 255 };
    	Vector3f shapeColorVel1 = new Vector3f(1f, -2f, 3f);
    	Color bkgColor1 = new Color(0.1f, 0.8f, 0.1f);
    	Color bkgColor2 = new Color(0.2f, 0.1f, 0.7f);
    	Color bkgColor3 = new Color(0.3f, 0.6f, 0.1f);
    	Color bkgColor4 = new Color(0.4f, 0.1f, 0.5f);
    	Color bkgColor5 = new Color(0.5f, 0.5f, 0.5f);
    	Color bkgColor6 = new Color(0.6f, 0.1f, 0.3f);
    	Color bkgColor7 = new Color(0.7f, 0.2f, 0.1f);
    	Color bkgColor8 = new Color(0.8f, 0.1f, 0.1f);
    	Color bkgColor9 = new Color(0.9f, 0.1f, 0.0f);
 
    	/***
    	//View 4
    	float[] shapeColor4 = {100, 255, 200, 255, 100, 255 };
    	Vector3f shapeColorVel4 = new Vector3f(2f, 4f, 2f);
    	Vector3f bkgColorVel4 = new Vector3f(2f, 0f, 2f);
    	
    	GlassPanel.GlassPanelColorPackage colorPkg4 = GlassPanel.getGlassPanelColorPackageInstance(
    			shapeColor4,
    			shapeColorVel4,
    			bkgColor4,
    			bkgColorVel4
    			); 
    	
    	this.gp4 = new GlassPanel(4,controller, colorPkg4);
    	gbc.fill = GridBagConstraints.VERTICAL;
    	//c.ipady = GLASSPANE_HEIGHT;
//    	c.ipady = GLASSPANE_WIDTH;
  //  	c.ipadx =GLASSPANE_HEIGHT;
    	gbc.gridwidth = 2;
    	gbc.gridheight = 2;
    	gbc.weightx = .5;
    	gbc.weighty = .5;
    	gbc.gridx = 1;
    	gbc.gridy = 1;
    	contentPane.add(gp4,gl);
    	
    	//View 5
    	float[] shapeColor5 = {127.5f, 255, 127.5f, 255, 127.5f, 255 };
    	Vector3f shapeColorVel5 = new Vector3f(2f, 4f, 2f);
    	Vector3f bkgColorVel5 = new Vector3f(-4f, 2f, -4f);
    	
    	GlassPanel.GlassPanelColorPackage colorPkg5 = GlassPanel.getGlassPanelColorPackageInstance(
    			shapeColor5,
    			shapeColorVel5,
    			bkgColor5,
    			bkgColorVel5
    			); 
    	
    	this.gp5 = new GlassPanel(5,controller, colorPkg4);
    	gbc.fill = GridBagConstraints.VERTICAL;
    	//c.ipady = GLASSPANE_HEIGHT;
//    	c.ipady = GLASSPANE_WIDTH;
  //  	c.ipadx =GLASSPANE_HEIGHT;
    	gbc.gridwidth = 2;
    	gbc.gridheight = 2;
    	gbc.weightx = .5;
    	gbc.weighty = .5;
    	gbc.gridx = 1;
    	gbc.gridy = 1;
    	contentPane.add(gp5,gl);
    	
    	//View 6
    	float[] shapeColor6 = {127.5f, 255, 255, 255, 0, 255 };
    	Vector3f shapeColorVel6 = new Vector3f(-2f, 1f, -2f);
    	Vector3f bkgColorVel6 = new Vector3f(-2f, 0f, -2f);
    	
    	GlassPanel.GlassPanelColorPackage colorPkg6 = GlassPanel.getGlassPanelColorPackageInstance(
    			shapeColor6,
    			shapeColorVel6,
    			bkgColor6,
    			bkgColorVel6
    			); 
    	
    	this.gp6 = new GlassPanel(6,controller, colorPkg4);
    	gbc.fill = GridBagConstraints.VERTICAL;
    	//c.ipady = GLASSPANE_HEIGHT;
//    	c.ipady = GLASSPANE_WIDTH;
  //  	c.ipadx =GLASSPANE_HEIGHT;
    	gbc.gridwidth = 2;
    	gbc.gridheight = 2;
    	gbc.weightx = .5;
    	gbc.weighty = .5;
    	gbc.gridx = 1;
    	gbc.gridy = 1;
    	contentPane.add(gp6,gl);
    	
 ***/   	
    	Vector3f bkgColorVel1 = new Vector3f(-1.17f, 1.34f, -1.51f);
    	
    	GlassPanel.GlassPanelColorPackage colorPkg1 = GlassPanel.getGlassPanelColorPackageInstance(
    			shapeColor1,
    			shapeColorVel1,
    			bkgColor1,
    			bkgColorVel1
    			);  	
    	
    	this.gp1 = new GlassPanel(1,controller, colorPkg1);    	
    	gbc.fill = GridBagConstraints.VERTICAL;
    	//c.ipady = GLASSPANE_HEIGHT;
//    	c.ipady = GLASSPANE_WIDTH;
  //  	c.ipadx =GLASSPANE_HEIGHT;
    	gbc.gridwidth = 2;
//    	c.gridheight = 4;
    	gbc.gridheight = 2;
    	gbc.weightx = .5;
    	gbc.weighty = .5;
    	gbc.gridx = 0;
    	gbc.gridy = 0;
    	
    	contentPane.add(gp1,gl);
    	contentPane.addKeyListener(new AllActionListeners() {
    		@Override
    		public void keyPressed(KeyEvent e) {
    			if(e.getKeyChar() == KeyEvent.VK_P)
    				controller.toggleToParrellelView(gp2.getPanelID());
    		}@Override
    		public void mouseClicked(MouseEvent e) {
    			// TODO Auto-generated method stub
    			int mark = 5;
    			mark = 9;
    			super.mouseClicked(e);
    		}
    		
    	});
    	
/***    	
    	//View 2
    	float[] shapeColor2 = {150, 255, 125, 255, 150, 255 };
    	Vector3f shapeColorVel2 = new Vector3f(-1f, 1f, -1f);
    	Vector3f bkgColorVel2 = new Vector3f(0f, 2f, 2f);
    	
    	GlassPanel.GlassPanelColorPackage colorPkg2 = GlassPanel.getGlassPanelColorPackageInstance(
    			shapeColor2,
    			shapeColorVel2,
    			bkgColor2,
    			bkgColorVel2
    			); 
    	
    	this.gp2 = new GlassPanel(2,controller, colorPkg2);
    	gbc.fill = GridBagConstraints.VERTICAL;
    	//c.ipady = GLASSPANE_HEIGHT;
//    	c.ipady = GLASSPANE_WIDTH;
  //  	c.ipadx =GLASSPANE_HEIGHT;
    	gbc.gridwidth = 2;
    	gbc.gridheight = 2;
    	gbc.weightx = .5;
    	gbc.weighty = .5;
    	gbc.gridx = 1;
    	gbc.gridy = 0;
    	contentPane.add(gp2,gl);
    	
    	//View 3
    	float[] shapeColor3 = {0, 255, 100, 255, 0, 127 };
    	Vector3f shapeColorVel3 = new Vector3f(4f, -4f, 4f);
    	Vector3f bkgColorVel3 = new Vector3f(2f, 2f, 2f);
    	
    	GlassPanel.GlassPanelColorPackage colorPkg3 = GlassPanel.getGlassPanelColorPackageInstance(
    			shapeColor3,
    			shapeColorVel3,
    			bkgColor3,
    			bkgColorVel3
    			); 
    	
    	this.gp3 = new GlassPanel(3,controller, colorPkg3);
    	gbc.fill = GridBagConstraints.VERTICAL;
    	//c.ipady = GLASSPANE_HEIGHT;
//    	c.ipady = GLASSPANE_WIDTH;
  //  	c.ipadx =GLASSPANE_HEIGHT;
    	gbc.gridwidth = 2;
    	gbc.gridheight = 2;
    	gbc.weightx = .5;
    	gbc.weighty = .5;
    	gbc.gridx = 0;
    	gbc.gridy = 1;
    	contentPane.add(gp3,gl);
    	
    	//View 7
    	float[] shapeColor7 = {50, 255, 150, 255, 250, 255 };
    	Vector3f shapeColorVel7 = new Vector3f(-2f, -3f, -2f);
    	Vector3f bkgColorVel7 = new Vector3f(-2f, -2f, -2f);
    	
    	GlassPanel.GlassPanelColorPackage colorPkg7 = GlassPanel.getGlassPanelColorPackageInstance(
    			shapeColor7,
    			shapeColorVel7,
    			bkgColor7,
    			bkgColorVel7
    			); 
    	
    	this.gp7 = new GlassPanel(7,controller, colorPkg4);
    	gbc.fill = GridBagConstraints.VERTICAL;
    	//c.ipady = GLASSPANE_HEIGHT;
//    	c.ipady = GLASSPANE_WIDTH;
  //  	c.ipadx =GLASSPANE_HEIGHT;
    	gbc.gridwidth = 2;
    	gbc.gridheight = 2;
    	gbc.weightx = .5;
    	gbc.weighty = .5;
    	gbc.gridx = 1;
    	gbc.gridy = 1;
    	contentPane.add(gp7,gl);
    	
    	//View 8
    	float[] shapeColor8 = {127.5f, 255, 255, 255, 12.5f, 255 };
    	Vector3f shapeColorVel8 = new Vector3f(-4f, -2f, -4f);
    	Vector3f bkgColorVel8 = new Vector3f(0f, -2f, -2f);
    	
    	GlassPanel.GlassPanelColorPackage colorPkg8 = GlassPanel.getGlassPanelColorPackageInstance(
    			shapeColor8,
    			shapeColorVel8,
    			bkgColor8,
    			bkgColorVel8
    			); 
    	
    	this.gp8 = new GlassPanel(8,controller, colorPkg4);
    	gbc.fill = GridBagConstraints.VERTICAL;
    	//c.ipady = GLASSPANE_HEIGHT;
//    	c.ipady = GLASSPANE_WIDTH;
  //  	c.ipadx =GLASSPANE_HEIGHT;
    	gbc.gridwidth = 2;
    	gbc.gridheight = 2;
    	gbc.weightx = .5;
    	gbc.weighty = .5;
    	gbc.gridx = 1;
    	gbc.gridy = 1;
    	contentPane.add(gp8,gl);
    	
    	//View 9
    	float[] shapeColor9 = {127.5f, 255, 0f, 255, 127.5f, 255 };
    	Vector3f shapeColorVel9 = new Vector3f(2f, 4f, 2f);
    	Vector3f bkgColorVel9 = new Vector3f(2f, -2f, -2f);
    	
    	GlassPanel.GlassPanelColorPackage colorPkg9 = GlassPanel.getGlassPanelColorPackageInstance(
    			shapeColor9,
    			shapeColorVel9,
    			bkgColor9,
    			bkgColorVel9
    			); 
    	
    	this.gp9 = new GlassPanel(9,controller, colorPkg4);
    	gbc.fill = GridBagConstraints.VERTICAL;
    	//c.ipady = GLASSPANE_HEIGHT;
//    	c.ipady = GLASSPANE_WIDTH;
  //  	c.ipadx =GLASSPANE_HEIGHT;
    	gbc.gridwidth = 2;
    	gbc.gridheight = 2;
    	gbc.weightx = .5;
    	gbc.weighty = .5;
    	gbc.gridx = 1;
    	gbc.gridy = 1;
    	contentPane.add(gp9,gl);
    	
    	***/
    	
    	this.pack();
    	this.setVisible(true);
		
	}

	private void setupControlPanel() {
		
		GridLayout gl = new GridLayout(3,1);
	//	cp.setLayout(new GridBagLayout());
		JTextField inX;
		cp.setLayout(gl);
		GridBagConstraints c = new GridBagConstraints();
		inX = new JTextField("put X bla bla here");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
    	c.weighty = 1/24;
    	c.gridx = 0;
    	c.gridy = 0;
    	this.cp.add(inX,gl);
    	
    	JTextArea inY = new JTextArea("put Y bla bla here");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
    	c.weighty = 1/24;
    	c.gridx = 0;
    	c.gridy = 1;
    	c.insets = new Insets(10,0,0,0);
    	this.cp.add(inY,gl);
    	
    	JTextArea inZ = new JTextArea("put Z bla bla here");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
    	c.weighty = 1/24;
    	c.gridx = 0;
    	c.gridy = 2;
    	c.insets = new Insets(10,0,0,0);
    	this.cp.add(inZ,gl);
	
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		flashRedraw = true;
		this.repaint();
	}
	
	@Override
	public void paint(Graphics g) {		
		super.paint(g);
	}
    
	@Override
	public void setController(ControllerGlassInt controller){
		this.controller = controller;
	}

	@Override
	public Dimension getViewPortDimensions() {
		return gp1.getSize();
	}
	
	@Override
	public void startRepaintFlashTimer(){
		this.timer.start();
	}

	public class ControlPanel extends JPanel {
		
		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D)g;
			
			g2.setColor(Color.CYAN);
			g2.fill(new Rectangle2D.Double(0,0,1000,1000));
		}
	}
	
}
