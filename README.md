
/***


***** Header *****

May 29th, 2016
Author : Mark Mills - The Ohio State University BS of Electrical & Computer Engineering
Email: mark.mills472@gmail.com


***** Summary *****

This is GlassPane.

This Program was originally written by Mark Mills starting in the Spring of 2013 as a visualizer for Sinusoidal and Linear Functions in the R^3 domain. It evolved to include other dimensions - such as color, transparency, static and dynamic image rendering, etc ...

The potential to link, analyze, manipulate, and visualize streamed data sets through timed correlation will be utilized in future versions of code. Currently the program is still in the Alpha stages as there is no GUI or Terminal interfaces. Most of the manipulations and visualizations of the steamed data are completed by setting global and local variables. However, there is currently a need for more RAM or cache memory as most streamed data sets exceed current Heap Space availability.

 
***** Current Available Features *****

GlassPane can currently :

    1) Capture and manipulate frames of images using transformations, linear algebra, and periodic behavior.

    2) Store, calculate on, and display data points with float precision vectors and matrices.

    3) Render points on a 2D projection of float precision vectors by providing XY pixel coordinates of each stored vector. With this information - regular geometric shapes like Polygons, Ellipses, and Periodic functions can be painted on pixels and spaces around each point.

    4) Provide data snapshots in millisecond intervals of dynamic data that can be played and analyzed in a stream of movie-like formats with snapshots taken at different frame rates. A greater amount of heap space and cache availability on better hardware would increase the rates at which this data can be analyzed.

    5) Import and cache different file types of data on the Application's launch.

    6) Create and display multiple simulations of imported data using any number of views.


***** Refactoring Needs *****

    1) Time needs to be spent breaking up methods to consolidate repeated code and for timing optimization.

    2) A rewrite, completion, and correction of mathematical transformation errors and non-optimized code in the following ModelGlass.java methods :
        a) Map<Vector3f, Shape> getAllCurrentCamera2DScreenProjections( Dimension drawingBoundsForPort, int id )
        b) Map<Vector3f, Shape> getAllCurrentParallel2DScreenProjections( Dimension viewPortDimensions, int id )
        c) Map<Vector3f, Shape> getAllCurrentCamera2DScreenProjections( Dimension drawingBoundsForPort, int glassPaneId, List<Matrix4x4f> transforms ) 
        

***** Features to be Added *****

    1) Frequency analyzer and visualization engine for streamed and stored data.

    2) A set of Correlation Engines that trigger events when user specified conditions are met in analyzed data.

    3) The ability to analyze, calculate on, and display live data streamed via feeds of XML and JSON data over internet.

    4) A SortingMachine helper class that can be customized to quickly sort different Collections like Arrays, ArrayLists, LinkedLists, etc ...

    5) The ability to simulate natural growth using a 3D version of Conway's Game of Life Algorithm. 

    6) An integration of optimized data structures that is currently stored in old college work.

    7) An integration of other visualization projects previously worked on - like MatrixMath and FunShapes - that have optimized models for triangular mesh and surface rendering. These projects also contain code that adds customizable gradients for shading and highlighting.

    8) General Application options available via a GUI - like import / export / a dashboard / parameter box / frequency display / etc ...

    9) Keyboard and Mouse interaction with hotkeys and shortcuts for quickly changing parameters and simulation settings.

    10) API's for Virtual Reality and Augmented Reality products like Oculus Rift and Google Glasses.

    11) Implementations of the ViewGlassInt.java interface. These Views would utilize different Graphics libraries other than java.awt - like OpenGL libraries used for hardware configured and optimized for Android devices.

    12) Cloud - or offsite - hosting of the product. 

    13) XML and JSON data export through a hosted REST service.

    14) Implementations of the ControllerGlassInt.java interface for the adoption of different input devices.

    15) Implementations of the ModelGlassInt.java interface that can run specified modules.

    16) Terminal interface and documentation.

    17) Online support forums, documentation, and a quick start set of coded examples.

    18) Registration and compliance with GPLv3 licensing.

    19) API's and interfaces that allow for drag & drop interaction with media libraries from sites like Facebook, Flickr, Instagram, etc ...

    20) Integration of automated atomic and module testing.

    21) Integration of automated builds through services like Jenkins.

    22) Integration of a project management tool - like Maven, Gradle, etc ...

    23) A distribution system / package download, management, and installation system though an online GUI interface - as well as through systems like Homebrew, Yum, etc ...


***** Plans for Growth and Development ***** 

Currently, I - (Mark Mills) - am the only developer working on GlassPane. There is the need for the following :

    1) More developers / community.

    2) Venture capital for hardware and dedicated developers.

    3) Examples that demonstrate the product's capabilities and relevance to fields like Big Data Analysis, Data Science, Trading, Electrical Engineering, General STEM Education, and Computer Graphics.

    4) I will be pursuing a Masters in Information and Data Science from The University of California, Berkeley in order to better understand the direction of the product.

    5) I will be pitching this product to Engineering and Development teams at Facebook during the Summer of 2016

    6) Marketing and cost of product MUST be determined by a Third Party or paid dedicated employee that specializes in such matters.

    7) A focus MUST be placed on scalability, ease of use, and stable architecture of the product and Engineering processes.

    8) Although the product will be used for financial gain - the core principle behind the Engineering efforts MUST be to promote usage among students and faculty at University.


***/

