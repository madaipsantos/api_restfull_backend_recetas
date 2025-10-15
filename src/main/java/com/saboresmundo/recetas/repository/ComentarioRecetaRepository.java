package com.saboresmundo.recetas.repository;

import com.saboresmundo.recetas.model.ComentarioReceta;
import com.saboresmundo.recetas.model.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComentarioRecetaRepository extends JpaRepository<ComentarioReceta, Long> {
    List<ComentarioReceta> findByReceta(Receta receta);
}
