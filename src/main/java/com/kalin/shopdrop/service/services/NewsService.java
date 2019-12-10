package com.kalin.shopdrop.service.services;

import com.kalin.shopdrop.service.models.NewsServiceModel;
import javassist.NotFoundException;

import java.util.List;

public interface NewsService {

    NewsServiceModel addNews(NewsServiceModel newsServiceModel);

    void deleteNews(String id);

    List<NewsServiceModel> getAll();

    NewsServiceModel getById(String id) throws NotFoundException;

    NewsServiceModel editNews(String id, NewsServiceModel newsServiceModel);

}
