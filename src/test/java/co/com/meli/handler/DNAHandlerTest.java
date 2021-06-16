package co.com.meli.handler;

import co.com.meli.db.DynamoOperations;
import co.com.meli.mutantes.ADNException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class DNAHandlerTest {

  @Test
  public void test01_handleRequestListDNAMalo() {
    String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG"};
    Request request = new Request(dna);
    RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> new DNAHandler().handleRequest(request, null));
    Assertions.assertEquals("400", exception.getMessage());
    ADNException execptionADN = (ADNException) exception.getCause();
    String mensajeEsperado = "El numero de letras de cada fila no conicde con la cantidad de filas";
    Assertions.assertEquals(mensajeEsperado, execptionADN.getMessage());
  }

  @Test
  public void test02_handleRequestListDNANoMutante() {
    String[] dna = {"GTAC", "CGAT", "TTAT", "AGAT"};
    DynamoOperations dynamoMock = Mockito.spy(DynamoOperations.class);
    Mockito.doNothing().when(dynamoMock).saveData(Mockito.any(), Mockito.any());
    Mockito.doCallRealMethod().when(dynamoMock).insertADN(dna, false);
    Request request = new Request(dna);
    RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> new DNAHandler(dynamoMock).handleRequest(request, null));
    Assertions.assertEquals("403-No mutante", exception.getMessage());
  }

  @Test
  public void test03_handleRequestListDNAMutante() {
    String[] dna = {"GTACG", "CGATG", "TTATG", "AGATG", "CAATG"};
    DynamoOperations dynamoMock = Mockito.spy(DynamoOperations.class);
    Mockito.doNothing().when(dynamoMock).saveData(Mockito.any(), Mockito.any());
    Mockito.doCallRealMethod().when(dynamoMock).insertADN(dna, false);
    Request request = new Request(dna);
    String response = new DNAHandler(dynamoMock).handleRequest(request, null);
    Assertions.assertEquals("true", response);
  }

}
