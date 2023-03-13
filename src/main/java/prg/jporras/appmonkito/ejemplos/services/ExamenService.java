package prg.jporras.appmonkito.ejemplos.services;

import prg.jporras.appmonkito.ejemplos.models.Examen;

import java.util.Optional;

public interface ExamenService {
  Optional<Examen> findExamenPorNombre(String nombre);
  Examen findExamenPorNombreConPreguntas(String nombre);

  Examen guardar(Examen examen);
}
