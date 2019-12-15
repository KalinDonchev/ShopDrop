package com.kalin.shopdrop.web.controllers;

import com.kalin.shopdrop.config.annotations.PageTitle;
import com.kalin.shopdrop.service.models.NewsServiceModel;
import com.kalin.shopdrop.service.services.NewsService;
import com.kalin.shopdrop.web.models.AddNewsModel;
import com.kalin.shopdrop.web.models.view.NewsViewModel;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/news")
public class NewsController extends BaseController {

    private final NewsService newsService;
    private final ModelMapper modelMapper;

    @Autowired
    public NewsController(NewsService newsService, ModelMapper modelMapper) {
        this.newsService = newsService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("Add News")
    public ModelAndView addNews() {
        return super.view("news/add-news");
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addNewsConfirm(@ModelAttribute AddNewsModel addNewsModel) {
        NewsServiceModel newsServiceModel = this.modelMapper.map(addNewsModel, NewsServiceModel.class);
        this.newsService.addNews(newsServiceModel);
        return super.redirect("/news/all");
    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("All News")
    public ModelAndView allNews(ModelAndView modelAndView) {
        List<NewsServiceModel> news = this.newsService.getAll();
        List<NewsViewModel> models = news.stream().map(n -> this.modelMapper.map(n, NewsViewModel.class)).collect(Collectors.toList());
        modelAndView.addObject("allNews", models);
        return super.view("news/all-news", modelAndView);
    }

    @GetMapping("/allNews")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("Add News Admin")
    public ModelAndView allNewsModerator(ModelAndView modelAndView) {
        List<NewsServiceModel> news = this.newsService.getAll();
        List<NewsViewModel> models = news.stream().map(n -> this.modelMapper.map(n, NewsViewModel.class)).collect(Collectors.toList());
        modelAndView.addObject("allNews", models);
        return super.view("news/all-news-moderator", modelAndView);
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("Edit News")
    public ModelAndView editNews(@PathVariable String id, ModelAndView modelAndView) throws NotFoundException {
        NewsServiceModel newsServiceModel = this.newsService.getById(id);
        AddNewsModel news = this.modelMapper.map(newsServiceModel, AddNewsModel.class);
        modelAndView.addObject("news", news);
        modelAndView.addObject("newsId", id);
        return super.view("news/edit-news", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView editNewsConfirm(@PathVariable String id, @ModelAttribute AddNewsModel addNewsModel)  {
        NewsServiceModel newsServiceModel = this.modelMapper.map(addNewsModel, NewsServiceModel.class);
        this.newsService.editNews(id, newsServiceModel);
        return super.redirect("/home");

    }


    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("Delete News")
    public ModelAndView deleteNews(@PathVariable String id, ModelAndView modelAndView) {
        NewsServiceModel newsServiceModel = this.newsService.getById(id);
        AddNewsModel news = this.modelMapper.map(newsServiceModel, AddNewsModel.class);

        modelAndView.addObject("news", news);
        modelAndView.addObject("newsId", id);

        return super.view("news/delete-news", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteNewsConfirm(@PathVariable String id)  {
        this.newsService.deleteNews(id);
        return super.redirect("/home");
    }


}
