package com.nags.legostore.persistence;

import com.nags.legostore.model.AvgRatingModel;
import com.nags.legostore.model.LegoSet;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

@Service
public class ReportService
{
    private MongoTemplate mongoTemplate;

    public ReportService(MongoTemplate mongoTemplate)
    {
        this.mongoTemplate = mongoTemplate;
    }

    public List<AvgRatingModel> getAvgRatingReport()
    {
        ProjectionOperation projectionToMatch = project().andExpression("name").as("productName")
                .andExpression("{$avg : '$reviews.rating'}").as("avgRating");

        Aggregation avgRatingAggregation = newAggregation(LegoSet.class, projectionToMatch);

        return this.mongoTemplate.aggregate(avgRatingAggregation, LegoSet.class, AvgRatingModel.class).getMappedResults();
    }

}
