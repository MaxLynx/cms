package edu.web.cms.page;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import java.util.Date;

/**
 * Page of website
 */
@Entity
public class Page {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    private String code;
    @Transient
    private Language lang;

    private String captionUa;
    private String captionEn;
    private String captionRu;

    private String shortCaptionUa;
    private String shortCaptionEn;
    private String shortCaptionRu;

    private String introUa;
    private String introEn;
    private String introRu;

    private String textUa;
    private String textEn;
    private String textRu;

    private String imageURL;

    private Date creationDate;
    private Date lastUpdateDate;

    public Page(){
        this.lang = Language.ENGLISH;
    }

    public Page(String id, String code, String captionUa,
                String captionEn, String captionRu, String shortCaptionUa,
                String shortCaptionEn, String shortCaptionRu, String introUa,
                String introEn, String introRu,
                String textUa, String textEn, String textRu, String imageURL,
                Date creationDate, Date lastUpdateDate) {
        this.id = id;
        this.code = code;
        this.lang = Language.ENGLISH;
        this.captionUa = captionUa;
        this.captionEn = captionEn;
        this.captionRu = captionRu;
        this.shortCaptionUa = shortCaptionUa;
        this.shortCaptionEn = shortCaptionEn;
        this.shortCaptionRu = shortCaptionRu;
        this.introUa = introUa;
        this.introEn = introEn;
        this.introRu = introRu;
        this.textUa = textUa;
        this.textEn = textEn;
        this.textRu = textRu;
        this.imageURL = imageURL;
        this.creationDate = creationDate;
        this.lastUpdateDate = lastUpdateDate;
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

    public String getIntro() {
        switch(lang) {
            case UKRAINIAN: return getIntroUa();
            case ENGLISH: return getIntroEn();
            case RUSSIAN: return getIntroRu();
            default: return getIntroEn();
        }
    }

    public String getIntroUa() {
        return introUa;
    }

    public void setIntroUa(String introUa) {
        this.introUa = introUa;
    }

    public String getIntroEn() {
        return introEn;
    }

    public void setIntroEn(String introEn) {
        this.introEn = introEn;
    }

    public String getIntroRu() {
        return introRu;
    }

    public void setIntroRu(String introRu) {
        this.introRu = introRu;
    }

    public String getText() {
        switch(lang) {
            case UKRAINIAN: return getTextUa();
            case ENGLISH: return getTextEn();
            case RUSSIAN: return getTextRu();
            default: return getTextEn();
        }
    }

    public String getTextUa() {
        return textUa;
    }

    public void setTextUa(String textUa) {
        this.textUa = textUa;
    }

    public String getTextEn() {
        return textEn;
    }

    public void setTextEn(String textEn) {
        this.textEn = textEn;
    }

    public String getTextRu() {
        return textRu;
    }

    public void setTextRu(String textRu) {
        this.textRu = textRu;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}
