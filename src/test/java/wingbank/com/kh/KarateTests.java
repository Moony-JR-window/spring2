package wingbank.com.kh;

import com.intuit.karate.junit5.Karate;

public class KarateTests {
    @Karate.Test
    Karate testAll() {
        return Karate.run("classpath:features").relativeTo(getClass());
    }

    @Karate.Test
    Karate testGetTransaction() {
        return Karate.run("classpath:features/API/GetTransaction.feature").relativeTo(getClass());
    }

    @Karate.Test
    Karate testRecent() {
        return Karate.run("classpath:features/API/RecentTransaction.feature").relativeTo(getClass());
    }

    @Karate.Test
    Karate SearchTransaction() {
        return Karate.run("classpath:features/API/SearchTransaction.feature").relativeTo(getClass());
    }
    @Karate.Test
    Karate SearchTransactionByName() {
        return Karate.run("classpath:features/API/SearchTransactionByName.feature").relativeTo(getClass());
    }

}
