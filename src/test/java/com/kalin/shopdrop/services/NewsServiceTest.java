package com.kalin.shopdrop.services;

import com.kalin.shopdrop.TestBase;
import com.kalin.shopdrop.data.models.News;
import com.kalin.shopdrop.data.repositories.NewsRepository;
import com.kalin.shopdrop.service.models.NewsServiceModel;
import com.kalin.shopdrop.service.services.NewsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class NewsServiceTest extends TestBase {

    @MockBean
    NewsRepository newsRepository;

    @Autowired
    NewsService newsService;


    @Test
    public void getAll_whenThereAreNews_shouldReturnAllNews() {

        List<News> news = new ArrayList<>();
        news.add(new News());
        news.add(new News());


        when(newsRepository.findAll())
                .thenReturn(news);

        List<NewsServiceModel> newsServiceModels = newsService.getAll();

        assertEquals(2, newsServiceModels.size());
    }

    @Test
    public void getAll_whenThereAreNoNews_shouldReturnEmpty() {
        when(newsRepository.findAll())
                .thenReturn(new ArrayList<>());

        List<NewsServiceModel> newsServiceModels = newsService.getAll();

        assertEquals(0, newsServiceModels.size());
    }

    @Test
    public void getById_whenIdIsCorrect_shouldReturnNews() {

        News news = new News();
        news.setId("1");

        when(newsRepository.findById("1"))
                .thenReturn(Optional.of(news));

        NewsServiceModel newsServiceModel = newsService.getById("1");

        assertEquals("1", newsServiceModel.getId());
    }

    @Test
    public void getById_whenIdIsNotCorrect_shouldThrowException() {
        when(newsRepository.findById("1"))
                .thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> newsService.getById("1"));
    }



}
