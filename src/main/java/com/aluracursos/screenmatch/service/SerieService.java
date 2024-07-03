package com.aluracursos.screenmatch.service;

import com.aluracursos.screenmatch.dto.EpisodioDTO;
import com.aluracursos.screenmatch.dto.SerieDTO;
import com.aluracursos.screenmatch.model.Categoria;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.model.Serie;
import com.aluracursos.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {
    @Autowired
    private SerieRepository repository;
    public List<SerieDTO> obtenerTodasLasSeries(){
        return convertirDatos(repository.findAll());
    }

    public List<SerieDTO> obtenerTop5() {
        return convertirDatos(repository.findTop5ByOrderByEvaluacionDesc());
    }

    public List<SerieDTO> obtenerLanzamientosMasRecientes(){
        return convertirDatos(repository.lanzamientosMasrecientes());
    }
    public List<SerieDTO> convertirDatos(List<Serie> serie){
        return serie.stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getEvaluacion(), s.getPoster(),
                        s.getGenero(), s.getActores(), s.getSinopsis()))
                .collect(Collectors.toList());
    }

    public SerieDTO obtenerPorId(Long id) {
        Optional <Serie>serie= repository.findById(id);
        if (serie.isPresent()){
            Serie s= serie.get();
            return new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getEvaluacion(), s.getPoster(),
                    s.getGenero(), s.getActores(), s.getSinopsis());
        }
        return null;
    }
    public List<EpisodioDTO> obtenerTodasLasTemporadas(Long id) {
        Optional <Serie>serie= repository.findById(id);
        if (serie.isPresent()){
            Serie s= serie.get();
            return s.getEpisodios().stream().map(e->new EpisodioDTO(e.getTemporada(), e.getTitulo(),
                    e.getNumeroEpisodio())).collect(Collectors.toList());
        }
        return null;
    }

    public List<EpisodioDTO> obtenerTemporadasporNumero(Long id,Long numeroTemporada) {

        return repository.obtenerTemporadasPorNumero(id,numeroTemporada).stream()
                .map(e->new EpisodioDTO(e.getTemporada(), e.getTitulo(),
                        e.getNumeroEpisodio())).collect(Collectors.toList());
    }

    public List<SerieDTO> obtenerSeriesPorCategoria(String nombreGenero) {
        Categoria categoria= Categoria.fromEspanol(nombreGenero);
        return convertirDatos(repository.findByGenero(categoria));
    }
}

//Spring Framework ofrece una amplia gama de anotaciones para el desarrollo de aplicaciones web. Aquí están algunas de las anotaciones más comunes e importantes usadas en Spring para aplicaciones web:
//@Controller: Usada para marcar una clase como un controlador en el patrón MVC (Model-View-Controller). Esta anotación se utiliza para recibir peticiones y manejar lógica de negocios.
//@RestController: Una variante de @Controller, específica para APIs RESTful. Combina las anotaciones @Controller y @ResponseBody, señalando que cada método retorna un objeto serializado directamente en JSON o XML como respuesta.
//@RequestMapping: Define mapeos entre URLs y métodos de controlador. Especifica las URLs a las que un método del controlador debe responder y los métodos HTTP correspondientes (GET, POST, PUT, DELETE, etc.).
//@GetMapping, @PostMapping, @PutMapping, @DeleteMapping: Abreviaturas para las operaciones HTTP GET, POST, PUT y DELETE, respectivamente, en métodos de controlador.
//@RequestParam: Usada para mapear los parámetros de petición HTTP a los parámetros del método del controlador.
//@PathVariable: Usada para vincular variables de plantilla de URL a parámetros de métodos de controlador.
//@RequestBody: Utilizada para mapear el cuerpo de la petición HTTP a un objeto de entrada del método del controlador.
//@ResponseBody: Indica que el valor retornado por el método del controlador debe ser usado directamente como cuerpo de la respuesta HTTP.
//@Valid y @Validated: Utilizadas para activar la validación de entrada en el lado del servidor. Generalmente combinadas con anotaciones de validación, como @NotNull, @Size, @Min, @Max, entre otras.
//@CrossOrigin: Utilizada para configurar permisos de acceso a recursos de diferentes orígenes (CORS - Cross-Origin Resource Sharing).
