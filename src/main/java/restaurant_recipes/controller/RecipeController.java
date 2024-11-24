// Package
package restaurant_recipes.controller;

// Spring
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Classes
import restaurant_recipes.model.Recipe;
import restaurant_recipes.repository.RecipeRepository;

// Java
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeRepository recipeRepository;

    public RecipeController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @GetMapping(produces = {"application/json", "application/xml"})
    public List<Recipe> getAllRecipes() {
        return recipeRepository.getAll();
    }

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        return recipeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> createRecipe(@RequestBody Recipe recipe) {
        recipeRepository.insert(recipe);
        return new ResponseEntity<>("Receita criado com sucesso!", HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> updateRecipe(@PathVariable Long id, @RequestBody Recipe recipe) {
       Optional<Recipe> existingRecipe = recipeRepository.findById(id);
        if (existingRecipe.isPresent()) {
            recipe.setId(id);
            recipeRepository.update(recipe);
            return new ResponseEntity<>("Receita atualizado com sucesso!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Receita não encontrado", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable Long id) {
        if (recipeRepository.findById(id).isPresent()) {
            recipeRepository.deleteById(id);
            return new ResponseEntity<>("Receita removido com sucesso!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Receita não encontrado", HttpStatus.NOT_FOUND);
    }
}
