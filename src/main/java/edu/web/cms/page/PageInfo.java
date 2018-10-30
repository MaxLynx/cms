package edu.web.cms.page;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Lightweight page object without text
 */
public class PageInfo {

    private String id;
    private String code;
    private Language lang;

    private String captionUa;
    private String captionEn;
    private String captionRu;

    private String shortCaptionUa;
    private String shortCaptionEn;
    private String shortCaptionRu;

    public PageInfo(){
        this.lang = Language.ENGLISH;
    }

    public PageInfo(String id, String code, String captionUa,
                    String captionEn, String captionRu, String shortCaptionUa,
                    String shortCaptionEn, String shortCaptionRu) {
        this.id = id;
        this.code = code;
        this.lang = Language.ENGLISH;
        this.captionUa = captionUa;
        this.captionEn = captionEn;
        this.captionRu = captionRu;
        this.shortCaptionUa = shortCaptionUa;
        this.shortCaptionEn = shortCaptionEn;
        this.shortCaptionRu = shortCaptionRu;
    }

    public static PageInfo createPageInfo(Page page){
        PageInfo pageInfo = new PageInfo();
        pageInfo.setId(page.getId());
        pageInfo.setCode(page.getCode());
        pageInfo.setLang(page.getLang());
        pageInfo.setCaptionUa(page.getCaptionUa());
        pageInfo.setCaptionRu(page.getCaptionRu());
        pageInfo.setCaptionEn(page.getCaptionEn());
        pageInfo.setShortCaptionUa(page.getShortCaptionUa());
        pageInfo.setShortCaptionRu(page.getShortCaptionRu());
        pageInfo.setShortCaptionEn(page.getShortCaptionEn());
        return pageInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Language getLang() {
        return lang;
    }

    public String getLangAsString() {
        switch(lang) {
            case UKRAINIAN: return "ua";
            case ENGLISH: return "en";
            case RUSSIAN: return "ru";
            default: return "en";
        }
    }

    public void setLang(Language lang) {
        this.lang = lang;
    }

    public void setLang(String lang) {
        switch(lang) {
            case "en" : this.lang = Language.ENGLISH; break;
            case "ru" : this.lang = Language.RUSSIAN; break;
            case "ua" : this.lang = Language.UKRAINIAN; break;
            default: this.lang = Language.ENGLISH;
        }
    }

    public String getCaption() {
        switch(lang) {
            case UKRAINIAN: return getCaptionUa();
            case ENGLISH: return getCaptionEn();
            case RUSSIAN: return getCaptionRu();
            default: return getCaptionEn();
        }
    }

    public String getCaptionUa() {
        return captionUa;
    }

    public void setCaptionUa(String captionUa) {
        this.captionUa = captionUa;
    }

    public String getCaptionEn() {
        return captionEn;
    }

    public void setCaptionEn(String captionEn) {
        this.captionEn = captionEn;
    }

    public String getCaptionRu() {
        return captionRu;
    }

    public void setCaptionRu(String captionRu) {
        this.captionRu = captionRu;
    }

    public String getShortCaption() {
        switch(lang) {
            case UKRAINIAN: return getShortCaptionUa();
            case ENGLISH: return getShortCaptionEn();
            case RUSSIAN: return getShortCaptionRu();
            default: return getShortCaptionEn();
        }
    }

    public String getShortCaptionUa() {
        return shortCaptionUa;
    }

    public void setShortCaptionUa(String shortCaptionUa) {
        this.shortCaptionUa = shortCaptionUa;
    }

    public String getShortCaptionEn() {
        return shortCaptionEn;
    }

    public void setShortCaptionEn(String shortCaptionEn) {
        this.shortCaptionEn = shortCaptionEn;
    }

    public String getShortCaptionRu() {
        return shortCaptionRu;
    }

    public void setShortCaptionRu(String shortCaptionRu) {
        this.shortCaptionRu = shortCaptionRu;
    }

}
