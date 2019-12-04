package com.kalin.shopdrop.web.controllers;

import com.kalin.shopdrop.service.models.NewsServiceModel;
import com.kalin.shopdrop.service.services.NewsService;
import com.kalin.shopdrop.web.models.AddNewsModel;
import com.kalin.shopdrop.web.models.view.NewsViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("news")
public class NewsController extends BaseController{

    private final NewsService newsService;
    private final ModelMapper modelMapper;

    @Autowired
    public NewsController(NewsService newsService, ModelMapper modelMapper) {
        this.newsService = newsService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public ModelAndView addNews() {
        return super.view("news/add-news");
    }

    @PostMapping("/add")
    public ModelAndView addReviewConfirm(@ModelAttribute AddNewsModel addNewsModel) {
        NewsServiceModel newsServiceModel = this.modelMapper.map(addNewsModel, NewsServiceModel.class);
        this.newsService.addNews(newsServiceModel);
        return super.redirect("/news/all");
    }

    @GetMapping("/all")
    public ModelAndView allReviews(ModelAndView modelAndView) {
        List<NewsServiceModel> news = this.newsService.getAll();
        List<NewsViewModel> models = news.stream().map(n -> this.modelMapper.map(n, NewsViewModel.class)).collect(Collectors.toList());
        modelAndView.addObject("allNews", models);
        return super.view("news/all-news", modelAndView);
    }



}
