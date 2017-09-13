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

import vol.metier.dao.VolDao;
import vol.metier.model.Views;
import vol.metier.model.Vol;

/**
 * @author ajc
 *
 */
@RestController
public class VolRestController {

	@Autowired
	private VolDao daoVol;

	@RequestMapping(value = "/vol", method = RequestMethod.GET)
	@JsonView(Views.Vol.class)
	public ResponseEntity<List<Vol>> list() {
		return new ResponseEntity<>(daoVol.findAll(), HttpStatus.OK);
	}

	@RequestMapping(value = "/vol", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Vol vol, UriComponentsBuilder uCB) {
		daoVol.create(vol);
		URI uri = uCB.path("/vol/{id}").buildAndExpand(vol.getId()).toUri();
		HttpHeaders header = new HttpHeaders();
		header.setLocation(uri);
		return new ResponseEntity<>(header, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/vol/{id}", method = RequestMethod.GET)
	@JsonView(Views.Vol.class)
	public ResponseEntity<Vol> find(@PathVariable("id") Long id) {
		Vol tmp = daoVol.find(id);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Vol>(tmp, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/vol/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		Vol tmp = daoVol.find(id);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			daoVol.delete(tmp.getId());
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(value = "/vol/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody Vol vol) {
		Vol tmp = daoVol.find(id);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			vol.setId(tmp.getId());
			vol.setVersion(tmp.getVersion());
			daoVol.update(vol);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

}
