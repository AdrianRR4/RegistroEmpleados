package com.example.demo.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.models.dao.service.IEmployeeService;
import com.example.demo.models.entity.Employee;
import com.example.demo.util.paginator.PageRender;

@SessionAttributes("employee")
@Controller
public class EmployeeController {
	
	
	@Autowired
	private IEmployeeService employeeService;
	
	private final Logger log=LoggerFactory.getLogger(getClass());
	
	@GetMapping(value="/uploads/{filename:.+}")
	
	public ResponseEntity<Resource>seePicture(@PathVariable String filename){
		
	Path pathPicture=Paths.get("uploads").resolve(filename).toAbsolutePath();
	log.info("pathPicture: " + 	pathPicture);
	Resource recurso=null;
	try {
		recurso= new UrlResource(pathPicture.toUri());
		if(!recurso.exists() || !recurso.isReadable()) {
			throw new RuntimeException("Error: no se puede cargar la imagen "+ pathPicture.toString());
		}
	}catch (MalformedURLException e){
		e.printStackTrace();
	}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+recurso.getFilename()+"\"")
				.body(recurso);
	}
	
	
		
	
	
	@GetMapping(value="/seeDetail/{id}")
	
	public String see(@PathVariable(value="id")Long id, Map<String, Object >model, RedirectAttributes flash) {
		
		Employee employee= employeeService.findOne(id);
		
		if(employee==null) {
			flash.addFlashAttribute("Error!", "El cliente no existe en la base de datos");
			return "redirect:/toList";
		}
		model.put("employee", employee);
		model.put("title", "Detalle empleado"+employee.getName());
		
		return "seeDetail";
	}

		@RequestMapping (value="/toList", method=RequestMethod.GET)
		
		public String listar (@RequestParam (name ="page", defaultValue="0")int page, Model model) { //Metodo que trae todos los empleados de la lista 
			Pageable pageRequest=PageRequest.of(page, 4);
			Page<Employee>employees=employeeService.findAll(pageRequest);
			
			
			PageRender<Employee> pageRender= new PageRender<>("/toList",employees);
			model.addAttribute("title", "Listado de empleados");
			model.addAttribute("employees", employees);
			model.addAttribute("page", pageRender);
			return "toList";
		}
		
		
	@RequestMapping(value="/formEmployee")
	public String create(Map<String, Object> model) {
		
		Employee employee= new Employee();
		model.put("employee", employee);
		
		model.put("title", "Formulario empleado");
		
		return "formEmployee";
		
	}
	
	@RequestMapping (value="/formEmployee", method=RequestMethod.POST)//Para procesar los datos
	
	public String save(@Valid Employee employee, BindingResult result, RedirectAttributes flash,Model model, @RequestParam("file") MultipartFile picture, SessionStatus status) {
		
		if(result.hasErrors()) {// para validar los campos
			model.addAttribute("title", "Registrar empleado");
			return "formEmployee";
		}
		if(!picture.isEmpty()) {
			
			String uniqueFilename=UUID.randomUUID().toString()+ "_" + picture.getOriginalFilename();
			//Path directoryResources=Paths.get("src//main//resources//static/uploads");
			//String rootPath="D://Temp//uploads";
					//directoryResources.toFile().getAbsolutePath();
			Path rootPath= Paths.get("uploads").resolve(uniqueFilename);
			Path rootAbsolutPath=rootPath.toAbsolutePath();
			log.info("rootPath: " + rootPath);
			log.info("rootAbsolutPath: " + rootAbsolutPath	);
			try {
				System.out.print("entree");
				/*byte[]bytes=picture.getBytes();
				Path completeRuta= Paths.get(rootPath + "//" + picture.getOriginalFilename());
				Files.write(completeRuta, bytes);
				 * */
				
				Files.copy(picture.getInputStream(), rootAbsolutPath);
				flash.addFlashAttribute("info", "Fotografia adjuntada correctamente '"+ uniqueFilename+"'");
				employee.setPicture(uniqueFilename);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String messageFlash=(employee.getId()!=null)? "Empleado editado con exito" : "Cliente creado con exito";
		employeeService.save(employee);
		status.setComplete();
		flash.addFlashAttribute("success", messageFlash);
		return "redirect:toList";
		
	}	
	
	@RequestMapping(value="/formEmployee/{id}") // metodo para editar
	public String edit(	@PathVariable(value="id") Long id,  Map <String, Object>  model, RedirectAttributes flash) {
		
		Employee employee=null;
		
		if(id>0) {
			employee=employeeService.findOne(id);
			
			if(employee==null) {
				flash.addFlashAttribute("error", "Id no existe en la base de datos");
				return "redirect:/toList";
			}
			
		}else {
			flash.addFlashAttribute("error", "Id invalido");
			return "redirect:/toList";
		}
		
		model.put("employee", employee);
		model.put("title", "Editar empleado");
		
		return "formEmployee";
	}
	
	@RequestMapping(value="/delete/{id}")// Metodo eliminar empleado
	public String delete(@PathVariable(value="id")Long id, RedirectAttributes flash ) {
		
		if(id>0) {
			employeeService.delete(id);
			System.out.println("Eliminado");
			flash.addFlashAttribute("success", "Empleado eliminado");
		}
		
			
		return "redirect:/toList";
	}
}	

	