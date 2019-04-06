package com.mpescarmona.api.demo.service;

import com.mpescarmona.api.demo.domain.HackerNew;
import com.mpescarmona.api.demo.domain.HackerNewDecorator;
import com.mpescarmona.api.demo.repository.HackerNewRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
@Slf4j
@AllArgsConstructor
public class HackerNewService {

    @Autowired
    private HackerNewRepository hackerNewRepository;

    public static HackerNewDecorator buildHackeNewDecoratorFromHackerNew(HackerNew hackerNew) {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                        .withZone(ZoneOffset.UTC);

        return HackerNewDecorator.builder()
                .id(hackerNew.getId())
                .author(hackerNew.getAuthor())
                .objectId(hackerNew.getObjectId())
                .newTitle(hackerNew.getNewTitle())
                .createdTimestamp(formatter.format(hackerNew.getCreatedTimestamp()))
                .build();
    }

    public List<HackerNewDecorator> getAllActiveHackerNewsByCreatedTimestampDesc() {
        log.info("action=getAllActiveHackerNewsByCreatedTimestampDesc");
        List<HackerNew> hackerNews = hackerNewRepository.findByActiveIsTrueOrderByCreatedTimestampDesc();
        return hackerNews.stream()
                .map(hackerNew -> buildHackeNewDecoratorFromHackerNew(hackerNew))
                .collect(Collectors.toList());
    }

    public void deactivateHackeNewById(String hackerNewId) {
        log.info("action=deactivateHackeNewById, hackerNewId={}", hackerNewId);
        HackerNew foundHackerNew = hackerNewRepository.findByObjectIdAndActiveIsTrue(hackerNewId);

        if (foundHackerNew != null) {
            foundHackerNew.setActive(false);
            hackerNewRepository.save(foundHackerNew);
        }
    }

    public void addHackerNews(List<HackerNew> hackerNews) {
        log.info("action=addHackerNews, hackerNews={}", hackerNews);
        hackerNews.stream()
                .filter(hackerNew -> hackerNewRepository.findByObjectId(hackerNew.getObjectId()) == null)
                .forEach(hackerNew -> hackerNewRepository.save(hackerNew));
    }
}
