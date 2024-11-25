// Package
package restaurant_recipes.controller;

// Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Classes
import restaurant_recipes.model.Restaurant;
import restaurant_recipes.service.RestaurantService;

// Java
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restauranteService) {
        this.restaurantService = restauranteService;
    }

    @GetMapping(produces = {"application/json", "application/xml"})
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAll();
    }

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        Optional<Restaurant> restaurante = restaurantService.findById(id);
        return restaurante.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> createRestaurant(@RequestBody Restaurant restaurant) {
        restaurantService.insert(restaurant);
        return new ResponseEntity<>("Restaurante criado com sucesso!", HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = {"application/json", "application/xml"})
        public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long id, @RequestBody Restaurant restaurant) {
            Optional<Restaurant> existingRestaurant = restaurantService.findById(id);
            if (existingRestaurant.isPresent()) {
                restaurant.setId(id);
                Restaurant updatedRestaurant = restaurantService.update(restaurant);
                return ResponseEntity.ok(updatedRestaurant);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable Long id) {
        if (restaurantService.findById(id).isPresent()) {
            restaurantService.deleteById(id);
            return new ResponseEntity<>("Restaurante excluído com sucesso!", HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
