package tacos.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tacos.model.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Iterable<Ingredient> findAll() {
        String query = "SELECT * FROM ingredient";
        return jdbcTemplate.query(query, this::rowMapper);
    }

    @Override
    public Optional<Ingredient> findById(String id) {
        String query = "SELECT * FROM ingredient WHERE id = ?";
        List<Ingredient> ingredient = jdbcTemplate.query(query, this::rowMapper, id);
        return (ingredient.isEmpty()) ? Optional.empty() : Optional.of(ingredient.get(0));
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        String query = "INSERT INTO ingredient (id, name, type) VALUES (?, ?, ?)";
        jdbcTemplate.update(query, ingredient.getId(), ingredient.getName(), ingredient.getType().toString());
        return ingredient;
    }

    private Ingredient rowMapper(ResultSet row, int rowNum) throws SQLException {
        return new Ingredient(
                row.getString(1),
                row.getString(2),
                Ingredient.Type.valueOf(row.getString(3))
        );
    }
}
