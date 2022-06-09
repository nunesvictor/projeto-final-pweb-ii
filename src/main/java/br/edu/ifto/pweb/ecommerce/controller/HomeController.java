package br.edu.ifto.pweb.ecommerce.controller;

import br.edu.ifto.pweb.ecommerce.model.entity.Produto;
import br.edu.ifto.pweb.ecommerce.model.repository.CategoriaRepository;
import br.edu.ifto.pweb.ecommerce.model.repository.MarcaRepository;
import br.edu.ifto.pweb.ecommerce.model.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.List;

@Controller
@Transactional
@RequestMapping("/")
public class HomeController {
    private final ProdutoRepository repository;
    private final MarcaRepository marcaRepository;
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public HomeController(ProdutoRepository repository,
                          MarcaRepository marcaRepository, CategoriaRepository categoriaRepository) {
        this.repository = repository;
        this.marcaRepository = marcaRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping("/")
    public ModelAndView home(ModelMap modelMap) {
        modelMap.addAttribute("produtos", repository.findAllByActiveTrue(
                Sort.by(Sort.Direction.DESC, "updatedAt")));
        modelMap.addAttribute("marcas", marcaRepository.findAllByActiveTrue(
                Sort.by(Sort.DEFAULT_DIRECTION, "nome")));
        modelMap.addAttribute("categorias", categoriaRepository.findAllByActiveTrue(
                Sort.by(Sort.DEFAULT_DIRECTION, "descricao")));

        return new ModelAndView("/home", modelMap);
    }

    @GetMapping("/filters/promocoes")
    public ModelAndView filterByPromocoes(ModelMap modelMap) {
        modelMap.addAttribute("produtos", repository.findAllByActiveTrueAndPrecos_PromocaoTrue(
                Sort.by(Sort.Direction.DESC, "updatedAt")));

        modelMap.addAttribute("marcas", marcaRepository.findAllByActiveTrue(
                Sort.by(Sort.DEFAULT_DIRECTION, "nome")));
        modelMap.addAttribute("categorias", categoriaRepository.findAllByActiveTrue(
                Sort.by(Sort.DEFAULT_DIRECTION, "descricao")));

        return new ModelAndView("/home", modelMap);
    }

    @GetMapping("/filters/categoria/{id}")
    public ModelAndView filterByCategoria(ModelMap modelMap, @PathVariable("id") Long id) {
        modelMap.addAttribute("produtos", repository.findAllByActiveTrueAndCategoriasId(
                id, Sort.by(Sort.Direction.DESC, "updatedAt")));

        modelMap.addAttribute("marcas", marcaRepository.findAllByActiveTrue(
                Sort.by(Sort.DEFAULT_DIRECTION, "nome")));
        modelMap.addAttribute("categorias", categoriaRepository.findAllByActiveTrue(
                Sort.by(Sort.DEFAULT_DIRECTION, "descricao")));

        return new ModelAndView("/home", modelMap);
    }

    @GetMapping("/filters/marca/{id}")
    public ModelAndView filterByMarca(ModelMap modelMap, @PathVariable("id") Long id) {
        modelMap.addAttribute("produtos", repository.findAllByActiveTrueAndMarcaId(
                id, Sort.by(Sort.Direction.DESC, "updatedAt")));

        modelMap.addAttribute("marcas", marcaRepository.findAllByActiveTrue(
                Sort.by(Sort.DEFAULT_DIRECTION, "nome")));
        modelMap.addAttribute("categorias", categoriaRepository.findAllByActiveTrue(
                Sort.by(Sort.DEFAULT_DIRECTION, "descricao")));

        return new ModelAndView("/home", modelMap);
    }
}
