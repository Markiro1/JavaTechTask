package com.ashapiro.testassigment.repository;

import com.ashapiro.testassigment.model.ReportSpecification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReportSpecificationRepo extends MongoRepository<ReportSpecification, String> {
}
