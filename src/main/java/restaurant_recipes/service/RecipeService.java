package restaurant_recipes.service;

import restaurant_recipes.model.Recipe;
import restaurant_recipes.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> getAll() {
        return recipeRepository.getAll();
    }

    public Optional<Recipe> findById(Long id) {
        return recipeRepository.findById(id);
    }

    public Recipe insert(Recipe recipe) {
        return recipeRepository.insert(recipe);
    }

    public Recipe update(Recipe recipe) {
        return recipeRepository.update(recipe);
    }

    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }
    
}
