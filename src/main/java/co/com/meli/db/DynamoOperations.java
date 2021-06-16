package co.com.meli.db;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.Select;

import java.util.Arrays;
import java.util.Random;

public class DynamoOperations {

  private static final  Regions REGION = Regions.US_EAST_2;
  private final DynamoDB dynamoDb;
  private static final String TABLA_ADN = "DNA_HUMAN";
  private static final String IDX_MUTANTES = "IDX_MUTANTES";

  public DynamoOperations() {
    AmazonDynamoDBClient client = new AmazonDynamoDBClient();
    client.setRegion(Region.getRegion(REGION));
    this.dynamoDb = new DynamoDB(client);
  }

  /**
   * Guarda en BD una cadena de ADN y si es o no mutante.
   *
   * @param dna Cadena de ADN a guardar
   * @param mutante si es o no mutante.
   */
  public void insertADN(String[] dna, boolean mutante) {
    Table tabla = this.dynamoDb.getTable(TABLA_ADN);
    Item itemADN = new Item();
    itemADN.with("id", new Random().nextInt());
    itemADN.with("dna", Arrays.asList(dna));
    itemADN.with("mutante", mutante ? 1 : 0);
    saveData(tabla, itemADN);
  }

  public void saveData(Table tabla, Item item) {
    tabla.putItem(item);
  }

  /**
   * Obtiene la cantidad de elementos de un indice que cumplen con el criterio
   * mutante = parmetro; donde parametro es 1 o 0, basciamente cuenta la cantidad
   * de mutantes o no muntantes con el indice.
   *
   * @param mutante 1 si es para buscar mutantes 0 no mutantes
   * @return Cantidad de mutantes o de no mutantes.
   */
  public int getECantidadADNs(int mutante) {
    Index index = this.dynamoDb.getTable(TABLA_ADN).getIndex(IDX_MUTANTES);
    QuerySpec query = new QuerySpec();
    query.withScanIndexForward(false);
    query.withKeyConditionExpression("mutante = :v_mutante");
    query.withSelect(Select.COUNT);
    query.withValueMap(new ValueMap().withInt(":v_mutante", mutante));
    ItemCollection<QueryOutcome> result = index.query(query);
    for (Page<Item, QueryOutcome> page : result.pages()) {
      //Normalmente deberia funcionar getAccumulatedItemCount, pero como no se usa esta funciona.
      return page.getLowLevelResult().getQueryResult().getCount();
    }
    //Esta seria la respuesta normalmente, pero NO FUNCIONA !!!
    return result.getAccumulatedItemCount();
  }

}
