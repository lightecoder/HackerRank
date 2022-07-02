package one.pizza;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Eager algorithm
 * @see <a href="https://en.wikipedia.org/wiki/Greedy_algorithm"/>
 *
 * For current situation we can start from sorting clients from least "food pickiness" ones to most ones.
 * For this purpose we can sort clients by size of disliked food
 * and for several clients with the same size of disliked food - sort them by size of liked food.
 * Then we can iterate over each client and verify that it mathes with previous clients
 * (none of his disliked food is not present in a result food set
 * and none of his liked food is not present in a set of previous clients' disliked food)
 *
 * Time complexity - O(n * log n) for sorting, and then O(n) - analyzing each client.
 * Additional time complexity for iterating over client's disliked food and attempting to find it in a result set,
 * as well as iterating over client's liked food and attempting to find it in a disliked food set.
 *
 * Space complexity - O(n) as we create new list with sorted clients, and additional set of disliked food.
 */
public class EagerAlgorithmAnalizer implements Analizer {

  @Override
  public Set<String> computeIngredientsOptimum(List<Client> clients) {
    List<Client> sortedClients = clients.stream()
        .sorted(Comparator.<Client>comparingInt(client -> Optional.ofNullable(client.getDislikedIngredients())
                .map(Collection::size)
                .orElse(1))
            .thenComparingInt(client -> client.getLikedIngredients().size()))
        .collect(Collectors.toList());
    Set<String> resultFood = new HashSet<>();
    Set<String> acceptedClientsDislikedFood = new HashSet<>();
    int acceptedClients = 0;
    for (Client client : sortedClients) {
      boolean resContainsDislikedFood = false;
      if (Objects.nonNull(client.getDislikedIngredients())) {
        resContainsDislikedFood = client.getDislikedIngredients().stream()
            .anyMatch(resultFood::contains);
      }
      boolean clientFoodPresentInDislikedFood = client.getLikedIngredients().stream()
          .anyMatch(acceptedClientsDislikedFood::contains);
      if (resContainsDislikedFood || clientFoodPresentInDislikedFood) {
        break;
      }
      resultFood.addAll(client.getLikedIngredients());
      if (Objects.nonNull(client.getDislikedIngredients())) {
        acceptedClientsDislikedFood.addAll(client.getDislikedIngredients());
      }
      acceptedClients++;
    }
    System.out.println("ACCEPTED CLIENTS: " + acceptedClients);
    return resultFood;
  }

}
