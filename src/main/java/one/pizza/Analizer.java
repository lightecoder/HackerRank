package one.pizza;

import java.util.List;
import java.util.Set;

public interface Analizer {

  Set<String> computeIngredientsOptimum(List<Client> clients);
}
