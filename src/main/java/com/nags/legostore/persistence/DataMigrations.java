package com.nags.legostore.persistence;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.nags.legostore.model.LegoSet;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@ChangeLog(order = "001")
public class DataMigrations
{
    @ChangeSet(order = "001", author = "nags", id = "update nbParts")
    public void updateNbParts(MongoTemplate mongoTemplate)
    {
        Criteria criteria = new Criteria().orOperator(
                Criteria.where("nbParts").is(0),
                Criteria.where("nbParts").is(null)

        );

        mongoTemplate.updateMulti(new Query(criteria), Update.update("nbParts", 122), LegoSet.class);

        System.out.println("Applied changeset 001");
    }
}
