package com.nags.legostore;

import com.nags.legostore.model.*;
import com.nags.legostore.persistence.LegoSetRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@DataMongoTest
class LegostoreDatabaseTests
{
	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private LegoSetRepository legoSetRepository;

	@Before
	public void before()
	{
		this.legoSetRepository.deleteAll();

		LegoSet milleniumFalcon = new LegoSet("Millenium Falcon", "Star Wars",
				LegoSetDifficulty.HARD,
				new DeliveryInfo(LocalDate.now().plusDays(1), 30, true),
				Arrays.asList(
						new ProductReview("Dan", 7),
						new ProductReview("Anna", 10),
						new ProductReview("John", 8)
				),
				new PaymentOptions(PaymentType.CreditCard,0));

		LegoSet skyPolice = new LegoSet("Sky Police Air Base", "City",
				LegoSetDifficulty.MEDIUM,
				new DeliveryInfo(LocalDate.now().plusDays(3), 50, true),
				Arrays.asList(
						new ProductReview("Dan", 5),
						new ProductReview("Andrew", 8)
				),
				new PaymentOptions(PaymentType.CreditCard,0));

		this.legoSetRepository.insert(milleniumFalcon);
		this.legoSetRepository.insert(skyPolice);

		this.legoSetRepository.findAll().forEach(legoSet -> {
			System.out.println("Fear:"+legoSet.getName());
		});
	}

	//@Test
	public void testFindAllByGreatReviews()
	{
		List<LegoSet> results = (List<LegoSet>) this.legoSetRepository.findAllByGreatReviews();

		Assert.assertEquals(1, results.size());
		Assert.assertEquals("Star Wars", results.get(0).getTheme());
	}

}
