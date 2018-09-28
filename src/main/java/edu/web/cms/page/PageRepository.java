package edu.web.cms.page;

import org.springframework.data.repository.CrudRepository;

/**
 * Interaction with database for CRUD operations on webpages
 */
interface PageRepository extends CrudRepository<Page, String> {
    Page findPageByCode(String code);
}
