package com.data_aggregator.web_crawler.controller;

import com.data_aggregator.web_crawler.beans.requests.CrawlRequest;
import com.data_aggregator.web_crawler.beans.requests.NewsLettersRequest;
import com.data_aggregator.web_crawler.beans.responses.NewsLettersResponse;
import com.data_aggregator.web_crawler.services.WebCrawlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WebCrawlerController {
    private final WebCrawlerService webCrawlerService;

    // Endpoint to fetch static HTML content
    @GetMapping("/crawl-html")
    public List<String> crawlHtmlContent(@RequestBody CrawlRequest crawlRequest) {
        try {
            return webCrawlerService.fetchHtmlContent(crawlRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Endpoint to fetch JavaScript-rendered content
    @GetMapping("/crawl-js")
    public NewsLettersResponse crawlJavaScriptContent(@RequestBody NewsLettersRequest newsLettersRequest) {
        try {
            return webCrawlerService.fetchJavaScriptContent(newsLettersRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return NewsLettersResponse.builder().exceptionMessage("Error Fetching Data").build();
        }
    }
}

