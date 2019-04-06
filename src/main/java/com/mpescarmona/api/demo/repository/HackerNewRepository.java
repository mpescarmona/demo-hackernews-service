package com.mpescarmona.api.demo.repository;

import com.mpescarmona.api.demo.domain.HackerNew;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HackerNewRepository extends MongoRepository<HackerNew, String> {

    List<HackerNew> findByActiveIsTrueOrderByCreatedTimestampDesc();

    HackerNew findByObjectId(String objectId);

    HackerNew findByObjectIdAndActiveIsTrue(String objectId);

    HackerNew findByIdAndActiveIsTrue(String id);
}
