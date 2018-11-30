package edu.web.cms.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Max on 27.09.2018.
 */
@Service
class PageService implements ContentService<Page>{

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

    public List<Page> getAll(){
        List<Page> pages = new LinkedList<>();
        for(Page page : pageRepository.findAll()){
            pages.add(page);
        }
        return pages;
    }

    public void update(Page page){
        System.out.println(page.getImageURL());
        System.out.println(page.getOrderPosition());
        System.out.println(page.getOrderType());
        pageRepository.save(page);
    }

    public void delete(Page page) throws Exception{
        if(page.getChildrenPages().size() == 0)
            pageRepository.delete(page);
        else
        {
            List<Page> childrenPages = page.getChildrenPages();
            pageRepository.delete(page);
            for(Page childPage : childrenPages){
                delete(childPage);
            }
        }
    }

    public Page getByField(String field, String value){
        Page page = null;
        if(field.equals("code"))
            page = pageRepository.findPageByCode(value);
        return page;
    }

    public void add(Page page){
        pageRepository.save(page);
    }

    public Page createBlank() {
        return new Page("blank");
    }

    public Map<String, String[]> getObjectAsPropertyPairs(Page page, String[] fieldNames){
        Map<String, String[]> propertyPairs = new LinkedHashMap<>();
        propertyPairs.put(fieldNames[0], new String[]{page.getCode(), "code", "textarea"});
        propertyPairs.put(fieldNames[1], new String[]{page.getCaptionUa(), "captionUa", "textarea"});
        propertyPairs.put(fieldNames[2], new String[]{page.getCaptionEn(), "captionEn", "textarea"});
        propertyPairs.put(fieldNames[3], new String[]{page.getCaptionRu(), "captionRu", "textarea"});
        propertyPairs.put(fieldNames[4], new String[]{page.getIntroUa(), "introUa", "textarea"});
        propertyPairs.put(fieldNames[5], new String[]{page.getIntroEn(), "introEn", "textarea"});
        propertyPairs.put(fieldNames[6], new String[]{page.getIntroRu(), "introRu", "textarea"});
        propertyPairs.put(fieldNames[7], new String[]{page.getTextUa(), "textUa", "textarea"});
        propertyPairs.put(fieldNames[8], new String[]{page.getTextEn(), "textEn", "textarea"});
        propertyPairs.put(fieldNames[9], new String[]{page.getTextRu(), "textRu", "textarea"});
        propertyPairs.put(fieldNames[10], new String[]{""+page.getOrderPosition(), "orderPosition", "textarea"});
        propertyPairs.put(fieldNames[11], new String[]{page.getImageURL(), "imageURL", "file"});
        propertyPairs.put(fieldNames[12], new String[]{""+page.getContainerType().getValue(), "containerType", "select"});

        return propertyPairs;
    }

    public List<Page> getAllChildren(String parentId){
        Page parent = pageRepository.findPageByCode(parentId);
        return parent.getChildrenPages();
    }

    public List<Page> getFirstLevel(){
        List<Page> pages = new ArrayList<>();
        for(Page page : pageRepository.findAll()){
            if(page.getParent() == null)
                pages.add(page);
        }
        return pages;
    }

}
