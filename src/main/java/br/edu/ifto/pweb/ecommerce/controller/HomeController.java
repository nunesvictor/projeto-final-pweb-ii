package br.edu.ifto.pweb.ecommerce.controller;

import br.edu.ifto.pweb.ecommerce.model.repository.CategoriaRepository;
import br.edu.ifto.pweb.ecommerce.model.repository.MarcaRepository;
import br.edu.ifto.pweb.ecommerce.model.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

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
        modelMap.addAttribute("produtos", repository.findActive(
                Sort.by(Sort.Direction.DESC, "updatedAt")));
        modelMap.addAttribute("marcas", marcaRepository.findActive(
                Sort.by(Sort.DEFAULT_DIRECTION, "nome")));
        modelMap.addAttribute("categorias", categoriaRepository.findActive(
                Sort.by(Sort.DEFAULT_DIRECTION, "descricao")));

        return new ModelAndView("/home", modelMap);
    }
}
