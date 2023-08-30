
# Pre-requisites
* Java 1.8
* Gradle 5.1

Step 1:  Run the below command after importing code to your machine

         gradle clean build

Step 2:  After step 1 geektrust.jar file will be crated in the file system. 
         You have to copy the relative path of this jar file under build and under libs.

        In my case my file path is = "build/libs/geektrust.jar"

        use forward slashes (/) as the directory separator for file paths instead of backslashes (\)

Step 3:  You will be giving input to the application through text file.
         You have to copy the absolute path of the input text file.
         I am attaching the reference of the file path in my case in your case it might vary.

         input file path= "C:/Users/Admin/Documents/input.txt".

Step 4:  You can run the application throght below line
         
         java -jar <jar file path> <absolute path of input file>

         example:

         java -jar build/libs/geektrust.jar C:/Users/Admin/Documents/input.txt

Step 5: Your programme will run with the desired output
