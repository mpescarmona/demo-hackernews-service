package com.mpescarmona.api.demo.domain.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Hits {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant created_at;
    private String objectID;
    private String story_title;
    private String author;
}
