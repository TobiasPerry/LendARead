package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.AssetInstanceService;
import ar.edu.itba.paw.interfaces.ImageService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class HelloWordController {
    private final ViewResolver viewResolver;
    AssetInstanceService assetInstanceService;
    @Autowired
    ImageService imageService;
    @Autowired
    public HelloWordController(@Qualifier("viewResolver")final ViewResolver vr, AssetInstanceService assetInstanceService){
        this.viewResolver = vr;
        this.assetInstanceService = assetInstanceService;
    }

    //En el requestMapping podemos ir agregandole cosas para poder tener mas especificaciones
    @RequestMapping( "/")
    public ModelAndView helloWord(){
        //El objeto ModelAndView nos deja detener el modelo y la view
        final ModelAndView mav = new ModelAndView("/views/index");
        List<AssetInstance> books = assetInstanceService.getAllAssetsInstances();
        mav.addObject("books", books);
        mav.addObject("path","home");
        return mav;
    }

    @RequestMapping( "/assetView")
    public ModelAndView assetView(){
        //El objeto ModelAndView nos deja detener el modelo y la view
        final ModelAndView mav = new ModelAndView("/views/assetView");
        return mav;
    }
    @RequestMapping( "/getImage/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable String id, HttpServletRequest request){
        byte[] array = imageService.getPhoto(Integer.parseInt(id));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", MediaType.IMAGE_JPEG_VALUE);
        headers.set("Content-Disposition","inline; filename=\"whaterver.jpg\"");

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(array);
    }
}
