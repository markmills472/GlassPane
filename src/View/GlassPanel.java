package View;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Controller.ControllerGlassInt;
import Helpers.AllActionListeners;
import Helpers.Vector3f;

public class GlassPanel extends JPanel {
		
		Map<Vector3f,Shape> allShapesToDraw;
		int panelID; // ****** MUST BE UNIQUE FOR EVERY PANEL **** //
		static ControllerGlassInt controller;
		int framesToKeepPaint = 1;
		boolean showFarthestFirst = false;
		GlassPanelColorPackage colorPkg = null;
		private String[][] frameDirectory;
		Map<Integer,BufferedImage[]> buffImgArray = null;
		
		// Set Movie import configurations
		int numBuffImgsStoredInEachArrayOfImages = 100; // 2479;
		int numScenesInCurrentLoad = 1;
		
		int frameReadCounterIncrement = 5;
		
		//For drawing images and morphing them
		int totalIterationCount = 0;
		float [] framesPerIterationClipN = { 20f, 20f, 20f }; // Rate to import images ( counter for reading images -- 1 == every picture -- 2 == every other -- etc... )
		float [] currentFrameForClipN = { 1f, 1f, 1f }; // Changes image file read during any given frame
		
		
//		Graphics2D globalGraphics;
			
		public GlassPanel(int panelNum, ControllerGlassInt controller){
			
			this.panelID = panelNum;
			this.controller = controller;
			this.frameDirectory = new String[this.numScenesInCurrentLoad][2];
			this.buffImgArray = new HashMap<Integer,BufferedImage[]> ();
			
			for( int i = 0 ; i < this.numScenesInCurrentLoad; i++ ) {
				this.buffImgArray.put(i,
						new BufferedImage[this.numBuffImgsStoredInEachArrayOfImages]);
			}

			populateVideoDirectories();
			// Load all background images into memory first
			uploadAllImagesTo2DArrayOfSceneFrames();
			AllActionListeners listener = new AllActionListeners(){
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getID() == KeyEvent.VK_P){
						GlassPanel.controller.toggleToParrellelView(panelID);
					}
				}
			};
			this.addKeyListener(listener);
			
		}
		
		public GlassPanel(int panelNum, ControllerGlassInt controller, GlassPanelColorPackage colorPkg){
			
			this.panelID = panelNum;
			this.controller = controller;
			this.frameDirectory = new String[this.numScenesInCurrentLoad][2];
			this.buffImgArray = new HashMap<Integer,BufferedImage[]> ();
			
			for( int i = 0 ; i < this.numScenesInCurrentLoad; i++ ) {
				this.buffImgArray.put(i,
						new BufferedImage[this.numBuffImgsStoredInEachArrayOfImages]);
			}
			
			populateVideoDirectories();
			// Load all background images into memory first
			uploadAllImagesTo2DArrayOfSceneFrames();
						
			AllActionListeners listener = new AllActionListeners(){
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getID() == KeyEvent.VK_P){
						GlassPanel.controller.toggleToParrellelView(panelID);
					}
				}
			};
			this.colorPkg = colorPkg;
			this.addKeyListener(listener);
			
		}
		
		private void populateVideoDirectories() {
			
			this.frameDirectory[0][0] = "res/homeVideo1-ImageSequence/IMG_0";
				this.frameDirectory[0][1] = ".jpg";
//			this.frameDirectory[1][0] = "res/seizureVideo-ImageSequence/IMG_0";
//				this.frameDirectory[1][1] = ".jpg";
//			this.frameDirectory[1][0] = "res/homeVideo2-ImageSequence/IMG_0";
//				this.frameDirectory[1][1] = ".jpg";
//			this.frameDirectory[1][0] = "res/teamAwesome/Team_Awesome_Huddle-ImageSequence/IMG_0";
//				this.frameDirectory[1][1] = ".jpg";
				
		}
		
		private void uploadAllImagesTo2DArrayOfSceneFrames() {
			
			if( this.buffImgArray.size() != 0 || this.buffImgArray.get(0).length != 0 ) {
				
				for ( int j = 0 ; j < this.numScenesInCurrentLoad ; j++ ) {
					fillBuffImg2DArray( j, this.frameDirectory[j][0], this.frameDirectory[j][1]);
				}	
				
			}
			
		}

		private void fillBuffImg2DArray( int directoryIndex, String firstHalfFileLocIntro, String secondHalfTimeDelayFileType ) {

			synchronized (this) {
				
				// :: *** NOTE *** ::: MUST ALWAYS BE TRUE ::: framesPerIterationClipN.length == this.currentFrameForClipN.length ::
				for ( int n = 0 ; n < this.currentFrameForClipN.length ; n++ ) {
					
					float currentFrameForClip = this.currentFrameForClipN[n];
					
					for ( int i = 1  ; i <= this.numBuffImgsStoredInEachArrayOfImages ; i++ ) {
							
						Image rawImg;
						
						
							try {
								
								String fileNumString = Integer.toString((int) ( currentFrameForClip % this.numBuffImgsStoredInEachArrayOfImages ) );
								
								while ( fileNumString.length() != 4 ) fileNumString = "0" + fileNumString;
									rawImg = ImageIO.read(new File(firstHalfFileLocIntro + fileNumString + secondHalfTimeDelayFileType ));
								
								BufferedImage buffImg = new BufferedImage(rawImg.getWidth(null),
										rawImg.getHeight(null),
										BufferedImage.TYPE_INT_ARGB);
								
								buffImg.getGraphics().drawImage(rawImg, 0, 0, null);
									
								// Create a buffered image with transparency
								this.buffImgArray.get(directoryIndex)[i-1] = buffImg;
				
							} catch (IOException e) {
								
								System.out.println(e.getMessage());
								
								// Fill all missed slots with nulls to save space
								this.buffImgArray.get(directoryIndex)[i] = null;
								
								if( e.getMessage().equalsIgnoreCase( "Can't read input file!" ) ) {
									System.out.println( "Img Loc : " + i + " -- Might not be an error" );
								} else {
									e.printStackTrace();
								}
			
							}
						}						
					}
				}
			
			return;
			
		}

		public void setColorPackage(GlassPanelColorPackage colorPkg){
			this.colorPkg = colorPkg;
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			
			synchronized (this) {
				
				Graphics2D g2 = (Graphics2D)g;
				
				updateColorVBO();
				updateBackgroundColor();
				
				boolean flashRedraw = ViewGlass.flashRedraw;
				AffineTransform originalTransform = g2.getTransform();
				
				float [] imgNums = new float [ this.numScenesInCurrentLoad ];
				for ( int i = 0 ; i < imgNums.length ; i++ ) {
					imgNums[i] = this.currentFrameForClipN[i] % this.buffImgArray.get(i).length;
				}
				
				BufferedImage [] buffImgs = new BufferedImage [ this.numScenesInCurrentLoad ];
				for ( int i = 0 ; i < buffImgs.length ; i++ ) {
					buffImgs[i] = getCurrentImgAfterIteration(i);
				}

				doFamilyMovieRenderings( g2, imgNums, buffImgs);
				
				AlphaComposite ac = java.awt.AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f );
	
				g2.setComposite(ac);
				
				if(controller == null)
					controller = ViewGlass.controller;
				else if(flashRedraw){
					allShapesToDraw = controller.getVectorsToProjMapToDraw(panelID);		
					Iterator<Vector3f> centerOfDrawnShapes = allShapesToDraw.keySet().iterator();
	/***
					SortingMachine<Vector3f> layerOrder = new SortingMachine1L<Vector3f>(new Comparator<Vector3f>() {
								@Override
								public int compare(Vector3f v1, Vector3f v2) {
									int retVal;
									if(showFarthestFirst){
										retVal = -1;
										if(v1.length() > v2.length())
											retVal = 1;
										else if(v1.length() == v2.length())
											retVal = 0;
									} else {
										retVal = 1;
										if(v1.length() > v2.length())
											retVal = -1;
										else if(v1.length() == v2.length())
											retVal = 0;
									}
									return retVal;
								}
					});
					
					while(centerOfDrawnShapes.hasNext()){
						layerOrder.add(centerOfDrawnShapes.next());	
					}
					layerOrder.changeToExtractionMode();
					Iterator<Vector3f> farthestFirst = layerOrder.iterator();
					
					while(farthestFirst.hasNext()){
						Vector3f center = farthestFirst.next();					
	 ***/
					
					while(centerOfDrawnShapes.hasNext()){
						Vector3f center = centerOfDrawnShapes.next();
						if(allShapesToDraw.get(center) instanceof Ellipse2D.Double){
							Vector3f colorTrnsFrmResult = new Vector3f();
							/* A(x) = 255.0/(5.0)*center.x()+255.0/(2.0)  		-5 <= x,y <= 5
							 * A(y) = 255.0/(5.0)*center.y()+255.0/(2.0)			22.5 <= z <= 27.5
							 * A(z) = 255.0/(5.0)*center.z()-255.0*22.5/(5.0)		diameter = (5.0)
							 */
							colorTrnsFrmResult.setX((float)(255.0 - Math.abs(center.x() - colorPkg.shapeColorVBO[0]) * 255.0/colorPkg.shapeColorVBO[1]));
							colorTrnsFrmResult.setY((float)(255.0 - Math.abs(center.y() - colorPkg.shapeColorVBO[2]) * 255.0/colorPkg.shapeColorVBO[3]));
							colorTrnsFrmResult.setZ((float)(255.0 - Math.abs(center.z() - colorPkg.shapeColorVBO[4]) * 255.0/colorPkg.shapeColorVBO[5]));
							if(colorTrnsFrmResult.x() >= 0 && colorTrnsFrmResult.y() >= 0 && colorTrnsFrmResult.z() >= 0 &&
									colorTrnsFrmResult.x() < 256 && colorTrnsFrmResult.y() < 256 && colorTrnsFrmResult.z() < 256){
									g2.setColor(new Color((int)colorTrnsFrmResult.x(),(int)colorTrnsFrmResult.y(),(int)colorTrnsFrmResult.z()));
							}
							else if(center.z() < 0) // virtual image
								g2.setColor(Color.BLACK);
							else //just far into picture
								g2.setColor(Color.WHITE);
							g2.setTransform(AffineTransform.getRotateInstance(totalIterationCount, this.getWidth() / 2.0f , this.getHeight() / 2.0f ) );
							g2.setTransform(AffineTransform.getTranslateInstance(
									this.getWidth() / 2.0f + (this.getWidth() / (totalIterationCount % 7.0f) ) * Math.cos(totalIterationCount),
									this.getHeight() / 2.0f + (this.getHeight() / (totalIterationCount % 7.0f) ) * Math.sin(totalIterationCount) ) );
	
						}
						else{
							g2.setColor(Color.RED);
							g2.fill(allShapesToDraw.get(center));
						}
					}
					
					flashRedraw = false;
					
					// reset COUNT OF TOTAL FLASH DRAWINGS ( this.totalIterationCount ) - when count == 1 Million
					if ( this.totalIterationCount > 1000000) {
						this.totalIterationCount = totalIterationCount % 1000000;
					}
					
				}
			}
			
			this.totalIterationCount++;
			
		}

		private void doFamilyMovieRenderings( Graphics2D g2, float[] imgNums, BufferedImage[] buffImgs) {
			
			ImageObserver observer = new ImageObserver() {
				@Override
				public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
					// TODO On Update do something ???
					return false;
				}
			};
			
			g2.drawImage( buffImgs[0],
					0, 0,
					this.getWidth(), this.getHeight(),
					observer);
			
