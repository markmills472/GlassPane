import Controller.ControllerGlass;
import Controller.ControllerGlassInt;
import Model.ModelGlass;
import Model.ModelGlassInt;
import View.ViewGlass;
import View.ViewGlassInt;

//import src.main.java.org.bytedeco.javacv.FFmpegFrameGrabber;

/**
 * Runner File
 * 
 * @author Mark Mills
 * 
 * </pre>
 */
public final class GlassPaneRun {

    /**
     * Main program that sets up main application window and starts user
     * interaction.
     * 
     * @param args
     *            command-line arguments; not used
     */
    public static void main(String[] args) {
    	
    	// TODO -- Current attempt to parse movies into images below - need FFmpegFrameGrabber library ...
    	// ... or utility tool
//    	FFmpegFrameGrabber g = new FFmpegFrameGrabber("textures/video/anim.mp4");
//    	g.start();
//
//    	for (int i = 0 ; i < 50 ; i++) {
//    	    ImageIO.write(g.grab().getBufferedImage(), "png", new File("frame-dump/video-frame-" + System.currentTimeMillis() + ".png"));
//    	}
//
//    	g.stop();
     
    	
    	// Actual Working Code START
    	
        ModelGlassInt model = new ModelGlass();
        ViewGlassInt view = new ViewGlass();
        ControllerGlassInt controller = new ControllerGlass(model, view);
        view.setController(controller);
//        model.addPattern("SwirlPatternModule2");
        
        controller.begin();
//        
        //Actual Working Code STOP
    	
    	
//    	facebookInterviewRound1FIGHT();
//        
//    	printFibSeriesSumUpToAndIncluding4Million();
    	
    	
//    	int [] elements = { 4, 2, 6, 1, 7, 3, 0 };
//        int sumK = 7;
//        
//        for ( int i = 0 ; i < elements.length ; i++ ) {
//        
//          int currentElement = elements[i];
//          
//          for ( int k = i + 1 ; k < elements.length ; k++ ) {
//          
//            int comparedElement = elements[k];
//            
//            if ( comparedElement + currentElement == sumK ) {
//              
//              System.out.println( "Sum Matched to equal K = " + sumK + " ; Pair : Elem1 == " + currentElement + " -- Elem2 == " + comparedElement );
//              
//            }
//
//          }
//          
//          
//        }
    	
//    	int [] elements = { 0, 1, 2, 3, 4, 6, 7 };
//        int sumK = 7;
//        
//        // sort elements
//        int [] sortedElements = { 0, 1, 2, 3, 4, 6, 7 };
//        
//        for ( int i = 0 ; i < sortedElements.length ; i++ ) {
//        
//          int firstElement = sortedElements[i];
//          int lastElement = sortedElements[ sortedElements.length - 1 - i ];
//          
//          if ( firstElement != lastElement && firstElement < lastElement) {
//
//            if ( firstElement + lastElement == sumK ) {
//
//              System.out.println( "Sum Matched to equal K = " + sumK + " ; Pair : Elem1 == " + firstElement + " -- Elem2 == " + lastElement );
//
//            }  
//            
//          }
//          
//        }
        
//    	facebookInterview2WelcomeMessage();
        
    }
    
    /***
     * Facebook interview round 2 opening message
     * 
     * 		Interviewer : Deepak Lakshmanan
     * 		Date & Time : Friday, May 27th at 11:00 AM - 12:00 PM PDT
     */
	private static void facebookInterview2WelcomeMessage() {
		
		int highestStackLevel = 150;
		int currentStackLevel = 0;
		int velocity = 1;
		String message1 = "_|GO_FACEBOOK_DEV_SUPPORT|_";
		String message2 = "_|Hi_there_Deepkil|_";
		String openingChar = "*";
		String betweenFowardAndBackwardsLinePrint = "|._.|";
		String lineBreakMessage = " //._.\\\\ ";
		String closingMessage = " __H_E_L_L_A__P_U_M_P_E_D__| --";
		
		while ( currentStackLevel != highestStackLevel && currentStackLevel >= 0) {
			
			System.out.println(  "-- " );
			
			printFacebookOpeningInterviewMessageLineForward(
					highestStackLevel,
					currentStackLevel,
					velocity,
					message1,
					message2,
					openingChar,
					betweenFowardAndBackwardsLinePrint,
					lineBreakMessage,
					closingMessage);
			
			printFacebookOpeningInterviewMessageLineBackward(
					highestStackLevel,
					currentStackLevel,
					velocity,
					message1,
					message2,
					openingChar,
					betweenFowardAndBackwardsLinePrint,
					lineBreakMessage,
					closingMessage);
			
			System.out.println();
			
			if ( currentStackLevel == ( highestStackLevel - 1 ) && velocity > 0 ) {
//				||
//				( currentStackLevel == 0 && velocity < 0 ) ) {
			
			velocity *= -1;
			
		}
		
		currentStackLevel += velocity;
			
		}
		
		System.out.println( closingMessage );
			
	}

