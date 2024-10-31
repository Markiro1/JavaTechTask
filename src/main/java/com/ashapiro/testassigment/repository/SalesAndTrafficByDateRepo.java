package com.ashapiro.testassigment.repository;

import com.ashapiro.testassigment.model.SalesAndTrafficByDate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SalesAndTrafficByDateRepo extends MongoRepository<SalesAndTrafficByDate, String> {

    Optional<List<SalesAndTrafficByDate>> findByDate(LocalDate date);

    Optional<List<SalesAndTrafficByDate>> findByDateBetween(LocalDate startDate, LocalDate endDate);

}
