/**
 * 
 */
package formation.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import formation.jpa.model.Civilite;
import formation.jpa.model.Views;

/**
 * @author ajc
 *
 */
@RestController
public class CiviliteRestController {

	@RequestMapping(value = "/civilite", method = RequestMethod.GET)
	@JsonView(Views.Eleve.class)
	public ResponseEntity<Civilite[]> list() {
		return new ResponseEntity<>(Civilite.values(), HttpStatus.OK);
	}

}
