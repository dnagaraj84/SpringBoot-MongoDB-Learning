package com.nags.legostore.api;

import com.nags.legostore.model.LegoSet;
import com.nags.legostore.model.LegoSetDifficulty;
import com.nags.legostore.model.QLegoSet;
import com.nags.legostore.persistence.LegoSetRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.function.Predicate;

@RestController
@RequestMapping("/legostore/api")
public class LegoStoreController
{
    //private MongoTemplate mongoTemplate;
    private LegoSetRepository legoSetRepository;

    public LegoStoreController(LegoSetRepository legoSetRepository)
    {
        this.legoSetRepository = legoSetRepository;
    }

    @PostMapping
    public void insert(@RequestBody LegoSet legoSet)
    {
        legoSetRepository.insert(legoSet);
    }

    @GetMapping("/all")
    public Collection<LegoSet> getAllLegoSets()
    {
        Sort sortByTheme = Sort.by("theme").ascending();
        return this.legoSetRepository.findAll(sortByTheme);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody LegoSet legoSet)
    {
        this.legoSetRepository.save(legoSet);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id)
    {
        this.legoSetRepository.deleteById(id);
    }

    @GetMapping("/{id}")
    public LegoSet getLegoSetById(@PathVariable String id)
    {
        LegoSet legoSet =  this.legoSetRepository.findById(id).orElse(null);

        return legoSet;
    }

    @GetMapping("/byTheme/{theme}")
    public Collection<LegoSet> findByTheme(@PathVariable String theme)
    {
        Sort sortByTheme = Sort.by("theme").ascending();
        return this.legoSetRepository.findAllByThemeContains(theme, sortByTheme);
    }

    @GetMapping("/hardStartsWithM")
    public Collection<LegoSet> getAllHardStartsWithM()
    {
        return this.legoSetRepository.findAllByDifficultyAndNameStartsWith(LegoSetDifficulty.HARD, "M");
    }

    @GetMapping("/byDeliveryFeeLessThanPrice/{price}")
    public Collection<LegoSet> getAllByDeliveryFeeLessThan(@PathVariable int price)
    {
        return  this.legoSetRepository.findAllByDeliveryFeeLessThan(price);
    }

    @GetMapping("/byGreatReviews")
    public Collection<LegoSet> getAllGreatReviews()
    {
        return  this.legoSetRepository.findAllByGreatReviews();
    }

    @GetMapping("/exceptTheme/{theme}")
    public Collection<LegoSet> getAllExceptTheme(@PathVariable String theme)
    {
        return  this.legoSetRepository.findAllByThemeNotContains(theme);
    }

    @GetMapping("/inStock")
    public  Collection<LegoSet> getAllInStock()
    {
        return this.legoSetRepository.findAllInStock();
    }

    @GetMapping("/bestBuy")
    public Collection<LegoSet> getBestBuyProducts()
    {
        QLegoSet query = new QLegoSet("query");

        BooleanExpression inStockFilter = query.deliveryInfo.inStock.isTrue();
        BooleanExpression smallDeliveryFeeFilter = query.deliveryInfo.deliveryFee.lt(50);
        BooleanExpression hasMoreReviews = query.reviews.any().rating.eq(10);

        BooleanExpression predicate = inStockFilter.and(smallDeliveryFeeFilter).and(hasMoreReviews);

        return (Collection<LegoSet>) this.legoSetRepository.findAll(predicate);
    }

    @GetMapping("/worstBuy")
    public Collection<LegoSet> getWorstBuyProducts()
    {
        QLegoSet query = new QLegoSet("query");

        BooleanExpression emptyReviewsFilter = query.reviews.isEmpty();
        BooleanExpression noStockFilter = query.deliveryInfo.inStock.isFalse();

        BooleanExpression predicate = emptyReviewsFilter.or(noStockFilter);

        return (Collection<LegoSet>) this.legoSetRepository.findAll(predicate);
    }

    @GetMapping("/fullTextSearch/{text}")
    public Collection<LegoSet> fullTextSearch(@PathVariable String text)
    {
        TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matching(text);

        return this.legoSetRepository.findAllBy(textCriteria);
    }

    @GetMapping("/byPaymentOption/{id}")
    public Collection<LegoSet> byPaymentOption(@PathVariable String id)
    {
        return  this.legoSetRepository.findByPaymentOptionId(id);
    }
}
