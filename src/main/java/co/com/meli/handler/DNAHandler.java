package co.com.meli.handler;

import co.com.meli.db.DynamoOperations;
import co.com.meli.mutantes.ADNException;
import co.com.meli.mutantes.DetectorMutantes;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class DNAHandler implements RequestHandler<Request, String> {

  private DynamoOperations dbManager;

  public DNAHandler() {
  }

  public DNAHandler(DynamoOperations dbManager) {
    this.dbManager = dbManager;
  }

  @Override
  public String handleRequest(Request request, Context context) {
    if(this.dbManager == null) {
      this.dbManager = new DynamoOperations();
    }
    try {
      boolean isMutant = DetectorMutantes.isMutant(request.getDna());
      dbManager.insertADN(request.getDna(), isMutant);
      if (isMutant) {
        return "true";
      } else {
        throw new RuntimeException("403-No mutante");
      }
    } catch (ADNException e) {
      throw new RuntimeException("400", e);
    }
  }

}
