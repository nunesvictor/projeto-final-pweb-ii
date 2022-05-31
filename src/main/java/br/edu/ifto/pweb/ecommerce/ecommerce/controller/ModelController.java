package br.edu.ifto.pweb.ecommerce.ecommerce.controller;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

public interface ModelController<T, I> {
    ModelAndView form(T model, ModelMap modelMap);

    ModelAndView list(ModelMap modelMap);

    ModelAndView create(@Valid T model, BindingResult result);

    ModelAndView recover(@PathVariable("id") I id, ModelMap modelMap);

    ModelAndView update(@Valid T model, BindingResult result);

    ModelAndView delete(@PathVariable("id") I id);
}
