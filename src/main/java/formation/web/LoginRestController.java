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

import vol.metier.dao.LoginDao;
import vol.metier.model.Login;

@RestController
public class LoginRestController {

	@Autowired
	private LoginDao daoLogin;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ResponseEntity<List<Login>> list() {
		return new ResponseEntity<>(daoLogin.findAll(), HttpStatus.OK);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Login login, UriComponentsBuilder uCB) {
		daoLogin.create(login);
		URI uri = uCB.path("/login/{id}").buildAndExpand(login.getId()).toUri();
		HttpHeaders header = new HttpHeaders();
		header.setLocation(uri);
		return new ResponseEntity<>(header, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/login/{id}", method = RequestMethod.GET)
	public ResponseEntity<Login> find(@PathVariable("id") Long id) {
		Login tmp = daoLogin.find(id);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Login>(tmp, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/login/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		Login tmp = daoLogin.find(id);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			daoLogin.delete(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(value = "/login/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody Login login) {
		Login tmp = daoLogin.find(id);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			daoLogin.update(login);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

}
