package edu.web.cms.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Max on 12.11.2018.
 */
@Controller
@RequestMapping("/admin")
public abstract class AdminController {

    @Autowired
    private ContentService contentService;

    protected String[] fieldNames;

    private String lastOrderType;


    AdminController(){
    }

    @RequestMapping("")
    public String getIndex(Map<String, Object> model){
        List<Object> objects;

        objects = contentService.getFirstLevel();
        model.put("objects", objects);
        return "index";
    }

    @RequestMapping(value="", params = {"parentCode"})
    public String getIndexWithParams(Map<String, Object> model,
                                     @RequestParam String parentCode){
        List<Object> objects;
        if(parentCode.equals("")) {
            objects = contentService.getFirstLevel();
        }
        else {
            objects = contentService.getAllChildren(parentCode);
        }
        model.put("objects", objects);
        model.put("parentCode", parentCode);
        return "index";
    }

    @RequestMapping("/create")
    public String getCreateForm(Map<String, Object> model){
        model.put("properties", contentService.getObjectAsPropertyPairs(contentService.createBlank(),
                fieldNames));
        model.put("object", contentService.createBlank());
        model.put("parentCode", "");
        return "editForm";
    }

    @RequestMapping(value="/create", params = {"parentCode"})
    public String getCreateFormWithParams(Map<String, Object> model,
                                          @RequestParam String parentCode){
        model.put("properties", contentService.getObjectAsPropertyPairs(contentService.createBlank(),
                fieldNames));
        model.put("object", contentService.createBlank());
        model.put("parentCode", parentCode);
        return "editForm";
    }

    @RequestMapping(method= RequestMethod.POST, value="/{parentCode}")
    public ModelAndView storeItem(@PathVariable String parentCode,
            @ModelAttribute("object") Page newObject,
                            @RequestParam("imgFile") MultipartFile file,
                            BindingResult bindingResult,
                            ModelMap model,
                            SessionStatus sessionStatus){
        if(!bindingResult.hasErrors()) {
            try {
                File targetFile = new File("src/main/resources/static/resources/" +
                        "images/" + file.getOriginalFilename());
                OutputStream outStream = new FileOutputStream(targetFile);
                byte[] buffer = file.getBytes();
                outStream.write(buffer);
                outStream.close();
                newObject.setImageURL(file.getOriginalFilename());
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
            newObject.setParent((Page)contentService.getByField("code", parentCode));
            contentService.add(newObject);
            sessionStatus.setComplete();
        }
        else
        {
            model.put("properties", contentService.getObjectAsPropertyPairs(contentService.createBlank(),
                    fieldNames));
            model.put("object", contentService.createBlank());
            return new ModelAndView("redirect:/admin/create?parentCode=" + parentCode, model);
        }
        List<Object> objects = contentService.getAll();
        model.put("objects", objects);
        return new ModelAndView("redirect:/admin?parentCode=" + parentCode, model);
    }

    @RequestMapping("/{code}/edit")
    public String getEditForm(@PathVariable String code, Map<String, Object> model){

        Object editableObj =  contentService.getByField("code", code);
        lastOrderType = ((Page)editableObj).getOrderType();
        model.put("object", editableObj);
        model.put("properties", contentService.getObjectAsPropertyPairs(editableObj,
                fieldNames));
        return "editForm";
    }
    @RequestMapping(value="/{code}/edit", params = {"parentCode"})
    public String getEditFormWithParams(@PathVariable String code, Map<String, Object> model,
                                        @RequestParam String parentCode){

        Object editableObj =  contentService.getByField("code", code);
        lastOrderType = ((Page)editableObj).getOrderType();
        model.put("object", editableObj);
        model.put("properties", contentService.getObjectAsPropertyPairs(editableObj,
                fieldNames));
        model.put("parentCode", parentCode);
        return "editForm";
    }

    @RequestMapping(method= RequestMethod.PUT, value="/{code}")
    public ModelAndView updateItem(@PathVariable String code,
                                                 @ModelAttribute("object") Page object,
                                                 @RequestParam("imgFile") MultipartFile file,
                                                 BindingResult bindingResult, ModelMap model){
        if(!bindingResult.hasErrors()) {
            try {
                File targetFile = new File("src/main/resources/static/resources/" +
                        "images/" + file.getOriginalFilename());
                OutputStream outStream = new FileOutputStream(targetFile);
                byte[] buffer = file.getBytes();
                outStream.write(buffer);
                outStream.close();
                object.setImageURL(file.getOriginalFilename());
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
            Object oldObject = contentService.getByField("code", object.getCode());
            object.setId(((Page)oldObject).getId());
            object.setOrderType(lastOrderType);
            contentService.update(object);
        }
        else{
            Object editableObj =  contentService.getByField("code", code);
            model.put("object", editableObj);
            model.put("properties", contentService.getObjectAsPropertyPairs(editableObj,
                    fieldNames));
            return new ModelAndView("redirect:/admin/create", model);
        }
        List<Object> objects = contentService.getAll();
        model.put("objects", objects);
        return new ModelAndView("redirect:/admin", model);
    }

    @RequestMapping(method= RequestMethod.PUT, value="/{code}/{parentCode}")
    public ModelAndView updateItemWithParentCode(@PathVariable String code,
                             @PathVariable String parentCode,
                             @ModelAttribute("object") Page object,
                             @RequestParam("imgFile") MultipartFile file,
                             BindingResult bindingResult, ModelMap model){
        if(!bindingResult.hasErrors()) {
            try {
                File targetFile = new File("src/main/resources/static/resources/" +
                        "images/" + file.getOriginalFilename());
                OutputStream outStream = new FileOutputStream(targetFile);
                byte[] buffer = file.getBytes();
                outStream.write(buffer);
                outStream.close();
                object.setImageURL(file.getOriginalFilename());
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }

            Object oldObject = contentService.getByField("code", object.getCode());
            if(file.getOriginalFilename().equals("")){
                object.setImageURL(((Page)oldObject).getImageURL());
            }
            object.setId(((Page)oldObject).getId());
            object.setOrderType(lastOrderType);
            object.setParent((Page)contentService.getByField("code", parentCode));
            contentService.update(object);
        }
        else{
            Object editableObj =  contentService.getByField("code", code);
            model.put("object", editableObj);
            model.put("properties", contentService.getObjectAsPropertyPairs(editableObj,
                    fieldNames));
            return new ModelAndView("redirect:/admin/create?parentCode=" + parentCode, model);
        }
        List<Object> objects = contentService.getAll();
        model.put("objects", objects);
        return new ModelAndView("redirect:/admin?parentCode=" + parentCode, model);
    }

    @RequestMapping(method= RequestMethod.DELETE, value="/{code}")
    public String deleteItem(@PathVariable String code, Map<String, Object> model){
        Object object = contentService.getByField("code", code);
        try {
            contentService.delete(object);
        }
        catch(Exception e){
            model.put("message", e.getMessage());
            return "error";
        }
        List<Object> objects = contentService.getAll();
        model.put("objects", objects);
        return "index";
    }
}
