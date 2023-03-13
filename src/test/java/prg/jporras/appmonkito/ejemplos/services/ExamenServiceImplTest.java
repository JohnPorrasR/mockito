package prg.jporras.appmonkito.ejemplos.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import prg.jporras.appmonkito.ejemplos.models.Examen;
import prg.jporras.appmonkito.ejemplos.reposotories.ExamenRepository;
import prg.jporras.appmonkito.ejemplos.reposotories.PreguntaRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class ExamenServiceImplTest {

  @Mock
  ExamenRepository repository;
  @Mock
  PreguntaRepository preguntaRepository;
  @InjectMocks
  ExamenServiceImpl service;

  @BeforeEach
  void setUp() {
//    MockitoAnnotations.initMocks(this);
//    repository = Mockito.mock(ExamenRepositoryOtro.class);
//    preguntaRepository = Mockito.mock(PreguntaRepository.class);
//    service = new ExamenServiceImpl(repository, preguntaRepository);
  }

  @Test
  void findExamenPorNombre() {

    Mockito.when(repository.findAll()).thenReturn(Datos.EXAMENES);
    Optional<Examen> examen = service.findExamenPorNombre("Matematicas");

    assertNotNull(examen);
    assertTrue(examen.isPresent());
    assertEquals(5L, examen.orElseThrow(null).getId());
    assertEquals("Matematicas", examen.get().getNombre());
  }

  @Test
  void findExamenPorNombreListaVacia() {
    List<Examen> datos = Collections.emptyList();

    Mockito.when(repository.findAll()).thenReturn(datos);
    Optional<Examen> examen = service.findExamenPorNombre("Matematicas");

    assertFalse(examen.isPresent());
  }

  @Test
  void preguntasExamenesTest() {
    Mockito.when(repository.findAll()).thenReturn(Datos.EXAMENES);
    Mockito.when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
    Examen examen = service.findExamenPorNombreConPreguntas("Historia");
    assertEquals(5, examen.getPreguntas().size());
    assertTrue(examen.getPreguntas().contains("aritmetica"));
  }

  @Test
  void preguntasExamenesVerifyTest() {
    Mockito.when(repository.findAll()).thenReturn(Datos.EXAMENES);
    Mockito.when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
    Examen examen = service.findExamenPorNombreConPreguntas("Historia");
    assertEquals(5, examen.getPreguntas().size());
    assertTrue(examen.getPreguntas().contains("aritmetica"));
    Mockito.verify(repository).findAll();
    Mockito.verify(preguntaRepository).findPreguntasPorExamenId(anyLong());
  }

  @Test
  void noExisteExamenTest() {
    // Given
    Mockito.when(repository.findAll()).thenReturn(Collections.emptyList());
    // When
    Examen examen = service.findExamenPorNombreConPreguntas("Historia");
    // Then
    assertNull(examen);
    Mockito.verify(repository).findAll();
  }

  @Test
  void guardarExamenTest() {
    // Given
    Examen newExamen = Datos.EXAMEN;
    newExamen.setPreguntas(Datos.PREGUNTAS);

    Mockito.when(repository.guardar(any(Examen.class))).then(new Answer<Examen>(){

      Long secuencia = 8L;

      @Override
      public Examen answer(InvocationOnMock invocationOnMock) throws Throwable {
        Examen examen = invocationOnMock.getArgument(0);
        examen.setId(secuencia++);
        return examen;
      }
    });
    // When
    Examen examen = service.guardar(newExamen);

    // Then
    assertNotNull(examen.getId());
    assertEquals(8L, examen.getId());
    assertEquals("Fisica", examen.getNombre());
  }
}
