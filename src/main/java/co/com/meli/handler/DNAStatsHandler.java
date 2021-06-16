package co.com.meli.handler;

import co.com.meli.db.DynamoOperations;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class DNAStatsHandler implements RequestHandler<Request, ResponseStats> {

  private DynamoOperations dbManager;

  public DNAStatsHandler() {
  }

  public DNAStatsHandler(DynamoOperations dbManager) {
    this.dbManager = dbManager;
  }

  /**
   *
   * @param request
   * @param context
   * @return
   */
  @Override
  public ResponseStats handleRequest(Request request, Context context) {
    if (this.dbManager == null) {
      this.dbManager = new DynamoOperations();
    }
    ResponseStats stats = new ResponseStats();
    int mutantes = this.dbManager.getECantidadADNs(1);
    int noMutantes = this.dbManager.getECantidadADNs(0);
    stats.setCount_mutant_dn(mutantes);
    stats.setCount_human_dna(mutantes + noMutantes);
    stats.setRatio(Math.round((mutantes * 100.0) / (mutantes + noMutantes)) / 100.0);
    return stats;
  }

}
