package com.kalin.shopdrop.service.services.impl;

import com.kalin.shopdrop.data.models.News;
import com.kalin.shopdrop.data.repositories.NewsRepository;
import com.kalin.shopdrop.service.models.NewsServiceModel;
import com.kalin.shopdrop.service.services.NewsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository, ModelMapper modelMapper) {
        this.newsRepository = newsRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public NewsServiceModel addNews(NewsServiceModel newsServiceModel) {
        News news = this.modelMapper.map(newsServiceModel, News.class);
        this.newsRepository.saveAndFlush(news);
        return this.modelMapper.map(news, NewsServiceModel.class);
    }

    @Override
    public List<NewsServiceModel> getAll() {
        List<News> news = this.newsRepository.findAll();
        return news.stream().map(n -> this.modelMapper.map(n,NewsServiceModel.class)).collect(Collectors.toList());
    }
}
