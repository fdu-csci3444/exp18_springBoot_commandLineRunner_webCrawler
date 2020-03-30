package exp18.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
public class CrawlerService {
	private static final Logger logger = LoggerFactory.getLogger(CrawlerService.class);

	public static final String selector4topics = "dd[class='browse-by-category-content'] ul li a";

	public static final String selector4topic = "div[id='facetDisplaySection'] ul li ul li span a";
	public static final String selector4url = "div[class='search-results-item'] div[class='search-results-item-body'] h3 a";
	public static final String selector4title = "div[class='search-results-item'] div[class='search-results-item-body'] h3 a";
	public static final String selector4author = "div[class='search-results-item'] div[class='search-results-item-body'] div[class='search-result-authors'] div";
	public static final String selector4date = "div[class='search-results-item'] div[class='search-results-item-body'] div[class='search-result-metadata-block'] div[class='search-result-date'] div";
	
	public static class Topic {
		private String topic;
		private String topicUrl;
		public Topic(String topic, String topicUrl) {
			super();
			this.topic = topic;
			this.topicUrl = topicUrl;
		}
		public String getTopic() {
			return topic;
		}
		public String getTopicUrl() {
			return topicUrl;
		}
		@Override
		public String toString() {
			return "Topic [topic=" + topic + ", topicUrl=" + topicUrl + "]";
		}	
	}
	
	public List<Topic> getTopics2search(String url4topics) throws IOException {
		List<Topic> topics2search = null;
		
		if (url4topics != null && !StringUtils.isEmpty(url4topics)) {
			topics2search = new ArrayList<Topic>();
			String topic, topicUrl;
			Topic topic2search;
			Document doc = Jsoup.connect(url4topics).get();
			Elements elements4topic = doc.select(selector4topics);  
			for (Element element4topic : elements4topic) {  
			    topic = element4topic.text(); 
			    topicUrl = element4topic.attr("href");
			    topic2search = new Topic(topic, topicUrl);
				topics2search.add(topic2search);
			}  
		}
		
		return topics2search;
	}

	public List<String> getTopicDetails(List<Topic> topics2search) throws IOException {
		List<String> topicDetails = null;
		
		if (topics2search != null) {
			topicDetails = new ArrayList<String>();
			String topic, topicUrl, url, title, author, date;
			List<String> urls = new ArrayList<String>();
			List<String> titles = new ArrayList<String>();
			List<String> authors = new ArrayList<String>();
			List<String> dates = new ArrayList<String>();

			for (Topic topic2search: topics2search) {
				 topic = topic2search.topic;
				 topicUrl = topic2search.topicUrl;
				 
				 Document doc = Jsoup.connect(topicUrl).get(); 			 
				 // get urls
				 Elements elements4url = doc.select(selector4url);
				 logWarning4elementsIsEmpty(elements4url, topicUrl, selector4url);
				 logger.info("--> found {} urls for topic:{}", elements4url.size(), topic);
				 for (Element element4url : elements4url) {  
				    url = element4url.attr("href"); 
					urls.add(url);
				 }  
				 // get titles
				 Elements elements4title = doc.select(selector4title);  
				 for (Element element4title : elements4title) {  
				    title = element4title.text(); 
					titles.add(title);
				 }  
				 // get authors
				 Elements elements4author = doc.select(selector4author);  
				 for (Element element4author : elements4author) {  
				    author = element4author.text(); 
					authors.add(author);
				 }  
				 // get dates
				 Elements elements4date = doc.select(selector4date);  
				 for (Element element4date : elements4date) {  
				    date = element4date.text(); 
					dates.add(date);
				 }  
				 
			}					 
			
			// output the results
			StringBuilder sb;
			for (int i = 0; i < urls.size(); i++) {
			   sb = new StringBuilder("URL:").append(urls.get(i)).append(", TITLE:").append(titles.get(i)).append(", AUTHOR:").append(authors.get(i)).append(", DATE:").append(dates.get(i));
			   logger.debug(sb.toString());
			   topicDetails.add(sb.toString());
			}
			logger.info(" EOF found {} results", topicDetails.size());
		}
		
		return topicDetails;
	}
	
