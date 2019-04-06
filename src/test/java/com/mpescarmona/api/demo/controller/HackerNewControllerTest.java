package com.mpescarmona.api.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mpescarmona.api.demo.domain.HackerNew;
import com.mpescarmona.api.demo.domain.HackerNewDecorator;
import com.mpescarmona.api.demo.service.HackerNewService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.mpescarmona.api.demo.service.HackerNewService.buildHackeNewDecoratorFromHackerNew;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class HackerNewControllerTest {

    @Mock
    private HackerNewService mockHackerNewService;

    @InjectMocks
    private HackerNewController hackerNewController;


    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private String orderNumber = "1293712";

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        hackerNewController = new HackerNewController(mockHackerNewService);
        mockMvc = MockMvcBuilders.standaloneSetup(hackerNewController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void getAllActiveHackerNews() throws Exception {
        HackerNew hackerNew1 = HackerNew.builder()
                .newTitle("New number one")
                .id("id-1")
                .objectId("12345")
                .author("John Doe")
                .createdTimestamp(Instant.now().minusSeconds(60))
                .active(true)
                .build();
        HackerNew hackerNew2 = HackerNew.builder()
                .id("id-2")
                .objectId("54321")
                .author("Peter Smith")
                .newTitle("New number two")
                .createdTimestamp(Instant.now().minusSeconds(40))
                .active(true)
                .build();

        List<HackerNew> expectedHackerNews = Arrays.asList(hackerNew2, hackerNew1);
        List<HackerNewDecorator> expextedHackerNewDecorators = expectedHackerNews.stream()
                .map(hackerNew -> buildHackeNewDecoratorFromHackerNew(hackerNew))
                .collect(Collectors.toList());

        when(mockHackerNewService.getAllActiveHackerNewsByCreatedTimestampDesc()).thenReturn(expextedHackerNewDecorators);

        MockHttpServletResponse response = getAllHackerNews();
        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        verify(mockHackerNewService, times(1)).getAllActiveHackerNewsByCreatedTimestampDesc();
        List<HackerNewDecorator> hackerNewResponse = objectMapper.readValue(response.getContentAsString(), List.class);
        assertThat(hackerNewResponse.size(), is(2));
    }

    @Test
    public void deactivateHackeNewById() throws Exception {
        MockHttpServletResponse response = deactivateHackerNew("12345");
        assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));
        verify(mockHackerNewService, times(1)).deactivateHackeNewById("12345");
    }

    private MockHttpServletResponse getAllHackerNews() throws Exception {
        return mockMvc.perform(
                get("/hacker-news")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
    }

    private MockHttpServletResponse deactivateHackerNew(String hackerNewId) throws Exception {
        return mockMvc.perform(
                delete("/hacker-news/" + hackerNewId))
                .andReturn().getResponse();
    }
}