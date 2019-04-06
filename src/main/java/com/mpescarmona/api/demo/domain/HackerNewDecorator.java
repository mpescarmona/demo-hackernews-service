package com.mpescarmona.api.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class HackerNewDecorator {
    private String id;
    private String objectId;
    private String newTitle;
    private String author;
    private String createdTimestamp;
}
