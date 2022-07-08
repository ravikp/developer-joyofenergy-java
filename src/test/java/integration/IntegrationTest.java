package integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import uk.tw.energy.App;
import uk.tw.energy.builders.MeterReadingsBuilder;
import uk.tw.energy.domain.MeterReadings;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = App.class)
public class IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldStoreReadings() throws JsonProcessingException {
        MeterReadings meterReadings = new MeterReadingsBuilder().generateElectricityReadings().build();
        HttpEntity<String> entity = getStringHttpEntity(meterReadings);

        ResponseEntity<String> response = restTemplate.postForEntity("/readings/store", entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void givenMeterIdShouldReturnAMeterReadingAssociatedWithMeterId() throws JsonProcessingException {
        String smartMeterId = "bob";
        populateMeterReadingsForMeter(smartMeterId);

        ResponseEntity<String> response = restTemplate.getForEntity("/readings/read/" + smartMeterId, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldCalculateAllPrices() throws JsonProcessingException {
        String smartMeterId = "bob";
        populateMeterReadingsForMeter(smartMeterId);

        ResponseEntity<String> response = restTemplate.getForEntity("/price-plans/compare-all/" + smartMeterId, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void givenMeterIdAndLimitShouldReturnRecommendedCheapestPricePlans() throws JsonProcessingException {
        String smartMeterId = "bob";
        populateMeterReadingsForMeter(smartMeterId);

        ResponseEntity<String> response =
                restTemplate.getForEntity("/price-plans/recommend/" + smartMeterId + "?limit=2", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private HttpEntity<String> getStringHttpEntity(Object object) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String jsonMeterData = mapper.writeValueAsString(object);
        return (HttpEntity<String>) new HttpEntity(jsonMeterData, headers);
    }

    private void populateMeterReadingsForMeter(String smartMeterId) throws JsonProcessingException {
        MeterReadings readings = new MeterReadingsBuilder().setSmartMeterId(smartMeterId)
                .generateElectricityReadings(20)
                .build();

        HttpEntity<String> entity = getStringHttpEntity(readings);
        restTemplate.postForEntity("/readings/store", entity, String.class);
    }
}
