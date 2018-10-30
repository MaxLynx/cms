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

    private List<PageInfo> pagesInfo;

    public List<PageInfo> getPagesInfo(String lang) {
        if(pagesInfo != null){
            if(pagesInfo.size() == 0 || pagesInfo.get(0).getLangAsString().equals(lang)){
                return pagesInfo;
            }
            else{
                for(PageInfo pageInfo : pagesInfo){
                    pageInfo.setLang(lang);
                }
                return pagesInfo;
            }
        }
        else{
            List<Page> pages = getPages(lang);
            pagesInfo = new ArrayList<PageInfo>();
            for(Page page : pages){
                if(page.getParent() == null)
                    pagesInfo.add(PageInfo.createPageInfo(page));
            }
            return pagesInfo;
        }
    }

    public List<Page> getPages(String lang){
        List<Page> pages = new ArrayList<Page>();
        pageRepository.findAll().forEach(pages::add);
        pages.forEach(page -> page.setLang(lang));
        return pages;
    }

    public List<Page> getChildrenPages(Page page){
        List<Page> childPages = pageRepository.findByParentCodeOrdered(page.getCode(), page.getOrderType());
        for (int i = 0; i < childPages.size(); i++) {
            Page childPage = childPages.get(i);
            if(childPage.getOriginal() != null){
                if(!childPage.getCaptionEn().equals(""))
                    childPage.getOriginal().setCaptionEn(childPage.getCaptionEn());
                if(!childPage.getCaptionRu().equals(""))
                    childPage.getOriginal().setCaptionRu(childPage.getCaptionRu());
                if(!childPage.getCaptionUa().equals(""))
                    childPage.getOriginal().setCaptionUa(childPage.getCaptionUa());
                if(!childPage.getImageURL().equals(""))
                    childPage.getOriginal().setImageURL(childPage.getImageURL());
                if(!childPage.getIntroEn().equals(""))
                    childPage.getOriginal().setIntroEn(childPage.getIntroEn());
                if(!childPage.getIntroRu().equals(""))
                    childPage.getOriginal().setIntroRu(childPage.getIntroRu());
                if(!childPage.getIntroUa().equals(""))
                    childPage.getOriginal().setIntroUa(childPage.getIntroUa());
                if(!childPage.getTextEn().equals(""))
                    childPage.getOriginal().setTextEn(childPage.getTextEn());
                if(!childPage.getTextRu().equals(""))
                    childPage.getOriginal().setTextRu(childPage.getTextRu());
                if(!childPage.getTextUa().equals(""))
                    childPage.getOriginal().setTextUa(childPage.getTextUa());
                childPage.getOriginal().setCode("alias/" + childPage.getCode());
                childPages.set(i, childPage.getOriginal());
            }

        }
        return childPages;
    }

    public List<Page> setPagesLang(List<Page> pages, String lang){
        for (Page page:
             pages) {
             page.setLang(lang);
        }
        return pages;
    }

    public Page getPageById(String id){
        return pageRepository.findById(id).get();
    }


    public Page getPageByCode(String code){
        Page page = pageRepository.findPageByCode(code);
        return page;
    }

}
