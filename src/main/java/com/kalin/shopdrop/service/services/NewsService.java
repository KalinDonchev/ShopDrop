package com.kalin.shopdrop.service.services;

import com.kalin.shopdrop.service.models.NewsServiceModel;

import java.util.List;

public interface NewsService {

    NewsServiceModel addNews(NewsServiceModel newsServiceModel);

    List<NewsServiceModel> getAll();
}
