# web crawler Spring boot commandline project
uses jSoup to parse http response via jQuery selectors to crawl site specified by below 
url in src/resources/application.properties
crawler.url.topics=https://www.cochranelibrary.com/cdsr/reviews/topics

## to clone the project
cd <toWhereEverYouWantToClone>
git clone https://github.com/fdu-csci3444/exp18_springBoot_commandLineRunner_webCrawler.git

## to run with log level of debug
- uncomment below line in src/resources/application.properties
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
cd <whereEverTheProjectDirIs>
mvn install

## to run it from command line
cd <whereEverTheProjectDirIs>
java -jar target\exp18_springBoot_commandLineRunner_webCrawler-0.0.1-SNAPSHOT.jar

