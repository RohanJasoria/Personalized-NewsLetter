package com.data_aggregator.web_crawler.services;

import com.data_aggregator.web_crawler.beans.requests.CrawlRequest;
import com.data_aggregator.web_crawler.beans.requests.NewsLettersRequest;
import com.data_aggregator.web_crawler.beans.responses.NewsByDataSource;
import com.data_aggregator.web_crawler.beans.responses.NewsInfo;
import com.data_aggregator.web_crawler.beans.responses.NewsLettersResponse;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@Slf4j
public class WebCrawlerService {
    private final WebDriver webDriver;

    @Autowired
    public WebCrawlerService(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public List<String> fetchHtmlContent(CrawlRequest crawlRequest) throws IOException {
        if (crawlRequest.getSourceLists().isEmpty()) {
            return null;
        }
        List<String> titles = crawlRequest.getSourceLists().parallelStream().map(source -> {
            // Connect to the URL and parse the HTML content
            Document document = null;
            try {
                document = Jsoup.connect(source).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Return the page's title or the entire HTML content
            return document.title(); // or document.html();
        }).toList();
        return titles;
    }

    public NewsLettersResponse fetchJavaScriptContent(NewsLettersRequest newsLettersRequest) throws InterruptedException {
        NewsLettersResponse newsLettersResponse = new NewsLettersResponse();

        if (isNull(newsLettersRequest) || isNull(newsLettersRequest.getSourceUrls())) {
            return newsLettersResponse;
        }

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        log.info("driver initialized");
        List<NewsByDataSource> newsByDataSourceList = newsLettersRequest.getSourceUrls().stream().map(url -> {
            NewsByDataSource newsByDataSource = new NewsByDataSource();
            newsByDataSource.setNewsSourceUrl(url);

            List<NewsInfo> newsInfoList = new ArrayList<>();

            // Navigate to the provided URL
            webDriver.get(url);
            log.info("Navigating to URL: " + url);

            // Wait for the page to load completely
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

            List<WebElement> titles = webDriver.findElements(By.className("container__headline-text"));

            for (WebElement headlineElement : titles) {
                NewsInfo newsInfo = new NewsInfo();
                newsInfo.setTitle(headlineElement.getText());
                newsInfoList.add(newsInfo);
            }

            newsByDataSource.setNewsInfoList(newsInfoList);
            newsByDataSource.setNewsFeedCount(newsInfoList.size());
            return newsByDataSource;
        }).collect(Collectors.toList());

        webDriver.quit();
        newsLettersResponse.setNewsByDataSources(newsByDataSourceList);
        return newsLettersResponse;
    }
}
