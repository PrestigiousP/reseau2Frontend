package database;

import java.util.Optional;

public interface Dao<T> {

    Optional<T> get(long id);

    void save(T t); // maybe add params : String[] params
}
