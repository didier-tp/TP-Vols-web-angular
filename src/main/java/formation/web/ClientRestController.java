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

import vol.metier.dao.ClientDao;
import vol.metier.model.Client;
import vol.metier.model.ClientEI;
import vol.metier.model.ClientMoral;
import vol.metier.model.ClientPhysique;
import vol.metier.model.Views;

@RestController
public class ClientRestController {

	@Autowired
	private ClientDao daoClient;

	@RequestMapping(value = "/client", method = RequestMethod.GET)
	@JsonView(Views.Client.class)
	public ResponseEntity<List<Client>> list() {
		return new ResponseEntity<>(daoClient.findAll(), HttpStatus.OK);
	}

	@RequestMapping(value = "/clientPhysique", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody ClientPhysique client, UriComponentsBuilder uCB) {
		daoClient.create(client);
		URI uri = uCB.path("/clientPhysique/{id}").buildAndExpand(client.getId()).toUri();
		HttpHeaders header = new HttpHeaders();
		header.setLocation(uri);
		return new ResponseEntity<>(header, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/clientMoral", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody ClientMoral client, UriComponentsBuilder uCB) {
		daoClient.create(client);
		URI uri = uCB.path("/clientMoral/{id}").buildAndExpand(client.getId()).toUri();
		HttpHeaders header = new HttpHeaders();
		header.setLocation(uri);
		return new ResponseEntity<>(header, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/clientEI", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody ClientEI client, UriComponentsBuilder uCB) {
		daoClient.create(client);
		URI uri = uCB.path("/clientEI/{id}").buildAndExpand(client.getId()).toUri();
		HttpHeaders header = new HttpHeaders();
		header.setLocation(uri);
		return new ResponseEntity<>(header, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/client/{id}", method = RequestMethod.GET)
	@JsonView(Views.Client.class)
	public ResponseEntity<Client> find(@PathVariable("id") Long id) {
		Client tmp = daoClient.find(id);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Client>(tmp, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/client/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		Client tmp = daoClient.find(id);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			
			daoClient.delete(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(value = "/clientPhysique/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody ClientPhysique client) {
		Client tmp = daoClient.find(id);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			client.setId(id);
			client.setVersion(tmp.getVersion());
			daoClient.update(client);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/clientMoral/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody ClientMoral client) {
		Client tmp = daoClient.find(id);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			client.setId(id);
			client.setVersion(tmp.getVersion());
			daoClient.update(client);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/clientEI/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody ClientEI client) {
		Client tmp = daoClient.find(id);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			client.setId(id);
			client.setVersion(tmp.getVersion());
			daoClient.update(client);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
	
	
}
