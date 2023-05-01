package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.AssetInstanceService;
import ar.edu.itba.paw.interfaces.ImageService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.viewsContext.interfaces.Page;
import ar.edu.itba.paw.webapp.form.SnackbarControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexViewController {
    private final ViewResolver viewResolver;
    private final AssetInstanceService assetInstanceService;

    @Autowired
    public IndexViewController(@Qualifier("viewResolver")final ViewResolver vr, AssetInstanceService assetInstanceService){
        this.viewResolver = vr;
        this.assetInstanceService = assetInstanceService;
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("path", "home");
    }
    @RequestMapping( "/")
    public ModelAndView indexView(){
        Page page = assetInstanceService.getAllAssetsInstances(1);

        ModelAndView mav = new ModelAndView("/views/index");
        mav.addObject("books", page.getBooks());

        return mav;
    }

    @RequestMapping("/discovery/{pageNum}")
    public ModelAndView discoveryView(@PathVariable String pageNum){
        final ModelAndView mav = new ModelAndView("/views/discoveryView");

        int pageNumParsed;

        try {
            pageNumParsed = Integer.parseInt(pageNum);
        }catch (NumberFormatException e){
            // In case the parameters received cannot be parsed as integers, we'll return a not found view
            return new ModelAndView("/views/notFoundView");
        }

        Page page = assetInstanceService.getAllAssetsInstances(pageNumParsed);

        mav.addObject("books", page.getBooks());
        mav.addObject("nextPage", page.getCurrentPage() != page.getTotalPages());
        mav.addObject("previousPage", page.getCurrentPage() != 1);
        mav.addObject("currentPage", page.getCurrentPage());
        mav.addObject("totalPages", page.getTotalPages());
        mav.addObject("page", page.getCurrentPage());

        return mav;
    }


    @RequestMapping( "/assetView")
    public ModelAndView assetView(){
        //El objeto ModelAndView nos deja detener el modelo y la view
        final ModelAndView mav = new ModelAndView("/views/assetView");
        return mav;
    }

}
