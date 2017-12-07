# Pitest

### user instructions:

 * Go to pitest file, use ```mvn install``` to generate local repository
 * In the project need to be tested, also use ```mvn install``` download dependencies
 * Add the plugin to build/plugins in your pom.xml 
 ```xml
  <plugin>
    	<groupId>org.pitest</groupId>
    	<artifactId>pitest-maven</artifactId>
    	<version>1.2.5-SNAPSHOT</version>
  </plugin>
  ``` 

 * use ```mvn org.pitest:pitest-maven:mutationCoverage``` to execute the mutation, the mutation coverage goal analyses all classes in the codebase that match the target tests and target classes filters.




### Notice:

 * All the defualt mutators are customized
 * In the plugin, "1.2.5-SNAPSHOT" should be the name of the local repository generated in the first step