//			rotateImage( g2,
//					( 2.0f * Math.PI * ( imgNums[0] % 64.0f ) ) / ( 64.0f ),
//					this.getWidth() / 2,
//					this.getHeight() / 2,
//					buffImgs[0],
//					observer); // Center rotation		
//			rotateImage( g2,
//					- ( 2.0f * Math.PI * ( imgNums[0] % 64.0f ) ) / ( 64.0f ),
//					this.getWidth() / 2,
//					this.getHeight() / 2,
//					buffImgs[0],
//					observer); // Center rotation		
			
			// Add TEAM AWESOME TRANSFORMS !!!
//			teamAwesomeTransform1( 2, buffImgs[2], g2, observer, 100,
//					(float) (2.0f * Math.PI / 64.0f),
//					(float) (2.0f * Math.PI / 64.0f));
			
			// Transform Image by Pixels
			
//			float numSphereSections = 2f;
//			for ( float theta = 0f ; theta < 2.0f * (float) Math.PI ; theta += 2.0f * (float) Math.PI / numSphereSections ) {
//				for ( float phi = 0f ; phi < 2.0f * (float) Math.PI ; phi += 2.0f * (float) Math.PI / numSphereSections ) {
//					addPixelatedImage( ( this.currentDirectoryIndexNum + 1 ) % this.numArrays,
//							imgNum2, g2, observer, 1,
//							(float) ( 8.0f * Math.sin(theta) * Math.cos(phi) ), (float) ( 8.0f * Math.sin(theta) * Math.sin(phi) ),
//							this.totalIterationCount / 64.0f, this.totalIterationCount / 64.0f);
//				}
//			}
			
			// Convex Rect Layers
