package uk.tw.energy.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class AccountServiceTest {

    private static final String PRICE_PLAN_ID = "price-plan-id";
    private static final String SMART_METER_ID = "smart-meter-id";

    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        Map<String, String> smartMeterToPricePlanAccounts = new HashMap<>();
        smartMeterToPricePlanAccounts.put(SMART_METER_ID, PRICE_PLAN_ID);

        accountService = new AccountService(smartMeterToPricePlanAccounts);
    }

    @Test
    public void givenTheSmartMeterIdReturnsThePricePlanId() throws Exception {
        assertThat(accountService.getPricePlanIdForSmartMeterId(SMART_METER_ID)).isEqualTo(PRICE_PLAN_ID);
    }

    @Test
    @Tag("fast")
    void testFastTaggedTest() {
        // remainder of test
        assert(true);
    }

    @Test
    @Tag("security")
    void testSecurityTaggedTest() {
        // remainder of test
        assert(true);
    }

    @Test
    @Tag("security")
    void testSecurity2FATaggedTest() {
        // remainder of test
        assert(true);
    }
}
