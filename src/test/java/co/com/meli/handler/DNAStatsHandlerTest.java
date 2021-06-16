package co.com.meli.handler;

import co.com.meli.db.DynamoOperations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class DNAStatsHandlerTest {


  @Test
  public void test01_stats() {
    DynamoOperations dynamoMock = Mockito.spy(DynamoOperations.class);
    Mockito.doReturn(5).when(dynamoMock).getECantidadADNs( 1);
    Mockito.doReturn(20).when(dynamoMock).getECantidadADNs( 0);
    ResponseStats response = new DNAStatsHandler(dynamoMock).handleRequest(null, null);
    Assertions.assertEquals(5, response.getCount_mutant_dna());
    Assertions.assertEquals(25, response.getCount_human_dna());
    Assertions.assertEquals(0.2, response.getRatio());
  }

}
