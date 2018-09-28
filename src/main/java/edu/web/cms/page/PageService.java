package edu.web.cms.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max on 27.09.2018.
 */
@Service
class PageService {

    @Autowired
    private PageRepository pageRepository;

    public List<Page> getPages(String lang){
        List<Page> pages = new ArrayList<Page>();
        pageRepository.findAll().forEach(pages::add);
        pages.forEach(page -> page.setLang(lang));
        return pages;
    }

    public Page getPageById(String id){
        return pageRepository.findById(id).get();
    }


    public Page getPageByCode(String code){
        return pageRepository.findPageByCode(code);
    }

}
