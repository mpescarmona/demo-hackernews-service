package com.mpescarmona.api.demo.scheduler;

import com.mpescarmona.api.demo.domain.HackerNew;
import com.mpescarmona.api.demo.domain.response.HackerNewServerResponse;
import com.mpescarmona.api.demo.domain.response.Hits;
import com.mpescarmona.api.demo.service.HackerNewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class HackerNewScheduler {

    private final long TRIGGER_PERIOD_IN_MILLISECONDS = 60000;
    @Value("${hackernews.baseUrl}")
    private String hackerNewsBaseUrl;
    @Value("${hackernews.searchString}")
    private String hackerNewssearchString;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HackerNewService hackerNewService;

    @Scheduled(fixedRate = TRIGGER_PERIOD_IN_MILLISECONDS)
    public void getNewPostsFromHackerNews() {
        log.info("action=getNewPostsFromHackerNews, hackerNewsUri={}", hackerNewsBaseUrl + hackerNewssearchString);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<HackerNewServerResponse> response = restTemplate.exchange(hackerNewsBaseUrl + hackerNewssearchString, HttpMethod.GET, entity, HackerNewServerResponse.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                HackerNewServerResponse hackerNewServerResponse = response.getBody();
                List<HackerNew> hackerNews = new ArrayList<>();
                for (Hits hits :
                        hackerNewServerResponse.getHits()) {
                    hackerNews.add(buildHackerNewFromHit(hits));
                }
                hackerNewService.addHackerNews(hackerNews);
            }

            log.info("action=getNewPostsFromHackerNews, result={}", response);
        } catch (Exception ex) {
            log.error("action=getNewPostsFromHackerNews, error={}", ex.getMessage());
        }

    }

    private HackerNew buildHackerNewFromHit(Hits hits) {
        return HackerNew.builder()
                .author(hits.getAuthor())
                .newTitle(hits.getStory_title())
                .objectId(hits.getObjectID())
                .active(true)
                .createdTimestamp(hits.getCreated_at())
                .build();
    }
}
