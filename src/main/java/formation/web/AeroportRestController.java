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

import vol.metier.dao.AeroportDao;
import vol.metier.model.Aeroport;
import vol.metier.model.Views;

/**
 * @author ajc
 *
 */
@RestController
public class AeroportRestController {

	@Autowired
	private AeroportDao daoAeroport;

	@JsonView(Views.Aeroport.class)
	@RequestMapping(value = "/aeroport", method = RequestMethod.GET)
	public ResponseEntity<List<Aeroport>> list() {
		return new ResponseEntity<>(daoAeroport.findAll(), HttpStatus.OK);
	}

	@RequestMapping(value = "/aeroport", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Aeroport aeroport, UriComponentsBuilder uCB) {
	
			daoAeroport.create(aeroport);
			URI uri = uCB.path("/aeroport/{id}").buildAndExpand(aeroport.getId()).toUri();
			HttpHeaders header = new HttpHeaders();
			header.setLocation(uri);
			return new ResponseEntity<>(header, HttpStatus.CREATED);
	}
	
	@JsonView(Views.Aeroport.class)
	@RequestMapping(value = "/aeroport/{id}", method = RequestMethod.GET)
	public ResponseEntity<Aeroport> find(@PathVariable("id") Long id) {
		Aeroport tmp = daoAeroport.find(id);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Aeroport>(tmp, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/aeroport/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		Aeroport tmp = daoAeroport.find(id);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			daoAeroport.delete(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	@RequestMapping(value = "/aeroport/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody Aeroport aeroport) {
		Aeroport tmp = daoAeroport.find(id);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			daoAeroport.update(aeroport);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

}
