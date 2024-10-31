package com.ashapiro.testassigment.repository;

import com.ashapiro.testassigment.model.SalesAndTrafficByAsin;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SalesAndTrafficByAsinRepo extends MongoRepository<SalesAndTrafficByAsin, String> {

    Optional<SalesAndTrafficByAsin> findByParentAsin(String asin);

    Optional<List<SalesAndTrafficByAsin>> findByParentAsinIn(List<String> asins);
}
