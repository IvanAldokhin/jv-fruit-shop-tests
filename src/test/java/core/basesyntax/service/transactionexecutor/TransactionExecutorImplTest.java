package core.basesyntax.service.transactionexecutor;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operationmap.OperationMap;
import core.basesyntax.strategy.operationmap.OperationMapImpl;
import core.basesyntax.strategy.operationstrategy.OperationStrategy;
import core.basesyntax.strategy.operationstrategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TransactionExecutorImplTest {
    private OperationStrategy operationStrategy;
    private OperationMap operationMap;
    private TransactionExecutor transactionExecutor;
    private List<FruitTransaction> fruitList;
    private List<Integer> quantity;
    private List<String> fruitsName;

    public TransactionExecutorImplTest() {
        fruitList = new ArrayList<>();
        operationMap = new OperationMapImpl();
        operationStrategy = new OperationStrategyImpl(operationMap.getOperationMap());
        transactionExecutor = new TransactionExecutorImpl(operationStrategy);
    }

    @Before
    public void setUp() throws Exception {
        quantity = new ArrayList<>();
        fruitsName = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            FruitTransaction fruit = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                    "apple " + i, i);
            fruitList.add(fruit);
            quantity.add(i);
            fruitsName.add("apple " + i);
        }
    }

    @Test
    public void executeBalance_Ok() {
        transactionExecutor.executeTransaction(fruitList);
        boolean quantityFruits = Storage.storage.values().containsAll(quantity);
        boolean namesFruits = Storage.storage.keySet().containsAll(fruitsName);
        boolean expected = quantityFruits & namesFruits;
        Assert.assertTrue("You don't execute balance transaction "
                        + "or add to the storage",expected);
    }

    @Test
    public void executePurchase_Ok() {
        transactionExecutor.executeTransaction(fruitList);
        fruitList.clear();
        quantity.clear();
        for (int i = 0; i < 10; i++) {
            FruitTransaction fruit = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                    "apple " + i, i);
            fruitList.add(fruit);
            quantity.add(0);
        }
        transactionExecutor.executeTransaction(fruitList);
        boolean quantityFruits = Storage.storage.values().containsAll(quantity);
        boolean namesFruits = Storage.storage.keySet().containsAll(fruitsName);
        boolean expected = quantityFruits & namesFruits;
        Assert.assertTrue("You don't execute purchase transaction "
                + "or add to the storage",expected);
    }

    @Test
    public void executeReturn_Ok() {
        transactionExecutor.executeTransaction(fruitList);
        fruitList.clear();
        quantity.clear();
        for (int i = 0; i < 10; i++) {
            FruitTransaction fruit = new FruitTransaction(FruitTransaction.Operation.RETURN,
                    "apple " + i, i);
            fruitList.add(fruit);
            quantity.add(i * 2);
        }
        transactionExecutor.executeTransaction(fruitList);
        boolean quantityFruits = Storage.storage.values().containsAll(quantity);
        boolean namesFruits = Storage.storage.keySet().containsAll(fruitsName);
        boolean expected = quantityFruits & namesFruits;
        Assert.assertTrue("You don't execute return transaction "
                + "or add to the storage",expected);
    }

    @Test
    public void executeSupply_Ok() {
        transactionExecutor.executeTransaction(fruitList);
        fruitList.clear();
        quantity.clear();
        for (int i = 0; i < 10; i++) {
            FruitTransaction fruit = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                    "apple " + i, i);
            fruitList.add(fruit);
            quantity.add(i * 2);
        }
        transactionExecutor.executeTransaction(fruitList);
        boolean quantityFruits = Storage.storage.values().containsAll(quantity);
        boolean namesFruits = Storage.storage.keySet().containsAll(fruitsName);
        boolean expected = quantityFruits & namesFruits;
        Assert.assertTrue("You don't execute supply transaction "
                + "or add to the storage",expected);
    }

    @After
    public void tearDown() throws Exception {
        fruitsName.clear();
        fruitList.clear();
        quantity.clear();
    }
}
