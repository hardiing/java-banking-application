/**
   Murach, J. (2017). Murachs Java Programming, Training and Reference, 
   5th Edition, Fresno, CA: Mike Murach & Associates. Inc.
 */

import java.util.List;

public interface DAO<T> {
    T get(String code);
    List<T> getAll();
    boolean add(T t);
    boolean update(T t);
    boolean delete(T t);
}
