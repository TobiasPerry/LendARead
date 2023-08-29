package ar.edu.itba.paw.webapp.oldControllers;


import ar.edu.itba.paw.interfaces.ISBNCheckerService;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class ISBNCheckerController {

    private final ISBNCheckerService isbnCheckerService;

    @Autowired
    public ISBNCheckerController(ISBNCheckerService isbnCheckerService) {
        this.isbnCheckerService = isbnCheckerService;
    }

    @RequestMapping(value = "/book", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> myEndpoint(@RequestParam("isbn") String isbn) {
        Optional<BookImpl> bookOpt = this.isbnCheckerService.getBookIfExistsByISBN(isbn);
        Map<String, String> response = new HashMap<>();
        if (!bookOpt.isPresent()) {
            response.put("error", "Not Found");
        } else {
            BookImpl book = bookOpt.get();
            response.put("author", book.getAuthor());
            response.put("language", book.getLanguage());
            response.put("isbn", book.getIsbn());
            response.put("name", book.getName());
        }
        return ResponseEntity.ok(response);
    }
}