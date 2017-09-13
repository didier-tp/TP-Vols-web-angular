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

import vol.metier.dao.PassagerDao;
import vol.metier.model.Passager;
import vol.metier.model.Views;

@RestController
public class PassagerRestController {
	@Autowired
	private PassagerDao daoPassager;

	@RequestMapping(value = "/passager", method = RequestMethod.GET)
	@JsonView(Views.Passager.class)
	public ResponseEntity<List<Passager>> list() {
		return new ResponseEntity<>(daoPassager.findAll(), HttpStatus.OK);
	}

	@RequestMapping(value = "/passager", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Passager passager, UriComponentsBuilder uCB) {
		daoPassager.create(passager);
		URI uri = uCB.path("/passager/{id}").buildAndExpand(passager.getId()).toUri();
		HttpHeaders header = new HttpHeaders();
		header.setLocation(uri);
		return new ResponseEntity<>(header, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/passager/{id}", method = RequestMethod.GET)
	@JsonView(Views.Passager.class)
	public ResponseEntity<Passager> find(@PathVariable("id") Long id) {
		Passager tmp = daoPassager.find(id);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Passager>(tmp, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/passager/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		Passager tmp = daoPassager.find(id);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			daoPassager.delete(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(value = "/passager/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody Passager passager) {
		Passager tmp = daoPassager.find(id);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			passager.setId(id);
			passager.setVersion(tmp.getVersion());
			daoPassager.update(passager);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
}
