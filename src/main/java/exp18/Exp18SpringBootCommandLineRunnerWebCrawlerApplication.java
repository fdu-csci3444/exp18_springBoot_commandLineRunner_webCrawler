package exp18;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import exp18.service.CrawlerService;
import exp18.service.CrawlerService.Topic;

@SpringBootApplication
//public class Exp18SpringBootCommandLineRunnerWebCrawlerApplication  {
public class Exp18SpringBootCommandLineRunnerWebCrawlerApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(Exp18SpringBootCommandLineRunnerWebCrawlerApplication.class);

	@Autowired
	private CrawlerService crawlerService;
	
	@Value("${crawler.url.topics}")
	private String url4topics;
	
	public static void main(String[] args) {
		SpringApplication.run(Exp18SpringBootCommandLineRunnerWebCrawlerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("BEF run");
		crawlerService.stayingAlive2BeeGees();
		
		List<Topic> topics2search = crawlerService.getTopics2search(url4topics);
		List<String> topicDetails = crawlerService.getTopicDetails(topics2search);
		for (String topicDetail : topicDetails) {
			System.out.println(topicDetail);			
		}
	}

}
