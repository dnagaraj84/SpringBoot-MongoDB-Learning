package com.nags.legostore.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Collection;
@Document(collection = "legosets")
public class LegoSet
{
    @Id
    private String id;
    @TextIndexed
    private String name;
    private LegoSetDifficulty difficulty;
    @TextIndexed
    @Indexed(direction = IndexDirection.ASCENDING)
    private String theme;
    private Collection<ProductReview> reviews = new ArrayList<>();
    @Field("delivery")
    private DeliveryInfo deliveryInfo;
    @DBRef
    private PaymentOptions paymentOptions;

    @PersistenceConstructor
    public LegoSet(String name, String theme, LegoSetDifficulty difficulty, DeliveryInfo deliveryInfo, Collection<ProductReview> reviews, PaymentOptions paymentOptions) {
        this.name = name;
        this.difficulty = difficulty;
        this.theme = theme;
        this.reviews = reviews;
        this.deliveryInfo = deliveryInfo;
        this.paymentOptions = paymentOptions;
    }

    //@Transient
    private int nbParts;

    public int getNbParts() {
        return nbParts;
    }

    public void setNbParts(int nbParts) {
        this.nbParts = nbParts;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LegoSetDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(LegoSetDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Collection<ProductReview> getReviews() {
        return reviews;
    }

    public void setReviews(Collection<ProductReview> reviews) {
        this.reviews = reviews;
    }

    public DeliveryInfo getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public PaymentOptions getPaymentOptions() {
        return paymentOptions;
    }

}
