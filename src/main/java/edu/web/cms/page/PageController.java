package edu.web.cms.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
        Page page = pageService.getPageByCode("index");
        model.put("page", page);
        List<Page> pages = pageService.getPages("en");
        model.put("pages", pages);
        return "layout";
    }

    @RequestMapping("/{codeOrLang}")
    public String getPage(@PathVariable String codeOrLang, Map<String, Object> model){
        Page page;
        List<Page> pages;
        if(acceptableLangs.contains(codeOrLang)){
            page = pageService.getPageByCode("index");
            page.setLang(codeOrLang);
            pages = pageService.getPages(codeOrLang);
        }
        else {
            page = pageService.getPageByCode(codeOrLang);
            pages = pageService.getPages("en");
        }
        model.put("page", page);
        model.put("pages", pages);
        return "layout";
    }

    @RequestMapping("/{code:^(?![css|images]).+}/{lang}")
    public String getPage(@PathVariable String code,
                                     @PathVariable String lang,
                                     Map<String, Object> model){
        Page page = pageService.getPageByCode(code);
        page.setLang(lang);
        model.put("page", page);
        List<Page> pages = pageService.getPages(lang);
        model.put("pages", pages);
        return "layout";
    }

}
