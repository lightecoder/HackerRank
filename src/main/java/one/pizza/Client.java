package one.pizza;

import java.util.Objects;
import java.util.Set;

public class Client {
    private Set<String> likedIngredients;
    private Set<String> dislikedIngredients;

    public Set<String> getLikedIngredients() {
        return likedIngredients;
    }

    public void setLikedIngredients(Set<String> likedIngredients) {
        this.likedIngredients = likedIngredients;
    }

    public Set<String> getDislikedIngredients() {
        return dislikedIngredients;
    }

    public void setDislikedIngredients(Set<String> dislikedIngredients) {
        this.dislikedIngredients = dislikedIngredients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(likedIngredients, client.likedIngredients) && Objects.equals(dislikedIngredients, client.dislikedIngredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(likedIngredients, dislikedIngredients);
    }

    @Override
    public String toString() {
        return "Client{" +
                "likedIngredients=" + likedIngredients +
                ", dislikedIngredients=" + dislikedIngredients +
                '}'
                +'\n';
    }
}
