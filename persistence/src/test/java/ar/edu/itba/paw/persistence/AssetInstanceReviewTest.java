package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.assetExistanceContext.AssetInstanceReview;
import ar.edu.itba.paw.models.assetLendingContext.Lending;
import ar.edu.itba.paw.models.userContext.User;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;
import ar.edu.itba.paw.persistence.config.TestConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class AssetInstanceReviewTest{

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private DataSource ds;
    @Autowired
    private AssetInstanceReviewsDaoJpa assetInstanceReviewRepository;
    private JdbcTemplate jdbcTemplate;

    @Rollback
    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }
    @Test
    public void testAddReview() {
        Lending lending = entityManager.find(Lending.class, 0L);
        User user = entityManager.find(User.class, 0L);
        AssetInstanceReview assetInstanceReview = new AssetInstanceReview();
        assetInstanceReview.setLending(lending);
        assetInstanceReview.setRating(5);
        assetInstanceReview.setReviewer(user);
        assetInstanceReview.setReview("");

        assetInstanceReviewRepository.addReview(assetInstanceReview);

        entityManager.flush();
        entityManager.clear();

        AssetInstanceReview retrievedReview = entityManager.find(AssetInstanceReview.class, assetInstanceReview.getId());
        Assert.assertNotNull(retrievedReview);
    }

    @Test
    public void testGetRating() {
        User user = entityManager.find(User.class, 0L);

        AssetInstanceReview review1 = new AssetInstanceReview();
        Lending lending = entityManager.find(Lending.class, 0L);
        Lending lending1 = entityManager.find(Lending.class, 1L);
        review1.setLending(lending);
        review1.setRating(5);
        review1.setReviewer(user);
        review1.setReview("");


        AssetInstanceReview review2 = new AssetInstanceReview();
        review2.setLending(lending1);
        review2.setRating(3);
        review2.setReviewer(user);
        review2.setReview("");


        entityManager.persist(review1);
        entityManager.persist(review2);
        entityManager.flush();
        entityManager.clear();

        Lending refreshedLending = entityManager.find(Lending.class, 0L);
        double rating = refreshedLending.getAssetInstance().getRating();

        // Cambié el tipo de la variable expectedRating a Double para mayor precisión
        double expectedRating = (review1.getRating() + review2.getRating()) / 2.0;

        Assert.assertEquals(expectedRating, rating, 0.01);
    }

    @Test
    public void testGetAssetInstanceReviews() {
        Lending lending = entityManager.find(Lending.class, 0L);
        User user = entityManager.find(User.class, 0L);
        AssetInstanceReview review1 = new AssetInstanceReview();
        review1.setLending(lending);
        review1.setRating(5);
        review1.setReviewer(user);
        review1.setReview("");

        AssetInstanceReview review2 = new AssetInstanceReview();
        review2.setLending(lending);
        review2.setRating(3);
        review2.setReviewer(user);
        review2.setReview("");


        entityManager.persist(review1);
        entityManager.persist(review2);


        int pageNum = 1;
        int itemsPerPage = 10;
        PagingImpl<AssetInstanceReview> result = assetInstanceReviewRepository.getAssetInstanceReviews(pageNum, itemsPerPage, lending.getAssetInstance());

        Assert.assertEquals(2, result.getList().size());
    }

}
