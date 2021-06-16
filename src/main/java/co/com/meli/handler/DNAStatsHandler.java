package co.com.meli.handler;

import co.com.meli.db.DynamoOperations;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class DNAStatsHandler implements RequestHandler<Request, ResponseStats> {

  @Override
  public ResponseStats handleRequest(Request request, Context context) {
    return new DynamoOperations().consultarStats();
  }

}
