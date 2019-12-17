package com.nags.legostore.persistence;

import com.nags.legostore.model.LegoSet;
import com.nags.legostore.model.LegoSetDifficulty;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface LegoSetRepository extends MongoRepository<LegoSet, String>, QuerydslPredicateExecutor
{
    Collection<LegoSet> findAllByThemeContains(String theme, Sort sort);

    Collection<LegoSet> findAllByDifficultyAndNameStartsWith(LegoSetDifficulty legoSetDifficulty, String name);

    Collection<LegoSet> findAllBy(TextCriteria textCriteria);

    @Query("{'delivery.deliveryFee' : {$lt : ?0}}")
    Collection<LegoSet> findAllByDeliveryFeeLessThan(int price);

    @Query("{'reviews.rating' : {$eq : 10}}")
    Collection<LegoSet> findAllByGreatReviews();

    Collection<LegoSet> findAllByThemeNotContains(String name);

    @Query("{'delivery.inStock' : {$eq : true}})")
    Collection<LegoSet> findAllInStock();

    @Query("{'paymentOptions.id' :?0}")
    Collection<LegoSet> findByPaymentOptionId(String id);
}
