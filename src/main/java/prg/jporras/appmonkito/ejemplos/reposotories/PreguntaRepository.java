package prg.jporras.appmonkito.ejemplos.reposotories;

import java.util.List;

public interface PreguntaRepository {
  void guardarVarias(List<String> preguntas);
  List<String> findPreguntasPorExamenId(Long id);
}
