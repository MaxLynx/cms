package edu.web.cms.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Webpages controller
 */
@Controller
@RequestMapping("/")
public class PageController {

    private Set<String> acceptableLangs;

    @Autowired
    private PageService pageService;

    private PageController(){
        acceptableLangs = new HashSet<>();
        acceptableLangs.add("en");
        acceptableLangs.add("ua");
        acceptableLangs.add("ru");
    }

    @RequestMapping("")
    public String getPage(Map<String, Object> model){
        Page page = pageService.getPageByCode("mollusca");
        model.put("page", page);
        List<PageInfo> pages = pageService.getPagesInfo("en");
        model.put("pages", pages);
        model.put("childPages", pageService.setPagesLang(pageService.getChildrenPages(page), "en"));
        return "layout";
    }

    @RequestMapping("/{codeOrLang}")
    public String getPage(@PathVariable String codeOrLang, Map<String, Object> model){
        Page page;
        List<PageInfo> pages;
        List<Page> childPages;
        if(acceptableLangs.contains(codeOrLang)){
            page = pageService.getPageByCode("mollusca");
            page.setLang(codeOrLang);
            pages = pageService.getPagesInfo(codeOrLang);
            childPages = pageService.setPagesLang(pageService.getChildrenPages(page), codeOrLang);
        }
        else {
            page = pageService.getPageByCode(codeOrLang);
            pages = pageService.getPagesInfo("en");
            childPages = pageService.setPagesLang(pageService.getChildrenPages(page), "en");
        }
        model.put("page", page);
        model.put("pages", pages);
        model.put("childPages", childPages);
        return "layout";
    }

    @RequestMapping("alias/{code}/{lang}")
    public ModelAndView getOriginalPage(@PathVariable String code,
                                        @PathVariable String lang,
                                        ModelMap model){
        Page aliasPage = pageService.getPageByCode(code);
        model.addAttribute("aliasPage", aliasPage);
        return new ModelAndView("redirect:/" + aliasPage.getOriginal().getCode(), model);
    }

    @RequestMapping("/{code:^(?!resources).+}/{lang}")
    public String getPage(@PathVariable String code,
                                     @PathVariable String lang,
                                     ModelMap model){
        Page page;
        if(model.containsAttribute("aliasPage")){
            System.out.println("Hey");
            Page aliasPage = (Page)model.get("aliasPage");
            page = aliasPage.getOriginal();
            if(!aliasPage.getCaptionEn().equals(""))
                page.setCaptionEn(aliasPage.getCaptionEn());
            if(!aliasPage.getCaptionRu().equals(""))
                page.setCaptionRu(aliasPage.getCaptionRu());
            if(!aliasPage.getCaptionUa().equals(""))
                page.setCaptionUa(aliasPage.getCaptionUa());
            if(!aliasPage.getImageURL().equals(""))
                page.setImageURL(aliasPage.getImageURL());
            if(!aliasPage.getIntroEn().equals(""))
                page.setIntroEn(aliasPage.getIntroEn());
            if(!aliasPage.getIntroRu().equals(""))
                page.setIntroRu(aliasPage.getIntroRu());
            if(!aliasPage.getIntroUa().equals(""))
                page.setIntroUa(aliasPage.getIntroUa());
            if(!aliasPage.getTextEn().equals(""))
                page.setTextEn(aliasPage.getTextEn());
            if(!aliasPage.getTextRu().equals(""))
                page.setTextRu(aliasPage.getTextRu());
            if(!aliasPage.getTextUa().equals(""))
                page.setTextUa(aliasPage.getTextUa());
        }
        else {
            page = pageService.getPageByCode(code);
        }
        page.setLang(lang);
        model.put("page", page);
        List<PageInfo> pages = pageService.getPagesInfo(lang);
        model.put("pages", pages);
        model.put("childPages", pageService.setPagesLang(pageService.getChildrenPages(page), lang));
        return "layout";
    }

}