	/***
	 * Helper method to print Facebook message forward line for Interview 2 opener
	 * 
	 * @param highestStackLevel
	 * @param currentStackLevel
	 * @param velocity
	 * @param message1
	 * @param message2
	 * @param closingMessage 
	 * @param lineBreakMessage 
	 * @param betweenFowardAndBackwardsLinePrint 
	 * @param openingChar 
	 */
	private static void printFacebookOpeningInterviewMessageLineForward(int highestStackLevel,
			int currentStackLevel,
			int velocity,
			String message1,
			String message2,
			String openingChar,
			String betweenFowardAndBackwardsLinePrint,
			String lineBreakMessage,
			String closingMessage) {
		
		for ( int i = 0 ; i < currentStackLevel ; i++ ) {
			
			System.out.print( openingChar );
			
		}
		
		for ( int i = currentStackLevel ;
				( ( i < message1.length() - 2 ) && ( i < currentStackLevel + 1) ) ;
				i++ ) {
			
			System.out.print( message1.substring( i , i + 1) + " " );
			
		}
		
		System.out.print( betweenFowardAndBackwardsLinePrint );
		
		for ( int i = currentStackLevel ;
				( ( i < message2.length() - 2 ) && ( i < currentStackLevel + 1) ) ;
				i++ ) {
			
			System.out.print( message2.substring( i , i + 1) + " " );
			
		}
		
		System.out.print( lineBreakMessage );
		
	}

	/***
	 * Helper method to print Facebook message line backward for Interview 2 opener
	 * 
	 * @param highestStackLevel
	 * @param currentStackLevel
	 * @param velocity
	 * @param message1
	 * @param message2
	 * @param closingMessage 
	 * @param lineBreakMessage 
	 * @param betweenFowardAndBackwardsLinePrint 
	 * @param openingChar 
	 */
	private static void printFacebookOpeningInterviewMessageLineBackward(int highestStackLevel,
			int currentStackLevel,
			int velocity,
			String message1,
			String message2,
			String openingChar,
			String betweenFowardAndBackwardsLinePrint,
			String lineBreakMessage,
			String closingMessage) {
			
		for ( int i = currentStackLevel ;
				( ( i < message2.length() - 2 ) && ( i < currentStackLevel + 1) ) ;
				i++ ) {
			
			System.out.print( message2.substring( i , i + 1) + " " );
			
		}
		
		System.out.print( betweenFowardAndBackwardsLinePrint );
		
		for ( int i = currentStackLevel ;
				( ( i < message1.length() - 2 ) && ( i < currentStackLevel + 1) ) ;
				i++ ) {
			
			System.out.print( message1.substring( i , i + 1) + " " );
			
		}
			
		
		for ( int i = 0 ; i < currentStackLevel ; i++ ) {
			
			System.out.print( openingChar );
			
		}
		
		System.out.print( lineBreakMessage );
		
	}

	/***
	 *  Facebook interview round 1
	 */
    private static void facebookInterviewRound1FIGHT() {
    	
      for ( int i = 0 ; i < 100 ; i++ ) {
      	
      	if( i % 3 == 0) {
      		System.out.print("LOL");
      	}
      	
      	if ( i % 5 ==0 ) {
      		System.out.print("WAT");
      	}
      	
      	System.out.println();
      }
    	
    }
    
    /***
     *  Fib series sum of even values up to and including 4 million
     */
	private static void printFibSeriesSumUpToAndIncluding4Million() {
    	
      int evenSum = 0;
      int lastTerm = 0;
      int currentTerm = 1;
      int holder = 0;
      
      for ( int i = currentTerm ; i <= 4.0 * Math.pow(10, 6) ; i = currentTerm + lastTerm ) {
      	
      	holder = currentTerm;
      	currentTerm = currentTerm + lastTerm;
      	lastTerm = holder;
      	
      	if ( i % 2 == 0) {
      		evenSum += currentTerm;
      	}
      	     	
      }
      
      System.out.println("Sum of all even valued terms : " + evenSum );
		
	}

}
