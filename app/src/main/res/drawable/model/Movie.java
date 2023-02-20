package ba.sum.fpmoz.deliciours.model;

public class food {
    private String name;
    private String ingredients;
    private Double score;
    private Long year;
    private String recept;
    private String image;

    public food() {}

    public food(String name, String ingredients, Double score, Long year, String recept, String image) {
        this.name = name;
        this.ingredients = ingredients;
        this.score = score;
        this.year = year;
        this.recept = recept;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getingredients() {
        return ingredients;
    }

    public void setingredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getrecept() {
        return recept;
    }

    public void setrecept(String recept) {
        this.recept = recept;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }
}
