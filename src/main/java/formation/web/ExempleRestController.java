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

import formation.jpa.dao.DaoSalle;
import formation.jpa.model.Salle;

/**
 * @author ajc
 *
 */
@RestController
public class ExempleRestController {

	@Autowired
	private DaoSalle daoSalle;

	@RequestMapping(value = "/salle", method = RequestMethod.GET)
	public ResponseEntity<List<Salle>> list() {
		return new ResponseEntity<>(daoSalle.findAll(), HttpStatus.OK);
	}

	@RequestMapping(value = "/salle", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Salle salle, UriComponentsBuilder uCB) {
		Salle tmp = daoSalle.find(salle.getNom());
		if (tmp == null) {
			daoSalle.create(salle);
			URI uri = uCB.path("/salle/{nom}").buildAndExpand(salle.getNom()).toUri();
			HttpHeaders header = new HttpHeaders();
			header.setLocation(uri);
			return new ResponseEntity<>(header, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value = "/salle/{nom}", method = RequestMethod.GET)
	public ResponseEntity<Salle> find(@PathVariable("nom") String nom) {
		Salle tmp = daoSalle.find(nom);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Salle>(tmp, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/salle/{nom}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable("nom") String nom) {
		Salle tmp = daoSalle.find(nom);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			daoSalle.delete(tmp);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(value = "/salle/{nom}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable("nom") String nom, @RequestBody Salle salle) {
		Salle tmp = daoSalle.find(nom);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			tmp.setEtage((salle.getEtage() != null) ? salle.getEtage() : tmp.getEtage());
			tmp.setCapacite((salle.getCapacite() != null) ? salle.getCapacite() : tmp.getCapacite());
			tmp.setAdresse(salle.getAdresse() != null ? salle.getAdresse() : tmp.getAdresse());
			daoSalle.update(tmp);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

}
