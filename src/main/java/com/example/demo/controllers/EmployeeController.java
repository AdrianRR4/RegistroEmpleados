package com.example.demo.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.models.dao.service.IEmployeeService;
import com.example.demo.models.entity.Employee;
import com.example.demo.util.paginator.PageRender;

@SessionAttributes("employee")
@Controller
public class EmployeeController {
	
	
	@Autowired
	private IEmployeeService employeeService;

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
	
	public String save(@Valid Employee employee, BindingResult result, RedirectAttributes flash,Model model, SessionStatus status) {
		
		if(result.hasErrors()) {// para validar los campos
			model.addAttribute("title", "Registrar empleado");
			return "formEmployee";
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

	