package com.szabodev.webflux.rest.controller;

import com.szabodev.webflux.rest.domain.Category;
import com.szabodev.webflux.rest.repository.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class CategoryControllerTest {

    private WebTestClient webTestClient;
    private CategoryRepository categoryRepository;

    @Before
    public void setUp() {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        CategoryController controller = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(controller).build();
    }

    @Test
    public void list() {
        given(categoryRepository.findAll())
                .willReturn(Flux.just(
                        Category.builder().description("Cat1").build(),
                        Category.builder().description("Cat2").build())
                );

        webTestClient.get()
                .uri("/api/v1/categories/")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    public void getById() {
        given(categoryRepository.findById("1"))
                .willReturn(Mono.just(Category.builder().description("Cat").build()));

        webTestClient.get()
                .uri("/api/v1/categories/1")
                .exchange()
                .expectBody(Category.class);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testCreateCateogry() {
        given(categoryRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Category.builder().description("desc").build()));

        Mono<Category> categoryToSave = Mono.just(Category.builder().description("Some Cat").build());

        webTestClient.post()
                .uri("/api/v1/categories")
                .body(categoryToSave, Category.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void testUpdate() {
        given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> catToUpdateMono = Mono.just(Category.builder().description("Some Cat").build());

        webTestClient.put()
                .uri("/api/v1/categories/id1")
                .body(catToUpdateMono, Category.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void testPatchWithChanges() {
        given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().build()));

        given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> catToUpdateMono = Mono.just(Category.builder().description("New Description").build());

        webTestClient.patch()
                .uri("/api/v1/categories/id2")
                .body(catToUpdateMono, Category.class)
                .exchange()
                .expectStatus()
                .isOk();

        verify(categoryRepository).save(any());
    }

    @Test
    public void testPatchNoChanges() {
        given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().description("desc").build()));

        given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> catToUpdateMono = Mono.just(Category.builder().description("desc").build());

        webTestClient.patch()
                .uri("/api/v1/categories/id2")
                .body(catToUpdateMono, Category.class)
                .exchange()
                .expectStatus()
                .isOk();

        verify(categoryRepository, never()).save(any());
    }
}