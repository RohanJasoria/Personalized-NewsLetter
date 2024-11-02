package com.data_aggregator.web_crawler.beans.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrawlRequest {
    private List<String> sourceLists;
}
