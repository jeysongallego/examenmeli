package co.com.meli.mutantes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DetectorMutantesTest {


  @Test
  public void test01_NoMutante_CadenaMala() {
    String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG"};
    ADNException exception = Assertions.assertThrows(ADNException.class, () -> DetectorMutantes.isMutant(dna));
    String mensajeEsperado = "El numero de letras de cada fila no conicde con la cantidad de filas";
    Assertions.assertEquals(mensajeEsperado, exception.getMessage());
  }

  @Test
  public void test02_NoMutante_CadenaLetraMala() {
    String[] dna = {"ATGC", "CAGT", "TTAT", "AXAA"};
    ADNException exception = Assertions.assertThrows(ADNException.class, () -> DetectorMutantes.isMutant(dna));
    //String mensajeEsperado = "La letra X con posicion 2 en la fila 4 no es una letra valida para una secuencia de ADN";
    String mensajeEsperado = "La secuencia de ADN contiene caracteres no validos";
    Assertions.assertEquals(mensajeEsperado, exception.getMessage());
  }

  @Test
  public void test03_NoMutante_UnaSecuenciaVetical() throws ADNException {
    String[] dna = {"GTAC", "CGAT", "TTAT", "AGAT"};
    Assertions.assertFalse(DetectorMutantes.isMutant(dna));
  }

  @Test
  public void test04_Mutante_DosSecuenciaVeticales() throws ADNException {
    String[] dna = {"GTACG", "CGATG", "TTATG", "AGATG", "CAATC"};
    Assertions.assertTrue(DetectorMutantes.isMutant(dna));
  }

  @Test
  public void test05_NoMutante_matrizPequena() throws ADNException {
    String[] dna = {"GGG", "GGG", "GGG"};
    Assertions.assertFalse(DetectorMutantes.isMutant(dna));
  }

  @Test
  public void test06_Mutante_DosSecuenciaHorizontales() throws ADNException {
    String[] dna = {"GTACG", "CGGGG", "GGGGC", "AGATG", "CAATG"};
    Assertions.assertTrue(DetectorMutantes.isMutant(dna));
  }

  @Test
  public void test07_Mutante_UnaVerticalUnaHorizontal() throws ADNException {
    String[] dna = {"GTACA", "CGGGG", "ACCAG", "ACTTG", "CAATG"};
    Assertions.assertTrue(DetectorMutantes.isMutant(dna));
  }

  @Test
  public void test08_Error_LetraMalaFinal() {
    String[] dna = {"GTACAG", "CGGGGT", "ACCAGA", "ACTTGC", "CAATGA", "CAATGK"};
    ADNException exception = Assertions.assertThrows(ADNException.class, () -> DetectorMutantes.isMutant(dna));
    String mensajeEsperado = "La secuencia de ADN contiene caracteres no validos";
    Assertions.assertEquals(mensajeEsperado, exception.getMessage());
  }

  @Test
  public void test09_Mutante_UnaVerticalUnaDiagonalPpal() throws ADNException {
    String[] dna = {"TGACA", "CTTAG", "ACTAG", "ACTTG", "CAATG"};
    Assertions.assertTrue(DetectorMutantes.isMutant(dna));
  }

  @Test
  public void test10_Mutante_UnaVerticalUnaDiagonalNoPpal() throws ADNException {
    String[] dna = {"CGACA", "CTGAG", "TCTGG", "ACTAG", "CAATG"};
    Assertions.assertTrue(DetectorMutantes.isMutant(dna));
  }

  @Test
  public void test11_Mutante_UnaDiagonalNoPpalUnaDiagonalInvertida() throws ADNException {
    String[] dna = {"CGACA", "CTGAT", "TCAGC", "AATAG", "CAATG"};
    Assertions.assertTrue(DetectorMutantes.isMutant(dna));
  }

  @Test
  public void test12_NoMutante_UnaDiagonalVertiicalCasi() throws ADNException {
    String[] dna = {"CTGCA", "CGAAT", "CCAGC", "AATAG", "CAATT"};
    Assertions.assertFalse(DetectorMutantes.isMutant(dna));
  }

  @Test
  public void test13_NoMutante_EjemploChallenge() throws ADNException {
    String[] dna = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
    Assertions.assertFalse(DetectorMutantes.isMutant(dna));
  }

  @Test
  public void test14_Mutante_EjemploChallenge() throws ADNException {
    String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
    Assertions.assertTrue(DetectorMutantes.isMutant(dna));
  }

  @Test
  public void test15_NoMutante_UnaDiagonalExtendida() throws ADNException {
    String[] dna = {"CTGCA", "CGAAT", "CCAGC", "AATAG", "AAATT"};
    Assertions.assertFalse(DetectorMutantes.isMutant(dna));
  }

  @Test
  public void test16_Mutante_DobleDiagonal() throws ADNException {
    String[] dna = {"ACGTACGT", "CGTACGTA", "TGCATTCA", "GTCATTCA", "ACGTACGT", "CGTACGTA", "TTCATGCA", "TTCAGTCA"};
    Assertions.assertTrue(DetectorMutantes.isMutant(dna));
  }

}
