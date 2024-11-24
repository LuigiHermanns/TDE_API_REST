package restaurant_recipes.service;

import restaurant_recipes.model.Restaurant;
import restaurant_recipes.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    // Retrieve all restaurants from BigQuery
    public List<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }

    // Find a restaurant by ID
    public Optional<Restaurant> findById(Long id) {
        return restaurantRepository.findById(id);
    }

    public Restaurant insert(Restaurant restaurant) {
        return restaurantRepository.insert(restaurant);
    }

    public Restaurant update(Restaurant restaurant) {
        return restaurantRepository.update(restaurant);
    }

    // Delete a restaurant by ID
    public void deleteById(Long id) {
        restaurantRepository.deleteById(id);
    }
}
