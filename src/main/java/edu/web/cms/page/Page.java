package edu.web.cms.page;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Page of website
 */
@Entity
public class Page implements Serializable {

    @Transient
    public static String TOP_PARENT_CODE = "TOP";

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

    @Lob
    private String introUa;
    @Lob
    private String introEn;
    @Lob
    private String introRu;

    @Lob
    private String textUa;
    @Lob
    private String textEn;
    @Lob
    private String textRu;

    private String imageURL;

    private Date creationDate;
    private Date lastUpdateDate;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne
    @JoinColumn(name = "parentCode", referencedColumnName="code")
    private Page parent;

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne
    @JoinColumn(name = "aliasOf", referencedColumnName="code")
    private Page original;

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    private List<Page> childrenPages;

    private int orderPosition; // position for children manual ordering
    private String orderType; // which field to be used in order by
    private ContainerType containerType;


    public Page(){
        this.lang = Language.ENGLISH;
        this.containerType = ContainerType.NONE;
    }

    public Page(String id, String code, String captionUa,
                String captionEn, String captionRu, String shortCaptionUa,
                String shortCaptionEn, String shortCaptionRu, String introUa,
                String introEn, String introRu,
                String textUa, String textEn, String textRu, String imageURL,
                Date creationDate, Date lastUpdateDate,
                Page parent, List<Page> childrenPages, Page original,
                int orderPosition, String orderType,
                ContainerType containerType) {
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
        this.parent = parent;
        this.childrenPages = childrenPages;
        this.original = original;
        this.orderPosition = orderPosition;
        this.orderType = orderType;
        this.containerType = containerType;
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

    public Page getParent() {
        return parent;
    }

    public void setParent(Page parent) {
        this.parent = parent;
    }

    public List<Page> getChildrenPages() {
        return childrenPages;
    }

    public void setChildrenPages(List<Page> childrenPages) {
        this.childrenPages = childrenPages;
    }

    public Page getOriginal() {
        return original;
    }

    public void setOriginal(Page original) {
        this.original = original;
    }

    public int getOrderPosition() {
        return orderPosition;
    }

    public void setOrderPosition(int orderPosition) {
        this.orderPosition = orderPosition;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public ContainerType getContainerType() {
        return containerType;
    }

    public void setContainerType(ContainerType containerType) {
        this.containerType = containerType;
    }
}
