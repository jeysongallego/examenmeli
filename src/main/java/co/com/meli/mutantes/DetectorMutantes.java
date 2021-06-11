package co.com.meli.mutantes;

import java.util.*;

/**
 * Clase encargada de procesar ADNs y detectar mutantes.
 */
public class DetectorMutantes {


  private static final char LETRA_A = 'A';
  private static final char LETRA_T = 'T';
  private static final char LETRA_C = 'C';
  private static final char LETRA_G = 'G';

  public static boolean isMutant(String[] dna) throws ADNException {
    List<String> matrizADN = Arrays.asList(dna);
    int tamanoMatriz = matrizADN.size();
    validacionesPrevias(matrizADN, tamanoMatriz);
    //Cada uno de lo siguientes mapas almacenan una coleccion de SecuenciaADN
    //que se mantienen mienta se recorre la matriz.
    Map<Integer, SecuenciaADN> verticalesMap = new HashMap<>();
    Map<Integer, SecuenciaADN> horizontalesMap = new HashMap<>();
    Map<Integer, SecuenciaADN> diagonalesMap = new HashMap<>();
    Map<Integer, SecuenciaADN> diagonalesInvMap = new HashMap<>();
    int cantSecunciaMutantes = 0;
    //i es la fila en la que va recorriendo
    int i = 1;
    for (String secuenciaFila : matrizADN) {
      //j es la columna o posicion del caracter de la fila que se esta evaluando
      for (int j = 1; j <= tamanoMatriz; j++) {
        char letraADN = secuenciaFila.charAt(j - 1);
        //El mapa de SecuenciaADN horizontales viene determinado por la fila (i) misma.
        boolean secuenciaMutanteH = horizontalesMap.computeIfAbsent(i, k -> new SecuenciaADN()).addLetra(letraADN);
        cantSecunciaMutantes += secuenciaMutanteH ? 1 : 0;
        //El mapa de SecuenciaADN verticales viene determinado por la columna (j) de cada fila.
        boolean secuenciaMutanteV = verticalesMap.computeIfAbsent(j, k -> new SecuenciaADN()).addLetra(letraADN);
        cantSecunciaMutantes += secuenciaMutanteV ? 1 : 0;
        //Las diagonales en el sentido izq-der y arriba-abajo, se clasifican con la resta de la columna menos la fila
        int idxDiagonal = j-i;
        if (tamanoMatriz - Math.abs(idxDiagonal) > 3) {
          //Solo se tienen en cuenta diagonales que puedan tener mas de 4 elementos
          boolean secuenciaMutanteD = diagonalesMap.computeIfAbsent(idxDiagonal, k -> new SecuenciaADN()).addLetra(letraADN);
          cantSecunciaMutantes += secuenciaMutanteD ? 1 : 0;
        }
        //Las diagonales "invertidas" en el sentido der-izq y arriba-abajo, se clasifican con la suma de la columna y la fila
        int idxDiagonalInv = j+i;
        if (idxDiagonalInv > 4 && (tamanoMatriz * 2 - idxDiagonalInv) > 2) {
          //Solo se tienen en cuenta diagonales que puedan tener mas de 4 elementos
          boolean secuenciaMutanteDInv = diagonalesInvMap.computeIfAbsent(idxDiagonalInv, k -> new SecuenciaADN()).addLetra(letraADN);
          cantSecunciaMutantes += secuenciaMutanteDInv ? 1 : 0;
        }
        //Si ya tiene mas de una secuencia mutante retorna true.
        if (cantSecunciaMutantes > 1) {
          return true;
        }
      }
      i++;
    }
    return false;
  }


  /**
   * Realiza validaciones preias de estrutura y caracteres validos en la totalidad de la matriz.
   *
   * @param filasADNList La matriz que representa la cadena de ADN.
   * @param tamanoMatriz Tamaño de la matiz.
   * @throws ADNException Excepcion lanada en caso de no ser valida.
   */
  private static void validacionesPrevias(List<String> filasADNList, int tamanoMatriz) throws ADNException {
    if (filasADNList.parallelStream().anyMatch(fila -> fila.length() != tamanoMatriz)) {
      throw new ADNException("El numero de letras de cada fila no conicde con la cantidad de filas");
    }
    if (filasADNList.parallelStream().anyMatch(fila -> fila.chars().anyMatch(letraADN ->
            (letraADN != LETRA_A && letraADN != LETRA_T && letraADN != LETRA_C && letraADN != LETRA_G)))) {
      throw new ADNException("La secuencia de ADN contiene caracteres no validos");
    }
  }

}
