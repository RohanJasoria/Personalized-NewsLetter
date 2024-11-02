package com.data_aggregator.web_crawler.beans.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsLettersResponse {
    private List<NewsByDataSource> newsByDataSources;
    private String exceptionMessage;
}
