package com.database.repos;

import com.database.model.Deal;

import java.util.List;

public interface DealsRepository extends Repository<Integer, Deal> {

    List<Deal> findByAmount(String amount);

    List<Deal> findByCurrency(String currency);

    int insertDeal(String source, String currency, int amount);
    int insertDeal(String currency, int amount);

    int editAmountByCurrency(int amount, String currency);

    int editCurrency(String currency);

    int editSourceByCurrency(String source, String currency);
}
