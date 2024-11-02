package com.data_aggregator.web_crawler.beans.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsLettersRequest {
    private String id;
    private List<String> sourceUrls;
}
