package br.edu.ifto.pweb.ecommerce.controller;

import br.edu.ifto.pweb.ecommerce.model.entity.Produto;
import br.edu.ifto.pweb.ecommerce.model.repository.CategoriaRepository;
import br.edu.ifto.pweb.ecommerce.model.repository.MarcaRepository;
import br.edu.ifto.pweb.ecommerce.model.repository.ProdutoRepository;
import br.edu.ifto.pweb.ecommerce.storage.StorageService;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
@Transactional
@RequestMapping("produtos")
public class ProdutoController implements ModelController<Produto, Long> {
    private final ProdutoRepository repository;
    private final MarcaRepository marcaRepository;
    private final CategoriaRepository categoriaRepository;
    private final StorageService storageService;

    public ProdutoController(ProdutoRepository repository,
                             MarcaRepository marcaRepository,
                             CategoriaRepository categoriaRepository,
                             StorageService storageService) {
        this.repository = repository;
        this.marcaRepository = marcaRepository;
        this.categoriaRepository = categoriaRepository;
        this.storageService = storageService;
    }

    @Override
    @GetMapping("/form")
    public ModelAndView form(Produto produto, ModelMap modelMap) {
        modelMap.addAttribute("marcas", marcaRepository.findAll(
                Sort.by(Sort.DEFAULT_DIRECTION, "nome")));
        modelMap.addAttribute("categorias", categoriaRepository.findAll(
                Sort.by(Sort.DEFAULT_DIRECTION, "descricao")));

        return new ModelAndView("/produtos/form");
    }

    @Override
    @GetMapping("/list")
    public ModelAndView list(ModelMap modelMap) {
        modelMap.addAttribute("produtos", repository.findAll(
                Sort.by(Sort.DEFAULT_DIRECTION, "descricao")));
        modelMap.addAttribute("categorias", categoriaRepository.findAll());

        return new ModelAndView("/produtos/list", modelMap);
    }

    @GetMapping("/filter/categoria/{id}")
    public ModelAndView filterByCategoria(@PathVariable String id, ModelMap modelMap) {
        modelMap.addAttribute("categoriaId", Long.valueOf(id));
        modelMap.addAttribute("categorias", categoriaRepository.findAll());
        modelMap.addAttribute("produtos", repository.findAllByCategorias_Id(Long.valueOf(id)));

        return new ModelAndView("/produtos/list", modelMap);
    }

    @Override
    public ModelAndView create(@Valid Produto produto, BindingResult result) {
        throw new NotYetImplementedException();
    }

    @PostMapping("/create")
    public ModelAndView create(@Valid Produto produto,
                               @RequestParam("file") MultipartFile multipartFile,
                               BindingResult result) {
        if (result.hasErrors()) {
            return form(produto, new ModelMap());
        }

        String fileName = multipartFile.getOriginalFilename();
        storageService.store(multipartFile);
        produto.setImagem(fileName);
        repository.save(produto);

        return new ModelAndView("redirect:/produtos/list");
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @Override
    @GetMapping("/recover/{id}")
    public ModelAndView recover(@PathVariable("id") Long id, ModelMap modelMap) {
        modelMap.addAttribute("produto", repository.findById(id).orElseThrow());
        return form(repository.findById(id).orElseThrow(), modelMap);
    }

    @Override
    public ModelAndView update(Produto model, BindingResult result) {
        throw new NotYetImplementedException();
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid Produto produto,
                               @RequestParam("file") MultipartFile multipartFile,
                               BindingResult result) {
        if (result.hasErrors()) {
            return form(produto, new ModelMap());
        }

        Produto p = repository.getReferenceById(produto.getId());

        if (!multipartFile.isEmpty()) {
            String fileName = multipartFile.getOriginalFilename();
            storageService.store(multipartFile);
            produto.setImagem(fileName);

        } else
            produto.setImagem(p.getImagem());

        repository.save(produto);

        return new ModelAndView("redirect:/produtos/list");
    }

    @Override
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        repository.deleteById(id);
        return new ModelAndView("redirect:/produtos/list");
    }
}
