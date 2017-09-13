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

import com.fasterxml.jackson.annotation.JsonView;

import formation.jpa.dao.DaoPersonne;
import formation.jpa.model.Eleve;
import formation.jpa.model.Views;

/**
 * @author ajc
 *
 */
@RestController
public class EleveRestController {

	@Autowired
	private DaoPersonne daoPersonne;

	@RequestMapping(value = "/eleve", method = RequestMethod.GET)
	@JsonView(Views.Eleve.class)
	public ResponseEntity<List<Eleve>> list() {
		return new ResponseEntity<>(daoPersonne.findAllEleve(), HttpStatus.OK);
	}

	@RequestMapping(value = "/eleve", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Eleve eleve, UriComponentsBuilder uCB) {

		daoPersonne.create(eleve);
		URI uri = uCB.path("/eleve/{id}").buildAndExpand(eleve.getId()).toUri();
		HttpHeaders header = new HttpHeaders();
		header.setLocation(uri);
		return new ResponseEntity<>(header, HttpStatus.CREATED);

	}

	@RequestMapping(value = "/eleve/{id}", method = RequestMethod.GET)
	@JsonView(Views.Eleve.class)
	public ResponseEntity<Eleve> find(@PathVariable("id") Integer id) {
		Eleve tmp = (Eleve) daoPersonne.find(id);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Eleve>(tmp, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/eleve/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
		Eleve tmp = (Eleve) daoPersonne.find(id);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			daoPersonne.delete(tmp);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(value = "/eleve/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable("id") Integer id, @RequestBody Eleve eleve) {
		Eleve tmp = (Eleve) daoPersonne.find(id);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			daoPersonne.update(eleve);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

}
