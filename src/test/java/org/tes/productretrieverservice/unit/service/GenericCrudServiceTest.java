package org.tes.productretrieverservice.unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.tes.productretrieverservice.exception.ModelIsNullException;
import org.tes.productretrieverservice.exception.ModelNotFoundException;
import org.tes.productretrieverservice.service.GenericCrudService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GenericCrudServiceTest {

    @Mock
    private JpaRepository<TestEntity, Long> repository;

    private GenericCrudService<TestEntity, Long> service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new TestGenericCrudService(repository);
    }

    @Test
    void testSave() {
        // Given
        TestEntity entity = new TestEntity(1L, "test");
        when(repository.save(entity)).thenReturn(entity);

        // When
        TestEntity result = service.save(Optional.of(entity));

        // Then
        assertEquals(entity, result);
        verify(repository).save(entity);
    }

    @Test
    void testSaveNullEntity() {
        // Given, When & Then
        assertThrows(ModelIsNullException.class, () -> service.save(Optional.empty()));
    }

    @Test
    void testFindAll() {
        // Given
        List<TestEntity> entities = Arrays.asList(
                new TestEntity(1L, "test1"),
                new TestEntity(2L, "test2")
        );
        when(repository.findAll()).thenReturn(entities);

        // When
        List<TestEntity> result = service.findAll();

        // Then
        assertEquals(entities, result);
        verify(repository).findAll();
    }

    @Test
    void testFindById() {
        // Given
        TestEntity entity = new TestEntity(1L, "test");
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        // When
        TestEntity result = service.findById(1L);

        // Then
        assertEquals(entity, result);
        verify(repository).findById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        // Given
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ModelNotFoundException.class, () -> service.findById(1L));
    }

    @Test
    void testUpdate() {
        // Given
        TestEntity entity = new TestEntity(1L, "test");
        when(repository.save(entity)).thenReturn(entity);

        // When
        TestEntity result = service.update(Optional.of(entity));

        // Then
        assertEquals(entity, result);
        verify(repository).save(entity);
    }

    @Test
    void testUpdateNullEntity() {
        // Given, When & Then
        assertThrows(ModelIsNullException.class, () -> service.update(Optional.empty()));
    }

    @Test
    void testDeleteById() {
        // Given, When
        service.deleteById(1L);

        // Then
        verify(repository).deleteById(1L);
    }

    // Test entity class
    private static class TestEntity {
        private Long id;
        private String name;

        public TestEntity(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    // Concrete implementation of GenericCrudService for testing
    private static class TestGenericCrudService extends GenericCrudService<TestEntity, Long> {
        public TestGenericCrudService(JpaRepository<TestEntity, Long> repository) {
            super(repository);
        }
    }
}
