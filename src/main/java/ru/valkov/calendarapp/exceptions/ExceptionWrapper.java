package ru.valkov.calendarapp.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.util.function.*;

@Slf4j
public class ExceptionWrapper {
    public static <T, R> ResponseEntity<R> wrap(Function<T, R> function, T arg) {
        try {
            return ResponseEntity.ok(function.apply(arg));
        } catch (NotFoundException e) {
            log.error("Not found", e);
            return ResponseEntity.notFound().build();
        } catch (BadRequestException e) {
            log.error("Bad request", e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Internal server error", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    public static <T, K, R> ResponseEntity<R> wrap(BiFunction<T, K, R> biFunction, T arg1, K arg2) {
        try {
            return ResponseEntity.ok(biFunction.apply(arg1, arg2));
        } catch (NotFoundException e) {
            log.error("Not found", e);
            return ResponseEntity.notFound().build();
        } catch (BadRequestException e) {
            log.error("Bad request", e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Internal server error", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    public static <T> ResponseEntity<T> wrap(Supplier<T> function) {
        try {
            return ResponseEntity.ok(function.get());
        } catch (NotFoundException e) {
            log.error("Not found", e);
            return ResponseEntity.notFound().build();
        } catch (BadRequestException e) {
            log.error("Bad request", e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Internal server error", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    public static <A,B,C,R> ResponseEntity<R> wrap(TriFunction<A,B,C,R> function, A arg1, B arg2, C arg3) {
        try {
            return ResponseEntity.ok(function.get(arg1, arg2, arg3));
        } catch (NotFoundException e) {
            log.error("Not found", e);
            return ResponseEntity.notFound().build();
        } catch (BadRequestException e) {
            log.error("Bad request", e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Internal server error", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    public static <T> ResponseEntity<Void> wrapWithoutResult(Consumer<T> function, T arg) {
        try {
            function.accept(arg);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            log.error("Not found", e);
            return ResponseEntity.notFound().build();
        } catch (BadRequestException e) {
            log.error("Bad request", e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Internal server error", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    public static  <T, R> ResponseEntity<Void> wrapWithoutResult(BiConsumer<T, R> function, T arg1, R arg2) {
        try {
            function.accept(arg1, arg2);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            log.error("Not found", e);
            return ResponseEntity.notFound().build();
        } catch (BadRequestException e) {
            log.error("Bad request", e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Internal server error", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    public static  <T, K, R> ResponseEntity<Void> wrapWithoutResult(TriConsumer<T, K, R> function, T arg1, K arg2, R arg3) {
        try {
            function.apply(arg1, arg2, arg3);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            log.error("Not found", e);
            return ResponseEntity.notFound().build();
        } catch (BadRequestException e) {
            log.error("Bad request", e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Internal server error", e);
            return ResponseEntity.internalServerError().build();
        }
    }

  public interface TriFunction<A,B,C,R> {
        R get(A a, B b, C c);
    }

    public interface TriConsumer<A,B,C> {
        void apply(A a, B b, C c);
    }
}
