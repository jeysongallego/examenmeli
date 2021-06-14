package co.com.meli.handler;

import co.com.meli.mutantes.ADNException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
    Request request = new Request(dna);
    RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> new DNAHandler().handleRequest(request, null));
    Assertions.assertEquals("403-No mutante", exception.getMessage());
  }

  @Test
  public void test03_handleRequestListDNAMutante() {
    String[] dna = {"GTACG", "CGATG", "TTATG", "AGATG", "CAATG"};
    Request request = new Request(dna);
    String response = new DNAHandler().handleRequest(request, null);
    Assertions.assertEquals("true", response);
  }

}
