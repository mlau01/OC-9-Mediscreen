package com.abernathyclinic.mediscreennote;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.abernathyclinic.mediscreennote.exception.NoSuchNoteException;
import com.abernathyclinic.mediscreennote.model.NoteModel;
import com.abernathyclinic.mediscreennote.service.NoteServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class NoteControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private NoteServiceImpl noteService;

	@Autowired
	private ObjectMapper oMapper;
	
	private static NoteModel noteTest;
	
	private static String CRUD_ENDPOINT_NAME = "/notes";
	
	@BeforeAll
	public static void setUp() {

		noteTest = new NoteModel();
		noteTest.setAuthor("Test");
		noteTest.setPatientId(0);
		noteTest.setNote("Hello World!");
		
	}

	@Test
	public void postNoteTest_shouldReturnStatusCreated() throws Exception {
	
		when(noteService.create(any(NoteModel.class))).thenReturn(noteTest);
		
		mockMvc.perform(post(CRUD_ENDPOINT_NAME)
				.contentType(MediaType.APPLICATION_JSON)
				.content(oMapper.writeValueAsString(noteTest)))
				.andExpect(status().isCreated());
		
		verify(noteService, Mockito.times(1)).create(any(NoteModel.class));

	}
	
	@Test
	public void postNoteFailedTest_shouldReturnStatusBadRequest() throws Exception {
	
		when(noteService.create(any(NoteModel.class))).thenReturn(null);
		
		mockMvc.perform(post(CRUD_ENDPOINT_NAME)
				.contentType(MediaType.APPLICATION_JSON)
				.content(oMapper.writeValueAsString(noteTest)))
				.andExpect(status().isBadRequest());
		
		verify(noteService, Mockito.times(1)).create(any(NoteModel.class));

	}

	@Test
	public void deleteNoteTest_shouldReturnStatusOk() throws Exception {
		
		mockMvc.perform(delete(CRUD_ENDPOINT_NAME + "/0"))
				.andExpect(status().isOk());
		
		verify(noteService, Mockito.times(1)).delete("0");
	}
	
	@Test
	public void getAllNoteTest_shouldReturnStatusOk() throws Exception {
		when(noteService.getByPatientIdOrderedDesc(0)).thenReturn(new ArrayList<NoteModel>());
		
		mockMvc.perform(get(CRUD_ENDPOINT_NAME + "/patient/0")).andExpect(status().isOk());
		
		verify(noteService, Mockito.times(1)).getByPatientIdOrderedDesc(0);
	}
	
	@Test
	public void getAllNoteUnknownPatientIdTest_shouldReturnEmptyListButResponseOk() throws Exception {
		when(noteService.getByPatientIdOrderedDesc(0)).thenThrow(NoSuchNoteException.class);
		
		mockMvc.perform(get(CRUD_ENDPOINT_NAME + "/patient/0")).andExpect(status().isOk());
		
		verify(noteService, Mockito.times(1)).getByPatientIdOrderedDesc(0);
	}
	
	@Test
	public void getSingleNoteTest() throws Exception {
		when(noteService.getById("0")).thenReturn(noteTest);
		
		mockMvc.perform(get(CRUD_ENDPOINT_NAME + "/0")).andExpect(status().isOk());
		
		verify(noteService, Mockito.times(1)).getById("0");
	}
	
	@Test
	public void getUnknownNoteTest_shouldReturnStatusNotFound() throws Exception {
		when(noteService.getById("0")).thenThrow(NoSuchNoteException.class);
		
		mockMvc.perform(get(CRUD_ENDPOINT_NAME + "/0")).andExpect(status().isNotFound());
		
		verify(noteService, Mockito.times(1)).getById("0");
	}
	
	@Test
	public void putNoteTest_shouldReturnStatusCreated() throws Exception {
		when(noteService.put(any(NoteModel.class))).thenReturn(noteTest);
		
		mockMvc.perform(put(CRUD_ENDPOINT_NAME)
				.contentType(MediaType.APPLICATION_JSON)
				.content(oMapper.writeValueAsString(noteTest)))
				.andExpect(status().isCreated());
		
		verify(noteService, Mockito.times(1)).put(any(NoteModel.class));
		
	}

}

