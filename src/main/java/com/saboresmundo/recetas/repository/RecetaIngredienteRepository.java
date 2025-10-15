package com.saboresmundo.recetas.repository;

import com.saboresmundo.recetas.model.RecetaIngrediente;
import com.saboresmundo.recetas.model.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RecetaIngredienteRepository extends JpaRepository<RecetaIngrediente, Long> {
    List<RecetaIngrediente> findByReceta(Receta receta);

    void deleteByReceta(Receta receta);
}