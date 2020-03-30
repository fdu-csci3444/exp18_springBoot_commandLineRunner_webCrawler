# web crawler Spring boot commandline project
Uses jSoup to parse http response via jQuery selectors to crawl site specified by below 
url in src/resources/application.properties
crawler.url.topics=https://www.cochranelibrary.com/cdsr/reviews/topics

It 1st "crawls" to above topics page extracting each topic and its topicUrl.
2nd it crawls to do a search for each topic extracted above.
3rd from above search result page, it extracts URL, TITLE, AUTHOR, DATE for each article.
Finally it prints the aggregate list of article details in console.

NOTE it only extracts results from 1st page of search results for each topic.
If there are multiple pages of results, it does NOT go to other pages of search of a topic other than 1st one.

This is a Spring boot app that implements CommandLineRunner, so it does not start up 
a web container(tomcat). It just executes CommandLineRunner interface's run method.

The "mvn install" build of it creates self executable JAR in target dir of project.

## to clone the project
```bash
cd toWhereEverYouWantToClone
git clone https://github.com/fdu-csci3444/exp18_springBoot_commandLineRunner_webCrawler.git
```

## to run with log level of debug in STS to see more log messages
uncomment below line in src/resources/application.properties 
logging.level.exp18=debug  

## to run in STS
- right click project
  - select "Run As" "Spring Boot App"
  or
  - select "Run As" "Java Application" 

## to build the self executable JAR of the project in STS under target dir
- right click project
  - select "Run As" "Maven install"

## to build the self executable JAR of the project via maven in command line under target dir
```bash
cd whereEverTheProjectDirIs
mvn install
```

## to run it from command line
```bash
cd whereEverTheProjectDirIs
java -jar target\exp18_springBoot_commandLineRunner_webCrawler-0.0.1-SNAPSHOT.jar
```