	public List<String> getTopicDetails_2(List<String> topics2search) throws IOException {
		List<String> topicDetails = null;
		
		if (topics2search != null) {
			topicDetails = new ArrayList<String>();
			String url4search, topic2search, topic, url, title, author, date;
			List<String> urls = new ArrayList<String>();
			List<String> titles = new ArrayList<String>();
			List<String> authors = new ArrayList<String>();
			List<String> dates = new ArrayList<String>();

			for (String topic2searchRaw: topics2search) {
				topic2search = URLEncoder.encode(topic2searchRaw);
				 //url4search = "https://www.cochranelibrary.com/en/search?_scolarissearchresultsportlet_WAR_scolarissearchresults_displayText=Cancer&_scolarissearchresultsportlet_WAR_scolarissearchresults_searchText=Cancer&_scolarissearchresultsportlet_WAR_scolarissearchresults_searchType=basic&_scolarissearchresultsportlet_WAR_scolarissearchresults_facetQueryField=topic_id&_scolarissearchresultsportlet_WAR_scolarissearchresults_searchBy=13&_scolarissearchresultsportlet_WAR_scolarissearchresults_orderBy=displayDate-true&_scolarissearchresultsportlet_WAR_scolarissearchresults_facetDisplayName=Cancer&_scolarissearchresultsportlet_WAR_scolarissearchresults_facetQueryTerm=z1209270504325056240433870825568&_scolarissearchresultsportlet_WAR_scolarissearchresults_facetCategory=Topics";
//				 url4search = "https://www.cochranelibrary.com/en/search?_scolarissearchresultsportlet_WAR_scolarissearchresults_displayText=" + topic2search + "&_scolarissearchresultsportlet_WAR_scolarissearchresults_searchText=" + topic2search + "&_scolarissearchresultsportlet_WAR_scolarissearchresults_searchType=basic&_scolarissearchresultsportlet_WAR_scolarissearchresults_facetQueryField=topic_id&_scolarissearchresultsportlet_WAR_scolarissearchresults_searchBy=13&_scolarissearchresultsportlet_WAR_scolarissearchresults_orderBy=displayDate-true&_scolarissearchresultsportlet_WAR_scolarissearchresults_facetDisplayName=" + topic2search + "&_scolarissearchresultsportlet_WAR_scolarissearchresults_facetQueryTerm=z1209270504325056240433870825568&_scolarissearchresultsportlet_WAR_scolarissearchresults_facetCategory=Topics";
				 url4search = "https://www.cochranelibrary.com/en/search?p_p_id=scolarissearchresultsportlet_WAR_scolarissearchresults&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_count=1&_scolarissearchresultsportlet_WAR_scolarissearchresults_displayText=" + topic2search + "&_scolarissearchresultsportlet_WAR_scolarissearchresults_searchText=" + topic2search + "&_scolarissearchresultsportlet_WAR_scolarissearchresults_searchType=basic&_scolarissearchresultsportlet_WAR_scolarissearchresults_facetQueryField=topic_id&_scolarissearchresultsportlet_WAR_scolarissearchresults_searchBy=13&_scolarissearchresultsportlet_WAR_scolarissearchresults_orderBy=displayDate-true&_scolarissearchresultsportlet_WAR_scolarissearchresults_facetDisplayName=" + topic2search + "&_scolarissearchresultsportlet_WAR_scolarissearchresults_facetQueryTerm=z1209270504325056240433870825568&_scolarissearchresultsportlet_WAR_scolarissearchresults_facetCategory=Topics";
				 
				 Document doc = Jsoup.connect(url4search).get(); 			 
				 // get urls
				 Elements elements4url = doc.select(selector4url);
				 logWarning4elementsIsEmpty_2(elements4url, topic2searchRaw, topic2search, url4search, selector4url);
				 logger.info("--> found {} urls for topic2searchRaw:{}, topic2search:{}", elements4url.size(), topic2searchRaw, topic2search);
				 for (Element element4url : elements4url) {  
				    url = element4url.attr("href"); 
					urls.add(url);
				 }  
				 // get titles
				 Elements elements4title = doc.select(selector4title);  
				 for (Element element4title : elements4title) {  
				    title = element4title.text(); 
					titles.add(title);
				 }  
				 // get authors
				 Elements elements4author = doc.select(selector4author);  
				 for (Element element4author : elements4author) {  
				    author = element4author.text(); 
					authors.add(author);
				 }  
				 // get dates
				 Elements elements4date = doc.select(selector4date);  
				 for (Element element4date : elements4date) {  
				    date = element4date.text(); 
					dates.add(date);
				 }  
				 
			}					 
			
			// output the results
			StringBuilder sb;
			for (int i = 0; i < urls.size(); i++) {
			   sb = new StringBuilder("URL:").append(urls.get(i)).append(", TITLE:").append(titles.get(i)).append(", AUTHOR:").append(authors.get(i)).append(", DATE:").append(dates.get(i));
			   logger.debug(sb.toString());
			   topicDetails.add(sb.toString());
			}
			logger.info(" EOF found {} results", topicDetails.size());
		}
		
		return topicDetails;
	}

	private void logWarning4elementsIsEmpty(Elements elements, String topicUrl, String selector) {
		 if (elements.isEmpty()) logger.warn("Empty elements for topicUrl:{}, selector:{}", topicUrl, selector);
	}
	private void logWarning4elementsIsEmpty_2(Elements elements, String topic2searchRaw, String topic2search, String url, String selector) {
		 if (elements.isEmpty()) logger.warn("Empty elements for topic2searchRaw:{}, topic2search:{}, selector:{}, url:{}", topic2searchRaw, topic2search, selector, url);
	}
	
	public void stayingAlive2BeeGees() {
		logger.debug("STAYING ALIVE, STAYING ALIVE");
	}
}
