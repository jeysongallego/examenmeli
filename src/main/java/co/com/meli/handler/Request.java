package co.com.meli.handler;

public class Request {

  private String[] dna;

  public Request() {
  }

  public Request(String[] dna) {
    this.setDna(dna);
  }

  public String[] getDna() {
    return dna;
  }

  public void setDna(String[] dna) {
    this.dna = dna;
  }

}
