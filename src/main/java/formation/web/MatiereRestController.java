/**
 * 
 */
package formation.web;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import formation.jpa.dao.DaoMatiere;
import formation.jpa.model.Matiere;

/**
 * @author ajc
 *
 */
@RestController
public class MatiereRestController {

	@Autowired
	private DaoMatiere daoMatiere;

	@RequestMapping(value = "/matiere", method = RequestMethod.GET)
	public ResponseEntity<List<Matiere>> list() {
		return new ResponseEntity<>(daoMatiere.findAll(), HttpStatus.OK);
	}

	@RequestMapping(value = "/matiere", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Matiere matiere, UriComponentsBuilder uCB) {

		daoMatiere.create(matiere);
		URI uri = uCB.path("/matiere/{code}").buildAndExpand(matiere.getCode()).toUri();
		HttpHeaders header = new HttpHeaders();
		header.setLocation(uri);
		return new ResponseEntity<>(header, HttpStatus.CREATED);

	}

	@RequestMapping(value = "/matiere/{code}", method = RequestMethod.GET)
	public ResponseEntity<Matiere> find(@PathVariable("code") Integer code) {
		Matiere tmp = (Matiere) daoMatiere.find(code);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Matiere>(tmp, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/matiere/{code}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable("code") Integer code) {
		Matiere tmp = (Matiere) daoMatiere.find(code);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			daoMatiere.delete(tmp);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(value = "/matiere/{code}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable("code") Integer code, @RequestBody Matiere matiere) {
		Matiere tmp = (Matiere) daoMatiere.find(code);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			daoMatiere.update(matiere);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

}
