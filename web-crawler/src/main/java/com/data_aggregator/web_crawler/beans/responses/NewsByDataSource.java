package com.data_aggregator.web_crawler.beans.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsByDataSource {
    private String newsSourceUrl;
    private Integer newsFeedCount;
    private List<NewsInfo> newsInfoList;
}
