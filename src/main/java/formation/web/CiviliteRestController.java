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

import vol.metier.model.TitreMoral;
import vol.metier.model.TitrePhysique;

/**
 * @author ajc
 *
 */
@RestController
public class CiviliteRestController {

	@RequestMapping(value = "/titrePhysique", method = RequestMethod.GET)
	@JsonView(vol.metier.model.Views.Client.class)
	public ResponseEntity<TitrePhysique[]> listPhysique() {
		return new ResponseEntity<>(TitrePhysique.values(), HttpStatus.OK);
	}

	@RequestMapping(value = "/titreMoral", method = RequestMethod.GET)
	@JsonView(vol.metier.model.Views.Client.class)
	public ResponseEntity<TitreMoral[]> listMoral() {
		return new ResponseEntity<>(TitreMoral.values(), HttpStatus.OK);
	}

}
