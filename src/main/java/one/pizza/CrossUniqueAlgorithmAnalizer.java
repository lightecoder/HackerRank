package one.pizza;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CrossUniqueAlgorithmAnalizer implements Analizer {

  @Override
  public Set<String> computeIngredientsOptimum(List<Client> clients) {
    Set<String> allLikedIngredients = clients.stream()
        .map(Client::getLikedIngredients)
        .flatMap(Set::stream)
        .collect(Collectors.toSet());

    Set<String> allDislikedIngredients = clients.stream()
        .map(Client::getDislikedIngredients)
        .filter(Objects::nonNull)
        .flatMap(Set::stream)
        .collect(Collectors.toSet());

    Set<String> crossUniqueLikedIngredients = new HashSet<>(allLikedIngredients);
    crossUniqueLikedIngredients.removeAll(allDislikedIngredients);

    return crossUniqueLikedIngredients;
  }

}
