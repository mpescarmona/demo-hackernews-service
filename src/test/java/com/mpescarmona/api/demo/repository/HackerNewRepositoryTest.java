package com.mpescarmona.api.demo.repository;

import com.mpescarmona.api.demo.domain.HackerNew;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = HackerNewRepositoryTest.EmbeddedMongoConfig.class)

public class HackerNewRepositoryTest {

    @Autowired
    HackerNewRepository hackerNewRepository;

    @Before
    public void setUp() throws Exception {
        hackerNewRepository.deleteAll();
    }

    @Test
    public void findByActiveIsTrueOrderByCreatedTimestampDesc() throws Exception {
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
        HackerNew hackerNew3 = HackerNew.builder()
                .newTitle("New number three")
                .objectId("11111")
                .author("Mike Spencer")
                .createdTimestamp(Instant.now().minusSeconds(10))
                .active(false)
                .build();
        hackerNewRepository.save(hackerNew1);
        hackerNewRepository.save(hackerNew2);
        hackerNewRepository.save(hackerNew3);

        List<HackerNew> hackerNews = hackerNewRepository.findByActiveIsTrueOrderByCreatedTimestampDesc();

        assertThat(hackerNews, hasSize(2));
        assertThat(hackerNews.get(0), is(hackerNew2));
        assertThat(hackerNews.get(1), is(hackerNew1));
    }

    @Test
    public void findByObjectIdAndActiveIsTrue() throws Exception {
        HackerNew hackerNew1 = HackerNew.builder()
                .newTitle("New number one")
                .objectId("12345")
                .author("John Doe")
                .createdTimestamp(Instant.now().minusSeconds(60))
                .active(true)
                .build();
        HackerNew hackerNew2 = HackerNew.builder()
                .newTitle("New number three")
                .objectId("11111")
                .author("Mike Spencer")
                .createdTimestamp(Instant.now().minusSeconds(10))
                .active(false)
                .build();
        hackerNewRepository.save(hackerNew1);
        hackerNewRepository.save(hackerNew2);

        HackerNew foundHackerNew1 = hackerNewRepository.findByObjectIdAndActiveIsTrue("12345");
        HackerNew foundHackerNew2 = hackerNewRepository.findByObjectIdAndActiveIsTrue("11111");

        assertThat(foundHackerNew1, is(hackerNew1));
        assertNull(foundHackerNew2);
    }

    @Test
    public void findByIdAndActiveIsTrue() throws Exception {
        HackerNew hackerNew1 = HackerNew.builder()
                .newTitle("New number one")
                .id("id-1")
                .objectId("12345")
                .author("John Doe")
                .createdTimestamp(Instant.now().minusSeconds(60))
                .active(true)
                .build();
        HackerNew hackerNew2 = HackerNew.builder()
                .newTitle("New number three")
                .id("id-2")
                .objectId("11111")
                .author("Mike Spencer")
                .createdTimestamp(Instant.now().minusSeconds(10))
                .active(false)
                .build();
        hackerNewRepository.save(hackerNew1);
        hackerNewRepository.save(hackerNew2);

        HackerNew foundHackerNew1 = hackerNewRepository.findByIdAndActiveIsTrue("id-1");
        HackerNew foundHackerNew2 = hackerNewRepository.findByIdAndActiveIsTrue("id-2");

        assertThat(foundHackerNew1, is(hackerNew1));
        assertNull(foundHackerNew2);
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableMongoRepositories(basePackages = "com.mpescarmona.api.demo.repository")
    static class EmbeddedMongoConfig {
    }

}