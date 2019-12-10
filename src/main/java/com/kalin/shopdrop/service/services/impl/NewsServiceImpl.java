package com.kalin.shopdrop.service.services.impl;

import com.kalin.shopdrop.data.models.News;
import com.kalin.shopdrop.data.repositories.NewsRepository;
import com.kalin.shopdrop.errors.NewsNotFoundException;
import com.kalin.shopdrop.service.models.NewsServiceModel;
import com.kalin.shopdrop.service.services.NewsService;
import javassist.NotFoundException;
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
    public void deleteNews(String id){
        News news = this.newsRepository.findById(id).orElseThrow(() -> new NewsNotFoundException("No such news"));
        this.newsRepository.delete(news);
    }

    @Override
    public List<NewsServiceModel> getAll() {
        List<News> news = this.newsRepository.findAll();
        return news.stream().map(n -> this.modelMapper.map(n, NewsServiceModel.class)).collect(Collectors.toList());
    }

    @Override
    public NewsServiceModel getById(String id) {
        News news = this.newsRepository.findById(id).orElseThrow(() -> new NewsNotFoundException("No such news"));
        return this.modelMapper.map(news, NewsServiceModel.class);
    }

    @Override
    public NewsServiceModel editNews(String id, NewsServiceModel newsServiceModel) {
        News news = this.newsRepository.findById(id).orElseThrow(() -> new NewsNotFoundException("No such news"));
        news.setTitle(newsServiceModel.getTitle());
        news.setText(newsServiceModel.getText());
        this.newsRepository.save(news);
        return this.modelMapper.map(news, NewsServiceModel.class);
    }


}
