package com.mpescarmona.api.demo.scheduler;

import org.awaitility.Duration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HackerNewSchedulerTest {

    @SpyBean
    private HackerNewScheduler hackerNewScheduler;

    @Test
    public void getNewPostsFromHackerNews() throws Exception {
        await().atMost(Duration.ONE_MINUTE).untilAsserted(() -> {
            verify(hackerNewScheduler, atLeast(1)).getNewPostsFromHackerNews();
        });
    }
}