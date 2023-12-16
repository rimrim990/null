package com.project.undefined.acceptance.utils;

import java.util.List;
import java.util.function.Function;
import org.springframework.data.jpa.repository.JpaRepository;

public class DataUtils {

    public static <T> T findAny(final JpaRepository<T, Long> repository) {
        return repository.findAll()
            .stream()
            .findAny()
            .get();
    }

    public static <T> Long findAnyId(final JpaRepository<T, Long> repository, final Function<T, Long> idExtractor) {
        return findAllIds(repository, idExtractor).get(0);
    }

    public static <T> List<Long> findAllIds(final JpaRepository<T, Long> repository, final Function<T, Long> idExtractor) {
        final List<T> result = repository.findAll();
        return result.stream()
            .map(idExtractor)
            .toList();
    }

    private DataUtils() {}
}
