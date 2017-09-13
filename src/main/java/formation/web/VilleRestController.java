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

import vol.metier.dao.VilleDao;
import vol.metier.model.Views;
import vol.metier.model.Ville;

@RestController
public class VilleRestController {

	@Autowired
	private VilleDao daoVille;

	@RequestMapping(value = "/ville", method = RequestMethod.GET)
	@JsonView(Views.Ville.class)
	public ResponseEntity<List<Ville>> list() {
		return new ResponseEntity<>(daoVille.findAll(), HttpStatus.OK);
	}

	@RequestMapping(value = "/ville", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Ville ville, UriComponentsBuilder uCB) {
		daoVille.create(ville);
		URI uri = uCB.path("/ville/{id}").buildAndExpand(ville.getId()).toUri();
		HttpHeaders header = new HttpHeaders();
		header.setLocation(uri);
		return new ResponseEntity<>(header, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/ville/{id}", method = RequestMethod.GET)
	@JsonView(Views.Ville.class)
	public ResponseEntity<Ville> find(@PathVariable("id") Long id) {
		Ville tmp = daoVille.find(id);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Ville>(tmp, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/ville/{id}", method = RequestMethod.DELETE)

	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		Ville tmp = daoVille.find(id);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			daoVille.delete(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(value = "/ville/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody Ville ville) {
		Ville tmp = daoVille.find(id);
		
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			ville.setId(id);
			ville.setVersion(tmp.getVersion());
			daoVille.update(ville);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

}
