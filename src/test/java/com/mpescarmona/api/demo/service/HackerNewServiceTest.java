package com.mpescarmona.api.demo.service;

import com.mpescarmona.api.demo.domain.HackerNew;
import com.mpescarmona.api.demo.domain.HackerNewDecorator;
import com.mpescarmona.api.demo.repository.HackerNewRepository;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.mpescarmona.api.demo.service.HackerNewService.buildHackeNewDecoratorFromHackerNew;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
public class HackerNewServiceTest {
    @InjectMocks
    private HackerNewService hackerNewService;

    @Mock
    private HackerNewRepository mockHackerNewRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        hackerNewService = new HackerNewService(mockHackerNewRepository);
    }

    @Test
    public void getAllActiveHackerNewsByCreatedTimestampDesc() throws Exception {
        HackerNew hackerNew1 = HackerNew.builder()
                .newTitle("New number one")
                .objectId("12345")
                .author("John Doe")
                .createdTimestamp(Instant.now().minusSeconds(60))
                .active(true)
                .build();
        HackerNew hackerNew2 = HackerNew.builder()
                .newTitle("New number two")
                .objectId("54321")
                .author("Peter Smith")
                .createdTimestamp(Instant.now().minusSeconds(40))
                .active(true)
                .build();

        List<HackerNew> expectedHackerNews = Arrays.asList(hackerNew2, hackerNew1);
        List<HackerNewDecorator> expextedHackerNewDecorators = expectedHackerNews.stream()
                .map(hackerNew -> buildHackeNewDecoratorFromHackerNew(hackerNew))
                .collect(Collectors.toList());

        given(mockHackerNewRepository.findByActiveIsTrueOrderByCreatedTimestampDesc())
                .willReturn(expectedHackerNews);

        List<HackerNewDecorator> hackerNewsDecorators = hackerNewService
                .getAllActiveHackerNewsByCreatedTimestampDesc();

        MatcherAssert.assertThat(hackerNewsDecorators, Matchers.is(expextedHackerNewDecorators));
    }

    @Test
    public void deactivateHackeNewById() throws Exception {
        HackerNew hackerNew = HackerNew.builder()
                .newTitle("New number one")
                .objectId("12345")
                .author("John Doe")
                .createdTimestamp(Instant.now().minusSeconds(60))
                .active(true)
                .build();

        given(mockHackerNewRepository.findByObjectIdAndActiveIsTrue(any(String.class)))
                .willReturn(hackerNew);

        hackerNewService.deactivateHackeNewById("12345");

        verify(mockHackerNewRepository, times(1)).findByObjectIdAndActiveIsTrue(any());
        verify(mockHackerNewRepository, times(1)).save(any());
    }

    @Test
    public void addHackerNews() throws Exception {
        HackerNew hackerNew1 = HackerNew.builder()
                .newTitle("New number one")
                .objectId("12345")
                .author("John Doe")
                .createdTimestamp(Instant.now().minusSeconds(60))
                .active(true)
                .build();
        HackerNew hackerNew2 = HackerNew.builder()
                .newTitle("New number two")
                .objectId("54321")
                .author("Peter Smith")
                .createdTimestamp(Instant.now().minusSeconds(40))
                .active(true)
                .build();

        List<HackerNew> newHackerNews = Arrays.asList(hackerNew1, hackerNew2);

        given(mockHackerNewRepository.findByObjectId(any(String.class))).willReturn(null);

        hackerNewService.addHackerNews(newHackerNews);

        verify(mockHackerNewRepository, times(2)).findByObjectId(any());
        verify(mockHackerNewRepository, times(2)).save(any());
    }
}