//			addConvexLayers( ( this.currentDirectoryIndexNum + 1 ) % ( this.numScenesInCurrentLoad ), imgNum3, g2, observer );

			// Concave Rect Layers
//			addConcaveLayers( ( this.currentDirectoryIndexNum ) % ( this.numScenesInCurrentLoad ), imgNum2, g2, observer );

		}

		private BufferedImage getCurrentImgAfterIteration(int directoryIndex) {
			
			float currentFrame = this.currentFrameForClipN[directoryIndex];
			if( currentFrame >= (this.numBuffImgsStoredInEachArrayOfImages - 1) ) {
				
				this.currentFrameForClipN[directoryIndex] = currentFrame;
				
				fillBuffImg2DArray( directoryIndex,
						this.frameDirectory[directoryIndex][0], this.frameDirectory[directoryIndex][1]);
				
				currentFrame = 0; // Loop Hack
				
			}
			
			BufferedImage buffImg = this.buffImgArray.get(directoryIndex)[(int) currentFrame];
			if( buffImg == null) {
				currentFrame = 0;
			}
			
			buffImg = this.buffImgArray.get(directoryIndex)[(int) currentFrame];
			this.currentFrameForClipN[directoryIndex] += this.framesPerIterationClipN[directoryIndex];
			
			
			return buffImg;
			
		}

		private void rotateImage( Graphics2D g2, double theta, int rotationPointX, int rotationPointY, BufferedImage buffImg, ImageObserver observer) {
			
			AffineTransform trans = new AffineTransform();
			trans.setTransform( new AffineTransform() );
			trans.rotate( theta );
			g2.drawImage(buffImg, trans, observer);
			g2.rotate(theta, rotationPointX, rotationPointY);
			trans.setTransform( new AffineTransform() );
			
		}

		private void addConvexLayers( int directoryIndex, int imgNum , Graphics2D g2, ImageObserver observer ) {
			
			BufferedImage graphicsBuffImg = this.buffImgArray.get(directoryIndex)[imgNum];
			float currentFrame = this.currentFrameForClipN[directoryIndex];
			while( graphicsBuffImg == null) {
				imgNum = imgNum % this.buffImgArray.get(0).length;
				directoryIndex = ( directoryIndex ) % ( this.numScenesInCurrentLoad );
				graphicsBuffImg = this.buffImgArray.get(directoryIndex)[imgNum];
			}
			
//			for ( float phi = 0 ; phi <= 2.0 * Math.PI ; phi += 2.0 * Math.PI/ 8.0f ) {
//				int numCircleSections = (int) ( directoryIndex % 8) ;
//				int numPoints = (int) Math.abs( 4 * numCircleSections * Math.sin(phi) );
//				
//				float radiusMultiplier = 2.5f;
//				float xWidth = this.currentFrame % 3.5f ;
//				float yHeight = this.currentFrame % 3.5f ;
//				
//				drawCircularPatternWithBufferedImageFromSceneNum(
//						directoryIndex,
//						g2,
//						graphicsBuffImg,
//						observer,
//						numCircleSections,
//						numPoints,
//						radiusMultiplier,
//						this.getWidth() / 0.75f,
//						this.getHeight() / 0.75f);
//				
//			}
			
			
		}

		private void addConcaveLayers(int directoryIndex, int imgNum, Graphics2D g2, ImageObserver observer) {
			
			BufferedImage graphicsBuffImg = this.buffImgArray.get(directoryIndex)[imgNum];
			while( graphicsBuffImg == null) {
				imgNum = imgNum % this.buffImgArray.get(imgNum).length;
				graphicsBuffImg = this.buffImgArray.get( directoryIndex )
								[imgNum % this.numBuffImgsStoredInEachArrayOfImages];
			}
			
			for ( float phi = 0 ; phi <= 2.0 * Math.PI ; phi += 2.0 * Math.PI/ 12.0f ) {
				int numCircleSections = (int) ( imgNum % 12 );
				int numPoints = (int) Math.abs(( numCircleSections * Math.cos(phi) ));
				float radiusMultiplier = 1.5f;
//				float xWidth = this.totalIterationCount % 1.5f / 1.5f;
//				float yHeight = this.totalIterationCount % 1.5f / 1.5f;
				
//				drawRectPatternWithBufferedImageFromSceneNum(
//						graphicsSceneNum,
//						g2,
//						graphicsBuffImg,
//						observer,
//						numCircleSections,
//						numPoints,
//						radiusMultiplier,
//						this.getWidth() / 16.0f,
//						this.getHeight() / 16.0f);
				
				drawCircularPatternWithBufferedImageFromSceneNum(
						directoryIndex,
						g2,
						graphicsBuffImg,
						observer,
						numCircleSections,
						numPoints,
						radiusMultiplier,
						this.getWidth() / 8.0f,
						this.getHeight() / 8.0f);
				
			}
			
		}

		private void addTransformOfImageByImageSection(
				int directoryIndex, int imgNum, Graphics2D g2,
				ImageObserver observer,
				int samplingInterval, int numThetaSectionsMod, int numPhiSectionsMod ,
				float xMultiplier, float yMultiplier ) {
			
			float currentFrame = this.currentFrameForClipN[directoryIndex];
			BufferedImage graphicsBuffImg = this.buffImgArray.get(directoryIndex)[imgNum];
			while( graphicsBuffImg == null) {
				imgNum = imgNum % this.buffImgArray.get(directoryIndex).length;
				directoryIndex = ( directoryIndex ) % ( this.numScenesInCurrentLoad );
				graphicsBuffImg = this.buffImgArray.get(directoryIndex)[imgNum];
			}
			
			float theta = 0;
			float phi = 0;
			float numThetaSections = numThetaSectionsMod;
			float numPhiSections = numPhiSectionsMod;
			for ( int x = 0 ; x < graphicsBuffImg.getWidth() ; x += samplingInterval) {
				
				for ( int y = 0 ; y < graphicsBuffImg.getHeight() ; y += samplingInterval) {
					
					int numCircleSections = (int) ( currentFrame % 8 ) ;
					int numPoints = (int) Math.abs( 4 * numCircleSections * Math.sin(phi) );
					
	//				float radiusMultiplier = 2.5f;
					int pixel = graphicsBuffImg.getRGB(x, y);
					
				    int red = (pixel >> 16) & 0xff;
				    int green = (pixel >> 8) & 0xff;
				    int blue = (pixel) & 0xff;
				    int alpha = (pixel >> 24) & 0xff;
				    
				    g2.setColor( new Color( red, green, blue, alpha ) );
				    g2.fill( new Ellipse2D.Float(
				    		(float) (this.getWidth() / 2.0f + xMultiplier * x * Math.sin(theta) * Math.cos(phi)),
				    		(float) (this.getHeight() / 2.0f + yMultiplier * y * Math.sin(theta) * Math.sin(phi)),
				    		samplingInterval, samplingInterval ) );
						
				}
				
				theta += 2.0 * Math.PI / (this.totalIterationCount % numThetaSections);
				phi += 2.0 * Math.PI / (this.totalIterationCount % numPhiSections);
				
			}
			
		}
		
		private void teamAwesomeTransform1(int directoryIndex,
				BufferedImage buffImg,
				Graphics2D g2,
				ImageObserver observer, int samplingInterval,
				float theta, float phi) {
			
			int imageWidth = this.getWidth();
			int imageHeight = this.getHeight();
			
			
			for ( int x = 0 ; x < buffImg.getWidth() ; x += samplingInterval) {
				
				for ( int y = 0 ; y < buffImg.getHeight() ; y += samplingInterval) {
					
					float radius = (float) Math.sqrt( Math.pow( x - imageWidth / 2.0f, 2 ) +
							Math.pow( y - imageHeight / 2.0f, 2 ) );
					
					int pixel = buffImg.getRGB(x, y);
					
				    int red = (pixel >> 16) & 0xff;
				    int green = (pixel >> 8) & 0xff;
				    int blue = (pixel) & 0xff;
				    int alpha = (pixel >> 24) & 0xff;
				    
				    g2.setColor( new Color( red, green, blue, alpha ) );
				    g2.fill( new Ellipse2D.Float(
				    		(float) ( this.getWidth() / 2.0f + 
				    				radius * Math.sin(theta) * Math.cos(phi) ),
				    		(float) ( this.getHeight() / 2.0f +
				    				radius * Math.sin(theta) * Math.sin(phi)),
//				    		samplingInterval, samplingInterval ) );
				    		5, 5 ) );
						
				}
				
			}
			
		}
		
		private void addPixelatedImage(int directoryIndex, int imgNum, Graphics2D g2,
				ImageObserver observer, int samplingInterval,
				float xDiv, float yDiv,
				float theta, float phi) {
			
			int graphicsSceneNum = ( directoryIndex ) % ( this.numScenesInCurrentLoad );
			float currentFrame = this.currentFrameForClipN[directoryIndex];
			BufferedImage graphicsBuffImg = this.buffImgArray.get(graphicsSceneNum)[imgNum];
			while( graphicsBuffImg == null) {
				imgNum = imgNum % this.buffImgArray.get(0).length;
				graphicsBuffImg = this.buffImgArray.get(graphicsSceneNum)[imgNum];
			}
			
			for ( int x = 0 ; x < graphicsBuffImg.getWidth() ; x += samplingInterval) {
				
				for ( int y = 0 ; y < graphicsBuffImg.getHeight() ; y += samplingInterval) {
					
					int pixel = graphicsBuffImg.getRGB(x, y);
					
				    int red = (pixel >> 16) & 0xff;
				    int green = (pixel >> 8) & 0xff;
				    int blue = (pixel) & 0xff;
				    int alpha = (pixel >> 24) & 0xff;
				    
				    g2.setColor( new Color( red, green, blue, alpha ) );
				    g2.fill( new Ellipse2D.Float(
				    		(float) ( this.getWidth() / 2.0f + (x / xDiv) * Math.sin(theta) * Math.cos(phi) ),
				    		(float) ( this.getHeight() / 2.0f + (y / yDiv) * Math.sin(theta) * Math.sin(phi)),
				    		samplingInterval, samplingInterval ) );
						
				}
				
			}
			
		}
		
		private void drawRectPatternWithBufferedImageFromSceneNum(int sceneNum,
				Graphics2D g2,
				BufferedImage buffImg,
				ImageObserver observer,
				int numCircleSections,
				int numPoints,
				float radiusMultiplier,
				float xWidth,
				float yHeight) {
			
			for ( int k = 0 ; k < numPoints ; k++ ) {
				
				// Should run total of 16 times
				for ( float xAdder = -1 * ( xWidth / 2.0f ) ; xAdder <= xWidth / 2.0f ; xAdder += xWidth ) {
					for ( float yAdder = -1 * ( yHeight / 2.0f ) ; yAdder <= yHeight / 2.0f ; yAdder += yHeight ) {
						
						// Four Image Corners (one image) for each of the polygons rendered
						int[] xRectCorners = new int[4];
						int[] yRectCorners = new int[4];
						
						int cornerCount = 0;
						
						// Four Points then draw image
						for ( float xFourth = -1 * ( this.getWidth() / 4.0f ) ; xFourth <= this.getWidth() / 2.0f ; xFourth += this.getWidth() / 2.0f ) {
							for ( float yFourth = -1 * ( this.getHeight() / 4.0f ) ; yFourth <= this.getHeight() / 2.0f ; yFourth += this.getHeight() / 2.0f ) {
			
								xRectCorners[cornerCount] =
										(int) ( this.getWidth() / 2.0f +
										xFourth +
										xAdder +
										(this.getWidth() / (4.0f)) *
										radiusMultiplier * Math.cos( k * 2.0 * Math.PI / numPoints) );
								
								yRectCorners[cornerCount] =
										(int) ( this.getHeight() / 2.0f +
										yFourth +
										yAdder +
										(this.getHeight() / (4.0f)) *
										radiusMultiplier * Math.sin( k * 2.0 * Math.PI / numPoints) );
								
								cornerCount++;
																	
							}
						}
				
						if( numCircleSections % 2 == 0 ) {
							g2.setColor( new Color ( Math.abs( 127.5f - colorPkg.backgroundColor.getRed() ) / 255.0f,
									Math.abs( 127.5f - colorPkg.backgroundColor.getGreen() ) / 255.0f,
									Math.abs( 127.5f - colorPkg.backgroundColor.getBlue() ) / 255.0f, 
									( totalIterationCount % 75.0f ) / 255.0f 
									)
									   );
						} else if( numCircleSections % 2 != 0 ) {
							g2.setColor( new Color ( Math.abs( 255.0f - 127.5f - colorPkg.backgroundColor.getRed() ) / 255.0f,
									Math.abs( 255.0f - 127.5f - colorPkg.backgroundColor.getGreen() ) / 255.0f,
									Math.abs( 255.0f - 127.5f - colorPkg.backgroundColor.getBlue() ) / 255.0f, 
									( totalIterationCount % 75.0f ) / 255.0f 
									)
									   );
						}
						
			//				g2.fill(new Polygon(x1,y1,numPoints));
						Polygon windowToDrawImage = new Polygon(
								xRectCorners,
								yRectCorners,
								4);
						
						Rectangle rect = windowToDrawImage.getBounds();
			//				AffineTransform centerTransform = AffineTransform.
			//		                getTranslateInstance(-rect.x+1, -rect.y+1);
			//				g2.setTransform(centerTransform);
						AffineTransform tx = AffineTransform.getRotateInstance(Math.sin(numCircleSections), this.getWidth() / 2.0f , this.getHeight() / 2.0f);
			//				AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);  
			//				buffImg = op.filter(buffImg, null);      
			//				Image scaledImg = buffImg.getScaledInstance( (int)  Math.abs(rect.getWidth()), (int) Math.abs(rect.getHeight()), Image.SCALE_SMOOTH);
						
						g2.setClip(windowToDrawImage);
						AlphaComposite ac = java.awt.AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
								(float) Math.abs(0.5f -  ( totalIterationCount % 75.0f ) / 255.0f - (Math.cos(numCircleSections) / (2.0 * Math.PI + totalIterationCount) ) ) );
					    g2.setComposite(ac);
					    g2.setTransform(tx);
						g2.drawImage(buffImg, rect.x, rect.y,  Math.abs(buffImg.getWidth()),  Math.abs(buffImg.getHeight()), observer);
			//				g2.drawRenderedImage(buffImg, at);;
						g2.setClip(windowToDrawImage);
			//				g2.drawImage(scaledImg, rect.x, rect.y, observer);
						g2.setStroke(new BasicStroke(1f));
						g2.setClip(null);
						g2.fill(windowToDrawImage);
			//				g2.setTransform(AffineTransform.getRotateInstance(-phi, 0, 0 ));
						
			//				g2.setTransform(originalTransform);
						
					}
						
				}
				
			}
		
		}

		private void drawCircularPatternWithBufferedImageFromSceneNum(int sceneNum,
				Graphics2D g2,
				BufferedImage buffImg,
				ImageObserver observer,
				int numCircleSections,
				int numPoints,
				float radiusMultiplier,
				float f, float g) {
			
			for ( float phi = 0 ; phi < 2.0 * Math.PI ; phi += 2.0*Math.PI/numCircleSections) {
				int[] x1 = new int[numPoints];
				int[] y1 = new int[numPoints];
				for ( int k = 0 ; k < numPoints ; k++ ) {
					x1[k] = (int) ( this.getWidth() / 4.0f +
//							(this.getWidth() / (2.0f + count % 2.0f)) *
							(this.getWidth() / (2.0f)) *
							radiusMultiplier * Math.cos( k * 2.0 *Math.PI / numPoints));
					y1[k] = (int) ( this.getHeight() / 4.0f +
//							(this.getHeight() / (2.0f + count % 2.0f)) *
							(this.getHeight() / (2.0f)) *
							radiusMultiplier * Math.sin( k * 2.0 *Math.PI / numPoints) );
				}
				
				if( numCircleSections % 2 == 0 ) {
					g2.setColor( new Color ( Math.abs( 127.5f - colorPkg.backgroundColor.getRed() ) / 255.0f ,
							Math.abs( 127.5f - colorPkg.backgroundColor.getGreen() ) / 255.0f ,
							Math.abs( 127.5f - colorPkg.backgroundColor.getBlue() ) / 255.0f , 
							( totalIterationCount % 75.0f ) / 255.0f 
							)
							   );
				} else if( numCircleSections % 2 != 0 ) {
					g2.setColor( new Color ( Math.abs( 255.0f - 127.5f - colorPkg.backgroundColor.getRed() ) / 255.0f ,
							Math.abs( 255.0f - 127.5f - colorPkg.backgroundColor.getGreen() ) / 255.0f ,
							Math.abs( 255.0f - 127.5f - colorPkg.backgroundColor.getBlue() ) / 255.0f , 
							( totalIterationCount % 75.0f ) / 255.0f 
							)
							   );
				}
				
//				g2.fill(new Polygon(x1,y1,numPoints));
				
				Polygon windowToDrawImage = new Polygon(x1,y1,numPoints);
				Rectangle rect = windowToDrawImage.getBounds();
//				AffineTransform centerTransform = AffineTransform.
//		                getTranslateInstance(-rect.x+1, -rect.y+1);
//				g2.setTransform(centerTransform);
				AffineTransform tx = AffineTransform.getRotateInstance(phi, this.getWidth() / 2.0f , this.getHeight() / 2.0f);
//				AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);  
//				buffImg = op.filter(buffImg, null);      
//				Image scaledImg = buffImg.getScaledInstance( (int)  Math.abs(rect.getWidth()), (int) Math.abs(rect.getHeight()), Image.SCALE_SMOOTH);
				
				g2.setClip(windowToDrawImage);
				AlphaComposite ac = java.awt.AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
						(float) Math.abs(0.5f -  ( totalIterationCount % 75.0f ) / 255.0f - (phi / (2.0 * Math.PI + totalIterationCount) ) ) );
			    g2.setComposite(ac);
			    g2.setTransform(tx);
				g2.drawImage(buffImg, rect.x, rect.y,  Math.abs(buffImg.getWidth()),  Math.abs(buffImg.getHeight()), observer);
//				g2.drawRenderedImage(buffImg, at);;
				g2.setClip(windowToDrawImage);
//				g2.drawImage(scaledImg, rect.x, rect.y, observer);
				g2.setStroke(new BasicStroke(1f));
				g2.setClip(null);
				g2.fill(windowToDrawImage);
//				g2.setTransform(AffineTransform.getRotateInstance(-phi, 0, 0 ));
				
//				g2.setTransform(originalTransform);
			}
			
		}
		
		private void updateBackgroundColor() {
			
			float red = colorPkg.backgroundColor.getRed();
			float green = colorPkg.backgroundColor.getGreen();
			float blue = colorPkg.backgroundColor.getBlue();
			
			Vector3f velocity = colorPkg.backgroundColorVelocity; 
			
			if(velocity.x() > 0 && red >= 255 ){
				velocity.setX(-1 * velocity.x());
				red = 255;
			} else if(velocity.x() < 0 && red <= 15 ){
				velocity.setX(-1 * velocity.x());
				red = 10;
			}
			
			if(velocity.y() > 0 && green >= 255 ){
				velocity.setY(-1 * velocity.y());
				green = 255;
			} else if(velocity.y() < 0 && green <= 15 ){
				velocity.setY(-1 * velocity.y());
				green = 10;
			}
			
			if(velocity.z() > 0 && blue >= 255 ){
				velocity.setZ(-1 * velocity.z());
				blue = 255;
			} else if(velocity.z() < 0 && blue <= 15 ){
				velocity.setZ(-1 * velocity.z());
				blue = 10;
			}
			
			red = red + velocity.x();
			green = green + velocity.y();
			blue = blue + velocity.z();
			
			if(red > 255.0f){
				red = 255.0f;
			} else if (red < 0){
				red = 0;
			}
			
			if(green > 255.0f){
				green = 255.0f;
			} else if (green < 0){
				green = 0;
			}
			
			if(blue > 255.0f){
				blue = 255.0f;
			} else if (blue < 0){
				blue = 0;
			}
			
			colorPkg.backgroundColor = new Color(red * 1.0f/255.0f, green * 1.0f/255.0f, blue * 1.0f/255.0f);
			
		}

		private void updateColorVBO() {
			
			float[] colorVBO = colorPkg.shapeColorVBO;
			Vector3f colorVBOVel = colorPkg.shapeColorVelocity;
			
			if(colorVBOVel.x() > 0 && colorVBO[0] >= 255 ){
				colorVBOVel.setX(-1 * colorVBOVel.x());
				colorVBO[0] = 255;
			} else if(colorVBOVel.x() < 0 && colorVBO[0] <= 15 ){
				colorVBOVel.setX(-1 * colorVBOVel.x());
				colorVBO[0] = 10;
			}
			
			if(colorVBOVel.y() > 0 && colorVBO[2] >= 255 ){
				colorVBOVel.setY(-1 * colorVBOVel.y());
				colorVBO[2] = 255;
			} else if(colorVBOVel.y() < 0 && colorVBO[2] <= 15 ){
				colorVBOVel.setY(-1 * colorVBOVel.y());
				colorVBO[2] = 10;
			}
			
			if(colorVBOVel.z() > 0 && colorVBO[4] >= 255 ){
				colorVBOVel.setZ(-1 * colorVBOVel.z());
				colorVBO[4] = 255;
			} else if(colorVBOVel.z() < 0 && colorVBO[4] <= 15 ){
				colorVBOVel.setZ(-1 * colorVBOVel.z());
				colorVBO[4] = 10;
			}
			
			colorVBO[0] = colorVBO[0] + colorVBOVel.x();
			colorVBO[2] = colorVBO[2] + colorVBOVel.y();
			colorVBO[4] = colorVBO[4] + colorVBOVel.z();
			
			colorPkg.shapeColorVBO = colorVBO;
		}

		public static BufferedImage deepCopy(BufferedImage bi) {
			 ColorModel cm = bi.getColorModel();
			 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
			 WritableRaster raster = bi.copyData(null);
			 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
		}
		
		public int getPanelID(){
			return panelID;
		}

		public static GlassPanelColorPackage getGlassPanelColorPackageInstance(float[] shapeColorVBO, 
				Vector3f shapeColorVelocity, Color backgroundColor, Vector3f backgroundColorVelocity){
			return new GlassPanelColorPackage(shapeColorVBO, shapeColorVelocity, backgroundColor, backgroundColorVelocity);
		}
		
		public static class GlassPanelColorPackage{
			
			//Shape Colors
			public float[] shapeColorVBO = {254, 255, 254 , 255 , 254, 255}; //color Virtual Buffer Object
			public Color backgroundColor = new Color(0f,0f,0f); // BLACK
			public Vector3f shapeColorVelocity = new Vector3f();
			public Vector3f backgroundColorVelocity = new Vector3f();	
			
			public GlassPanelColorPackage (float[] shapeColorVBO, Vector3f shapeColorVelocity, Color backgroundColor, Vector3f backgroundColorVelocity) {
				this.shapeColorVBO = shapeColorVBO;
				this.shapeColorVelocity = shapeColorVelocity;
				this.backgroundColor = backgroundColor;
				this.backgroundColorVelocity = backgroundColorVelocity;
			}
			
		}
		
}
