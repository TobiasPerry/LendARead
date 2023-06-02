package ar.edu.itba.paw.webapp.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ReviewForm {

    @Size(max = 200)
    private String review;

    @Min(1)
    @Max(5)
    private int rating;

    public ReviewForm(){
        this.rating = 1;
    }

}
