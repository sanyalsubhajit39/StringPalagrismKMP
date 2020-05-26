# String Palagrism Ckecker
USING KMP ALGORITHM 

Aim of the Project:

This project was aimed at designing a system that will be able to detect the possible act of plaigiarism By referencing a source file as our dataset and if the programme finds a match with the input currently passing by then it detects palagrism and notes it. We used one existing classical string matching algoritm KMP.

Installation for vsCode

1. Create a JAVA project in Code
2. Copy entire pool of documents in a folder named "DataSet_SourceFiles", and include the path of this directory in MClass.java on line no    34, example:---> final File folder = new File("j:\\DataSet_SourceFiles");
3. Include the test file on the line no 59 of the MClass.java, example:---> File inputFile = new File( "input.txt");


Flow of the project:

1. The source code has many java classes which are:--> MClass.java, KMP.java 
   a) MClass.java : Main java file.
   b) KMP.java : Has the KMP algorithm implementation.
2. Please run the MClass.java file, it will take the input file and test for plaigarism with respect to
   the files in the document pool.
3. The result of the plaigarism is shown on the console, at the same time the results are also written back
   on the result files:--> kmp.txt  
