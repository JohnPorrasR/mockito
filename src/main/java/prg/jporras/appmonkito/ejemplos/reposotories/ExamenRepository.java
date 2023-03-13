package prg.jporras.appmonkito.ejemplos.reposotories;

import prg.jporras.appmonkito.ejemplos.models.Examen;

import java.util.List;

public interface ExamenRepository {
  Examen guardar(Examen examen);
  List<Examen> findAll();
}
