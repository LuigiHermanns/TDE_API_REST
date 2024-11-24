package restaurant_recipes.model;

public class Recipe {
    private Long id;
    private String plateName;
    private Double price;
    private Long restaurantID;

    public Recipe() {
    }

    public Recipe(Long id, String plateName, Double price, Long restaurantID) {
        this.id = id;
        this.plateName = plateName;
        this.price = price;
        this.restaurantID = restaurantID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlateName() {
        return plateName;
    }

    public void setPlateName(String plateName) {
        this.plateName = plateName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getRestaurantId() {
        return restaurantID;
    }

    public void setRestaurantId(Long restaurantID) {
        this.restaurantID = restaurantID;
    }
}
