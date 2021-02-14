package com.database;


import com.database.model.Deal;
import com.database.model.User;
import com.database.queryProcessors.delete.DeleteAll;
import com.database.queryProcessors.delete.DeleteOne;
import com.database.queryProcessors.insert.InsertOneProcessor;
import com.database.queryProcessors.select.FindAllProcessor;
import com.database.queryProcessors.select.FindByFieldProcessor;
import com.database.queryProcessors.select.FindOne;
import com.database.queryProcessors.update.UpdateOneProcessor;
import com.database.repos.DealsRepository;
import com.database.repos.UserRepository;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.SqlStatements;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class SelectQueryTest {

    private static DbViewer viewer;

    @BeforeAll
    static void beforeAll() {
        final Jdbi jdbi = Jdbi.create("jdbc:postgresql://localhost:5432/postgres?user=postgres&password=admin");
        jdbi.getConfig(SqlStatements.class).setUnusedBindingAllowed(true);
        QueryRunner queryRunner = new JdbiQueryRunner(jdbi);

        queryRunner.addProcessor(new FindOne(jdbi));
        queryRunner.addProcessor(new FindAllProcessor(jdbi));
        queryRunner.addProcessor(new FindByFieldProcessor(jdbi));
        queryRunner.addProcessor(new DeleteOne(jdbi));
        queryRunner.addProcessor(new DeleteAll(jdbi));
        queryRunner.addProcessor(new InsertOneProcessor(jdbi));
        queryRunner.addProcessor(new UpdateOneProcessor(jdbi));
        viewer = new DbViewer(queryRunner);
    }


    @Test
    void testCanGetDealById() {
        DealsRepository dealRepo = viewer.create(DealsRepository.class);
        Deal deal = dealRepo.findOne(1);
        assertThat(deal).isNotNull();
    }

    @Test
    void testCanGetAllDeals() {
        DealsRepository dealRepo = viewer.create(DealsRepository.class);
        List<Deal> deal = dealRepo.findAll();
        assertThat(deal).isNotNull();
    }

    @Test
    void testCanGetUserById() {
        UserRepository userRepo = viewer.create(UserRepository.class);
        User user = userRepo.findOne(5);
        assertThat(user).isNotNull();
        System.out.println("rESPONSE= "+ user.getFirstName()+" "+user.getId());
    }

    @Test
    void testCanGetAllUsers() {
        UserRepository userRepo = viewer.create(UserRepository.class);
        List<User> user = userRepo.findAll();
        assertThat(user).isNotNull();
    }

    @Test
    void testCanGetDealByAmount() {
        DealsRepository dealsRepo = viewer.create(DealsRepository.class);
        List<Deal> deals = dealsRepo.findByAmount("150");
        assertThat(deals.size()).isGreaterThan(0);

    }

    @Test
    void testCanGetDealsByCurrency() {
        DealsRepository dealsRepo = viewer.create(DealsRepository.class);
        List<Deal> deals = dealsRepo.findByCurrency("USD");
        assertThat(deals.size()).isEqualTo(1);
    }

    @Test
    void testCanGetUserByFirstName() {
        UserRepository userRepo = viewer.create(UserRepository.class);
        List<User> users = userRepo.findByName("Ivan");
        assertThat(users.size()).isGreaterThan(0);
    }

    @Test
    void testCanDeleteOneDeal() {
        DealsRepository dealsRepository = viewer.create(DealsRepository.class);
        int deletedRowsQuantity = dealsRepository.deleteOne(4);
        assertThat(deletedRowsQuantity).isEqualTo(1);
    }

    @Test
    void testCanDeleteOneUser() {
        UserRepository userRepository = viewer.create(UserRepository.class);
        int deletedRowsQuantity = userRepository.deleteOne(2);
        assertThat(deletedRowsQuantity).isEqualTo(1);
    }


    @Test
    void testCanDeleteAllUsers() {
        UserRepository userRepository = viewer.create(UserRepository.class);
        int deletedRowsQuantity = userRepository.deleteAll();
        assertThat(deletedRowsQuantity).isEqualTo(2);
    }

    @Test
    void testCanDeleteAllDeals() {
        DealsRepository dealsRepository = viewer.create(DealsRepository.class);
        int deletedRowsQuantity = dealsRepository.deleteAll();
        assertThat(deletedRowsQuantity).isEqualTo(2);
    }

    @Test
    void testCanInsertOneDeal() {
        DealsRepository dealsRepository = viewer.create(DealsRepository.class);
        int insertedRowsQuantity = dealsRepository.insertDeal("test_" + System.currentTimeMillis(), "USD", 250000);
        assertThat(insertedRowsQuantity).isEqualTo(1);
    }

    @Test
    void testCanInsertOneDealWithRequiredFieldsOnly() {
        DealsRepository dealsRepository = viewer.create(DealsRepository.class);
        int insertedRowsQuantity = dealsRepository.insertDeal("", "USD", 20);
        assertThat(insertedRowsQuantity).isEqualTo(1);
    }

    @Test
    void testCanInsertOnUser() {
        UserRepository userRepository = viewer.create(UserRepository.class);
        int insertedRowsQuantity = 0;
        for (int i = 0; i < 5; i++) {
            userRepository.insertUser("_" + System.currentTimeMillis());
            insertedRowsQuantity++;
        }
        assertThat(insertedRowsQuantity).isEqualTo(5);
    }

    @Test
    void canUpdateAmountByCurrency() {
        DealsRepository dealsRepository = viewer.create(DealsRepository.class);
        int rows=dealsRepository.editAmountByCurrency(3, "USD");
        assertThat(rows).isGreaterThan(0);
        System.out.println("affected rows="+rows);
    }

    @Test
    void canUpdateSourceByCurrency() {
        DealsRepository dealsRepository = viewer.create(DealsRepository.class);
        int rows=dealsRepository.editSourceByCurrency("n/a", "EUR");
        assertThat(rows).isGreaterThan(0);
        System.out.println("affected rows="+rows);
    }

}




