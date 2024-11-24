// Package
package restaurant_recipes.repository;

// BigQuery
import com.google.cloud.bigquery.*;

// Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// Classes
import restaurant_recipes.model.Restaurant;

// Java
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RestaurantRepository {

    private final BigQuery bigQuery;
    private final String datasetName = "api_dataset"; 
    private final String tableName = "restaurants";        

    @Autowired
    public RestaurantRepository(BigQuery bigQuery) {
        this.bigQuery = bigQuery;
    }

    public List<Restaurant> getAll() {
        String query = String.format("SELECT * FROM `%s.%s`", datasetName, tableName);
        QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();
        List<Restaurant> restaurants = new ArrayList<>();

        try {
            Job job = bigQuery.create(JobInfo.newBuilder(queryConfig).build());
            job = job.waitFor();

            if (job.getStatus().getError() != null) {
                throw new RuntimeException("BigQuery job failed: " + job.getStatus().getError());
            }

            TableResult result = job.getQueryResults();
            for (FieldValueList row : result.iterateAll()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setId(row.get("id").getLongValue());
                restaurant.setName(row.get("name").getStringValue());
                restaurant.setAddress(row.get("address").getStringValue());
                restaurants.add(restaurant);
            }
        } catch (InterruptedException | JobException e) {
            throw new RuntimeException("Error querying BigQuery", e);
        }
        return restaurants;
    }

    public Optional<Restaurant> findById(Long id) {
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
                Restaurant restaurant = new Restaurant();
                restaurant.setId(row.get("id").getLongValue());
                restaurant.setName(row.get("name").getStringValue());
                restaurant.setAddress(row.get("address").getStringValue());
                return Optional.of(restaurant);
            }
        } catch (InterruptedException | JobException e) {
            throw new RuntimeException("Error querying BigQuery", e);
        }
        return Optional.empty();
    }

    public Restaurant insert(Restaurant restaurant) {
        String query;
            query = String.format("INSERT INTO `%s.%s` (id, name, address) VALUES (%d, '%s', '%s')",
            datasetName, tableName, restaurant.getId(), restaurant.getName(), restaurant.getAddress());

        try {
            QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();
            Job job = bigQuery.create(JobInfo.newBuilder(queryConfig).build());
            job.waitFor();
        } catch (InterruptedException | JobException e) {
            throw new RuntimeException("Error saving to BigQuery", e);
        }
        return restaurant;
    }

    public Restaurant update(Restaurant restaurant) {
        String query;
        query = String.format("UPDATE `%s.%s` SET name = '%s', address = '%s' WHERE id = %d",
        datasetName, tableName, restaurant.getName(), restaurant.getAddress(), restaurant.getId());

        try {
            QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();
            Job job = bigQuery.create(JobInfo.newBuilder(queryConfig).build());
            job.waitFor();
        } catch (InterruptedException | JobException e) {
            throw new RuntimeException("Error saving to BigQuery", e);
        }
        return restaurant;
    }

    // Delete a restaurant by ID from BigQuery
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
