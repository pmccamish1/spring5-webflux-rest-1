package guru.springframework.spring5webfluxrest.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;

import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repositories.VendorRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class VendorControllerTest {
	WebTestClient webTestClient;
	VendorRepository vendorRepository;
	VendorController vendorController;
	
	@Before
	public void setUp() throws Exception {
		vendorRepository = Mockito.mock(VendorRepository.class);
		vendorController = new VendorController(vendorRepository);
		webTestClient = WebTestClient.bindToController(vendorController).build();
	}

	@Test
	public void testGetAllVendors() {
		BDDMockito.given(vendorRepository.findAll()).willReturn(Flux.just(Vendor.builder().firstName("fn").lastName("ln").build(), 
				Vendor.builder().firstName("fn2").lastName("ln2").build()));
		
		webTestClient.get().uri("/api/v1/vendors").exchange().expectBodyList(Vendor.class).hasSize(2);
	}
	
	@Test
	public void testGetVendor() {
		BDDMockito.given(vendorRepository.findById("someid")).willReturn(Mono.just(Vendor.builder().firstName("fn").lastName("ln").id("id").build()));
		
		webTestClient.get().uri("/api/v1/vendors/someid").exchange().expectBody(Vendor.class);
	}

}
