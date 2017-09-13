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

import vol.metier.dao.CompagnieAerienneDao;
import vol.metier.model.CompagnieAerienne;
import vol.metier.model.Views;

@RestController
public class CompagnieAerienneRestController {

	@Autowired
	private CompagnieAerienneDao compagnieAerienneDao;

	@RequestMapping(value = "/compagnieAerienne", method = RequestMethod.GET) //obtenir la liste des compagnie aérienne
	@JsonView(Views.CompagnieAerienne.class)
	public ResponseEntity<List<CompagnieAerienne>> list() {
		return new ResponseEntity<>(compagnieAerienneDao.findAll(), HttpStatus.OK);
	}

	@RequestMapping(value = "/compagnieAerienne", method = RequestMethod.POST) //cree
	public ResponseEntity<Void> create(@RequestBody CompagnieAerienne compagnieAerienne, UriComponentsBuilder uCB) {
		CompagnieAerienne tmp = compagnieAerienneDao.find(compagnieAerienne.getId());
		if (tmp == null) {
			compagnieAerienneDao.create(compagnieAerienne);
			URI uri = uCB.path("/compagnieAerienne/{id}").buildAndExpand(compagnieAerienne.getId()).toUri();
			HttpHeaders header = new HttpHeaders();
			header.setLocation(uri);
			return new ResponseEntity<>(header, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value = "/compagnieAerienne/{id}", method = RequestMethod.GET) //obtenir la compagnie à partir de son id
	@JsonView(Views.CompagnieAerienne.class)
	public ResponseEntity<CompagnieAerienne> find(@PathVariable("id") Long id) {
		CompagnieAerienne tmp = compagnieAerienneDao.find(id);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<CompagnieAerienne>(tmp, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/compagnieAerienne/{id}", method = RequestMethod.DELETE) //supprime
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		CompagnieAerienne tmp = compagnieAerienneDao.find(id);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			compagnieAerienneDao.delete(tmp.getId());
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(value = "/compagnieAerienne/{id}", method = RequestMethod.PUT) //met à jour
	public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody CompagnieAerienne compagnieAerienne) {
		CompagnieAerienne tmp = compagnieAerienneDao.find(id);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			compagnieAerienne.setId(id);
			compagnieAerienne.setVersion(tmp.getVersion());
			compagnieAerienneDao.update(compagnieAerienne);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

}
