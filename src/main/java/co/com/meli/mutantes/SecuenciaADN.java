package co.com.meli.mutantes;

import java.util.Stack;

public class SecuenciaADN {

  /* Es la secuencia original que se va armando*/
  private final Stack<Character> secuencia;

  /* Es la secuencia solo de letras iguales */
  private final Stack<Character> secuenciaContinua;

  public SecuenciaADN() {
    this.secuencia = new Stack<>();
    this.secuenciaContinua = new Stack<>();
  }

  /**
   * Adiciona un carater al objeto SecuenciaADN de la sguiente forma : <br>
   * al atributo stack <b>secuencia</b> la adicina al final.<br>
   * para el atributo stack <b>secuenciaContinua</b> adiciona si es igual a la anterior
   * en caso contrario reinicia el stack.<br>
   * Cuando detecta una secuancia mutante (4 iguales) tambien reinicia el stack <b>secuenciaContinua</b><br>
   * Retorna true o false si detecta ya una secuencia mutante (4 letras iguales)
   *
   * @param letra Caracter que se va a adicionar.
   * @return true si se detecta una secuencia mutante.
   */
  public boolean addLetra(char letra) {
    if (!secuencia.empty()) {
      char ultimaLetra = secuencia.lastElement();
      if (letra != ultimaLetra) {
        //Si la ultima letra es diferente a la que llega se reinicia el stack de continua.
        secuenciaContinua.removeAllElements();
      }
    }
    //Se adiona siempre la letra nueva a ambos stack.
    secuencia.push(letra);
    secuenciaContinua.push(letra);
    if (secuenciaContinua.size() == 4) {
      //Evalua si el stack de continuo ya tiene 4 elementos (que deben ser iguales)
      secuenciaContinua.removeAllElements();
      return true;
    }
    return false;
  }


}
