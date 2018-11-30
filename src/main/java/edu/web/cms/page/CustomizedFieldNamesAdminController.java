package edu.web.cms.page;

import org.springframework.stereotype.Controller;

/**
 * Created by Max on 20.11.2018.
 */
@Controller
public class CustomizedFieldNamesAdminController extends AdminController {

    CustomizedFieldNamesAdminController(){
        fieldNames = new String[]{"Код сторінки",
                "Заголовок українською", "Заголовок англійською", "Заголовок російською",
                "Анотація українською", "Анотація англійською", "Анотація російською",
                "Текст українською", "Текст англійською", "Текст російською", "Порядковий номер при сортуванні",
                "Зображення", "Тип категорії"};
    }
}
