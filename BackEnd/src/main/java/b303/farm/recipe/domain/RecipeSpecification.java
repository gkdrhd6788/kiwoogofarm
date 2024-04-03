package b303.farm.recipe.domain;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class RecipeSpecification {
    public static Specification<Recipe> containingIngredients(String ingredients) {
        return new Specification<Recipe>() {
            @Override
            public Predicate toPredicate(Root<Recipe> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("ingredients"), "%" + ingredients + "%");
            }
        };
    }

    public static Specification<Recipe> containingCook(String cook) {
        return new Specification<Recipe>() {
            @Override
            public Predicate toPredicate(Root<Recipe> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("cook"), "%" + cook + "%");
            }
        };
    }

    public static Specification<Recipe> containingDifficulty(String difficulty) {
        return new Specification<Recipe>() {
            @Override
            public Predicate toPredicate(Root<Recipe> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("difficulty"), "%" + difficulty + "%");
            }
        };
    }

    public static Specification<Recipe> orderByLikesDescending() {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.desc(root.get("likes")));
            return criteriaBuilder.conjunction(); // or any other Predicate if needed
        };
    }

}
