# A-Portuguese-Wine-browser---Assignment-2019
 WineType.java  -- a helper enum that provides a list of helpful constants describing the wine type (minor update to make it accessible from assignment2019 package, 10th April).</br>
 WineProperty.java  -- a helper enum that provides a list of helpful constants describing the wine properties of a wine sample (minor update to make it accessible from assignment2019 package, 10th April).
 WineSample.java  -- contains all the necessary member variables and methods to store a wine sample. This means that one row in the original CSV files provided will become a WineSample object in this project.
 Query.java  -- a class that represents one query (fixed a minor bug that was leading to inconsistent query outputs in some cases, 23rd April).
 QueryCondition.java  -- a class that specifies one of the conditions that are part of a Query object.
 AbstractWineSampleCellar.java  -- an abstract class that provides implementations of file reading utilities for the input datasets containing the wine samples and the query text file (no changes to the code - updated only the comment for methods highestAlcoholContent and lowestCitricAcid, 10th April).
 AbstractWineSampleBrowserPanel.java  -- an abstract class that provides the basic code you will need to build the GUI.
