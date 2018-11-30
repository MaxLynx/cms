package edu.web.cms.page;

import java.util.List;
import java.util.Map;


public interface ContentService<T> {

    List<T> getAll();

    List<T> getFirstLevel();

    List<T> getAllChildren(String parentId);

    void update(T object);

    void delete(T object) throws Exception;

    T getByField(String field, String value);

    void add(T object);

    T createBlank();

    Map<String, String[]> getObjectAsPropertyPairs(T object, String[] fieldNames);
}
