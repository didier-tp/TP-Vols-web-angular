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
import formation.jpa.model.Formateur;
import formation.jpa.model.Views;

/**
 * @author ajc
 *
 */
@RestController
public class FormateurRestController {

	@Autowired
	private DaoPersonne daoPersonne;

	@RequestMapping(value = "/formateur", method = RequestMethod.GET)
	@JsonView(Views.Formateur.class)
	public ResponseEntity<List<Formateur>> list() {
		return new ResponseEntity<>(daoPersonne.findAllFormateurLazyLoading(), HttpStatus.OK);
	}

	@RequestMapping(value = "/formateur", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Formateur formateur, UriComponentsBuilder uCB) {

		daoPersonne.create(formateur);
		URI uri = uCB.path("/formateur/{id}").buildAndExpand(formateur.getId()).toUri();
		HttpHeaders header = new HttpHeaders();
		header.setLocation(uri);
		return new ResponseEntity<>(header, HttpStatus.CREATED);

	}

	@RequestMapping(value = "/formateur/{id}", method = RequestMethod.GET)
	@JsonView(Views.Formateur.class)
	public ResponseEntity<Formateur> find(@PathVariable("id") Integer id) {
		Formateur tmp = daoPersonne.findFormateurLazyLoading(id);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Formateur>(tmp, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/formateur/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
		Formateur tmp = (Formateur) daoPersonne.find(id);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			daoPersonne.delete(tmp);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(value = "/formateur/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable("id") Integer id, @RequestBody Formateur formateur) {
		Formateur tmp = (Formateur) daoPersonne.findFormateurLazyLoading(id);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			daoPersonne.update(formateur);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

}
