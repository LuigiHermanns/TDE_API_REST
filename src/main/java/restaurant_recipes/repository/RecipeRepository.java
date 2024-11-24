// Package
package restaurant_recipes.repository;

// BigQuery
import com.google.cloud.bigquery.*;

// Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// Classes
import restaurant_recipes.model.Recipe;

// Java
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Repository
public class RecipeRepository {

    private final BigQuery bigQuery;
    private final String datasetName = "api_dataset"; 
    private final String tableName = "recipes"; 

    @Autowired
    public RecipeRepository(BigQuery bigQuery) {
        this.bigQuery = bigQuery;
    }

    public List<Recipe> getAll() {
        String query = String.format("SELECT * FROM `%s.%s`", datasetName, tableName);
        QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();
        List<Recipe> recipes = new ArrayList<>();

        try {
            Job job = bigQuery.create(JobInfo.newBuilder(queryConfig).build());
            job = job.waitFor();

            if (job.getStatus().getError() != null) {
                throw new RuntimeException("BigQuery job failed: " + job.getStatus().getError());
            }

            TableResult result = job.getQueryResults();
            for (FieldValueList row : result.iterateAll()) {
                Recipe recipe = new Recipe();
                recipe.setId(row.get("id").getLongValue());
                recipe.setPlateName(row.get("plateName").getStringValue());
                recipe.setPrice(row.get("price").getDoubleValue());
                recipe.setRestaurantId(row.get("restaurantId").getLongValue());
                recipes.add(recipe);
            }
        } catch (InterruptedException | JobException e) {
            throw new RuntimeException("Error querying BigQuery", e);
        }
        return recipes;
    }

    public Optional<Recipe> findById(Long id) {
        String query = String.format("SELECT * FROM `%s.%s` WHERE id = %d", datasetName, tableName, id);
        QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();

        try {
            Job job = bigQuery.create(JobInfo.newBuilder(queryConfig).build());
            job = job.waitFor();

            if (job.getStatus().getError() != null) {
                throw new RuntimeException("BigQuery job failed: " + job.getStatus().getError());
            }

            TableResult result = job.getQueryResults();
            if (result.getTotalRows() > 0) {
                FieldValueList row = result.iterateAll().iterator().next();
                Recipe recipe = new Recipe();
                recipe.setId(row.get("id").getLongValue());
                recipe.setPlateName(row.get("plateName").getStringValue());
                recipe.setPrice(row.get("price").getDoubleValue());
                recipe.setRestaurantId(row.get("restaurantId").getLongValue());
                return Optional.of(recipe);
            }
        } catch (InterruptedException | JobException e) {
            throw new RuntimeException("Error querying BigQuery", e);
        }
        return Optional.empty();
    }

    public Recipe insert(Recipe recipe) {
        String query;
            query = String.format(Locale.US,"INSERT INTO `%s.%s` (id, plateName, price, RestaurantId) VALUES (%d, '%s', %.6f, %d)",
            datasetName, 
            tableName, 
            recipe.getId(), 
            recipe.getPlateName(), 
            recipe.getPrice(), 
            recipe.getRestaurantId());

        try {
            System.out.println("Essa é a query: " + query);
            QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();
            Job job = bigQuery.create(JobInfo.newBuilder(queryConfig).build());
            job.waitFor();
        } catch (InterruptedException | JobException e) {
            throw new RuntimeException("Error saving to BigQuery", e);
        }
        return recipe;
    }

    public Recipe update(Recipe recipe) {
        String query;
        query = String.format(Locale.US,"UPDATE `%s.%s` SET id = %d, plateName = '%s', price = %.6f , RestaurantId = %d WHERE id = %d",
        datasetName, 
        tableName, 
        recipe.getId(), 
        recipe.getPlateName(), 
        recipe.getPrice(), 
        recipe.getRestaurantId(),
        recipe.getId());

        try {
            QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();
            Job job = bigQuery.create(JobInfo.newBuilder(queryConfig).build());
            job.waitFor();
        } catch (InterruptedException | JobException e) {
            throw new RuntimeException("Error saving to BigQuery", e);
        }
        return recipe;
    }

    public void deleteById(Long id) {
        String query = String.format("DELETE FROM `%s.%s` WHERE id = %d", datasetName, tableName, id);

        try {
            QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();
            Job job = bigQuery.create(JobInfo.newBuilder(queryConfig).build());
            job.waitFor();
        } catch (InterruptedException | JobException e) {
            throw new RuntimeException("Error deleting from BigQuery", e);
        }
    }
}
    
