package edu.web.cms.page;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Interaction with database for CRUD operations on webpages
 */
interface PageRepository extends CrudRepository<Page, String> {
    Page findPageByCode(String code);

    default List<Page> findByParentCodeOrdered(String parentCode, String orderField){
        List<Page> pages;
        switch(orderField){
            case "OrderPosition": pages = findByParentCodeOrderByOrderPositionAsc(parentCode); break;
            case "CaptionEn": pages = findByParentCodeOrderByCaptionEnAsc(parentCode); break;
            default: pages = findByParentCodeOrderByOrderPositionAsc(parentCode); break;
        }
        return pages;
    }

    List<Page> findByParentCodeOrderByOrderPositionAsc(String parentCode);

    List<Page> findByParentCodeOrderByCaptionEnAsc(String parentCode);
}
